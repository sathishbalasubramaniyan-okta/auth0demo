package com.auth0.demo.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

import com.auth0.AuthenticationController;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() {
    	//LoadProperties.loadProperties(getClass().getResourceAsStream(PROP_FILE));
    	
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String domain = request.getServletContext().getInitParameter("com.auth0.domain");
        String clientId = request.getServletContext().getInitParameter("com.auth0.clientId");
        String clientSecret = request.getServletContext().getInitParameter("com.auth0.clientSecret");
        AuthenticationController authenticationController = null;

        if (domain == null || clientId == null || clientSecret == null) {
            throw new IllegalArgumentException("Missing domain, clientId, or clientSecret. Did you update web.xml?");
        }
        
        HttpSession session = (HttpSession)request.getSession();
        
        authenticationController = (AuthenticationController)session.getAttribute("AuthenticationController");
        
        if ((authenticationController == null)) {
        	JwkProvider jwkProvider = new JwkProviderBuilder(domain).build();
        	authenticationController = AuthenticationController.newBuilder(domain, clientId, clientSecret).withJwkProvider(jwkProvider).build();
        	session.setAttribute("AuthenticationController", authenticationController);
        }
        
        String redirectUri = request.getScheme() + "://" + request.getServerName();
        if ((request.getScheme().equals("http") && request.getServerPort() != 80) || (request.getScheme().equals("https") && request.getServerPort() != 443)) {
            redirectUri += ":" + request.getServerPort();
        }
        redirectUri += "/auth0demo/callback";
        
        String authorizeUrl = authenticationController.buildAuthorizeUrl(request, response, redirectUri).withScope("openid profile email address phone read:subscribers").withParameter("primarycolor","#dbdd40").withParameter("backgroundcolor","#e090d1").withAudience("http://apidefault").build();
        response.sendRedirect(authorizeUrl);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
