package br.com.fiap.techchallengefase2.core.domain.usuario;

import br.com.fiap.techchallengefase2.core.domain.restaurante.Restaurante;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DonoTest {

    @Test
    @DisplayName("Deve retornar verdadeiro quando o Dono for proprietário do restaurante")
    void deveRetornarVerdadeiroQuandoForProprietario() {
        List<Restaurante> restaurantes = List.of(
                new Restaurante(1L, "Restaurante A", "any-endereco","any-tipo-cozinha","any-horario",1L),
                new Restaurante(2L, "Restaurante B", "any-endereco","any-tipo-cozinha","any-horario",1L)
        );
        Dono dono = new Dono(1L, "Dono Teste", "email@teste.com", "login", "senha", "Endereço", restaurantes, new ArrayList<>());

        assertTrue(dono.isProprietario(1L));
        assertTrue(dono.isProprietario(2L));
    }

    @Test
    @DisplayName("Deve retornar falso quando o Dono não for proprietário do restaurante")
    void deveRetornarFalsoQuandoNaoForProprietario() {
        List<Restaurante> restaurantes = List.of(
                new Restaurante(1L, "Restaurante A", "any-endereco","any-tipo-cozinha","any-horario",1L)
        );
        Dono dono = new Dono(1L, "Dono Teste", "email@teste.com", "login", "senha", "Endereço", restaurantes, new ArrayList<>());

        assertFalse(dono.isProprietario(99L));
    }

    @Test
    @DisplayName("Deve retornar falso ao verificar propriedade com lista de restaurantes vazia")
    void deveRetornarFalsoComListaVazia() {
        Dono dono = new Dono(1L, "Dono Teste", "email@teste.com", "login", "senha", "Endereço", new ArrayList<>(), new ArrayList<>());

        assertFalse(dono.isProprietario(1L));
    }

    @Test
    @DisplayName("Deve substituir a lista de restaurantes corretamente")
    void deveAdicionarRestaurantes() {
        Dono dono = new Dono(1L, "Dono Teste", "email@teste.com", "login", "senha", "Endereço", new ArrayList<>(), new ArrayList<>());
        List<Restaurante> novosRestaurantes = List.of(
                new Restaurante(10L, "Novo Restaurante", "any-endereco","any-tipo-cozinha","any-horario",1L)
        );

        dono.adicionarRestaurantes(novosRestaurantes);

        assertEquals(1, dono.getRestaurantes().size());
        assertTrue(dono.isProprietario(10L));
    }

    @Test
    @DisplayName("Deve atribuir a categoria DONO no momento da criação")
    void deveAtribuirCategoriaCorreta() {
        Dono dono = new Dono(1L, "Dono Teste", "email@teste.com", "login", "senha", "Endereço", new ArrayList<>(), new ArrayList<>());

        assertEquals(CategoriaUsuario.DONO.getCodigo(), dono.getCategoriaUsuario());
    }
}