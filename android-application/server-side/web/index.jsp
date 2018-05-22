<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.vishrant.db.DBHelper"%>
<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<link rel="stylesheet" href="css/style.css">
<title>Home</title>
</head>

<body style="margin: 0px;">

	<!-- HEADER -->
	<div id="header">
		<div class="titlePage">Customer Feedback</div>
	</div>

	<!-- CONTENT -->
	<div id="content">
		<div class="middleAlignDiv"
			style="border: 1px #000 solid; padding: 20px;">

			<form name="addNewFeedback" action="addNewFeedback">


				<table>
					<tr style="text-align: center;">
						<th style="text-align: center; font-size: 20px; padding: 5px;">ADD
							NEW FEEDBACK</th>
					</tr>
					<tr>
						<td>USER NAME:</td>
						<td><select name="userId">

								<%
									Connection connection = null;
									Statement stmt = null;
									ResultSet rs = null;
									try {

										connection = DBHelper.getConnection();
										stmt = connection.createStatement();
										String sql = "SELECT USER_NAME FROM APP_USER where USER_TYPE = 'C'";

										rs = stmt.executeQuery(sql);

										while (rs.next()) {
								%><option><%=rs.getString("USER_NAME")%></option>
								<%
									}

									} catch (Exception e) {

									} finally {

									}
								%>
						</select></td>
					</tr>
					<tr>
						<td>FEEDBACK_TYPE:</td>
						<td><select name="feedbackType">
								<option>ADHOC</option>
								<option>CSAT</option>
						</select></td>
					</tr>
					<tr>
						<td>STATUS:</td>
						<td><input type="text" value="NEW" name="status"
							readonly="readonly" style="border: none;"></td>
					</tr>
					<tr>
						<td>PROJECT:</td>
						<td><select name="projectName">
								<%
									String projectNames = "SELECT PROJECT_NAME " +
														  "  FROM PROJECTS P " +
														  "		, APP_USER AU " +
														  "	WHERE p.client_id = au.user_name " +
														  "	  AND au.user_type = 'C'";
									rs = stmt.executeQuery(projectNames);

									while (rs.next()) {
								%>
								<option><%=rs.getString("PROJECT_NAME")%></option>
								<%
									}
								%>
						</select></td>
					</tr>
					<tr>
						<td>APPLICABLE MONTH:</td>
						<td><input name="applicableMonth" type="text" onfocus="this.value=''" placeholder="MM/DD/YY" value="MM/DD/YY"
							id="dateValue"></td>
					</tr>
					<tr style="margin-top: 10px;">
						<td align="right"><input type="submit" value="Submit" /></td>
						<td><input style="margin-left: 5px;" type="reset"></td>
					</tr>
				</table>

			</form>

			<form name="deleteEveryThing" action="deleteEveryThing" style="margin-top: 20px; float: right; text-align: right;">
				<input type="submit" value="Delete Every Thing">
			</form>

		</div>
	</div>

</body>

</html>
