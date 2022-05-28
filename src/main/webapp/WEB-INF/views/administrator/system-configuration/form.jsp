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

	<acme:input-textbox code="administrator.systemConfiguration.form.label.systemCurrency" path="systemCurrency"/>
	<acme:input-textbox code="administrator.systemConfiguration.form.label.acceptedCurrencies" path="acceptedCurrencies"/>
	<acme:input-textbox code="administrator.systemConfiguration.form.label.strongSpam" path="strongSpam"/>
	<acme:input-double code="administrator.systemConfiguration.form.label.strongThreshold" path="strongThreshold"/>
	<acme:input-textbox code="administrator.systemConfiguration.form.label.weakSpam" path="weakSpam"/>
	<acme:input-double code="administrator.systemConfiguration.form.label.weakThreshold" path="weakThreshold"/>
	
	<acme:submit test="${acme:anyOf(command, 'show, update')}" code="administrator.systemConfiguration.form.button.update" action="/administrator/system-configuration/update"/>

</acme:form>