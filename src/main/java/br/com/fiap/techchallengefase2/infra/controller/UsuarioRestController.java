package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.UsuarioController;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.DesvincularUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.VincularUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.AtualizarSenhaInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.CriarUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.DadosUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.UsuarioOutputDTO;
import br.com.fiap.techchallengefase2.infra.controller.model.request.tipousuario.DesvincularUsuarioJson;
import br.com.fiap.techchallengefase2.infra.controller.model.request.tipousuario.VincularUsuarioJson;
import br.com.fiap.techchallengefase2.infra.controller.model.request.usuario.AtualizarSenhaJson;
import br.com.fiap.techchallengefase2.infra.controller.model.request.usuario.UsuarioJson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioRestController {

    private final UsuarioController usuarioController;

    @PostMapping
    public ResponseEntity<Long> criar(@RequestBody UsuarioJson json) {
        CriarUsuarioInputDTO input = new CriarUsuarioInputDTO(
                json.nome(), json.endereco(), json.email(), json.login(), json.senha(), json.categoria()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioController.criar(input));
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioOutputDTO> atualizar(
            @RequestHeader("x-usuario-logado-id") Long usuarioLogadoId,
            @RequestBody UsuarioJson json
    ) {
        DadosUsuarioInputDTO input = new DadosUsuarioInputDTO(
                json.nome(), json.endereco(), json.email(), json.login()
        );

        return ResponseEntity.ok(usuarioController.atualizarDadosParciaisUsuario(usuarioLogadoId, input));
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioOutputDTO> buscarPorId(
            @RequestHeader("x-usuario-logado-id") Long usuarioLogadoId,
            @PathVariable Long usuarioId
    ) {
        var usuarioDTO = usuarioController.buscarUsuarioPorId(usuarioLogadoId, usuarioId);
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioOutputDTO>> buscarTodos(@RequestHeader("x-usuario-logado-id") Long usuarioLogadoId) {
        List<UsuarioOutputDTO> usuarios = usuarioController.buscarTodosUsuarios(usuarioLogadoId);
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> deletar(
            @RequestHeader("x-usuario-logado-id") Long usuarioLogadoId,
            @PathVariable Long usuarioId
    ) {
        usuarioController.deletarUsuarioPorId(usuarioLogadoId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("atribuir")
    public ResponseEntity<Void> atribuirTipoUsuario(
            @RequestHeader("x-usuario-logado-id") Long usuarioLogadoId,
            @RequestBody VincularUsuarioJson json
    ) {
        VincularUsuarioInputDTO input = VincularUsuarioJson.fromInput(json.tipoUsuarioId(), json.usuarioParaAtribuirId());
        usuarioController.atribuirTipoUsuario(usuarioLogadoId, input);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("remover")
    public ResponseEntity<Void> removerTipoUsuario(
            @RequestHeader("x-usuario-logado-id") Long usuarioLogadoId,
            @RequestBody DesvincularUsuarioJson json
    ) {
        DesvincularUsuarioInputDTO input = DesvincularUsuarioJson.fromInput(json.tipoUsuarioId(), json.usuarioParaAtribuirId());
        usuarioController.removerTipoUsuario(usuarioLogadoId, input);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("senha")
    public ResponseEntity<Void> atualizarSenhaUsuario(
            @RequestHeader("x-usuario-logado-id") Long usuarioLogadoId,
            @RequestBody AtualizarSenhaJson json
    ) {
        AtualizarSenhaInputDTO input = AtualizarSenhaJson.fromInput(json.novaSenha(), json.senhaAtual());

        usuarioController.atualizarSenhaUsuario(usuarioLogadoId, input);
        return ResponseEntity.ok().build();
    }
}