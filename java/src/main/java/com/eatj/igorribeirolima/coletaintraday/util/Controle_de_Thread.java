package com.eatj.igorribeirolima.coletaintraday.util;

public class Controle_de_Thread {
	public static final long LIMITE_MAXIMO_THREAD = 10L;
	
	public final long maximoThread;
	private int threads = 0;
	public static final int tempo_de_sono_da_thread = 3000;
	
	public Controle_de_Thread(){
		this(LIMITE_MAXIMO_THREAD);
	}
	
	public Controle_de_Thread( long maximo_de_thread ){
		this.maximoThread = maximo_de_thread;
	}
	
	public synchronized void incremento(){
		this.threads += 1;
	}
	
	public synchronized void decremento(){
		this.threads -= 1;
	}

	public synchronized long getQteThread() {
		return this.threads;
	}
	
	public synchronized boolean limiteMaximo(){
		return this.threads >= maximoThread;
	}
	
}
