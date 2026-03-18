package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.RestauranteController;
import br.com.fiap.techchallengefase2.core.dto.restaurante.DadosRestauranteInputDTO;
import br.com.fiap.techchallengefase2.core.dto.restaurante.RestauranteOutputDTO;
import br.com.fiap.techchallengefase2.infra.controller.model.request.restaurante.RestauranteJson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
@RequiredArgsConstructor
public class RestauranteRestController {

    private final RestauranteController restauranteController;

    @PostMapping
    public ResponseEntity<Long> criar(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @RequestBody RestauranteJson json) {

        DadosRestauranteInputDTO input = new DadosRestauranteInputDTO(
                json.nome(),
                json.endereco(),
                json.tipoCozinha(),
                json.horarioFuncionamento()
        );

        var restaurante = restauranteController.criar(usuarioLogadoId, input);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<Long> atualizar(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @PathVariable Long restauranteId,
            @RequestBody RestauranteJson json) {

        DadosRestauranteInputDTO input = new DadosRestauranteInputDTO(
                json.nome(),
                json.endereco(),
                json.tipoCozinha(),
                json.horarioFuncionamento()
        );

        var restaurante = restauranteController.atualizar(usuarioLogadoId, restauranteId, input);
        return ResponseEntity.ok(restaurante);
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<RestauranteOutputDTO> buscarPorId(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @PathVariable Long restauranteId) {

        var restaurante = restauranteController.buscarPorId(usuarioLogadoId, restauranteId);
        return ResponseEntity.ok(restaurante);
    }

    @GetMapping("/usuario")
    public ResponseEntity<List<RestauranteOutputDTO>> buscarTodosPorUsuario(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId) {

        var restaurantes = restauranteController.buscarTodosPorUsuarioId(usuarioLogadoId);
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping
    public ResponseEntity<List<RestauranteOutputDTO>> buscarTodos() {
        var restaurantes = restauranteController.buscarTodos();
        return ResponseEntity.ok(restaurantes);
    }

    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<Void> deletar(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @PathVariable Long restauranteId) {

        restauranteController.deletarPorId(usuarioLogadoId, restauranteId);
        return ResponseEntity.noContent().build();
    }
}
