


window.GAST="data_1.txt";


var bookItem = $(".book-item").detach();
var paperItem = $(".paper-item").detach();
var topicItem=$(".topic-item").detach();
var topicItem_sub=$(".topic-item-sub").detach();
var display_type=""; //book or paprer
//VARIABLE

var book_isbn_array=new Array(5);
var book_id_array=new Array(5);

 var diagnosis="";
 var align_category="";
 var startpage="";
 var cur_page=0;
//EVENT

String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
}







$(document).ajaxStart(function(){ 
      showLoadingImg();
      console.log("ajaxStart");
});

$(document).ajaxStop(function(){ 
      hideLoadingImg();
      console.log("ajaxStop");
});

$(".hidden-keywords-btn").click(function(){	
	console.log("check! btn check");

	if($(".hidden-keywords-btn b u").text()=="More Keywords...")
	{	
		$(".hidden-keywords-btn b u").text("Hide keywords");
		$(".topic-additional").removeClass("hidden-item");
	}
	else if($(".hidden-keywords-btn b u").text()=="Hide keywords")
	{
		$(".hidden-keywords-btn b u").text("More Keywords...");
		$(".topic-additional").addClass("hidden-item");
	}

});



$(".add-keyword-btn").click(function(){

	if($("input.form-control").val()=="")
	{
		alert("Input the keyword.");

	}
	else{

		topicItem.clone().find(".keyword").text($("input.form-control").val()).end().appendTo(".topic");
		var added_keyword=$("input.form-control").val();
		
		diagnosis=diagnosis+"%20"+$("input.form-control").val("");
	
		if(display_type=="book"){
		bringBookResult(diagnosis,"");
	

		}
		else if(display_type=="paper")
		{
		bringPaperResult(diagnosis,"");

		}

	}

 });


$(".book-head-btn").click(function(){

	$(".book-head-btn").addClass('active');
	$(".book-panel").addClass('active');

	$(".paper-head-btn").removeClass('active');
	$(".paper-panel").removeClass('active');
	console.log("test!!!!");
	bringBookResult(diagnosis,"");
	display_type="book";
	$("#sbox").removeClass("inactive-item");
 });

$(".paper-head-btn").click(function(){
	console.log("test!!!!paper");

	if(align_category=="default")
	{
		$(".book-head-btn").removeClass('active');
		$(".book-panel").removeClass('active');

		$(".paper-head-btn").addClass('active');
		$(".paper-panel").addClass('active');
		display_type="paper";
	}
	else{
		alert("default로 분류를 선택해주세요.");
	}
	bringPaperResult(diagnosis,"");
	$("#sbox").addClass("inactive-item");
 });


$(".page-num-1").click(function(){
	 location.replace("DDK_UI.html?index="+diagnosis+"=startpage="+startpage+"=align="+align_category+"=cur_page="+$(this).text()+"=mode="+display_type); 
 });
$(".page-num-2").click(function(){
	 location.replace("DDK_UI.html?index="+diagnosis+"=startpage="+startpage+"=align="+align_category+"=cur_page="+$(this).text()+"=mode="+display_type); 
 });

$(".page-num-3").click(function(){
	 location.replace("DDK_UI.html?index="+diagnosis+"=startpage="+startpage+"=align="+align_category+"=cur_page="+$(this).text()+"=mode="+display_type); 
 });
$(".page-num-4").click(function(){
	 location.replace("DDK_UI.html?index="+diagnosis+"=startpage="+startpage+"=align="+align_category+"=cur_page="+$(this).text()+"=mode="+display_type); 
 });

$(".page-num-5").click(function(){
	 location.replace("DDK_UI.html?index="+diagnosis+"=startpage="+startpage+"=align="+align_category+"=cur_page="+$(this).text()+"=mode="+display_type); 
 });
$(".page-num-6").click(function(){
	 location.replace("DDK_UI.html?index="+diagnosis+"=startpage="+startpage+"=align="+align_category+"=cur_page="+$(this).text()+"=mode="+display_type); 
 });

$(".page-num-7").click(function(){
	 location.replace("DDK_UI.html?index="+diagnosis+"=startpage="+startpage+"=align="+align_category+"=cur_page="+$(this).text()+"=mode="+display_type); 
 });
$(".page-num-8").click(function(){
	 location.replace("DDK_UI.html?index="+diagnosis+"=startpage="+startpage+"=align="+align_category+"=cur_page="+$(this).text()+"=mode="+display_type); 
 });

$(".page-num-9").click(function(){
	 location.replace("DDK_UI.html?index="+diagnosis+"=startpage="+startpage+"=align="+align_category+"=cur_page="+$(this).text()+"=mode="+display_type); 
 });
$(".page-num-10").click(function(){
	 location.replace("DDK_UI.html?index="+diagnosis+"=startpage="+startpage+"=align="+align_category+"=cur_page="+$(this).text()+"=mode="+display_type); 
 });
$(".next-button").click(function(){
	 location.replace("DDK_UI.html?index="+diagnosis+"=startpage="+(Number(startpage)+1)+"=align="+align_category+"=cur_page="+(Number(startpage)*10+1)+"=mode="+display_type); 
 });
$(".before-button").click(function(){
	if(startpage!=1)
	{ location.replace("DDK_UI.html?index="+diagnosis+"=startpage="+(Number(startpage)-1)+"=align="+align_category+"=cur_page="+((Number(startpage)-2)*10+1)+"=mode="+display_type);
	} 
 });

chageCategory=function(){

	 location.replace("DDK_UI.html?index="+diagnosis+"=startpage="+1+"=align="+align_category+"=cur_page="+1+"=mode="+display_type); 
}

//function
loadInitialStat=function(){
		
	

	$(document).ready(function () {
	 
	    var fullUrl=window.location.href;
	    var parameter=fullUrl.split("=");
	    
	    diagnosis=parameter[1];
	    diagnosis=diagnosis.replaceAll("_"," ");
	    startpage=parameter[3];
	    align_category=parameter[5];
	    cur_page=parameter[7];
	    display_type=parameter[9];
	});
	
	settingPageItem(startpage);
	settingCurrentPage(cur_page);
	settingAlignCategory(align_category);
	if(display_type=="book"){
		bringBookResult(diagnosis,"initial");
		$(".book-head-btn").addClass('active');
		$(".book-panel").addClass('active');

		$(".paper-head-btn").removeClass('active');
		$(".paper-panel").removeClass('active');
		$("#sbox").removeClass("inactive-item");
	}
	else if(display_type=="paper")
	{
		bringPaperResult(diagnosis,"initial");

		$(".book-head-btn").removeClass('active');
		$(".book-panel").removeClass('active');

		$(".paper-head-btn").addClass('active');
		$(".paper-panel").addClass('active');
		$("#sbox").addClass("inactive-item");
	}
	
	

	
}

function settingAlignCategory(category)
{	

	var encodeWord =  decodeURIComponent(category);
	var test=decodeURIComponent('\uae4c\ud0c8\ub808\ub098');
	console.log(test);
	$("option").each(function(idx)
	{	
		if($(this).text()==encodeWord)
		{	
			
			$(this).attr( 'selected', 'selected');
		}
	});	
}
function settingPageItem(startpage)
{
	$("a.item-pagenum").each(function(idx)
	{		
	 	$(this).text((idx+1)+(startpage-1)*10);
	});
}
function settingCurrentPage(currentPage)
{		
	$("a.item-pagenum").each(function(idx)
	{	console.log($(this).text());
		console.log(currentPage);
	 	if($(this).text()==currentPage)
	 	{	
	 		console.log("what");
	 		$(this).addClass('active');
	 	}
	});
}
function bringBookResult(query,mode)
{	
	
	query_want="http://143.248.92.77:8080/kirc_ekp/apis/getBooks?q="+query+"&prev="+((Number(cur_page)-1)*5+1)+"&next="+((Number(cur_page)-1)*5+5)+"&rel="+align_category;
	console.log(query_want);
	client.sendAPI("GET", query_want, null, function(dat) {
	var data = dat;
	var contentsList = data.books;
	$(".book-item-container").empty();
	
	//keyword
	if(mode=="initial")
	{
		var keywordList=dat.main_topics;
		console.log(keywordList);
		
		
		$.each(keywordList, function(idx, item) {
		topicItem.clone().find(".keyword").text(item.topic_title).end()
							 .appendTo("div.topic");
			
		});

		var sub_keywordList=dat.sub_topics;

		$.each(sub_keywordList, function(idx, item) {
		topicItem_sub.clone().find(".keyword").text(item.topic_title).end()
							 .appendTo("div.topic-additional");
			
		});
		
	}
	var count=0;
	var idx_=0;
	$.each(contentsList, function(idx, item) {
		idx_+=1;
		bookItem.clone().attr("book-idx", ((Number(cur_page)-1)*5+idx_)).find(".book-title").text(item.book_source).end()
						.find(".book-author").text(item.book_author).end()
						.find(".book-detail").text(item.book_highlighted_contents).end()
						.find(".book-date").text(item.book_year).end()
						.find("img").attr("idxImg",count).attr("src",item.book_image_url).end()
						.appendTo(".book-item-container"); 
		
		book_id_array[idx]=item.book_id;
		book_isbn_array[idx]=item.book_isbn;
		});
	
	

	$(".book-item").each(function(idx){
		single_book_diagnosis=diagnosis;
		
		var single_book_url="http://143.248.92.77:8080/kirc_ekp/project/single_book_detail.html?="+book_id_array[idx]+"="+book_isbn_array[idx]+"="+single_book_diagnosis;
		var idx_= $(this).attr("book-idx");
		
		single_book_url=single_book_url+idx_+"/rel=none;"
		 $(this).find(".book-title").attr("href",single_book_url);
		 console.log(single_book_url);

		 
	});

	$(".book-detail").each(function(idx){
		 $(this).attr("id",count);
		 count+=1;
	});

	var count=0;
	$(".book-detail").each(function(idx){
		console.log($(this).text());
		 document.getElementById(count.toString()).innerHTML=$(this).text();
		 count+=1;
	});
	
	});

}


function bringPaperResult(query,mode)
{	
	
	query_want="http://143.248.92.77:8080/kirc_ekp/apis/getPapers?q="+query+"&prev="+((Number(cur_page)-1)*5+1)+"&next="+((Number(cur_page)-1)*5+5);
	

	$(".paper-item-container").empty();


	client.sendAPI("GET", query_want, null, function(dat) {
	var data = dat;
	var contentsList = data.journals;
	

	if(mode=="initial")
	{
		var keywordList=dat.main_topics;
		console.log(keywordList);
		
		
		$.each(keywordList, function(idx, item) {
		topicItem.clone().find(".keyword").text(item.topic_title).end()
							 .appendTo("div.topic");
			
		});

		var sub_keywordList=dat.sub_topics;

		$.each(sub_keywordList, function(idx, item) {
		topicItem_sub.clone().find(".keyword").text(item.topic_title).end()
							 .appendTo("div.topic-additional");
			
		});
		
	}
	
	
	var urlList=new Array();
	var count=0;
	$.each(contentsList, function(idx, item) {
		paperItem.clone().find(".paper-title").text(item.journal_title).end()
						.find(".paper-author").text(item.author).end()
						.find(".paper-detail").text(item.highlighted_contents).end()
						.find(".paper-date").text(item.book_year).end()
						.appendTo(".paper-item-container"); 
		urlList[idx]=item.pmc_url;
		console.log("url paper 확인");
	
		});
	
	
	$(".paper-item").each(function(idx){
		 
		 $(this).find(".paper-title").attr("href",urlList[idx]);
	});

	$(".paper-detail").each(function(idx){
		 $(this).attr("id",count.toString()+"p");
		 count+=1;
	});

	var count=0;
	$(".paper-detail").each(function(idx){
		
		 document.getElementById(count.toString()+"p").innerHTML=$(this).text();
		 count+=1;
	});
	
	});

}


	

function fitImageSize(obj, href, maxWidth, maxHeight) 
{
	var image = new Image();

	image.onload = function(){
	
		var width = image.width;
		var height = image.height;
		
		var scalex = maxWidth / width;
		var scaley = maxHeight / height;
		
		var scale = (scalex < scaley) ? scalex : scaley;
		if (scale > 1) 
			scale = 1;
		
		obj.width = scale * width;
		obj.height = scale * height;
		
		obj.style.display = "";
	}
	image.src = href;
}

function selfCol(obj,nextcol)
{	
	var frm=document.all;
	next_c="frm."+nextcol+".focus();"
	eval(next_c);
		
		
	
}





function after_select()
{
	

}

$('#sbox').change(function(){
	
	align_category=this.value;
	chageCategory();

});



function showLoadingImg()
{
	
	wrapWindowByMask();
}

      
      
function hideLoadingImg()
{
    $('#mask, .window').hide();  
}


function wrapWindowByMask(){
	//화면의 높이와 너비를 구한다.
	var maskHeight = $(document).height();  
	var maskWidth = $(window).width();  

	//마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채운다.
	$('#mask').css({'width':maskWidth,'height':maskHeight});  

	//애니메이션 효과 - 일단 1초동안 까맣게 됐다가 80% 불투명도로 간다.
	      
	$('#mask').fadeTo("slow",1);    

	//윈도우 같은 거 띄운다.
	$('.window').show();
}