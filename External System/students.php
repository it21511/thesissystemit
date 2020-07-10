<?php
  session_start();
  if(!isset($_SESSION["loggedin"])){
    header("Location: login.php");
    exit;
  }
 ?>
<html>
  <head>
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>

	  <!-- Reference Bootstrap files -->
  	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

  	<script src="https://kit.fontawesome.com/6626873403.js" crossorigin="anonymous"></script>


  	<script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
  	<link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css"></link>
  </head>
  <body>
    <?php include("topNav.php"); ?>
    <h3 style="text-align: center;">Students' Selections</h3>
    <div class="container" style="margin-top: 25px;">
      <table id="thesisTable" class="table table-striped table-bordered">
  			<thead>
    			<tr>
    				<th>Student</th>
    				<th>Thesis</th>
    				<th>Action</th>
    			</tr>
        </thead>
  			<tbody>

  			</tbody>
  		</table>
    </div>
    <script>

        $(document).ready( function () {
          $('#thesisTable').DataTable({
              dom: 'Bflrtip',
              "order": [[1,'DESC']],
              "language": {
                    "emptyTable": "No student has selected any of your theses"
                  },
              "ajax": {
                      "url": "api/getMyStudents.php",
                      "type": "POST"
                  },
                  "columns": [
                      { "data": "student" },
                      { "data": "thesis" },
                      { "data": "action" }
                  ]
          });

      } );

      function SelectStudent(id, student_email){
        var http = new XMLHttpRequest();
        var url = 'api/selectStudent.php';
        var params = 'student_email='+encodeURI(student_email)+'&thesis_id='+encodeURI(id);
        http.open('POST', url, true);

        //Send the proper header information along with the request
        http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');

        http.onreadystatechange = function() {//Call a function when the state changes.
            if(http.readyState == 4 && http.status == 200) {
                window.location.reload();
            }
        }
        http.send(params);
      }
    </script>
  </body>
</html>
