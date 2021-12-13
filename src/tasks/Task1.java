package tasks;

import common.Person;
import common.PersonService;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */
public class Task1 implements Task {

    // !!! Редактируйте этот метод !!!
    private List<Person> findOrderedPersons(List<Integer> personIds) {
        class PositionComparator implements Comparator<Person> {

            @Override
            public int compare(Person p1, Person p2) {
                return Integer.compare(personIds.indexOf(p1.getId()), personIds.indexOf(p2.getId()));

            }
        }

        Set<Person> persons = PersonService.findPersons(personIds);


        return persons.stream().sorted(
                Comparator.comparingInt(p -> personIds.indexOf(p.getId()))).collect(Collectors.toList());
    }


    @Override
    public boolean check() {
        List<Integer> ids = List.of(1, 2, 3);

        return findOrderedPersons(ids).stream()
                .map(Person::getId)
                .collect(Collectors.toList())
                .equals(ids);
    }

}
