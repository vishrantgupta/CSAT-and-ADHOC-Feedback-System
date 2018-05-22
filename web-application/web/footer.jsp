<!DOCTYPE html>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<html:html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Footer</title>
    </head>
    <body>
        <div class="panel-footer">
            <div class="row text-muted text-center text-nowrap">
                <bean:message key="form.vishrant.cf.footerNote" />
            </div>
            <!-- /.row -->
        </div>

        <!-- JAVASCRIPTS -->
        <!-- Placed at the end of the document so the pages load faster -->

        <!-- JQUERY UI-->
        <script src="<%=request.getContextPath()%>/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
        <!-- BOOTSTRAP -->
        <script src="<%=request.getContextPath()%>/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>


        <!-- DATE RANGE PICKER -->
        <script src="<%=request.getContextPath()%>/js/bootstrap-daterangepicker/moment.min.js"></script>

        <script src="<%=request.getContextPath()%>/js/bootstrap-daterangepicker/daterangepicker.min.js"></script>
        
        <!-- SLIMSCROLL -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jQuery-slimScroll-1.3.0/jquery.slimscroll.min.js"></script>
        
        <!-- SLIMSCROLL -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jQuery-slimScroll-1.3.0/jquery.slimscroll.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jQuery-slimScroll-1.3.0/slimScrollHorizontal.min.js"></script>
        
        <!-- BLOCK UI -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jQuery-BlockUI/jquery.blockUI.min.js"></script>
       
        <!-- SPARKLINES -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/sparklines/jquery.sparkline.min.js"></script>
        
        <!-- EASY PIE CHART -->
        <script src="<%=request.getContextPath()%>/js/jquery-easing/jquery.easing.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/easypiechart/jquery.easypiechart.min.js"></script>
        
        <!-- FLOT CHARTS -->
        <script src="<%=request.getContextPath()%>/js/flot/jquery.flot.min.js"></script>
        <script src="<%=request.getContextPath()%>/js/flot/jquery.flot.time.min.js"></script>
        <script src="<%=request.getContextPath()%>/js/flot/jquery.flot.selection.min.js"></script>
        <script src="<%=request.getContextPath()%>/js/flot/jquery.flot.resize.min.js"></script>
        <script src="<%=request.getContextPath()%>/js/flot/jquery.flot.pie.min.js"></script>
        <script src="<%=request.getContextPath()%>/js/flot/jquery.flot.stack.min.js"></script>
        <script src="<%=request.getContextPath()%>/js/flot/jquery.flot.crosshair.min.js"></script>
        
        <!-- TODO -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-todo/js/paddystodolist.js"></script>
        
        <!-- TIMEAGO -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/timeago/jquery.timeago.min.js"></script>
        
        <!-- FULL CALENDAR -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/fullcalendar/fullcalendar.min.js"></script>
        
        <!-- COOKIE -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jQuery-Cookie/jquery.cookie.min.js"></script>
        
        <!-- GRITTER -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/gritter/js/jquery.gritter.min.js"></script>
        
        <!-- Table -->
        <script type="text/javascript" src="<%=request.getContextPath()%>/bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.js"></script>
        
        <link href="<%=request.getContextPath()%>/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet">
        <!-- Table Ends -->

        <!-- CUSTOM SCRIPT -->
        <script src="<%=request.getContextPath()%>/js/script.js"></script>
        
        <!-- CUSTOM ALERT -->
        <script src="<%=request.getContextPath()%>/js/alertbox/jquery.ui.draggable.js" type="text/javascript"></script>
		
        <!-- Core files -->
        <script src="<%=request.getContextPath()%>/js/alertbox/jquery.alerts.js" type="text/javascript"></script>
        <link href="<%=request.getContextPath()%>/css/alertbox/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
        
        <script src="<%=request.getContextPath()%>/js/jquery-migrate/jquery-migrate-1.0.0.js" type="text/javascript"></script>
        
        <script>
            $(document).ready(function () {
                App.setPage("index");  //Set current page
                App.init(); //Initialise plugins and elements
                document.body.style.visibility = "visible";
            });
        </script>
        <!-- /JAVASCRIPTS -->
        
        <div id="wait" style="display: none;">
            <span><img src='<%=request.getContextPath()%>/img/loaders/4.gif' /></span>
        </div>
    </body>
</html:html>