<?php
     
    require 'database.php';
 
    if ( !empty($_POST)) {
       // keep track post values
	   $giong = $_POST['giong'];
	   $id = $_POST['id'];
	   $gioitinh = $_POST['gioitinh'];
	   $ngaytuoi = $_POST['ngaytuoi'];
	   $cannang = $_POST['cannang'];
        
		// insert data
		
        $pdo = Database::connect();
		$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		$sql = "INSERT INTO table_pigdata_mysql (giong,id,gioitinh,ngaytuoi,cannang) values(?, ?, ?, ?, ?)";
		$q = $pdo->prepare($sql);
		$q->execute(array($giong,$id,$gioitinh,$ngaytuoi,$cannang));
		Database::disconnect();
		header("Location: user data.php");
    }
?>