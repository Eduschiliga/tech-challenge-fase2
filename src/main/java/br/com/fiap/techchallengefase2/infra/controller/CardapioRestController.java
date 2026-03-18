package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.CardapioController;
import br.com.fiap.techchallengefase2.core.dto.cardapio.AtualizarCardapioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.cardapio.CardapioOutputDTO;
import br.com.fiap.techchallengefase2.core.dto.cardapio.CriarCardapioInputDTO;
import br.com.fiap.techchallengefase2.infra.controller.model.request.cardapio.AtualizarCardapioJson;
import br.com.fiap.techchallengefase2.infra.controller.model.request.cardapio.CriarCardapioJson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cardapios")
@RequiredArgsConstructor
public class CardapioRestController {

    private final CardapioController cardapioController;

    @PostMapping("/restaurantes/{restauranteId}")
    public ResponseEntity<Long> criar(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @PathVariable Long restauranteId,
            @RequestBody CriarCardapioJson json) {

        CriarCardapioInputDTO input = new CriarCardapioInputDTO(restauranteId, json.nome());

        var output = cardapioController.criar(usuarioLogadoId, input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }

    @PutMapping("/{cardapioId}")
    public ResponseEntity<Long> atualizar(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @PathVariable Long cardapioId,
            @RequestBody AtualizarCardapioJson json) {

        AtualizarCardapioInputDTO input = new AtualizarCardapioInputDTO(cardapioId, json.nome());

        var output = cardapioController.atualizar(usuarioLogadoId, input);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/{cardapioId}")
    public ResponseEntity<CardapioOutputDTO> buscarPorId(
            @PathVariable Long cardapioId) {

        var output = cardapioController.buscarPorId(cardapioId);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/restaurantes/{restauranteId}")
    public ResponseEntity<List<CardapioOutputDTO>> buscarTodosPorRestaurante(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @PathVariable Long restauranteId) {

        var output = cardapioController.buscarTodosPorRestaurante(usuarioLogadoId, restauranteId);
        return ResponseEntity.ok(output);
    }

    @DeleteMapping("/{cardapioId}")
    public ResponseEntity<Void> deletar(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @PathVariable Long cardapioId) {

        cardapioController.deletarPorId(usuarioLogadoId, cardapioId);
        return ResponseEntity.noContent().build();
    }
}
