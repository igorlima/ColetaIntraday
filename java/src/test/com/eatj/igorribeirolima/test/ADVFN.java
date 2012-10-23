package com.eatj.igorribeirolima.test;

import java.io.IOException;

import org.junit.Test;

public class ADVFN {
	
	@Test
	public void teste() throws IllegalStateException, IOException{
		
		try{
			teste2();
		}catch (Exception e) {
			System.out.println( "iupii" );
		}
//		LoginADVFN login = new LoginADVFN();
//		System.out.println(RequisicaoHTTP.requisicaoGET(login, "http://br.advfn.com/p.php?pid=data&daily=0&symbol=BOV%5E" + "BBAS3" ));
//		System.out.println(RequisicaoHTTP.requisicaoGET(login, "http://br.advfn.com/p.php?pid=data&daily=0&symbol=BOV%5E" + "WHRL4" ));
		
	}
	
	
	public void teste2(){
		throw new RuntimeException();
	}
}
