MODULE prueba1;
CONST
	PI = 3.14;
	palabrota = "Joder";
TYPE
	pene = INTEGER;
	polla = POINTER TO POINTER TO SET OF LONGINT;
	ciruelo = polla;
VAR
	a,b : CHAR;
	c,d :polla;

	MODULE prueba2;
	VAR
		a : ciruelo;
		b,d : REAL;
	END prueba2;
BEGIN

END prueba1. 
