#language:ru
@component
Функционал: Проверка метода balance

  Сценарий: Проверка баланса на кошельке
    #И ожидается 500 секунд поднятия сервиса по адресу "${hosts.restService.url}/actuator/health"

    И настроить и подключиться к RabbitMQ
    И отправить содержимое файла "${data.base.path}/requests/json/test.json" в очередь "adapterQueue" как JSON
    #И получить последнее сообщение из очереди "adapterQueue" и сохранить его в переменную "response"
    И закрыть соединение с RabbitMQ

