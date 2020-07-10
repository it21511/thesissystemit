<?php
    session_start();

		require 'db.php';

		$query = "insert into thesis VALUES(DEFAULT, ?, ?, ?, ?, ?, NULL)";

		$stmt = $db->prepare($query);
		if($stmt->execute(array($_SESSION["id"], $_POST["title"], $_POST["description"], $_POST["keywords"], $_POST["is_undergraduate"]=="on"?1:0))){
      echo 1;
    }
    else{
      echo 0;
    }

		?>
