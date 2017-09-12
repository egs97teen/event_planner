<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Events</title>
<!-- 	<link rel="stylesheet" type="text/css" href="css/eventsViewStyle.css"> -->
</head>
<body>

	<a href="/events">Home</a>
	
	<h1>${event.name}</h1>
	<h3>Host: ${event.host.first_name} ${event.host.last_name}</h3>
	<h3>Date: ${event.dateFormat()}</h3>
	<h3>Location: ${event.city}, ${event.state}</h3>
	<h3>People who are attending the event: ${event.users.size() + 1}</h3>
	
	<table>
		<tr>
			<th>Name</th>
			<th>Location</th>
		</tr>
		<c:forEach var="attendee" items="${event.users}">
			<tr>
				<td>${attendee.first_name} ${attendee.last_name}</td>
				<td>${attendee.city}</td>
			</tr>
		</c:forEach>
	</table>
	
	<h2>Message Wall</h2>
	<div>
		<c:forEach var="comment" items="${comments}">
			<p>${comment.commenter.first_name} ${comment.commenter.last_name}: ${comment.content}</p>
		</c:forEach>
	</div>

	<p><form:errors path="comment.*"/></p>
	<form:form method="POST" action="/addComment" modelAttribute="comment">
		<form:hidden path="event.id" value="${event.id}"/>
		<p>
			<form:label path="content">Add Comment:
			<form:input path="content"/></form:label>
		</p>
		<input type="submit" value="Submit">
	</form:form>
</body>
</html>