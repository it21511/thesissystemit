<?php
    session_start();
		require 'db.php';

    $email = $_POST["student_email"];
    $isUndergraduate = $_POST["is_undergraduate"];

		$query = "select student_selections.*, thesis.name, thesis.student_email as thesis_student_email
              FROM `student_selections` LEFT JOIN thesis ON thesis.id = student_selections.thesis_id
              WHERE professor_id = ?
              AND student_selections.thesis_id NOT IN (SELECT id FROM thesis WHERE student_email IS NOT NULL)
              AND student_selections.student_email NOT IN (SELECT student_email FROM thesis WHERE student_email IS NOT NULL)";

              //first AND is to hide the theses that have already a student selected
              //second AND is to hide the selected student, in case he had selected more than one theses

		$stmt = $db->prepare($query);
		$stmt->execute(array($_SESSION["id"]));

    $data = array();
    $tempData = array();

    $selectedStudents = array();

    $counter = 0;

		while ($result = $stmt->fetchObject()) {
      $data['data'][$counter]["student"] = $result->student_email;
      $data['data'][$counter]["thesis"] = $result->name;
      $data['data'][$counter]["action"] = "<i class='fas fa-check-circle' style='color: green; cursor: pointer;' onclick='SelectStudent(".$result->thesis_id.",\"".$result->student_email."\")'></i>";

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
