package br.com.fiap.techchallengefase2.usuario.core.usecase.criar;

import br.com.fiap.techchallengefase2.usuario.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.usuario.core.dto.CriarUsuarioDTO;
import br.com.fiap.techchallengefase2.usuario.core.dto.DadosParciaisUsuarioDTO;
import br.com.fiap.techchallengefase2.usuario.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.usuario.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.usuario.core.rule.credenciais.RuleCredenciaisUsuario;
import br.com.fiap.techchallengefase2.usuario.core.rule.dados.RuleDadosUsuario;

import java.util.List;

import static br.com.fiap.techchallengefase2.usuario.core.domain.factory.UsuarioFactory.criarUsuario;

public class CriarUsuarioUseCase implements CriarUsuario {
    private final CodificadorSenhaGateway codificadorSenhaGateway;
    private final List<RuleDadosUsuario> ruleDadosUsuarioList;
    private final List<RuleCredenciaisUsuario> ruleCredenciaisUsuarioList;
    private final UsuarioGateway usuarioGateway;

    public CriarUsuarioUseCase(
            CodificadorSenhaGateway codificadorSenhaGateway,
            UsuarioGateway usuarioGateway,
            List<RuleDadosUsuario> ruleDadosUsuarioList, List<RuleCredenciaisUsuario> ruleCredenciaisUsuarioList
    ) {
        this.codificadorSenhaGateway = codificadorSenhaGateway;
        this.ruleDadosUsuarioList = ruleDadosUsuarioList;
        this.usuarioGateway = usuarioGateway;
        this.ruleCredenciaisUsuarioList = ruleCredenciaisUsuarioList;
    }

    @Override
    public Long criar(CriarUsuarioDTO criarUsuarioDto) {
        DadosParciaisUsuarioDTO dados = criarUsuarioDto.toDadosParciaisUsuarioDTO();

        validarDadosBasicos(dados);

        String senhaCodificada = codificadorSenhaGateway.codificar(criarUsuarioDto.getSenha());

        UsuarioBase usuario = criarUsuario(
                criarUsuarioDto.getCategoriaUsuario(),
                criarUsuarioDto.getNome(),
                criarUsuarioDto.getEmail(),
                criarUsuarioDto.getLogin(),
                senhaCodificada,
                criarUsuarioDto.getEndereco()
        );

        validarCredenciaisDeLogin(usuario, dados);

        return usuarioGateway.salvar(usuario);
    }

    private void validarDadosBasicos(DadosParciaisUsuarioDTO dados) {
        ruleDadosUsuarioList.forEach(rule -> rule.validar(dados));
    }

    private void validarCredenciaisDeLogin(UsuarioBase usuario, DadosParciaisUsuarioDTO dados) {
        ruleCredenciaisUsuarioList.forEach((impl) -> impl.validar(usuario, dados));
    }
}
