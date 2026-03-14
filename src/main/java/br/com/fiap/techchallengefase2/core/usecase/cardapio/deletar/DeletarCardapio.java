package br.com.fiap.techchallengefase2.core.usecase.cardapio.deletar;

public interface DeletarCardapio {
    void deletarPorId(Long usuarioLogadoId, Long cardapioId);
}