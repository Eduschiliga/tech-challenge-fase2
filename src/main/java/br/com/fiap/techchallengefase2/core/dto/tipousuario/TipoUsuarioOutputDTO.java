package br.com.fiap.techchallengefase2.core.dto.tipousuario;

import br.com.fiap.techchallengefase2.core.domain.tipousuario.TipoUsuario;

public record TipoUsuarioOutputDTO(
        Long tipoUsuarioId,
        String nome,
        Long restauranteId
) {
    public static TipoUsuarioOutputDTO fromDomain(TipoUsuario tipoUsuario) {
        return new TipoUsuarioOutputDTO(
                tipoUsuario.getTipoUsuarioId(),
                tipoUsuario.getNome(),
                tipoUsuario.getRestauranteId()
        );
    }
}