package br.com.fiap.techchallengefase2.core.controller;

import br.com.fiap.techchallengefase2.core.dto.auth.LoginInputDTO;
import br.com.fiap.techchallengefase2.core.dto.auth.TokenOutputDTO;
import br.com.fiap.techchallengefase2.core.usecase.auth.LoginUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private LoginUseCase loginUseCase;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_DeveRetornarTokenOutputDtoComSucesso() {
        LoginInputDTO input = new LoginInputDTO("user_test", "password123");
        TokenOutputDTO tokenEsperado = new TokenOutputDTO("generated-token");

        when(loginUseCase.realizarLogin(input)).thenReturn(tokenEsperado);

        TokenOutputDTO result = authController.login(input);

        assertNotNull(result);
        assertEquals("generated-token", result.getToken());
        verify(loginUseCase).realizarLogin(input);
    }
}