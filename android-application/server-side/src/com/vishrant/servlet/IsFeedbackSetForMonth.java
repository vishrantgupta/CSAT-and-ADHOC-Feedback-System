package com.vishrant.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vishrant.model.IsFeedbackSetForMonthBO;

/**
 * Servlet implementation class IsFeedbackSetForMonth
 */
@WebServlet("/isFeedbackSetForMonth")
public class IsFeedbackSetForMonth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IsFeedbackSetForMonth() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		isFeedbackSetForMonth(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		isFeedbackSetForMonth(request, response);
	}

	private void isFeedbackSetForMonth(HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out = null;
		
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			String userName = request.getParameter("userName");
			String applicableMonth = request.getParameter("applicableMonth");
			String status = request.getParameter("status");
			
			IsFeedbackSetForMonthBO bo = new IsFeedbackSetForMonthBO();
			Boolean result = bo.isFeedbackSetForMonth(userName, applicableMonth, status);
			
			System.err.println("result is feedbacksubmitted>>>>" + result);
			
			if (result) {
				out.write("yes");
			} else {
				out.write("no");
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
