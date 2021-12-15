package tasks;

import common.Person;
import common.PersonService;
import common.Task;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */

/*  Комментарии

Асимпотика следующая O(n) на заполнение HashMap + O(n) перебор set Person
Итого O(2*n) -> O(n)
HashMap использую для быстрого получения порядка Персоны в последовательности O(1)
*/
public class Task1 implements Task {

    // !!! Редактируйте этот метод !!!
    private List<Person> findOrderedPersons(List<Integer> personIds) {
        Set<Person> persons = PersonService.findPersons(personIds);

        Map<Integer, Integer> hmPersonIds = IntStream.range(0, personIds.size()).boxed().collect(Collectors.toMap(personIds::get, i -> i));
        Person[] personsInRightOrder = new Person[persons.size()];

        persons.forEach(p -> personsInRightOrder[hmPersonIds.get(p.getId())] = p);

        return Arrays.asList(personsInRightOrder);
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
