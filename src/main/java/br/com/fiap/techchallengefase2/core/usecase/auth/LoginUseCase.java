package br.com.fiap.techchallengefase2.core.usecase.auth;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.auth.LoginInputDTO;
import br.com.fiap.techchallengefase2.core.dto.auth.TokenOutputDTO;
import br.com.fiap.techchallengefase2.core.exception.usuario.DadosUsuarioInvalidosException;
import br.com.fiap.techchallengefase2.core.exception.usuario.UsuarioNaoEncontradoException;
import br.com.fiap.techchallengefase2.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.core.gateway.TokenGateway;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginUseCase {
    private final UsuarioGateway usuarioGateway;
    private final CodificadorSenhaGateway codificadorSenhaGateway;
    private final TokenGateway tokenGateway;

    public TokenOutputDTO realizarLogin(LoginInputDTO input) {
        UsuarioBase usuario = usuarioGateway.buscarPorLogin(input.getLogin())
                .orElseThrow(() -> new UsuarioNaoEncontradoException());

        String senhaCodificadaInput = codificadorSenhaGateway.codificar(input.getSenha());
        
        if (!usuario.getSenha().equals(senhaCodificadaInput)) {
            throw new DadosUsuarioInvalidosException("Senha incorreta.");
        }

        return new TokenOutputDTO(tokenGateway.gerarToken(usuario.getUsuarioId()));
    }
}
