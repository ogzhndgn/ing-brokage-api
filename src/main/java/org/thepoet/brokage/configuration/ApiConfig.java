package org.thepoet.brokage.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
@RequiredArgsConstructor
public class ApiConfig {

    private final HashingProperties hashingProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(hashingProperties.getSaltLength(), hashingProperties.getHashLength(),
                hashingProperties.getParallelism(), hashingProperties.getMemory(),
                hashingProperties.getIterations());
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("messages");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setFallbackToSystemLocale(false);
        return resourceBundleMessageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
        acceptHeaderLocaleResolver.setDefaultLocale(new Locale("tr"));
        List<Locale> supportedLocales = Arrays.asList(new Locale("tr"), new Locale("en"));
        acceptHeaderLocaleResolver.setSupportedLocales(supportedLocales);
        return acceptHeaderLocaleResolver;
    }
}
