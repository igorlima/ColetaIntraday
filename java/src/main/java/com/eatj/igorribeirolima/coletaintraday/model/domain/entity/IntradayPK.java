package com.eatj.igorribeirolima.coletaintraday.model.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IntradayPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Date data_hora;
	
	@Column( name="id_ativo" )
	private Integer idAtivo;

	public IntradayPK(){
	}
	
	public IntradayPK( Integer idAtivo, Date data_hora){
		this.idAtivo = idAtivo;
		this.data_hora = data_hora;
	}

	public Date getData_hora() {
		return data_hora;
	}

	public void setData_hora(Date data_hora) {
		this.data_hora = data_hora;
	}

	public Integer getIdAtivo() {
    return idAtivo;
  }

  public void setIdAtivo(Integer idAtivo) {
    this.idAtivo = idAtivo;
  }

  @Override
  public boolean equals(Object o) {
    if (idAtivo == null || data_hora == null)
      return false;

    if ((o instanceof IntradayPK) 
        && idAtivo.equals(((IntradayPK) o).idAtivo)
        && data_hora.getTime() == ((IntradayPK) o).getData_hora().getTime())
      return true;

    return false;
  }

  @Override
  public int hashCode() {
    if (idAtivo == null || data_hora == null)
      return IntradayPK.class.getName().hashCode();
    else
      return IntradayPK.class.getName().hashCode()
          * (idAtivo.hashCode() + data_hora.hashCode());
  }
}
