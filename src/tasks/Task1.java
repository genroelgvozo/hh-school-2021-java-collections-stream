package tasks;

import common.Person;
import common.PersonService;
import common.Task;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */
public class Task1 implements Task {

  /*
   Такой кейс в реальности возникает достаточно часто
   Например, нам надо с фильтрами и пагинацией (а для пагинации какая-то фиксированная сортировка обятельна) вытащить из базы тех же персон
   Но данные нужны из некоторых таблиц, которые не требуются в фильтрации,
   а некоторые вообще OneToMany и множат персон при джойне, а значит не удастся сделать нормальную пагинацию с limit+offset
   Поэтому обычно пагинацией получают айдишки (применяя сортировку и фильтры)
   а после по айдишкам вытаскивают все данные, приджойнивая уже необходимое. И вот это нужно отсортировать в порядке айдишек

   Асимптотика - O(n)
   */
  private List<Person> findOrderedPersons(List<Integer> personIds) {
    Map<Integer, Person> idPersonMap = PersonService.findPersons(personIds).stream() // обычно называю просто personMap, так как ключ Integer подразумевает  что там id, но можно и так
      .collect(Collectors.toMap(Person::getId, Function.identity()));
    return personIds.stream()
        .map(idPersonMap::get)
        .filter(Objects::nonNull) // Этот фильтр большинство пропускали, и на самом деле это нормально. Но иногда он необходим
        // Естественно если айдишки переданны извне, то по ним может что-то не найтись,
        // но в этом случае обычно не требуется сортировать, тут скорее кейс, что описан выше
        // И тогда вопрос, а как это - мы селектнули из базы айди, а потом селектим персон и не находим
        // Ну во-первых в ридонли без транзакций повторный селект может и не найти что нужно
        // Но еще более частый кейс - айдишки получили из другого источника. Например база elasticsearch, которая хорошо подходит для полнотекстового поиска.
        // И оттуда обычно получают айди найденных документов,
        // а сами документы уже ищут в базе, и из-за лага индексации (которого не избежать), может быть разница. В таком случае filter необходим
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
