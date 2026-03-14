package br.com.fiap.techchallengefase2.core.domain.usuario;

import br.com.fiap.techchallengefase2.core.exception.CategoriaNaoEncontradaException;
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
                .orElseThrow(CategoriaNaoEncontradaException::new);
    }
}