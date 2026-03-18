package br.com.fiap.techchallengefase2.infra.config.beans;

import br.com.fiap.techchallengefase2.core.gateway.TipoUsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.atualizar.AtualizarTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.idusuario.BuscarTipoUsuarioPorUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.todos.BuscarTipoUsuarioPorRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.criar.CriarTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.deletar.DeletarTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class TipoUsuarioConfigTest {

    private final TipoUsuarioConfig tipoUsuarioConfig = new TipoUsuarioConfig();
    private final BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase = mock(BuscarUsuarioPorIdUseCase.class);
    private final ValidaSeUsuarioDono validaSeUsuarioDono = mock(ValidaSeUsuarioDono.class);
    private final ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante = mock(ValidaSeUsuarioDonoRestaurante.class);
    private final TipoUsuarioGateway tipoUsuarioGateway = mock(TipoUsuarioGateway.class);
    private final BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase = mock(BuscarTipoUsuarioPorIdUseCase.class);

    @Test
    void buscarTipoUsuarioPorIdUseCase_DeveInstanciarCorretamente() {
        BuscarTipoUsuarioPorIdUseCase bean = tipoUsuarioConfig.buscarTipoUsuarioPorIdUseCase(
                buscarUsuarioPorIdUseCase,
                validaSeUsuarioDono,
                validaSeUsuarioDonoRestaurante,
                tipoUsuarioGateway
        );
        assertNotNull(bean);
    }

    @Test
    void buscarTipoUsuarioPorRestaurante_DeveInstanciarCorretamente() {
        BuscarTipoUsuarioPorRestaurante bean = tipoUsuarioConfig.buscarTipoUsuarioPorRestaurante(
                buscarUsuarioPorIdUseCase,
                validaSeUsuarioDono,
                validaSeUsuarioDonoRestaurante,
                tipoUsuarioGateway
        );
        assertNotNull(bean);
    }

    @Test
    void buscarTipoUsuarioPorUsuario_DeveInstanciarCorretamente() {
        BuscarTipoUsuarioPorUsuario bean = tipoUsuarioConfig.buscarTipoUsuarioPorUsuario(
                buscarUsuarioPorIdUseCase,
                validaSeUsuarioDono
        );
        assertNotNull(bean);
    }

    @Test
    void criarTipoUsuario_DeveInstanciarCorretamente() {
        CriarTipoUsuario bean = tipoUsuarioConfig.criarTipoUsuario(
                buscarUsuarioPorIdUseCase,
                tipoUsuarioGateway,
                validaSeUsuarioDono,
                validaSeUsuarioDonoRestaurante
        );
        assertNotNull(bean);
    }

    @Test
    void atualizarTipoUsuario_DeveInstanciarCorretamente() {
        AtualizarTipoUsuario bean = tipoUsuarioConfig.atualizarTipoUsuario(
                buscarTipoUsuarioPorIdUseCase,
                buscarUsuarioPorIdUseCase,
                validaSeUsuarioDono,
                tipoUsuarioGateway
        );
        assertNotNull(bean);
    }

    @Test
    void deletarTipoUsuario_DeveInstanciarCorretamente() {
        DeletarTipoUsuario bean = tipoUsuarioConfig.deletarTipoUsuario(
                buscarTipoUsuarioPorIdUseCase,
                buscarUsuarioPorIdUseCase,
                validaSeUsuarioDono,
                tipoUsuarioGateway
        );
        assertNotNull(bean);
    }
}