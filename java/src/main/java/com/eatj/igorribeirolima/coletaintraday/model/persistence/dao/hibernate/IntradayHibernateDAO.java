package com.eatj.igorribeirolima.coletaintraday.model.persistence.dao.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.util.CollectionUtils;

import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAO;
import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAOImplementation;
import br.ufla.lemaf.commons.model.persistence.dao.hibernate.HibernateDAO;

import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Ativo;
import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Intraday;
import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.IntradayPK;
import com.eatj.igorribeirolima.coletaintraday.model.persistence.dao.IntradayDAO;
import com.eatj.igorribeirolima.coletaintraday.model.service.bo.advfn.Util;

@Named
@DAO( implementation = DAOImplementation.HIBERNATE )
public class IntradayHibernateDAO extends HibernateDAO<Intraday, Long> implements IntradayDAO {
	private final Log log = LogFactory.getLog( getClass() );
	
	@Override
	public Intraday procura(IntradayPK id) {
		String jpql = "select intraday from Intraday as intraday WHERE intraday.data_hora = :data_hora AND intraday.ativo.id = :idAtivo";
		
		return this.entityManager.createQuery( jpql, Intraday.class)
		.setParameter( "idAtivo", id.getIdAtivo() )
		.setParameter( "data_hora", id.getData_hora() )
		.setMaxResults(1)
		.getSingleResult();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Intraday> busca(Ativo ativo, Date dtInicio, Date dtFim) {
		
		Criteria criteria = this.getSession().createCriteria( Intraday.class )
		.add( Restrictions.eq( "ativo.id", ativo.getId() ) )
		.add( Restrictions.ge( "data_hora", dtInicio ) )
		.add( Restrictions.le( "data_hora", dtFim ) )
		.addOrder( Order.asc( "data_hora" ) )
		.setMaxResults( 4000 )
		.addOrder( Order.asc( "data_hora" ) );
		
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
  @Override
  public List<Intraday> busca(Integer idAtivo) {
    
	  Criteria criteria = this.getSession().createCriteria( Intraday.class )
	      .add( Restrictions.eq( "idAtivo", idAtivo ) )
	      .addOrder( Order.asc( "data_hora" ) );
    
    return criteria.list();
  }

	@Override
	public void salva(List<Intraday> intradays) {
		if( CollectionUtils.isEmpty(intradays) ) return;
		
		List<Intraday> intradays_que_jah_foram_salvos = new ArrayList<Intraday>();
		List<Integer> ativos = Util.getAtivos(intradays);
		for( Integer idAtivo : ativos )
		  intradays_que_jah_foram_salvos.addAll( busca(idAtivo) );
		  
		Util.remove( intradays, intradays_que_jah_foram_salvos );
		
		String strAtivos = Util.toString(ativos);
		log.info( "Salvando " + intradays.size() + " intraday's: " + strAtivos + ". " );
		
		for( Intraday intraday : intradays ){
		  try{
		    this.createOrUpdate(intraday);
		  }catch( ConstraintViolationException e ){
		    log.info( "Intraday já existente. Id do ativo: " + intraday.getIdAtivo() + ". Date: " + intraday.getStrData() + ". " );
      }
		}
		
		log.info( "Intraday's do ativo com id " + strAtivos + " salvo(s) com sucesso." );
		
	}
	
}
