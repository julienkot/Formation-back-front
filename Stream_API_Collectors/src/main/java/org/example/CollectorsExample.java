package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class CollectorsExample {

    public static void main(String[] args) {

        List<Person> persons = new ArrayList<>();

        try(
            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    CollectorsExample.class.getClassLoader().getResourceAsStream("people.txt")
                            )
                    );
            Stream<String> stream = reader.lines();

        ) {
            stream.map(line -> {
                        String[] s = line.split(" ");
                        Person p = new Person(s[0].trim(), Integer.parseInt(s[1]));
                        persons.add(p);
                        return p;
                    })
                    .forEach(System.out::println);
        }catch(IOException ioe) {
            throw new RuntimeException(ioe);
        }
        Stream<Person> stream = persons.stream();

        Optional<Person> opt =
        stream.filter(p -> p.getAge() >= 20)
                .min(Comparator.comparing(Person::getAge));
        System.out.println(opt);

        Optional<Person> opt2 =
                persons.stream().max(Comparator.comparing(Person::getAge));
        System.out.println(opt2);
    }
}
