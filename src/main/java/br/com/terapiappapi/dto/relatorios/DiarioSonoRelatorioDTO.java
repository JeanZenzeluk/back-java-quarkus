package br.com.terapiappapi.dto.relatorios;

import java.util.ArrayList;
import java.util.List;

public class DiarioSonoRelatorioDTO {

	public List<DiarioSonoChartDTO> itemChart;

	public DiarioSonoRelatorioDTO() {
		this.itemChart = new ArrayList<DiarioSonoChartDTO>();
	}
	
	
}
