<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="kr.ac.kirc.ekp.core.ontology.Ontology"%>
<%@ page import="kr.ac.kirc.ekp.core.ontology.Ontology_servlet"%>
<%@ page import="kr.ac.kirc.ekp.dao.ExperimentDao"%>
<%@ page import="kr.ac.kirc.ekp.bean.Query"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%

String id = "";

id = (String)session.getAttribute("user");
if(id==null||id.equals("")){
	RequestDispatcher dispatcher = request.getRequestDispatcher("/authentification.jsp");	
	dispatcher.forward(request, response);
}
else
{
//System.out.println("executed on this line:" + task_id);
String task_id = (String)session.getAttribute("task_ids");
String[] tasks = task_id.split("&");
ExperimentDao dao = new ExperimentDao();
Connection conn = dao.getEKPConnection();

ArrayList<Query> queries = new ArrayList<Query>();
//queries = dao.getQueryList(conn);
for(int i=0;i<tasks.length;i++)
{
	Query q = dao.getSpecificQueryList(conn, Integer.parseInt(tasks[i]));
	queries.add(q);
}
//System.out.println("executed on this line:");
String q = "";
ArrayList<ArrayList<String>> sections = new ArrayList<ArrayList<String>>();
String message = "";
String tableName = "";
ArrayList<String> fieldNames = new ArrayList<String>();
int nofFields=0;
int nofDocs=0;
String stat="";
int errcode=0;

if (request.getAttribute("errcode") != null)
{
	 q = (String) request.getAttribute("queries");
	 if(q==null) q="";
	 sections = (ArrayList<ArrayList<String>>)request.getAttribute("sections");
	 message = (String)request.getAttribute("message");
	 tableName = (String)request.getAttribute("tableName");
	 fieldNames = (ArrayList<String>)request.getAttribute("fieldNames");
	 if(fieldNames!=null){nofFields=fieldNames.size();}
	 if((Integer) request.getAttribute("numDoc")!=null) nofDocs=(Integer) request.getAttribute("numDoc");	 
	 stat = (String)request.getAttribute("stat");
	 errcode=(Integer) request.getAttribute("errcode");
} 
%>


<!DOCTYPE html>
<html>
<head>
<title>KIRC EKP</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"
	name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="./layout/styles/layout.css" rel="stylesheet" type="text/css"
	media="all">

</head>




<body id="top">
	<div class="wrapper row1 bgded"
		style="background-image: url('../images/demo/backgrounds/01.png');">
		<header id="header" class="clear">
			<div id="logo" class="fl_left">
				<h1>
					<a href="./index_sample.jsp">KIRC EKP</a>
				</h1>
			</div>
			<nav id="mainav" class="fl_right">
				<ul class="clear">
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
				<li><a href="#">TTA_Test</a></li>
			</ul>
			<!-- ################################################################################################ -->
		</div>
	</div>
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->
	<!-- ################################################################################################ -->

























	<div class="wrapper row3">
		<main class="container clear"> <!-- main body --> <!-- ################################################################################################ -->
		<div class="content">
			<!-- ################################################################################################ -->
			<div id="comments">



				<h2>
					본인에게 할당된 쿼리를 클릭하여 시작하세요. <br /> (Please clicking queries below
					that you are informed to make a decision for.)
				</h2>
				<div>
					<ul class="databasemenu">
						<%
      			for (int i=0;i<queries.size();i++)
      			{
      		
      		%>
						<li class="one_ten database-label"><%=queries.get(i).getQuery_id() %></li>
						<li class="database-command"><a href=tta_searcher?query_id=<%=queries.get(i).getQuery_id()%>><%=queries.get(i).getQuery_original() %>(<%=queries.get(i).getQuery() %>)</a></li>
						<%
    			}	
    		%>
					</ul>
				</div>

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

	dao.closeConnection(conn);
}


%>