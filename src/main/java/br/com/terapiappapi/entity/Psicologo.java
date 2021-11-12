package br.com.terapiappapi.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity(name = "Psicologo")
public class Psicologo extends PanacheEntityBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4471679852729798225L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long id;
	
	@Column
	public Integer limiteDePacientes;
	
	@OneToMany(mappedBy = "psicologo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	public List<Paciente> pacienteList;
	
	@ManyToOne
	public Usuario usuario;
	
	public static List<Psicologo> findByUsuarioId(Long usuarioId){
        return list("usuario.id", usuarioId);
    }
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Psicologo)) {
            return false;
        }

        Psicologo other = (Psicologo) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
