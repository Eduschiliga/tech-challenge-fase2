package br.com.fiap.techchallengefase2.core.usecase.usuario.atualizar.senha;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.usuario.AtualizarSenhaInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.senha.RuleSenhaUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class AtualizarSenhaUsuarioUseCase implements AtualizarSenhaUsuario {
    private final CodificadorSenhaGateway codificadorSenhaGateway;
    private final UsuarioGateway usuarioGateway;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final List<RuleSenhaUsuario> ruleSenhaUsuarioList;

    @Override
    public UsuarioBase atualizar(Long usuarioLogadoId, AtualizarSenhaInputDTO atualizarSenhaInputDto) {
        UsuarioBase usuario = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);

        validarSenha(usuario, atualizarSenhaInputDto);

        String novaSenhaCodificada = codificadorSenhaGateway.codificar(atualizarSenhaInputDto.getNovaSenha());
        usuario.atribuirSenhaCodificada(novaSenhaCodificada);

        return usuarioGateway.salvar(usuario);
    }

    private void validarSenha(UsuarioBase usuario, AtualizarSenhaInputDTO atualizarSenhaInputDto) {
        String senhaAtualDecodificada = codificadorSenhaGateway.decodificar(usuario.getSenha());

        ruleSenhaUsuarioList.stream()
                .sorted(Comparator.comparingInt(RuleSenhaUsuario::getOrdemValidacao))
                .forEach(rule -> rule.validar(senhaAtualDecodificada, atualizarSenhaInputDto));
    }
}
