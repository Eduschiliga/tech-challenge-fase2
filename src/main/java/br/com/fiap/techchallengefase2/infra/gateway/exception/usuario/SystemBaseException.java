package br.com.fiap.techchallengefase2.infra.gateway.exception.usuario;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SystemBaseException extends RuntimeException {
    private static final long serialVersionUID = 443911183945646720L;

    private final String code;
    private final String message;
    private final Integer httpStatus;
}