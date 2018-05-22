package com.vishrant.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vishrant.service.SaveClientFeedback;

/**
 * Servlet implementation class SubmitFeedback
 */
@WebServlet(description = "Servlet to submit feedback", urlPatterns = { "/SubmitFeedback" })
public class SubmitFeedback extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitFeedback() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		submitFeedback(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		submitFeedback(request, response);
	}
	
	private void submitFeedback(HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			String feedbackString = request.getParameter("feedbackString");
			
			System.out.println("feedbackString>>>>" + feedbackString);
			
			SaveClientFeedback scf = new SaveClientFeedback();
			boolean status = scf.saveClientFeedback(feedbackString);
			
			if (feedbackString != null && status) {
				out.write("success");
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
