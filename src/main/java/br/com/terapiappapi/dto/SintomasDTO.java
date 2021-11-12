package br.com.terapiappapi.dto;

public class SintomasDTO {

	public Boolean tosse;
	public Boolean diarreia;	
	public SintomasDTO() {
		
	}
	
	public SintomasDTO(Boolean tosse, Boolean diarreia) {
		super();
		this.tosse = tosse;
		this.diarreia = diarreia;
	}

//	@Override
//	public String toString() {
//		return "{nome=" + nome + ", token=" + token + ", perfil=" + perfil + "}";
//	}
	
	
}
