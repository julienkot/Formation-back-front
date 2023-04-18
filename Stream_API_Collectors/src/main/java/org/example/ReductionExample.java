package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReductionExample {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(-2);

        Optional<Integer> red =
                list.stream()
                        .reduce(Integer::sum);
        System.out.println("red = " + red);
    }
}
