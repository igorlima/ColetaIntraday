package com.eatj.igorribeirolima.coletaintraday.model.service.bo.advfn;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.CollectionUtils;

import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Ativo;
import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Intraday;
import com.eatj.igorribeirolima.coletaintraday.model.service.bo.IntradayBO;
import com.eatj.igorribeirolima.coletaintraday.util.Controle_de_Thread;
import com.eatj.igorribeirolima.coletaintraday.util.RequisicaoHTTP;

@Named
public class IntradayADVFN{
	private final Log log = LogFactory.getLog( getClass() );
	
	@Inject private IntradayBO intradayBO;
	@Inject private RequisicaoHTTP requisicaoHTTP;
	
	public IntradayADVFN() {
	}
	
	@Async
	public void run( LoginADVFN login, Ativo ativo, Controle_de_Thread controle_de_thread ) {
	  log.info( ativo.getCodigo() + " (id:"+ativo.getId()+ ") ... " );
	  
		try {
			String strHtml = getIntraday_do_ADVFN( login, ativo );
				
			if( strHtml != null && strHtml.length() > 0 )
				salvarIntradays( ativo, strHtml );
				
		} catch (Exception e) {
			log.info( "Não foi possível atualizar o intraday do ativo " + ativo.getCodigo() + ". Motivo: " + e.getMessage() + "." );
			e.printStackTrace();
		} finally {
			controle_de_thread.decremento();
		}
		
	}
	
	private String getIntraday_do_ADVFN( LoginADVFN login, Ativo ativo ){
		String strUrl = Configuracao_de_ADVFN.getString("DadosDeAcesso.urlintraday") + ativo.getCodigo();
		String strHtml = "";
		
		try {
			strHtml = requisicaoHTTP.requisicaoGET( login, strUrl );
		} catch (Exception e) {
			log.info( "Não foi possível obter o intraday do ativo " + ativo.getCodigo() + ". Motivo " + e.getMessage() + "." );
		}
		
		return strHtml;
	}

	private void salvarIntradays( Ativo ativo, String strHtml ){
		String codigoAtivo = ativo.getCodigo();
		String str_intradays = strHtml;
		try{
			if( str_intradays != null && codigoAtivo != null ){
				
				List<Intraday> intradays = Util.converterStringToDadosHistoricos( str_intradays, ativo );
				if( !CollectionUtils.isEmpty(intradays) ) intradayBO.salvar( intradays );
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException( e );
		}
	}

}
