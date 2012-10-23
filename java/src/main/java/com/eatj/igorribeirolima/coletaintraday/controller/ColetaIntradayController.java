package com.eatj.igorribeirolima.coletaintraday.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ufla.lemaf.commons.model.service.to.MessageReturnTO;
import br.ufla.lemaf.commons.model.service.to.ReturnTO;

import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Ativo;
import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Intraday;
import com.eatj.igorribeirolima.coletaintraday.model.service.bo.AtivoBO;
import com.eatj.igorribeirolima.coletaintraday.model.service.bo.AtualizarService;
import com.eatj.igorribeirolima.coletaintraday.model.service.bo.FiltroAtivos;
import com.eatj.igorribeirolima.coletaintraday.model.service.bo.IntradayBO;

@Controller
@RequestMapping("/**")
public class ColetaIntradayController {

	@Inject private AtivoBO ativoBO;
	@Inject private IntradayBO intradayBO;
	@Inject private AtualizarService atualizarService;
	
	
	@RequestMapping( value="infomoney/ativos", method=RequestMethod.GET)
	public List<Ativo> getAtivosFromInfomoney(){
		FiltroAtivos filtroAtivos = new FiltroAtivos();
		filtroAtivos.atualizarAtivos();
		return filtroAtivos.getAtivos();
	}
	
	@RequestMapping( value="/ativos", method=RequestMethod.GET)
	public List<Ativo> getAtivos(){
		return ativoBO.listAll();
	}
	
	@RequestMapping( value="/intraday/ativo/{codigoAtivo}/dataInicio/{dtInicio}/dataFim/{dtFim}", method=RequestMethod.GET)
	public List<Intraday> buscaIntraday( @PathVariable String codigoAtivo, @PathVariable String dtInicio, @PathVariable String dtFim ){
		return intradayBO.busca( codigoAtivo, dtInicio, dtFim );
	}
	
	@RequestMapping( value="/intraday/atualizacao", method=RequestMethod.GET)
	public ReturnTO atualizarIntradays(){
		atualizarService.atualizarIntraday();
		return new MessageReturnTO( ReturnTO.Status.SUCCESS, "atualizacao realizada com sucesso" );
	}
	
}
