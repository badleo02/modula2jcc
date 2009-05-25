package scanner;
import tabla_de_simbolos.*;
import gestor_de_errores.*;
import java.io.Reader;
import java.util.ArrayList;

/* Declaraciones */
%%

%class Scanner
%public
%function get_token
%line
%column
%unicode
%full
%type Token

/* Codigo Empotrado */
%{
 	
/* ATRIBUTOS */
private TablaSimbolos _tablaSimbolos;
private GestorErrores _gestorErrores;
private String _lexema = new String ();
private int _numComentariosAbiertos = 0;
private String _fuenteDelError = null; // Para saber la fuente de la que procedemos al ir al sumidero
  
/**
 * Crea el token correspondiente.
 *
 * @param   tipo  Campo tipo del token del token a generar. 
 * @param   valor  Campo atributo del token a generar.
 */
private Token creaToken(TipoToken tipo, Object valor) {

	ArrayList<Object> atributos = new ArrayList<Object>();

	if (tipo == TipoToken.PUNTUACION){
		atributos.add((TipoPuntuacion)valor);
	}
	else if (tipo == TipoToken.OPERADOR_SUMADOR){
		atributos.add((TipoOperadorSumador)valor);
	} 
	else if (tipo == TipoToken.OPERADOR_COMPARADOR){
		atributos.add((TipoOperadorComparador)valor);
	} 
	else if (tipo == TipoToken.OPERADOR_MULTIPLICADOR){
		atributos.add((TipoOperadorMultiplicador)valor);
	}
	else if (tipo == TipoToken.OPERADOR_UNITARIO){
		atributos.add((TipoOperadorUnitario)valor);
	}
	else if (tipo == TipoToken.TIPO_SIMPLE){
		atributos.add((TipoSimple)valor));	
	}
	else if (tipo == TipoToken.CONSTANTE_PREDEFINIDA){
		atributos.add((ConstantePredefinida)valor));	
	}
	else if (tipo == TipoToken.IDENTIFICADOR){
		
            // Si es Palabra Reservada
            if (_tablaSimbolos.esPalabraReservada((String) valor)) {
                tipo = TipoToken.PALABRA_RESERVADA;
            }
            
            // Si es funcion predefinida
            if(_tablaSimbolos.esFuncionPredefinida((String) valor)){

                atributos.add(FuncionesPredefinidas.valueOf((String) valor));
                tipo = TipoToken.FUNCION_PREDEFINIDA;
            }
            else // Si es procedimiento predefinido
                if(_tablaSimbolos.esProcedimientoPredefinido((String) valor)){

                    atributos.add(ProcedimientosPredefinidos.valueOf((String) valor));
                    tipo = TipoToken.PROCEDIMIENTO_PREDEFINIDO;
                }
                else{ // Si es un identificador
                    atributos.add((String) valor);
                }

	} 
	else if (tipo == TipoToken.NUMERO_ENTERO){
		atributos.add(new Integer((Integer)valor)); 		
	} 
	else if (tipo == TipoToken.NUMERO_REAL){
		atributos.add(new Double((Double)valor));
	} 
	else if (tipo == TipoToken.CARACTER){
		atributos.add(new Character((Character)valor));
	} 
	else if (tipo == TipoToken.CADENA){
		atributos.add(new String((String)valor));	
	}  

    	return new Token(tipo.ordinal(), yyline, yycolumn, atributos);
}

/**
 * Constructor de la clase Scanner.
 *
 * @param   in  El Reader para leer el fichero de entrada.
 * @param   gestorErrores  El gestor de errores del compilador.
 * @param   tablaSimboloes  La tabla de simbolos del compilador.
 */
public Scanner(Reader in, GestorErrores gestorErrores, TablaDeSimbolos tablaSimbolos) {
    	
	zzReader = in;
    	_tablaSimbolos = tablaSimbolos;
    	_gestorErrores = gestorErrores;
    	yyline = 1;
}

/**
 * Devuelve la columna asociada al token.
 * 
 * @return La columna asociada al token.
 */
public int getYycolumn() {

    return yycolumn;
}

/**
 * Establece la columna asociada al token a valor <b>yycolumn</b>.
 * 
 * @param yycolumn Nuevo valor a establecer.
 */
public void setYycolumn(int yycolumn) {

    this.yycolumn = yycolumn;
}

/**
 * Devuelve la linea asociada al token.
 * 
 * @return La linea asociada al token.
 */
public int getYyline() {

    return yyline;
}

/**
 * Establece la linea asociada al token a valor <b>yyline</b>.
 * 
 * @param yyline Nuevo valor a establecer.
 */
public void setYyline(int yyline) {

    this.yyline = yyline;
}

%}/* Fin de C�digo Empotrado */

/* Expresiones Regulares */

letra_hexadecimal    		= [A-F]
digito				= [0-9]
digito_octal			= [0-7]
numero_entero   			= {digito}+
numero_real				= {numero_entero}("."{numero_entero})("E"("+"|"-")?{numero_entero})?
numero_octal			= {digito_octal}+"B"
caracter_en_octal			= {digito_octal}+"C"
numero_hexadecimal		= {digito}({digito}|{letra_hexadecimal})+"H"
identificador   			= ([:jletter:]|"_")([:jletterdigit:]|"_")* 
caracter_espacio_blanco		= [\n\r\ \t\b\012]
texto_cadena_comillas_dobles	= (\\\"|[^\n\r\"]|\\{caracter_espacio_blanco}+\\)*
texto_cadena_comillas_simples	= (\\\'|[^\n\r\']|\\{caracter_espacio_blanco}+\\)*
delimitador      			= \+|\-|\*|\/|\<|\>|\(|\,|\)|\[|\]|\;|\:|\=|\.|\"|\'|{caracter_espacio_blanco}|\^|\|


/* Tratamiento de los estados del aut�mata */
%state COMENTARIO, IDENTIFICADOR, NUMERO_ENTERO, NUMERO_REAL, CARACTER_EN_OCTAL, NUMERO_HEXADECIMAL, NUMERO_OCTAL, SUMIDERO
		 
%%

/* Estado Inicial */
<YYINITIAL> {
 	
	":"   	{ return creaToken(TipoToken.PUNTUACION, TipoPuntuacion.DOS_PUNTOS); }
	"."   	{ return creaToken(TipoToken.PUNTUACION, TipoPuntuacion.PUNTO); }
	";"   	{ return creaToken(TipoToken.PUNTUACION, TipoPuntuacion.PUNTO_Y_COMA); }
	","   	{ return creaToken(TipoToken.PUNTUACION, TipoPuntuacion.COMA); }
	"("   	{ return creaToken(TipoToken.PUNTUACION, TipoPuntuacion.PARENTESIS_IZQUIERDO); }
	")"   	{ return creaToken(TipoToken.PUNTUACION, TipoPuntuacion.PARENTESIS_DERECHO); }
	"["   	{ return creaToken(TipoToken.PUNTUACION, TipoPuntuacion.CORCHETE_IZQUIERDO); }
	"]"   	{ return creaToken(TipoToken.PUNTUACION, TipoPuntuacion.CORCHETE_DERECHO); }
	"{"		{ return creaToken(TipoToken.PUNTUACION, TipoPuntuacion.LLAVE_IZQUIERDA); }
	"}"		{ return creaToken(TipoToken.PUNTUACION, TipoPuntuacion.LLAVE_DERECHA); }
	"^"		{ return creaToken(TipoToken.PUNTUACION, TipoPuntuacion.PUNTERO); }
	"|"		{ return creaToken(TipoToken.PUNTUACION, TipoPuntuacion.BARRA_VERTICAL); }
	"+"   	{ return creaToken(TipoToken.OPERADOR_SUMADOR, TipoOperadorSumador.SUMA); }
 	"-"   	{ return creaToken(TipoToken.OPERADOR_SUMADOR, TipoOperadorSumador.RESTA); }
 	"/"   	{ return creaToken(TipoToken.OPERADOR_MULTIPLICADOR, TipoOperadorMultiplicador.DIVISION); }
 	"&"   	{ return creaToken(TipoToken.OPERADOR_MULTIPLICADOR, TipoOperadorMultiplicador.AMPERSAND); }
	"*"   	{ return creaToken(TipoToken.OPERADOR_MULTIPLICADOR, TipoOperadorMultiplicador.MULTIPLICACION); }
	"="   	{ return creaToken(TipoToken.OPERADOR_COMPARADOR, TipoOperadorComparador.IGUAL); }
	"<"   	{ return creaToken(TipoToken.OPERADOR_COMPARADOR, TipoOperadorComparador.MENOR); }
	">"   	{ return creaToken(TipoToken.OPERADOR_COMPARADOR, TipoOperadorComparador.MAYOR); }
	"AND" 	{ return creaToken(TipoToken.OPERADOR_MULTIPLICADOR, TipoOperadorMultiplicador.AND); }
 	"DIV" 	{ return creaToken(TipoToken.OPERADOR_MULTIPLICADOR, TipoOperadorMultiplicador.DIVISION_ENTERA); }
	"MOD" 	{ return creaToken(TipoToken.OPERADOR_MULTIPLICADOR, TipoOperadorMultiplicador.MOD); }
	"<="  	{ return creaToken(TipoToken.OPERADOR_COMPARADOR, TipoOperadorComparador.MENOR_IGUAL); }
	">="  	{ return creaToken(TipoToken.OPERADOR_COMPARADOR, TipoOperadorComparador.MAYOR_IGUAL); }
	"<>"		{ return creaToken(TipoToken.OPERADOR_COMPARADOR, TipoOperadorComparador.DISTINTO); }
	"#"		{ return creaToken(TipoToken.OPERADOR_COMPARADOR, TipoOperadorComparador.ALMOHADILLA); }
	"OR"  	{ return creaToken(TipoToken.OPERADOR_SUMADOR, TipoOperadorSumador.OR); }
	"IN"  	{ return creaToken(TipoToken.OPERADOR_COMPARADOR, TipoOperadorComparador.IN); }
	":="  	{ return creaToken(TipoToken.OPERADOR_ASIGNACION, yytext()); }
	".."		{ return creaToken(TipoToken.PUNTUACION, TipoPuntuacion.PUNTO_PUNTO); }
	"NOT"		{ return creaToken(TipoToken.OPERADOR_UNITARIO, TipoOperadorUnitario.NOT); }
	"~"		{ return creaToken(TipoToken.OPERADOR_UNITARIO, TipoOperadorUnitario.NEGACION); }
	"INTEGER"   { return creaToken(TipoToken.TIPO_SIMPLE, TipoSimple.INTEGER); }
	"REAL"   	{ return creaToken(TipoToken.TIPO_SIMPLE, TipoSimple.REAL); }
	"BOOLEAN"   { return creaToken(TipoToken.TIPO_SIMPLE, TipoSimple.BOOLEAN); }
	"LONGINT"   { return creaToken(TipoToken.TIPO_SIMPLE, TipoSimple.LONGINT); }
	"LONGREAL"  { return creaToken(TipoToken.TIPO_SIMPLE, TipoSimple.LONGREAL); }
	"CHAR"   	{ return creaToken(TipoToken.TIPO_SIMPLE, TipoSimple.CHAR); }
	"CARDINAL"  { return creaToken(TipoToken.TIPO_SIMPLE, TipoSimple.CARDINAL); }
	"BITSET"   	{ return creaToken(TipoToken.TIPO_SIMPLE, TipoSimple.BITSET); }
	"PROC"   	{ return creaToken(TipoToken.TIPO_SIMPLE, TipoSimple.PROC); }
	"TRUE"   	{ return creaToken(TipoToken.CONSTANTE_PREDEFINIDA, ConstantePredefinida.TRUE); }
	"FALSE"   	{ return creaToken(TipoToken.CONSTANTE_PREDEFINIDA, ConstantePredefinida.FALSE); }
	"NIL"   	{ return creaToken(TipoToken.CONSTANTE_PREDEFINIDA, ConstantePredefinida.NIL); }

	{caracter_espacio_blanco}+ { }
	
	{identificador} { 	
	
		/* Nos vamos al siguiente estado */
		yybegin(IDENTIFICADOR); 
		_lexema = yytext();
	}

	{numero_entero} { 
	
		/* Nos vamos al siguiente estado */
		yybegin(NUMERO_ENTERO); 
		_lexema = yytext();
	}

	{numero_real} { 
	
		/* Nos vamos al siguiente estado */
		yybegin(NUMERO_REAL); 
		_lexema = yytext();
	}

	{numero_hexadecimal} {

		/* Nos vamos al siguiente estado */
		yybegin(NUMERO_HEXADECIMAL); 
		_lexema = yytext();		
	}

	{numero_octal} {
	
		/* Nos vamos al siguiente estado */
		yybegin(NUMERO_OCTAL); 
		_lexema = yytext();

	}

	{caracter_en_octal} {
		
		/* Nos vamos al siguiente estado */
		yybegin(CARACTER_EN_OCTAL); 
		_lexema = yytext();	
	}

	/* Cadena o car�cter */
  	\"{texto_cadena_comillas_dobles}\"| \'{texto_cadena_comillas_simples}\' {

		/* Quitamos las comillas (simples o dobles) del lexema */
		String str =  yytext().substring(1, yylength()-1);

		if(str.length() == 1)
			return creaToken(TipoToken.CARACTER, new Character(yytext().charAt(1))); 
		else
			return creaToken(TipoToken.CADENA, str);	
	}

	/* Cadena o car�cter sin cerrar */
  	\"{texto_cadena_comillas_dobles}|\'{texto_cadena_comillas_simples} {

		yybegin(YYINITIAL);
		/* Generamos el error correspondiente */
		TErrorLexico error = new TErrorLexico(ErroresLexicos.ERROR_LEXICO_CADENA_O_CARACTER_MAL_FORMADO, "Cadena o car�cter sin terminar al final de l�nea, falta \" � \'", yyline, yycolumn); 
		_gestorErrores.insertaErrorLexico(error);
	}

	/* Comienzo de comentario. */
	"(*"	{ _numComentariosAbiertos++; yybegin(COMENTARIO); }

} /* Fin de Estado Inicial*/

/* Tratamiento de comentarios anidados */
<COMENTARIO> {
	
	/* Se aumenta el contador de comentarios abiertos */
	"(*" { _numComentariosAbiertos++; }
	
	/* Se disminuye el contador de comentarios abiertos */
	"*)" { 
		if (--_numComentariosAbiertos == 0) 
			yybegin(YYINITIAL); 
	}

	.|{caracter_espacio_blanco} { }	
	
	<<EOF>> {
	
		yybegin(YYINITIAL); 

		/* Generamos el error correspondiente */
		TErrorLexico error = new TErrorLexico(ErroresLexicos.ERROR_LEXICO_COMENTARIO_MAL_FORMADO, "Falta *) para completar el comentario", yyline, yycolumn); 
		_gestorErrores.insertaErrorLexico(error);
	}	
}

/* Falta s�lo el delimitador para reconocer el indentificador */
<IDENTIFICADOR> {  

	/* Con un delimitadoritador aceptamos el identificador */ 
	{delimitador} { 
	
		/* Devolvemos un car�cter a la entrada */
		yypushback(1); 
	
		/* Volvemos al estado inicial */
		yybegin (YYINITIAL);

		/* Devolvemos el token correspondiente */
		return creaToken(TipoToken.IDENTIFICADOR, _lexema);
	}

	/* Si recibimos cualquier cosa que no sea d�gito es un token err�neo */
	[^[:digit:][:jletter:]_]|"�" { 

		/* Nos vamos a un estado en el que ignoramos el resto de caracteres hasta el pr�ximo delimitador*/
		yybegin (SUMIDERO);

		_fuenteDelError = "identificador";
		
		_lexema += yytext();
	}
}

/* Falta s�lo el delimitador para reconocer el n�mero real */
<NUMERO_REAL> {   
	
	/* Con un delimitador aceptamos el n�mero real */ 
	{delimitador} { 
	
		/* Devolvemos el car�cter a la entrada */
		yypushback(1); 
	
		/* Nos vamos al estado inicial */
		yybegin (YYINITIAL);

		/* Devolvemos el n�mero real */
		return creaToken(TipoToken.NUMERO_REAL, new Double(_lexema)); 
	}

	/* Si recibimos otra cosa en este estado es un token err�neo */
	. { 
	
		/* Nos vamos a un estado en el que ignoramos el resto de caracteres hasta el pr�ximo delimitador*/
		yybegin (SUMIDERO);

		_fuenteDelError = "numero_real";

		_lexema += yytext();
	}
}

/* Falta s�lo el delimitador para reconocer el n�mero entero */	
<NUMERO_ENTERO> {   

	/* Con un delimitador aceptamos el n�mero entero */ 
	{delimitador} { 
	
		/* Devolvemos un car�cter a la entrada */
		yypushback(1); 
	
		/* Volvemos al estado inicial */
		yybegin (YYINITIAL);
	
		/* Devolvemos el token entero */
		return creaToken(TipoToken.NUMERO_ENTERO, new Integer(_lexema));
	}

	/* Si recibimos cualquier cosa que no sea d�gito o H es un token err�neo */
	[^[:digit:]] { 
	
		/* Nos vamos a un estado en el que ignoramos el resto de caracteres hasta el pr�ximo delimitador*/
		yybegin (SUMIDERO);

		_fuenteDelError = "numero_entero";

		_lexema += yytext();
	}
}

/* Falta s�lo el delimitador para reconocer el n�mero hexadecimal */	
<NUMERO_HEXADECIMAL> {

	/* Con un delimitador aceptamos el n�mero hexadecimal */ 
	{delimitador} {

		/* Devolvemos el car�cter a la entrada */
		yypushback(1); 
	
		/* Nos vamos al estado inicial */
		yybegin (YYINITIAL);

		/* Realizamos la conversi�n del texto a hexadecimal. */
		String auxiliar = _lexema;
      
		/* Quitamos la "H" */
		auxiliar = auxiliar.substring(0, auxiliar.length() - 1);
      	int traduccionHex = 0;
	
		/* Iremos guardando aqu� las potencias de 16 */
      	int numBase = 1;
     	 	char c;
		int j;
      	for(int i = 0; i < auxiliar.length(); i++){
      		c = auxiliar.charAt(auxiliar.length() - i - 1);
            	j = Character.digit(c, 16);
            	traduccionHex += j * numBase;
            	numBase = numBase * 16;
      	}

     	 	return creaToken(TipoToken.NUMERO_ENTERO, new Integer(traduccionHex));
	}
	
	. {
		/* Nos vamos a un estado en el que ignoramos el resto de caracteres hasta el pr�ximo delimitador*/
		yybegin (SUMIDERO);

		_fuenteDelError = "numero_hexadecimal";

		_lexema += yytext();
	}
}

/* Falta s�lo el delimitador para reconocer el n�mero octal */	
<NUMERO_OCTAL> {

	/* Con un delimitador aceptamos el n�mero octal */ 
	{delimitador} {

		/* Devolvemos el car�cter a la entrada */
		yypushback(1); 
	
		/* Nos vamos al estado inicial */
		yybegin (YYINITIAL);

		/* Realizamos la conversi�n del texto a n�mero octal. */
      
		String auxiliar = _lexema;
      
		/* Quitamos la "B" */
		auxiliar = auxiliar.substring(0, auxiliar.length() - 1);
      
		int traduccionOct = 0;
      
		/* Iremos guardando aqui las potencias de 8 */
		int numBase = 1;
		char c;
		int j;
      
		for(int i = 0; i < auxiliar.length(); i++){
			c = auxiliar.charAt(auxiliar.length() - i - 1);
            	j = Character.digit(c, 8);
            	traduccionOct += j*numBase;
            	numBase = numBase*8;
      	}

		return creaToken(TipoToken.NUMERO_ENTERO, new Integer(traduccionOct));
	}
	
	. {
 		/* Nos vamos a un estado en el que ignoramos el resto de caracteres hasta el pr�ximo delimitador*/
		yybegin (SUMIDERO);

		_fuenteDelError = "numero_octal";

		_lexema += yytext();
	}
}

/* Falta s�lo el delimitador para reconocer el car�cter en octal */	
<CARACTER_EN_OCTAL> {

	/* Con un delimitador aceptamos el car�cter en octal */ 
	{delimitador} {

		/* Devolvemos el car�cter a la entrada */
		yypushback(1); 
	
		/* Nos vamos al estado inicial */
		yybegin (YYINITIAL);

		/* Realizamos la conversi�n de car�cter en octal a car�cter. */
		String entrada = _lexema;
	
		/* Le quitamos la C */
		String[] resultado = entrada.split("C");
	
		int decimal = Integer.parseInt(resultado[0], 8);
		char ch = (char) decimal; 
	
		return creaToken(TipoToken.CARACTER, new Character(ch)); 
	}
	
	. {
		/* Nos vamos a un estado en el que ignoramos el resto de caracteres hasta el pr�ximo delimitador*/
		yybegin (SUMIDERO);

		_fuenteDelError = "caracter_en_octal";

		_lexema += yytext();
	}
}

/* Ignora todos los caracteres err�neos de un token hasta encontrar el siguiente delimitador una vez detectado el error */
<SUMIDERO> {

	{delimitador} {
		
		/* Devolvemos un car�cter a la entrada */
		yypushback(1); 
	
		/* Volvemos al estado inicial */
		yybegin (YYINITIAL);
	
		TErrorLexico error = null;

		/* Generamos el error correspondiente */
		if(_fuenteDelError.matches("numero_entero"))
			error = new TErrorLexico(ErroresLexicos.ERROR_LEXICO_FORMATO_DE_IDENTIFICADOR_NO_VALIDO, _lexema + yytext(), yyline, yycolumn); 
		else if(_fuenteDelError.matches("identificador"))
			error = new TErrorLexico(ErroresLexicos.ERROR_LEXICO_CARACTER_NO_VALIDO_EN_IDENTIFICADOR, _lexema + yytext(), yyline, yycolumn); 
		else if(_fuenteDelError.matches("numero_real"))
			error = new TErrorLexico(ErroresLexicos.ERROR_LEXICO_NUMERO_REAL_MAL_FORMADO, _lexema + yytext(), yyline, yycolumn); 
		else if(_fuenteDelError.matches("numero_hexadecimal"))
			error = new TErrorLexico(ErroresLexicos.ERROR_LEXICO_NUMERO_HEXADECIMAL_MAL_FORMADO, _lexema + yytext(), yyline, yycolumn); 
		else if(_fuenteDelError.matches("numero_octal"))
			error = new TErrorLexico(ErroresLexicos.ERROR_LEXICO_NUMERO_OCTAL_MAL_FORMADO, _lexema + yytext(), yyline, yycolumn); 
		else if(_fuenteDelError.matches("caracter_en_octal"))
			error = new TErrorLexico(ErroresLexicos.ERROR_LEXICO_CARACTER_EN_OCTAL_MAL_FORMADO, _lexema + yytext(), yyline, yycolumn); 
		
		_gestorErrores.insertaErrorLexico(error);
	}

	. { 

		/* Ignora todos los caracteres que vengan */ 
		_lexema += yytext();
	}
}

/* Otro caracter */
.|"�" { 

	TErrorLexico error = new TErrorLexico(ErroresLexicos.ERROR_LEXICO_CARACTER_NO_VALIDO_EN_ESTE_CONTEXTO, yytext(), yyline, yycolumn);
	_gestorErrores.insertaErrorLexico(error);	
}

<<EOF>> {
	
	return creaToken(TipoToken.EOF, "");
}