package br.com.fiap.techchallengefase2.infra.config.interceptor;

import br.com.fiap.techchallengefase2.core.gateway.TokenGateway;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthFilterTest {

    @Mock
    private TokenGateway tokenGateway;

    @InjectMocks
    private AuthFilter authFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain filterChain;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = new MockFilterChain();
    }

    @Test
    void doFilterInternal_DevePermitirRotaDeLoginSemToken() throws ServletException, IOException {
        request.setRequestURI("/auth/login");

        authFilter.doFilterInternal(request, response, filterChain);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(tokenGateway, never()).extrairUsuarioId(anyString());
    }

    @Test
    void doFilterInternal_DevePermitirPostEmUsuariosSemToken() throws ServletException, IOException {
        request.setRequestURI("/usuarios");
        request.setMethod("POST");

        authFilter.doFilterInternal(request, response, filterChain);

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(tokenGateway, never()).extrairUsuarioId(anyString());
    }

    @Test
    void doFilterInternal_DeveBloquearGetEmUsuariosSemToken() throws ServletException, IOException {
        request.setRequestURI("/usuarios");
        request.setMethod("GET");

        authFilter.doFilterInternal(request, response, filterChain);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    @Test
    void doFilterInternal_DeveInjetarHeaderQuandoTokenValido() throws ServletException, IOException {
        request.setRequestURI("/api/protegida");
        request.addHeader("Authorization", "Bearer token-valido");
        when(tokenGateway.extrairUsuarioId("token-valido")).thenReturn(10L);

        // Usando um mock para o FilterChain para capturar a requisição alterada (Wrapper)
        var mockFilterChain = mock(jakarta.servlet.FilterChain.class);

        authFilter.doFilterInternal(request, response, mockFilterChain);

        verify(mockFilterChain).doFilter(
                argThat(req -> "10".equals(((HttpServletRequest) req).getHeader("x-usuario-logado-id"))),
                eq(response)
        );
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void doFilterInternal_DeveRetornarUnauthorizedQuandoTokenNaoComecaComBearer() throws ServletException, IOException {
        request.setRequestURI("/api/protegida");
        request.addHeader("Authorization", "Basic hash");

        authFilter.doFilterInternal(request, response, filterChain);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        verify(tokenGateway, never()).extrairUsuarioId(anyString());
    }

    @Test
    void doFilterInternal_DeveRetornarUnauthorizedQuandoTokenGatewayRetornaNull() throws ServletException, IOException {
        request.setRequestURI("/api/protegida");
        request.addHeader("Authorization", "Bearer token-invalido");
        when(tokenGateway.extrairUsuarioId("token-invalido")).thenReturn(null);

        authFilter.doFilterInternal(request, response, filterChain);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    @Test
    void doFilterInternal_DeveBloquearRequisicaoSemHeaderAuthorization() throws ServletException, IOException {
        request.setRequestURI("/api/protegida");

        authFilter.doFilterInternal(request, response, filterChain);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        assertNull(request.getHeader("Authorization"));
    }
}