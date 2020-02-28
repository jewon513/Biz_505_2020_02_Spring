<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />

<c:forEach items="${COMMENTLIST}" var="vo">
	<div class="comment-item" data-id="${vo.c_id}">
		<div class="d-flex align-items-center">
			<h4>${vo.c_writer}</h4>
			<small class="ml-2">${vo.c_date_time}</small>
			<small class="ml-2 comment-item-delete text-danger">&times;</small>
		</div>
		<p class="pl-3 pr-3">${vo.c_comment}</p>
	</div>
</c:forEach>
