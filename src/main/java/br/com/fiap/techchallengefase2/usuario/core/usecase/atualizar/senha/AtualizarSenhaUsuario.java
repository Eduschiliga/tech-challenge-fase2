package br.com.fiap.techchallengefase2.usuario.core.usecase.atualizar.senha;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.dto.AtualizarSenhaInputDTO;

public interface AtualizarSenhaUsuario {

    UsuarioBase atualizar(Long usuarioLogadoId, AtualizarSenhaInputDTO atualizarSenhaInputDTO);

}
