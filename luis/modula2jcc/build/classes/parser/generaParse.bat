@ECHO OFF
REM Generador de Gramaticas con SLK
ECHO Lanzador de SLK v0.1

IF NOT EXIST %1 echo Uso incorrecto o fichero no valido
IF NOT EXIST %1 echo Uso: generaParser.bat fichero
IF NOT EXIST %1 echo 	fichero: gramatica a generar

IF EXIST %1 echo Generando gramatica del fichero %1
IF EXIST %1 slk %1 -j -i -k=2