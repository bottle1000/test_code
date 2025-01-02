package com.sparta.test_code.unit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.test_code.controller.UserController;
import com.sparta.test_code.dto.UserCreationDto;
import com.sparta.test_code.dto.UserResponseDto;
import com.sparta.test_code.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static final String BASE_URL = "/users";

    @MockitoBean //컨트롤러에서 의존성을 배제하는 방법
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void 사용자_저장_컨트롤러_호출_성공() throws Exception {
        //given
        UserCreationDto userCreationDto = new UserCreationDto("name", "email", "password");
        UserResponseDto response = new UserResponseDto(1L, "name", "email");
        when(userService.saveUser(any(UserCreationDto.class))).thenReturn(response);
        //when
        ResultActions result = this.mockMvc.perform(post(BASE_URL)
                .contentType("application/json")
                .content(this.objectMapper.writeValueAsString(userCreationDto)));

        //then
        String contentAsString = result.andReturn().getResponse().getContentAsString();
        UserResponseDto actualResult
                = this.objectMapper.readValue(contentAsString, UserResponseDto.class);

        // Http Status가 200 Ok 인지 검증
        result.andExpect(status().isOk());
        assertThat(actualResult.getName()).isEqualTo(userCreationDto.getName());
        assertThat(actualResult.getEmail()).isEqualTo(userCreationDto.getEmail());
    }
}
