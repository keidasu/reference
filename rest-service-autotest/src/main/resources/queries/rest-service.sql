-- получить баланс клиента
select balance from gpb.wallet where client_id = '${clientId}';