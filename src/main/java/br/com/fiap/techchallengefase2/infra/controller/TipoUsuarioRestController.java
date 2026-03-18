package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.TipoUsuarioController;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.AtualizarTipoUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.CriarTipoUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.TipoUsuarioOutputDTO;
import br.com.fiap.techchallengefase2.infra.controller.model.request.tipousuario.TipoUsuarioJson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipos-usuario")
@RequiredArgsConstructor
public class TipoUsuarioRestController {

    private final TipoUsuarioController tipoUsuarioController;

    @PostMapping
    public ResponseEntity<Long> criar(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @RequestBody TipoUsuarioJson json
    ) {

        CriarTipoUsuarioInputDTO input = new CriarTipoUsuarioInputDTO(
                json.nome(),
                json.restauranteId()
        );

        var tipoUsuario = tipoUsuarioController.criar(usuarioLogadoId, input);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoUsuario);
    }

    @PutMapping("/{tipoUsuarioId}")
    public ResponseEntity<Long> atualizar(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @PathVariable Long tipoUsuarioId,
            @RequestBody TipoUsuarioJson json
    ) {

        var input = new AtualizarTipoUsuarioInputDTO(
                json.nome(),
                tipoUsuarioId
        );

        var tipoUsuario = tipoUsuarioController.atualizar(usuarioLogadoId, input);
        return ResponseEntity.ok(tipoUsuario);
    }

    @GetMapping("/{tipoUsuarioId}")
    public ResponseEntity<TipoUsuarioOutputDTO> buscarPorId(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @PathVariable Long tipoUsuarioId) {

        var output = tipoUsuarioController.buscarPorId(usuarioLogadoId, tipoUsuarioId);
        return ResponseEntity.ok(output);
    }

    @GetMapping("/restaurantes/{restauranteId}")
    public ResponseEntity<List<TipoUsuarioOutputDTO>> buscarTodosPorRestaurante(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @PathVariable Long restauranteId) {

        var output = tipoUsuarioController.buscarTodosPorRestaurante(usuarioLogadoId, restauranteId);
        return ResponseEntity.ok(output); 
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<List<TipoUsuarioOutputDTO>> buscarTodosPorUsuario(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @PathVariable Long usuarioId) {

        var output = tipoUsuarioController.buscarTodosPorUsuario(usuarioLogadoId, usuarioId);
        return ResponseEntity.ok(output);
    }

    @DeleteMapping("/{tipoUsuarioId}")
    public ResponseEntity<Void> deletar(
            @RequestHeader(value = "x-usuario-logado-id", required = false) Long usuarioLogadoId,
            @PathVariable Long tipoUsuarioId) {

        tipoUsuarioController.deletarTipoUsuario(usuarioLogadoId, tipoUsuarioId);
        return ResponseEntity.noContent().build();
    }
}