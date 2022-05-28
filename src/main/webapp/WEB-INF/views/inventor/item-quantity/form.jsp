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
	<jstl:choose>
		<jstl:when test="${command == 'show' or command =='update'}">
			<jstl:if test="${type == 'TOOL'}">
				<acme:input-textbox code="inventor.item-quantity.form.label.quantity" path="quantity" readonly="true"/>
			</jstl:if>
			<jstl:if test="${type == 'COMPONENT'}">
				<acme:input-textbox code="inventor.item-quantity.form.label.quantity" path="quantity"/>
			</jstl:if>
			<acme:input-textbox code="inventor.item.form.label.name" path="item.name" readonly="true"/>
			<acme:input-textbox code="inventor.item.form.label.code" placeholder="ABC-123-A" path="item.code" readonly="true"/>
			<acme:input-textbox code="inventor.item.form.label.type" path="item.type" readonly="true"/>
			<acme:input-textbox code="inventor.item.form.label.technology" path="item.technology" readonly="true"/>
			<acme:input-textarea code="inventor.item.form.label.description" path="item.description" readonly="true"/>
			<acme:input-money code="inventor.item.form.label.retailPrice" path="item.retailPrice" readonly="true"/>
			<acme:input-money code="inventor.item.form.label.convertedPrice" path="item.convertedPrice" readonly="true"/>
			<jstl:if test="${draftMode == true}">
				<acme:submit code="inventor.item-quantity.form.button.delete" action="/inventor/item-quantity/delete"/>
				<jstl:if test="${type == 'COMPONENT'}">
					<acme:submit code="inventor.item-quantity.form.button.update" action="/inventor/item-quantity/update"/>
				</jstl:if>
			</jstl:if>
		</jstl:when>
		<jstl:when test="${command == 'create'}">
			<jstl:if test="${type == 'TOOL' && draftMode == true}">
				<acme:input-integer code="inventor.item-quantity.form.label.quantity" path="quantity"  readonly="true"/>
			    <acme:input-select code="inventor.item-quantity.form.label.item" path="itemId">
			        <jstl:forEach items="${items}" var="item">
			            <acme:input-option code="${item.getCode()}   ${item.getName()}" value="${item.getId()}" selected="${item.getId() == itemId}"/>
			        </jstl:forEach>
			    </acme:input-select>
			    <acme:submit code="inventor.item-quantity.tool.button.create" action="/inventor/item-quantity/create?type=TOOL&masterId=${masterId}"/>
		    </jstl:if>
			<jstl:if test="${type == 'COMPONENT' && draftMode == true}">
				<acme:input-integer code="inventor.item-quantity.form.label.quantity" path="quantity"/>
			    <acme:input-select code="inventor.item-quantity.form.label.item" path="itemId">
			        <jstl:forEach items="${items}" var="item">
			            <acme:input-option code="${item.getCode()}   ${item.getName()}" value="${item.getId()}" selected="${item.getId() == itemId}"/>
			        </jstl:forEach>
			    </acme:input-select>
			    <acme:submit code="inventor.item-quantity.component.button.create" action="/inventor/item-quantity/create?type=COMPONENT&masterId=${masterId}"/>
		    </jstl:if>
    	</jstl:when>
    </jstl:choose>
</acme:form>