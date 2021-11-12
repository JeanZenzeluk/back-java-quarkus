package br.com.terapiappapi.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity(name = "visualizacao_cartao")
public class VisualizacaoCartao extends PanacheEntityBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2856880138246731570L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="cartaoenfrentamento_id")
	@JoinColumn(name = "cartao_enfrentamento", referencedColumnName = "id")
	public CartaoEnfrentamento cartaoEnfrentamento;

	@Column(name = "dt_visualizado")
	public Date visualizado;

	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VisualizacaoCartao)) {
            return false;
        }

        VisualizacaoCartao other = (VisualizacaoCartao) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
