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
	<acme:input-textbox code="any.item.form.label.name" path="name"/>
	<acme:input-textbox code="any.item.form.label.code" path="code"/>
	<acme:input-textbox code="any.item.form.label.technology" path="technology"/>
	<acme:input-textarea code="any.item.form.label.description" path="description"/>
	<acme:input-money code="any.item.form.label.retailPrice" path="retailPrice"/>
	<acme:input-money code="any.item.form.label.convertedPrice" path="convertedPrice"/>
	<acme:input-url code="any.item.form.label.more-info" path="moreInfo"/>
	
	<jstl:if test="${command == 'show' && hasChimpum == 'true'}">
		<br>
		<h1>---------------------------------------------------------------------</h1>
		<br>
		<acme:input-textbox code="any.item.form.label.chimpumTitle" path="chimpumTitle" readonly="true"/>
		<acme:input-textarea code="any.item.form.label.chimpumDescription" path="chimpumDescription" readonly="true"/>
		<acme:input-money code="any.item.form.label.chimpumBudget" path="chimpumBudget" readonly="true"/>
		<acme:input-textbox code="any.item.form.label.chimpumStartDate" path="chimpumStartDate" readonly="true"/>
		<acme:input-textbox code="any.item.form.label.chimpumEndDate" path="chimpumEndDate" readonly="true"/>
		<acme:input-url code="any.item.form.label.chimpumLink" path="chimpumLink" readonly="true"/>
	</jstl:if>
	
</acme:form>

<jstl:if test="${type == 'COMPONENT' && command == 'show'  && isInventor == 'true' && hasChimpum == 'false'}">
		<acme:button code="any.item.form.button.associate-chimpum" action="/inventor/chimpum/create?itemId=${itemId}"/>
</jstl:if>
