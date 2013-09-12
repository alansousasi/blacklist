package br.alansousa.blacklist.modelo;

import java.io.Serializable;

public class LogSMS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8559467429367975743L;

	private Long id;
	
	private String numero;
	
	private String dataHora;
	
	private String mensagem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDataHora() {
		return dataHora;
	}

	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
