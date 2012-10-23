package com.eatj.igorribeirolima.coletaintraday.model.persistence.dao;

import java.util.List;

import br.ufla.lemaf.commons.model.persistence.dao.DAO;

import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Ativo;

public interface AtivoDAO extends DAO<Ativo, Long> {
	
	Ativo buscaPeloCodigo( String codigo );
	
	@Override
	List<Ativo> retrieve();
	
}
