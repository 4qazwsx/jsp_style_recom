<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:if test="${result > 0 }">
	<script type="text/javascript">
		alert("로그인 되었습니다.환영 합니다!");  
		location.href="updateForm.do?mem_id="+"${mem_id}";
		
	</script>
</c:if>
<c:if test="${result == 0 }">  
	<script type="text/javascript">
		alert("로그인 실패입니다.");  
		location.href="joinForm.do";
	</script>
</c:if>
<c:if test="${result == -1 }">  
	<script type="text/javascript">
		alert("회원가입 부탁 드립니다.");  
		location.href="joinForm.do";
	</script>
</c:if>




</body>
</html>