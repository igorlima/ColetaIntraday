package com.eatj.igorribeirolima.coletaintraday.model.persistence.dao;

import java.util.Date;
import java.util.List;

import br.ufla.lemaf.commons.model.persistence.dao.DAO;

import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Ativo;
import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Intraday;
import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.IntradayPK;

public interface IntradayDAO extends DAO<Intraday, Long> {
	
	Intraday procura ( IntradayPK id );
	List<Intraday> busca( Ativo ativo, Date dtInicio, Date dtFim );
	List<Intraday> busca( Integer idAtivo );
	
	void salva( List<Intraday> intradays );
	
}
