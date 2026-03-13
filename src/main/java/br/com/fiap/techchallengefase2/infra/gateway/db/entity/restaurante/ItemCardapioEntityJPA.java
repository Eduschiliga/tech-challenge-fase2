package br.com.fiap.techchallengefase2.infra.gateway.db.entity.restaurante;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_cardapio")
public class ItemCardapioEntityJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemCardapioId;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false)
    private Double preco;

    @Column(name = "disponivel_apenas_restaurante", nullable = false)
    private Boolean disponivelApenasRestaurante;

    @Column(name = "caminho_foto")
    private String caminhoFoto;

    @Column(name = "restaurante_id", nullable = false)
    private Long restauranteId;
}
