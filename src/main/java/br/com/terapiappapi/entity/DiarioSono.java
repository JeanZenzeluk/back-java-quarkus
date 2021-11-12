package br.com.terapiappapi.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity(name = "diario_sono")
public class DiarioSono extends PanacheEntityBase implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9208876902171684038L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long id;
	
	@ManyToOne
	public Paciente paciente;

	@Column(name = "dt_hora")
	public Date dtHora;
	
	@Column(name = "hora_foi_dormir")
	public Date horaFoiDormir;
	
	@Column(name = "hora_que_acordou")
	public Date horaQueAcordou;

	@Column(name = "tempo_sem_dormir")
	public Integer tempoSemDormir;

	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DiarioSono)) {
            return false;
        }

        DiarioSono other = (DiarioSono) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
