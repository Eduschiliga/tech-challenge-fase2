package br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.senha;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.usuario.AtualizarSenhaInputDTO;

public interface AtualizarSenhaUsuario {

    UsuarioBase atualizar(Long usuarioLogadoId, AtualizarSenhaInputDTO atualizarSenhaInputDTO);

}
