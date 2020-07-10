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
    <h3 style="text-align: center;">My Theses</h3>
    <div class="container" style="margin-top: 25px;">
      <table id="thesisTable" class="table table-striped table-bordered">
  			<thead>
    			<tr>
    				<th>Title</th>
    				<th>Description</th>
    				<th>Keywords</th>
    				<th>Is for Undergraduates</th>
            <th>Selected Student</th>
            <th>Action</th>
    			</tr>
        </thead>
  			<tbody>

  			</tbody>
  		</table>
    </div>
    <script>
      function Delete(id){
        var http = new XMLHttpRequest();
        var url = 'api/deleteThesis.php';
        var params = 'thesis_id='+encodeURI(id);
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

        $(document).ready( function () {
          $('#thesisTable').DataTable({
              "dom": 'Bflrtip',
              "order": [[0,"asc"]],
                "language": {
                    "emptyTable": "You haven't submitted any theses yet"
                  },
                  "ajax": {
                      "url": "api/getProfessorTheses.php",
                      "type": "POST"
                  },
                  "columns": [
                      { "data": "title" },
                      { "data": "description" },
                      { "data": "keywords" },
                      { "data": "is_undergraduate" },
                      { "data": "student" },
                      { "data": "action" }
                  ],
                  "columnDefs": [
                    {
                        "targets": 4,
                        "className": 'dt-body-center'
                    }
                  ]
          });

      } );
    </script>
  </body>
</html>
