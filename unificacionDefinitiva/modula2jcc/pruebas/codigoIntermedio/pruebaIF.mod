MODULE pruebaIF;
VAR
	a,b,c:INTEGER;
BEGIN
	IF (TRUE) THEN 
		a:=2+3; 
	END;
	b:=3*6;
	IF (FALSE) THEN 
		a:=3+1; 
		b:=6*3;
		IF (TRUE) THEN
			c:=3*5;
		END;
	END; 

END pruebaIF.
