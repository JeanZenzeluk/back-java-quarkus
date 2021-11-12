package br.com.terapiappapi.dto.relatorios;

import java.util.ArrayList;
import java.util.List;

public class CartaoRelatorioDTO {

	public List<CartaoChartDTO> itemChart;

	public CartaoRelatorioDTO() {
		this.itemChart = new ArrayList<CartaoChartDTO>();
	}
	
	
}
