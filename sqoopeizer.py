# coding=utf-8

#Script para ejecutar sqoopeizer de acuerdo a los parametros de entrada del usuario
#Diego Jaramillo Celis
#11/03/2015

import subprocess

print 'Motores de bases de datos, digite: 1 (Oracle), 2 (SQL Server), 3 (iSeries DB2) \n'
vmotor = raw_input ('Digite tipo de motor: ')
try:
	vmotor = int(vmotor)
except:
	print 'debe digitar un tipo valido (1, 2 o 3)'
        raw_input ('Digite tipo de motor: ')
	
if vmotor == 1: #Oracle
        vserver = raw_input ('Digite nombre del servidor: ')
	vport = raw_input ('Digite el número de puerto: ')
	vdbname = raw_input ('Digite nombre de la base de datos: ')
        vuser = raw_input ('Digite nombre del usuario de conexión: ')
        vname = raw_input ('Digite nombre del archivo de salida: ')
	
	vjdbc = 'jdbc:oracle:thin:@'+vserver+':'+vport+':'+vdbname
	
elif vmotor == 2: #SQL Server
	vserver = raw_input ('Digite nombre del servidor: ')
	vport = raw_input ('Digite el número de puerto: ')
	vinstance = raw_input ('Digite nombre de la instancia (si no existe, presione enter): ')
	vdbname = raw_input ('Digite nombre de la base de datos: ')
	vuser = raw_input ('Digite nombre del usuario de conexión: ')
	vname = raw_input ('Digite nombre del archivo de salida: ')
	
	if len(vinstance)==0:
		vjdbc = 'jdbc:sqlserver://'+vserver+':'+vport+';databaseName='+vdbname
	else: 
		vjdbc = 'jdbc:sqlserver://'+vserver+':'+vport+';instanceName='+vinstance+';databaseName='+vdbname
else:
	#if vmotor == 3: #DB2
        vserver = raw_input ('Digite nombre o ip del servidor: ')
	vport = raw_input ('Digite el número de puerto: ')
	vuser = raw_input ('Digite nombre del usuario de conexión: ')
	vname = raw_input ('Digite nombre del archivo de salida: ')

	vjdbc = 'jdbc:as400://'+vserver+':'+vport

print 'A continuación deberá digitar la contraseña del usuario... \n'
subprocess.call(["java", "-jar", "sqoopeizer-0.0.3-SNAPSHOT.jar", vjdbc, vuser, vname])
