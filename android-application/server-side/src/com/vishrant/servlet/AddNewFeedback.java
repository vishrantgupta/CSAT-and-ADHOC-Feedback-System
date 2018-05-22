package com.vishrant.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vishrant.model.AddNewFeedbackBO;

/**
 * Servlet implementation class AddNewFeedback
 */
@WebServlet("/addNewFeedback")
public class AddNewFeedback extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewFeedback() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		addNewFeedback(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		addNewFeedback(request, response);
	}
		
	private void addNewFeedback(HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			String userId = request.getParameter("userId");
			String feedbackType = request.getParameter("feedbackType");
			String projectName = request.getParameter("projectName");
			String applicableMonth = request.getParameter("applicableMonth");
			String status = request.getParameter("status");
			
			System.err.println("user id: " + userId + " feedbackType "
					+ feedbackType + " project name " + projectName
					+ " applicable month " + applicableMonth
					+ " status " + status);
			
			AddNewFeedbackBO addFeedbackBO = new AddNewFeedbackBO();
			Boolean result = addFeedbackBO.addNewFeedback(userId, feedbackType, projectName, applicableMonth, status);
			
			System.err.println("result " + result);
			
			if (result) {
				System.err.println("escapsed resut is: " + result);
				out.write("Feedback successfully added.");
			} else {
				out.write("fail");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
