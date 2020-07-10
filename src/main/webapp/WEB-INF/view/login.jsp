<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!doctype html>
<html lang="en">
   <head>
      <title>Login Page</title>
      <link type="text/css"
         rel="stylesheet"
         href="${pageContext.request.contextPath}/resources/css/style.css" />
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
      <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
      <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
      <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
      <link rel="stylesheet"
         href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
      <script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
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
            </ul>
         </div>
      </nav>
      <div>
         <div id="loginbox">
            <div class="panel panel-info">
               <div class="panel-heading" style="background-color: #ac6bfb; color: white; border-color: #ac6bfb;">
                  <div class="panel-title">Sign In</div>
               </div>
               <div style="padding-top: 30px" class="panel-body">
                  <!-- Login Form -->
                  <form action="${pageContext.request.contextPath}/authenticate" 
                     method="POST" class="form-horizontal">
                     <!-- Place for messages: error, alert etc ... -->
                     <div class="form-group">
                        <div class="col-xs-15">
                           <div>
                              <!-- Check for login error -->
                              <c:if test="${param.error != null}">
                                 <div class="alert alert-danger col-xs-offset-1 col-xs-10">
                                    Invalid username and password.
                                 </div>
                              </c:if>
                              <!-- Check for logout -->
                              <c:if test="${param.logout != null}">
                                 <div class="alert alert-success col-xs-offset-1 col-xs-10">
                                    You have been logged out.
                                 </div>
                              </c:if>
                           </div>
                        </div>
                     </div>
                     <!-- User name -->
                     <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span> 
                        <input type="text" name="username" placeholder="username" class="form-control">
                     </div>
                     <!-- Password -->
                     <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span> 
                        <input type="password" name="password" placeholder="password" class="form-control" >
                     </div>
                     <!-- Login/Submit Button -->
                     <div style="margin-top: 10px" class="form-group">
                        <div class="col-sm-6 controls">
                           <button type="submit" class="btn btn-success" style="background: #ac6bfb; border-color: #ac6bfb;">Login</button>
                        </div>
                     </div>
                     <input type="hidden"
                        name="${_csrf.parameterName}"
                        value="${_csrf.token}" />
                  </form>
               </div>
            </div>
         </div>
      </div>
   </body>
</html>