package br.com.terapiappapi.dto.relatorios;

import java.util.ArrayList;
import java.util.List;

public class RpaHistoricoDTO {

	public Long id;
	public String dia;
	public List<RpaHistoricoEmocaoDTO> emocoes;
	
	public RpaHistoricoDTO() {
		
	}
	
	public RpaHistoricoDTO(Long id, String dia) {
		super();
		this.id = id;
		this.dia = dia;
		this.emocoes = new ArrayList<RpaHistoricoEmocaoDTO>();
	}
	
	
}
