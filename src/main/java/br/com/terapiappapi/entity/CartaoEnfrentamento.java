package br.com.terapiappapi.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity(name = "cartao_enfrentamento")
public class CartaoEnfrentamento extends PanacheEntityBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6419893341492975324L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long id;
	
	@OneToMany(mappedBy = "cartaoEnfrentamento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	public List<CartaoItem> cartaoItemList;
	
	@OneToMany(mappedBy = "cartaoEnfrentamento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	public List<VisualizacaoCartao> visualizacaoCartaoList;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonbTransient
	public Paciente paciente;
	
	@Column
	public Boolean ativo;

	@Column
	public String titulo;
	
	public CartaoEnfrentamento() {
		
	}
	
	 @JsonCreator
	 public CartaoEnfrentamento(
	        @JsonProperty("id") final Long id,
	        @JsonProperty("cartaoItemList") final List<CartaoItem> cartaoItemList,
	        @JsonProperty("visualizacaoCartaoList") final List<VisualizacaoCartao> visualizacaoCartaoList,
	        @JsonProperty("paciente") final Paciente paciente,
	        @JsonProperty("ativo") final Boolean ativo,
	        @JsonProperty("titulo") final String titulo) {
	    this.id = id;
	    this.cartaoItemList = cartaoItemList;
	    this.visualizacaoCartaoList = visualizacaoCartaoList;
	    this.paciente = paciente;
	    this.ativo = ativo;
	    this.titulo = titulo;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartaoEnfrentamento)) {
            return false;
        }

        CartaoEnfrentamento other = (CartaoEnfrentamento) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
    
    public static List<CartaoEnfrentamento> findByPacienteId(Long pacienteId){
        return list("paciente.id", pacienteId);
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
