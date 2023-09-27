package my.project.autoconfigure.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ContainerCustomizer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class KafkaCustomizerConfig {
    private final KafkaCustomizerProps customizerProps;

    // spring-boot 2.6.0 and later (spring kafka 2.8.0 and later)
    public static <K, V> ContainerCustomizer<K, V, ConcurrentMessageListenerContainer<K, V>> getContainerCustomizer(
            Integer authRetryIntervalSeconds) {
        return listenerContainer -> listenerContainer.getContainerProperties()
                .setAuthExceptionRetryInterval(Duration.ofSeconds(authRetryIntervalSeconds));
    }

    // spring-boot 3.1.0 and later
    @Bean
    @ConditionalOnProperty(value = "kafka.customizer.enabled", havingValue = "true")
    public ContainerCustomizer<Object, Object, ConcurrentMessageListenerContainer<Object, Object>> kafkaContainerCustomizer() {
        return getContainerCustomizer(customizerProps.getAuthRetryIntervalSeconds());
    }
}
