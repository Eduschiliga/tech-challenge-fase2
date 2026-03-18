package br.com.fiap.techchallengefase2.infra.gateway;

import br.com.fiap.techchallengefase2.core.gateway.TokenGateway;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class TokenGatewayJWT implements TokenGateway {

    // Simple Base64 token for this usecase
    @Override
    public String gerarToken(Long usuarioId) {
        return Base64.getEncoder().encodeToString(usuarioId.toString().getBytes());
    }

    @Override
    public Long extrairUsuarioId(String token) {
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            return Long.parseLong(decoded);
        } catch (Exception e) {
            return null;
        }
    }
}
