<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
   <c:url value="../user/list" var="userUrl"/>
   <c:url value="../student/list" var="studentUrl"/>
   <c:url value="../student/thesis" var="thesesUrl"/>
   <head>
      <title>List Theses</title>
      <!-- reference our style sheet -->
      <link type="text/css"
         rel="stylesheet"
         href="${pageContext.request.contextPath}/resources/css/style.css" />
      <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
      <!-- Reference Bootstrap files -->
      <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
      <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
      <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
      <script src="https://kit.fontawesome.com/6626873403.js" crossorigin="anonymous"></script>
      <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
      <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css">
      </link>
   </head>
   <body>
      <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
         <a class="navbar-brand" href="#">Internal Thesis System</a>
         <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
         <span class="navbar-toggler-icon"></span>
         </button>
         <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
               <li class="nav-item active">
                  <a class="nav-link" href="/internal_thesis_system">Home <span class="sr-only">(current)</span></a>
               </li>
               <li class="nav-item dropdown">
                  <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                  Actions
                  </a>
                  <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                     <security:authorize access="hasAnyRole('ADMIN')">
                     	<a class="dropdown-item" href="${userUrl}">User's List</a>
                     </security:authorize>
                     <security:authorize access="hasAnyRole('SECRETARY', 'ADMIN')">
                     	<a class="dropdown-item" href="${studentUrl}">Student's List</a>
                     </security:authorize>
                     <security:authorize access="hasAnyRole('STUDENT')">
                     	<a class="dropdown-item" href="${thesesUrl}">Theses List</a>
                     </security:authorize>
                  </div>
               </li>
            </ul>
            <form:form action="${pageContext.request.contextPath}/logout" 
               method="POST">
               <input type="submit" value="Logout" class="btn btn-outline-danger my-2 my-sm-0" />
            </form:form>
         </div>
      </nav>
      <div id="wrapper">
         <div id="header">
            <h2>Available theses: </h2>
         </div>
      </div>
      <div id="container">
         <div id="content" style="text-align: center;">
            <p>
               User: 
               <b>
                  <security:authentication property="principal.username" />
               </b>
            </p>
            <p>
               Role: 
               <b>
                  <security:authentication property="principal.authorities" />
               </b>
            </p>
            <table id="thesisTable" class="table table-dark table-bordered" style="color: #ac6bfb;">
               <thead>
                  <tr>
                     <th>Professor First Name</th>
                     <th>Professor Last Name</th>
                     <th>Professor Email</th>
                     <th>Title</th>
                     <th>Description</th>
                     <th>Keywords</th>
                     <th>Action</th>
                  </tr>
               </thead>
               <tbody>
                
                  
               </tbody>
            </table>
         </div>
      </div>
      <p></p>
      <script>
         var email = "${email}";
         var isUndergraduate = "${is_undergraduate}";
         var postData = {'secret_key':'secretkey12345', 'student_email':email, 'is_undergraduate':isUndergraduate};
         
         $(document).ready( function () {
             $('#thesisTable').DataTable({
             		dom: 'Bflrtip',
             		"order": [[2,'DESC']],
             		"language": {
             		      "emptyTable": "No theses found for you at this time"
             		    },
             		"ajax": {
                         "url": "https://thesissystemit.alwaysdata.net/api/getTheses.php",
                         "type": "POST",
                         "data": postData
                     },
                     "columns": [
                         { "data": "professor_first_name" },
                         { "data": "professor_last_name" },
                         { "data": "email" },
                         { "data": "name" },
                         { "data": "description" },
                         { "data": "keywords" },
                         { "data": "action" }
                     ]
             });
             
         } );
         
         function SelectThesis(element, thesisId){ //Id sent from external system API
         	if(element.className == "far fa-square"){ //Empty square means it's not selected
         		element.className = "far fa-check-square";
         		Select(thesisId);
         	}
         	else{ //It is selected
         		element.className = "far fa-square";
         		Deselect(thesisId);
         	}
         }
         
         function Select(thesisId){
         	var http = new XMLHttpRequest();
         	var url = 'https://thesissystemit.alwaysdata.net/api/selectThesis.php';
         	var params = 'secret_key=secretkey12345&student_email='+encodeURI(email)+'&thesis_id='+thesisId;
         	http.open('POST', url, true);
         
         	//Send the proper header information along with the request
         	http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
         
         	http.onreadystatechange = function() {//Call a function when the state changes.
         	    if(http.readyState == 4 && http.status == 200) {
         	    //    alert(http.responseText);
         	    }
         	}
         	http.send(params);
         }
         function Deselect(thesisId){
         	var http = new XMLHttpRequest();
         	var url = 'https://thesissystemit.alwaysdata.net/api/deselectThesis.php';
         	var params = 'secret_key=secretkey12345&student_email='+encodeURI(email)+'&thesis_id='+thesisId;
         	http.open('POST', url, true);
         
         	//Send the proper header information along with the request
         	http.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
         
         	http.onreadystatechange = function() {//Call a function when the state changes.
         	    if(http.readyState == 4 && http.status == 200) {
         	    //    alert(http.responseText);
         	    }
         	}
         	http.send(params);
         }
      </script>
   </body>
</html>