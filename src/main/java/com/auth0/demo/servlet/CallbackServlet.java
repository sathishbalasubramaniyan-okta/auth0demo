package com.auth0.demo.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.SessionUtils;
import com.auth0.Tokens;
import com.auth0.jwt.JWT;

/**
 * Servlet implementation class CallbackServlet
 */
@WebServlet("/CallbackServlet")
public class CallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CallbackServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = (HttpSession)request.getSession();
        
        AuthenticationController authenticationController = (AuthenticationController)session.getAttribute("AuthenticationController");
        
        if (authenticationController == null) {
        	response.sendRedirect("/auth0demo");
        }
        
        try {
        	Tokens tokens = authenticationController.handle(request, response);
            SessionUtils.set(request, "accessToken", tokens.getAccessToken());
            SessionUtils.set(request, "idToken", tokens.getIdToken());
            String nickname = JWT.decode(tokens.getIdToken()).getClaim("nickname").asString();
            String email = JWT.decode(tokens.getIdToken()).getClaim("email").asString();
            SessionUtils.set(request, "email", email);
            SessionUtils.set(request, "firstName", nickname);
            request.getRequestDispatcher("/home.jsp").forward(request, response);
        } catch (IdentityVerificationException e) {
            e.printStackTrace();
            response.sendRedirect("/auth0demo");
        }
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
