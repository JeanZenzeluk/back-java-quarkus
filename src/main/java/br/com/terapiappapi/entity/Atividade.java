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

@Entity(name = "atividade")
public class Atividade extends PanacheEntityBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2606395549701316529L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long id;
	
	@ManyToOne
	public Paciente paciente;
	
	@OneToMany(mappedBy = "atividade", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	public List<AtividadeRealizacao> atividadeRealizacaoList;
	
	@Column
	public String titulo;
	
	@Column
	public String descricao;
	
	@Column
	public Boolean ativo;

	@Column
	public Boolean concluido;

	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Atividade)) {
            return false;
        }

        Atividade other = (Atividade) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
    
    public static List<Atividade> findByPacienteId(Long pacienteId){
        return list("paciente.id", pacienteId);
    }
}
