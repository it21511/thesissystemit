<?php
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST');
header("Access-Control-Allow-Headers: X-Requested-With");

    if($_POST["secret_key"] != "secretkey12345"){
      header("HTTP/1.1 401 Unauthorized");
      exit;
    }
		require 'db.php';

    $studentEmail = $_POST["student_email"];
    $thesisId = $_POST["thesis_id"];

		$query = "replace INTO student_selections VALUES(?,?)";
		$stmt = $db->prepare($query);
		$stmt->execute(array($studentEmail, $thesisId));
?>
