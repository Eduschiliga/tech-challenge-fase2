package br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cardapio")
public class CardapioEntityJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardapioId;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private RestauranteEntityJPA restaurante;

    @OneToMany(mappedBy = "cardapio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCardapioEntityJPA> itens;
}
