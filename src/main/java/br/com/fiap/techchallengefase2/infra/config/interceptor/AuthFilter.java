package br.com.fiap.techchallengefase2.infra.config.interceptor;

import br.com.fiap.techchallengefase2.core.gateway.TokenGateway;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final TokenGateway tokenGateway;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // Rotas liberadas
        if (path.startsWith("/auth/login") ||
            (path.equals("/usuarios") && request.getMethod().equalsIgnoreCase("POST")) ||
            path.startsWith("/v3/api-docs") ||
            path.startsWith("/swagger-ui") ||
            path.startsWith("/h2-console")) {
            
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            Long usuarioId = tokenGateway.extrairUsuarioId(token);

            if (usuarioId != null) {
                // Para injetar o header "x-usuario-logado-cardapioId" que é exigido nos controllers,
                // usamos um HttpServletRequestWrapper, pois os headers da requisição original são imutáveis.
                CustomHttpServletRequestWrapper requestWrapper = new CustomHttpServletRequestWrapper(request);
                requestWrapper.addHeader("x-usuario-logado-cardapioId", String.valueOf(usuarioId));
                
                filterChain.doFilter(requestWrapper, response);
                return;
            }
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    private static class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {
        private final Map<String, String> customHeaders;

        public CustomHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
            this.customHeaders = new HashMap<>();
        }

        public void addHeader(String name, String value) {
            this.customHeaders.put(name, value);
        }

        @Override
        public String getHeader(String name) {
            String headerValue = customHeaders.get(name);
            if (headerValue != null) {
                return headerValue;
            }
            return super.getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            List<String> names = Collections.list(super.getHeaderNames());
            names.addAll(customHeaders.keySet());
            return Collections.enumeration(names);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            List<String> values = Collections.list(super.getHeaders(name));
            if (customHeaders.containsKey(name)) {
                values.add(customHeaders.get(name));
            }
            return Collections.enumeration(values);
        }
    }
}
