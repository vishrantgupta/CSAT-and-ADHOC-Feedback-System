<%@page import="com.vishrant.cf.bean.ApplicationBean"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!DOCTYPE html>
<html:html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="com.vishrant.cf.application" /></title>

        <script type="text/javascript" src="<%=request.getContextPath()%>/js/eldarion-ajax/eldarion-ajax.min.js"></script>
        <script type="text/javascript" src="./js/applicationOperation.js"></script>
        
    </head>
    <body>

        <div class="art-body-welcome col-lg-10 col-sm-10 col-xs-12" style="padding-left: 5px; padding-right: 5px;">

            <div id="wrapper" class="form-group">
                <html:form style="text-align: left" styleClass="form ajax" method="post" action="/page/application.do?action=">
                    <div class="form-group">
                    <fieldset>

                        <table class="tableStyle table-condensed" style="width: 100%;">
                            
                            <tr id="" class="pull-left" style="width: 100%; margin-top: 10px; margin-bottom: 20px">
                                <td colspan="10" class="columnLabel">
                                    
                                    <div class="col-lg-5 col-md-5 col-sm-5 pull-left" style="padding-right: 0px;">
                                        <span style="vertical-align: middle; padding-top: 10px;">
                                            <bean:message key="com.vishrant.cf.engagement" />
                                            <bean:message key="form.single.space" />
                                        </span>
                                    </div>
                                    
                                    <div class="col-lg-5 col-md-5 col-sm-5">
                                        <html:select styleId="" style="width: 50% auto;" property="engagement" styleClass="selectBox form-control" onchange="populateApplicaitons(this)">
                                            <html:options property="engagementValueList" labelProperty="engagementNameList"/>
                                        </html:select>
                                    </div>
                                </td>
                                
                            </tr>
                            
                            <logic:present name="application" scope="request">
                                <tr>
                                    <td>
                                        <span class="question-category">Available Applications</span>
                                    </td>
                                </tr>

                                <tr style="height: 10px;">
                                    <td></td>
                                </tr>

                                <tr>
                                    <td>
                                        <table id="applicationTable" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
                                            <thead>
                                                <tr>
                                                    <!--<th>Engagement</th>-->
                                                    <th>Application Name</th>
                                                    <th>Client Name</th>
                                                </tr>
                                            </thead>

                                            <logic:iterate name="application" id="applicationId" property="availableApplications">
                                                <tr>
                                                    <!--<td>
                                                        <bean:write name="applicationId" property="engagementName"/>
                                                    </td>-->
                                                    <td>
                                                        <bean:write name="applicationId" property="appName"/>
                                                    </td>
                                                    <td>
                                                        <bean:write name="applicationId" property="clientName"/>
                                                    </td>
                                                </tr>
                                            </logic:iterate>
                                        </table>
                                    </td>
                                </tr>
                            </logic:present>

                            <tr>
                                <td>
                                    <input id="addApplication" type="button" class="btn btn-primary inline" value="Add New" />
                                    <%--<input id="editApplication" type="button" class="btn btn-primary inline" value="Edit" />--%>
                                </td>
                            </tr>

                            <tr style="height: 30px">
                                <td></td>
                            </tr>
                            
                            <tr id="addApplicationHeading" style="display: none;" class="text-center question-category">
                                <td><strong><bean:message key="com.vishrant.cf.newApplication" /></strong></td>
                            </tr>
                            
                            <%--<tr id="editApplicationHeading" style="display: none;" class="text-center question-category">
                                <td><strong><bean:message key="com.vishrant.cf.editApplication" /></strong></td>
                            </tr>--%>
                            
                            <tr id="engagementTr" class="pull-left" style="width: 100%; display: none; margin-top: 10px;">
                                <td colspan="10" class="columnLabel">
                                    
                                    <div class="col-lg-5 col-md-5 col-sm-5 pull-left" style="padding-right: 0px;">
                                        <span style="vertical-align: middle; padding-top: 5px;">
                                            <bean:message key="com.vishrant.cf.engagement" />
                                            <bean:message key="form.single.space" />
                                        </span>
                                    </div>
                                    
                                    <div class="col-lg-5 col-md-5 col-sm-5">
                                        <html:select styleId="addAppEngagementId" style="width: 50% auto;" property="engagement" styleClass="selectBox form-control" onchange="populateClientName(this)">
                                            <html:options property="engagementValueList" labelProperty="engagementNameList"/>
                                        </html:select>
                                    </div>
                                </td>
                                
                            </tr>
                            
                            <tr style="height: 15px">
                                <td></td>
                            </tr>
                            
                            <%--<tr  id="editEngagementTr" class="center" style="width: 100%; display: none; margin-top: 10px;">
                                <td colspan="10" class="columnLabel">
                                    
                                    <div class="col-lg-5 col-md-5 col-sm-5 pull-left" style="padding-right: 0px;">
                                        <span style="vertical-align: middle; padding-top: 5px;">
                                            <bean:message key="com.vishrant.cf.engagement" />
                                            <bean:message key="form.single.space" />
                                        </span>
                                    </div>
                                    
                                    <div class="col-lg-5 col-md-5 col-sm-5 pull-left">
                                        <html:select styleId="editAppEngagementId" style="width: 50% auto;" property="engagement" styleClass="selectBox form-control" onchange="loadApplications(this)">
                                            <html:options property="engagementValueList" labelProperty="engagementNameList"/>
                                        </html:select>
                                    </div>
                                    
                                </td>
                            </tr>
                            
                            <tr  id="editApplicationTr" class="center" style="width: 100%; display: none; margin-top: 10px;">
                                <td colspan="10" class="columnLabel">
                                    
                                    <div class="col-lg-5 col-md-5 col-sm-5 pull-left" style="padding-right: 0px;">
                                        <span style="vertical-align: middle; padding-top: 5px;">
                                            <bean:message key="com.vishrant.cf.application" />
                                            <bean:message key="form.single.space" />
                                        </span>
                                    </div>
                                    
                                    <div class="col-lg-5 col-md-5 col-sm-5 pull-left">
                                        <select id="editAppSelectBox" style="width: 50% auto;" class="selectBox form-control" onchange="getApplicationDetails(this)">
                                            <option>Select Application</option>
                                        </select>
                                    </div>
                                    
                                </td>
                            </tr>
                            
                            <tr id="editApplicationTbl" class="pull-left" style="width: 100%; margin-top: 10px;">
                                <td class="columnLabel">
                                    <table class="table table-bordered table-hover" id="">
                                        <thead>
                                            <tr>
                                                <th rowspan="2" class="text-center" style="vertical-align: middle;">Application Name</th>
                                                <th colspan="2" class="text-center">Client</th>
                                            </tr>
                                            <tr>
                                                <th class="text-center">First Name</th>
                                                <th class="text-center">Last Name</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <tr>
                                                <td><input type="hidden" id="editAppId" /><input type="text" name='application' id="editAppName" maxlength='50' placeholder='Application' class="form-control"/></td>
                                                <td><input type="text" name='firstName' id="editFirstName" placeholder='First Name' class="form-control"/></td>
                                                <td><input type="text" name='lastName' id="editLastName" placeholder='Last Name' class="form-control"/></td>
                                            </tr>
                                            <tr></tr>
                                        </tbody>

                                    </table>
                                    <input type="button" id="submitApplicationDtlBtn" class="btn btn-primary inline pull-right" onclick="udpateApplicationDetails()" value="Update" />
                                </td>
                            </tr>--%>
                            
                            <tr style="height: 15px;">
                                <td></td>
                            </tr>
                            
                            <tr id="newApplicationTr" style="display: none;">
                                <td colspan="10">
                                    <div class="row clearfix">
                                        <div class="col-md-12 column">
                                            <table class="table table-bordered table-hover" id="tab_logic">
                                                <thead>
                                                    <tr>
                                                        <th rowspan="1" class="text-center" style="vertical-align: middle;">#</th>
                                                        <th rowspan="1" class="text-center" style="vertical-align: middle;">Application Name</th>
                                                        <th colspan="1" class="text-center">Client Name</th>
                                                    </tr>
                                                    <!--<tr>
                                                        <th class="text-center">First Name</th>
                                                        <th class="text-center">Last Name</th>
                                                    </tr>-->
                                                </thead>

                                                <tbody>
                                                    
                                                    <tr id='addr0'>
                                                        <td style="width: 5%">1</td>
                                                        <td style="width: 50%"><input type="text" name='application'  maxlength='50' placeholder='Application' class="form-control row0"/></td>
                                                        <td style="text-align: center; width: 45%">
                                                            <!--<input type="text" name='firstName' placeholder='First Name' class="form-control row0"/>-->
                                                            <select id="clientName0" name="clientSelectBox" class="form-control row0" multiple="true" style="display: inline; max-width: 100%; overflow-y:scroll;"></select>
                                                        </td>
                                                        <!--<td><input type="text" name='lastName' placeholder='Last Name' class="form-control row0"/></td>-->
                                                    </tr>
                                                    <tr id='addr1'></tr>
                                                </tbody>

                                            </table>
                                        </div>
                                    </div>
                                    <a id="add_row" class="btn btn-default pull-left">Add Row</a>
                                    <a id='delete_row' class="pull-left btn btn-default" style="margin-left: 5px;">Delete Row</a>
                                    <input type="button" id="submitApplicationDtlBtn" class="btn btn-primary inline pull-right" onclick="submitApplicationDetails()" value="Submit" />
                                </td>
                            </tr>
                            
                        </table>
                        
                    </fieldset>
                    </div>
                </html:form>
                
            </div>

        </div>

    </body>
</html:html>