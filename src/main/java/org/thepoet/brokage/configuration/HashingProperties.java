package org.thepoet.brokage.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hashing")
@Data
public class HashingProperties {
    private int saltLength = 16;
    private int hashLength = 32;
    private int parallelism = 1;
    private int memory = 16;
    private int iterations = 2;
}