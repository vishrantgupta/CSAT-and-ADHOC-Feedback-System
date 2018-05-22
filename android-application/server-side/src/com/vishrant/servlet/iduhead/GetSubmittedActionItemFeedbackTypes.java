package com.vishrant.servlet.iduhead;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vishrant.model.iduhead.GetSubmittedActionItemFeedbackTypesBO;

/**
 * Servlet implementation class GetSubmittedActionItemFeedbackTypes
 */
@WebServlet("/getSubmittedActionItemFeedbackTypes")
public class GetSubmittedActionItemFeedbackTypes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSubmittedActionItemFeedbackTypes() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		submittedFeedbackTypes(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		submittedFeedbackTypes(request, response);
	}

	private void submittedFeedbackTypes(HttpServletRequest request, HttpServletResponse response) {
		
		PrintWriter out = null;
		
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			String userName = request.getParameter("userId");
			String selectedProject = request.getParameter("selectedProject");
			
			GetSubmittedActionItemFeedbackTypesBO bo = new GetSubmittedActionItemFeedbackTypesBO();
			String result = bo.getSubmittedActionItemFeedbackTypes(userName, selectedProject);
			
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
