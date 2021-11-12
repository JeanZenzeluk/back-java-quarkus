package br.com.terapiappapi.dto.relatorios;

public class DiarioSonoChartDTO {

	public String dtHora;
	public String dormiuXHoras;
	public String horaFoiDormir;
	public String horaAcordou;
	public String tempoSemDormir;
	
	public DiarioSonoChartDTO() {
		
	}
	
	public DiarioSonoChartDTO(String dtHora, String dormiuXHoras, String horaFoiDormir, String horaAcordou,
			String tempoSemDormir) {
		super();
		this.dtHora = dtHora;
		this.dormiuXHoras = dormiuXHoras;
		this.horaFoiDormir = horaFoiDormir;
		this.horaAcordou = horaAcordou;
		this.tempoSemDormir = tempoSemDormir;
	}
	
	
}
