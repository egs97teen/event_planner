<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Welcome</title>
<!-- 	<link rel="stylesheet" type="text/css" href="css/loginRegStyle.css"> -->
</head>
<body>
	<h1>Welcome</h1>
	
	<c:if test="${logoutMessage != null}">
		<p>${logoutMessage}</p>
	</c:if>
	
	<div id="register">
		<h2>Register</h2>
		<p><form:errors path="user.*"/></p>
		<form:form method="POST" action="/registration" modelAttribute="user">
			<p>
				<form:label path="first_name">First Name
				<form:input path="first_name"/></form:label>
			</p>
			<p>
				<form:label path="last_name">Last Name
				<form:input path="last_name"/></form:label>
			</p>
			<p>
				<form:label path="email">Email
				<form:input path="email"/></form:label>
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
			<p>
				<form:label path="password">Password
				<form:password path="password"/></form:label>
			</p>
			<p>
				<form:label path="passwordConfirmation">Password Confirmation
				<form:password path="passwordConfirmation"/></form:label>
			</p>
			<input type="submit" value="Register">
		</form:form>
	</div>
	
	<div id="login">
		<h2>Login</h2>
		<c:if test="${errorMessage != null}">
			<p>${errorMessage}</p>
		</c:if>
	    <form method="POST" action="/login">
	        <p>
	            <label for="email">Email</label>
	            <input type="text" id="email" name="username"/>
	        </p>
	        <p>
	            <label for="password">Password</label>
	            <input type="password" id="password" name="password"/>
	        </p>
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input type="submit" value="Login"/>
	    </form>
	</div>
</body>
</html>