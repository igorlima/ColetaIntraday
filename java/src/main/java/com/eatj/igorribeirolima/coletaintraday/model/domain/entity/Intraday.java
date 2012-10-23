package com.eatj.igorribeirolima.coletaintraday.model.domain.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name = "intraday")
@IdClass(IntradayPK.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Intraday implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column( name="id_ativo" )
	private Integer idAtivo;

	@Id
	private Date data_hora;

	private Float abertura;
	private Float maximo;
	private Float minimo;
	private Float fechamento;
	private Long volume;

	public Intraday() {
	}

	public Integer getIdAtivo() {
    return idAtivo;
  }

  public void setIdAtivo(Integer idAtivo) {
    this.idAtivo = idAtivo;
  }

  public Date getData_hora() {
		return data_hora;
	}

	public void setData_hora(Date data_hora) {
		this.data_hora = data_hora;
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

		if (data_hora != null)
			return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(data_hora);
		else
			return "";
		
	}

	@Override
	public boolean equals(Object obj) {
		if (idAtivo == null || data_hora == null)
			return false;

		if (obj instanceof Intraday
				&& idAtivo.equals(((Intraday) obj).idAtivo)
				&& data_hora.getTime() == ((Intraday) obj).data_hora.getTime() )
			return true;

		return false;
	}

	@Override
	public int hashCode() {
		if (idAtivo == null || data_hora == null)
			return Intraday.class.getName().hashCode();
		else
			return Intraday.class.getName().hashCode()
					* (idAtivo.hashCode() + data_hora.hashCode());
	}
	
}
