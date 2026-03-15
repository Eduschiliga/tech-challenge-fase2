package br.com.fiap.techchallengefase2.infra.controller.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String code,
        String message,
        String path,
        LocalDateTime timestamp,
        List<ValidationError> errors
) {
    public record ValidationError(String field, String message) {}
}
