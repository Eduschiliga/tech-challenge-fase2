package br.com.fiap.techchallengefase2.usuario.core.domain.tipousuario;

public abstract class TipoUsuarioBase {
    protected Long usuarioBaseId;
    protected String nomeTipo;

    public TipoUsuarioBase(Long usuarioId, String nomeTipo) {
        this.usuarioBaseId = usuarioId;
        this.nomeTipo = nomeTipo;
    }

    public Long getUsuarioBaseId() {
        return usuarioBaseId;
    }

    public String getNomeTipo() {
        return nomeTipo;
    }
}
