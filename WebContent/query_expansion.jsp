<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>


<%@ page import ="kr.ac.kirc.ekp.bean.EBook" %>
<%@ page import ="kr.ac.kirc.ekp.core.search.Search" %>
<%@ page import ="kr.ac.kirc.ekp.core.search.Search_servlet" %>
<%@ page import ="java.util.*" %>
<%@ page import ="java.sql.*" %>

<%

 String q = "";
 if (request.getAttribute("queries") != null)
 {
	 q = (String) request.getAttribute("queries");
 }

 
 ArrayList<EBook> sections = new ArrayList<EBook>();
 
 sections = (ArrayList<EBook>)request.getAttribute("sections");

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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="./layout/styles/layout.css" rel="stylesheet" type="text/css" media="all">
</head>
<body id="top">
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<div class="wrapper row1 bgded" style="background-image:url('../images/demo/backgrounds/01.png');">
  <header id="header" class="clear">
    <div id="logo" class="fl_left"> 
      <!-- ################################################################################################ -->
      <h1><a href="./index_sample.jsp">KIRC EKP</a></h1>
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
            <li><a href="#">Query Expansion</a></li>
            <li><a href="./tta_index.jsp">TTA_TEST</a></li>
            <li><a href="#">Ontology</a></li>
          </ul>
        </li>
        <li><a href="http://kse.kaist.ac.kr">KSE</a></li>
        <li><a href="http://www.kaist.ac.kr">KAIST</a></li>
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
      <li><a href="#">Query Expansion</a></li>
    </ul>
    <!-- ################################################################################################ -->
  </div>
</div>
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<div class="wrapper row3">
  <main class="container clear"> 
    <!-- main body -->
    <!-- ################################################################################################ -->
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
       <h2>SEARCH FOR A GIVEN TEXT</h2>
        <form action="searcher" method="get">
          <div class="one_half first">
            <input type="text" name="allQuery" id="allQuery" value="<%=q %>" size="22">
          </div>
          <div class="one_half">
           	<input name="submit" type="submit" value="Search">
           	&nbsp;
            <input name="extension" type="submit" value="Extension">
          </div>
          <div class="block clear">
          </div>
          <div>
         
          </div>
       </form> 
      
      
        <%
        	if ( sections !=null ) 
        	{
        %>
      
        <h2>Results <%=(Integer) request.getAttribute("numDoc") %></h2>
       
        <ul>
        <%
        		int k = 10; 
        		if (sections.size() <10 )
        		{
        			k = sections.size();
        		}
        		for(int i=0;i<k;i++)
        		{        
        %>
          <li>
            <article>
              <header>
                <figure class="avatar"><img src="<%=sections.get(i).getImage_url() %>" width="100" height="80" alt="" /></figure>
                <address>
                From <a href="#"> <%=sections.get(i).getTitle() %> </a>
                </address>
                <time datetime=""><%=sections.get(i).getNumber() %> , <%=sections.get(i).getChap_num() %>, <%=sections.get(i).getScore() %> </time>
              </header>
              <div class="comcont">
                <p><%=sections.get(i).getContents() %></p>
                <p><%=sections.get(i).getHighlight_content() %></p>
              </div>
            </article>
          </li>
          
         <%
        		}
        	
         
         %>
        </ul>
        <%
        	}
        %>
        <!--   
          <li>
            <article>
              <header>
                <figure class="avatar"><img src="../images/demo/avatar.png" alt=""></figure>
                <address>
                By <a href="#">A Name</a>
                </address>
                <time datetime="2045-04-06T08:15+00:00">Friday, 6<sup>th</sup> April 2045 @08:15:00</time>
              </header>
              <div class="comcont">
                <p>This is an example of a comment made on a post. You can either edit the comment, delete the comment or reply to the comment. Use this as a place to respond to the post or to share what you are thinking.</p>
              </div>
            </article>
          </li>
          <li>
            <article>
              <header>
                <figure class="avatar"><img src="../images/demo/avatar.png" alt=""></figure>
                <address>
                By <a href="#">A Name</a>
                </address>
                <time datetime="2045-04-06T08:15+00:00">Friday, 6<sup>th</sup> April 2045 @08:15:00</time>
              </header>
              <div class="comcont">
                <p>This is an example of a comment made on a post. You can either edit the comment, delete the comment or reply to the comment. Use this as a place to respond to the post or to share what you are thinking.</p>
              </div>
            </article>
          </li>
        -->
        <!-- 
        <h2>Write A Comment</h2>
        <form action="#" method="post">
          <div class="one_third first">
            <label for="name">Name <span>*</span></label>
            <input type="text" name="name" id="name" value="" size="22">
          </div>
          <div class="one_third">
            <label for="email">Mail <span>*</span></label>
            <input type="text" name="email" id="email" value="" size="22">
          </div>
          <div class="one_third">
            <label for="url">Website</label>
            <input type="text" name="url" id="url" value="" size="22">
          </div>
          <div class="block clear">
            <label for="comment">Your Comment</label>
            <textarea name="comment" id="comment" cols="25" rows="10"></textarea>
          </div>
          <div>
            <input name="submit" type="submit" value="Submit Form">
            &nbsp;
            <input name="reset" type="reset" value="Reset Form">
          </div>
        </form>
        -->
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
      Room 1201, <br>
      Bldg. E2-1, KAIST 291<br>
      Daehak-ro, Yuseong-gu<br>
      Daejeon, South Korea
      </address>
      <ul class="nospace">
        <li class="btmspace-10"><span class="fa fa-phone"></span> +82 (42) 350 1602</li>
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
        <p>This work was supported by the Technology Innovation Program, 10052955, funded by the Ministry of Trade, industry & Energy.</p>
      </article>
    </div>
    <div class="one_quarter">
      <h6 class="title">Grab Our Newsletter</h6>
      <form method="post" action="#">
        <fieldset>
          <legend>Newsletter:</legend>
          <input class="btmspace-15" type="text" value="" placeholder="Name">
          <input class="btmspace-15" type="text" value="" placeholder="Email">
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
    <p class="fl_left">Copyright &copy; 2015 - All Rights Reserved - <a href="http://kirc.kaist.ac.kr">KAIST KIRC</a></p>
    <p class="fl_right">Developed by <a target="_blank" href="http://www.os-templates.com/" title="Free Website Templates">Keejun Han</a></p>
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