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
 <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

 <acme:form>
 	<acme:input-textbox code="patron.dashboard.form.label.numberOfAcceptedPatronages" path="numberOfAcceptedPatronages"/>
 	<acme:input-money code="patron.dashboard.form.label.maxAcceptedPatronages" path="maxAcceptedPatronages"/>
 	<acme:input-money code="patron.dashboard.form.label.minAcceptedPatronages" path="minAcceptedPatronages"/>
 	<acme:input-money code="patron.dashboard.form.label.averageAcceptedPatronages" path="averageAcceptedPatronages"/>
 	<acme:input-money code="patron.dashboard.form.label.deviationAcceptedPatronages" path="deviationAcceptedPatronages"/>
 	<acme:input-textbox code="patron.dashboard.form.label.numberOfDeniedPatronages" path="numberOfDeniedPatronages"/>
 	<acme:input-money code="patron.dashboard.form.label.maxDeniedPatronages" path="maxDeniedPatronages"/>
 	<acme:input-money code="patron.dashboard.form.label.minDeniedPatronages" path="minDeniedPatronages"/>
 	<acme:input-money code="patron.dashboard.form.label.averageDeniedPatronages" path="averageDeniedPatronages"/>
 	<acme:input-money code="patron.dashboard.form.label.deviationDeniedPatronages" path="deviationDeniedPatronages"/>
 	<acme:input-textbox code="patron.dashboard.form.label.numberOfProposedPatronages" path="numberOfProposedPatronages"/>
 	<acme:input-money code="patron.dashboard.form.label.maxProposedPatronages" path="maxProposedPatronages"/>
 	<acme:input-money code="patron.dashboard.form.label.minProposedPatronages" path="minProposedPatronages"/>
 	<acme:input-money code="patron.dashboard.form.label.averageProposedPatronages" path="averageProposedPatronages"/>
 	<acme:input-money code="patron.dashboard.form.label.deviationProposedPatronages" path="deviationProposedPatronages"/>
 </acme:form> 