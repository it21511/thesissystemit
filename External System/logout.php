<?php
  session_start();
  unset($_SESSION["loggedin"]);
  unset($_SESSION["email"]);
  unset($_SESSION["id"]);
  header("Location: login.php");
 ?>
