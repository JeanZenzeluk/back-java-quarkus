package br.com.terapiappapi.entity;

import java.io.Serializable;
import java.util.Date;
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

@Entity(name = "paciente")
public class Paciente extends PanacheEntityBase implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5977491172188765285L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long id;
	
	@ManyToOne
	public Usuario usuario;
	
	@ManyToOne
	public Psicologo psicologo;
	
	@OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	public List<Rpa> rpaList;

	@OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	public List<DiarioSono> diarioSonosList;
	
	@OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	public List<Atividade> atividadeList;

	@OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	public List<CartaoEnfrentamento> cartaoEnfrentamentoList;
	
	@Column(name = "dt_exclusao")
	public Date dtExclusao;

	@Column(name = "permite_notificacoes")
	public Boolean permiteNotificacoes;

	@Column(name = "habilita_rpa")
	public Boolean habilitaRpa;
	
	@Column(name = "habilita_cartao_enfrentamento")
	public Boolean habilitaCartaoEnfrentamento;
	
	@Column(name = "habilita_psicoeducacao")
	public Boolean habilitaPsicoeducacao;
	
	@Column(name = "habilita_plano_atividade")
	public Boolean habilitaPlanoAtividade;
	
	@Column(name = "habilita_diario_sono")
	public Boolean habilitaDiarioSono;
	
	@Column(name = "token_push")
	public String tokenPush;

	
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paciente)) {
            return false;
        }

        Paciente other = (Paciente) o;

        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
    
    public static Paciente findByusuarioId(Long usuarioId){
        return find("usuario.id", usuarioId).firstResult();
    }
    
    public static List<Paciente> findByPsicologoId(Long psicologoId){
        return list("psicologo.id", psicologoId);
    }
}
