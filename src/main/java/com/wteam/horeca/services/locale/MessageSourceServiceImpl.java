package com.wteam.horeca.services.locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.lang.Nullable;

import java.util.Locale;

public class MessageSourceServiceImpl extends ReloadableResourceBundleMessageSource implements MessageSourceService {

    public MessageSourceServiceImpl() {
        this.setBasename("classpath:locale/messages");
        this.setDefaultEncoding("UTF-8");
        this.setCacheMillis(3600); // reload message source once per hour
    }

    @Override
    public String getMessage(String code) {
        return getMessage(code, null);
    }

    @Override
    public String getMessage(String code, @Nullable Object[] args) {
        return getMessage(code, args, Locale.getDefault());
    }

}
