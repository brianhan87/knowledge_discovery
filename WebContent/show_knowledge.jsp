<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
<link rel="stylesheet" type="text/css" href= "css/cshare_css.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Knowledge meta-search beta version 1.0</title>
</head>
<script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>

<%@ page import ="java.util.*" %>
<%@ page import ="java.sql.*" %>
<%

//String json = "json/"+ request.getParameter("movie_id")+".json";
String json = "knowledge_json/result_link.txt";
%>

<body>
<div class ="search_wrap" >
					
					<form id="mainForm" action="/samsung_demo/process_search" method="get">
						<div class="searchTextBoxArea" >
							<a href="index.jsp"><img src ="images/small_samsung_logo.jpg" height="30px" style="float:left; " border ="0" /></a>
							<input type="text" id="allQuery" name = "allQuery" class= "searchTextBox"  />
							<input type="image" src = "images/search.jpg" class= "mainSearchButton" />		
						</div> <!-- searchTextBoxArea -->
						
					</form>
					
		
				</div> 
				
<!-- 
<div>

 <table class="tstyle_list" >
   
       	<colgroup>
			
				<col width="20%" />
				<col width="10%" />
				<col width="10%" />
				<col width="10%" />
			
		</colgroup>
        <thead>
            <tr>
             
                <th scope="col" >제목</th>
                <th scope="col" >출시연도</th>
                <th scope="col" >출시국가</th>
                <th scope="col" >상영시간</th>
                <th scope="col">시놉시스 (시놉시스를 클릭하면 해당 영화의 IMDB 페이지로 이동합니다)</th>
            </tr>
        </thead>
            <tbody>
            <tr>
                               
                
            </tr>
       </tbody>
</table>


</div>

-->
<div>

<script>
var width = window.screen.width,
height = 800;
	//alert(pid);
	var force = d3.layout.force()
	    .charge(-800)
	    .linkDistance(250)
	    .size([width, height]);
	
	var svg = d3.select("body").append("svg")
    .attr("width", width)
    .attr("height", height);
	//var jsondata; 
	//d3.json("./ksjson.jsp?pid="+pid, function(graph) { alert(); jsondata = graph.nodes; });
	//alert(jsondata);

	d3.json("<%=json%>", function(error, graph) {
		try{
		maxnodesize = graph.nodes[0].size;
		//	maxnodesize = 90;
		
		force.stop();
		var node_drag = d3.behavior.drag()
	    .on("dragstart", dragstart)
	    .on("drag", dragmove)
	    .on("dragend", dragend);
		if (!document.getElementsByClassName) {
		 document.getElementsByClassName = function (cn) {
		    var rx = new RegExp("\\b" + cn + "\\b"), allT = document.getElementsByTagName("*"), allCN = [], i = 0, a;
		        while (a = allT[i++]) {
		          if (a.className && a.className.indexOf(cn) + 1) {
		            if(a.className===cn){ allCN[allCN.length] = a; continue;   }
		            rx.test(a.className) ? (allCN[allCN.length] = a) : 0;
		          }
		        }
		    return allCN;
		      }
		 }
		function dragstart(d, i) {
		    force.stop(); // stops the force auto positioning before you start dragging
		}
	
		function dragmove(d, i) {
		    d.px += d3.event.dx;
		    d.py += d3.event.dy;
		    d.x += d3.event.dx;
		    d.y += d3.event.dy;
		    
		    tick(); // this is the key to make it work together with updating both px,py,x,y on d !
		}
	
		function dragend(d, i) {
		    d.fixed = true; // of course set the node to fixed so the force doesn't include the node in its auto positioning stuff
		    tick();
		    //force.resume();
		}
		
	  force
	      .nodes(graph.nodes)
	      .links(graph.links)
	      .start();
	
	  var link = svg.selectAll(".link_d")
      .data(graph.links)
      .enter().append("path")
      .attr("class", "link_d")
      .attr("fill", "none")
      .style("stroke-width", function(d) { 
					    	  if(d.value<=1)
									return "10";
								else if(d.value<=2)
									return "10";
								else if(d.value<=3)
									return "7";
								else if(d.value<=4)
									return "7";
								else if(d.value<=5)
									return "4";
								else if(d.value<=6)
									return "2";
								else if(d.value<=7)
									return "2";
      						})
      .style("stroke", function(d) { 
    	  					//return "D7E4BD";
    	  					
					    	  if(d.value<=1)
									return "254061";
								else if(d.value<=2)
									return "376092";
								else if(d.value<=3)
									return "4F81BD";
								else if(d.value<=4)
									return "95B3D7";
								else if(d.value<=5)
									return "B9CDE5";
								else if(d.value<=6)
									return "DCE6F2";
								else if(d.value<=7)
									return "D9D9D9";
    	  					
    	  					} );

	  var node = svg.selectAll(".node_d")
	      .data(graph.nodes)
	      .enter().append("circle")
	      .attr("class", "node_d")
	      .attr("alt", function(d) {return d.name})
	      .attr("r", 10 )
	      .style("fill", "D9D9D9")
	      //.call(force.drag)

	      .call(node_drag)
	      .on("mouseover", function(d){
	    	  d3.select(this).style("fill","D54643");
	    	  //alert(link.attr("x2"));
	    	  var elemArray = document.getElementsByClassName("link_d");
	    	  //alert(d.x.value);
	    	  for(var i=0;i<elemArray.length;i++)
	    	  {
	    		  
	    		  var path_d = elemArray[i].getAttribute("d");
	    		  //alert(path_d);
	    		  //var link_x2 = elemArray[i].x2;
	    		  var path_d_arr = path_d.split(",");
	    		  var link_x1 = path_d_arr[0].split("M")[1];
	    		  var link_x2 = path_d_arr[3].split(" ")[1];
	    	  	  if(link_x1 == d.x || link_x2 == d.x)
    	  		  {
	    	  		  elemArray[i].style.stroke = "FFAF91";
    	  		  }

    		  }
	      })
	      .on("mouseout", function(){
	    	  d3.select(this).style("fill","D9D9D9");
	    	  link.style("stroke",function(d) { 
	    		  	//return "D7E4BD";
					
	    		  if(d.value<=1)
						return "254061";
					else if(d.value<=2)
						return "376092";
					else if(d.value<=3)
						return "4F81BD";
					else if(d.value<=4)
						return "95B3D7";
					else if(d.value<=5)
						return "B9CDE5";
					else if(d.value<=6)
						return "DCE6F2";
					else if(d.value<=7)
						return "D9D9D9";
					
  					} );
	      })
	      .on("dblclick",function(d){
	    	  var dbclick_value = "";
	    	  var related_value_name = ""; 
	    	  var related_value_width = "";
	    	  //alert("CLICK: " + d.name);
	    	  dbclick_value += d.name;
	    	  
	    	  var elemArray = document.getElementsByClassName("link_d");
	    	  //alert(d.x.value);
	    	  var linkedNode_Xval_Array = new Array();
	    	  var linkedNode_Widthval_Array = new Array();
	    	  var j=0;
	    	  for(var i=0;i<elemArray.length;i++)
	    	  {
	    		  
	    		  var path_d = elemArray[i].getAttribute("d");
	    		  var path_style = elemArray[i].getAttribute("style");
	    		  //alert(path_d);
	    		  //var link_x2 = elemArray[i].x2;
	    		  var path_d_arr = path_d.split(",");
	    		  var link_x1 = path_d_arr[0].split("M")[1];
	    		  var link_x2 = path_d_arr[3].split(" ")[1];
	    		  
	    		  var path_style_arr = path_style.split(" ");
	    		  var link_width = path_style_arr[1].split(".")[0]; 
	    	  	  if(link_x1 == d.x)
    	  		  {
	    	  		  linkedNode_Xval_Array[j] = link_x2;
	    	  		  linkedNode_Widthval_Array[j] = link_width;
	    	  		  j++;
    	  		  }
	    	  	  if(link_x2 == d.x)
	    	  	  {
	    	  		  linkedNode_Xval_Array[j] = link_x1;
	    	  		  linkedNode_Widthval_Array[j] = link_width;
	    	  		  j++;
	    	  	  }
    		  }
	    	  var nodeArray = document.getElementsByClassName("node_d");
	    	  for(var i in linkedNode_Xval_Array){
	    		  for(var k=0;k<nodeArray.length;k++){
	    			  var node_d = nodeArray[k].getAttribute("transform");
	    			  var node_d_arr = node_d.split(",");
		    		  var node_x1 = node_d_arr[0].split("(")[1];
		    		  var node_x2 = node_d_arr[1].split(")")[0];
		    		  //alert(linkedNode_Xval_Array[i] + " " + node_x1 + " " + node_x2);
		    		  if(linkedNode_Xval_Array[i]==node_x1 || linkedNode_Xval_Array[i]==node_x2){
		    			  //alert("NODE: " + nodeArray[k].getAttribute("alt") + ", Width: " + linkedNode_Widthval_Array[i]);
		    			  related_value_name += nodeArray[k].getAttribute("alt") + "/";
		    			  related_value_width += linkedNode_Widthval_Array[i] + "/";
		    		  }
	    		  }
	    	  }
	    	  $().ready(function(){
	    		  $.ajax({
	    			  type: "POST",
	    			  url: "search_KnowledgeStructure.jsp",
	    			  data: "currentId=" + pid + "&" + "dbclick_value=" + dbclick_value + "&" + "related_value_name=" + related_value_name + "&" + "related_value_width=" + related_value_width,
	    			  success: reciver
	    		  });
	    	  });
	  			function reciver(val){
	  				$("#searchresults").html("");
	  				var arrval = val.split(",");
	  				//$("#searchresults").append(arrval[0]);
	  				for(var i=0; i < arrval.length; i++)
	  				{
	  					var tmpi = arrval[i].split("=")
	  					var tmpkey = tmpi[0];
	  					var tmpvalue = tmpi[1];
	  					if(i==0){
	  						tmpkey = tmpkey.split("{")[1];
	  					}
	  					if(i==(arrval.length-1))
	  						tmpkey = tmpkey.split("}")[1];
	  					//var tmpi = arrval[i].split("=")
	  					$("#searchresults").append("<p>key: " + tmpkey + " <a href='http://jwchul.kaist.ac.kr:8080/cshare_local/detailed.jsp?allQuery=&viewType=list&ranking=0&result=no&pageNum=1&slidePageNum=1&pid=" + tmpkey.trim() + "' target='_blank' >LINK</a>, sim: " + tmpvalue + "</p>");
	  				}
	  				//$("#searchresults").clear();
	  				//$("#searchresults").append("<p>" + Object.prototype.toString.call(val)+ "</p>");
	  				//$("#searchresults").append("<p>" + val.table+ "</p>");
	  			}
	  		
	      });
	
	  //node.append("title") .text(function(d) { return d.name; });
	  
	  
	  var texts = svg.selectAll("text.label")
      .data(graph.nodes)
      .enter().append("text")
      .attr("class", "nodelabel_d")
      .attr("fill", "black")
      .text(function(d) { return d.name;});
	  
	  
	  force.on("tick", tick);
	  
	  function tick() {
		  	link.attr("d", function(d) {
			  var dx = d.target.x - d.source.x,
			      dy = d.target.y - d.source.y,
			      dr = Math.sqrt(dx * dx + dy * dy);
			  return "M" + d.source.x + "," + d.source.y + "A" + dr + "," + dr + " 0 0,1 " + d.target.x + "," + d.target.y;
			  });
		  	
			link.attr("x1", function(d) { return d.source.x; })
		        .attr("y1", function(d) { return d.source.y; })
		        .attr("x2", function(d) { return d.target.x; })
		        .attr("y2", function(d) { return d.target.y; });
		    
		    node.attr("transform", function(d) { 
		    	return "translate(" + d.x + "," + d.y + ")"; 
	    	});
		    
		    texts.attr("transform", function(d) {
		        return "translate(" + d.x + "," + d.y + ")";
		    });
		  }
		}catch(err){alert("This ppt is not available to produce Knowledge Structure");
		window.location.reload();}
	});
	$(document).ready(function(){
		//alert("jo");
		
	});
	</script>


	</div>
	<div class="foot">
			<div style='margin:0 0 0 0; width:100%; height:15px;'>
			</div>
			<div style='width:100%; height:20px;'>
				Copyright © 2013 <a href="http://kse.kaist.ac.kr" target='_blank'>Department of Knowledge Service Engineering</a>, KAIST
			</div>

</div> <!-- foot -->
	
	</body>
	