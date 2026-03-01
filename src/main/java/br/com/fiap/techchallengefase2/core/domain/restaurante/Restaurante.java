package br.com.fiap.techchallengefase2.core.domain.restaurante;

import lombok.Getter;

@Getter
public class Restaurante {
    private Long restauranteId;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private String horarioFuncionamento;
    private Long usuarioId;

    public Restaurante(
            Long restauranteId,
            String nome,
            String endereco,
            String tipoCozinha,
            String horarioFuncionamento,
            Long usuarioId
    ) {
        this.restauranteId = restauranteId;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
        this.usuarioId = usuarioId;
    }
}
