<?php 
	//Class DbConnect
	class DbConnect {
		//Variable que almacena la conexión a la BBDD
		private $conn;
	 
		//Método para conectarnos 
		function connect() {
			//Incluimos el fichero con las constantes
			require_once /*dirname(__FILE__) . */"Constants.php";
	    
			//connecting to mysql database
			$this->conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);
			//echo "Datos del host: ". mysqli_get_host_info();
			
			 if ($this->conn -> connect_errno){//connect_errno Devuelve el código de error de la última llamada  
			    echo "Falló la conexión a MySQL: (".$this->conn -> connect_errno.")".$this->conn -> connect_error;  
			 }
            /*
			//Nos conectamos y comprobamos si se produce algun error
			if (!$this->conn) {
			    if (mysqli_connect_errno()){
                  echo "Failed to connect to MySQL: " . mysqli_connect_error();
                }
			    die("Connection failed: " . mysqli_connect_error());
			}*/
			//echo "Connected successfully";
	 		
			return $this->conn;//Devolvemos el objeto con la conexión
		}
	}
?>