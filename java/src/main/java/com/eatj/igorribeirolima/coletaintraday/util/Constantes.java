package com.eatj.igorribeirolima.coletaintraday.util;

public class Constantes {
	public static final int UNAUTHORIZED = 401;
	
	public static final String urlUltimasNoticias = "http://web.infomoney.com.br/";
	public static final String urlInfoAtivo = "http://web.infomoney.com.br//investimentos/acoes/cotacoes/popup/popup.asp";
	public static final String urlInfoProventos = "http://web.infomoney.com.br//investimentos/acoes/proventos/";
	public static final String requestDataNoticiaAtivo = "TipoPop=NOT";
	public static final String requestDataCodigoAtivo = "codigo=";
	
	
	
	public static final String urlIFR_MACD_Histograma_Estocastico_OBV = "http://custom.infomoney.com.br/rotinas/GeraDadosGrafico.asp";
	public static final String requestDataIFR = "linhas=1&tipo_cotacao=2&atv_dado10=28&ativo=";
	public static final String requestDataMACD = "linhas=1&tipo_cotacao=2&atv_dado11=82&atv_dado12=83&ativo=";
	public static final String requestDataHistograma = "linhas=1&tipo_cotacao=2&atv_dado11=397&ativo=";
	public static final String requestDataEstocastico = "linhas=1&tipo_cotacao=2&atv_dado11=85&ativo=";
	public static final String requestDataOBV = "linhas=1&tipo_cotacao=2&atv_dado11=84&ativo=";
	
}
