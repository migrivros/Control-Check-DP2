
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

<acme:form>

	<acme:input-textbox code="patron.patronage-report.form.label.sequenceNumber" path="sequenceNumber"/>
	<acme:input-textbox code="patron.patronage-report.form.label.creationMoment" path="creationMoment"/>
	<acme:input-textarea code="patron.patronage-report.form.label.memorandum" path="memorandum"/>
	<acme:input-textbox code="patron.patronage-report.form.label.moreInfo" path="moreInfo"/>
	<acme:input-textarea code="patron.patronage-report.form.label.patronage.legalStuff" path="patronage.legalStuff"/>
	<acme:input-money code="patron.patronage-report.form.label.patronage.budget" path="patronage.budget"/>
	

</acme:form> 