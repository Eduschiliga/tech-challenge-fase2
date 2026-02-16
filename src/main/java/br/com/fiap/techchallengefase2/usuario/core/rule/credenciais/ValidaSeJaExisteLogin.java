package br.com.fiap.techchallengefase2.usuario.core.rule.credenciais;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.dto.DadosParciaisUsuarioDTO;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;

import java.util.Objects;

public class ValidaSeJaExisteLogin implements RuleCredenciaisUsuario {
    private final UsuarioGateway usuarioGateway;

    public ValidaSeJaExisteLogin(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public void validar(UsuarioBase usuarioAtual, DadosParciaisUsuarioDTO dadosParciaisDto) {
        if (!Objects.equals(dadosParciaisDto.getLogin(), usuarioAtual.getLogin())) {
            boolean existeUsuarioComLogin = usuarioGateway.existeUsuarioComLogin(dadosParciaisDto.getLogin());

            if (existeUsuarioComLogin) {
                throw new IllegalArgumentException("Atualmente já existe um usuário cadastrado com o login informado");
            }
        }
    }
}
