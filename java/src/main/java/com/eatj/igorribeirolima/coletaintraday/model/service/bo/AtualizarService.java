package com.eatj.igorribeirolima.coletaintraday.model.service.bo;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Ativo;
import com.eatj.igorribeirolima.coletaintraday.model.service.bo.advfn.DadosADVFN;
import com.eatj.igorribeirolima.coletaintraday.model.service.bo.advfn.Util;

@Named
public class AtualizarService{
	private final Log log = LogFactory.getLog( getClass() );
	
	@Inject private AtivoBO ativoBO;
	@Inject private IntradayBO intradayBO;
	@Inject private DadosADVFN dadosADVFN;
	
	public void atualizarAtivos(){
		FiltroAtivos filtrosAtivos = new FiltroAtivos();
		filtrosAtivos.atualizarAtivos();
		
		List<Ativo> ativos = filtrosAtivos.getAtivos();
		for( Ativo ativo : ativos )
			salvarAtivo(ativo);
		
	}
	
	@Transactional
	public void salvarAtivo( Ativo ativo ){
		
		//verificar se existe ativo com o mesmo codigo 
		Ativo ativoAux = ativoBO.buscaPeloCodigo( ativo.getCodigo() );
		if( ativoAux != null ) ativo.setId( ativoAux.getId() );
		
		ativoBO.salvar(ativo);
		log.info("Dados do ativo " + ativo.getCodigo() + " atualizado.");
		
	}
	
	//http://www.quartz-scheduler.org/docs/tutorials/crontrigger.html
	//aceiona às onze da noite de segunda a sexta
	@Scheduled(cron="0 0 17 * * SUN-SAT")
	@Transactional
	public synchronized void atualizarIntraday(){
		log.info( ">>" + Util.getHoraAtual() + ": executando atualização de intraday..." );
		dadosADVFN.atualizar_intraday( ativoBO.listAll(), ativoBO, intradayBO );
		log.info(  ">>" + Util.getHoraAtual() + ": atualização de intraday FINALIZADA!!!" );
	}
	
}