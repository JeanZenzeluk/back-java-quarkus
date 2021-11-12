package br.com.terapiappapi.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.terapiappapi.entity.Paciente;
import br.com.terapiappapi.entity.Usuario;

public class PacienteDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4273827278877892414L;

	public Long id;
	public String nome;
	public String email;
	public String dt_exclusao;
	public String telefone;
	public Boolean ativo;
	public Boolean habilita_rpa;
	public Boolean habilita_cartao_enfrentamento;
	public Boolean habilita_plano_atividade;
	public Boolean habilita_diario_sono;
	public Long psicologoId;
	public Boolean realizouPrimeiroAcesso;
	public Boolean habilitaNotificacoes;
	public Boolean appInstalado;
	public String tokenPush;
	public String senha;
	
	public PacienteDTO() {
	}

	public static PacienteDTO transformaEmDTO(Paciente paciente, SimpleDateFormat sdf) {
		PacienteDTO pacientDTO = new PacienteDTO();
		pacientDTO.id = paciente.id;
		pacientDTO.nome = paciente.usuario.nome;
		pacientDTO.email = paciente.usuario.email;
		if(paciente.dtExclusao != null && !paciente.dtExclusao.equals("") && sdf != null)
			pacientDTO.dt_exclusao = sdf.format(paciente.dtExclusao);
		pacientDTO.telefone = paciente.usuario.telefone;
		pacientDTO.ativo = paciente.usuario.ativo;
		pacientDTO.habilita_rpa = paciente.habilitaRpa;
		pacientDTO.habilita_cartao_enfrentamento = paciente.habilitaCartaoEnfrentamento;
		pacientDTO.habilita_plano_atividade = paciente.habilitaPlanoAtividade;
		pacientDTO.habilita_diario_sono = paciente.habilitaDiarioSono;
		
		return pacientDTO;
	}

	public static Paciente transformaParaObjeto(PacienteDTO pacienteDTO){
		Paciente p = new Paciente();
		p.id = pacienteDTO.id;
		p.usuario = new Usuario();
		p.usuario.nome = pacienteDTO.nome;
		p.usuario.email = pacienteDTO.email;
		p.usuario.telefone = pacienteDTO.telefone;
		p.usuario.ativo = pacienteDTO.ativo;
		p.habilitaRpa = pacienteDTO.habilita_rpa;
		p.habilitaCartaoEnfrentamento = pacienteDTO.habilita_cartao_enfrentamento;
		p.habilitaPlanoAtividade = pacienteDTO.habilita_plano_atividade;
		p.habilitaDiarioSono = pacienteDTO.habilita_diario_sono;
		
		return p;
	}
	
	public static List<PacienteDTO> transformaListaEmDTO(List<Paciente> pacienteList, SimpleDateFormat sdf) {
		List<PacienteDTO> pacientDTOList = new ArrayList<PacienteDTO>();
		if(pacienteList != null) {
			for(Paciente cartao : pacienteList) {
				pacientDTOList.add(transformaEmDTO(cartao, sdf));
			}
		}
		return pacientDTOList;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PacienteDTO)) {
            return false;
        }

        PacienteDTO other = (PacienteDTO) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
