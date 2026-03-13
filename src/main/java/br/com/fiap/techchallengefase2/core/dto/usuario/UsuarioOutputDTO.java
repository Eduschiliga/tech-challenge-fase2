package br.com.fiap.techchallengefase2.core.dto.usuario;

import br.com.fiap.techchallengefase2.core.domain.usuario.UsuarioBase;

public record UsuarioOutputDTO(
        Long usuarioId,
        String nome,
        String email,
        String login,
        String endereco,
        Integer categoria
) {
    public static UsuarioOutputDTO fromDomain(UsuarioBase usuario) {
        return new UsuarioOutputDTO(
                usuario.getUsuarioId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getLogin(),
                usuario.getEndereco(),
                usuario.getCategoriaUsuario()
        );
    }
}