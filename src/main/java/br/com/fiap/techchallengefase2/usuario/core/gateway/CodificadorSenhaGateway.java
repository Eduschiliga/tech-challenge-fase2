package br.com.fiap.techchallengefase2.usuario.core.gateway;

public interface CodificadorSenhaGateway {

    String codificar(String senha);

    String decodificar(String senhaCodificada);
}
