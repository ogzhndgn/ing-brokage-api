package org.thepoet.brokage.i18n;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageTranslator {

    private final MessageSource messageSource;

    public String getMessage(String messageCode) {
        try {
            return messageSource.getMessage(messageCode, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return messageCode;
        }
    }

    public String getMessage(String messageCode, Map<String, String> details) {
        try {
            final Object[] args = details.isEmpty() ? null : details.values().toArray();
            return messageSource.getMessage(messageCode, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return messageCode;
        }
    }
}