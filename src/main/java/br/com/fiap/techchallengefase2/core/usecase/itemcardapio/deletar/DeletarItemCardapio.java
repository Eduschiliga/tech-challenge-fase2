package br.com.fiap.techchallengefase2.core.usecase.itemcardapio.deletar;

public interface DeletarItemCardapio {
    void deletarPorId(Long usuarioLogadoId, Long itemCardapioId);
}