package com.vishrant.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vishrant.model.IsFeedbackSubmittedBO;

/**
 * Servlet implementation class IsFeedbackSubmitted
 */
@WebServlet(description = "Check if user have already submitted the feedback", urlPatterns = { "/IsFeedbackSubmitted" })
public class IsFeedbackSubmitted extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IsFeedbackSubmitted() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		isFeedbackSubmitted(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		isFeedbackSubmitted(request, response);		
	}

	private void isFeedbackSubmitted(HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out = null;
		
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			String userName = request.getParameter("userId");
			String projectName = request.getParameter("projectName");
			
			System.err.println("userid " + userName);
			System.err.println("projectname " + projectName);
			
			IsFeedbackSubmittedBO bo = new IsFeedbackSubmittedBO();
			String result = bo.isFeedbackSubmitted(userName, projectName);
			
			System.err.println("result is feedbacksubmitted>>>>" + result);
			
			if (result != null) {
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
