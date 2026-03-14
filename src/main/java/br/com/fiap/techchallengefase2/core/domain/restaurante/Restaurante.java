package br.com.fiap.techchallengefase2.core.domain.restaurante;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurante {
    private Long restauranteId;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private String horarioFuncionamento;
    private Long usuarioId;

    public void atualizarDados(
            String nome,
            String endereco,
            String tipoCozinha,
            String horarioFuncionamento
    ) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
        this.horarioFuncionamento = horarioFuncionamento;
    }
}