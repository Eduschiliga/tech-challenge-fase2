package br.com.fiap.techchallengefase2.core.domain.tipousuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TipoUsuario {
    private Long tipoUsuarioId;
    private Long restauranteId;
    private String nome;

    public void atualizarNome(String novoNome) {
        this.nome = novoNome;
    }
}
