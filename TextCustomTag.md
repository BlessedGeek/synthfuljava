#JSP Custom Tag for encapsulating continuous blocks of texts

# Download #
The source is at
http://code.google.com/p/synthfuljava/source/browse/#svn/trunk/jsp/org/synthful/jsp/tags/Text.

The TLD is at
http://code.google.com/p/synthfuljava/source/browse/#svn/trunk/jsp/org/resources/WEB-INF.

# Introduction #
How would you like to be able to do this in your JSP?

```
<text id='lineQuery'>
select <%=selection%> from <%=table%>
where <%=mrbean.getGriteria(filterItems)%>
</text>
<%
myJdbcConn.query(lineQuery);
%>
```

# Details #

http://www.nesug.org/proceedings/nesug04/ap/ap02.pdf

is an example of dynamically piping scripts into a non-JEE application server by embedding the scripts within the JSP text custom tag.