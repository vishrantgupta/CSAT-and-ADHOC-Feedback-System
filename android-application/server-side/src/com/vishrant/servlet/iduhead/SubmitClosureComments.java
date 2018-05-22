package com.vishrant.servlet.iduhead;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vishrant.service.iduhead.SaveIduHeadActionItems;

/**
 * Servlet implementation class SubmitClosureComments
 */
@WebServlet("/submitClosureComments")
public class SubmitClosureComments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitClosureComments() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		submitClosureComments(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		submitClosureComments(request, response);
	}

	private void submitClosureComments(HttpServletRequest request, HttpServletResponse response){
		
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();

			String feedbackString = request.getParameter("actionItemString");
			
			System.out.println("feedbackString>>>>" + feedbackString);
			
			SaveIduHeadActionItems sih = new SaveIduHeadActionItems();
			sih.saveClosureComments(feedbackString);
			
			if (feedbackString != null) {
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
