<%@ page
language="java"
contentType="text/html; charset=utf-8"
pageEncoding="utf-8"
%>
<jsp:useBean id="mrBean" class="com.blessedgeek.gwt.gdata.server.MrBean" scope="session"/>
<%
mrBean.setSheetDoc(request);
System.out.println("AuthToken=" + mrBean.AuthToken);
System.out.println("SessionAuthToken=" + mrBean.SessionAuthToken);
%>
<script type="text/javascript">
location.replace("TableMgr.jsp");
</script>