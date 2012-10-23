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
public class LoginADVFN_Mobile implements Login{
	private final Log log = LogFactory.getLog( getClass() );
	
	@Inject RequisicaoHTTP requisicaoHTTP;
	
	private final CookieStore cookieStore = new BasicCookieStore();
	private final HttpContext localContext = new BasicHttpContext();
	private boolean logado = false;
	private final Map< String, String > dadosAcesso = new HashMap<String, String>();
	
	public LoginADVFN_Mobile(){
	  localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	  
	  try {
	    dadosAcesso.put( "login_query", "" );
	    dadosAcesso.put( "login_pid", "" );
	    dadosAcesso.put( "login_un", Configuracao_de_ADVFN.getString("Mobile.user") );
	    dadosAcesso.put( "login_pw", Configuracao_de_ADVFN.getString("Mobile.password") );
	  } catch (Exception e) {
	    throw new RuntimeException(e);
	  }
	}
	
	public synchronized boolean login(){
		if( getLogado() ) return true;
		
		log.info( Util.getHoraAtual() + "-> Efetuando login mobile ADVFN by " + Configuracao_de_ADVFN.getString("Mobile.user") + " ..." );
		HttpResponse response = null;
		try {
			response = requisicaoHTTP.requisicaoPOST( this, Configuracao_de_ADVFN.getString("Mobile.pageLogin"), dadosAcesso, true );
		}catch( Exception e ){
			throw new RuntimeException( "Não foi possível logar em mobile ADVFN", e );
		}
		
		//statusLine = RequisicaoHTTP.getStatusLine();
		StatusLine statusLine = null;
		if( response != null ) statusLine = response.getStatusLine();
		if( statusLine != null && statusLine.getStatusCode() == 302 && statusLine.getReasonPhrase().indexOf( "Found" ) != -1 ){
			log.info( "Logado em mobile ADVFN!" );
			setLogado( true );
		}else{
			log.info( "Login não efetuado!" );
		}
		
		return getLogado(); 
	}
	
	public boolean isLogado( String strHtml ){
		if( strHtml.contains( Configuracao_de_ADVFN.getString("Mobile.isLogado") ) )
			return true;
		else
			return false;
	}
	
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
