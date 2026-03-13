package br.com.fiap.techchallengefase2.infra.controller;


import br.com.fiap.techchallengefase2.core.controller.ItemCardapioController;
import br.com.fiap.techchallengefase2.core.domain.restaurante.ItemCardapio;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.DadosItemCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.ItemCardapioOutputDTO;
import br.com.fiap.techchallengefase2.infra.controller.model.request.ItemCardapioJson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-cardapio")
@RequiredArgsConstructor
public class ItemCardapioRestController {

    private final ItemCardapioController itemCardapioController;

    @PostMapping("/restaurantes/{restauranteId}")
    public ResponseEntity<Long> criar(
            @RequestHeader("x-usuario-logado-id") Long usuarioLogadoId,
            @PathVariable Long restauranteId,
            @RequestBody ItemCardapioJson json) {

        DadosItemCardapioInputDTO input = new DadosItemCardapioInputDTO(
                json.nome(),
                json.descricao(),
                json.preco(),
                json.disponivelApenasRestaurante(),
                json.caminhoFoto()
        );

        var output = itemCardapioController.criar(usuarioLogadoId, restauranteId, input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PutMapping("/{itemCardapioId}")
    public ResponseEntity<Long> atualizar(
            @RequestHeader("x-usuario-logado-id") Long usuarioLogadoId,
            @PathVariable Long itemCardapioId,
            @RequestBody ItemCardapioJson json) {

        DadosItemCardapioInputDTO input = new DadosItemCardapioInputDTO(
                json.nome(),
                json.descricao(),
                json.preco(),
                json.disponivelApenasRestaurante(),
                json.caminhoFoto()
        );

        var output = itemCardapioController.atualizar(usuarioLogadoId, itemCardapioId, input);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/{itemCardapioId}")
    public ResponseEntity<ItemCardapioOutputDTO> buscarPorId(
            @RequestHeader("x-usuario-logado-id") Long usuarioLogadoId,
            @PathVariable Long itemCardapioId) {

        ItemCardapio output = itemCardapioController.buscarPorId(usuarioLogadoId, itemCardapioId);
        return ResponseEntity.ok(ItemCardapioOutputDTO.fromDomain(output));
    }

    @GetMapping("/restaurantes/{restauranteId}")
    public ResponseEntity<List<ItemCardapioOutputDTO>> buscarTodosPorRestaurante(
            @RequestHeader("x-usuario-logado-id") Long usuarioLogadoId,
            @PathVariable Long restauranteId) {

        List<ItemCardapio> output = itemCardapioController.buscarTodosPorRestaurante(usuarioLogadoId, restauranteId);

        List<ItemCardapioOutputDTO> itemCardapioDtoList = output.stream().map(ItemCardapioOutputDTO::fromDomain).toList();

        return ResponseEntity.ok(itemCardapioDtoList);
    }

    @DeleteMapping("/{itemCardapioId}")
    public ResponseEntity<Void> deletar(
            @RequestHeader("x-usuario-logado-id") Long usuarioLogadoId,
            @PathVariable Long itemCardapioId) {

        itemCardapioController.deletarPorId(usuarioLogadoId, itemCardapioId);
        return ResponseEntity.noContent().build();
    }
}
