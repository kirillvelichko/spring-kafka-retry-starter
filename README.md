### Description

Стартер настраивает retry при ошибке авторизации подключения к Kafka-брокеру.
По умолчанию в Spring Kafka при ошибке авторизации переподключение не выполняется.

### Gradle dependencies

```groovy
dependencies {
    implementation "my.project:kafka-spring-boot-starter:1"
}
```

### Spring configuration

```yaml
kafka:
  customizer:
    enabled: true
    auth-retry-interval-seconds: 60
```

_auth-retry-interval-seconds_  
Опционален, если не задан в конфигурации, будет установлено значение 60 секунд.  
Должен быть меньше чем max.poll.interval.ms у consumer, по умолчанию равен 5 минутам.

__spring-boot version >= 3.1.0__  
При настройке через yaml ничего дополнительно настраивать не требуется.

__spring-boot version < 3.1.0__ или __настройка через создание бинов__

```java
@Bean
public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<>();
        // Подставить ConsumerFactory
        factory.setConsumerFactory(getConsumerFactory());
        // Использовать static метод из класса KafkaCustomizerConfig
        factory.setContainerCustomizer(KafkaCustomizerConfig.getContainerCustomizer(60));
        return factory;
}
```
