package com.eatj.igorribeirolima.coletaintraday.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

@Named
public class RequisicaoHTTP {
	
	public String requisicaoGET( Login login, String strUrl ) {
		return requisicaoGET(login, strUrl, false );
	}
	
	public String requisicaoGET( Login login, String strUrl, boolean requisicaoDeCelular ) {
		
		// Execute the request
		HttpResponse response = executeRequisicaoGet( login, strUrl, requisicaoDeCelular );
		
		HttpEntity entity = response.getEntity();
		String conteudoUrl = getConteudo(entity);
		return conteudoUrl;
	}
	
	public HttpResponse requisicaoPOST( Login login, String strUrl, Map<String, String> values ) {
		return requisicaoPOST(login, strUrl, values, false );
	}
	
	public HttpResponse requisicaoPOST( Login login, String strUrl, Map<String, String> values, boolean requisicaoDeCelular ) {
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost( strUrl );
		
		if( requisicaoDeCelular ){
			httpPost.setHeader( "x-wap-profile", "\"http://wap.samsungmobile.com/uaprof/GT-S8500B.xml\"" );
			httpPost.setHeader( "user-agent", "Mozilla/5.0 (SAMSUNG; SAMSUNG-GT-S8500B/1.0; U; Bada/1.0; pt-br) AppleWebKit/533.1 (KHTML, like Gecko) Dolfin/2.0 Mobile WVGA SMM-MMS/1.2.0 OPN-B" );
		}
		
		
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		for(  String key : values.keySet() )
			nvps.add(new BasicNameValuePair( key, values.get(key) ) );
			
        try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		// Execute the request
		try {
			return httpClient.execute( httpPost, login.getHttpContext() );
		} catch (ClientProtocolException e) {
			throw new RuntimeException( e );
		} catch (IOException e) {
			throw new RuntimeException( e );
		}
		
	}
	
	private HttpResponse executeRequisicaoGet( Login login, String strUrl, boolean requisicaoDeCelular ){
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = createHttpGet(strUrl, requisicaoDeCelular);
		try {
			HttpResponse response = httpClient.execute( httpget, login.getHttpContext() );
			
			if( response.getStatusLine().getStatusCode() == Constantes.UNAUTHORIZED ){
				login.unauthorized();
				login.login();
				httpClient = new DefaultHttpClient();
				response = httpClient.execute(httpget, login.getHttpContext());
			}
			
			if( response.getStatusLine().getStatusCode() == Constantes.UNAUTHORIZED )
				throw new RuntimeException( "Usuário não logado" );
			else
				return response;
			
		} catch (ClientProtocolException e) {
			throw new RuntimeException( e );
		} catch (IOException e) {
			throw new RuntimeException( e );
		}
	}
	
	private HttpGet createHttpGet( String strUrl, boolean requisicaoDeCelular ){
		HttpGet httpget = new HttpGet( strUrl );
		if( requisicaoDeCelular ){
			httpget.setHeader( "x-wap-profile", "\"http://wap.samsungmobile.com/uaprof/GT-S8500B.xml\"" );
			httpget.setHeader( "user-agent", "Mozilla/5.0 (SAMSUNG; SAMSUNG-GT-S8500B/1.0; U; Bada/1.0; pt-br) AppleWebKit/533.1 (KHTML, like Gecko) Dolfin/2.0 Mobile WVGA SMM-MMS/1.2.0 OPN-B" );
		}
		return httpget;
	}
	
	private String getConteudo( HttpEntity entity ){
		InputStream instream = null;
		String conteudoUrl = null;
		
		// If the response does not enclose an entity, there is no need
		// to worry about connection release
		if (entity != null) {
			try {
				instream = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(instream));
				// do something useful with the response
				String line = null;
				conteudoUrl = "";
				while ((line = reader.readLine()) != null)
					conteudoUrl += line + "\n";
				
			} catch (IOException ex) {
				// In case of an IOException the connection will be released
				// back to the connection manager automatically
				throw new RuntimeException( ex );
			} catch (RuntimeException ex) {
				// In case of an unexpected exception you may want to abort
				// the HTTP request in order to shut down the underlying
				// connection and release it back to the connection manager.
				throw new RuntimeException( ex );
			} finally {
				// Closing the input stream will trigger connection release
				try {
					instream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException( e );
				}
			}
		}
		
		return conteudoUrl;
	}
}
