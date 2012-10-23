package com.eatj.igorribeirolima.coletaintraday.model.persistence.dao.hibernate;

import java.util.List;

import javax.inject.Named;

import org.hibernate.CacheMode;

import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAO;
import br.ufla.lemaf.commons.model.persistence.dao.annotation.DAOImplementation;
import br.ufla.lemaf.commons.model.persistence.dao.hibernate.HibernateDAO;

import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Ativo;
import com.eatj.igorribeirolima.coletaintraday.model.persistence.dao.AtivoDAO;

@Named
@DAO( implementation = DAOImplementation.HIBERNATE )
public class AtivoHibernateDAO extends HibernateDAO<Ativo, Long> implements AtivoDAO {

	@Override
	public Ativo buscaPeloCodigo(String codigo) {
		String jpql = "SELECT ativo from Ativo ativo where ativo.codigo like :codigo";
		
		return this.entityManager.createQuery( jpql, Ativo.class )
		.setParameter( "codigo", "%" + codigo + "%" )
		.setHint("codigo", "%" + codigo + "%")
		.setMaxResults(1)
		.getSingleResult();
		
	}
	
	@Override
	public List<Ativo> retrieve(){
		String jpql = "SELECT ativo from Ativo ativo";
		
		return this.entityManager.createQuery( jpql, Ativo.class )
		.setHint( "org.hibernate.cacheMode", CacheMode.NORMAL )
		.setHint("org.hibernate.cacheable", true)
		.getResultList();
	}
	
}
