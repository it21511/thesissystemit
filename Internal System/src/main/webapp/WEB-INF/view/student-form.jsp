<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<c:url value="../user/list" var="userUrl"/>
   <c:url value="../student/list" var="studentUrl"/>
   <c:url value="../student/thesis" var="thesesUrl"/>
   <head>
      <title>Save Student</title>
      <link type="text/css"
         rel="stylesheet"
         href="${pageContext.request.contextPath}/resources/css/style.css">
      <link type="text/css"
         rel="stylesheet"
         href="${pageContext.request.contextPath}/resources/css/add-student-style.css">
               <script src="https://code.jquery.com/jquery-3.3.1.js"></script>
      <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
      <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
      <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
      <script src="https://kit.fontawesome.com/6626873403.js" crossorigin="anonymous"></script>
      <script src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"></script>
      <link rel="stylesheet" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css">
   </head>
   <body>
   <nav class="navbar navbar-expand-lg navbar-dark bg-dark" style="position: sticky; top: 0; z-index: 10;">
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
               <input type="submit" value="Logout" class="btn btn-outline-danger my-2 my-sm-0" style="width: 75px;"/>
            </form:form>
         </div>
      </nav>
      <div id="wrapper">
         <div id="container">
            <div id="header">
               <h2></h2>
            </div>
         </div>
      </div>
      <div id="container">
         <div id="content">
            <% 
               String isUpdate = (String)request.getAttribute("isUpdate"); 
               String action = (isUpdate=="0"?"saveStudent":"updateStudent");
               String title = (isUpdate=="0"?"Save Student":"Update Student");
               %>
			<a href="${pageContext.request.contextPath}/student/list"><i class="fas fa-arrow-circle-left" aria-hidden="true" style="float: left; font-size: 33px; position: absolute; color: #ac6bfb;"></i></a>
            <h3><%= title %></h3>
            <form:form action="<%= action %>" modelAttribute="student" method="POST" style="padding: 25px 50px;background-color: white;border-radius: 10px;" >
               <form:hidden path="id" />
               <table>
                  <tbody>
                     <tr>
                        <td><label>First Name:</label></td>
                        <td>
                           <form:input path="firstName" />
                        </td>
                     </tr>
                     <tr>
                        <td><label>Last Name:</label></td>
                        <td>
                           <form:input path="lastName" />
                        </td>
                     </tr>
                     <tr>
                        <td><label>Email:</label></td>
                        <td>
                           <form:input path="email" />
                        </td>
                     </tr>
                     <tr>
                        <td><label>Phone Number:</label></td>
                        <td>
                           <form:input path="phoneNumber" />
                        </td>
                     </tr>
                     <tr>
                        <td><label>Is Undergraduate:</label></td>
                        <td>
                           <label class="switch">
                           <input form="dummy" id="undergraduateCheckboxEnalbed" type="checkbox" name="enabled" onchange="UndergraduateEnabledChanged(this);">
                           <span class="slider round"></span>
                           </label>
                           <form:input id="isUndergraduate" path="isUndergraduate" style="display:none;"/>
                        </td>
                     </tr>
                     <tr>
                        <td><label>Registration Year</label></td>
                        <td>
                           <form:input id="registrationYear" path="registrationYear" />
                        </td>
                     </tr>
                     <tr>
                        <td><label>Semester </label></td>
                        <td>
                           <form:input id="semester" path="semester" />
                        </td>
                     </tr>
                     <tr>
                        <td><label>Owes </label></td>
                        <td>
                           <form:input id="owes" path="owes" />
                        </td>
                     </tr>
                     <tr>
                        <td><label><b>Is Eligible </b></label></td>
                        <td>
                           <label class="switch">
                           <input form="dummy" id="checkboxEnalbed" type="checkbox" name="enabled" onchange="EnabledChanged(this);">
                           <span class="slider round"></span>
                           </label>
                           <form:input id="enabled" path="enabled" style="display:none;"/>
                        </td>
                     </tr>
                     <script>
                        function EnabledChanged(element){
                        	if(element.checked){
                        		document.getElementById("enabled").value = "1";
                        	}
                        	else
                        		document.getElementById("enabled").value = "0";
                        }
                        if(document.getElementById("enabled").value == "1")
                        	document.getElementById("checkboxEnalbed").checked = true;
                        else
                        	document.getElementById("checkboxEnalbed").checked = false;
                        
                        function UndergraduateEnabledChanged(element){
                        	if(element.checked){
                        		document.getElementById("isUndergraduate").value = "1";
                        	}
                        	else
                        		document.getElementById("isUndergraduate").value = "0";
                        }
                        if(document.getElementById("isUndergraduate").value == "1")
                        	document.getElementById("undergraduateCheckboxEnalbed").checked = true;
                        else
                        	document.getElementById("undergraduateCheckboxEnalbed").checked = false;
                     </script>
                     <tr>
                        <td><label>Username </label></td>
                        <td>
                           <form:input id="username" path="username" />
                        </td>
                     </tr>
                     <tr id="passwordRow">
                        <td><label>Password </label></td>
                        <td>
                           <form:input id="password" path="password" />
                        </td>
                     </tr>
                     <script>
                        var isUpdate = "<%= (String)request.getAttribute("isUpdate") %>";
                        
                        if(isUpdate == "0"){
                        	//generate password
                        	var chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%&*()_+-=[]?";
                        	var pwd = "";
                        	var length = 12;
                        	var n = chars.length;
                        	
                        	for (var i = 0; i < length; ++i) {
                        		pwd += chars.charAt(Math.floor(Math.random() * n));
                            }
                        	document.getElementById("password").value = pwd;
                        }
                     </script>
                     <security:authorize access="hasAnyRole('SECRETARY')">
                        <script>
                           document.getElementById("firstName").readOnly = true;
                           document.getElementById("lastName").readOnly = true;
                           document.getElementById("email").readOnly = true;
                           document.getElementById("phoneNumber").readOnly = true;
                           document.getElementById("undergraduateCheckboxEnalbed").readOnly = true;
                           document.getElementById("registrationYear").readOnly = true;
                           document.getElementById("semester").readOnly = true;
                           document.getElementById("username").readOnly = true;
                           document.getElementById("password").readOnly = true;
                           
                           document.getElementById("passwordRow").style.visibility = "hidden";
                        </script>
                     </security:authorize>
                     <script>
                        if(isUpdate=="0"){
                        	document.getElementById("registrationYear").value="";
                        	document.getElementById("semester").value="";
                        	document.getElementById("owes").value="";
                        }
                     </script>
                     <tr>
                        <td><label></label></td>
                        <td><input type="submit" value="Save" class="save" /></td>
                     </tr>
                  </tbody>
               </table>
            </form:form>
         </div>
      </div>
   </body>
</html>