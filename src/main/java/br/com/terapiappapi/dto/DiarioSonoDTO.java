package br.com.terapiappapi.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Objects;

import br.com.terapiappapi.entity.DiarioSono;
import br.com.terapiappapi.entity.Paciente;

public class DiarioSonoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9208876902171684038L;

	public Long id;
	public Long pacienteId;
	public String dtHora;
	public String horaFoiDormir;
	public String horaQueAcordou;
	public String tempoSemDormir;
	
	public DiarioSonoDTO() {
	}
	
	public static DiarioSonoDTO transformaEmDTO(DiarioSono diario) {
		DiarioSonoDTO diarioDTO = new DiarioSonoDTO();
		diarioDTO.id = diario.id;
		diarioDTO.pacienteId = diario.paciente.id;
		
		return diarioDTO;
	}

	public static DiarioSono transformaParaObjeto(DiarioSonoDTO diarioSonoDTO){
		DiarioSono diario = new DiarioSono();
		diario.id = diarioSonoDTO.id;
		
		return diario;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiarioSonoDTO)) {
            return false;
        }

        DiarioSonoDTO other = (DiarioSonoDTO) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
