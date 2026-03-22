package br.com.fiap.techchallengefase2.infra.controller;

import br.com.fiap.techchallengefase2.core.controller.UsuarioController;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.DesvincularUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.tipousuario.VincularUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.AtualizarSenhaInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.CriarUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.DadosUsuarioInputDTO;
import br.com.fiap.techchallengefase2.core.dto.usuario.UsuarioOutputDTO;
import br.com.fiap.techchallengefase2.core.gateway.TokenGateway;
import br.com.fiap.techchallengefase2.util.container.AbstractContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioRestControllerIT extends AbstractContainer {

    @TestConfiguration
    static class ObjectMapperConfig {
        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    @Autowired
    private TokenGateway tokenGateway;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioController usuarioController;

    private final Long HEADER_USUARIO_LOGADO_ID = 99L;
    private String tokenAutenticacao;

    @BeforeEach
    void setUp() {
        tokenAutenticacao = "Bearer " + tokenGateway.gerarToken(HEADER_USUARIO_LOGADO_ID);
    }

    @Test
    void criar_DeveRetornarStatusCreatedEId() throws Exception {
        CriarUsuarioInputDTO body = new CriarUsuarioInputDTO(
                "Eduardo Schiliga",
                "Ponta Grossa - PR",
                "eduardo@email.com",
                "eduschiliga",
                "senha123",
                1
        );

        when(usuarioController.criar(any(CriarUsuarioInputDTO.class))).thenReturn(1L);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));

        verify(usuarioController).criar(any(CriarUsuarioInputDTO.class));
    }

    @Test
    void atualizar_DeveRetornarStatusOkEUsuarioAtualizado() throws Exception {
        Map<String, Object> body = Map.of(
                "nome", "Eduardo Atualizado",
                "email", "eduardo.novo@email.com",
                "login", "eduschiliganovo",
                "endereco", "Curitiba - PR"
        );

        UsuarioOutputDTO outputMock = new UsuarioOutputDTO(
                1L,
                "Eduardo Atualizado",
                "eduardo.novo@email.com",
                "eduschiliganovo",
                "Curitiba - PR",
                1
        );

        when(usuarioController.atualizarDadosParciaisUsuario(eq(HEADER_USUARIO_LOGADO_ID), any(DadosUsuarioInputDTO.class)))
                .thenReturn(outputMock);

        mockMvc.perform(put("/usuarios/{usuarioId}", 1L)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Eduardo Atualizado"));
    }

    @Test
    void buscarPorId_DeveRetornarStatusOkEUsuario() throws Exception {
        UsuarioOutputDTO outputMock = new UsuarioOutputDTO(
                1L,
                "Eduardo Schiliga",
                "eduardo@email.com",
                "eduschiliga",
                "Ponta Grossa - PR",
                1
        );

        when(usuarioController.buscarUsuarioPorId(HEADER_USUARIO_LOGADO_ID, 1L)).thenReturn(outputMock);

        mockMvc.perform(get("/usuarios/{usuarioId}", 1L)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuarioId").value(1L))
                .andExpect(jsonPath("$.nome").value("Eduardo Schiliga"));
    }

    @Test
    void buscarTodos_DeveRetornarStatusOkEListaUsuarios() throws Exception {
        UsuarioOutputDTO outputMock = new UsuarioOutputDTO(
                1L,
                "Eduardo",
                "eduardo@email.com",
                "edu",
                "PR",
                1
        );

        when(usuarioController.buscarTodosUsuarios(HEADER_USUARIO_LOGADO_ID)).thenReturn(List.of(outputMock));

        mockMvc.perform(get("/usuarios")
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].usuarioId").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Eduardo"));
    }

    @Test
    void deletar_DeveRetornarStatusNoContent() throws Exception {
        mockMvc.perform(delete("/usuarios/{usuarioId}", 1L)
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(usuarioController).deletarUsuarioPorId(HEADER_USUARIO_LOGADO_ID, 1L);
    }

    @Test
    void atribuirTipoUsuario_DeveRetornarStatusOk() throws Exception {
        Map<String, Object> body = Map.of(
                "tipoUsuarioId", 2L,
                "usuarioParaAtribuirId", 3L
        );

        mockMvc.perform(post("/usuarios/tipos-usuario")
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());

        verify(usuarioController).atribuirTipoUsuario(eq(HEADER_USUARIO_LOGADO_ID), any(VincularUsuarioInputDTO.class));
    }

    @Test
    void removerTipoUsuario_DeveRetornarStatusOk() throws Exception {
        Map<String, Object> body = Map.of(
                "tipoUsuarioId", 2L,
                "usuarioParaAtribuirId", 3L
        );

        mockMvc.perform(delete("/usuarios/tipos-usuario")
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());

        verify(usuarioController).removerTipoUsuario(eq(HEADER_USUARIO_LOGADO_ID), any(DesvincularUsuarioInputDTO.class));
    }

    @Test
    void atualizarSenhaUsuario_DeveRetornarStatusOk() throws Exception {
        Map<String, Object> body = Map.of(
                "novaSenha", "novaSenha123",
                "senhaAtual", "senhaVelha123"
        );

        mockMvc.perform(patch("/usuarios/senha")
                        .header("Authorization", tokenAutenticacao)
                        .header("x-usuario-logado-id", HEADER_USUARIO_LOGADO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());

        verify(usuarioController).atualizarSenhaUsuario(eq(HEADER_USUARIO_LOGADO_ID), any(AtualizarSenhaInputDTO.class));
    }
}