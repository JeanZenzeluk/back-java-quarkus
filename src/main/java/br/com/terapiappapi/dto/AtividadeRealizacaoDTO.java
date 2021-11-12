package br.com.terapiappapi.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import br.com.terapiappapi.entity.AtividadeRealizacao;

public class AtividadeRealizacaoDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5590585254046978531L;


	public Long id;
	public AtividadeDTO atividade;	
	public Date dtHoraRealizado;
	
	public AtividadeRealizacaoDTO() {
		
	}
	
	public static AtividadeRealizacaoDTO transformaEmDTO(AtividadeRealizacao atividadeRealizacao) {
		AtividadeRealizacaoDTO realizacaoDTO = new AtividadeRealizacaoDTO();
		realizacaoDTO.id = atividadeRealizacao.id;
		realizacaoDTO.dtHoraRealizado = atividadeRealizacao.dtHoraRealizado;
		
		return realizacaoDTO;
	}

	public AtividadeRealizacao transformaParaObjeto(AtividadeRealizacaoDTO atividadeRealizacaoDTO){
		AtividadeRealizacao realizacao = new AtividadeRealizacao();
		realizacao.id = atividadeRealizacaoDTO.id;
		realizacao.dtHoraRealizado = atividadeRealizacaoDTO.dtHoraRealizado;
		
		return realizacao;
	}
	
	public static List<AtividadeRealizacaoDTO> transformaListaEmDTO(List<AtividadeRealizacao> atividadeRealizacaoList) {
		
		List<AtividadeRealizacaoDTO> realizacaoDTOList = new ArrayList<AtividadeRealizacaoDTO>();
		if(atividadeRealizacaoList != null) {
			for(AtividadeRealizacao realizacao : atividadeRealizacaoList) {
				realizacaoDTOList.add(transformaEmDTO(realizacao));
			}
		}
		return realizacaoDTOList;
	}
	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AtividadeRealizacaoDTO)) {
            return false;
        }

        AtividadeRealizacaoDTO other = (AtividadeRealizacaoDTO) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
