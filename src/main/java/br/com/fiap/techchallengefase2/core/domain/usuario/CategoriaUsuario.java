package br.com.fiap.techchallengefase2.core.domain.usuario;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CategoriaUsuario {
    DONO(0),
    CLIENTE(1);

    private final int codigo;

    CategoriaUsuario(int codigo) {
        this.codigo = codigo;
    }

    public static CategoriaUsuario fromCodigo(Integer codigo) {
        return Arrays.stream(values())
                .filter(c -> c.getCodigo() == codigo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Categoria de usuário " + codigo + " não suportada"));
    }
}