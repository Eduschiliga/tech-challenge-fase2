package br.com.fiap.techchallengefase2.core.exception;

public class DeletarUsuarioException extends SystemBaseException {
    private static final long serialVersionUID = -6678327325055715089L;

    private static final String CODE = "usuario.deletarUsuario";
    private static final String MESSAGE = "Não é possível deletar o registro de outros usuários";
    private static final Integer HTTP_STATUS = 422;

    public DeletarUsuarioException() {
        super(CODE, MESSAGE, HTTP_STATUS);
    }
}