
(*  este programa prueba la declaracion de procedimientos
 *  así como la inclusión de sus variables y parámetros en
 *  un nuevo ambito 
 *)

MODULE testProc;
VAR
	a,b:INTEGER;

     PROCEDURE primero;
     VAR
         a, b : INTEGER;

         PROCEDURE interior;
         BEGIN
         END interior
        
     BEGIN
     END primero;


     PROCEDURE segundo (a: INTEGER, VAR b:INTEGER);
     VAR
         e, f : INTEGER;
     BEGIN
     END segundo;

     PROCEDURE tercero (a: INTEGER, b : INTEGER): INTEGER;
     VAR
         e, f : INTEGER;
     BEGIN
     END segundo;

BEGIN

END testProc.
