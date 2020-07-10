<?php
		require 'db.php';

    $studentEmail = $_POST["student_email"];
    $thesisId = $_POST["thesis_id"];

		$query = "update thesis SET student_email = ? where id = ?";
		$stmt = $db->prepare($query);
		$stmt->execute(array($studentEmail, $thesisId));
		
		$query = "select * from thesis WHERE id=?";

		$stmt = $db->prepare($query);

		$stmt->execute(array($thesisId));

		while ($result = $stmt->fetchObject()) {
			mail($studentEmail, "You have been selected!", "Your thesis is ".$result->name."!");
		}
?>
