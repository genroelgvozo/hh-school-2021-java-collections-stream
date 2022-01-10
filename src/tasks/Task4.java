package tasks;

import common.ApiPersonDto;
import common.Person;
import common.Task;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/*
Задача 4
Список персон класса Person необходимо сконвертировать в список ApiPersonDto
(предположим, что это некоторый внешний формат)
Конвертер для одной персоны уже предоставлен
FYI - DTO = Data Transfer Object - распространенный паттерн, можно погуглить
 */
public class Task4 implements Task {

  private List<ApiPersonDto> convert(List<Person> persons) {
    return persons.stream()
        .map(Task4::convert) // предпочтительнее чем person -> convert(person) - тут создается объект лямбда, а так передается сразу ссылка на метод
        // было пару вопрос про перегрузку, мол методов convert с одним параметром может быть много
        // Во-первых метод и так выводится из типа стрима (сразу видно что стрим персон, зачем еще писать (Person person) -> convert(person))
        // Можно кликнуть с ctrl и идея покажет что за метод
        // Во-вторых не надо так перегружать методы. Что за конверт? одно дело когда есть класс PersonToDtoConverter
        // (ну занимается конвертацией внутреннего представления персоны во внешнюю dto). И там будет один метод с одним параметром (и возможно еще других несколько с другим кол-вом)
        // А если в классе несколько convert с одним параметром, то что-то уже не то. Либо они отличаются по имени (convertToAPI, convertFromAPI), либо им место в другом классе
        // (если их зона ответственности вообще другая)
        .collect(Collectors.toList());
  }

  private static ApiPersonDto convert(Person person) {
    ApiPersonDto dto = new ApiPersonDto();
    dto.setCreated(person.getCreatedAt().toEpochMilli());
    dto.setId(person.getId().toString());
    dto.setName(person.getFirstName());
    return dto;
  }

  @Override
  public boolean check() {
    Person person1 = new Person(1, "Name", Instant.now());
    Person person2 = new Person(2, "Name", Instant.now());
    return List.of(convert(person1), convert(person2))
        .equals(convert(List.of(person1, person2)));
  }
}
