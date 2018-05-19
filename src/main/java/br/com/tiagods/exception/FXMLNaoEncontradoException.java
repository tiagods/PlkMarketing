package br.com.tiagods.exception;

public class FXMLNaoEncontradoException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public FXMLNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	public FXMLNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem,causa);
	}
}
