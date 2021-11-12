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
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Usuario extends PanacheEntityBase  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 987161634009524517L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long id;

	@Column
	public String nome;

	@Column
	public String login;

	@Column
	public String senha;
	
	@Column
	public String email;
	
	@Column
	public String telefone;

	@Column
	public String perfil;

	@Column
	public String clinica;

	@Column
	public Boolean ativo;

	@Column
	public Boolean realizouPrimeiroAcesso;

	@Column
	public Boolean appInstalado;

	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }

        Usuario other = (Usuario) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
