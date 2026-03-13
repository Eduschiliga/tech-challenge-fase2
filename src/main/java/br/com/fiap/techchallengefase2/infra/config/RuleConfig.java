package br.com.fiap.techchallengefase2.infra.config;

import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dados.RuleDadosUsuario;
import br.com.fiap.techchallengefase2.core.rule.dados.ValidaSePossuiEmail;
import br.com.fiap.techchallengefase2.core.rule.dados.ValidaSePossuiLogin;
import br.com.fiap.techchallengefase2.core.rule.dados.ValidaSePossuiNome;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.RuleCredenciaisUsuario;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.ValidaSeJaExisteEmail;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.ValidaSeJaExisteLogin;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.senha.RuleAtualizarSenhaUsuario;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.senha.ValidaSenha;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.senha.ValidaSenhaAtual;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuleConfig {

    @Bean
    public ValidaSeUsuarioDono validaSeUsuarioDono() {
        return new ValidaSeUsuarioDono();
    }

    @Bean
    public ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante() {
        return new ValidaSeUsuarioDonoRestaurante();
    }

    @Bean
    public RuleDadosUsuario validaSePossuiNome() {
        return new ValidaSePossuiNome();
    }

    @Bean
    public RuleDadosUsuario validaSePossuiLogin() {
        return new ValidaSePossuiLogin();
    }

    @Bean
    public RuleDadosUsuario validaSePossuiEmail() {
        return new ValidaSePossuiEmail();
    }

    @Bean
    public RuleCredenciaisUsuario validaSeJaExisteEmail(UsuarioGateway usuarioGateway) {
        return new ValidaSeJaExisteEmail(usuarioGateway);
    }

    @Bean
    public RuleCredenciaisUsuario validaSeJaExisteLogin(UsuarioGateway usuarioGateway) {
        return new ValidaSeJaExisteLogin(usuarioGateway);
    }

    @Bean
    public RuleAtualizarSenhaUsuario validaSenhaAtual() {
        return new ValidaSenhaAtual();
    }

    @Bean
    public RuleAtualizarSenhaUsuario validaSenha() {
        return new ValidaSenha();
    }
}