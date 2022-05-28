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
    <acme:list-column code="inventor.item-quantity.list.label.quantity" path="quantity" width="15%"/>
    <acme:list-column code="inventor.item-quantity.list.label.item-name" path="item.name" width="20%"/>
    <acme:list-column code="inventor.item-quantity.list.label.item-code" path="item.code" width="20%"/>
    <acme:list-column code="inventor.item-quantity.list.label.item-type" path="item.type" width="15%"/>
    <acme:list-column code="inventor.item-quantity.list.label.item-technology" path="item.technology" width="15%"/>
    <acme:list-column code="inventor.item-quantity.list.label.item-retailPrice" path="item.retailPrice" width="15%"/>
</acme:list>

<jstl:if test="${type == 'TOOL' && draftMode == true}">
	<acme:button code="inventor.item-quantity.button.addTool" action="/inventor/item-quantity/create?type=TOOL&masterId=${masterId}"/>
</jstl:if>

<jstl:if test="${type == 'COMPONENT' && draftMode == true}">
	<acme:button code="inventor.item-quantity.button.addComponent" action="/inventor/item-quantity/create?type=COMPONENT&masterId=${masterId}"/>
</jstl:if>
