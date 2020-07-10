<?php
  session_start();
  if(!isset($_SESSION["loggedin"])){
    header("Location: login.php");
    exit;
  }
 ?>

 <!DOCTYPE html>
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
   <style>
   form {
   width: 100%;
   max-width: 500px;
   margin: 0 auto;
   border-radius: 5px;
   background-color: #f2f2f2;
   padding: 20px;
   }

   input[type=text], select {
     width: 100%;
     padding: 12px 20px;
     margin: 8px 0;
     display: inline-block;
     border: 1px solid #ccc;
     border-radius: 4px;
     box-sizing: border-box;
   }

   input[type=submit] {
     width: 100%;
     background-color: #4CAF50;
     color: white;
     padding: 14px 20px;
     margin: 8px 0;
     border: none;
     border-radius: 4px;
     cursor: pointer;
   }

   input[type=submit]:hover {
     background-color: #45a049;
   }
   </style>
 </head>
 <body>
   <?php include("topNav.php"); ?>
   <h3 style="text-align: center;">Submit new Thesis</h3>

   <div>
     <form id="thesisForm" action="#">
       <label for="title">Title</label>
       <input type="text" id="title" name="title" placeholder="Thesis title..." required>

       <label for="description">Description</label>
       <input type="text" id="description" name="description" placeholder="Thesis description..." required>

       <label for="keywords">Keywords</label>
       <input type="text" id="keywords" name="keywords" placeholder="Thesis keywords..." required>

       <label for="keywords">Is for undergraduate students?</label><br>
       <input type="checkbox" id="is_undergraduate" name="is_undergraduate">

       <input type="submit" value="Submit">
     </form>
   </div>
   <script>
     // this is the id of the form
     $("#thesisForm").submit(function(e) {

       e.preventDefault(); // avoid to execute the actual submit of the form.

       var form = $(this);

       $.ajax({
              type: "POST",
              url: "api/submitThesis.php",
              data: form.serialize(), // serializes the form's elements.
              success: function(data)
              {
                if(data==1){
                  alert("Thesis submitted successfully");
                  window.location.href = "thesisList.php";
                }
                else{
                  alert("An error occured");
                }
              }
            });


     });
   </script>
   </body>
 </html>
