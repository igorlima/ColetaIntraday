package com.eatj.igorribeirolima.coletaintraday.model.service.bo.advfn;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.util.CollectionUtils;

import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Ativo;
import com.eatj.igorribeirolima.coletaintraday.model.domain.entity.Intraday;
import com.eatj.igorribeirolima.coletaintraday.model.domain.type.Regex;
import com.eatj.igorribeirolima.coletaintraday.util.Controle_de_Thread;

public class Util {

	public static boolean isDate(String strDate) {

		if (Pattern.matches(Regex.DATE.get(), strDate))
			return true;
		else
			return false;

	}

	public static Date converterStringToDate(String strDate) {

		if (isDate(strDate)) {
			DateFormat formatter;

			if (Pattern.matches(Regex.DATE_HORA.get(), strDate))
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			else
				formatter = new SimpleDateFormat("dd/MM/yyyy");

			try {
				return (Date) formatter.parse(strDate);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}

		return null;
	}

	public static Intraday converterStringToDadoHistorico(
			String strDadoHistorico, Ativo ativo) {
		Intraday intraday = converterStringToDadoHistorico(strDadoHistorico);
		if (intraday != null)
			intraday.setIdAtivo(ativo.getId());
		return intraday;
	}

	public static Intraday converterStringToDadoHistorico(
			String strDadoHistorico) {
		Intraday cotacaoIntraday = null;

		if (Pattern.matches(Regex.DADO_HISTORICO.get(), strDadoHistorico)) {
			cotacaoIntraday = new Intraday();

			String[] dados = strDadoHistorico.split(",");

			cotacaoIntraday.setData_hora(converterStringToDate(dados[0]));
			cotacaoIntraday.setAbertura(parseFloat(dados[1]));
			cotacaoIntraday.setMaximo(parseFloat(dados[2]));
			cotacaoIntraday.setMinimo(parseFloat(dados[3]));
			cotacaoIntraday.setFechamento(parseFloat(dados[4]));
			cotacaoIntraday.setVolume(parseLong(dados[5]));

		}

		return cotacaoIntraday;
	}

	public static List<Intraday> converterStringToDadosHistoricos(
			String strDadosHistoricos, Ativo ativo) {
		List<Intraday> cotacoes = null;

		String[] dados = strDadosHistoricos.split("\n");

		for (String dadoHistorico : dados) {
			Intraday cotacao = converterStringToDadoHistorico(dadoHistorico,
					ativo);
			if (cotacao != null) {
				if (cotacoes == null)
					cotacoes = new ArrayList<Intraday>();
				cotacoes.add(cotacao);
			}
		}

		return cotacoes;
	}

	public static List<Intraday> converterStringToDadosHistoricos(
			String strDadosHistoricos) {
		List<Intraday> cotacoes = null;

		String[] dados = strDadosHistoricos.split("\n");

		for (String dadoHistorico : dados) {
			Intraday cotacao = converterStringToDadoHistorico(dadoHistorico);
			if (cotacao != null) {
				if (cotacoes == null)
					cotacoes = new ArrayList<Intraday>();
				cotacoes.add(cotacao);
			}
		}

		return cotacoes;
	}

	public static Float parseFloat(String strNumber) {
		try {
			Float f = Float.parseFloat(strNumber);
			if (f.equals(0f))
				return null;
			else
				return f;
		} catch (Exception e) {
			return null;
		}
	}

	public static Long parseLong(String strNumber) {
		try {
			Long l = Long.parseLong(strNumber);
			if (l.equals(0L))
				return null;
			else
				return l;
		} catch (Exception e) {
			return null;
		}
	}

	public static String toString(List<Integer> ativos) {
		String retorno = "";
		for (Integer ativo : ativos)
			retorno += ativo + " ";

		return retorno;
	}

	public static void remove(List<Intraday> colecao, List<Intraday> list) {
		if ( CollectionUtils.isEmpty( colecao ) || CollectionUtils.isEmpty( list ) )
			return;

		for (Intraday intraday : list)
			colecao.remove(intraday);

	}

	public static List<Integer> getAtivos(List<Intraday> colecao) {
		List<Integer> ativos = new ArrayList<Integer>();

		for (Intraday intraday : colecao)
			if (!ativos.contains(intraday.getIdAtivo()))
				ativos.add(intraday.getIdAtivo());

		return ativos;
	}

	public static String getHoraAtual() {
		return converterDateToString(new Date());
	}

	public static String converterDateToString(Date dt) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dt);
	}
	
	public static void sleep(){
		try {
			Thread.sleep( Controle_de_Thread.tempo_de_sono_da_thread );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
