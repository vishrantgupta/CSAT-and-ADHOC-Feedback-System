<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.vishrant.cf.bean.QuestionDetail"%>
<%@page import="com.vishrant.cf.bean.Question"%>
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
        <title><bean:message key="com.vishrant.cf.captureTitle" /></title>

        <style>
            /*#feedbackTable tr td {
                padding-bottom: 20px;
            }*/
        </style>

        <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/custom/jquery.ratings.css" />
        
        <script src="<%=request.getContextPath()%>/js/custom/jquery.ratings.js"></script>
        <script type="text/javascript" src="./js/captureFeedback.js"></script>

    </head>
    <body>

        <!--HOME BODY TEXT-->
        <div class="art-body-welcome col-lg-10 col-sm-10 col-xs-12" style="padding-left: 5px; padding-right: 5px;">

            <div id="wrapper" class="form-group">
                <html:form style="text-align: left" action="/page/capture.do?action=submitFeedback">
                    <div class="form-group">
                    <fieldset>
                        

                        <table class="tableStyle table-condensed" style="width: 100%;">
                            <tr>
                                <td style="white-space: nowrap; text-align: right;" class="columnLabel">
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
                                <td class="columnLabel" style="text-align: right;"></td>
                                <td>
                                    <html:radio styleId="incidentIdRadio" property="feedbackTypeRadio" value="incident" onclick="showAppName(this)" />
                                    <label for="incidentIdRadio" class="inline control-label columnLeft"><bean:message key="com.vishrant.cf.incident"/><bean:message key="form.space"/></label>
                                    
                                    <html:radio styleId="eventIdRadio" property="feedbackTypeRadio" value="event" onclick="showAppName(this)" />
                                    <label for="eventIdRadio" class="inline control-label columnRight"><bean:message key="com.vishrant.cf.event"/></label>
                                </td>
                            </tr>

                            <tr id="appNameTr">
                                <td style="white-space: nowrap; text-align: right;" class="columnLabel">
                                    <bean:message key="com.vishrant.cf.appName" />
                                    <bean:message key="form.single.space" />
                                </td>
                                <td>
                                    <html:select styleId="selectedAppNameId" style="width: 50% auto; max-width: 250px;" property="selectedAppName" styleClass="selectBox form-control" onchange="showClientName()">
                                        <html:options property="appValueList" labelProperty="appNameList"/>
                                    </html:select>
                                </td>
                            </tr>
                            
                            <tr height="7px">
                                <td></td>
                            </tr>

                            <tr id="clientNameTr" style="display: none; vertical-align: top">
                                <td style="white-space: nowrap; text-align: right;" class="columnLabel"><bean:message key="com.vishrant.cf.clientName" /><bean:message key="form.space"/></td>
                                <td id="clientNameTd" style="text-align: left;"></td>
                            </tr>

                            <tr height="7px">
                                <td></td>
                            </tr>

                            <tr id="descriptionIdTr" style="display: none; vertical-align: top">
                                <td style="text-align: right; vertical-align: middle" class="columnLabel">
                                    <bean:message key="com.vishrant.cf.description" /><bean:message key="form.space"/>
                                </td>
                                <td>
                                    <html:text styleClass="form-control" style="width: 50% auto; max-width: 250px;" maxlength="150" styleId="descriptionId" property="description"></html:text>
                                    </td>
                                </tr>

                                <tr height="10px">
                                    <td></td>
                                </tr>

                                <tr id='feedbackTableTr' style='display: none;'>
                                    <td colspan='10' align='left'>
                                        <table id="feedbackTable" class="table-condensed" style='text-align: left;'></table>
                                    </td>
                                </tr>

                            </table>

                        </fieldset>
                    </div>
                </html:form>
                
                <div id="wait" style="display: none;">
                    <span><img src='<%=request.getContextPath()%>/img/loaders/4.gif' /></span>
		</div>
            </div>

        </div>
        <div class="art-body-details">

        </div>
        <!--HOME BODY TEXT ENDS-->

    </body>
</html:html>
