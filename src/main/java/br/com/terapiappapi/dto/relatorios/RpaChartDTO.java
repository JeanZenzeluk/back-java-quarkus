package br.com.terapiappapi.dto.relatorios;

public class RpaChartDTO {

	public String emocao;
	public Integer quantidade;

	public RpaChartDTO() {
		
	}
	
	public RpaChartDTO(String emocao, Integer quantidade) {
		super();
		this.emocao = emocao;
		this.quantidade = quantidade;
	}
	
	
}
