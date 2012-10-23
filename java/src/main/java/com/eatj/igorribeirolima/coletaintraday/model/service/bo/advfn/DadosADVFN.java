package com.eatj.igorribeirolima.coletaintraday.model.service.bo.advfn;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Ativo;
import com.eatj.igorribeirolima.coletaintraday.model.service.bo.AtivoBO;
import com.eatj.igorribeirolima.coletaintraday.model.service.bo.IntradayBO;
import com.eatj.igorribeirolima.coletaintraday.util.Controle_de_Thread;

@Named
public class DadosADVFN {
  
  @Inject private IntradayADVFN intradayADVFN;
  @Inject private LoginADVFN login;
	
	public synchronized void atualizar_intraday( List<Ativo> ativos, AtivoBO ativoBO, IntradayBO intradayBO ){
		final Log log = LogFactory.getLog( DadosADVFN.class );
		log.info( "*" + Util.getHoraAtual() + ": atualizando intradays em DadosADVFN..." );
		final Controle_de_Thread controle_de_thread = new Controle_de_Thread();
		
		for( Ativo ativo : ativos ){
		  
		  controle_de_thread.incremento();
		  intradayADVFN.run( login, ativo, controle_de_thread);
			while( controle_de_thread.limiteMaximo() ) Util.sleep();
			
		}
		
		while( controle_de_thread.getQteThread() > 0L ) { Util.sleep(); }
		log.info( "*" + Util.getHoraAtual() + ": atualização intradays em DadosADVFN foi FINALIZADA!!!" );
	}
	
}
