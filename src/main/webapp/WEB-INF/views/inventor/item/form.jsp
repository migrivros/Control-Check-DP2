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

	<acme:input-textbox code="inventor.item.form.label.name" path="name"/>
	<jstl:if test="${acme:anyOf(command, 'show, update, delete, publish')}">
			<acme:input-textbox code="inventor.item.form.label.code" placeholder="ABC-123-A" path="code" readonly="true"/>
	</jstl:if>
	<jstl:if test="${command == 'create'}">
			<acme:input-textbox code="inventor.item.form.label.code" placeholder="ABC-123-A" path="code"/>
	</jstl:if>
	<acme:input-textbox code="inventor.item.form.label.technology" path="technology"/>
	<acme:input-textarea code="inventor.item.form.label.description" path="description"/>
	<acme:input-money code="inventor.item.form.label.retailPrice" path="retailPrice"/>
	<jstl:if test="${command == 'show'}">
	<acme:input-money code="inventor.item.form.label.convertedPrice" path="convertedPrice" readonly="true"/>
	</jstl:if>
	<acme:input-url code="inventor.item.form.label.more-info" path="moreInfo"/>

	<jstl:if test="${command == 'show' && hasChimpum == 'true'}">
		<br>
		<h1>---------------------------------------------------------------------</h1>
		<br>
		<acme:input-textbox code="inventor.item.form.label.chimpumCode" path="chimpumCode" readonly="true"/>
		<acme:input-textbox code="inventor.item.form.label.chimpumTitle" path="chimpumTitle" readonly="true"/>
		<acme:input-textarea code="inventor.item.form.label.chimpumDescription" path="chimpumDescription" readonly="true"/>
		<acme:input-money code="inventor.item.form.label.chimpumBudget" path="chimpumBudget" readonly="true"/>
		<acme:input-textbox code="inventor.item.form.label.chimpumStartDate" path="chimpumStartDate" readonly="true"/>
		<acme:input-textbox code="inventor.item.form.label.chimpumEndDate" path="chimpumEndDate" readonly="true"/>
		<acme:input-url code="inventor.item.form.label.chimpumLink" path="chimpumLink" readonly="true"/>
	</jstl:if>
	
	<jstl:if test="${command == 'create'}">
		<acme:submit code="inventor.item.form.button.create" action="/inventor/item/create?type=${type}"/>
	</jstl:if>
	
	<jstl:if test="${acme:anyOf(command, 'show, update, delete, publish') && published == 'false'}">
		<acme:submit code="inventor.item.form.button.update" action="/inventor/item/update?type=${type}"/>
		<acme:submit code="inventor.item.form.button.publish" action="/inventor/item/publish?type=${type}"/>
		<acme:submit code="inventor.item.form.button.delete" action="/inventor/item/delete"/>
	</jstl:if>
	
	<jstl:if test="${type == 'COMPONENT' && command == 'show' && hasChimpum == 'false' && published == 'false'}">
		<acme:button code="inventor.item.form.button.associate-chimpum" action="/inventor/chimpum/create?itemId=${itemId}"/>
	</jstl:if>
	
</acme:form> 


