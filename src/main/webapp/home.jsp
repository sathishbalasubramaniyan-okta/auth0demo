<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.auth0.SessionUtils"%>

<%
	//Welcome&nbsp;&nbsp;
	String name = "";
	String email = "";
	String firstName="";
	String lastName="";
	String accessToken = "";
	String idToken = "";
	
	
	if (SessionUtils.get(request, "name") != null) {
		name = (String)SessionUtils.get(request, "name");
	}
	if (SessionUtils.get(request, "accessToken") != null) {
		accessToken = (String)SessionUtils.get(request, "accessToken");
	} else {
		response.sendRedirect("/auth0demo");
	}
	if (SessionUtils.get(request, "idToken") != null) {
		idToken = (String)SessionUtils.get(request, "idToken");
	}
	if (SessionUtils.get(request, "email") != null) {
		email = (String)SessionUtils.get(request, "email");
	}
	if (SessionUtils.get(request, "firstName") != null) {
		firstName = (String)SessionUtils.get(request, "firstName");
	}
	if (SessionUtils.get(request, "lastName") != null) {
		lastName = (String)SessionUtils.get(request, "lastName");
	}
	
	System.out.println("In home.jsp");
%>
<html>
  <head>
      <title>Auth0 demo</title>
      <link rel="stylesheet" href="css/demo.css">
  </head>
  <body onload="processModal()">
    <script type="text/javascript">
      function logout() {
    	console.log("Get Signout call start ...");	
    	var pageContext = window.location.protocol + "//" + window.location.host;
    	var method = "post";
    	var url = pageContext + '/auth0demo/logout';
    	var form = document.createElement("form");
    	form.setAttribute("method", method);
    	form.setAttribute("action", url);
    	document.body.appendChild(form);
    	form.submit();
      }
      
      function processModal() {
        // Get the modal
        var modal = document.getElementById("jwtModal");

        // Get the button that opens the modal
        var btn = document.getElementById("technicalDetails");

        // Get the <span> element that closes the modal
        var span = document.getElementsByClassName("close")[0];

        // When the user clicks on the button, open the modal 
        btn.onclick = function() {
          modal.style.display = "block";
        }

        // When the user clicks on <span> (x), close the modal
        span.onclick = function() {
          modal.style.display = "none";
        }

        // When the user clicks anywhere outside of the modal, close it
        window.onclick = function(event) {
          if (event.target == modal) {
            modal.style.display = "none";
          }
      }
    }        
    </script>
    <header id="header">
      <div class="welcome">Welcome <%=name%>!</div>
      <a href="#" class="logout" onclick="logout()">Logout</a> 
    </header>
    <div id="jwtModal" class="jwt-modal">
      <!-- Modal content -->
      <div class="modal-content">
        <span class="close">&times;</span>
        <p>ID Token</p>
        <textarea class="token-area" id="idtoken" rows="10" cols="120"><%=idToken%></textarea>
        <p>Access Token</p>
        <textarea class="token-area" id="accesstoken" rows="10" cols="120"><%=accessToken%></textarea>
        <p>Refresh Token</p>
        <textarea class="token-area" id="refreshtoken" rows="10" cols="120"></textarea>
      </div>
    </div>
    <main id="main">
      <section id="content">
        <div class="content-box">
          <div class="name"><%=name%></div>
          <div>Here is your profile information</div>
          <div class="profile-box">
              <div class="profile-box-header">
                
                <button id="technicalDetails" class="technical-details">Technical Details</button>
              </div>
              <table class="profile-table">
                <tr>
                  <td>Email</td>
                  <td class="attrvalue"><%=email%></td>
                </tr>
                <tr>
                  <td>First Name</td>
                  <td class="attrvalue"><%=firstName%></td>
                </tr>
                <tr>
                  <td>Last Name</td>
                  <td class="attrvalue"><%=lastName%></td>
                </tr>
                <tr>
                  <td>Full Name</td>
                  <td class="attrvalue"><%=name%></td>
                </tr>
                <tr>
                  <td>Mobile Number</td>
                  <td class="attrvalue"></td>
                </tr>
              </table>
           
          </div>
        </div>
      </section>
    </main>
    <footer id="footer">This is a demo application, not copyrighted!</footer>    
  </body>
</html>