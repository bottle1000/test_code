package com.sparta.test_code.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.test_code.dto.UserCreationDto;
import com.sparta.test_code.dto.UserResponseDto;
import com.sparta.test_code.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {

    private static final String BASE_URL = "/users";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void 사용자_저장_컨트롤러_호출_성공() throws Exception {
        //given
        UserCreationDto userCreationDto = new UserCreationDto("name", "email", "password");
        UserResponseDto response = new UserResponseDto(1L, "name", "email");

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
