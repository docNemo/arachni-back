# arachni-back

## Деплой базы данных

Команда для деплоя схемы БД

```shell
mvn clean liquibase:update -Dliquibase.url=jdbc:postgresql://192.168.3.187:5432/arachni -Dliquibase.username=<Логин УЗ> -Dliquibase.password=<Пароль УЗ> -Dliquibase.verbose=true
```
