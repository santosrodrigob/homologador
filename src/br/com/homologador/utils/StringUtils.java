/**
 * @author RodrigoBorges.
 * @author Emerson Rodrigo
 * @date 01/03/2020
 */

package br.com.homologador.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public final class StringUtils
{
	public final static DateFormat DATE_FORMAT_DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy");
	public final static String DATE_PATTERN_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	private final static Pattern LTRIM = Pattern.compile("^\\s+");
	private final static Pattern RTRIM = Pattern.compile("\\s+$");
	
	final static Locale ptBR = new Locale("pt", "BR");
	final static NumberFormat form = NumberFormat.getCurrencyInstance(ptBR);
	final static DecimalFormat df = (DecimalFormat) form;

	public static String lTrim(final String s) 
	{
	    return LTRIM.matcher(s).replaceAll("");
	}
	
	public static String rTrim(final String s) 
	{
	    return RTRIM.matcher(s).replaceAll("");
	}

	/**
	 * Verify if text is null or empty.
	 * 
	 * @param value the text.
	 * 
	 * @return true/false is null.
	 */
	public static boolean isNull(final String value)
	{
		if( value == null )
			return true;
		if( value.trim().length() < 1 )
			return true;
		return false;
	}
	
	/**
	 * Verify if array is null or empty
	 * 
	 * @param value the array value.
	 * 
	 * @return true/false is null.
	 */
	public static boolean isNull(String[] value){
		if( value == null )
			return true;
		if( value.length < 1 )
			return true;
		return false;
	}

	/**
	 * Verify is content is null.
	 * 
	 * @param value the value to validated.
	 * 
	 * @param contentIsNull the content interpreted with null.
	 * 
	 * @return true/false is null.
	 */
	public static boolean isNull(String[] value, String contentIsNull){
		String contentToCompare = contentIsNull;
		
		if( value == null )
			return true;
		boolean ret = true;
		for( int i = 0; i < value.length; i++ ){
			if( !isNull(value[i]) ){
				contentToCompare = parseNull(contentIsNull);
				if( !contentToCompare.equals(value[i]) ){
					ret = false;
					break;
				}
			}
		}
		return ret;
	}

	
	/**
	 * Return the empty text or the same text.
	 * 
	 * @param value the text value.
	 * 
	 * @return the validated text.
	 */
	public static String parseNull(String value) {
		if (isNull(value))
			return ConstantDataManager.BLANK;
		return value;
	}

	/**
	 * Return text with zeros the left.
	 * 
	 * @param value the text value.
	 * 
	 * @param size the length of text.
	 * 
	 * @return the text formatted.
	 */
	public static String zeroLeft(String value, int size) {
		String valueReturn = value;
		if (valueReturn == null)
			valueReturn = ConstantDataManager.BLANK;
		int len = value.length();
		for (int i = 0; i < size - len; i++) {
			valueReturn = ConstantDataManager.ZERO_STRING + value;
		}
		return value;
	}
 
	/**
	 * Array to 'SQLString'.
	 * 
	 * @param str the string.
	 * 
	 * @return the 'SQLString'.
	 */
	public static String arrayToSQLString(String[] str) {
		StringBuffer ret = new StringBuffer();
		if( str == null )
			return null;
		int count = 0;
		for (int i = 0; i < str.length; i++) {
			if (!isNull(str[i])){
				if( count++ > 0 )
					ret.append(ConstantDataManager.VIRGULA_STRING);
				ret.append(ConstantDataManager.SIMPLE_ASPAS).append(str[i]).append(ConstantDataManager.SIMPLE_ASPAS);
			}
		}
		return ret.toString();
	}

	/**
	 * Array Integer to 'SQLString'.
	 * 
	 * @param in the 'Integer'.
	 * 
	 * @return the 'SQLString'.
	 */
	public static String arrayIntToSQLString(Integer[] in) {
		StringBuffer ret = new StringBuffer();
		if( in == null )
			return null;
		int count = 0;
		for (int i = 0; i < in.length; i++) {
			if (!isNull(in[i].toString())){
				if( count++ > 0 )
					ret.append(ConstantDataManager.VIRGULA_STRING);
				ret.append(ConstantDataManager.SIMPLE_ASPAS).append(in[i].toString()).append(ConstantDataManager.SIMPLE_ASPAS);
			}
		}
		return ret.toString();
	}
	
	/**
	 * Array Long to 'SQLString'.
	 * 
	 * @param in the 'Long'.
	 * 
	 * @return the 'SQLString'.
	 */
	public static String arrayLongToSQLString(Long[] in) {
		StringBuffer ret = new StringBuffer();
		if( in == null )
			return null;
		int count = 0;
		for (int i = 0; i < in.length; i++) {
			if (!isNull(in[i].toString())){
				if( count++ > 0 )
					ret.append(ConstantDataManager.VIRGULA_STRING);
				ret.append(ConstantDataManager.SIMPLE_ASPAS).append(in[i].toString()).append(ConstantDataManager.SIMPLE_ASPAS);
			}
		}
		return ret.toString();
	}
	
	/**
	 * Substitui a ocorrencia original pela substituicao, dentro de um texto fornecido
	 * @param string Texto a ser verificado
	 * @param original Ocorrencia original
	 * @param replaced Texto substituido 
	 * @return Texto com a substituicao
	 */
	public static String replace( String string, String original, String replaced ){
		String retVal = string;
		int index = retVal.indexOf(original);
		while( index >= 0 ){
			retVal = retVal.substring(0,index) + replaced + retVal.substring(index+original.length(),retVal.length());
			index = retVal.indexOf(original);
		}
		return retVal;
	}
	
	/**
	 * Obtem o valor boolean de um texto. Utilizado em casos em que o valor booleano e um texto 
	 * @param value Valor tipo booleano como texto
	 * @param trueValue Valor verdadeiro
	 * @return true ou false
	 */
	public static boolean booleanValue( String value, String trueValue ){
		if( value == null )
			return false;
		if( !value.equals( trueValue ) )
			return false;
		return true;
	}
	
	/**
	 * Obtem o valor boolean de um texto. Utilizado em casos em que o valor booleano e um texto
	 * @param value Valor tipo booleano como texto
	 * @return true ou false
	 */
	public static boolean booleanValue(final String value){
		return booleanValue( value, "1" );
	}
	
	/**
	 * Verifica se o texto pode ser convertido para o tipo long, atravï¿½s de Long.parseLong
	 * @param value
	 * @return
	 */
	public static boolean isLong(final String value){
		boolean result = false;
		if(!isNull(value))
		{
			final String decimalPattern = "([0-9]*)";   
			result = Pattern.matches(decimalPattern, value);						
		}
		return result;
	}
	
	/**
	 * Verifica se o texto pode ser convertido para o tipo double, atravï¿½s de Double.parseDouble
	 * @param value
	 * @return
	 */
	public static boolean isDouble(final String value)
	{
		boolean result = false;
		if(value!=null)
		{
			final String decimalPattern = "([0-9]*)\\.([0-9]*)";   
			result = Pattern.matches(decimalPattern, value);
		}
		return result;
	}

	/**
	 * 
	 * @param value Valor base
	 * @param size Tamanho limite para o preenchimento
	 * @param ch Caractere a ser utilizado para o preenchimento
	 * @return String preenchida
	 */
	public static String charLeft(String value, int size, char ch) {
		String retVal = value;
		if (retVal == null)
			retVal = "";
		int len = retVal.length();
		for (int i = 0; i < size - len; i++) {
			retVal = ch + retVal;
		}
		return retVal;
	}

	/**
	 * Preenche com qualquer character, caso seja necessï¿½rio, tanto no inï¿½cio quanto no final da string
	 * Substitui caracteres especiais, por caracteres comuns
	 * @param value String a ser preenchida!!
	 * @param length 
	 * @param letra
	 * @param posicao
	 * @return String preenchida cfe. parï¿½metros
	 * 
	 * exemplo de utilizaï¿½ï¿½o
	 * boolean bFlCartera = documentHandler.getCustomerStatus( fillString(ficha.getAccountId().toString(), 8, '0', 0) );
	 */
	 public static String fillString(String value, int length, char letra, int posicao) {
		if (value == null) {
			return null;
		}
		if (value.length() > length) {
			return value;
		}
		StringBuffer sb = new StringBuffer(length);
		// Os caracteres devem ser colocados no inï¿½cio da string
		if (posicao == 0) {
			for (int i = 0; i < length - value.length(); i++) {
				sb.append(letra);
			}
			sb.append(value);
		} else {
			sb.append(value);
			for (int i = 0; i < length - value.length(); i++) {
				sb.append(letra);
			}
		}
		return sb.toString();
	}

	/**
	 * Retorna um texto com a primeira e a ultima palavra
	 * @param value Valor do texto
	 * @return Texto com a primeira e a ultima palavra
	 */
	public static String getFirstAndLast( String value ) {
		
		String valueReturn = value;
		
		if (isNull(valueReturn))
			return null;
		valueReturn = value.trim();
		if (value.indexOf(" ") == -1)
			return value;
		String first = value.substring(0, value.indexOf(" "));
		String last = value.substring(value.lastIndexOf(" "), value.length());
		return first + last;
	}

	/**
	 * Retorna um String com o primeiro caracter de cada palavra em maiusculo
	 * @param value Valor a ser convertido
	 * @return String com o primeiro caracter de cada palava em maiusculo
	 */
	public static String initCap( String value ){
		
		String valueReturn = value;
		
		valueReturn = parseNull(valueReturn).toLowerCase();
		StringBuffer ret = new StringBuffer();
		for( int i = 0; i < valueReturn.length(); i++ ){
			if( i == 0 )
				ret.append(String.valueOf(valueReturn.charAt(i)).toUpperCase());
			else if( value.charAt(i-1) == ' ' )
				ret.append(String.valueOf(valueReturn.charAt(i)).toUpperCase());
			else
				ret.append(String.valueOf(valueReturn.charAt(i)));
		}
		return ret.toString();
	}

	/**
	 * Retorna um String com o primeiro caracter em maiusculo
	 * @param value Valor a ser convertido
	 * @return String com o primeiro caracter maiusculo
	 */
	public static String firstCap( String value ){
		
		String valueReturn = value;
		
		valueReturn = parseNull(valueReturn).toLowerCase();
		if( valueReturn.length() > 0 )
			valueReturn = String.valueOf(valueReturn.charAt(0)).toUpperCase() + valueReturn.substring(1);
		return valueReturn;
	}

    /**
     * Verifica se um texto contem um padrao
     * @param input
     * @param pattern
     * @param ignoreCase
     * @return
     */
    public static final boolean contains(String input, String pattern, boolean ignoreCase) {
    	if( isNull(input) || isNull(pattern) )
    		return false;
		final int n = pattern.length();
		int last = 0;
		for (int i = 0; i < n;) {
			char c = ' ';
			int j = i;
			for (; j < n; j++) {
				char c2 = pattern.charAt(j);
				if (c2 == ' ' || c2 == '+' || c2 == '*') {
					c = c2;
					break;
				}
			}
			int k = subset(pattern, i, j, input, last, ignoreCase);
			if (k < 0)
				return false;
			if (c == ' ' || c == '+')
				last = 0;
			else if (c == '*')
				last = k + j - i;
			i = j + 1;
		}
		return true;
	}
    
    /**
     * 
     * @param little
     * @param littleStart
     * @param littleStop
     * @param big
     * @param bigStart
     * @param ignoreCase
     * @return
     */
    private static final int subset(String little, int littleStart, int littleStop, String big, int bigStart, boolean ignoreCase) {
		if (ignoreCase) {
			final int n = big.length() - (littleStop - littleStart) + 1;
			outerLoop:
			for (int i = bigStart; i < n; i++) {
				final int n2 = littleStop - littleStart;
				for (int j = 0; j < n2; j++) {
					char c1 = big.charAt(i + j);
					char c2 = little.charAt(littleStart + j);
					if (c1 != c2 && c1 != toOtherCase(c2)) //Ignore case. See below.
						continue outerLoop;

				}
				return i;
			}
			return -1;
		}
		final int n = big.length() - (littleStop - littleStart) + 1;
		outerLoop:
		for (int i = bigStart; i < n; i++) {
			final int n2 = littleStop - littleStart;
			for (int j = 0; j < n2; j++) {
				char c1 = big.charAt(i + j);
				char c2 = little.charAt(littleStart + j);
				if (c1 != c2) //Consider case.  See above.
					continue outerLoop;
			}
			return i;
		}
		return -1;
	} 
    
    /**
     * Reverte o caracter para maiusculo ou minusculo
     * @param c
     * @return
     */
    public static final char toOtherCase(char c) {
		int i = c;
		final int A = 'A'; //65
		final int Z = 'Z'; //90
		final int a = 'a'; //97
		final int z = 'z'; //122
		final int SHIFT = a - A;
		if (i < A) //non alphabetic
			return c;
		else if (i <= Z) //upper-case
			return (char) (i + SHIFT);
		else if (i < a) //non alphabetic
			return c;
		else if (i <= z) //lower-case
			return (char) (i - SHIFT);
		else
			//non alphabetic 
			return c;
	} 
    
	/**
	 * Retorna uma string com os dados de um String[]
	 * 
	 */
	public static String setArrayToString(String[] str) {
		
		String strResult = "";
		
		for(int i = 0 ; i < str.length ; i++)
		{
			if (str[i].trim().length() > 0) strResult += "'" + str[i] + "',";
		}
		
		if (strResult.length() > 0) strResult = strResult.substring(0, strResult.length() - 1);
		
		return strResult;
	}

	/**
	 * 
	 * @param value Valor a ser validado
	 * @return Retorna true se value for validado como nulo
	 */
	public static boolean isArrayNull(String[] value){
		return isArrayNull(value,null);
	}

	/**
	 * 
	 * @param value Valor a ser validado
	 * @param contentIsNull Conteudo interpretado como nulo
	 * @return Retorna true se value for validado como nulo
	 */
	public static boolean isArrayNull(String[] value,String contentIsNull){
		if( value == null )
			return true;
		boolean ret = true;
		for( int i = 0; i < value.length; i++ ){
			if( !isNull(value[i]) ){
				if( contentIsNull == null ){
					ret = false;
					break;
				}
				if( !contentIsNull.equals(value[i]) ){
					ret = false;
					break;
				}
			}
		}
		return ret;
	}
	
	/**
	 * Substitui os caracteres(from) par os novos(to) que ocorrerem dentro de um texto(str)
	 * @param str Texto a ser analisado
	 * @param from Texto que devera ser encontrado
	 * @param to Texto a ser substituido
	 * @return
	 */
	public static String convertChar(String str, char from, String to) {
		// Convert special characters
		int length = str.length();
		int nextCh = 0;
		StringBuffer out = new StringBuffer();
		while (true) {
			// continue as long as there is another conversion character
			// Get index of next from
			int nextfrom = str.indexOf(from, nextCh);
			// Check if no from left
			if (nextfrom == -1) {// no more special characters
				// append remainder of string
				out.append(str.substring(nextCh, str.length()));
				break;
			}
			// Append this line of string, to, skip the from and continue
			if (nextCh < nextfrom) // at least one character before from
				out.append(str.substring(nextCh, nextfrom ));// - 1));
			// append converted character
			out.append(to);
			nextCh = nextfrom + 1;
			// Check if end of line
			if (nextCh > length)
				break;
		}
		return out.toString();
	}
	
	
	/**
	 * Verifica se um texto e nulo ou vazio
	 * @param value Valor do texto
	 * @return true ou false
	 */
	public static String trim( String value ){
		if( value == null )
			return null;
		return value.trim();
	}
	
	/**
	 * Transforma um array de String em array de Long
	 * @param value Array de String
	 * @return Array de Long
	 */
	public static Long[] toLong( String[] value ){
		// Verifica se os elementos podem ser Long
		List<Long> list = null;
		if( isNull(value) )
			return null;
		for( int i = 0; i < value.length; i++ ){
			try {
				long lval = Long.parseLong(parseNull(value[i]));
				if( list == null )
					list = new ArrayList<Long>();
				list.add(new Long(lval));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Long[] lArr = null;
		if( list != null && list.size() > 0 )
			lArr = list.toArray ( new Long[list.size()] );
		return lArr;
	}
	
	/**
	 * Transforma um array de String em array de Integer
	 * @param value Array de String
	 * @return Array de Integer
	 */
	public static Integer[] toInteger( String[] value ){
		// Verifica se os elementos podem ser Integer
		List<Integer> list = null;
		if( isNull(value) )
			return null;
		for( int i = 0; i < value.length; i++ ){
			try {
				int lval = Integer.parseInt(parseNull(value[i]));
				if( list == null )
					list = new ArrayList<Integer>();
				list.add(new Integer(lval));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Integer[] lArr = null;
		if( list != null && list.size() > 0 )
			lArr = list.toArray ( new Integer[list.size()] );
		return lArr;
	}
	
	/**
	 * Remove os caracteres de espaco que ocorrerem em value 
	 * @param value String do conteu a ser analisado
	 * @return String com value sem espacos
	 */
	public static String removeSpace(final String value)
	{
		if(isNull(value))
			return null;
		StringBuffer ret = new StringBuffer();
		for( int i = 0; i < value.length(); i++ )
		{
			if( value.charAt(i)!=' ')
				ret.append(value.charAt(i));
		}
		return ret.toString();
	}

	/**
	 * Obtem somente os numeros dentro de um value
	 * @param value Texto a ser analisado
	 * @return String somente com numeros
	 */
	public static String getNumbers( String value ){
		if( isNull(value) )
			return null;
		String numbs = "0123456789";
		StringBuffer sb = new StringBuffer();
		for( int i = 0; i < value.length(); i++ ){
			boolean hasPerm = false;
			for( int j = 0; j < numbs.length(); j++ ){
				if( value.charAt(i) == numbs.charAt(j) ){
					hasPerm = true;
					break;
				}
			}
			if( hasPerm ){
				sb.append(value.charAt(i));
			}
		}
		return sb.toString();
	}

	/**
	 * Obtem somente os numeros dentro de um value, como um Long
	 * @param value Texto a ser analisado
	 * @return Long dos numeros obtidos
	 */
	public static Long getLongNumbers( String value ){
		long ret = 0;
		try {
			ret = Long.parseLong(getNumbers(value));
		} catch (Exception e) {
			e.printStackTrace();
			ret = 0;
		}
		return new Long(ret);
	}
	
	/**
	 * Verifica se os textos sao iguais, com verificacao de null
	 * @param s1 Texto 1 a ser validado
	 * @param s2 Texto 2 a ser validado
	 * @return true - os objetos sao iguais, false - os objetos sao diferentes
	 */
	public static boolean equalsNull( String s1, String s2 )
	{
				
		if( s1 == null && s2 == null )
			return true;
		else if( s1 == null && s2 != null )
			return false;
		else if( s1 != null && s2 == null )
			return false;
		
		if(s1!=null){
			return s1.equals(s2);
		}
		return false;
	}
	
	public static String replaceOnce(String text, String searchString, String replacement) {
        return replaceCommons(text, searchString, replacement, 1);
    }
    public static String replaceCommons(String text, String searchString, String replacement) {
        return replaceCommons(text, searchString, replacement, -1);
    }
    public static String replaceCommons(String text, String searchString, String replacement, int max) {
    	int maxInteger = max;
        if (isNull(text) || isNull(searchString) || replacement == null || maxInteger == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == -1) {
            return text;
        }
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = (increase < 0 ? 0 : increase);
        increase *= (maxInteger < 0 ? 16 : (maxInteger > 64 ? 64 : maxInteger));
        StringBuffer buf = new StringBuffer(text.length() + increase);
        while (end != -1) {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--maxInteger == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }
    
    /**
     * Efetua um merge entre message(deve conter {0},{1},...) e o array de parametros
     * @param message
     * @param args
     * @return
     */
	public static String mergeMessage( String message, Object[] args ){
		StringBuffer sb = new StringBuffer(message);
		if( args != null && args.length > 0 ){
			for( int i = 0; i < args.length; i++ ){
				String currKeyParam = "{"+i+"}";
				if( args[i] != null ){
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					String currVal = "";
					if( args[i] instanceof Long ){
						currVal = ""+((Long)args[i]).longValue();
					}else if( args[i] instanceof long[] ){
						long[] obArr = (long[])args[i];
						StringBuffer sbAux = new StringBuffer();
						for( int j = 0; j < obArr.length; j++ ){
							if( j > 0 )
								sbAux.append(" ");
							sbAux.append(obArr[j]);
						}
						currVal = sbAux.toString();
					}else if( args[i] instanceof Integer ){
						currVal = ""+((Integer)args[i]).intValue();
					}else if( args[i] instanceof int[] ){
						int[] obArr = (int[])args[i];
						StringBuffer sbAux = new StringBuffer();
						for( int j = 0; j < obArr.length; j++ ){
							if( j > 0 )
								sbAux.append(" ");
							sbAux.append(obArr[j]);
						}
						currVal = sbAux.toString();
					}else if( args[i] instanceof Double ){
						currVal = ""+((Double)args[i]).doubleValue();
					}else if( args[i] instanceof double[] ){
						double[] obArr = (double[])args[i];
						StringBuffer sbAux = new StringBuffer();
						for( int j = 0; j < obArr.length; j++ ){
							if( j > 0 )
								sbAux.append(" ");
							sbAux.append(obArr[j]);
						}
						currVal = sbAux.toString();
					}else if( args[i] instanceof Float ){
						currVal = ""+((Float)args[i]).floatValue();
					}else if( args[i] instanceof float[] ){
						float[] obArr = (float[])args[i];
						StringBuffer sbAux = new StringBuffer();
						for( int j = 0; j < obArr.length; j++ ){
							if( j > 0 )
								sbAux.append(" ");
							sbAux.append(obArr[j]);
						}
						currVal = sbAux.toString();
					}else if( args[i] instanceof Boolean ){
						currVal = ""+((Boolean)args[i]).booleanValue();
					}else if( args[i] instanceof boolean[] ){
						boolean[] obArr = (boolean[])args[i];
						StringBuffer sbAux = new StringBuffer();
						for( int j = 0; j < obArr.length; j++ ){
							if( j > 0 )
								sbAux.append(" ");
							sbAux.append(obArr[j]);
						}
						currVal = sbAux.toString();
					}else if( args[i] instanceof BigDecimal ){
						currVal = ""+((BigDecimal)args[i]).doubleValue();
					}else if( args[i] instanceof Date ){
						try{currVal = sdf.format((Date)args[i]);}catch(Exception e){
							e.printStackTrace();
						}
					}else if( args[i] instanceof Timestamp ){						
						try{currVal = sdf.format((Date)args[i]);}catch(Exception e){
							e.printStackTrace();
						}
					}else if( args[i] instanceof String ){
						currVal = ""+args[i];
					}
					if( !StringUtils.isNull(currVal) ){
						int ini = sb.toString().indexOf(currKeyParam);
						if( ini > -1 ){
							int fim = ini + currKeyParam.length();
							sb.replace(ini,fim,currVal);
						}
					}
				}
			}
		}
		return sb.toString(); 
	}
	
	public static String arrayToString( String[] arr, char separator ){
		StringBuffer sb = new StringBuffer();
		for( int i = 0; arr != null && i < arr.length; i++ ){
			if( i > 0 )
				sb.append(separator);
			sb.append(arr[i]);
		}
		return sb.toString();
	}
	
	/**
	 * Verifica se contem um objeto especï¿½fico (objectToFind) dentro de um Array
	*/
	public static boolean contains(Object array[], Object objectToFind)
    {
        return indexOf(array, objectToFind) != -1;
    }
    
    public static int indexOf(Object array[], Object objectToFind)
    {
        if(array == null) return -1;
        int startIndex = 0;
        if(objectToFind == null) {
            for(int i = startIndex; i < array.length; i++)
                if(array[i] == null)
                    return i;

        } else {
            for(int i = startIndex; i < array.length; i++)
                if(objectToFind.equals(array[i]))
                    return i;
        }
        return -1;
    }

	/**
	 * Ricardo Saraiva
	 * 04/06/2009
	 * 
	 * Conta a quantidade de ocorrï¿½ncias de um character dentro de uma string
	 */
	public static int conta_ocorrencias(char caracter, String str)
	{     
		if (StringUtils.isNull(str))
			return 0;
		
		int count = 0;
		
		for (int i = 0; i < str.length(); i++)
		{     
			if(str.charAt(i) == caracter)
			{     
				count++;     
			}     
		}     
		
		return count;     
	}
	
	public static double roundTo2Decimals(final double val) 
	{
        final DecimalFormat df2 = new DecimalFormat("###.##");
        String valorString = df2.format(val);
        valorString = valorString.replaceAll(",", ".");
        return Double.valueOf(valorString);
	}
	
	public static String convertTime(long time)
	{
	    final Date date = new Date(time);
	    final Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    return format.format(date);
	}
	
	public static String ellipsize(final String text, final int max) 
	{

	    if (textWidth(text) <= max)
	        return text;

	    // Start by chopping off at the word before max
	    // This is an over-approximation due to thin-characters...
	    int end = text.lastIndexOf(' ', max - 3);

	    // Just one long word. Chop it off.
	    if (end == -1)
	        return text.substring(0, max-3) + "...";

	    // Step forward as long as textWidth allows.
	    int newEnd = end;
	    do {
	        end = newEnd;
	        newEnd = text.indexOf(' ', end + 1);

	        // No more spaces.
	        if (newEnd == -1)
	            newEnd = text.length();

	    } while (textWidth(text.substring(0, newEnd) + "...") < max);

	    return text.substring(0, end) + "...";
	}
	
	private static int textWidth(final String str) 
	{
	    return str.length() - str.replaceAll(NON_THIN, "").length() / 2;
	}
	
	private final static String NON_THIN = "[^iIl1\\.,']";
	
	public static String priceWithDecimal (final double price) 
	{
		final String result = String.format("%.2f", price);
		return result;
	}
	
	public static String priceWith3Decimal(final double price) 
	{
		final String result = String.format("%.3f", price);
		return result;
	}
	
	public static String priceWith1Decimal (final double price) 
	{
		final String result = String.format("%.1f", price);
		return result;
	}

	public static String priceWithoutDecimal (final double price) 
	{
		final DecimalFormat formatter = new DecimalFormat("###.###.###,##");
	    return formatter.format(price);
	}
	
	public static String formataMoedaBR(final double valor)
	{
		final Locale ptBr = new Locale("pt", "BR");
		final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(ptBr);
		return numberFormat.format(valor);
	}

	public static String priceToString(final double price) 
	{
		final String toShow = priceWithoutDecimal(price);
	    if (toShow.indexOf(".") > 0) {
	        return priceWithDecimal(price);
	    }
		return priceWithoutDecimal(price);
	}
	
	public static final String formatDate(final Date date, final String pattern)
	{
		final SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
	
	public static final int getNextRandom(final int inicial, final int numberFinal)
	{
		final Random gerador = new Random();
		int result = 0;
		while(result<=inicial)
		{
			result = gerador.nextInt(numberFinal);
		}
		return result;
	}
	
	public static String replaceNonDigits(final String string) 
	{
	    if (string == null || string.length() == 0) {
	        return "";
	    }
	    return string.replaceAll("[^0-9]+", "");
	}
	
	public static String padRight(final String s, final int n, final String car) 
	{
	     return String.format("%1$-" + n + "s", s).replace(" ", car);  
	}

	public static String padLeft(final String s, final int n, final String car)
	{
	    return String.format("%1$" + n + "s", s).replace(" ", car);   
	}
	
	public static String replaceSpecialCharacteres(final String string, final String regex)
	{
		if (string == null || string.length() == 0) 
		{
	        return "";
	    }
	    return string.replaceAll(regex, "");
	}
	
	public static double bankersRound(final double value)
	{
		double result = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); 
		return result;
	}
	
	public static String removeAllBreakLines(String text)
	{
		text = text.replaceAll("\\r\\n|\\r|\\n", " ");
		while(text.indexOf("  ")>-1)
		{
			text = text.replaceAll("  ", " ");
		}		
		return text;
	}
	
	public static String converteEspeciaCaracter(final String text) 
	{ 
	    return text.replaceAll("[Ã£Ã¢Ã Ã¡Ã¤]", "a")   
	                .replaceAll("[ÃªÃ¨Ã©Ã«]", "e")   
	                .replaceAll("[Ã®Ã¬Ã­Ã¯]", "i")   
	                .replaceAll("[ÃµÃ´Ã²Ã³Ã¶]", "o")   
	                .replaceAll("[Ã»ÃºÃ¹Ã¼]", "u")   
	                .replaceAll("[ÃƒÃ‚Ã€Ã�Ã„]", "A")   
	                .replaceAll("[ÃŠÃˆÃ‰Ã‹]", "E")   
	                .replaceAll("[ÃŽÃŒÃ�Ã�]", "I")   
	                .replaceAll("[Ã•Ã�?Ã’Ã“Ã–]", "O")   
	                .replaceAll("[Ã›Ã™ÃšÃœ]", "U")   
//	                .replace('Ã§', 'c')
//	                .replace('Ã‡', 'C')   
//	                .replace('Ã±', 'n')   
//	                .replace('Ã‘', 'N')
	                .replaceAll("!", "")	                
	                .replaceAll("[^.,/-/(/)a-zA-Z0-9 ]", "");
	}

	public static boolean isValidEmailAddress(final String email)
	{
        final String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}
	
    public static boolean isInteger(double n, double tolerance)
    {
        double absN = Math.abs(n);
        if (Math.abs(absN - (double)Math.round(absN)) <= tolerance) 
        {
            return true;
        }
        return false;
    }
    
	public static String formataValorDecimal(double value) 
	{
		df.applyPattern(",##0.00");
		return df.format(value);
	}
	
	public static String formataQtdeDecimal(double value) 
	{
		df.applyPattern(",##0.000");
		return df.format(value);
	}
	
	public static String formataTempo(int elapsed)
	{
	    int ss = elapsed % 60;
	    elapsed /= 60;
	    int min = elapsed % 60;
	    elapsed /= 60;
	    int hh = elapsed % 24;
	    return strzero(hh) + ":" + strzero(min) + ":" + strzero(ss);
	}
	
	private static String strzero(int n)
	{
	    if(n < 10)
	      return "0" + String.valueOf(n);
	    return String.valueOf(n);
	}
	
	//public static void main(String[] args) {
	//	System.out.println(formataTempo(23683));
	//}
}