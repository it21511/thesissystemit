<html>
  <head>
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
    <style>
      /* Bordered form */
      form {
      border: 3px solid #f1f1f1;
      width: 100%;
      max-width: 500px;
      margin: 0 auto;
      }

      /* Full-width inputs */
      input[type=text], input[type=password] {
      width: 100%;
      padding: 12px 20px;
      margin: 8px 0;
      display: inline-block;
      border: 1px solid #ccc;
      box-sizing: border-box;
      }

      /* Set a style for all buttons */
      button {
      background-color: #4CAF50;
      color: white;
      padding: 14px 20px;
      margin: 8px 0;
      border: none;
      cursor: pointer;
      width: 100%;
      }

      /* Add a hover effect for buttons */
      button:hover {
      opacity: 0.8;
      }

      /* Extra style for the cancel button (red) */
      .cancelbtn {
      width: auto;
      padding: 10px 18px;
      background-color: #f44336;
      }

      /* Center the avatar image inside this container */
      .imgcontainer {
      text-align: center;
      margin: 24px 0 12px 0;
      }

      /* Avatar image */
      img.avatar {
      width: 40%;
      border-radius: 50%;
      }

      /* Add padding to containers */
      .container {
        padding: 16px;
      }

      /* The "Forgot password" text */
      span.psw {
      float: right;
      padding-top: 16px;
      }

      /* Change styles for span and cancel button on extra small screens */
      @media screen and (max-width: 300px) {
      span.psw {
        display: block;
        float: none;
      }
      .cancelbtn {
        width: 100%;
      }
      }
    </style>
  </head>
  <body>
    <form id="loginForm" action="#" method="post">
      <div class="container">
        <label for="email"><b>Email</b></label>
        <input type="text" placeholder="Enter Email" name="email" required>

        <label for="password"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="password" required>

        <button type="submit">Login</button>
      </div>
    </form>
    <script>
      // this is the id of the form
      $("#loginForm").submit(function(e) {

        e.preventDefault(); // avoid to execute the actual submit of the form.

        var form = $(this);

        $.ajax({
               type: "POST",
               url: "api/login.php",
               data: form.serialize(), // serializes the form's elements.
               success: function(returnData)
               {
                 if(returnData==1){
                   window.location.href = "index.php";
                 }
                 else{
                   alert("Wrong username or password");
                 }
               }
             });
      });
    </script>
  </body>
</html>
