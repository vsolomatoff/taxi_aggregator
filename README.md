
# Демонстрационное приложение taxi_aggregator

Данное приложение создает сервис, позволяющий осуществлять поиск и бронирование, отмену бронирования 
предложений по адресам в различных агрегаторах такси, которые могут регистрироваться в данном сервисе 
и участвовать в нем.


Приложение было создано в рамках выполнения тестового задания на вакансию Java-разработчика


[![Build Status](https://app.travis-ci.com/vsolomatoff/taxi_aggregator.svg?branch=master)](https://app.travis-ci.com/vsolomatoff/taxi_aggregator)
[![codecov](https://codecov.io/gh/vsolomatoff/taxi_aggregator/branch/master/graph/badge.svg?token=1cuMQeJKjG)](https://codecov.io/gh/vsolomatoff/taxi_aggregator)


# Сборка приложения

В приложении используется коллекция в памяти приложения в качестве хранилища данных.

Для сборки приложения используется сборщик maven.

Вы можете просто запустить команду:
```yml
mvn install
```

# Запуск приложения (сервиса)

Запустить приложение Вы можете с помощью следующей команды:
```yml
java -jar taxi_aggregator-1.0.0.jar
```

# Использование сервиса:

Документацию по API сервиса Вы можете найти по ссылке
[Документация по API](https://app.swaggerhub.com/apis-docs/solomatoff70/taxi_aggregator_api/1.0.0)




