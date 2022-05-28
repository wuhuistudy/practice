package com.springbootexample.practice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SpringBootTest
class PracticeApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        List<String> strings = Arrays.asList("C", "D", "A", "B", null)
                .stream()
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());
        System.out.println(strings);
    }
}
