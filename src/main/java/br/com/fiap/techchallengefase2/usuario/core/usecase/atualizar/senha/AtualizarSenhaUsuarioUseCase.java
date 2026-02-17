package br.com.fiap.techchallengefase2.usuario.core.usecase.atualizar.senha;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.dto.AtualizarSenhaInputDTO;
import br.com.fiap.techchallengefase2.usuario.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.usuario.core.rule.senha.RuleAtualizarSenhaUsuario;
import br.com.fiap.techchallengefase2.usuario.core.usecase.consultar.id.BuscarUsuarioPorIdUseCase;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class AtualizarSenhaUsuarioUseCase implements AtualizarSenhaUsuario {
    private final CodificadorSenhaGateway codificadorSenhaGateway;
    private final UsuarioGateway usuarioGateway;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final List<RuleAtualizarSenhaUsuario> ruleAtualizarSenhaUsuarioList;

    @Override
    public UsuarioBase atualizar(Long usuarioLogadoId, AtualizarSenhaInputDTO atualizarSenhaInputDto) {
        UsuarioBase usuario = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);

        validarSenha(usuario, atualizarSenhaInputDto);

        String novaSenhaCodificada = codificadorSenhaGateway.codificar(atualizarSenhaInputDto.getNovaSenha());

        return usuarioGateway.atualizarSenha(novaSenhaCodificada, usuarioLogadoId);
    }

    private void validarSenha(UsuarioBase usuario, AtualizarSenhaInputDTO atualizarSenhaInputDto) {
        String senhaAtualDecodificada = codificadorSenhaGateway.decodificar(usuario.getSenha());

        ruleAtualizarSenhaUsuarioList.stream()
                .sorted(Comparator.comparingInt(RuleAtualizarSenhaUsuario::getOrdemValidacao))
                .forEach(rule -> rule.validar(senhaAtualDecodificada, atualizarSenhaInputDto));
    }
}
