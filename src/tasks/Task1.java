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
    Set<Person> persons = PersonService.findPersons(personIds);
    // При решении этой задачи для каждого Id я запомню в HashMap его позицию в исходном списке;
    // теперь для каждого Person по его Id я смогу быстро (~O(1)) сказать, на какое место он должен встать;
    // создам массив размера personIds.size();
    // пройдусь по множеству и каждый его элемент помещу в массив на нужное место;
    // верну данные в требуемом формате.
    // Итоговая сложность: ~O(n)
    Map<Integer, Integer> ordinalNumbersForIds = new HashMap<>();
    int numberOfId = 0;
    for (Integer id: personIds) { // O(n)
      ordinalNumbersForIds.put(id, numberOfId); // ~O(1)
      numberOfId++;
    }
    Person[] personsSortedById = new Person[personIds.size()];
    for (Person person: persons) { //O(n)
      Integer index = ordinalNumbersForIds.get(person.getId()); // ~O(1)
      personsSortedById[index] = person;
    }
    return Arrays.asList(personsSortedById); //O (n)
    // Если есть вероятность, что не для всех id найдутся объекты в Set, то имеет смысл сделать:
    /*
    return Arrays.stream(personsSortedById)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    */
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
