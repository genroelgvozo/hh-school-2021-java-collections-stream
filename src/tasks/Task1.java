package tasks;

import common.Person;
import common.PersonService;
import common.Task;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
    Set<Person> persons = PersonService.findPersons(personIds);

    // Нужна только карта соответствий
    Map<Integer, Person> personNameId = persons.stream()
    .collect(Collectors.toMap(Person::getId, x -> x));
 
    // Собираем в лист по порядку указанному в personIds. Асимптотика метода O(n)
    return personIds.stream()
    .map(x -> personNameId.get(x))
    .collect(Collectors.toList());

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
