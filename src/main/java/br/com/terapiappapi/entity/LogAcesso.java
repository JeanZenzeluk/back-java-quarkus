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

@Entity(name = "log_acesso")
public class LogAcesso extends PanacheEntityBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2856880138246731570L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long id;

	@ManyToOne
	public Usuario usuario;

	@Column(name = "dt_hora_acesso")
	public Date dtHoraAcesso;

	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogAcesso)) {
            return false;
        }

        LogAcesso other = (LogAcesso) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
