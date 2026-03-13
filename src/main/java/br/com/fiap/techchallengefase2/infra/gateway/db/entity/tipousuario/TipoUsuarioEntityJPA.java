package br.com.fiap.techchallengefase2.infra.gateway.db.entity.tipousuario;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tipo_usuario")
public class TipoUsuarioEntityJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tipoUsuarioId;

    @Column(nullable = false)
    private String nome;

    @Column(name = "restaurante_id", nullable = false)
    private Long restauranteId;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;
}
