/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vishrant.cf.action;

import com.vishrant.cf.bean.UserBean;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

/**
 *
 * @author VG00124233
 */
public class JSExceptionCaptureAction extends DispatchAction {

    final Logger log = Logger.getLogger(JSExceptionCaptureAction.class);

    public ActionForward reportJSException(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        UserBean ub = (UserBean) request.getSession().getAttribute("UserInfo");

        if (ub != null) {

            try {
                PrintWriter out = response.getWriter();
                response.setContentType("text/xml");
                response.setHeader("Cache-Control", "no-cache");
                response.setStatus(HttpServletResponse.SC_OK);

                String jsException = request.getParameter("jsException");
                log.error("SEVERE: JS exception occured: " + jsException);

                out.flush();
                out.close();

            } catch (Exception e) {
                log.error("Exception occured in captureJSException " + e);
            }
            return mapping.findForward(null);
        }

        return mapping.findForward(null);
    }
}