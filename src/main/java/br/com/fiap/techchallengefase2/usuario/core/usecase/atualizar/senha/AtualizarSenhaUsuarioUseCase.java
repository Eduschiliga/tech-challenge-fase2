package br.com.fiap.techchallengefase2.usuario.core.usecase.atualizar.senha;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.dto.senha.AtualizarSenhaDTO;
import br.com.fiap.techchallengefase2.usuario.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.usuario.core.rule.senha.RuleAtualizarSenhaUsuario;
import br.com.fiap.techchallengefase2.usuario.core.usecase.buscar.BuscarUsuarioPorIdUseCase;

import java.util.Comparator;
import java.util.List;

public class AtualizarSenhaUsuarioUseCase implements AtualizarSenhaUsuario {
    private final CodificadorSenhaGateway codificadorSenhaGateway;
    private final UsuarioGateway usuarioGateway;
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase;
    private final List<RuleAtualizarSenhaUsuario> ruleAtualizarSenhaUsuarioList;

    public AtualizarSenhaUsuarioUseCase(
            CodificadorSenhaGateway codificadorSenhaGateway,
            UsuarioGateway usuarioGateway,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            List<RuleAtualizarSenhaUsuario> ruleAtualizarSenhaUsuarioList
    ) {
        this.codificadorSenhaGateway = codificadorSenhaGateway;
        this.usuarioGateway = usuarioGateway;
        this.buscarUsuarioPorIdUseCase = buscarUsuarioPorIdUseCase;
        this.ruleAtualizarSenhaUsuarioList = ruleAtualizarSenhaUsuarioList;
    }

    @Override
    public Long atualizar(Long usuarioLogadoId, AtualizarSenhaDTO atualizarSenhaDto) {
        UsuarioBase usuario = buscarUsuarioPorIdUseCase.buscarPorId(usuarioLogadoId);

        validarSenha(usuario, atualizarSenhaDto);

        String novaSenhaCodificada = codificadorSenhaGateway.codificar(atualizarSenhaDto.getNovaSenha());

        return usuarioGateway.atualizarSenha(novaSenhaCodificada, usuarioLogadoId);
    }

    private void validarSenha(UsuarioBase usuario, AtualizarSenhaDTO atualizarSenhaDto) {
        String senhaAtualDecodificada = codificadorSenhaGateway.decodificar(usuario.getSenha());

        ruleAtualizarSenhaUsuarioList.stream()
                .sorted(Comparator.comparingInt(RuleAtualizarSenhaUsuario::getOrdemValidacao))
                .forEach(rule -> rule.validar(senhaAtualDecodificada, atualizarSenhaDto));
    }
}
