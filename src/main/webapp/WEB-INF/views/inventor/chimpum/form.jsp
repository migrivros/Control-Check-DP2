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

	<jstl:if test="${acme:anyOf(command, 'create')}">
		<acme:input-textbox code="inventor.chimpum.form.label.pattern" path="pattern" placeholder="AB-CC"/>
	</jstl:if>

	<jstl:if test="${acme:anyOf(command, 'show, update, delete')}">
		<acme:input-textbox code="inventor.chimpum.form.label.code" placeholder="AB-CC-220513" path="code" readonly="true"/>
	</jstl:if>
	
	<acme:input-textbox code="inventor.chimpum.form.label.title" path="title"/>
	<acme:input-textarea code="inventor.chimpum.form.label.description" path="description"/>
	<acme:input-money code="inventor.chimpum.form.label.budget" path="budget"/>
	<acme:input-moment code="inventor.chimpum.form.label.startDate" path="startDate"/>
	<acme:input-moment code="inventor.chimpum.form.label.endDate" path="endDate"/>
	<acme:input-url code="inventor.chimpum.form.label.link" path="link"/>
	
	<jstl:if test="${acme:anyOf(command, 'show, update, delete') && isPublishedItem == 'false'}">
		<br>
		<h1>---------------------------------------------------------------------</h1>
		<br>
		<acme:input-textbox code="inventor.chimpum.form.label.ArtefactName" path="ArtefactName" readonly="true"/>
		<acme:input-textbox code="inventor.chimpum.form.label.ArtefactCode" placeholder="AAA-000-A" path="ArtefactCode" readonly="true"/>
		<acme:input-textbox code="inventor.chimpum.form.label.ArtefactTechnology" path="ArtefactTechnology" readonly="true"/>
		<acme:input-textarea code="inventor.chimpum.form.label.ArtefactDescription" path="ArtefactDescription" readonly="true"/>
		<acme:input-money code="inventor.chimpum.form.label.ArtefactRetailPrice" path="ArtefactRetailPrice" readonly="true"/>
		<acme:input-url code="inventor.chimpum.form.label.ArtefactMoreInfo" path="ArtefactMoreInfo" readonly="true"/>
	</jstl:if>
	
	<jstl:if test="${command == 'create'}">
		<acme:submit code="inventor.chimpum.form.button.create" action="/inventor/chimpum/create?itemId=${itemId}"/>
	</jstl:if>
	
	<jstl:if test="${acme:anyOf(command, 'show, update, delete') && isPublishedItem == 'false'}">
			<acme:submit code="inventor.chimpum.form.button.update" action="/inventor/chimpum/update"/>
			<acme:submit code="inventor.chimpum.form.button.delete" action="/inventor/chimpum/delete"/>
	</jstl:if>
	
</acme:form> 
