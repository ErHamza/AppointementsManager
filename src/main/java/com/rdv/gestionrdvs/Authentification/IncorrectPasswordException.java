package com.rdv.gestionrdvs.Authentification;

import org.springframework.security.core.AuthenticationException;

public class IncorrectPasswordException extends AuthenticationException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
