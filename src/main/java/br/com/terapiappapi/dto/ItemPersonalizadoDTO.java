package br.com.terapiappapi.dto;

import java.io.Serializable;
import java.util.Objects;

public class ItemPersonalizadoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5289300592941775975L;
	
	public Long pacienteId;
	public Long cartaoId;
	public String descricao;
	public String titulo;
	public Long id;
	

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemPersonalizadoDTO)) {
            return false;
        }

        ItemPersonalizadoDTO other = (ItemPersonalizadoDTO) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
    
}
