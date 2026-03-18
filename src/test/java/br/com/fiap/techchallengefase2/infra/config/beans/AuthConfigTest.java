package br.com.fiap.techchallengefase2.infra.config.beans;

import br.com.fiap.techchallengefase2.core.controller.AuthController;
import br.com.fiap.techchallengefase2.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.core.gateway.TokenGateway;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.usecase.auth.LoginUseCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class AuthConfigTest {

    private final AuthConfig authConfig = new AuthConfig();
    private final UsuarioGateway usuarioGateway = mock(UsuarioGateway.class);
    private final CodificadorSenhaGateway codificadorSenhaGateway = mock(CodificadorSenhaGateway.class);
    private final TokenGateway tokenGateway = mock(TokenGateway.class);
    private final LoginUseCase loginUseCase = mock(LoginUseCase.class);

    @Test
    void loginUseCase_DeveInstanciarCorretamente() {
        var result = authConfig.loginUseCase(usuarioGateway, codificadorSenhaGateway, tokenGateway);
        assertNotNull(result);
    }

    @Test
    void authController_DeveInstanciarCorretamente() {
        AuthController result = authConfig.authController(loginUseCase);
        assertNotNull(result);
    }
}