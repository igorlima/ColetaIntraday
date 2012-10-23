package com.eatj.igorribeirolima.coletaintraday.model.service.bo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAO;
import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAOImplementation;

import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Ativo;
import com.eatj.igorribeirolima.coletaintraday.model.persistence.dao.AtivoDAO;

@Named
public class AtivoBO {
	
	@Inject
	@DAO( implementation = DAOImplementation.HIBERNATE )
	private AtivoDAO dao;

	public Ativo buscaPeloCodigo( String codigo ){
		return dao.buscaPeloCodigo(codigo);
	}
	
	@Transactional
	public void salvar( Ativo ativo ){
		dao.createOrUpdate(ativo);
	}
	
	public List<Ativo> listAll(){
		return dao.retrieve();
	}
}
