package com.sparta.test_code.unit.utils;

import com.sparta.test_code.utils.PasswordEncoder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith( MockitoExtension.class)
public class PasswordEncoderTest {

    @Test
    void 비밀번호_검사_성공() {
        //given
        String rawPassword = "123456";
        String encodedPassword = PasswordEncoder.encode(rawPassword);

        //when
        boolean actualResult = PasswordEncoder.matches(rawPassword, encodedPassword);

        //then
        Assertions.assertThat(actualResult).isTrue();

    }

    @Test
    void 비밀번호_검사_실패() {
        //given
        String encodedPassword = PasswordEncoder.encode("123456");
        String wrongPassword = "abcdef";

        //when
        boolean actualResult = PasswordEncoder.matches(wrongPassword, encodedPassword);

        //then
        Assertions.assertThat(actualResult).isFalse();
    }
}
