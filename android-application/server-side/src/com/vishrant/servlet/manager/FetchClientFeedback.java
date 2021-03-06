package com.vishrant.servlet.manager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vishrant.model.FetchQuestionsBO;
import com.vishrant.model.manager.FetchClientFeedbackBO;

/**
 * Servlet implementation class FetchClientFeedback
 */
@WebServlet("/fetchClientFeedback")
public class FetchClientFeedback extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchClientFeedback() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		fetchClientFeedBack(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		fetchClientFeedBack(request, response);
	}

private void fetchClientFeedBack(HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			String managerId = request.getParameter("managerId");
			String projectName = request.getParameter("projectName");
			String questionType = request.getParameter("questionType");
			
			System.err.println("questionType "+ questionType);
			System.err.println("projectName "+ projectName);
			System.err.println("managerId " + managerId);
			
			String questionStr = null;
			if (questionType != null && projectName != null && managerId != null) {
				FetchClientFeedbackBO fetchFeedbackBO = new FetchClientFeedbackBO();
				questionStr = fetchFeedbackBO.fetchClientFeedback(managerId, projectName, questionType);
			}

			System.err.println("questionStr " + questionStr);
			
			out.write(questionStr != null ? questionStr : "failed");
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		
	}
	
}
