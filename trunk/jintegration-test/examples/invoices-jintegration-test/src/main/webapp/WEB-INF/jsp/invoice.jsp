<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Invoice Resume</title>
</head>

<%@ page import="org.jsemantic.services.examples.energy.model.Invoice"%>

<%

Invoice invoice = (Invoice)request.getAttribute("invoice");

%>

<body>
<h2>Invoice Resume</h2>

<p>

<form action="/tariff/app/invoice" method="GET">

<table>	
	<tbody>
		<tr>
			<td>Invoice Number: <%=invoice.getNumber()%></td>
		</tr>
		<tr>
			<td>&nbsp</td>
		</tr>
		<tr>
			<td>Customer Number: <%=invoice.getCustomer().getId()%></td>
		</tr>
		<tr>
			<td>&nbsp</td>
		</tr>
		<tr>
			<td>Tariff: <%=invoice.getCustomer().getTariff()%></td>
		</tr>
		<tr>
			<td>&nbsp</td>
		</tr>
		
		<tr>
			<td>Total: <%=invoice.getTotal()%></td>
		</tr>
		<tr>
			<td>&nbsp</td>
		</tr>
		<tr>
			<td><input type="submit" value="Generate"/></td>
		</tr>
		
	</tbody>
	
</table>

</form>

</body>
</html>
