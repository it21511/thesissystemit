<?php
    session_start();
		require 'db.php';

		$query = "select thesis.*, professor.first_name, professor.last_name, professor.email from thesis
      LEFT JOIN professor ON professor.id = thesis.professor_id
      WHERE thesis.professor_id=?";

		$stmt = $db->prepare($query);
		$stmt->execute(array($_SESSION["id"]));

    $data = array();

    $counter = 0;
		while ($result = $stmt->fetchObject()) {
      $data['data'][$counter]["title"] = $result->name;
      $data['data'][$counter]["description"] = $result->description;
      $data['data'][$counter]["keywords"] = $result->keywords;
      $data['data'][$counter]["is_undergraduate"] = $result->is_undergraduate==1?"Yes":"No";
      $data['data'][$counter]["student"] = $result->student_email;
      $data['data'][$counter]["action"] = '<i class="fas fa-trash-alt" style="cursor: pointer; color: red;" onclick="Delete('.$result->id.');"></i>';

      $counter++;
		}

    if (count($data) == 0) {
        $data['iTotalRecords']     = "0";
        $data['data']             = array();
    } else {
        $data['iTotalRecords']     = $counter;
    }

		echo json_encode($data);

		?>
