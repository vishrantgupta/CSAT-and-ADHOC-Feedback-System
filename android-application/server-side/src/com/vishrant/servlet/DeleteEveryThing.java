package com.vishrant.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vishrant.model.DeleteEveryThingBO;

/**
 * Servlet implementation class DeleteEveryThing
 */
@WebServlet("/deleteEveryThing")
public class DeleteEveryThing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteEveryThing() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		deleteEveryThing(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		deleteEveryThing(request, response);
	}

	private void deleteEveryThing(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			DeleteEveryThingBO deleteEveryThingBO = new DeleteEveryThingBO();
			String status = deleteEveryThingBO.deleteEntries();
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			System.err.println("status is: " + status);
			out.write("Feedback successfully added.");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
