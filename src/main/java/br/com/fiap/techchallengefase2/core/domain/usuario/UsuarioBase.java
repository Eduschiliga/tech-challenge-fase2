package br.com.fiap.techchallengefase2.core.domain.usuario;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class UsuarioBase {
    private Long usuarioId;
    private String nome;
    private String email;
    private String login;
    private String senha;
    private String endereco;
    private Integer categoriaUsuario;
    private List<TipoUsuario> tipoUsuarioList;

    public void atribuirTipoUsuario(TipoUsuario tipoUsuario) {
        if (tipoUsuarioList == null) {
            this.tipoUsuarioList = new ArrayList<>();
        }

        this.tipoUsuarioList.add(tipoUsuario);
    }

    public void removerTipoUsuario(TipoUsuario tipoUsuario) {
        if (this.tipoUsuarioList != null) {
            this.tipoUsuarioList.removeIf(tipo ->
                    tipo.getTipoUsuarioId().equals(tipoUsuario.getTipoUsuarioId())
            );
        }
    }

    public void atribuirSenhaCodificada(String senhaCodificada) {
        this.senha = senhaCodificada;
    }
}