package br.com.terapiappapi.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import br.com.terapiappapi.entity.VisualizacaoCartao;
public class CartaoVisualizacaoDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -986350198704558536L;

	public Long id;
	public Long cartaoEnfrentamentoId;
	public Date visualizado;

	
	
	public CartaoVisualizacaoDTO() {
	}

	public static CartaoVisualizacaoDTO transformaEmDTO(VisualizacaoCartao cartaoVisualizacao) {
		CartaoVisualizacaoDTO visualizacaoDTO = new CartaoVisualizacaoDTO();
		visualizacaoDTO.id = cartaoVisualizacao.id;
		visualizacaoDTO.visualizado = cartaoVisualizacao.visualizado;
		
		return visualizacaoDTO;
	}
	
	public static List<CartaoVisualizacaoDTO> transformaListaEmDTO(List<VisualizacaoCartao> cartaoVisualizacaoList) {
		List<CartaoVisualizacaoDTO> visualizacaoDTOList = new ArrayList<CartaoVisualizacaoDTO>();
		if(cartaoVisualizacaoList != null) {
			for(VisualizacaoCartao cartao : cartaoVisualizacaoList) {
				visualizacaoDTOList.add(transformaEmDTO(cartao));
			}
		}
		return visualizacaoDTOList;
	}
		
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartaoVisualizacaoDTO)) {
            return false;
        }

        CartaoVisualizacaoDTO other = (CartaoVisualizacaoDTO) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
