package br.com.fiap.techchallengefase2.usuario.core.rule.credenciais;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.dto.DadosParciaisUsuarioDTO;

public interface RuleCredenciaisUsuario {

    void validar(UsuarioBase usuarioAtual, DadosParciaisUsuarioDTO dadosParciaisDto);

}
