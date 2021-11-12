package br.com.terapiappapi.util;

import java.util.Calendar;
import java.util.Date;

public final class HorarioServidorConfig {

	/**
	 * Classe responsável apenas por adiantar ou manter horas do servidor. No heroku o horario do 
	 * servidor está 3 horas adiantado, assim é necessário diminuir 3 horas em qualquer 
	 * persistência de data/hora.)
	 */
	
	private final static int horasAdiantadas = 3;
	
	public static Date diaHoraAtualServidor() {

    	Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.HOUR, -horasAdiantadas);
        return c.getTime();
	}
	
}
