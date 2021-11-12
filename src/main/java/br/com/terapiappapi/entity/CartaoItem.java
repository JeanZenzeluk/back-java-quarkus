package br.com.terapiappapi.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.json.bind.annotation.JsonbAnnotation;
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

@JsonbAnnotation
@Entity(name = "cartao_item")
public class CartaoItem extends PanacheEntityBase implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7654880282219261987L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="cartaoenfrentamento_id")
	@JoinColumn(name = "cartao_enfrentamento", referencedColumnName = "id")
	public CartaoEnfrentamento cartaoEnfrentamento;

	@Column
	public String descricao;

	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartaoItem)) {
            return false;
        }

        CartaoItem other = (CartaoItem) o;

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
