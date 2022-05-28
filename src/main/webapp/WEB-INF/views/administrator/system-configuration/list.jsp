<%--
- list.jsp
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

<acme:list>
	<acme:list-column code="administrator.systemConfiguration.list.label.systemCurrency" path="systemCurrency" width="10%"/>
	<acme:list-column code="administrator.systemConfiguration.list.label.acceptedCurrencies" path="acceptedCurrencies" width="10%"/>
	<acme:list-column code="administrator.systemConfiguration.list.label.strongSpam" path="strongSpam" width="40%"/>
	<acme:list-column code="administrator.systemConfiguration.list.label.strongThreshold" path="strongThreshold" width="40%"/>
	<acme:list-column code="administrator.systemConfiguration.list.label.weakSpam" path="weakSpam" width="40%"/>
	<acme:list-column code="administrator.systemConfiguration.list.label.weakThreshold" path="weakThreshold" width="40%"/>
</acme:list>