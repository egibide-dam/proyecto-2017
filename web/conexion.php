<?php
	$servername = "85.214.126.89:3306";
	$username = "root";
	$password = "toor";

	// Create connection
	$conn = new mysqli($servername, $username, $password);

	// Check connection
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	} 	
?>