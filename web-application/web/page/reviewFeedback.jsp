<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<html:html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="com.vishrant.cf.reports" /></title>

        <script type="text/javascript" src="./js/reviewFeedback.js"></script>

        <link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/js/jquery-ui-1.10.3.custom/css/custom-theme/jquery-ui.css" >

        <script>
            $(function() {
                $(".datepicker").datepicker();
            });
        </script>

    </head>
    <body style="visibility: hidden">

        <!--HOME BODY TEXT-->
        <div class="art-body-welcome col-lg-10 col-sm-10 col-xs-12" style="padding-left: 5px; padding-right: 5px;">

            <div id="wrapper" class="form-group">
                <html:form style="text-align: left" action="/page/review.do?">
                    <div class="form-group">
                    <fieldset>

                        <table class="tableStyle table-condensed" style="width: 100%;">

                            <tr>
                                <td style="white-space: nowrap;" class="columnLabel">
                                    <bean:message key="com.vishrant.cf.engagement" />
                                    <bean:message key="form.single.space" />
                                </td>
                                <td>
                                    <html:select styleId="engagementId" style="width: 50% auto; max-width: 250px;" property="engagement" styleClass="selectBox form-control" onchange="showFeedbackTypeRadio(this)">
                                        <html:options property="engagementValueList" labelProperty="engagementNameList"/>
                                    </html:select>
                                </td>
                            </tr>

                            <tr height="2px">
                                <td></td>
                            </tr>
                            
                            <tr id="feedbackTypeRadioTr">

                                <td class="columnLabel"></td>
                                <td>
                                    <html:radio styleId="incidentIdRadio" property="feedbackTypeRadio" value="incident" onclick="showDateRange()" />
                                    <label for="incidentIdRadio" class="inline control-label columnLeft"><bean:message key="com.vishrant.cf.incident"/></label>
                                    
                                    <html:radio styleId="eventIdRadio" style="margin-left: 10px;" property="feedbackTypeRadio" value="event" onclick="showDateRange()" />
                                    <label for="eventIdRadio" class="inline control-label columnRight"><bean:message key="com.vishrant.cf.event"/></label>
                                </td>

                            </tr>

                            <tr id="dateRangeTr">
                                <td class="columnLabel">
                                    <bean:message key="com.vishrant.cf.dateRange" />
                                    <bean:message key="form.single.space" />
                                </td>
                                <td>
                                    <div class="col-lg-12 input-group">
                                        <input type="text" id="fromDate" placeholder="From Date" class="datepicker input-sm" onchange="clearReport()" readonly style="width: 85px; cursor: default" />
                                        <span style="margin: 5px;"><bean:message key="com.vishrant.cf.to" /></span>
                                        <input type="text" id="toDate" placeholder="To Date" class="datepicker input-sm" onchange="clearReport()" readonly style="width: 85px; cursor: default" />
                                        <a href="#" id="searchBtn" class="btn btn-default" onclick="showAppName()" style="margin-left: 5px; color: #000;">
                                            <span class="glyphicon glyphicon-search" style="font-size:18px;"></span>
                                        </a>
                                    </div>
                                </td>
                            </tr>

                            <tr height="2px">
                                <td></td>
                            </tr>
                            
                            <tr id="appNameTr" class="tableTr">
                                <td style="white-space: nowrap;" class="columnLabel">
                                    <bean:message key="com.vishrant.cf.appName" />
                                    <bean:message key="form.single.space" />
                                </td>
                                <td>
                                    <html:select styleId="selectedAppNameId" style="width: 50% auto; max-width: 250px;" styleClass="form-control" property="selectedAppName" onchange="showClientName()">
                                        <html:options property="appValueList" labelProperty="appNameList"/>
                                    </html:select>
                                </td>
                            </tr>

                            <tr id="clientNameTr" class="columnLabel" style="display: none; vertical-align: top;">
                                <td style="white-space: nowrap; text-align: right;"><bean:message key="com.vishrant.cf.clientName" /><bean:message key="form.space"/></td>
                                <td id="clientNameTd" style="text-align: left;"></td>
                            </tr>

                            <tr height="2px">
                                <td></td>
                            </tr>
                            
                            <tr id="incidentIdTr" style="display: none; vertical-align: top;">
                                <td style="text-align: right">
                                    <bean:message key="com.vishrant.cf.description" /><bean:message key="form.space"/>
                                </td>
                                <td>
                                    <html:select styleId="selectedIncidentId" styleClass="form-control" style="width: 50% auto; max-width: 250px;" property="description" onchange="showFeedback(this)">
                                        <html:options property="selectedIncidentIdList" labelProperty="descriptionList" />
                                    </html:select>
                                </td>
                            </tr>

                            <tr height="2px">
                                <td></td>
                            </tr>

                            <tr id='feedbackTableTr' class="tableTr" style='display: none;'>
                                <td colspan='2' align='left'>
                                    <table id="feedbackTable" style='text-align: left;'>
                                    </table>
                                </td>
                            </tr>

                        </table>

                    </fieldset>    
                </div>
                </html:form>
                
            </div>

        </div>
        <div class="art-body-details">

        </div>
        <!--HOME BODY TEXT ENDS-->
    </body>
</html:html>