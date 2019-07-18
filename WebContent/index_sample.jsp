<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

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
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<link href="./layout/styles/layout.css" rel="stylesheet" type="text/css" media="all">
</head>
<body id="top" class="homepage"><!-- Homepage class is only used on the homepage in conjunction with the slider -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<div class="wrapper row1">
  <header id="header" class="clear">
    <div id="logo" class="fl_left"> 
      <!-- ################################################################################################ -->
      <h1><a href="./index_sample.html">KIRC EKP</a></h1>
      <!-- ################################################################################################ -->
    </div>
    <nav id="mainav" class="fl_right">
      <ul class="clear">
        <!-- ################################################################################################ -->
        <li class="active"><a href="index_sample.html">Home</a></li>
        <li><a href="./gallery.html">About Us</a></li>
        <li><a class="drop" href="#">Navigation</a>
          <ul>
            <li><a href="#">Crawling</a></li>
            <li><a href="#">Annotation</a></li>
            <li><a href="./tta_index.jsp">TTA_TEST</a></li>
            <li><a href="#">Ontology</a></li>
          </ul>
        </li>
        <li><a href="http://kse.kaist.ac.kr">KSE</a></li>
     		<%
     			String id = "";

     			id = (String)session.getAttribute("user");
     			if(id==null||id.equals("")){
     		%>
     		        <li><a href="./authentification.jsp">Login</a></li>
     		<%
     			}
     			else
     			{
     		%>
     			<li><a href="Logout">Logout</a></li>
     		<%
     			}
     		%>

        <!-- ################################################################################################ -->
      </ul>
    </nav>
  </header>
</div>
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<div id="slider">
  <div class="flexslider basicslider">
    <ul class="slides">
      <!-- ################################################################################################ -->
      <li class="bgded" style="background-image:url('images/demo/slider/01.png')">
        <article class="flex-content">
          <h2 class="heading underlined">Experiential Knowledge Platform</h2>
          <p>Based upon your valued experience, we help your decision making. In this early version, we automatically search supporting statements for your decision making by using a pre-built ontology and novel query expansion method</p>
          <p><a class="btn" href="#">Show System Demo</a></p>
        </article>
      </li>
      <li class="bgded" style="background-image:url('images/demo/slider/02.png')">
        <article class="flex-content">
          <h2 class="heading underlined">Ontology</h2>
          <p>We have created our own medical ontology to be used for automatic search for supporting statements. Other than that, it defines a standard relations between medical terms to remove conflict for developing EKP prototype.</p>
          <p><a class="btn" href="#">Show ontology Demo</a></p>
        </article>
      </li>
      <li class="bgded" style="background-image:url('images/demo/slider/03.png')">
        <article class="flex-content">
          <h2 class="heading underlined">Document Processing</h2>
          <p>We collected a set of medical journals as well as medical textbooks and then used them as a corpus for document search. It ensures the high precision of detecting the supporting statements for your decision making.</p>
          <p><a class="btn" href="./query_expansion.jsp">Show search demo</a></p>
        </article>
      </li>
      <!-- ################################################################################################ -->
    </ul>
  </div>
</div>
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<div class="wrapper row3">
  <main class="container clear"> 
    <!-- main body -->
    <!-- ################################################################################################ -->
    <div class="center clear">
      <div class="btmspace-80">
        <h2 class="font-x2 bold underlined">CORE MODULES</h2>
        <p>In EKP project, we take the following modules: (1) Crawling medical papers from the Web (2) Search with Query Expansion by using the ontology and Defacto (3) Document Storage (4) Ontology creation and validation</p>
      </div>
      <div class="group">
        <article class="one_quarter first"><a href="#"><i class="btmspace-15 fa fa-3x fa-qrcode"></i></a>
          <h4 class="font-x1 underlined">Manage Crawling</h4>
          <p>You can manage the status of our crawling module.</p>
        </article>
        <article class="one_quarter"><a href="./query_expansion.jsp"><i class="btmspace-15 fa fa-3x fa-cogs"></i></a>
          <h4 class="font-x1 underlined">Search with Expansion </h4>
          <p>You can experience our search module where the performance is elevated by our novel query expansion method.</p>
        </article>
        <article class="one_quarter"><a href="#"><i class="btmspace-15 fa fa-3x fa-university"></i></a>
          <h4 class="font-x1 underlined">Manage Documents </h4>
          <p>You can take a look at the collected documents for our prototype. It also provides some statistics about the collection.</p>
        </article>
        <article class="one_quarter"><a href="./ontology_index.jsp"><i class="btmspace-15 fa fa-3x fa-globe"></i></a>
          <h4 class="font-x1 underlined">Build Ontology</h4>
          <p>You can easily add, modify, delete attributes in the ontology by using this module.</p>
        </article>
      </div>
    </div>
    <!-- ################################################################################################ -->
    <!-- / main body -->
    <div class="clear"></div>
  </main>
</div>
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<div class="wrapper row1 bgded" style="background-image:url('images/demo/backgrounds/01.png');">
  <div class="overlay">
    <div class="container clear"> 
      <!-- ################################################################################################ -->
      <div class="btmspace-80 center">
        <h2 class="font-x2 bold underlined">Show our progess</h2>
        <p>We constantly check our progress as time goes by to meet the expected schedule of EKP project. Note that the progress are updated fortnightly. We will keep up until it truly brings benefit for field experts!</p>
      </div>
      <ul class="pr-charts nospace group center">
        <li class="pr-chart-ctrl" data-animate="false">
          <div class="pr-chart" data-percent="75"><i></i></div>
          <p>Crawling</p>
        </li>
        <li class="pr-chart-ctrl" data-animate="false">
          <div class="pr-chart" data-percent="50"><i></i></div>
          <p>Query Expansion</p>
        </li>
        <li class="pr-chart-ctrl" data-animate="false">
          <div class="pr-chart" data-percent="50"><i></i></div>
          <p>Document Processing</p>
        </li>
        <li class="pr-chart-ctrl" data-animate="false">
          <div class="pr-chart" data-percent="25"><i></i></div>
          <p>Ontology</p>
        </li>
      </ul>
      <!-- ################################################################################################ -->
    </div>
  </div>
</div>
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!-- ################################################################################################ -->
<!--
<div class="wrapper row3">
  <section class="container clear"> 
    
    <div class="btmspace-80 center">
      <h2 class="font-x2 bold underlined">Morbi ac augue rutrum</h2>
      <p>Pharetra ipsum scelerisque luctus turpis phasellus elementum nibh ut lorem dapibus facilisis fusce pharetra ipsum vitae convallis dignissim. Sapien magna pellentesque massa in dictum risus felis eu diam cras vel erat ut justo fringilla.</p>
    </div>
    <div class="group latest">
      <article class="one_quarter first">
        <figure><img src="images/demo/500x400.png" alt="">
          <figcaption><strong>01</strong> Jan</figcaption>
        </figure>
        <h3 class="font-x1 heading">Vitae metus males</h3>
        <p>In vel ligula sodales aliquet diam sed rhoncus dui suspen disse eleifend erat&hellip;</p>
        <p><a href="#">Read More &raquo;</a></p>
      </article>
      <article class="one_quarter">
        <figure><img src="images/demo/500x400.png" alt="">
          <figcaption><strong>02</strong> Jan</figcaption>
        </figure>
        <h3 class="font-x1 heading">Molestie laoreet</h3>
        <p>Consec tetur sed a dolor effic itur ultricies tortor in commodo erat phasellus suscipit&hellip;</p>
        <p><a href="#">Read More &raquo;</a></p>
      </article>
      <article class="one_quarter">
        <figure><img src="images/demo/500x400.png" alt="">
          <figcaption><strong>03</strong> Jan</figcaption>
        </figure>
        <h3 class="font-x1 heading">Eleifend sit amet</h3>
        <p>Fusce ullamcorper felis et dui tempor dignissim mauris eu feugiat et&hellip;</p>
        <p><a href="#">Read More &raquo;</a></p>
      </article>
      <article class="one_quarter">
        <figure><img src="images/demo/500x400.png" alt="">
          <figcaption><strong>04</strong> Jan</figcaption>
        </figure>
        <h3 class="font-x1 heading">Fermentum quis</h3>
        <p>Pellentesque faucibus mauris sem a fringilla libero commodo sed duis erat&hellip;</p>
        <p><a href="#">Read More &raquo;</a></p>
      </article>
    </div>
     
    <div class="clear"></div>
  </section>
</div>
-->
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
<script src="layout/scripts/jquery.min.js"></script>
<script src="layout/scripts/jquery.backtotop.js"></script>
<script src="layout/scripts/jquery.mobilemenu.js"></script>
<!-- IE9 Placeholder Support -->
<script src="layout/scripts/jquery.placeholder.min.js"></script>
<!-- / IE9 Placeholder Support -->
<!-- Homepage specific -->
<script src="layout/scripts/jquery.flexslider-min.js"></script>
<script src="layout/scripts/jquery.easypiechart.min.js"></script>
<div id="preloader"><div></div></div><!-- Basic page preloader -->
<script>$(window).load(function(){$("#preloader div").delay(500).fadeOut();$("#preloader").delay(800).fadeOut("slow");});</script>
<!-- / Homepage specific -->
</body>
</html>