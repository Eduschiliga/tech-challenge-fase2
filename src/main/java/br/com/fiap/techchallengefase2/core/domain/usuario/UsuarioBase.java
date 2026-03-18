package br.com.fiap.techchallengefase2.core.domain.usuario;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;
import br.com.fiap.techchallengefase2.core.exception.usuario.CategoriaInvalidaException;
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
    private Integer categoria;
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



    public UsuarioBase atualizar(
            String nome,
            String email,
            String login,
            String endereco
    ) {
        return switch (this) {
            case Dono dono -> new Dono(
                    dono.getUsuarioId(),
                    nome,
                    email,
                    login,
                    dono.getSenha(),
                    endereco,
                    dono.getRestaurantes(),
                    dono.getTipoUsuarioList()
            );

            case Cliente cliente -> new Cliente(
                    cliente.getUsuarioId(),
                    nome,
                    email,
                    login,
                    cliente.getSenha(),
                    endereco,
                    cliente.getTipoUsuarioList()
            );

            default -> throw new CategoriaInvalidaException();
        };
    }
}