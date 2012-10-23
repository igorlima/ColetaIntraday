package com.eatj.igorribeirolima.coletaintraday.model.service.bo.advfn;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.eatj.igorribeirolima.coletaintraday.util.Login;
import com.eatj.igorribeirolima.coletaintraday.util.RequisicaoHTTP;

@Named
public class LoginADVFN implements Login{
	private final Log log = LogFactory.getLog( getClass() );
	
	@Inject private RequisicaoHTTP requisicaoHTTP;
	
	private final CookieStore cookieStore = new BasicCookieStore();
	private final HttpContext localContext = new BasicHttpContext();
	private boolean logado = false;
	private final Map< String, String > dadosAcesso = new HashMap<String, String>();
	
	public LoginADVFN(){
	  localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	  
	  try {
	    dadosAcesso.put( "login_username", Configuracao_de_ADVFN.getString("DadosDeAcesso.user") );
	    dadosAcesso.put( "login_password", Configuracao_de_ADVFN.getString("DadosDeAcesso.password") );
	    dadosAcesso.put( "redirect_url", Configuracao_de_ADVFN.getString("DadosDeAcesso.pageLogin") );
	  } catch (Exception e) {
	    throw new RuntimeException(e);
	  }
	}
	
	@Override
	public synchronized boolean login(){
		if( getLogado() ) return true;
		log.info( "Efetuando login ADVFN by " + Configuracao_de_ADVFN.getString("DadosDeAcesso.user") + " ..." ); //$NON-NLS-1$
		
		HttpResponse response = null;
		try {
			response = requisicaoHTTP.requisicaoPOST( this, Configuracao_de_ADVFN.getString("DadosDeAcesso.pageSecurity"), dadosAcesso );
		} catch (Exception e) {
			throw new RuntimeException( "Não foi possível logar em ADVFN.", e );
		}
		
		StatusLine statusLine = null;
		if( response != null ) statusLine = response.getStatusLine();
		if( statusLine != null && statusLine.getStatusCode() == 302 && statusLine.getReasonPhrase().indexOf( "Found" ) != -1 ){
			log.info( "Logado em ADVFN!" );
			setLogado( true );
		}else{
			log.info( "Não foi possível efetuar o login!" );
		}
		
		return getLogado();
	}
	
	@Override
	public HttpContext getHttpContext() {
		return localContext;
	}
	
	private boolean getLogado() {
		return logado;
	}
	
	private void setLogado( boolean logado ) {
		this.logado = logado;
	}

	@Override
	public void unauthorized() {
		setLogado(false);
	}

}
