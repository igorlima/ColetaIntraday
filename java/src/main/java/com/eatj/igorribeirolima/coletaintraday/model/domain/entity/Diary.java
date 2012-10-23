package com.eatj.igorribeirolima.coletaintraday.model.domain.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "diary")
@IdClass( DiaryPK.class )
public class Diary implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ativo", referencedColumnName = "id")
	private Ativo ativo;
	
	@Id
	private Date data;
	
	private Float abertura;
	private Float maximo;
	private Float minimo;
	private Float fechamento;
	private Long volume;
	
	public Diary() {
	}

	public Ativo getAtivo() {
		return ativo;
	}

	public void setAtivo(Ativo ativo) {
		this.ativo = ativo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Float getAbertura() {
		return abertura;
	}

	public void setAbertura(Float abertura) {
		this.abertura = abertura;
	}

	public Float getMaximo() {
		return maximo;
	}

	public void setMaximo(Float maximo) {
		this.maximo = maximo;
	}

	public Float getMinimo() {
		return minimo;
	}

	public void setMinimo(Float minimo) {
		this.minimo = minimo;
	}

	public Float getFechamento() {
		return fechamento;
	}

	public void setFechamento(Float fechamento) {
		this.fechamento = fechamento;
	}

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public String getStrData() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format( data );
	}

	@Override
	public boolean equals(Object obj) {
		if( ativo == null || data == null ) return false;
			
		if( obj instanceof Diary && ativo.equals( ((Diary) obj).getAtivo() ) && data.getTime() == ((Diary) obj).getData().getTime() )
			return true;
			
		return false;
	}
	
	@Override
	public int hashCode() {
		if( ativo == null || data == null )
			return Diary.class.getName().hashCode();
		else
			return Diary.class.getName().hashCode() * ( ativo.hashCode() + data.hashCode() );
	}
	
}
