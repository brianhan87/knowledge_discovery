<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>


<%@ page import="kr.ac.kirc.ekp.bean.EBook"%>
<%@ page import="kr.ac.kirc.ekp.bean.SearchResult"%>
<%@ page import="kr.ac.kirc.ekp.core.search.Search"%>
<%@ page import="kr.ac.kirc.ekp.core.search.Search_servlet"%>
<%@ page import="kr.ac.kirc.ekp.dao.ExperimentDao"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="org.json.simple.parser.JSONParser"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="org.json.simple.JSONArray"%>
<%
String id = "";
id = (String)session.getAttribute("user");
if(id==null||id.equals("")){
		RequestDispatcher dispatcher = request.getRequestDispatcher("/authentification.jsp");	
		dispatcher.forward(request, response);
	}	


 int pageSize = 20;
 String q = "";
 
 List<SearchResult> passages = null;
 passages = (List<SearchResult>)request.getAttribute("passages");
 
 if(passages ==null)
 {
	 RequestDispatcher dispatcher = request.getRequestDispatcher("/index_sample.jsp");	
	 dispatcher.forward(request, response);
 }
 else
 {
	 
 
 int numDoc = passages.size();
 ExperimentDao dao = new ExperimentDao();
 Connection conn = dao.getEKPConnection();
 
 int query_id = (int)request.getAttribute("query_id");
 String query_text = dao.getQueryText(conn, query_id);
 if (request.getAttribute("query") != null)
 {
	 q = (String)request.getAttribute("query");
	 System.out.println("Q is : " + q);
 }


    if (passages == null)
    {
    	passages = new ArrayList<SearchResult>();
    }

 	String pageNum = request.getParameter("pageNum");
	if(pageNum == null)
	{
		pageNum = "1";
	}
	int currentPage = Integer.parseInt(pageNum);
	
	int startRow = (currentPage - 1)*pageSize ;
	int endRow = currentPage*pageSize;
	int count= passages.size();
	int number=0;
	
	int numOfpage = count/pageSize + (count%pageSize == 0 ? 0:1);
	//count = 15;
	
	if(startRow+20 < passages.size())
	{
		passages = passages.subList(startRow, startRow+20);
		
	}
	else
	{
		passages = passages.subList(startRow, passages.size());
	}
	
 
 
%>

<!DOCTYPE html>
<!--
Template Name: Splash
Author: <a href="http://www.os-templates.com/">OS Templates</a>
Author URI: http://www.os-templates.com/
Licence: Free to use under our free template licence terms
Licence URI: http://www.os-templates.com/template-terms
-->
<html>
<head>
<title>KIRC EKP</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"
	name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="./layout/styles/layout.css" rel="stylesheet" type="text/css"
	media="all">
<style type="text/css">
#layer_fixed {
	height: 80px;
	width: 100%;
	color: #555;
	font-size: 16px;
	position: fixed;
	left: 0px;
	-webkit-box-shadow: 0 1px 2px 0 #777;
	box-shadow: 0 1px 2px 0 #777;
	background-color: #ccc;
	text-align: center;
	padding-top: 10px;
}
</style>
</head>
<body id="top">
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<div class="wrapper row1 bgded"
		style="background-image: url('../images/demo/backgrounds/01.png');">
		<header id="header" class="clear">
			<div id="logo" class="fl_left">
				<!-- ################################################################################################ -->
				<h1>
					<a href="./index_sample.jsp">KIRC EKP</a>
				</h1>
				<!-- ################################################################################################ -->
			</div>
			<nav id="mainav" class="fl_right">
				<ul class="clear">
					<!-- ################################################################################################ -->
					<li class="active"><a href="index_sample.jsp">Home</a></li>
					<li><a href="./gallery.html">About Us</a></li>
					<li><a class="drop" href="#">Navigation</a>
						<ul>
							<li><a href="#">Crawling</a></li>
							<li><a href="#">Annotation</a></li>
							<li><a href="./tta_index.jsp">TTA_TEST</a></li>
							<li><a href="#">Ontology</a></li>
						</ul></li>
					<li><a href="http://kse.kaist.ac.kr">KSE</a></li>
					<li><a href="Logout">Logout</a></li>
					<!-- ################################################################################################ -->
				</ul>
			</nav>
		</header>
	</div>
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<div class="wrapper row2">
		<div id="breadcrumb" class="clear">
			<!-- ################################################################################################ -->
			<ul>
				<li><a href="#">Home</a></li>
				<li><a href="#">Navigation</a></li>
				<li><a href="#">Annotation</a></li>
			</ul>
			<!-- ################################################################################################ -->
		</div>
		<div id="layer_fixed">
			Query: <b><%=query_text %></b> <br /> The number of passages
			completed:<b><%=dao.getNumberofCompletionforQuery(conn, query_id, id) %></b>
			out of
			<%=numDoc %>
		</div>
	</div>
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<div class="wrapper row3">
		<main class="container clear"> <!-- main body --> <!-- ################################################################################################ -->
		<div class="content">
			<!-- ################################################################################################ -->
			<!--
      <h1>&lt;h1&gt; to &lt;h6&gt; - Headline Colour and Size Are All The Same</h1>
      <img class="imgr borderedbox inspace-5" src="../images/demo/imgr.gif" alt="">
      <p>Aliquatjusto quisque nam consequat doloreet vest orna partur scetur portortis nam. Metadipiscing eget facilis elit sagittis felisi eger id justo maurisus convallicitur.</p>
      <p>Dapiensociis <a href="#">temper donec auctortortis cumsan</a> et curabitur condis lorem loborttis leo. Ipsumcommodo libero nunc at in velis tincidunt pellentum tincidunt vel lorem.</p>
      <img class="imgl borderedbox inspace-5" src="../images/demo/imgl.gif" alt="">
      <p>This is a W3C compliant free website template from <a href="http://www.os-templates.com/" title="Free Website Templates">OS Templates</a>. For full terms of use of this template please read our <a href="http://www.os-templates.com/template-terms">website template licence</a>.</p>
      <p>You can use and modify the template for both personal and commercial use. You must keep all copyright information and credit links in the template and associated files. For more website templates visit our <a href="http://www.os-templates.com/">free website templates</a> section.</p>
      <p>Portortornec condimenterdum eget consectetuer condis consequam pretium pellus sed mauris enim. Puruselit mauris nulla hendimentesque elit semper nam a sapien urna sempus.</p>
      <h1>Table(s)</h1>
      <div class="scrollable">
        <table>
          <thead>
            <tr>
              <th>Header 1</th>
              <th>Header 2</th>
              <th>Header 3</th>
              <th>Header 4</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td><a href="#">Value 1</a></td>
              <td>Value 2</td>
              <td>Value 3</td>
              <td>Value 4</td>
            </tr>
            <tr>
              <td>Value 5</td>
              <td>Value 6</td>
              <td>Value 7</td>
              <td><a href="#">Value 8</a></td>
            </tr>
            <tr>
              <td>Value 9</td>
              <td>Value 10</td>
              <td>Value 11</td>
              <td>Value 12</td>
            </tr>
            <tr>
              <td>Value 13</td>
              <td><a href="#">Value 14</a></td>
              <td>Value 15</td>
              <td>Value 16</td>
            </tr>
          </tbody>
        </table>
      </div>
      -->

			<div id="comments">
				<%
        	if ( passages !=null ) 
        	{
        %>




				<ul>
					<%
        		
        		if (passages.size() <20 )
        		{
        			pageSize = passages.size();
        		}
        		for(int i=0;i<pageSize;i++)
        		{  
        			
        		String isbn = passages.get(i).getIsbn();
        		String rank = String.valueOf(passages.get(i).getRank());
        		String contents = passages.get(i).getContents();
        		String score = String.valueOf(passages.get(i).getScore());
        		String method_num = String.valueOf(passages.get(i).getMethod_num());
        		int passage_id = passages.get(i).getNumber();
        %>
					<li>
						<article>
							<header>
								<address>
									Passage id:
									<%=passage_id %>
									From <a href="#"> <%=method_num %>
									</a>
								</address>
								<time datetime="">
									ISBN of the passage:
									<%=isbn %>
								</time>
							</header>
							<div class="comcont">
								<%=contents %>
							</div>
							<nav id="mainav" class="fl_right">
								<ul class="clear">
									<!-- ################################################################################################ -->
									<!-- <li class="active"><a href="index_sample.jsp">Home</a></li>-->
									<!-- <li><a href="./gallery.html">About Us</a></li>-->
									<li><a
										href="http://ekp.kaist.ac.kr:8080/kirc_ekp/project/single_book_detail.html?=<%=passage_id%>=<%=isbn%>="target="_sub">More...</a></li>

									<%
				if (!dao.isJudged(conn, query_id, passage_id, isbn, id).equals("Not Done")){
			%>
									<li><a class="drop" href="#"><%=dao.isJudged(conn, query_id, passage_id, isbn, id)%></a>
										<%
				}
				else
				{
			%>
									<li><a class="drop" href="#">Not Done</a> <%} %>
										<ul>
											<li><a
												href="DeterminingRelevance?pageNum=<%=pageNum%>&query_id=<%=query_id%>&isbn=<%=isbn%>&passage_id=<%=passage_id%>&relevance=3">Definitely	Relevant</a></li>
											<li><a
												href="DeterminingRelevance?pageNum=<%=pageNum%>&query_id=<%=query_id%>&isbn=<%=isbn%>&passage_id=<%=passage_id%>&relevance=2">Possibly Relevant</a></li>
											<li><a
												href="DeterminingRelevance?pageNum=<%=pageNum%>&query_id=<%=query_id%>&isbn=<%=isbn%>&passage_id=<%=passage_id%>&relevance=1">Not Relevant</a></li>
										</ul></li>
									<!-- ################################################################################################ -->
								</ul>
							</nav>
						</article>
					</li>
					<br />
					<br />

					<%
        		}
        	
         
         %>
				</ul>
				<%
        	}
        %>
				<%
	if(count >0){
		int pageCount = count/pageSize + (count%pageSize == 0 ? 0:1);
		int startPage = currentPage / pageSize + 1;
		int endPage = startPage + 10;
		
		if(endPage > pageCount)
		{
			endPage = pageCount;
		}
		
		if(currentPage > 1) {
		
		
%>

				<a href="searcher?pageNum=<%=currentPage-1 %>&query_id=<%=request.getParameter("query_id")%>"><img	src="images/icon/icon_prev.gif" alt="이전 페이지로" /></a>


				<%
		}
		for(int i=startPage ; i<=endPage;i++)
		{
			if(i == currentPage)
			{
%>
				<a href="searcher?pageNum=<%= i %>&query_id=<%=request.getParameter("query_id")%>"><b>[<%= i %>]</b></a>
				<%				
			}else{
			
%>
				<a href="searcher?pageNum=<%= i %>&query_id=<%=request.getParameter("query_id")%>">[<%= i %>]</a>
				<%
		
			}
		}
		if(currentPage < pageCount) { 
			
 %>




				<a href="searcher?pageNum=<%=currentPage+1 %>&query_id=<%=request.getParameter("query_id")%>"><img src="images/icon/icon_next.gif" alt="다음 페이지로" /></a>

				<%
		}
	}
        
    dao.closeConnection(conn);

%>




			</div>
			<!-- ################################################################################################ -->
		</div>
		<!-- ################################################################################################ -->
		<!-- / main body -->
		<div class="clear"></div>
		</main>
	</div>
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<div class="wrapper row4">
		<footer id="footer" class="clear">
			<!-- ################################################################################################ -->
			<div class="one_quarter first">
				<h6 class="title">KAIST KIRC</h6>
				<address class="btmspace-15">
					Room 1201, <br> Bldg. E2-1, KAIST 291<br> Daehak-ro,
					Yuseong-gu<br> Daejeon, South Korea
				</address>
				<ul class="nospace">
					<li class="btmspace-10"><span class="fa fa-phone"></span> +82
						(42) 350 1602</li>
					<li><span class="fa fa-envelope-o"></span> munyi@kaist.ac.kr</li>
				</ul>
			</div>
			<div class="one_quarter">
				<h6 class="title">Consortium</h6>
				<ul class="nospace linklist">
					<li><a href="#">KAIST</a></li>
					<li><a href="#">Tasmania University</a></li>
					<li><a href="#">Soongsil University</a></li>
					<li><a href="#">Seegene Medical Foundation</a></li>
					<li><a href="#">SG Medical</a></li>
					<li><a href="#">Bit Computer</a></li>
				</ul>
			</div>
			<div class="one_quarter">
				<h6 class="title">Acknowledgement</h6>
				<article>
					<p>This work was supported by the Technology Innovation
						Program, 10052955, funded by the Ministry of Trade, industry &
						Energy.</p>
				</article>
			</div>
			<div class="one_quarter">
				<h6 class="title">Grab Our Newsletter</h6>
				<form method="post" action="#">
					<fieldset>
						<legend>Newsletter:</legend>
						<input class="btmspace-15" type="text" value="" placeholder="Name">
						<input class="btmspace-15" type="text" value=""
							placeholder="Email">
						<button type="submit" value="submit">Submit</button>
					</fieldset>
				</form>
			</div>
			<!-- ################################################################################################ -->
		</footer>
	</div>
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<div class="wrapper row5">
		<div id="copyright" class="clear">
			<!-- ################################################################################################ -->
			<p class="fl_left">
				Copyright &copy; 2015 - All Rights Reserved - <a
					href="http://kirc.kaist.ac.kr">KAIST KIRC</a>
			</p>
			<p class="fl_right">
				Developed by <a target="_blank" href="http://www.os-templates.com/"
					title="Free Website Templates">Keejun Han</a>
			</p>
			<!-- ################################################################################################ -->
		</div>
	</div>
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<a id="backtotop" href="#top"><i class="fa fa-chevron-up"></i></a>
	<!-- JAVASCRIPTS -->
	<script src="../layout/scripts/jquery.min.js"></script>
	<script src="../layout/scripts/jquery.backtotop.js"></script>
	<script src="../layout/scripts/jquery.mobilemenu.js"></script>
	<!-- IE9 Placeholder Support -->
	<script src="../layout/scripts/jquery.placeholder.min.js"></script>
	<!-- / IE9 Placeholder Support -->
</body>
</html>
<%

 }
%>