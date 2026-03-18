package br.com.fiap.techchallengefase2.infra.config.beans;

import br.com.fiap.techchallengefase2.core.gateway.TipoUsuarioGateway;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDono;
import br.com.fiap.techchallengefase2.core.rule.dono.ValidaSeUsuarioDonoRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.atualizar.AtualizarTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.atualizar.AtualizarTipoUsuarioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.id.BuscarTipoUsuarioPorIdUseCase;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.idusuario.BuscarTipoUsuarioPorUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.idusuario.BuscarTipoUsuarioPorUsuarioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.todos.BuscarTipoUsuarioPorRestaurante;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.consultar.todos.BuscarTipoUsuarioPorRestautanteUseCase;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.criar.CriarTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.criar.CriarTipoUsuarioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.deletar.DeletarTipoUsuario;
import br.com.fiap.techchallengefase2.core.usecase.tipousuario.deletar.DeletarTipoUsuarioUseCase;
import br.com.fiap.techchallengefase2.core.usecase.usuario.consultar.id.BuscarUsuarioPorIdUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TipoUsuarioConfig {

    @Bean
    public BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase(
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            ValidaSeUsuarioDono validaSeUsuarioDono,
            ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante,
            TipoUsuarioGateway tipoUsuarioGateway
    ) {
        return new BuscarTipoUsuarioPorIdUseCase(buscarUsuarioPorIdUseCase, validaSeUsuarioDono, validaSeUsuarioDonoRestaurante, tipoUsuarioGateway);
    }

    @Bean
    public BuscarTipoUsuarioPorRestaurante buscarTipoUsuarioPorRestaurante(
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            ValidaSeUsuarioDono validaSeUsuarioDono,
            ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante,
            TipoUsuarioGateway tipoUsuarioGateway
    ) {
        return new BuscarTipoUsuarioPorRestautanteUseCase(buscarUsuarioPorIdUseCase, validaSeUsuarioDono, validaSeUsuarioDonoRestaurante, tipoUsuarioGateway);
    }

    @Bean
    public BuscarTipoUsuarioPorUsuario buscarTipoUsuarioPorUsuario(
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            ValidaSeUsuarioDono validaSeUsuarioDono
    ) {
        return new BuscarTipoUsuarioPorUsuarioUseCase(buscarUsuarioPorIdUseCase, validaSeUsuarioDono);
    }

    @Bean
    public CriarTipoUsuario criarTipoUsuario(
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            TipoUsuarioGateway tipoUsuarioGateway,
            ValidaSeUsuarioDono validaSeUsuarioDono,
            ValidaSeUsuarioDonoRestaurante validaSeUsuarioDonoRestaurante
    ) {
        return new CriarTipoUsuarioUseCase(buscarUsuarioPorIdUseCase, tipoUsuarioGateway, validaSeUsuarioDono, validaSeUsuarioDonoRestaurante);
    }

    @Bean
    public AtualizarTipoUsuario atualizarTipoUsuario(
            BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            ValidaSeUsuarioDono validaSeUsuarioDono,
            TipoUsuarioGateway tipoUsuarioGateway
    ) {
        return new AtualizarTipoUsuarioUseCase(buscarTipoUsuarioPorIdUseCase, buscarUsuarioPorIdUseCase, validaSeUsuarioDono, tipoUsuarioGateway);
    }

    @Bean
    public DeletarTipoUsuario deletarTipoUsuario(
            BuscarTipoUsuarioPorIdUseCase buscarTipoUsuarioPorIdUseCase,
            BuscarUsuarioPorIdUseCase buscarUsuarioPorIdUseCase,
            ValidaSeUsuarioDono validaSeUsuarioDono,
            TipoUsuarioGateway tipoUsuarioGateway
    ) {
        return new DeletarTipoUsuarioUseCase(buscarTipoUsuarioPorIdUseCase, buscarUsuarioPorIdUseCase, validaSeUsuarioDono, tipoUsuarioGateway);
    }
}
