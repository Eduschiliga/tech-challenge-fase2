package br.com.fiap.techchallengefase2.core.controller;

import br.com.fiap.techchallengefase2.core.dto.auth.LoginInputDTO;
import br.com.fiap.techchallengefase2.core.dto.auth.TokenOutputDTO;
import br.com.fiap.techchallengefase2.core.usecase.auth.LoginUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthController {
    private final LoginUseCase loginUseCase;

    public TokenOutputDTO login(LoginInputDTO input) {
        return loginUseCase.realizarLogin(input);
    }
}
