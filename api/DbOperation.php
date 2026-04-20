<?php
	class DbOperation {

		private $conn;

		function DbOperation(){
			require_once "DbConnect.php";

			$db = new DbConnect();
			$this->conn = $db->connect();
		}


		function getSong($id){
			//Consulta con la que recogeré todos los datos de la canción, excepto los del aartista que los trataré a parte
			//$id =1;//Para comprobar resultados
			$stmt = mysqli_prepare($this->conn, "SELECT song.id_song, song.id_album, song.id_author, song.uri, song.title, song.genere, song.duration FROM song WHERE id_song = ?");
			if (!$stmt) {//Compruebo que no hay fallos en el stmt_prepare
                echo "Falló la preparación: (" . $stmt->errno . ") " . $stmt->error;
            }
        	mysqli_stmt_bind_param($stmt, "i", $id);//variable que paso por parámetro en la query
        	mysqli_stmt_execute($stmt);
        	if(!$stmt->execute()){//Compruebo que no hay fallos al ejecutar
				echo "Falló la ejecución: (" . $stmt->errno . ") " . $stmt->error;
			}
            mysqli_stmt_bind_result($stmt, $idSong, $idAlbum, $idAuthor, $uriSong, $title, $genereSong, $duration);
            
			if(mysqli_stmt_fetch($stmt)) {
				$arr = array();

				$arr['idSong'] = $idSong; 
				$arr['idAlbum'] = $idAlbum; 
				$arr['idAuthor'] = $idAuthor; 
				$arr['uriSong'] = $uriSong;
				$arr['title'] = $title;
				$arr['genereSong'] = $genereSong;
				$arr['duration'] = $duration;

				//echo "<br>".$arr['idSong']."->".$arr['title']."<br>";
				return $arr;
			}else{
				echo "No results were found";
				return null;//devuelvo un valor vacio
			}
			mysqli_free_result("$stmt");
			mysqli_stmt_close("$stmt");
		}
		
		function getArtist($idArtist){
		    $stmt = mysqli_prepare($this->conn, "SELECT artist.id_artist, artist.name, artist.date FROM artist WHERE artist.id_artist = ?");
    		if (!$stmt) {//Compruebo que no hay fallos en el stmt_prepare
                echo "Falló la preparación: (" . $stmt->errno . ") " . $stmt->error;
            }
            mysqli_stmt_bind_param($stmt, "i", $idArtist);
    		mysqli_stmt_execute($stmt);
            if(!$stmt->execute()){//Compruebo que no hay fallos al ejecutar
				echo "Falló la ejecución: (" . $stmt->errno . ") " . $stmt->error;
			}
    		mysqli_stmt_bind_result($stmt, $idArtist, $artistName, $artistDate);
    		
    		if(mysqli_stmt_fetch($stmt)) {
				$arr = array();

				$arr['idArtist'] = $idArtist; 
				$arr['artistName'] = $artistName;
				$arr['artistDate'] = $artistDate;

				//echo "<br>".$arr['idArtist']."->".$arr['artistName']."<br>";
				return $arr;
				///////////Los artistas y autores los recogeré en otro método//////////////////////////////
			}else{
				echo "No results were found";
				return null;//devuelvo un valor vacio
			}
			mysqli_free_result("$stmt");
			mysqli_stmt_close("$stmt");
		}
		
		function getListArtist($idAuthor){
		    $stmt = mysqli_prepare($this->conn, "SELECT author.id_artist, author.id_author, artist.name FROM author INNER JOIN artist ON author.id_artist = artist.id_artist WHERE author.id_author = ?");
    		if (!$stmt) {//Compruebo que no hay fallos en el stmt_prepare
                echo "Falló la preparación: (" . $stmt->errno . ") " . $stmt->error;
            }
            mysqli_stmt_bind_param($stmt, "i", $idAuthor);
    		mysqli_stmt_execute($stmt);
            if(!$stmt->execute()){//Compruebo que no hay fallos al ejecutar
				echo "Falló la ejecución: (" . $stmt->errno . ") " . $stmt->error;
			}
    		mysqli_stmt_bind_result($stmt, $idArtist, $idAuthor, $artistName);
    		
    		$arr = array();
    		while(mysqli_stmt_fetch($stmt)) {
				$arrdata = array();

				$arrdata['idArtist'] = $idArtist; 
				$arrdata['idAuthor'] = $idAuthor;
				$arrdata['artistName'] = $artistName;

				//echo "<br>".$arr['idArtist']."->".$arr['artistName']."<br>";
				array_push($arr, $arrdata);
			}
			return $arr;
			mysqli_free_result("$stmt");
			mysqli_stmt_close("$stmt");
		}
		
		function getAlbumList($idArtist){//Falta de pasar las fotos al json
		    $stmt = mysqli_prepare($this->conn, 
		    "SELECT album.id_album, album.name FROM author
		    INNER JOIN song ON author.id_author = song.id_author 
		    INNER JOIN album ON song.id_album = album.id_album 
		    WHERE author.id_artist = ? AND author.id_author = ?");
    		if (!$stmt) {//Compruebo que no hay fallos en el stmt_prepare
                echo "Falló la preparación: (" . $stmt->errno . ") " . $stmt->error;
            }
            mysqli_stmt_bind_param($stmt, "ii", $idArtist, $idArtist);
    		mysqli_stmt_execute($stmt);
            if(!$stmt->execute()){//Compruebo que no hay fallos al ejecutar
				echo "Falló la ejecución: (" . $stmt->errno . ") " . $stmt->error;
			}
    		mysqli_stmt_bind_result($stmt, $idAlbum, $albumName);
    		
    		$arr = array();
    		while(mysqli_stmt_fetch($stmt)) {
				$arrdata = array();

				$arrdata['idAlbum'] = $idAlbum; 
				$arrdata['albumName'] = $albumName;

				//echo "<br>".$arr['idArtist']."->".$arr['artistName']."<br>";
				array_push($arr, $arrdata);
			}
				return $arr;
			mysqli_free_result("$stmt");
			mysqli_stmt_close("$stmt");
		}
		
		function getAlbum($idAlbum){
			//Consulta con la que recogeré todos los datos de la canción, excepto los del aartista que los trataré a parte
			//$id =1;//Para comprobar resultados
			$stmt = mysqli_prepare($this->conn, "SELECT album.id_album, album.name, album.release, album.genere, album.img FROM album WHERE album.id_album = ?");
			if (!$stmt) {//Compruebo que no hay fallos en el stmt_prepare
                echo "Falló la preparación: (" . $stmt->errno . ") " . $stmt->error;
            }
        	mysqli_stmt_bind_param($stmt, "i", $idAlbum);//variable que paso por parámetro en la query
        	mysqli_stmt_execute($stmt);
        	if(!$stmt->execute()){//Compruebo que no hay fallos al ejecutar
				echo "Falló la ejecución: (" . $stmt->errno . ") " . $stmt->error;
			}
			
            mysqli_stmt_bind_result($stmt, $idAlbum, $albumName, $release, $albumGenere, $albumImg);
            
			if(mysqli_stmt_fetch($stmt)) {
				$arr = array();

				$arr['idAlbum'] = $idAlbum; 
				$arr['albumName'] = $albumName;
				$arr['release'] = $release;
				$arr['albumGenere'] = $albumGenere;
				$arr['albumImg'] = $albumImg;

				//echo "<br>".$arr['idAlbum']."->".$arr['albumName']."<br>";
				return $arr;
			}else{
				echo "No results were found";
				return null;//devuelvo un valor vacio
			}
			mysqli_free_result("$stmt");
			mysqli_stmt_close("$stmt");
		}
		
		function searching($str, $num){
		    
		    $num = ($num * 12) + $num;//número desde donde empiezo a coger resultados de la query
			$stmt = mysqli_prepare($this->conn, 
			    "SELECT song.id_song, song.title, 'song' FROM song WHERE song.title LIKE CONCAT(?,'%')
                UNION
                SELECT album.id_album, album.name, 'album' FROM album WHERE album.name LIKE CONCAT(?,'%')
                UNION
                SELECT artist.id_artist, artist.name, 'artist' FROM artist WHERE artist.name LIKE CONCAT(?,'%')
                LIMIT ?, 12");
                
			if (!$stmt) {//Compruebo que no hay fallos en el stmt_prepare
                echo "Falló la preparación: (" . $stmt->errno . ") " . $stmt->error;
            }
            
        	mysqli_stmt_bind_param($stmt, "sssi", $str, $str, $str, $num);//variable que paso por parámetro en la query
        	mysqli_stmt_execute($stmt);
        	if(!$stmt->execute()){//Compruebo que no hay fallos al ejecutar
				echo "Falló la ejecución: (" . $stmt->errno . ") " . $stmt->error;
			}
			
            mysqli_stmt_bind_result($stmt, $id, $name, $we);//$we es What ever(para saber si song, album o artist)
            
			//if(mysqli_stmt_fetch($stmt)) {
			$arr = array();
				
            while($stmt->fetch()){
                $arrdata = array();
        		$arrdata['id'] = $id; 
        		$arrdata['name'] = $name;
        		$arrdata['we'] = $we;
        			
        		//echo "<br>".$arrdata['id']."->".$arrdata['name']."->".$arrdata['we']."<br>";
        		array_push($arr, $arrdata);
    		}
				
            return $arr;
			mysqli_free_result("$stmt");
			mysqli_stmt_close("$stmt");
		}
		
		function getAlbumSongs($idAlbum){
		    
			$stmt = mysqli_prepare($this->conn, 
			    "SELECT song.id_song, song.title FROM song INNER JOIN album ON song.id_album = album.id_album WHERE album.id_album = ?");
                
			if (!$stmt) {//Compruebo que no hay fallos en el stmt_prepare
                echo "Falló la preparación: (" . $stmt->errno . ") " . $stmt->error;
            }
            
        	mysqli_stmt_bind_param($stmt, "i", $idAlbum);//variable que paso por parámetro en la query
        	mysqli_stmt_execute($stmt);
        	if(!$stmt->execute()){//Compruebo que no hay fallos al ejecutar
				echo "Falló la ejecución: (" . $stmt->errno . ") " . $stmt->error;
			}
			
            mysqli_stmt_bind_result($stmt, $id, $title);//$we es What ever(para saber si song, album o artist)
            
			//if(mysqli_stmt_fetch($stmt)) {
			$arr = array();
				
            while($stmt->fetch()){
                $arrdata = array();
        		$arrdata['id'] = $id; 
        		$arrdata['title'] = $title;
        			
        		//echo "<br>".$arrdata['id']."->".$arrdata['title']."<br>";
        		array_push($arr, $arrdata);
    		}
				
            return $arr;
			mysqli_free_result("$stmt");
			mysqli_stmt_close("$stmt");
		}
		
		function getNumRegisters(){
			//Consulta con la que recogeré todos los datos de la canción, excepto los del aartista que los trataré a parte
			//$id =1;//Para comprobar resultados
			$stmt = mysqli_prepare($this->conn, "SELECT COUNT(*) FROM song;");
			if (!$stmt) {//Compruebo que no hay fallos en el stmt_prepare
                echo "Falló la preparación: (" . $stmt->errno . ") " . $stmt->error;
            }
        	mysqli_stmt_execute($stmt);
        	if(!$stmt->execute()){//Compruebo que no hay fallos al ejecutar
				echo "Falló la ejecución: (" . $stmt->errno . ") " . $stmt->error;
			}
            mysqli_stmt_bind_result($stmt, $numRegisters);
            
			if(mysqli_stmt_fetch($stmt)) {
				//echo "<br>".$arr['idSong']."->".$arr['title']."<br>";
				return $numRegisters;
			}else{
				echo "No results were found";
				return null;//devuelvo un valor vacio
			}
			mysqli_free_result("$stmt");
			mysqli_stmt_close("$stmt");
		}
	}
	
//$_POST["str"];
//$_POST["idAuthor"];
//$prueba = new DbOperation();
//$result = $prueba->searching("d",0);
//$result = $prueba->getListArtist(2);
//$jsonF = json_encode($result);
//echo json_encode($result);
//echo $jsonF."</br>";
//var_dump(json_decode($jsonF));
	/*SELECT artist.id_artist, artist.name, artist.date, song.id_song, song.title, song.id_album, song.genere FROM artist INNER JOIN author ON artist.id_artist = author.id_artist INNER JOIN song ON author.id_song = song.id_song WHERE song.id_song = 4;*/
?>