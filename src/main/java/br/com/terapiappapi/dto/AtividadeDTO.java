package br.com.terapiappapi.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.terapiappapi.entity.Atividade;

public class AtividadeDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2068990714222185795L;
	
	public Long id;
	public List<AtividadeRealizacaoDTO> atividadeRealizacaoList;
	public String titulo;
	public String descricao;
	public Boolean ativo;
	public Boolean concluido;
	public Integer totalVisualizacaoesSeteDias;

	
	
	public AtividadeDTO() {
	}
	
	

	public static AtividadeDTO transformaEmDTO(Atividade atividade) {
		AtividadeDTO atividadeDTO = new AtividadeDTO();
		atividadeDTO.id = atividade.id;
		atividadeDTO.ativo = atividade.ativo;
		atividadeDTO.titulo = atividade.titulo;
		atividadeDTO.descricao = atividade.descricao;
		atividadeDTO.concluido = atividade.concluido;
		atividadeDTO.totalVisualizacaoesSeteDias = 0;
		
		
		return atividadeDTO;
	}

	public Atividade transformaParaObjeto(AtividadeDTO atividadeDTO){
		Atividade atividade = new Atividade();
		atividade.id = atividadeDTO.id;
		atividade.ativo = atividadeDTO.ativo;
		atividade.titulo = atividadeDTO.titulo;
		atividade.descricao = atividadeDTO.descricao;
		atividade.concluido = atividadeDTO.concluido;
		
		return atividade;
	}
	
	public static List<AtividadeDTO> transformaListaEmDTO(List<Atividade> atividadeList) {
		
		List<AtividadeDTO> atividadeDTOList = new ArrayList<AtividadeDTO>();
		if(atividadeList != null) {
			for(Atividade atividade : atividadeList) {
				atividadeDTOList.add(transformaEmDTO(atividade));
			}
		}
		return atividadeDTOList;
	}
	


	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AtividadeDTO)) {
            return false;
        }

        AtividadeDTO other = (AtividadeDTO) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
