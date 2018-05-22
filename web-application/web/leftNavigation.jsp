<%@page import="com.vishrant.cf.constants.AppConstants"%>
<%@page import="com.vishrant.cf.bean.UserBean"%><!DOCTYPE html>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html:html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Left Navigation</title>

        <%
            UserBean ub = (UserBean) session.getAttribute("UserInfo");
        %>

    </head>
    <body>
        <!-- SIDEBAR -->
        <div id="sidebar" class="sidebar">
            <div class="sidebar-menu nav-collapse">
                <div class="divide-20"></div>
                <!-- SEARCH BAR -->

                <!-- SIDEBAR MENU -->
                <ul>
                    <li class="active">
                        <a href="<%=request.getContextPath()%>">
                            <i class="fa fa-tachometer fa-fw"></i> <span class="menu-text">Dashboard</span>
                            <span class="selected"></span>
                        </a>					
                    </li>
                    
                    <% if (ub != null && (ub.isRoleValidForUser(AppConstants.FEEDBACK_USERS) || ub.isRoleValidForUser(AppConstants.REPORTS_USERS))) {%>
                        <li class="has-sub">
                            <a href="javascript:;" class="">
                                <i class="fa fa-bookmark-o fa-fw"></i> <span class="menu-text">Tasks</span>
                                <span class="arrow"></span>
                            </a>
                            <ul class="sub">
                                <% if (ub.isRoleValidForUser(AppConstants.FEEDBACK_USERS)) {%>
                                    <li>
                                        <a href="<%=request.getContextPath()%>/page/capture.do?action=getDefaultFeedback" ><span class="sub-menu-text">Feedback</span></a>
                                    </li>
                                <% } %>
                                <% if (ub.isRoleValidForUser(AppConstants.REPORTS_USERS)) {%>
                                    <li>
                                        <a href="<%=request.getContextPath()%>/page/review.do?action=getDefaultReports"><span class="sub-menu-text">Reports</span></a>
                                    </li>
                                <% }%>

                            </ul>
                        </li>
                    <% } %>


                    <% if (ub != null && (ub.isRoleValidForUser(AppConstants.ENGAGEMENT_OPERATIONS) || ub.isRoleValidForUser(AppConstants.APPLICATION_OPERATIONS) || ub.isRoleValidForUser(AppConstants.USER_OPERATIONS))) {%>
                        <li class="has-sub">
                            <a href="javascript:;" class="">
                                <i class="fa fa-wrench fa-fw"></i> <span class="menu-text">Administration</span>
                                <span class="arrow"></span>
                            </a>
                            <ul class="sub">
                                <% if (ub.isRoleValidForUser(AppConstants.ENGAGEMENT_OPERATIONS)) {%>
                                    <li>
                                        <a href="<%=request.getContextPath()%>/page/engagement.do?action=getDefaultEngagement">
                                            <span class="sub-menu-text">Engagement</span>
                                        </a>
                                    </li>
                                <% } %>
                                
                                <% if (ub.isRoleValidForUser(AppConstants.APPLICATION_OPERATIONS)) {%>
                                    <li>
                                        <a href="<%=request.getContextPath()%>/page/application.do?action=getDefaultApplication">
                                            <span class="sub-menu-text">Application</span>
                                        </a>
                                    </li>
                                <% } %>
                                
                                <% if (ub.isRoleValidForUser(AppConstants.APPLICATION_OPERATIONS)) {%>
                                    <li>
                                        <a class="" href="<%=request.getContextPath()%>/page/userOperations.do?action=getDefaultUser">
                                            <span class="sub-menu-text">Users</span>
                                        </a>
                                    </li>
                                <% } %>
                            </ul>
                        </li>
                    <% } %>

                    <li><a class="" href="#"><i class="fa fa-edit fa-fw"></i> <span class="menu-text">About</span></a></li>
                    <li><a class="" href="#"><i class="fa fa-newspaper-o fa-fw"></i> <span class="menu-text">Contact Us</span></a></li>

                </ul>
                <!-- /SIDEBAR MENU -->
            </div>
        </div>
        <!-- /SIDEBAR -->

    </body>

</html:html>