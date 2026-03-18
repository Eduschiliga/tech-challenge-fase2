package br.com.fiap.techchallengefase2.core.rule.dono;

import br.com.fiap.techchallengefase2.core.domain.usuario.Cliente;
import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import br.com.fiap.techchallengefase2.core.exception.usuario.UsuarioNaoDonoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ValidaSeUsuarioDonoTest {

    @InjectMocks
    private ValidaSeUsuarioDono validaSeUsuarioDono;

    @Test
    @DisplayName("Deve passar na validação quando o usuário for do tipo Dono")
    void devePassarQuandoUsuarioForDono() {
        Dono dono = new Dono(1L, "Dono", "dono@test.com", "login", "senha", "End", new ArrayList<>());

        assertThatCode(() -> validaSeUsuarioDono.validar(dono))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário for do tipo Cliente")
    void deveLancarExcecaoQuandoUsuarioForCliente() {
        Cliente cliente = new Cliente(1L, "Cliente", "cli@test.com", "login", "senha", "End");

        assertThatThrownBy(() -> validaSeUsuarioDono.validar(cliente))
                .isInstanceOf(UsuarioNaoDonoException.class);
    }
}