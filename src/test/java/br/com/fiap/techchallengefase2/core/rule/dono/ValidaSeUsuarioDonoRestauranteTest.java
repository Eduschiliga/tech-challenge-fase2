package br.com.fiap.techchallengefase2.core.rule.dono;

import br.com.fiap.techchallengefase2.core.domain.usuario.Dono;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidaSeUsuarioDonoRestauranteTest {

    @InjectMocks
    private ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante;

    @Mock
    private Dono dono;

    @Test
    @DisplayName("Deve passar na validação quando o usuário for dono do restaurante")
    void devePassarQuandoForDonoDoRestaurante() {
        Long restauranteId = 1L;

        when(dono.isProprietario(restauranteId)).thenReturn(true);

        assertThatCode(() -> validaSeUsuarioDonoRestaurante.validar(dono, restauranteId))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não for dono do restaurante")
    void deveLancarExcecaoQuandoNaoForDonoDoRestaurante() {
        Long restauranteId = 1L;

        when(dono.isProprietario(restauranteId)).thenReturn(false);

        assertThatThrownBy(() -> validaSeUsuarioDonoRestaurante.validar(dono, restauranteId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}