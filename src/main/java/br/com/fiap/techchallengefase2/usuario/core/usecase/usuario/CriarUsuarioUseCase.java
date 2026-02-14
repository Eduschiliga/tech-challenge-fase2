package br.com.fiap.techchallengefase2.usuario.core.usecase.usuario;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.Usuario;
import br.com.fiap.techchallengefase2.usuario.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.usuario.core.rule.usuario.RuleCriarUsuario;

import java.util.Comparator;
import java.util.List;

public class CriarUsuarioUseCase {
    private final CodificadorSenhaGateway codificadorSenhaGateway;
    private final List<RuleCriarUsuario> ruleCriarUsuarioList;
    private final UsuarioGateway usuarioGateway;

    public CriarUsuarioUseCase(
            CodificadorSenhaGateway codificadorSenhaGateway,
            UsuarioGateway usuarioGateway,
            List<RuleCriarUsuario> ruleCriarUsuarioList
    ) {
        this.codificadorSenhaGateway = codificadorSenhaGateway;
        this.ruleCriarUsuarioList = ruleCriarUsuarioList;
        this.usuarioGateway = usuarioGateway;
    }

    public Long criar(Usuario usuario) {
        validarUsuario(usuario);

        atribuirSenhaCodificada(usuario);

        return usuarioGateway.salvar(usuario);
    }

    private void validarUsuario(Usuario usuario) {
        // Ordena as rules pela prioridade antes de executar
        ruleCriarUsuarioList.stream()
                .sorted(Comparator.comparingInt(RuleCriarUsuario::getOrdemValidacao))
                .forEach(rule -> rule.validar(usuario));
    }

    private void atribuirSenhaCodificada(Usuario usuario) {
        String senhaCodificada = codificadorSenhaGateway.codificar(usuario.getSenha());
        usuario.atribuirSenhaCodificada(senhaCodificada);
    }

}
