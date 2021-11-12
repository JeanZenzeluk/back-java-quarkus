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

@Entity(name = "rpa")
public class Rpa extends PanacheEntityBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 318597919399268292L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long id;
	
	@ManyToOne
	public Paciente paciente;

	@Column
	public String emocao;
	
	@Column
	public String situacao;
	
	@Column(name = "pensamentos_automaticos")
	public String pensamentosAutomaticos;
	
	@Column
	public String acao;
	
	@Column(name = "quanto_acredita")
	public Integer quantoAcredita;

	@Column(name = "dt_registro")
	public Date dtRegistro;

	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rpa)) {
            return false;
        }

        Rpa other = (Rpa) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
