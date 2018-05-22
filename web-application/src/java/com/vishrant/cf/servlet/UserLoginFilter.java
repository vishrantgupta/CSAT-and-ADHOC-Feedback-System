package com.vishrant.cf.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
@WebFilter(filterName = "UserLoginFilter", urlPatterns = {"/*"})
public class UserLoginFilter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public UserLoginFilter() {
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpRes = (HttpServletResponse) response;

        HttpSession session = httpReq.getSession();
        session.setMaxInactiveInterval(60 * 60 * 10);

        String uri = httpReq.getRequestURI();

        httpRes.addHeader("X-FRAME-OPTIONS", "DENY");
        
        if (!(uri.endsWith("login") || uri.endsWith("login.jsp") // login urls
                || uri.endsWith("png") // allowing images
                || uri.endsWith("jpg")
                || uri.endsWith("html") // allowing static pages
                || uri.endsWith("css") // allowing style sheets
                || uri.endsWith("js") // allowing javascript
                )) {

            if (session != null && session.getAttribute("UserInfo") == null) {

                String redirectUrl = httpReq.getScheme() + "://" + // "http" + "://
                        httpReq.getServerName() + // "host"
                        ":" + // ":"
                        httpReq.getServerPort() + // "8080"
                        getFilterConfig().getServletContext().getContextPath() + // context path
                        "/login.jsp";

                httpRes.sendRedirect(redirectUrl);
            } else {
                chain.doFilter(request, response);
            }

        } else {
            chain.doFilter(request, response);
        }

    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("UserLoginFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("UserLoginFilter()");
        }
        StringBuffer sb = new StringBuffer("UserLoginFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
}