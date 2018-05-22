package com.vishrant.cf.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getSession().invalidate();

        String redirectUrl = request.getScheme() + "://" + // "http" + "://
                request.getServerName() + // "host"
                ":" + // ":"
                request.getServerPort() + // "8080"
                getServletContext().getContextPath() + // context path
                "/login.jsp";

        response.sendRedirect(redirectUrl);

    }
}