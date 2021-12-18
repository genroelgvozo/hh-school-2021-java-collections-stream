package tasks;

import common.Person;
import common.Task;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 implements Task {

  // !!! Редактируйте этот метод !!!
  private List<Person> sort(Collection<Person> persons) {
    // черновой вариант на коллекциях
                    /*List<Person> perList = new ArrayList<Person>(persons);

                    perList.sort(new Comparator<Person>() {
                      @Override
                      public int compare(Person o1, Person o2) {
                        if (!o1.getSecondName().equals(o2.getSecondName())){
                          return o1.getSecondName().compareTo(o2.getSecondName());
                        }
                        else if (!o1.getFirstName().equals(o2.getFirstName())){
                          return o1.getFirstName().compareTo(o2.getFirstName());
                        }
                        else return o1.getCreatedAt().compareTo(o2.getCreatedAt());

                      }
                    });
                    return perList;*/
    // версия на стримах
    // методу sorted передаём компаратор, который организует требуемое сравнение, результат преобразуем в список
    return persons.stream().sorted(new Comparator<Person>() {
                                        @Override
                                        public int compare(Person o1, Person o2) {
                                          if (!o1.getSecondName().equals(o2.getSecondName())){
                                            return o1.getSecondName().compareTo(o2.getSecondName());
                                          }
                                          else if (!o1.getFirstName().equals(o2.getFirstName())){
                                            return o1.getFirstName().compareTo(o2.getFirstName());
                                          }
                                          else return o1.getCreatedAt().compareTo(o2.getCreatedAt());

                                        }}).collect(Collectors.toList());

  }

  @Override
  public boolean check() {
    Instant time = Instant.now();
    List<Person> persons = List.of(
        new Person(1, "Oleg", "Ivanov", time),
        new Person(2, "Vasya", "Petrov", time),
        new Person(3, "Oleg", "Petrov", time.plusSeconds(1)),
        new Person(4, "Oleg", "Ivanov", time.plusSeconds(1))
    );
    List<Person> sortedPersons = List.of(
        new Person(1, "Oleg", "Ivanov", time),
        new Person(4, "Oleg", "Ivanov", time.plusSeconds(1)),
        new Person(3, "Oleg", "Petrov", time.plusSeconds(1)),
        new Person(2, "Vasya", "Petrov", time)
    );
    return sortedPersons.equals(sort(persons));
  }
}
