<?php

    session_start();

		require 'db.php';

    $thesisId = $_POST["thesis_id"];

		$query = "delete from thesis WHERE id=? AND professor_id=?";

		$stmt = $db->prepare($query);
		$stmt->execute(array($thesisId, $_SESSION["id"]));

		echo 1;

		?>
