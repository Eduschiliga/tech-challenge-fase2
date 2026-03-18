package br.com.fiap.techchallengefase2.core.gateway;

public interface TokenGateway {
    String gerarToken(Long usuarioId);
    Long extrairUsuarioId(String token);
}
