package com.vishrant.cf.servlet;

import com.vishrant.cf.bean.UserBean;
import com.vishrant.cf.bo.LoginBO;
import com.vishrant.cf.exception.InvalidUserExpetion;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
@WebServlet(name = "UserLoginServlet", urlPatterns = {"/login"})
public class UserLoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String userName = request.getParameter("userName").trim();
            String password = request.getParameter("password");

            LoginBO loginBO = new LoginBO();
            UserBean ub = loginBO.isValidUser(userName, password);

            if (ub != null) {

                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(60 * 60 * 10);
                session.setAttribute("UserInfo", ub);

                response.sendRedirect("index.jsp");
            } else {
                throw new InvalidUserExpetion("Either user name or password is wrong.");
            }

        } catch (InvalidUserExpetion iue) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            request.setAttribute("message", "Either user name or password is wrong." + iue);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            request.setAttribute("message", "Unknow exception, please try again." + e);
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
        rd.include(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}