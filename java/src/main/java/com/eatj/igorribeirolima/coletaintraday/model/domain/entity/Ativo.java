package com.eatj.igorribeirolima.coletaintraday.model.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table( name="ativo" )
@Cache( usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE )
public class Ativo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column( unique=true )
	private String codigo;
	private String nome_pregao;
	private String nome_companhia;
	
	public Ativo() {
		// TODO Auto-generated constructor stub
	}
	
	public Ativo( Integer id ) {
		this.id = id;
	}
	
	public Ativo( String codigo ) {
		this.codigo = codigo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome_pregao() {
		return nome_pregao;
	}

	public void setNome_pregao(String nome_pregao) {
		this.nome_pregao = nome_pregao;
	}

	public String getNome_companhia() {
		return nome_companhia;
	}

	public void setNome_companhia(String nome_companhia) {
		this.nome_companhia = nome_companhia;
	}
	
	@Override
	public boolean equals(Object obj) {
		if( codigo == null ) return false;
		
		if( obj instanceof Ativo && codigo.equals( ((Ativo) obj).getCodigo() ) )
			return true;
		
		return false;
	}
	
	@Override
	public int hashCode() {
		if( codigo == null )
			return Ativo.class.getName().hashCode();
		else
			return Ativo.class.getName().hashCode() * ( codigo.hashCode() );
	}
}
