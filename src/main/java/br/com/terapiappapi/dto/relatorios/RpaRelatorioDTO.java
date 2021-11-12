package br.com.terapiappapi.dto.relatorios;

import java.util.ArrayList;
import java.util.List;

public class RpaRelatorioDTO {

	public List<RpaChartDTO> itemChart;
	public List<RpaHistoricoDTO> pensamentosHistorico;
	
	public RpaRelatorioDTO() {
		this.itemChart = new ArrayList<RpaChartDTO>();
		this.pensamentosHistorico = new ArrayList<RpaHistoricoDTO>();
	}
}
