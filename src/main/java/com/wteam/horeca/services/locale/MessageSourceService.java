package com.wteam.horeca.services.locale;

import org.springframework.context.MessageSource;

public interface MessageSourceService extends MessageSource {
    String getMessage(String code);

    String getMessage(String code, Object[] args);
}
