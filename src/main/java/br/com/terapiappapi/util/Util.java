package br.com.terapiappapi.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.terapiappapi.entity.DiarioSono;

public final class Util {

	public static boolean dataDeHoje(Date data) {
		
		if(data == null)
			return false;
		
		Date hoje = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String dataParaComparacaoStr = sdf.format(data);
		String dataDeHojeStr = sdf.format(hoje);
		
		return dataParaComparacaoStr.equals(dataDeHojeStr);
	}
	
	public static boolean dataMaiorQue(Date data, Date data7DiasAtras) {
		
		if(data == null || data7DiasAtras == null)
			return false;
		
		int compare = data.compareTo(data7DiasAtras);
		
		return compare == 1;
	}
		
	public static String retornaPrimeiroNome(String nomeComSobrenome) {
		if(nomeComSobrenome == null || nomeComSobrenome.equals(""))
			return "";
		
		String[] nomeSplit = nomeComSobrenome.split(" ");
		if(nomeSplit.length > 1)
			return nomeSplit[0];
		else 
			return nomeComSobrenome;
	}
	
}
