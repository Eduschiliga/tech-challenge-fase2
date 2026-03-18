package br.com.fiap.techchallengefase2.infra.config.beans;

import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dados.RuleDadosUsuario;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.RuleCredenciaisUsuario;
import br.com.fiap.techchallengefase2.core.rule.dados.credenciais.senha.RuleSenhaUsuario;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class RuleConfigTest {

    private final RuleConfig ruleConfig = new RuleConfig();
    private final UsuarioGateway usuarioGateway = mock(UsuarioGateway.class);

    @Test
    void validaSeUsuarioDono_DeveInstanciarCorretamente() {
        ValidaSeUsuarioDono bean = ruleConfig.validaSeUsuarioDono();
        assertNotNull(bean);
    }

    @Test
    void validaSeUsuarioDonoRestaurante_DeveInstanciarCorretamente() {
        ValidaSeUsuarioDonoRestaurante bean = ruleConfig.validaSeUsuarioDonoRestaurante();
        assertNotNull(bean);
    }

    @Test
    void validaSePossuiNome_DeveInstanciarCorretamente() {
        RuleDadosUsuario bean = ruleConfig.validaSePossuiNome();
        assertNotNull(bean);
    }

    @Test
    void validaSePossuiLogin_DeveInstanciarCorretamente() {
        RuleDadosUsuario bean = ruleConfig.validaSePossuiLogin();
        assertNotNull(bean);
    }

    @Test
    void validaSePossuiEmail_DeveInstanciarCorretamente() {
        RuleDadosUsuario bean = ruleConfig.validaSePossuiEmail();
        assertNotNull(bean);
    }

    @Test
    void validaSeJaExisteEmail_DeveInstanciarCorretamente() {
        RuleCredenciaisUsuario bean = ruleConfig.validaSeJaExisteEmail(usuarioGateway);
        assertNotNull(bean);
    }

    @Test
    void validaSeJaExisteLogin_DeveInstanciarCorretamente() {
        RuleCredenciaisUsuario bean = ruleConfig.validaSeJaExisteLogin(usuarioGateway);
        assertNotNull(bean);
    }

    @Test
    void validaSenhaAtual_DeveInstanciarCorretamente() {
        RuleSenhaUsuario bean = ruleConfig.validaSenhaAtual();
        assertNotNull(bean);
    }

    @Test
    void validaSenha_DeveInstanciarCorretamente() {
        RuleSenhaUsuario bean = ruleConfig.validaSenha();
        assertNotNull(bean);
    }
}