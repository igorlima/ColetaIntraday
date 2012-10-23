package com.eatj.igorribeirolima.coletaintraday.model.domain.type;

public enum Regex {

	NUMERO_REAL ( "\\-?[0-9]+(\\.[0-9]+)?" ),
	DATE_HORA ( "[0-9]{2}[/][0-9]{2}[/][0-9]{4}[ ][0-9]{2}[:][0-9]{2}" ),
	ONLY_DATE ( "[0-9]{2}[/][0-9]{2}[/][0-9]{4}" ),
	DATE ( "("+Regex.DATE_HORA.get()+")|("+Regex.ONLY_DATE.get()+")" ),
	DADO_HISTORICO ( "("+Regex.DATE.get()+")([,]"+Regex.NUMERO_REAL.get()+"){5}" ); //Data,Abertura,MAX,MIN,Fechamento,Volume
	
	private String regex;

	private Regex(String regex) {
		this.regex = regex;
	}

	public String get() {
		return regex;
	}
	
}
