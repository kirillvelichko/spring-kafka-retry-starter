# Как использовать этот стартер

## Причина создания

По умолчанию в Spring Kafka при ошибке авторизации подключения к Kafka в приложении consumer не пытается переподключиться.
Этот стартер настраивает автоматическое переподключение Kafka consumer в случае ошибки авторизации.

## Подключение зависимостей

```groovy
dependencies {
    implementation "my.project:kafka-spring-boot-starter:1"
}
```

## Конфигурация

```yaml
kafka:
  customizer:
    enabled: true
    auth-retry-interval-seconds: 120
```

__Параметры:__

_auth-retry-interval-seconds_  
Опционален, если не задан в конфигурации будет установлено значение 60 секунд.  
Должен быть меньше чем max.poll.interval.ms у consumer, по умолчанию
равен 5 минутам.

## Использование

__spring-boot version >= 3.1.0__  
При настройке через yaml ничего дополнительно настраивать не требуется.

__spring-boot version < 3.1.0__  
или  
__настройка через создание бинов__

```java
@Bean
public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListener() {
        var factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(getConsumerFactory());
        // Необходимо использовать public static метод из класса KafkaCustomizerConfig
        factory.setContainerCustomizer(getContainerCustomizer(60));
        return factory;
}
```