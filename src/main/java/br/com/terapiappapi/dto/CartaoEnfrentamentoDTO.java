package br.com.terapiappapi.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.terapiappapi.entity.CartaoEnfrentamento;

public class CartaoEnfrentamentoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8276388764699866162L;

	public Long id;
	public List<CartaoItemDTO> cartaoItemList;
	public List<CartaoVisualizacaoDTO> visualizacaoCartaoList;
	public Boolean ativo;
	public String titulo;
	public Integer totalVisualizacaoesHoje;

	public CartaoEnfrentamentoDTO() {
		
	}
	

	public static CartaoEnfrentamentoDTO transformaEmDTO(CartaoEnfrentamento cartao) {
		CartaoEnfrentamentoDTO cartaoDTO = new CartaoEnfrentamentoDTO();
		cartaoDTO.id = cartao.id;
		cartaoDTO.ativo = cartao.ativo;
		cartaoDTO.titulo = cartao.titulo;
		cartaoDTO.totalVisualizacaoesHoje = 0;
		
		cartaoDTO.cartaoItemList = CartaoItemDTO.transformaListaEmDTO(cartao.cartaoItemList);
		cartaoDTO.visualizacaoCartaoList = CartaoVisualizacaoDTO.transformaListaEmDTO(cartao.visualizacaoCartaoList);
		
		return cartaoDTO;
	}

	public static List<CartaoEnfrentamentoDTO> transformaListaEmDTO(List<CartaoEnfrentamento> cartaoList) {
		
		List<CartaoEnfrentamentoDTO> cartaoDTOList = new ArrayList<CartaoEnfrentamentoDTO>();
		if(cartaoList != null) {
			for(CartaoEnfrentamento cartao : cartaoList) {
				cartaoDTOList.add(transformaEmDTO(cartao));
			}
		}
		return cartaoDTOList;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartaoEnfrentamentoDTO)) {
            return false;
        }

        CartaoEnfrentamentoDTO other = (CartaoEnfrentamentoDTO) o;

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
