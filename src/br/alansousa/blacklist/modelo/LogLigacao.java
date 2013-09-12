package br.alansousa.blacklist.modelo;

import java.io.Serializable;

public class LogLigacao implements Serializable{
	
	private static final long serialVersionUID = 8802550573663743825L;

	private Long id;
	
	private String numero;
	
	private String dataHora;

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
}
