<!DOCTYPE html>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<html:html>

    <meta http-equiv="content-type" content="text/html;charset=UTF-8" />
    <head>
        <title><tiles:getAsString name="title" /></title>
    </head>
    <body>
        <!-- HEADER -->
        <header class="navbar clearfix" id="header">
            <div class="container">
                <jsp:include page="header.jsp" />

                <jsp:include page="userControls.jsp" />
            </div>
        </header>
        <!--/HEADER -->

        <!-- PAGE -->
        <section id="page">

            <jsp:include page="leftNavigation.jsp" />

            <div id="main-content">

                <div class="container">
                    <div class="row">
                        <div id="content" class="col-lg-12">
                            <!-- PAGE HEADER-->
                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="page-header">
                                        <ul class="breadcrumb">
                                            <li>
                                                <i class="fa fa-home"></i>
                                                <a href="<%=request.getContextPath()%>"><tiles:getAsString name="firstNavigation" /></a>
                                            </li>
                                            <li><tiles:getAsString name="currentPage" /></li>
                                        </ul>
                                        <div class="clearfix">
                                            <h3 class="content-title pull-left"><tiles:getAsString name="pageTitle" /></h3>
                                        </div>
                                        <div class="description"><tiles:getAsString name="pageDesc" /></div>
                                    </div>
                                </div>
                            </div>
                            <!-- /PAGE HEADER -->
                            <!-- DASHBOARD CONTENT -->

                            <tiles:insert attribute="body" />
                            
                        </div><!-- /CONTENT-->
                    </div>
                </div>
            </div>
        </section>
        <!--/PAGE -->
        <jsp:include page="footer.jsp" ></jsp:include>
    </body>

</html:html>
