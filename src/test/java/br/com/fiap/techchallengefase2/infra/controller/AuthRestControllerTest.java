package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.AuthController;
import br.com.fiap.techchallengefase2.core.dto.auth.LoginInputDTO;
import br.com.fiap.techchallengefase2.core.dto.auth.TokenOutputDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthRestControllerTest {

    @Mock
    private AuthController authController;

    @InjectMocks
    private AuthRestController authRestController;

    @Test
    void login_DeveRetornarStatusOkEToken() {
        LoginInputDTO input = new LoginInputDTO("usuario_teste", "senha123");
        TokenOutputDTO output = mock(TokenOutputDTO.class);

        when(authController.login(argThat(dto ->
                dto.getLogin().equals("usuario_teste") &&
                        dto.getSenha().equals("senha123")
        ))).thenReturn(output);

        ResponseEntity<TokenOutputDTO> response = authRestController.login(input);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(authController).login(argThat(dto -> dto.getLogin().equals("usuario_teste")));
    }
}