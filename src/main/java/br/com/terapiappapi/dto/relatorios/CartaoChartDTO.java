package br.com.terapiappapi.dto.relatorios;

public class CartaoChartDTO {

	public String data;
	public Integer leuXVezes;
	
	public CartaoChartDTO() {
		
	}

	public CartaoChartDTO(String data, Integer leuXVezes) {
		super();
		this.data = data;
		this.leuXVezes = leuXVezes;
	}
	
}
