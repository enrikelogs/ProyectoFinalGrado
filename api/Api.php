<?php
require_once "DbOperation.php";


$result = array();

if (isset($_GET["apicall"])) {

	switch ($_GET["apicall"]) {
		case 'getSong':
			$db = new DbOperation();
			$result = $db->getSong($_POST["idSong"]); 
			break;
		case 'getArtist':
			$db = new DbOperation();
			$result = $db->getArtist($_POST["idArtist"]);  
			break;
		case 'getListArtist':
			$db = new DbOperation();
			$result = $db->getListArtist($_POST["idAuthor"]);  
			break;
		case 'getAlbumList':
			$db = new DbOperation();
			$result = $db->getAlbumList($_POST["idArtist"]);  
			break;
		case 'getAlbum':
			$db = new DbOperation();
			$result = $db->getAlbum($_POST["idAlbum"]);  
			break;
		case 'getAlbumSongs':
			$db = new DbOperation();
			$result = $db->getAlbumSongs($_POST["idAlbum"]);  
			break;
		case 'searching':
			$db = new DbOperation();
			$result = $db->searching($_POST["str"],$_POST["num"]);  
			break;
		case 'getNumRegisters':
		    $db = new DbOperation();
			$result = $db->getNumRegisters();
		    break;
		default:
			# code...
			break;
	}
}else{

}

echo json_encode($result);
?>