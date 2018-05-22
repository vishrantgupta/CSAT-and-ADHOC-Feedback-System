<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<html:html>
    <!--[if lt IE 7 ]><html class="ie ie6" lang="en"> <![endif]-->
    <!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
    <!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
    <!--[if (gte IE 9)|!(IE)]><!--> 	<html lang="en"> <!--<![endif]-->
        <head>

            <!-- General Metas -->
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">	<!-- Force Latest IE rendering engine -->
            <title>Feedback Management</title>
            <meta name="viewport" content="width=430, initial-scale=1">
            <meta name="description" content="">
            <meta name="author" content="">


            <!--[if lt IE 9]>
                    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
            <![endif]-->

            
            <!--[if lt IE 9 ]>
                <script>
                    window.location = "<%=request.getContextPath()%>/invalid-browser.html";
                </script>
            <![endif]--> 
            
            <!-- Mobile Specific Metas -->
            <meta name="viewport" content="width=430, initial-scale=1, maximum-scale=1" /> 

            <!-- Stylesheets -->
            <link rel="stylesheet" href="css/login/base.css">
            <link rel="stylesheet" href="css/login/skeleton.css">
            <link rel="stylesheet" href="css/login/layout.css">

            <link rel="shortcut icon" href="images/login/icon-feedback.png">

            <noscript>
            <meta http-equiv="refresh" content="0;url=<%=request.getContextPath()%>/noscript.html">
            </noscript>
        </head>

        <body style="visibility: hidden;">

            <div class="notice" style='<logic:notPresent name="message" scope="request">display: none;</logic:notPresent>'>
                    <!--<a href="" class="close">close</a>-->
                    <div class="warn" style="text-align: center;">Whoops! We didn't recognise your username or password. Please try again.</div>
                </div>

                <!-- Primary Page Layout -->

                <div class="container col-lg-10 col-sm-10 col-xs-12" style="width: 100%">

                    <div class="form-bg">
                        <img src='images/login/vishrant-logo_1.png' />
                        <form method="post" name="loginForm" action="login" autocomplete="off">

                            <h2>Feedback Management</h2>
                            <p><input type="text" name="userName" id="userName" placeholder="Username" autocomplete="off"></p>
                            <!-- disables autocomplete --><input type="text" style="display:none">
                            <p><input type="password" name="password" id="password" placeholder="Password" autocomplete="off"></p>

                            <label for="remember">
                                <input type="checkbox" disabled id="remember" value="remember" />
                                <span>Remember me on this computer</span>
                            </label>
                            <button type="submit"></button>

                            <form>

                                </div>

                                <!--<p class="forgot">Forgot your password? <a href="#">Click here to reset it.</a></p>-->

                                </div><!-- container -->

                                <!-- JS  -->
                                <script src="<%=request.getContextPath()%>/js/jquery/jquery-1.11.2.min.js"></script>
                                <script src="<%=request.getContextPath()%>/js/login/app.js"></script>

                                <script>

                                    $(document).ready(function () {
//                                        var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
//                                        if (is_chrome) {
//                                            window.location = "<%=request.getContextPath()%>/invalid-browser.html";
//                                        }
                                        document.body.style.visibility = "visible";
                                    });

                            </script>

                            <!-- End Document -->
                            </body>

                        </html:html>