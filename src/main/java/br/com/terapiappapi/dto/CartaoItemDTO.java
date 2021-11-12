package br.com.terapiappapi.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.com.terapiappapi.entity.CartaoItem;

public class CartaoItemDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4273827278877892414L;

	public Long id;
	public Long cartaoEnfrentamentoId;
	public String descricao;
	private String ativo;
	
	public CartaoItemDTO() {
		
	}
	
	public static CartaoItemDTO transformaEmDTO(CartaoItem cartaoItem) {
		
		CartaoItemDTO cartaoItemDTO = new CartaoItemDTO();
		cartaoItemDTO.id = cartaoItem.id;
		cartaoItemDTO.descricao = cartaoItem.descricao;
		
		return cartaoItemDTO;
		
	}
	public static List<CartaoItemDTO> transformaListaEmDTO(List<CartaoItem> cartaoList) {
		List<CartaoItemDTO> itemDTOList = new ArrayList<CartaoItemDTO>();
		if(cartaoList != null) {
			for(CartaoItem cartao : cartaoList) {
				itemDTOList.add(transformaEmDTO(cartao));
			}
		}
		return itemDTOList;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartaoItemDTO)) {
            return false;
        }

        CartaoItemDTO other = (CartaoItemDTO) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
