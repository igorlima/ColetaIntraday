package com.eatj.igorribeirolima.coletaintraday.model.service.bo;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.client.RestTemplate;

import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Ativo;
import com.eatj.igorribeirolima.coletaintraday.util.Constantes;

public class FiltroAtivos {
	private final Log log = LogFactory.getLog( getClass() );
	
	private RestTemplate restTemplate = new RestTemplate();
	private List<Ativo> ativos = new ArrayList<Ativo>();
	private String strConteudoURL;
	
	public FiltroAtivos(){
		try {
			strConteudoURL = this.restTemplate.getForObject( Constantes.urlInfoAtivo, String.class );
		} catch( Exception e ){
			throw new RuntimeException( "Conteudo da url vazio", e);
		}
	}
	
	public void atualizarAtivos(){
		//Extrai ultimos noticias
		String strProventosAux = "";
		String conteudoAux = "";
		
		strProventosAux = this.strConteudoURL.substring(1);
		while( strProventosAux.indexOf( "class=\"Select\">" ) != -1  ){
			Ativo novoAtivo = new Ativo();
			//codigo ativo
			strProventosAux = strProventosAux.substring( strProventosAux.indexOf( "class=\"Select\">" ) + 1 );
			strProventosAux =  strProventosAux.substring( strProventosAux.indexOf( ">" ) + 1 );
			conteudoAux = strProventosAux.substring( 0, strProventosAux.indexOf( "<" ) );
			novoAtivo.setCodigo( conteudoAux );
			
			//nome ativo
			strProventosAux = strProventosAux.substring( strProventosAux.indexOf( "class=\"Select\">" ) + 1 );
			strProventosAux =  strProventosAux.substring( strProventosAux.indexOf( ">" ) + 1 );
			conteudoAux = strProventosAux.substring( 0, strProventosAux.indexOf( "<" ) );
			novoAtivo.setNome_pregao( conteudoAux  );
			
			//empresa ativo
			strProventosAux = strProventosAux.substring( strProventosAux.indexOf( "class=\"Select\">" ) + 1 );
			strProventosAux =  strProventosAux.substring( strProventosAux.indexOf( ">" ) + 1 );
			conteudoAux = strProventosAux.substring( 0, strProventosAux.indexOf( "<" ) );
			novoAtivo.setNome_companhia( conteudoAux );
			
			ativos.add( novoAtivo );
			
			log.info( "Ativo: " + novoAtivo.getCodigo() + "..." );
		}
		
	}

	public List<Ativo> getAtivos() {
		return ativos;
	}

	public void setAtivos(List<Ativo> ativos) {
		this.ativos = ativos;
	}

}
