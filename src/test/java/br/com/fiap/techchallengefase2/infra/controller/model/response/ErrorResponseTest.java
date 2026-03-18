package br.com.fiap.techchallengefase2.infra.controller.model.response;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {

    @Test
    void deveInstanciarEAcessarPropriedadesCorretamente() {
        LocalDateTime timestamp = LocalDateTime.now();
        List<ErrorResponse.ValidationError> errors = List.of(
                new ErrorResponse.ValidationError("nome", "O nome é obrigatório")
        );

        ErrorResponse errorResponse = new ErrorResponse(
                "400",
                "Erro de validação",
                "/api/usuarios",
                timestamp,
                errors
        );

        assertNotNull(errorResponse);
        assertEquals("400", errorResponse.code());
        assertEquals("Erro de validação", errorResponse.message());
        assertEquals("/api/usuarios", errorResponse.path());
        assertEquals(timestamp, errorResponse.timestamp());
        assertEquals(1, errorResponse.errors().size());

        ErrorResponse.ValidationError validationError = errorResponse.errors().get(0);
        assertEquals("nome", validationError.field());
        assertEquals("O nome é obrigatório", validationError.message());
    }

    @Test
    void devePermitirErrosNulos() {
        ErrorResponse errorResponse = new ErrorResponse(
                "500",
                "Erro Interno",
                "/api/usuarios",
                LocalDateTime.now(),
                null
        );

        assertNotNull(errorResponse);
        assertNull(errorResponse.errors());
    }

    @Test
    void deveInstanciarValidationErrorCorretamente() {
        ErrorResponse.ValidationError validationError = new ErrorResponse.ValidationError(
                "email",
                "Formato inválido"
        );

        assertNotNull(validationError);
        assertEquals("email", validationError.field());
        assertEquals("Formato inválido", validationError.message());
    }
}