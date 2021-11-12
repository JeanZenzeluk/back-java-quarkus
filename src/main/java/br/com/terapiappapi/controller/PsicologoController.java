package br.com.terapiappapi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import br.com.terapiappapi.dto.PacienteDTO;
import br.com.terapiappapi.entity.Atividade;
import br.com.terapiappapi.entity.CartaoEnfrentamento;
import br.com.terapiappapi.entity.Paciente;
import br.com.terapiappapi.entity.Psicologo;
import br.com.terapiappapi.entity.Usuario;

@ApplicationScoped
public class PsicologoController {
	
	/**
	 * Busca os pacientes por psicólogo ID
	 * @param filtro
	 * @return
	 */
	public List<PacienteDTO> buscaPacientesPorPsicologoID(Long psicologoID){
		try {
			
			Map<String, Object> params = new HashMap<>();
			params.put("psicologoId", psicologoID);
	    	
			// Realiza a Busca pelos filtros
			List<Paciente> pacientesList = Paciente.list("psicologo.id = :psicologoId"
					+ " order by usuario.nome", params);
	    	
			List<PacienteDTO> pacienteListDTO = new ArrayList<PacienteDTO>();
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    	pacienteListDTO = PacienteDTO.transformaListaEmDTO(pacientesList, sdf);
			
	    	return pacienteListDTO;
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    		return null;
	    	}
	}
	
	/**
	 * Busca o id e nome dos pacientes por psicólogo ID
	 * @param filtro
	 * @return
	 */
	public List<PacienteDTO> buscaPacientesIdNomePorPsicologoUsuarioID(Long usuarioID){
		try {
			
			List<Psicologo> psicologos = Psicologo.findByUsuarioId(usuarioID);
			
			if(psicologos != null && psicologos.size() > 0) {
				Long psicologoId = psicologos.get(0).id;
			

				Map<String, Object> params = new HashMap<>();
				params.put("psicologoId", psicologoId);
			
				// Realiza a Busca pelos filtros
				List<Paciente> pacientesList = Paciente.list("psicologo.id = :psicologoId"
						+ " order by usuario.nome", params);
		    	
				List<PacienteDTO> pacienteListDTO = new ArrayList<PacienteDTO>();
		    	
		    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		    	
		    	if(pacientesList != null) {
		    		for(Paciente paciente : pacientesList) {
		    			PacienteDTO pacienteDTO = new PacienteDTO();
		    			pacienteDTO.id = paciente.id;
		    			pacienteDTO.nome = paciente.usuario.nome;
		    			pacienteListDTO.add(pacienteDTO);
		    		}
		    	}
		    	return pacienteListDTO;
			}else {
				return null;
			}
	    	
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    		return null;
	    	}
	}
	
	public Boolean inativarPaciente(Long idPaciente) {
	      Paciente pacienteEntity = Paciente.findById(idPaciente);
	      if (pacienteEntity == null) {
	          return null;
	      }
	      
	      Usuario.update("ativo = false where id = ?1", pacienteEntity.usuario.id);
	      
	      Map<String, Object> paramsPaciente = new HashMap<>();
			paramsPaciente.put("idPaciente", idPaciente);
			paramsPaciente.put("data", new Date());
			
	      Paciente.update("dt_exclusao = :data where id = :idPaciente", paramsPaciente);
	      return true;
	}
	
	public Boolean reativarPaciente(Long idPaciente) {
	      Paciente pacienteEntity = Paciente.findById(idPaciente);
	      if (pacienteEntity == null) {
	          return null;
	      }
	      
	      Usuario.update("ativo = true where id = ?1", pacienteEntity.usuario.id);
	      return true;
	}
	
	public Boolean inativarCartaoEnfrentamento(Long idCartao) {
	      CartaoEnfrentamento cartaoEntity = CartaoEnfrentamento.findById(idCartao);
	      if (cartaoEntity == null) {
	          return null;
	      }
	      cartaoEntity.ativo = false;
	      
	      CartaoEnfrentamento.update("ativo = false where id = ?1", idCartao);
	      return true;
	}
	
	public Boolean inativarAtividade(Long idAtividade) {
		Atividade atividadeEntity = Atividade.findById(idAtividade);
	      if (atividadeEntity == null) {
	          return null;
	      }
	      atividadeEntity.ativo = false;
	      
	      Atividade.update("ativo = false where id = ?1", idAtividade);
	      return true;
	}
	
	public Boolean alterarDadosPaciente(PacienteDTO pacienteDTO) {
		
		Paciente p = Paciente.findById(pacienteDTO.id);
		
		Map<String, Object> paramsUser = new HashMap<>();
		paramsUser.put("usuarioId", p.usuario.id);
		paramsUser.put("nome", pacienteDTO.nome);
		paramsUser.put("email", pacienteDTO.email);
		paramsUser.put("telefone", pacienteDTO.telefone);
    	
		Map<String, Object> paramsPaciente = new HashMap<>();
		paramsPaciente.put("id", pacienteDTO.id);
		paramsPaciente.put("habilita_cartao_enfrentamento", pacienteDTO.habilita_cartao_enfrentamento);
		paramsPaciente.put("habilita_rpa", pacienteDTO.habilita_rpa);
		paramsPaciente.put("habilita_diario_sono", pacienteDTO.habilita_diario_sono);
		paramsPaciente.put("habilita_plano_atividade", pacienteDTO.habilita_plano_atividade);
		
		try{
			Paciente.update("habilitaCartaoEnfrentamento = :habilita_cartao_enfrentamento"
					+ ", habilitaRpa = :habilita_rpa"
					+ ", habilitaDiarioSono = :habilita_diario_sono"
					+ ", habilitaPlanoAtividade = :habilita_plano_atividade"
					+ " where id = :id", paramsPaciente);
			
			Usuario.update("nome = :nome"
					+ ", email = :email"
					+ ", telefone = :telefone"
					+ " where id = :usuarioId", paramsUser);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}