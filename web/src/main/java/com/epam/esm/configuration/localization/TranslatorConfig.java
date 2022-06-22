package com.epam.esm.configuration.localization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Class {@code Translator} is designed for getting localized messages.
 *
 */
@Component
public class TranslatorConfig {
    private static ResourceBundleMessageSource resourceBundleMessageSource;

    @Autowired
    TranslatorConfig(ResourceBundleMessageSource resourceBundleMessageSource) {
        TranslatorConfig.resourceBundleMessageSource = resourceBundleMessageSource;
    }

    /**
     * Method for getting localized message from property file.
     *
     * @param msg code of message to get
     * @return localized message
     */

    public static String toLocale(String msg) {
        Locale locale = LocaleContextHolder.getLocale();
        return resourceBundleMessageSource.getMessage(msg, null, locale);
    }
}
