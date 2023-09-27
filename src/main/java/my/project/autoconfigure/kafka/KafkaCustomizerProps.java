package my.project.autoconfigure.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka.customizer")
@Data
public class KafkaCustomizerProps {
    // must be less than max.poll.interval.ms consumer property, default = 5 min
    private Integer authRetryIntervalSeconds = 60;
}
