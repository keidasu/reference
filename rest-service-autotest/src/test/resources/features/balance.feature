#language:ru
@component
Функционал: Проверка метода balance

  Сценарий: Проверка баланса на кошельке
    #И ожидается 500 секунд поднятия сервиса по адресу "${hosts.restService.url}/actuator/health"

    И сохраняем в переменные сценария значения из таблицы
      | clientId | 550e8400-e29b-41d4-a716-446655440000 |
      | dateFrom | 2024-06-01T12:00:00Z                 |
      | dateTo   | 2024-06-01T13:00:00Z                 |

    И выполнен POST запрос на URL "${hosts.restService.url}/wallet/balance" с headers и parameters из таблицы. Полученный ответ сохранен в переменную "orderResponse"
      | type   | name         | value                                     |
      | header | Content-Type | application/json; charset=utf-8           |
      | body   |              | ${data.base.path}/requests/json/test.json |
    И в ответе "orderResponse" код ответа равен 200
    И из ответа "orderResponse" значение тела сохранено в переменную "bodyResponse"

    И значения из json строки "bodyResponse", найденные по JsonPath из таблицы, сохранены в переменные
      | $.balance | balanceResponse |

    И в БД "rest-service-db" выполняем запрос "получить баланс клиента" с параметрами и сохраняем результат в "result"
    И в ответе от БД "result", проверяем значения полей
      | balance | ${balanceResponse} |

