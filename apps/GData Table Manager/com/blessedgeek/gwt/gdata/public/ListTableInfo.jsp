<%@ page
contentType="text/html; charset=utf-8"
language="java"
extends="com.blessedgeek.gwt.gdata.server.TableMgrJspBeanable"
import="
java.util.List,
com.google.gdata.data.spreadsheet.TableEntry,
com.google.gdata.data.spreadsheet.Data,
com.google.gdata.data.spreadsheet.Column"
%><jsp:useBean id="mrBean" class="com.blessedgeek.gwt.gdata.server.MrBean" scope="session"/>
<%
TableEntry entry = mrBean.Table;
Data data = entry.getData();
%>{
"id":"<%=entry.getId()%>",
"title":"<%=entry.getTitle().getPlainText()%>",
"summary":"<%=entry.getSummary().getPlainText()%>",
"worksheet":"<%=entry.getWorksheet().getName()%>",
"header":"<%=entry.getHeader().getRow()%>",
"insertionMode":"<%=data.getInsertionMode().name()%>",
"startRow":"<%=data.getStartIndex()%>",
"numRows":"<%=data.getNumberOfRows()%>",
"columns":"<%
int i=0;
for (Column col : data.getColumns())
{
    %><%=i>0?";":""%><%=col.getIndex()%>:<%=col.getName()%><%
    i++;
}
%>"
}