<%--
- form.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:input-textbox code="authenticated.announcement.form.label.title" path="title"/>	
	<acme:input-select code="authenticated.announcement.form.label.critical" path="critical">
		<acme:input-option code="NOT CRITICAL" value="NOT CRITICAL" selected="${critical == false}"/>
		<acme:input-option code="CRITICAL" value="CRITICAL" selected="${critical == true}"/>
	</acme:input-select>
	<acme:input-textarea code="authenticated.announcement.form.label.body" path="body"/>
	<acme:input-email code="authenticated.announcement.form.label.email" path="email"/>
	<acme:input-url code="authenticated.announcement.form.label.moreInfo" path="moreInfo"/>

</acme:form>
