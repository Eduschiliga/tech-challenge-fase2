package br.com.fiap.techchallengefase2.infra.controller;


import br.com.fiap.techchallengefase2.core.controller.ItemCardapioController;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.DadosItemCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.itemcardapio.ItemCardapioOutputDTO;
import br.com.fiap.techchallengefase2.infra.controller.model.request.cardapio.ItemCardapioJson;
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

    @PostMapping("/cardapios/{cardapioId}")
    public ResponseEntity<Long> criar(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @PathVariable Long cardapioId,
            @RequestBody ItemCardapioJson json) {

        DadosItemCardapioInputDTO input = new DadosItemCardapioInputDTO(
                json.nome(),
                json.descricao(),
                json.preco(),
                json.disponivelApenasRestaurante(),
                json.caminhoFoto()
        );

        var output = itemCardapioController.criar(usuarioLogadoId, cardapioId, input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PutMapping("/{itemCardapioId}")
    public ResponseEntity<Long> atualizar(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
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
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @PathVariable Long itemCardapioId) {

        var output = itemCardapioController.buscarPorId(usuarioLogadoId, itemCardapioId);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/cardapios/{cardapioId}")
    public ResponseEntity<List<ItemCardapioOutputDTO>> buscarTodosPorCardapio(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @PathVariable Long cardapioId) {

        var output = itemCardapioController.buscarTodosPorCardapio(usuarioLogadoId, cardapioId);
        return ResponseEntity.ok(output);
    }

    @DeleteMapping("/{itemCardapioId}")
    public ResponseEntity<Void> deletar(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @PathVariable Long itemCardapioId) {

        itemCardapioController.deletarPorId(usuarioLogadoId, itemCardapioId);
        return ResponseEntity.noContent().build();
    }
}
