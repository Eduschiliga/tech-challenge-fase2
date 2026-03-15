package br.com.fiap.techchallengefase2.infra.gateway;

import br.com.fiap.techchallengefase2.core.gateway.CodificadorSenhaGateway;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class CodificadorSenhaGatewayBase64 implements CodificadorSenhaGateway {

    @Override
    public String codificar(String senha) {
        return Base64.getEncoder().encodeToString(senha.getBytes());
    }

    @Override
    public String decodificar(String senhaCodificada) {
        return new String(Base64.getDecoder().decode(senhaCodificada));
    }
}