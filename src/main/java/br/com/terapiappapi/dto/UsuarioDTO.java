package br.com.terapiappapi.dto;

public class UsuarioDTO {

	public String nome;
	public String token;
	public String perfil;
	public String clinica;
	public Long id;
	public Long pacienteId;
	public Boolean permitePensamentos;
	public Boolean permiteCartoes;
	public Boolean permiteAtividades;
	public Boolean permiteDiarioSono;
	
	
	public UsuarioDTO() {
		
	}
	
	public UsuarioDTO(Long id) {
		super();
		this.id = id;
	}
		
	public UsuarioDTO(String nome, String token, String perfil, String clinica, Long id, Long pacienteId, 
			Boolean permitePensamentos, Boolean permiteCartoes, 
			Boolean permiteAtividades, Boolean permiteDiarioSono) {
		super();
		this.nome = nome;
		this.token = token;
		this.perfil = perfil;
		this.clinica = clinica;
		this.id = id;
		this.pacienteId = pacienteId;
		this.permitePensamentos = permitePensamentos;
		this.permiteCartoes = permiteCartoes;
		this.permiteAtividades = permiteAtividades;
		this.permiteDiarioSono = permiteDiarioSono;
	}

//	@Override
//	public String toString() {
//		return "{nome=" + nome + ", token=" + token + ", perfil=" + perfil + "}";
//	}
	
	
}
