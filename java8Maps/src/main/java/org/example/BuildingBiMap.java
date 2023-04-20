package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BuildingBiMap {
    public static void main(String[] args) {

        List<Person> persons = new ArrayList<>();

        try (
                BufferedReader reader =

                        new BufferedReader(
                                new InputStreamReader(
                                        BuildingBiMap.class.getClassLoader().getResourceAsStream("people.txt")
                                )
                        );
                Stream<String> stream = reader.lines();

        ) {
            stream.map(line -> {
                        String[] s = line.split(" ");
                        Person p = new Person(
                                s[0].trim(), Integer.parseInt(s[1]),
                                s[2].trim());
                        persons.add(p);
                        return p;
                    })
                    .forEach(System.out::println);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

        persons.forEach(System.out::println);

        List<Person> list1 = persons.subList(1, 10);
        List<Person> list2 = persons.subList(10, persons.size());

        Map<Integer, List<Person>> map1 = mapByAge(list1);
        System.out.println("Map 1");
        map1.forEach((age,list) -> System.out.println(age + " -> " + list));

        Map<Integer, List<Person>> map2 = mapByAge(list2);
        System.out.println("Map 2");
        map2.forEach((age,list) -> System.out.println(age + " -> " + list));

        map2.entrySet().stream()
                .forEach(
                        entry ->
                                map1.merge(
                                        entry.getKey(),
                                        entry.getValue(),
                                        (l1, l2) -> {
                                                l1.addAll(l2);
                                                return l1;
                                                }

                                )
                );

        System.out.println("Map 1 merged");
        map1.forEach((age,list) -> System.out.println(age + " -> " + list));


        Map<Integer, List<Person>> map =
                persons.stream().collect(
                        Collectors.groupingBy(
                                Person::getAge
                        )
                );

        map.forEach((age,list) -> System.out.println(age + " -> " + list));

        Map<Integer, Map<String, List<Person>>> bimap =
                new HashMap<>();

        persons.forEach(person ->
                bimap.computeIfAbsent(
                        person.getAge(),
                        HashMap::new
                ).merge(
                        person.getGender(),
                        new ArrayList<>(Arrays.asList(person)),
                        (l1, l2) -> {
                            l1.addAll(l2);
                            return l1;
                        }
                )
        );

        System.out.println("Bimap : ");
        bimap.forEach(
                (age, m) -> System.out.println(age + " -> " + m)
        );


    }

    private static Map<Integer, List<Person>> mapByAge(List<Person> list) {
        Map<Integer, List<Person>> map =
                list.stream().collect(
                        Collectors. groupingBy(
                                Person::getAge
                        ));
        return map;
    };

}