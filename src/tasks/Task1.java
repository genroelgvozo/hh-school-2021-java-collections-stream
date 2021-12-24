package tasks;

import common.Person;
import common.PersonService;
import common.Task;

import java.sql.ClientInfoStatus;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    Set<Person> persons = PersonService.findPersons(personIds);
    List<Person> resultSortPersons = new ArrayList<>();
    personIds.forEach(
            (personId)->{
              resultSortPersons.addAll(
                      persons.stream().filter(person->person.getId().equals(personId.intValue())).collect(Collectors.toList())
              );}
    );
    // O(n^2)
    return resultSortPersons;

    // persons.stream().sorted((a,b)->a.getId().compareTo(b.getId())).collect(Collectors.toList());
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
