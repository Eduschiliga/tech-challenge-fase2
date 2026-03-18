package br.com.fiap.techchallengefase2.core.usecase.auth;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;
import br.com.fiap.techchallengefase2.core.dto.auth.LoginInputDTO;
import br.com.fiap.techchallengefase2.core.dto.auth.TokenOutputDTO;
import br.com.fiap.techchallengefase2.core.exception.usuario.DadosUsuarioInvalidosException;
import br.com.fiap.techchallengefase2.core.exception.usuario.UsuarioNaoEncontradoException;
import br.com.fiap.techchallengefase2.core.gateway.CodificadorSenhaGateway;
import br.com.fiap.techchallengefase2.core.gateway.TokenGateway;
import br.com.fiap.techchallengefase2.core.gateway.UsuarioGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @Mock
    private UsuarioGateway usuarioGateway;

    @Mock
    private CodificadorSenhaGateway codificadorSenhaGateway;

    @Mock
    private TokenGateway tokenGateway;

    @InjectMocks
    private LoginUseCase loginUseCase;

    @Test
    void realizarLogin_DeveRetornarTokenQuandoDadosCorretos() {
        LoginInputDTO input = new LoginInputDTO("usuario_login", "senha123");
        UsuarioBase usuario = mock(UsuarioBase.class);

        when(usuario.getUsuarioId()).thenReturn(1L);
        when(usuario.getSenha()).thenReturn("senha_codificada");
        when(usuarioGateway.buscarPorLogin("usuario_login")).thenReturn(Optional.of(usuario));
        when(codificadorSenhaGateway.codificar("senha123")).thenReturn("senha_codificada");
        when(tokenGateway.gerarToken(1L)).thenReturn("jwt_token");

        TokenOutputDTO result = loginUseCase.realizarLogin(input);

        assertNotNull(result);
        assertEquals("jwt_token", result.getToken());
        verify(usuarioGateway).buscarPorLogin("usuario_login");
        verify(codificadorSenhaGateway).codificar("senha123");
        verify(tokenGateway).gerarToken(1L);
    }

    @Test
    void realizarLogin_DeveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        LoginInputDTO input = new LoginInputDTO("login_inexistente", "senha");

        when(usuarioGateway.buscarPorLogin("login_inexistente")).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> loginUseCase.realizarLogin(input));
    }

    @Test
    void realizarLogin_DeveLancarExcecaoQuandoSenhaIncorreta() {
        LoginInputDTO input = new LoginInputDTO("usuario_login", "senha_errada");
        UsuarioBase usuario = mock(UsuarioBase.class);

        when(usuario.getSenha()).thenReturn("senha_correta_codificada");
        when(usuarioGateway.buscarPorLogin("usuario_login")).thenReturn(Optional.of(usuario));
        when(codificadorSenhaGateway.codificar("senha_errada")).thenReturn("hash_errado");

        DadosUsuarioInvalidosException exception = assertThrows(DadosUsuarioInvalidosException.class,
                () -> loginUseCase.realizarLogin(input));

        assertEquals("Senha incorreta.", exception.getMessage());
    }
}