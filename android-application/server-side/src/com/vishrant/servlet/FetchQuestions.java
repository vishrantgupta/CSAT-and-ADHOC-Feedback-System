package com.vishrant.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vishrant.model.FetchQuestionsBO;

/**
 * Servlet implementation class FetchQuestions
 */
@WebServlet(description = "Fetch CSAT or ADHOC question", urlPatterns = { "/FetchQuestions" })
public class FetchQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchQuestions() {
    	super();
    }

   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		fetchQuestions(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		fetchQuestions(request, response);
	}

	private void fetchQuestions(HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			String questionType = request.getParameter("questionType");
			String date = request.getParameter("date");
			
			System.err.println("questionType "+ questionType);
			System.err.println("Date " + date);
			
			String questionStr = null;
			if (questionType != null && date != null) {
				FetchQuestionsBO fetchQuestionsBO = new FetchQuestionsBO();
				questionStr = fetchQuestionsBO.fetchQuestions(questionType, date);
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
