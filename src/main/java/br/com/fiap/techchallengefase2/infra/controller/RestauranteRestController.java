package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.RestauranteController;
import br.com.fiap.techchallengefase2.core.dto.restaurante.DadosRestauranteInputDTO;
import br.com.fiap.techchallengefase2.core.dto.restaurante.RestauranteOutputDTO;
import br.com.fiap.techchallengefase2.infra.controller.model.request.RestauranteJson;
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
            @RequestHeader("x-usuario-logado-id") Long usuarioLogadoId,
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
            @RequestHeader("x-usuario-logado-id") Long usuarioLogadoId,
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
            @RequestHeader("x-usuario-logado-id") Long usuarioLogadoId,
            @PathVariable Long restauranteId) {

        var restaurante = restauranteController.buscarPorId(usuarioLogadoId, restauranteId);
        return ResponseEntity.ok(RestauranteOutputDTO.fromDomain(restaurante));
    }

    @GetMapping
    public ResponseEntity<List<RestauranteOutputDTO>> buscarTodosPorUsuario(
            @RequestHeader("x-usuario-logado-id") Long usuarioLogadoId) {

        List<RestauranteOutputDTO> restaurantes = restauranteController.buscarTodosPorUsuarioId(usuarioLogadoId)
                .stream()
                .map(RestauranteOutputDTO::fromDomain)
                .toList();

        return ResponseEntity.ok(restaurantes);
    }

    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<Void> deletar(
            @RequestHeader("x-usuario-logado-id") Long usuarioLogadoId,
            @PathVariable Long restauranteId) {

        restauranteController.deletarPorId(usuarioLogadoId, restauranteId);
        return ResponseEntity.noContent().build();
    }
}