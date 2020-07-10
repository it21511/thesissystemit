<?php
// Fill in all the info we need to connect to the database.
// This is the same info you need even if you're using the old mysql_ library.
$host = 'mysql-thesissystemit.alwaysdata.net';
$port = 3306; // This is the default port for MySQL
$database = 'thesissystemit_external';
$db_username = '207918_external';
$db_password = 'thesis_system12345';

// Construct the DSN, or "Data Source Name".  Really, it's just a fancy name
// for a string that says what type of server we're connecting to, and how
// to connect to it.  As long as the above is filled out, this line is all
// you need :)
$dsn = "mysql:host=$host;port=$port;dbname=$database";

// Connect!
try {
	$db = new PDO("mysql:host=$host;dbname=$database", $db_username, $db_password);
	$db->exec("SET time_zone='UTC';");
	$db->exec("SET NAMES utf8;");
	$db->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
	$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	$db->setAttribute(PDO::MYSQL_ATTR_USE_BUFFERED_QUERY, true);
}
catch(PDOException $e)
{
	echo $e->getMessage();
}

?>
