package modelo

import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {


    fun cadenaConexion(): Connection?{
        try {
            val url="jdbc:oracle:thin:@192.168.56.1:1521:xe"
            val user= "Bryan_Cornejo"
            val contrasena="190906"

            val connection = DriverManager.getConnection(url,user,contrasena)

            return connection
        }catch (e: Exception){
            println("Este es el error: $e")
            return null
        }
    }
}