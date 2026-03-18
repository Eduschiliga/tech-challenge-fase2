package br.com.fiap.techchallengefase2.infra.gateway.db.entity.usuario;


import br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante.RestauranteEntityJPA;
import br.com.fiap.techchallengefase2.infra.gateway.db.entity.tipousuario.TipoUsuarioEntityJPA;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
public class UsuarioEntityJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private CategoriaUsuario categoria;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RestauranteEntityJPA> restauranteList;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "usuario_tipos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_usuario_id")
    )
    private Set<TipoUsuarioEntityJPA> tipoUsuarioList;
}
