package com.sparta.test_code.unit.repository;

import com.sparta.test_code.entity.User;
import com.sparta.test_code.respository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 유저_저장_성공_테스트() {
        //given
        User user = new User("박병천", "genius1788@naver.com", "1234");

        //when
        User actualResult = userRepository.save(user);

        //then
        User expectedUser = new User("박병천", "genius1788@naver.com", "1234");
        // 객체를 한번에 검사(재귀를 돌면서)
        Assertions.assertThat(actualResult)
                .usingRecursiveComparison() // 재귀를 돌며 값들만 검사, 주소는 검사 x
                .ignoringFields("id") //id는 알아서 DB가 해주니 검사X
                .isEqualTo(expectedUser);
    }
}
