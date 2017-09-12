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
	<h1>Welcome, ${currentUser.first_name}</h1>
	
	<form id="logoutForm" method="POST" action="/logout">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<input type="submit" value="Logout"/>
	</form>
	
	<h2>Here are some of the events in your state:</h2>
	<table>
		<tr>
			<th>Name</th>
			<th>Date</th>
			<th>Location</th>
			<th>Host</th>
			<th>Action/Status</th>
		</tr>
		<c:forEach var="row" items="${eventsInState}">
			<tr>
				<td><a href="/events/${row[0].id}">${row[0].name}</a></td>
				<td>${row[0].dateFormat()}</td>
				<td>${row[0].city}</td>
				<td>${row[0].host.first_name}</td>
				<c:if test="${row[0].host == currentUser}">
					<td><a href="/events/${row[0].id}/edit">Edit</a> | <a href="/delete/${row[0].id}">Delete</a></td>
				</c:if>
				<c:if test="${row[0].host != currentUser}">
					<c:if test="${!row[0].users.contains(currentUser)}">
						<td><a href="/join/${row[0].id}">Join</a></td>
					</c:if>
					<c:if test="${row[0].users.contains(currentUser)}">
						<td>Joined <a href="/cancel/${row[0].id}">Cancel</a></td>
					</c:if>
				</c:if>
			</tr>
		</c:forEach>
	</table>
	
	<h2>Here are some of the events in other states:</h2>
	<table>
		<tr>
			<th>Name</th>
			<th>Date</th>
			<th>Location</th>
			<th>State</th>
			<th>Host</th>
			<th>Action/Status</th>
		</tr>
		<c:forEach var="row" items="${eventsOutOfState}">
			<tr>
				<td><a href="/events/${row[0].id}">${row[0].name}</a></td>
				<td>${row[0].dateFormat()}</td>
				<td>${row[0].city}</td>
				<td>${row[0].state}</td>
				<td>${row[0].host.first_name}</td>
				<c:if test="${row[0].host == currentUser}">
					<td><a href="/events/${row[0].id}/edit">Edit</a> | <a href="/delete/${row[0].id}">Delete</a></td>
				</c:if>
				<c:if test="${row[0].host != currentUser}">
					<c:if test="${!row[0].users.contains(currentUser)}">
						<td><a href="/join/${row[0].id}">Join</a></td>
					</c:if>
					<c:if test="${row[0].users.contains(currentUser)}">
						<td>Joined <a href="/cancel/${row[0].id}">Cancel</a></td>
					</c:if>
				</c:if>
			</tr>
		</c:forEach>
	</table>
	
	<h2>Create an Event</h2>
	<p><form:errors path="event.*"/></p>
	<form:form method="POST" action="/createEvent" modelAttribute="event">
		<p>
			<form:label path="name">Name</form:label>
			<form:input path="name"/>
		</p>
		<p>
			<label>Date</label>
			<input name="date" type="date">
		</p>
		<p>
			<form:label path="city">City
			<form:input path="city"/></form:label>
		</p>
		<p>
			<form:select path="state">
				<option selected disabled>State</option>
				<form:option value="AZ">AZ</form:option>
				<form:option value="CA">CA</form:option>
				<form:option value="FL">FL</form:option>
				<form:option value="NY">NY</form:option>
				<form:option value="WA">WA</form:option>
			</form:select>
		</p>
		<input type="submit" value="Create Event">
	</form:form>
</body>
</html>