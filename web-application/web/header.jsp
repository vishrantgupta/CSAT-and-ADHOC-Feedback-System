<!DOCTYPE html>
<html lang="en">

    <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <title>Welcome</title>
        <meta name="viewport" content="width=430, initial-scale=1.0, maximum-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/cloud-admin.css" >
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/bower_components/bootstrap/dist/css/bootstrap.min.css" >

        <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/css/themes/graphite.css" id="skin-switcher" >
        <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/css/responsive.css" >
        
        <!-- STYLESHEETS --><!--[if lt IE 9]><script src="js/flot/excanvas.min.js"></script><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script><![endif]-->
        <link href="<%=request.getContextPath()%>/bower_components/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        
        <!-- MetisMenu CSS -->
        <link href="<%=request.getContextPath()%>/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">
        
        <!-- ANIMATE -->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/animatecss/animate.min.css" />
        
        <!-- DATE RANGE PICKER -->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/bootstrap-daterangepicker/daterangepicker-bs3.css" />
        
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/jquery-todo/css/styles.css" />
        
        <!-- FULL CALENDAR -->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/fullcalendar/fullcalendar.min.css" />
        
        <!-- GRITTER -->
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/js/gritter/css/jquery.gritter.css" />
        
        <!-- FONTS -->
        <link href='<%=request.getContextPath()%>/css/googleApiFont.css' rel='stylesheet' type='text/css'>
        
        <!-- CUSTOM -->
        <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/css/custom/form.css" >
        <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/css/custom/formcontrol.css" >
        
        <script>
            var root = "<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()%>";
            var contextRoot = "<%=request.getContextPath()%>";
        </script>
        
        <!-- JQUERY -->
        <script src="<%=request.getContextPath()%>/js/jquery/jquery-1.11.2.min.js"></script>
        <script src="<%=request.getContextPath()%>/js/custom/common.js"></script>
        
        <script>
            /*if((BrowserDetect.browser == "Explorer") && (BrowserDetect.version < 9)) {
                // window.location = "<%=request.getContextPath()%>/invalid-browser.html";
            }*/
        </script>
        
        <!--[if lt IE 9 ]>
            <script>
                window.location = "<%=request.getContextPath()%>/invalid-browser.html";
            </script>
        <![endif]--> 
        
        <style>
            @media screen and (max-width: 430px) {

                #submitBtn {
                    margin-top: 20px;
                }

                #applicationTable_length label {
                    width: 100%;
                    margin-left: 15px;
                }

                #applicationTable_filter label {
                    width: 100%;
                }

                #applicationTable_filter input {
                    max-width: 150px;
                }

                #fromDate {
                    width: 50px;
                }
                
                #toDate {
                    width: 50px;
                }
                /*#tm-logo {
                    width: 40px;
                    height: 40px;
                }*/
            }
            
            @media (max-width: 766px) and (min-width: 430px) {

                #submitBtn {
                    margin-top: 20px;
                }

                #applicationTable_length label {
                    width: 100%;
                    margin-left: 15px;
                    text-align: center;
                }

                #applicationTable_filter label {
                    width: 100%;
                    text-align: center;
                }

                #applicationTable_filter input {
                    max-width: 150px;
                }

                /*#tm-logo {
                    width: 40px;
                    height: 40px;
                }*/

            }
            
            /* Custom dialog styles */
            #popup_container.style_1 {
                    font-family: Georgia, serif;
                    color: #A4C6E2;
                    background: #005294;
                    border-color: #113F66;
            }

            #popup_container.style_1 #popup_title {
                    color: #FFF;
                    font-weight: normal;
                    text-align: left;
                    background: #76A5CC;
                    border: solid 1px #005294;
                    padding-left: 1em;
            }

            #popup_container.style_1 #popup_content {
                    background: none;
            }

            #popup_container.style_1 #popup_message {
                    padding-left: 0em;
            }

            #popup_container.style_1 INPUT[type='button'] {
                    border: outset 2px #76A5CC;
                    color: #A4C6E2;
                    background: #3778AE;
            }
            
        </style>
        
    </head>
    <body>
        
        <div class="navbar-brand">
            <!-- COMPANY LOGO -->
            <a href="<%=request.getContextPath()%>">
                <img src="<%=request.getContextPath()%>/img/vishrant-logo_1.png" alt="Tech Mahindra" class="img-responsive" height="30" width="120">
            </a>

                <div id="sidebar-collapse" class="sidebar-collapse btn">
                <i class="fa fa-bars" 
                   data-icon1="fa fa-bars" 
                   data-icon2="fa fa-bars" ></i>
            </div>
            <!-- /SIDEBAR COLLAPSE -->
        </div>
            <a href="<%=request.getContextPath()%>" class="navbar-brand" style="color: #F0F0F0"><strong>Feedback Management</strong></a>
    </body>

</html>
