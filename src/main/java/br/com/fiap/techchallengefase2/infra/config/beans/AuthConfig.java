package br.com.fiap.techchallengefase2.infra.config.beans;

import br.com.fiap.techchallengefase2.core.controller.AuthController;
import br.com.fiap.techchallengefase2.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.core.gateway.TokenGateway;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.usecase.auth.LoginUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    @Bean
    public LoginUseCase loginUseCase(
            UsuarioGateway usuarioGateway,
            CodificadorSenhaGateway codificadorSenhaGateway,
            TokenGateway tokenGateway
    ) {
        return new LoginUseCase(usuarioGateway, codificadorSenhaGateway, tokenGateway);
    }

    @Bean
    public AuthController authController(LoginUseCase loginUseCase) {
        return new AuthController(loginUseCase);
    }
}
