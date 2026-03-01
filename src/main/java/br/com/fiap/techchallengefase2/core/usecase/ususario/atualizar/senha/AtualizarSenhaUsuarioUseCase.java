package br.com.fiap.techchallengefase2.core.usecase.ususario.atualizar.senha;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.AtualizarSenhaInputDTO;
import br.com.fiap.techchallengefase2.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.senha.RuleAtualizarSenhaUsuario;
import br.com.fiap.techchallengefase2.core.usecase.ususario.consultar.id.BuscarUsuarioPorIdUseCase;
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
