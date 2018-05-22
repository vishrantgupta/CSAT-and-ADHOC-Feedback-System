package com.vishrant.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vishrant.model.FetchFeedbackStatusBO;

/**
 * Servlet implementation class GetFeedbackStatus
 */
@WebServlet("/getFeedbackStatus")
public class GetFeedbackStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFeedbackStatus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getFeedbackStatus(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getFeedbackStatus(request, response);
	}

	private void getFeedbackStatus(HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			String userId = request.getParameter("userId");
			String feedbackType = request.getParameter("feedbackType");
			String month = request.getParameter("month");
			
			FetchFeedbackStatusBO fetchFeedbackStatusBO = new FetchFeedbackStatusBO();
			String feedbackStr = fetchFeedbackStatusBO.getFeedbackStatus(userId, feedbackType, month);

			System.err.println("feedbackStr >>>>>>> ****** "  + feedbackStr);
			
			out.write(feedbackStr != null ? feedbackStr : "failed");
			
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
