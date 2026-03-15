package br.com.fiap.techchallengefase2.infra.gateway.db.entity.tipousuario;

import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario.UsuarioEntityJPA;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private RestauranteEntityJPA restaurante;

    @ManyToMany(mappedBy = "tipoUsuarioList")
    private List<UsuarioEntityJPA> usuarios;
}
