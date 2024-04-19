<?php
    require 'database.php';
 
    $id = null;
    if ( !empty($_GET['id'])) {
        $id = $_REQUEST['id'];
    }
     
    if ( !empty($_POST)) {
        // keep track post values
        $giong = $_POST['giong'];
		$id = $_POST['id'];
		$gioitinh = $_POST['gioitinh'];
        $ngaytuoi = $_POST['ngaytuoi'];
        $cannang = $_POST['cannang'];
         
        $pdo = Database::connect();
		$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		$sql = "UPDATE table_pigdata_mysql  set giong = ?, gioitinh =?, ngaytuoi =?, cannang =? WHERE id = ?";
		$q = $pdo->prepare($sql);
		$q->execute(array($giong,$gioitinh,$ngaytuoi,$cannang,$id));
		Database::disconnect();
		header("Location: user data.php");
    }
?>