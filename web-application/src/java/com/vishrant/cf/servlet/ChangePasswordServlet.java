/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vishrant.cf.servlet;

import com.vishrant.cf.bean.UserBean;
import com.vishrant.cf.bo.ChangePasswordBO;
import com.vishrant.cf.constants.AppConstants;
import com.vishrant.cf.exception.InvalidUserExpetion;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/changePassword"})
public class ChangePasswordServlet extends HttpServlet {
    
    final Logger log = Logger.getLogger(ChangePasswordServlet.class);
    
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

            UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

            if (ub != null) {
                String oldPassword = request.getParameter("old");
                String newPassword = request.getParameter("new");
                
                String userId = ub.getUserId();
                String userName = ub.getUserName();
                
                if(newPassword.length() >= 8 && newPassword.length() < 16) {
                    ChangePasswordBO changePasswordBO = new ChangePasswordBO();
                    boolean status = changePasswordBO.isPasswordChanged(oldPassword, newPassword, userId, userName);
                    if(status) {
                        response.setStatus(HttpServletResponse.SC_OK);
                    } else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
                
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                throw new InvalidUserExpetion("Either user name or password is wrong.");
            }
        } catch (InvalidUserExpetion iue) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.error(AppConstants.LOGGING_EXCEPTION + iue);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.error(AppConstants.LOGGING_EXCEPTION + e);
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