<!DOCTYPE html>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html:html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <!-- BEGIN TOP NAVIGATION MENU -->					
        <ul class="nav navbar-nav pull-right">

            <c:if test="${UserInfo.lastLogin != null}">
                <!-- LAST LOGIN -->
                <li class="pull-left" id="header-user" style="font-size: 70%">
                    <a href="#" class="dropdown-toggle">
                        <span class="username">
                            Last Login: 
                            <fmt:formatDate pattern="MM/dd/yyyy hh:mm a" value="${UserInfo.lastLogin}" />
                        </span>
                    </a>
                </li>   
                <!-- END USER LAST LOGIn -->
            </c:if>

            <!-- BEGIN USER LOGIN DROPDOWN -->
            <li class="dropdown user pull-right" style="padding-right: 15px" id="header-user">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <img alt="" src="<%=request.getContextPath()%>/img/avatars.png" />
                    <span class="username">

                        <bean:write name="UserInfo" scope="session" property="firstName" />
                        <bean:write name="UserInfo" scope="session" property="lastName" />
                    </span>
                    <i class="fa fa-angle-down"></i>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="#" id="changePassword"><i class="fa fa-lock"></i> <bean:message key="com.vishrant.cf.changePassword" /></a></li>
                    <li><a href="<%=request.getContextPath()%>/logout"><i class="fa fa-power-off"></i> <bean:message key="com.vishrant.cf.logOut" /></a></li>
                </ul>
            </li>   
            <!-- END USER LOGIN DROPDOWN -->
        </ul>

        <!-- Add New Engagement Modal -->
        <div id="changePasswordModel" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <input type="button" class="close" data-dismiss="modal" aria-hidden="true" value="&times;" />
                        <h4 class="modal-title">Change Password</h4>
                    </div>
                    <div class="modal-body">
                        <p>Old Password:</p>
                        <p><input type="password" id="oldPassword" class="form-group-sm form-control input-group-sm" ></input></p>

                        <p>New Password:</p>
                        <p><input type="password" id="newPassword" class="form-group-sm form-control input-group-sm" ></input></p>

                        <p class="text-danger">
                            Password must contain one digit, one lowercase character, one uppercase character, some special character(_, @, #, $, %), length should be within 8 to 15 chars.
                        </p>
                    </div>
                    <div class="modal-footer">
                        
                        <input type="button" class="btn btn-default" data-dismiss="modal" value="Close" />
                        <input type="button" class="btn btn-primary" onclick="changePassword()" value="Change Password" />

                    </div>

                </div>
            </div>
        </div>
        <!-- END TOP NAVIGATION MENU -->

        <script>
            $(document).ready(function() {
                $('#changePassword').click(function() {
                    document.getElementById("oldPassword").value = "";
                    document.getElementById("newPassword").value = "";
                    $('#changePasswordModel').modal('show');
                });
            });
            function changePassword() {
                var oldPassword = document.getElementById("oldPassword");
                var newPassword = document.getElementById("newPassword");

                if (newPassword.value.length > 5 && checkPassword(newPassword.value)) {
                    var URL = '<%=request.getContextPath()%>/changePassword?old=' + oldPassword.value + "&new=" + newPassword.value;
                    ajaxCall(URL, "passwordChanged", true);
                } else {
                    alert("Password does not match all conditions.");
                }

            }
            function passwordChanged(response) {
                hideWait();
                alert("Your password is changed successfully.");
            }

            function checkPassword(str) {
                // at least one number, one lowercase and one uppercase letter
                // at least six characters that are letters, numbers and special character(_, @, #, $, %)
               var re = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[_@#$%]).{8,15}$/;
               return re.test(str);
           }
        </script>
    </body>
</html:html>