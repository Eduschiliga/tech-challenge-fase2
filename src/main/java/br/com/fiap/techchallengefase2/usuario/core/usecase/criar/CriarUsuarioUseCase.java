package br.com.fiap.techchallengefase2.usuario.core.usecase.criar;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.usuario.core.rule.dados.RuleDadosUsuario;

import java.util.Comparator;
import java.util.List;

public class CriarUsuarioUseCase implements CriarUsuario {
    private final CodificadorSenhaGateway codificadorSenhaGateway;
    private final List<RuleDadosUsuario> ruleDadosUsuarioList;
    private final UsuarioGateway usuarioGateway;

    public CriarUsuarioUseCase(
            CodificadorSenhaGateway codificadorSenhaGateway,
            UsuarioGateway usuarioGateway,
            List<RuleDadosUsuario> ruleDadosUsuarioList
    ) {
        this.codificadorSenhaGateway = codificadorSenhaGateway;
        this.ruleDadosUsuarioList = ruleDadosUsuarioList;
        this.usuarioGateway = usuarioGateway;
    }

    @Override
    public Long criar(UsuarioBase usuario) {
        validarUsuario(usuario);

        atribuirSenhaCodificada(usuario);

        return usuarioGateway.salvar(usuario);
    }

    private void validarUsuario(UsuarioBase usuario) {
        // Ordena as rules pela prioridade antes de executar

        ruleDadosUsuarioList.stream()
                .sorted(Comparator.comparingInt(RuleDadosUsuario::getOrdemValidacao))
                .forEach(rule -> rule.validar(usuario));
    }

    private void atribuirSenhaCodificada(UsuarioBase usuario) {
        String senhaCodificada = codificadorSenhaGateway.codificar(usuario.getSenha());
        usuario.atribuirSenhaCodificada(senhaCodificada);
    }

}
