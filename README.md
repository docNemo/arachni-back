# arachni-back

## Деплой базы данных

Команда для деплоя схемы БД

```shell
mvn clean liquibase:update -Dliquibase.url=jdbc:postgresql://192.168.1.18:5432/arachni -Dliquibase.username=<Логин УЗ> -Dliquibase.password=<Пароль УЗ> -Dliquibase.verbose=true
```
