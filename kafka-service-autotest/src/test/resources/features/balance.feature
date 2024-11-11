#language:ru
@component
Функционал: Проверка метода balance

  Сценарий: Проверка баланса на кошельке
    #И ожидается 500 секунд поднятия сервиса по адресу "${hosts.restService.url}/actuator/health"

    И выполнено подключение к kafka и отправлено сообщение с параметрами из таблицы
      | type   | name       | value                                         |
      | header | __TypeId__ | ru.gpb.tech.mqrestadapter.model.ClientRequest |
      | topic  | topic      | kafka-service-request                         |
      | body   | body       | ${data.base.path}/requests/json/test.json     |

