package com.vishrant.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import com.vishrant.model.AppUserDetailsBO;
import com.vishrant.model.LoginBO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "Used to login", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		validateLogin(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		validateLogin(request, response);
	}
	
	private void validateLogin(HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			String userName = request.getParameter("userName");
			String password = request.getParameter("password");
			String userType = request.getParameter("userType");
			
			LoginBO loginBO = new LoginBO();
			
			String result = loginBO.validateLogin(userType.toUpperCase(), userName, password);
			
			System.out.println(">>>>" + result);
			
			if (!result.equalsIgnoreCase("fail")) {
				System.err.println("escapsed resut is: " + result);
				out.write(result);
			} else {
				out.write("fail");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
