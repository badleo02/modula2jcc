MODULE pruebaREPEAT;
VAR
	a,b:INTEGER;
BEGIN
	REPEAT
		a:=a+1;
		a:=a+2;
		IF (TRUE) THEN
			b:=(6*9)-3;
		END;
		b:= 9*2;
	UNTIL (FALSE);

END pruebaREPEAT.