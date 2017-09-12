<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Edit Event</title>
<!-- 	<link rel="stylesheet" type="text/css" href="css/eventsViewStyle.css"> -->
</head>
<body>
	<h1>${editEvent.name}</h1>
	<h2>Edit Event</h2>
	<p><form:errors path="editEvent.*"/></p>
	<form:form method="POST" action="/edit" modelAttribute="editEvent">
		<form:hidden path="id" value="${editEvent.id}"/>
		<p>
			<form:label path="name">Name</form:label>
			<form:input path="name" value="${editEvent.name}"/>
		</p>
		<p>
			<label>Date</label>
			<input name="date" type="date" value="${editEvent.getDateString()}">
		</p>
		<p>
			<form:label path="city">City</form:label>
			<form:input path="city" value="${editEvent.city}"/>
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
		<input type="submit" value="Edit">
	</form:form>
</body>
</html>