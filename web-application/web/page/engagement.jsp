<%@page import="com.vishrant.cf.bean.EngagementBean"%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<!DOCTYPE html>
<html:html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><bean:message key="com.vishrant.cf.engagement" /></title>

        <script type="text/javascript" src="<%=request.getContextPath()%>/js/eldarion-ajax/eldarion-ajax.min.js"></script>
        <script type="text/javascript" src="./js/engagement.js"></script>
        
    </head>
    <body>

        <!--HOME BODY TEXT-->
        <div class="art-body-welcome col-lg-10 col-sm-10 col-xs-12" style="padding-left: 5px; padding-right: 5px;">

            <div id="wrapper" class="form-group">
                <html:form style="text-align: left" styleClass="form" method="post" action="/page/engagement.do?action=">
                    <div class="form-group">
                    <fieldset>

                        <table class="tableStyle table-condensed" style="width: 100%;">
                            
                            <logic:present name="engagement" scope="request">
                                <tr>
                                    <td>
                                        <span class="question-category">Available Engagements</span>
                                    </td>
                                </tr>

                                <tr style="height: 10px;">
                                    <td></td>
                                </tr>

                                <tr>
                                    <td>
                                        <table id="engagementTable" class="table table-striped table-bordered" cellspacing="0" width="100%">
                                            <thead>
                                                <tr>
                                                    <th>Name</th>
                                                    <th>Description</th>
                                                </tr>
                                            </thead>

                                            <logic:iterate name="engagement" id="engagementId" property="availableEngagements">
                                                <tr>
                                                    <td>
                                                        <bean:write name="engagementId" property="engagementName"/>
                                                    </td>
                                                    <td>
                                                        <bean:write name="engagementId" property="description"/>
                                                    </td>
                                                </tr>
                                            </logic:iterate>
                                        </table>
                                    </td>
                                </tr>
                            </logic:present>

                            <tr>
                                <td>
                                    <input type="button" id="addEngagement" class="btn btn-primary inline" value="Add Engagement" />
                                    <input type="button" id="addClientDetails" class="btn btn-primary inline" style="margin-left: 15px" value="Add Client Details" />
                                </td>
                            </tr>

                            <tr id="addEngagementHeading" style="display: none;" class="text-center question-category">
                                <td><strong><bean:message key="com.vishrant.cf.newEngagement" /></strong></td>
                            </tr>

                            <tr id="addClientHeading" style="display: none;" class="text-center question-category">
                                <td><strong><bean:message key="com.vishrant.cf.newClientDetails" /></strong></td>
                            </tr>
                            
                            <tr id="addEngagementTbl" class="pull-left" style="width: 100%; margin-top: 10px; display: none;">
                                <td class="columnLabel">
                                    <table class="table table-bordered table-hover">
                                        <thead>
                                            <tr>
                                                <th class="text-center" style="vertical-align: middle;">Engagement Name</th>
                                                <th class="text-center">Description</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <tr>
                                                <td>
                                                    <input type="text" name='engagementName' id="addEngagementName" maxlength="50" placeholder='Engagement' class="form-control"/>
                                                </td>
                                                <td>
                                                    <input type="text" name='engagementDesc' id="addEngagementDesc" maxlength="150" placeholder='Description' class="form-control"/>
                                                </td>
                                            </tr>
                                            <tr></tr>
                                        </tbody>

                                    </table>
                                    <input type="button" class="btn btn-primary inline pull-right" onclick="saveNewEngagement()" value="Save" />
                                </td>
                            </tr>
                            
                            <tr id="addNewClientDetails" style="display: none;">
                                <td>
                                    <div class="row clearfix">
                                        <div class="col-md-12 column">
                                            <table class="table table-bordered table-hover" id="tab_logic">
                                                <thead>
                                                    <tr>
                                                        <th rowspan="2" class="text-center" style="vertical-align: middle;">#</th>
                                                        <th rowspan="2" class="text-center" style="vertical-align: middle;">Engagement Name</th>
                                                        <th colspan="2" class="text-center">Client</th>
                                                    </tr>
                                                    <tr>
                                                        <th class="text-center">First Name</th>
                                                        <th class="text-center">Last Name</th>
                                                    </tr>
                                                </thead>

                                                <tbody>
                                                    
                                                    <tr id='addr0'>
                                                        <td>1</td>
                                                        <td>
                                                            <select name="engagement" id="engagementSelectBox0" class="selectBox form-control engagementSelectBox" ></select>
                                                        </td>
                                                        <td><input type="text" name='firstName' maxlength="25" placeholder='First Name' class="form-control row0 cFirstName"/></td>
                                                        <td><input type="text" name='lastName' maxlength="25" placeholder='Last Name' class="form-control row0 cLastName"/></td>
                                                    </tr>
                                                    <tr id='addr1'></tr>
                                                </tbody>

                                            </table>
                                        </div>
                                    </div>
                                    <a id="add_row" class="btn btn-default pull-left">Add Row</a>
                                    <a id='delete_row' class="pull-left btn btn-default" style="margin-left: 5px;">Delete Row</a>
                                    <input type="button" id="submitApplicationDtlBtn" class="btn btn-primary inline pull-right" onclick="submitClientDetails()" value="Submit" />
                                </td>
                            </tr>
                            
                        </table>
                        
                    </fieldset>
                    </div>
                </html:form>
                
            </div>

        </div>
        <!--HOME BODY TEXT ENDS-->
    </body>
</html:html>