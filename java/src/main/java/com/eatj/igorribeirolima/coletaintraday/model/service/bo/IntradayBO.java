package com.eatj.igorribeirolima.coletaintraday.model.service.bo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAO;
import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAOImplementation;

import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Intraday;
import com.eatj.igorribeirolima.coletaintraday.model.persistence.dao.IntradayDAO;
import com.eatj.igorribeirolima.coletaintraday.model.service.bo.advfn.Util;

@Named
public class IntradayBO {
	
	//private final Log log = LogFactory.getLog( getClass() );

	@Inject
	@DAO( implementation = DAOImplementation.HIBERNATE )
	private IntradayDAO dao;
	
	@Inject
	private AtivoBO ativoBO;

	@Transactional
	public void salvar( Intraday intraday ){
		dao.createOrUpdate(intraday);
	}
	
	@Transactional
	public void salvar( List<Intraday> intradays ){
	  dao.salva(intradays);
	}
	
	@Transactional( readOnly=true )
	public List<Intraday> busca( String codigoAtivo, String dtInicio, String dtFim ){
		return dao.busca(ativoBO.buscaPeloCodigo(codigoAtivo), Util.converterStringToDate(dtInicio.replace( '-', '/')), Util.converterStringToDate(dtFim.replace( '-', '/')) );
	}
}
