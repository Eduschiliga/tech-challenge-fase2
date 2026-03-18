package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.AuthController;
import br.com.fiap.techchallengefase2.core.dto.auth.LoginInputDTO;
import br.com.fiap.techchallengefase2.core.dto.auth.TokenOutputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Endpoints de Autenticação")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthController authController;

    @PostMapping("/login")
    @Operation(summary = "Realizar login e retornar token")
    public ResponseEntity<TokenOutputDTO> login(@RequestBody LoginInputDTO input) {
        return ResponseEntity.ok(authController.login(input));
    }
}
