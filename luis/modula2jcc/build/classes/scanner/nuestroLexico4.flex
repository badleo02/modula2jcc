package Scanner;
import TablaDeSimbolos.*;
import GestorDeErrores.*;
import java.io.FileReader;
import java.util.ArrayList;

/* Declaraciones */
%%

%class Scanner
%unicode
%cup
%public
%line
%column
/* Token que retornará con el fin de fichero */
%eofval{
  return new Token(TipoToken.EOF.ordinal());
%eofval}

/* Codigo empotrado */
%{
  
	TablaDeSimbolos tablaSimbolos;
  	GestorErrores gestor;
  	String cadena = new String ();

  	StringBuffer string = new StringBuffer();
  
  	private Token creaToken(TipoToken type, Object value) {
	
		ArrayList<Object> atributos = new ArrayList<Object>();
		
		if (type == TipoToken.SEPARADOR){
			atributos.add((TipoSeparadores)value);
		} 
		else if (type == TipoToken.PARENTESIS_IZQ 	||
		   		 type == TipoToken.PARENTESIS_DER	||
		   		 type == TipoToken.CORCHETE_IZQ ||
		   		 type == TipoToken.CORCHETE_DER){
					// No tiene atributos
		} 
		else if (type == TipoToken.OP_SUMADOR){
			atributos.add((TipoOperadoresAditivos)value);
		} 
		else if (type == TipoToken.OP_COMPARADOR){
			atributos.add((TipoOperadoresComparadores)value);
		} 
		else if (type == TipoToken.OP_MULTIPLICADOR){
			atributos.add((TipoOperadoresMultiplicativos)value);
		}
		else if (type == TipoToken.IDENTIFICADOR){
			
			if (tablaSimbolos.esReservada((String)value)){
				type = TipoToken.PAL_RES;
			}

			boolean esFunPred = false;
			boolean esProcPred = false;
			
			try{
				atributos.add (funcionesPredef.valueOf((String) value));
				esFunPred = true;
				type = TipoToken.FUNCION_PREDEFINIDA;
			}
			catch (Exception e){
				esFunPred = false;
			}
			
			try{
				atributos.add(ProcedimientosPredefinidos.valueOf((String) value));
				esProcPred = true;
				type = TipoToken.PROCEDIMIENTO_PREDEFINIDO;
			}
			catch (Exception e2) {
				esProcPred = false;
			}
		
			if (!esProcPred && !esFunPred)
				atributos = tablaSimbolos.insertaIdentificador((String)value);
		} 
		else if (type == TipoToken.PUNTERO) {
			atributos = tablaSimbolos.insertaIdentificador((String)value);
		} 
		else if (type == TipoToken.NUM_ENTERO){
			atributos.add(new Integer((Integer)value)); 		
		} 
		else if (type == TipoToken.NUM_REAL){
			atributos.add(new Double((Double)value));
		} 
		else if (type == TipoToken.CARACTER){
			atributos.add(new Character((Character)value));
		} 
		else if (type == TipoToken.CADENA){
			atributos.add(new String((String)value));	
		} 
		
	    return new Token(type.ordinal(), yyline, yycolumn, atributos);
	}

  	/**
   	* Constructor de la clase Scanner.
   	* There is also java.io.Reader version of this constructor.
   	*
   	* @param   in  the java.io.Inputstream to read input from.
   	*/
  	public Lexico(java.io.Reader in, GestorErrores gestor, TablaDeSimbolos ts) {
    	
    	this.zzReader = in;
    	this.tablaSimbolos = ts;
    	this.gestor = gestor;
    	this.yyline = 1;
  	}

  	/**
   	* Creates a new scanner.
   	* There is also java.io.Reader version of this constructor.
   	*
   	* @param   in  the java.io.Inputstream to read input from.
   	*/
  	public Lexico(java.io.InputStream in, GestorErrores gestor, TablaDeSimbolos ts) {
    	this(new java.io.InputStreamReader(in), gestor, ts);
  	}

%}

/* Expresiones Regulares */

digito_octal	= [0-7]
letrahex        = [A-F]
digito		= [0-9]
numero_octal	= {digito_octal}+"B"
caracter_en_octal	= {digito_octal}+"C"
numero_hexadecimal	= {digito}({digito}|{letrahex})+"H"
caracter        = \'.\'
cadena	        = \"~\"|\'~\'
identificador   = [:jletter:]([:jletterdigit:]|"_")* 
idError         = [:jdigit:]+({identificador})
comentarioIni   = \(\* 
comentarioFin   = \*\)
todoMenosParen  = [^)]
cuerpoComentario2= {todoMenosParen}* 
comentario      = {comentarioIni}{cuerpoComentario2}{comentarioFin}
espacio         = [ \n\t\r]
numero_entero   = {digito}+
numero_real={numero_entero}("."{numero_entero})("E"("+"|"-")?{numero_entero})?
WHITE_SPACE_CHAR =[\n\r\ \t\b\012]
separadores      = \+|\-|\*|\/|\<|\>|\(|\,|\)|\[|\]|\;|\:|\=|\.|{WHITE_SPACE_CHAR}



%state YYINITIAL, IDENSEP, INTSEP, REALSEP, PALRESSEP, PALRESSEPEND, OPARSEP, NOIDSEP, STRING

%%

<YYINITIAL> "*"             { return creaToken(TipoToken.OPERADOR_MULTIPLICADOR, TipoOperadoresMultiplicativos.MULT); }
<YYINITIAL> "+"             { return creaToken(TipoToken.OPERADOR_SUMADOR, TipoOperadoresAditivos.MAS); }
<YYINITIAL> "-"             { return creaToken(TipoToken.OPERADOR_SUMADOR, TipoOperadoresAditivos.MENOS); }
<YYINITIAL> "/"             { return creaToken(TipoToken.OPERADOR_MULTIPLICADOR, TipoOperadoresMultiplicativos.DIV); }
<YYINITIAL> ";"             { return creaToken(TipoToken.SEPARADOR, TipoSeparadores.PUNTOYCOMA); }
<YYINITIAL> ","             { return creaToken(TipoToken.SEPARADOR, TipoSeparadores.COMA); }
<YYINITIAL> "("             { return creaToken(TipoToken.ABREPAREN, yytext()); }
<YYINITIAL> ")"             { return creaToken(TipoToken.CIERRAPAREN, yytext()); }
<YYINITIAL> "["             { return creaToken(TipoToken.ABRECORCHETE, yytext()); }
<YYINITIAL> "]"             { return creaToken(TipoToken.CIERRACORCHETE, yytext()); }
// Estos dos de debajo son iguales que los corchetes
<YYINITIAL> "(."            { return creaToken(TipoToken.ABRECORCHETE, yytext()); }
<YYINITIAL> ".)"            { return creaToken(TipoToken.CIERRACORCHETE, yytext()); }
<YYINITIAL> "AND"           { return creaToken(TipoToken.OPERADOR_MULTIPLICADOR, TipoOperadoresMultiplicativos.AND); }
<YYINITIAL> "DIV"           { return creaToken(TipoToken.OPERADOR_MULTIPLICADOR, TipoOperadoresMultiplicativos.DIVENT); }
<YYINITIAL> "MOD"           { return creaToken(TipoToken.OPERADOR_MULTIPLICADOR, TipoOperadoresMultiplicativos.MOD); }
<YYINITIAL> "OR"            { return creaToken(TipoToken.OPERADOR_SUMADOR, TipoOperadoresAditivos.OR); }
<YYINITIAL> "&"           { return creaToken(TipoToken.OPERADOR_MULTIPLICADOR, TipoOperadoresMultiplicativos.AMPERSAND); }
<YYINITIAL> "="             { return creaToken(TipoToken.OPERADOR_COMPARADOR, TipoOperadoresComparadores.IGUAL); }
<YYINITIAL> "<"             { return creaToken(TipoToken.OPERADOR_COMPARADOR, TipoOperadoresComparadores.MENOR); }
<YYINITIAL> ">"             { return creaToken(TipoToken.OPERADOR_COMPARADOR, TipoOperadoresComparadores.MAYOR); }
<YYINITIAL> "<="            { return creaToken(TipoToken.OPERADOR_COMPARADOR, TipoOperadoresComparadores.MENOROIGUAL); }
<YYINITIAL> ">="            { return creaToken(TipoToken.OPERADOR_COMPARADOR, TipoOperadoresComparadores.MAYOROIGUAL); }
<YYINITIAL> "!="            { return creaToken(TipoToken.OPERADOR_COMPARADOR, TipoOperadoresComparadores.DISTINTO); }
<YYINITIAL> "#"            { return creaToken(TipoToken.OPERADOR_COMPARADOR, TipoOperadoresComparadores.ALMOHADILLA); }
<YYINITIAL> "IN"            { return creaToken(TipoToken.OPERADOR_COMPARADOR, TipoOperadoresComparadores.IN); }
<YYINITIAL> ":"             { return creaToken(TipoToken.SEPARADOR, TipoSeparadores.DOSPUNTOS); }
<YYINITIAL> ":="            { return creaToken(TipoToken.OPER_ASIGNACION, yytext()); }
<YYINITIAL> "."             { return creaToken(TipoToken.SEPARADOR, TipoSeparadores.PUNTO); }
<YYINITIAL> "^"				{ return creaToken(TipoToken.PUNTERO, yytext()); }


<YYINITIAL> {identificador} { yybegin(IDENSEP); cadena = yytext();}
<YYINITIAL> {numero_entero}        { yybegin(INTSEP); cadena=yytext();}
<YYINITIAL> {numero_real}          { yybegin(REALSEP); cadena=yytext();}
<YYINITIAL> {caracter}		{ return creaToken(TipoToken.LITERAL_CARACTER, new Character(yytext().charAt(1)));}
<YYINITIAL> {cadena}		{ return creaToken(TipoToken.LITERAL_CADENA, new String(yytext().substring(1,yytext().length()-1)));}
<YYINITIAL> {idError}       {gestor.insertaError(new GestorDeErrores.TError(Errores.LEXICO_ID_ERROR,yytext(),yyline,yycolumn));
				/*System.out.println("Error construccion identificador: '" + yytext() + "' linea: " + yyline + ", columna: " + yychar);*/ }
<YYINITIAL> {comentario}    {System.out.println("Comentario reconocido: " + yytext());}
<YYINITIAL> {espacio}       { /* Ignora espacios. */ }
<YYINITIAL> .               { /*System.out.println("Caracter ilegal, '" + yytext() + "' linea: " + yyline + ", columna: " + yychar);*/ 
				  gestor.insertaError(new GestorDeErrores.TError(Errores.LEXICO_CARACTER_NO_VALIDO,yytext(),yyline,yycolumn)); 
				}
<YYINITIAL> "ñ"              { /*System.out.println("Caracter ilegal, '" + yytext() + "' linea: " + yyline + ", columna: " + yychar);*/ 
				  gestor.insertaError(new GestorDeErrores.TError(Errores.LEXICO_CARACTER_NO_VALIDO,yytext(),yyline,yycolumn)); 
				}


<YYINITIAL> {numero_hexadecimal} {
	
	/*realizar la conversion del texto a hexadecimal*/
            String auxiliar=yytext();
            auxiliar=auxiliar.substring(0,auxiliar.length()-1);//quitamos el "H"
            int traduccionHex=0;
            int numBase=1;//iremos guardando aqui las potencias de 16
            char c;int j;
            for(int i=0;i<auxiliar.length();i++){
                c=auxiliar.charAt(auxiliar.length()-i-1);
                j =Character.digit(c,16);
                traduccionHex+=j*numBase;
                numBase=numBase*16;
            }
        	return creaToken(TipoToken.LITERAL_ENTERO,new Integer(traduccionHex));
	}

<YYINITIAL> {numero_octal} {
	
	/*realizar la conversion del texto a hexadecimal*/
            String auxiliar=yytext();
            auxiliar=auxiliar.substring(0,auxiliar.length()-1);//quitamos el "O"
            int traduccionOct=0;
            int numBase=1;//iremos guardando aqui las potencias de 16
            char c;int j;
            for(int i=0;i<auxiliar.length();i++){
                c=auxiliar.charAt(auxiliar.length()-i-1);
                j =Character.digit(c,8);
                traduccionOct+=j*numBase;
                numBase=numBase*8;
            }
	return creaToken(TipoToken.LITERAL_ENTERO,new Integer(traduccionOct));
	}

<YYINITIAL> {caracter_en_octal} {
		String entrada = yytext();
		String[] resultado = entrada.split("C");
		int decimal = Integer.parseInt(resultado[0],8);
		char ch = (char) decimal; 
		return creaToken(TipoToken.LITERAL_CARACTER, new Character(ch)); 
}

<REALSEP>   {separadores}   { yypushback(1); yybegin (YYINITIAL);return creaToken(TipoToken.LITERAL_REAL, new Double(cadena)); }

<REALSEP>   .  { String cadena2= yytext();yypushback(1);gestor.insertaError(new GestorDeErrores.TError(Errores.LEXICO_REAL_MAL_FORMADO,cadena+cadena2,yyline,yycolumn));yybegin (YYINITIAL);}

<INTSEP>    {separadores}   { yypushback(1); yybegin (YYINITIAL);return creaToken(TipoToken.LITERAL_ENTERO, new Integer(cadena));}
<INTSEP>    [^[:digit:]]    { String cadena2= yytext();yypushback(1);gestor.insertaError(new GestorDeErrores.TError(Errores.LEXICO_ID_ERROR,cadena+cadena2,yyline,yycolumn));yybegin (YYINITIAL);}


<IDENSEP>   {separadores}   { yypushback(1); yybegin (YYINITIAL);return creaToken(TipoToken.IDENTIFICADOR, cadena);}
<IDENSEP>   [^[:digit:][:jletter:]_]|"ñ"	{ String cadena2= yytext();yypushback(1);gestor.insertaError(new GestorDeErrores.TError(Errores.LEXICO_ID_ERROR,cadena+cadena2,yyline,yycolumn));yybegin (YYINITIAL);}

<NOIDSEP>   {separadores}   { gestor.insertaError(new GestorDeErrores.TError(Errores.LEXICO_ID_ERROR,yytext(),yyline,yycolumn));yybegin (YYINITIAL);}




