package com.eatj.igorribeirolima.coletaintraday.model.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class DiaryPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Date data;
	private Ativo ativo;

	public DiaryPK(){
	}
	
	public DiaryPK( Ativo ativo, Date data){
		this.ativo = ativo;
		this.data = data;
	}
	
	public DiaryPK( Integer idAtivo, Date data){
		this.ativo = new Ativo( idAtivo );
		this.data = data;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Ativo getAtivo() {
		return ativo;
	}

	public void setAtivo(Ativo ativo) {
		this.ativo = ativo;
	}
	
	@Override
	public boolean equals(Object o) {
		if( ativo == null || data == null ) return false;
		
        return ((o instanceof DiaryPK) && 
                ativo.equals( ((DiaryPK)o).getAtivo() ) &&
                data.getTime() == ((DiaryPK) o).getData().getTime() );
    }

	@Override
    public int hashCode() {
		if( ativo == null || data == null )
			return DiaryPK.class.getName().hashCode();
		else
			return DiaryPK.class.getName().hashCode() * ( ativo.hashCode() + data.hashCode() ); 
    }
}
