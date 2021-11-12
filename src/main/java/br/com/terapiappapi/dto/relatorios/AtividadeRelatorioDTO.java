package br.com.terapiappapi.dto.relatorios;

import java.util.ArrayList;
import java.util.List;

public class AtividadeRelatorioDTO {

	public Integer diasRealizados;
	public Integer diasNãoRealizados;
	public List<AtividadeChartDTO> itemChart;

	public AtividadeRelatorioDTO() {
		this.itemChart = new ArrayList<AtividadeChartDTO>();
	}
	
	
}
