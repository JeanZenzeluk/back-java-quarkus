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

@Entity(name = "atividade_realizacao")
public class AtividadeRealizacao extends PanacheEntityBase implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5590585254046978531L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long id;
	
	@ManyToOne
	public Atividade atividade;	

	@Column(name = "dt_hora_realizado")
	public Date dtHoraRealizado;

	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AtividadeRealizacao)) {
            return false;
        }

        AtividadeRealizacao other = (AtividadeRealizacao) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
