<?php
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST');
header("Access-Control-Allow-Headers: X-Requested-With");

    if($_POST["secret_key"] != "secretkey12345"){
      header("HTTP/1.1 401 Unauthorized");
      exit;
    }
		require 'db.php';

    $email = $_POST["student_email"];
    $isUndergraduate = $_POST["is_undergraduate"];

		$query = "select thesis.*, professor.first_name, professor.last_name, professor.email from thesis
      LEFT JOIN professor ON professor.id = thesis.professor_id
      WHERE thesis.is_undergraduate=?";

		$stmt = $db->prepare($query);
		$stmt->execute(array($isUndergraduate));

    $data = array();

    //Check if user has been selected by a professor
    $querySelected = "select * from thesis WHERE student_email=?";

    $stmtSelected = $db->prepare($querySelected);
    $stmtSelected->execute(array($email));

    $selectedId = -1;
    while ($resultSelected = $stmtSelected->fetchObject()) {
      $selectedId = $resultSelected->id;
    }

    $counter = 0;
		while ($result = $stmt->fetchObject()) {
      $data['data'][$counter]["professor_first_name"] = $result->first_name;
      $data['data'][$counter]["professor_last_name"] = $result->last_name;
      $data['data'][$counter]["email"] = $result->email;
      $data['data'][$counter]["name"] = $result->name;
      $data['data'][$counter]["description"] = $result->description;
      $data['data'][$counter]["keywords"] = $result->keywords;

      //Check if user has selected this thesis (to display the correct symbol)
      $query2 = "select * from student_selections WHERE student_email=? AND thesis_id=?";

  		$stmt2 = $db->prepare($query2);
  		$stmt2->execute(array($email, $result->id));

      $counter2 = 0;
      while ($result2 = $stmt2->fetchObject()) {
        $counter2++;
      }

      if($selectedId == -1){ //User has not been selected to any thesis yet
        if($counter2 == 0){ //User has not selected this thesis
          $data['data'][$counter]["action"] = '<i class="far fa-square" style="color: blue; cursor: pointer;" onclick="SelectThesis(this,'.$result->id.');"></i></a>';
        }
        else{
          $data['data'][$counter]["action"] = '<i class="far fa-check-square" style="color: blue; cursor: pointer;" onclick="SelectThesis(this,'.$result->id.');"></i></a>';
        }
      }
      else{ //User has been selected to a thesis
        if($selectedId == $result->id){ //This is the thesis that the user has been selected
          $data['data'][$counter]["action"] = '<i class="far fa-check-square" style="color: blue; cursor: pointer;"></i></a>';
        }
        else{
          $data['data'][$counter]["action"] = "";
        }
      }

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
