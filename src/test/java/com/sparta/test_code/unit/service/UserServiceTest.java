package com.sparta.test_code.unit.service;

import com.sparta.test_code.dto.UserCreationDto;
import com.sparta.test_code.dto.UserResponseDto;
import com.sparta.test_code.entity.User;
import com.sparta.test_code.respository.UserRepository;
import com.sparta.test_code.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //모키토 프레임워크 같이 사용
public class UserServiceTest {

    @InjectMocks //목 객체를 주입 받아야해서 이 어노테이션을 사용
    UserService userService;

    @Mock //서비스는 리포지토리에 의존해서 목 객체 생성
    UserRepository userRepository;

    @Test
    void 유저_저장_성공_테스트() {
        //given
        UserCreationDto userCreateDto = new UserCreationDto("박병천", "genius1788@naver.com", "1234");
        User user = new User("박병천", "genius1788@naver.com", "1234");

        //목 리포지토리에 user가 저장될 때 user를 반환해라
        when(userRepository.save(any(User.class))).thenReturn(user);

        //when
        UserResponseDto actualResult = userService.saveUser(userCreateDto);

        //then
        //(상태 검증) -> 결과값이 기대값과 동일한가?
        Assertions.assertThat(actualResult.getName()).isEqualTo("박병천");
        Assertions.assertThat(actualResult.getEmail()).isEqualTo("genius1788@naver.com");

        //(행위 검증) -> userRepository.save() 메서드가 1회 실행이 되었는가?
        verify(userRepository, times(1)).save(any(User.class));
    }
}
