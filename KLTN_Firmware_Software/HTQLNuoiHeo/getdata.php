<?php
	$connect = mysqli_connect("localhost","root","","pigdata_mysql");
	mysqli_query($connect,"SET NAMES 'utf8'");
	
	$query = "SELECT * FROM table_pigdata_mysql";
	
	$data = mysqli_query($connect,$query);
	

	$mangHeo = array();
	
	while($row = mysqli_fetch_assoc($data)){
		$mangHeo[] = $row;
	}
	
	echo json_encode($mangHeo);
?>