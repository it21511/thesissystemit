<?php

		require 'db.php';

		$query = "select * from professor WHERE email=? AND password=?";

		$stmt = $db->prepare($query);

		$stmt->execute(array($_POST["email"], $_POST["password"]));

    $data = array();

    $counter = 0;
		while ($result = $stmt->fetchObject()) {
      $counter++;
      $id = $result->id;
		}

    if($counter > 0){ //Found the user
      session_start();

      $_SESSION["loggedin"] = true;
      $_SESSION["email"] = $_POST["email"];
      $_SESSION["id"] = $id;
      echo 1;
    }
    else{
      echo 0;
    }

		?>
