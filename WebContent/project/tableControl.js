
window.GAST="http://kyjk3.cafe24.com/project/data_1.txt";
window.PLOYP="http://kyjk3.cafe24.com/project/data_2.txt";
window.ADENOMA="http://kyjk3.cafe24.com/project/data_3.txt";
window.PATIENTINFO="http://kyjk3.cafe24.com/project/patient_info.txt";


window.DIAGNOSIS1="http://143.248.91.99:8080/rdr-biopsy-web/Biopsy?mode=inference&morphologyType=0&features=";
window.DIAGNOSIS2="http://143.248.91.99:8080/rdr-biopsy-web/Biopsy?mode=inference&morphologyType=2&features=";
window.DIAGNOSIS3="http://143.248.91.99:8080/rdr-biopsy-web/Biopsy?mode=inference&morphologyType=1&features=";


var arrVar1=new Array();
var arrVar2=new Array();
var arrVar3=new Array();
var arrTitle1=new Array();
var arrTitle2=new Array();
var arrTitle3=new Array();
var arrRange1=new Array();
var arrRange2=new Array();
var arrRange3=new Array();
var arrDes1=new Array();
var arrDes2=new Array();
var arrDes3=new Array();
var modalChangeTarget=0;
var modalOriginalTarget=0;
var feature_input="";

var patientArrVar1 = new Array(15); 
for (var i = 0; i < 15; i++) {
    patientArrVar1[i] = new Array(6);
}
var patientArrVar2 = new Array(15); 
for (var i = 0; i < 15; i++) {
    patientArrVar2[i] = new Array(3);
}
var patientArrVar3 = new Array(15); 
for (var i = 0; i < 15; i++) {
    patientArrVar3[i] = new Array(20);
}

for (var i = 0; i < 15; i++) {
    for(var j=0;j<5;j++)
    {
    	patientArrVar1[i][j]=0;
    }
}

for (var i = 0; i < 15; i++) {
    for(var j=0;j<2;j++)
    {
    	patientArrVar2[i][j] = 0;
    }

}

for (var i = 0; i < 15; i++) {
    for(var j=0;j<15;j++)
    {
    	patientArrVar3[i][j]=0;
    }
}


var patientcurrentMorphologyArray = new Array(15); 
for (var i = 0; i < 15; i++) {
    patientcurrentMorphologyArray[i] = 1;
}
var patientMemo=new Array(15);
var patientDiagnosis=new Array(15);

for(var i=0;i<15;i++)
{
	patientMemo[i]="Enter some memo";
}
for(var i=0;i<15;i++)
{
	patientDiagnosis[i]="";
}

var attrNumber=new Array(); //pid
var arrName=new Array();
var arrDocName=new Array();
var arrDateTest=new Array();
var arrHospital=new Array();
var arrGender=new Array();
var arrChartNum=new Array();
var arrSecurityNum=new Array();
var arrAge=new Array();
var arrResult1=new Array();
var arrResult2=new Array();
var arrState=new Array();
var arrDateIn=new Array();
var arrRemark=new Array();
var arrGross=new Array();
var diagnosis_result="";
var cur_patient_id=0;


var contentsItem = $(".contents-item").detach();
var bookItem = $(".book-item").detach();
var topicItem=$(".topic-item").detach();
var microItem=$(".micro-table-item").detach();

var currentAddtionalPage=new Array();
currentAddtionalPage=[0,0,0];


String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
}
//EVENT
$(".change-table-modal-btn").click(function(){

	changeTableModal(0,1); 
	
});
 $(".keep-table-modal-btn").click(function(){


	changeTableModal(0,0);
	
});


$(".table1-space").click(function(){

	changeTableModal(1,1);
	
});
$(".table2-space").click(function(){
	
	changeTableModal(1,2);
});
$(".table3-space").click(function(){
	
	changeTableModal(1,3);
});


$(".book-button").click(function(){
	var temp=$("textarea.diagnosis-detail-textarea").value;
	if(!diagnosis_result || diagnosis_result=="소견이 없습니다.")
	{
		alert("먼저 소견을 생성해주십시오.");
	}
	else
	{
		 window.open("http://kyjk3.cafe24.com/project/DDK_UI.html?index="+diagnosis_result+"=startpage=1=align=default=cur_page=1=mode=book");
	}
});


$(".button-diagnosis").click(function(){

	
	
	removeBookItem();
	$("textarea.diagnosis-detail").val('');
	
	

	patientDiagnosis[cur_patient_id]="";

	//settingRuleTable();
	removeModalTarget();
	

	if(patientcurrentMorphologyArray[cur_patient_id]==1)
	{
		var result="";
		for(var i=0;i<5;i++)
		{	
			var temp=patientArrVar1[cur_patient_id][i]+"";
			result=result+temp;

		}
		
		feature_input=result;

		client.sendAPI("GET", DIAGNOSIS1+result, null, function(dat) {
			
			
			if(dat.diagnosis=="NULL")
			{
				//$("textarea.diagnosis-detail").value("소견이 없습니다.");
				document.getElementById("diagnosis-detail-textarea").value = "소견이 없습니다.";
				$("#diagnosis-detail-rule-1").text("소견이 없습니다.");
				$("#diagnosis-detail-modal").text("소견이 없습니다.");
				
				
				diagnosis_result="소견이 없습니다.";
			}
			else{
				var temp=(dat.diagnosis).replaceAll("_"," ");
				document.getElementById("diagnosis-detail-textarea").value=temp;
				$("#diagnosis-detail-rule-1").text(temp);
				$("#diagnosis-detail-modal").text(temp);
				
				diagnosis_result=temp;
			}
			
			
			bringBookResult(diagnosis_result);
			saveDiagnosis(diagnosis_result);
			
			
		});

	}
	else if(patientcurrentMorphologyArray[cur_patient_id]==2)
	{
		var result="";
		for(var i=0;i<2;i++)
		{	
			var temp=patientArrVar2[cur_patient_id][i]+"";
			result=result+temp;

		}
		feature_input=result;

		client.sendAPI("GET", DIAGNOSIS2+result, null, function(dat) {
	
			if(dat.diagnosis=="NULL")
			{
				//$("textarea.diagnosis-detail").value("소견이 없습니다.");
				document.getElementById("diagnosis-detail-textarea").value = "소견이 없습니다.";
				diagnosis_result="소견이 없습니다.";
				$("#diagnosis-detail-rule-2").text("소견이 없습니다.");
				$("#diagnosis-detail-modal").text("소견이 없습니다.");

			}
			else{
			//$("textarea.diagnosis-detail").value(dat.diagnosis);

				var temp=(dat.diagnosis).replaceAll("_"," ");
			document.getElementById("diagnosis-detail-textarea").value =temp;
				diagnosis_result=temp;
				$("td.diagnosis-detail").val(temp);
				$("#diagnosis-detail-rule-2").val(temp);
				$("#diagnosis-detail-modal").val(temp);
				
			}
			
			
			bringBookResult(diagnosis_result);
			saveDiagnosis(diagnosis_result);

		});

		
	}
	else
	{
		var result="";
		for(var i=0;i<15;i++)
		{	
			var temp=patientArrVar3[cur_patient_id][i]+"";
			result=result+temp;

		}	
		feature_input=result;
		client.sendAPI("GET", DIAGNOSIS3+result, null, function(dat) {
		
			if(dat.diagnosis=="NULL")
			{
				//$("textarea.diagnosis-detail").value("소견이 없습니다.");
				document.getElementById("diagnosis-detail-textarea").value = "소견이 없습니다.";
				diagnosis_result="소견이 없습니다.";
				
				$("#diagnosis-detail-rule-3").text("소견이 없습니다.");
				$("#diagnosis-detail-modal").text("소견이 없습니다.");

			}
			else{
				//$("textarea.diagnosis-detail").value(dat.diagnosis);
				var temp=(dat.diagnosis).replaceAll("_"," ");
				document.getElementById("diagnosis-detail-textarea").value =temp;
				diagnosis_result=temp;
				$("#diagnosis-detail-rule-3").text(temp);
				$("#diagnosis-detail-modal").text(temp);
			}
			
			
			bringBookResult(diagnosis_result);
			saveDiagnosis(diagnosis_result);

		});
	}

	saveValueToTable();
	fillMicroTable();
	ruleTable3ValueSetting();
	settingRuleTable("load");
	settingRDR_UI();

	
});


//patient change and setting
$("body").on("click", ".contents-item", function() {
	$(".contents-item").removeClass("patient-select");
	$(this).addClass("patient-select");
	var targetIdx=0;
	cur_patient_id=$(this).attr("idx");
	
	targetIdx=cur_patient_id;
	var all="All";
	$(".p-doctorName").text(arrDocName[targetIdx]);
	$(".p-result1").text(arrResult1[targetIdx]);
	$(".p-result2").text(arrResult2[targetIdx]);
	$(".p-state").text(arrState[targetIdx]);
	$("td.p-hospital").text(arrHospital[targetIdx]);
	$(".p-dateIn").text(arrDateIn[targetIdx]);
	$(".p-dateTest").text(arrDateTest[targetIdx]);
	$(".p-patientName").text(arrName[targetIdx]);
	$(".p-securityNumber").text(arrSecurityNum[targetIdx]);
	$(".p-chartNumber").text(arrChartNum[targetIdx]);
	$(".p-gross").text(arrGross[targetIdx]);
	$(".p-gender").text(arrGender[targetIdx]);
	$(".p-age").text(arrAge[targetIdx]);
	$(".p-remark").text(arrRemark[targetIdx]);
	$("td.p-all").text(all);
	$(".seq").text("01");
	$(".loc-info").text("K25GALC");
	$(".cell-parameter").text("2");



	$(".table-area").removeClass("inactive-item");
	$("textarea.diagnosis-detail").val('');
	$(".diagnosis-detail").val('');

	
	diagnosis_result="";
	tableSetting();
	setTableVar();
	createMemoId();
	loadMemo();
	loadDiagnosis();
	settingRuleTable("load");
	removeBookItem();
	bringBookResult(patientDiagnosis[cur_patient_id]);
	ruleTable3ValueSetting();
	removeModalTarget();
	
	fillMicroTable();
	settingRDR_UI();


});


$(document).ajaxStart(function(){ 
      showLoadingImg();
      console.log("ajaxStart");
});

$(document).ajaxStop(function(){ 
      hideLoadingImg();
      console.log("ajaxStop");
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



$(".val-get-contents").click(function(){

	
	addTableItem();
 });

$("button.conclusion-btn").click(function(){

	var temp=document.getElementById("diagnosis-detail-textarea").value ;
	console.log("plz@@@@");
	console.log(temp);
	$("#diagnosis-detail-modal").text(temp);

	//$(".diagnosis-detail-modal").text($(""));
	if(patientDiagnosis[cur_patient_id])
	{
		$(this).attr( 'data-target', '#myModal' );
	}
	else if(patientDiagnosis[cur_patient_id]==null )
	{
		alert("diagnosis가 생성되지 않았습니다.");		
	}
	else
	{
		alert("diagnosis를 먼저 생성해주십시오.");	
	}
	
 });


removeModalTarget=function(){
	$("button.conclusion-btn").removeAttr("data-target");

	if(patientDiagnosis[cur_patient_id])
	{
		$(this).attr( 'data-target', '#myModal' );
	}
	else if(patientDiagnosis[cur_patient_id]==null)
	{
				
	}
}


loadInitialTable=function(){
	
	$(".table-area").addClass("inactive-item");

	
	//initialize values
	for(var i=0;i<20;i++)
	{
		arrVar1[i]=0;
		arrVar2[i]=0;
		arrVar3[i]=0;

	}

	for(var i=0;i<15;i++)
	{
		for(var j=0;j<6;j++)
		{
			patientArrVar1[i][j]=0;
		}
		for(var j=0;j<3;j++)
		{
			patientArrVar2[i][j]=0;
		}
		for(var j=0;j<20;j++)
		{
			patientArrVar3[i][j]=0;
		}
	}


	
	//setTableVar();

	console.log("patient info test");
	client.sendAPI("GET", PATIENTINFO, null, function(dat) {
	var data = JSON.parse(dat);
	var contentsList = data.contents_list;
	
	console.log(contentsList)

	$.each(contentsList, function(idx, item) {
		contentsItem.clone().attr("idx", idx).find(".content-pid").text(item.id).end()
							.find(".content-pname").text(item.name).end()
							.appendTo(".contents-item-container");
		});
	});

	client.sendAPI("GET", PATIENTINFO, null, function(dat) {
	var data = JSON.parse(dat);
	var contentsList = data.contents_list;
	
	$.each(contentsList, function(idx, item) {
			attrNumber[idx]=item.id;
			arrName[idx]=item.name;
			arrDocName[idx]=item.doctor;
			arrDateTest[idx]=item.date_test;
			arrHospital[idx]=item.hospital;
			arrGender[idx]=item.gender;
			arrChartNum[idx]=item.chart_num;
			arrSecurityNum[idx]=item.security_num;
			arrAge[idx]=item.age;
			arrResult1[idx]=item.result_1;
			arrResult2[idx]=item.result_2;
			arrState[idx]=item.state;
			arrDateIn[idx]=item.date_in;
			arrRemark[idx]=item.remark;
			arrGross[idx]=item.gross;
		});
	});

	
	//test 
	var query="";
	//bringBookResult(query);

}



getTableVals=function()
{


	$("input[class=content-value]").each(function(idx){
		var value = $(this).val();
	});

	// 배열의 특정순서의 Value 가져오기
     var orange = $("input[class=content-value]:eq(2)").val() ;

     // 배열의 특정순서 Value 변경하기
      $("input[class=content-value]:eq(2)").val(1);
}

saveValueToTable=function()
{		
	console.log("save problem in");
	$("td.content-value-1").each(function(idx)
	{	
		
		patientArrVar1[cur_patient_id][idx]=$(this).find("input").val();
		
	});	

	$("td.content-value-2").each(function(idx)
	{	
		patientArrVar2[cur_patient_id][idx]=$(this).find("input").val();
		
	});	

	$("td.content-value-3").each(function(idx)
	{	
		patientArrVar3[cur_patient_id][idx]=$(this).find("input").val();
		
	});	
	
}




setTableVar=function()
{

	$("td.content-value-1").each(function(idx)
	{	
		
		$(this).find("input").val(patientArrVar1[cur_patient_id][idx]);
		changeBackground_2($(this).find("input").attr("name"),patientArrVar1[cur_patient_id][idx],$(this).find("input").attr("max"));

		//form here ~ input 넘기기 
	});	


	$("td.content-value-2").each(function(idx)
	{	
		$(this).find("input").val(patientArrVar2[cur_patient_id][idx]);
		changeBackground_2($(this).find("input").attr("name"),patientArrVar2[cur_patient_id][idx],$(this).find("input").attr("max"));
		
	});	
	

	$("td.content-value-3").each(function(idx)
	{	
		$(this).find("input").val(patientArrVar3[cur_patient_id][idx]);
		changeBackground_2($(this).find("input").attr("name"),patientArrVar3[cur_patient_id][idx],$(this).find("input").attr("max"));

	});	

	fillSummaryTable();
	/*
	$("input.content-value").each(function(idx)
	{	
		console.log($(this).attr("name"));
		console.log($(this).attr("value"));
		console.log($(this).attr("max"));
		changeBackground_2($(this).attr("name"),$(this).attr("value"),$(this).attr("max"));
	});	
	*/
}

function tableSetting()
{	
	//current setting morphology=1
	changeTable(patientcurrentMorphologyArray[cur_patient_id]);

	$("tr.additional-1").each(function(idx)
	{	
		
		$(this).addClass("inactive-item-gray");
		$(this).find("input").attr("disabled",true);
		$(this).find("input").addClass("inactive-item-gray");
	});

	$("tr.additional-2").each(function(idx)
	{	
		
		$(this).addClass("inactive-item-gray");
		$(this).find("input").attr("disabled",true);
		$(this).find("input").addClass("inactive-item-gray");
	});

	$("tr.additional-3").each(function(idx)
	{	
		
		$(this).addClass("inactive-item-gray");
		$(this).find("input").attr("disabled",true);
		$(this).find("input").addClass("inactive-item-gray");
	});

	
	
}


function nextCol(number,obj,nextcol)
{	
	var frm=document.all;
	console.log("test next space");
	if(obj.value.length>=number)
	{	
			if (nextcol!="button") 
			{
				next_c="frm."+nextcol+".focus();"
				console.log(frm+nextcol+".focus()");
				eval(next_c);
			}
			else
			{	
				$("button[name=diagnosis-btn]").focus();
			}
	}
}



function bringBookResult(query)
{	
	if(query&&(query!="소견이 없습니다."))
	{	

	
		query2="http://219.252.39.18:8080/kirc_ekp/apis/getBooksbyCLI?q=ALB&next=10&prev=4&rel=%EA%B4%80%EC%B0%B0%EA%B0%92";
		query_want="http://143.248.92.77:8080/kirc_ekp/apis/getBooks?q="+query+"&prev=1&next=5";
		//q_url="http://219.252.39.18:8080/kirc_ekp/apis/getBooks?q="+query+"&prev=1&next=5"
		
		console.log(query_want);
		client.sendAPI("GET", query_want, null, function(dat) {
			var data = dat;
			var contentsList = data.books;
			
			
			console.log(contentsList)
			
			var count=0;
			$.each(contentsList, function(idx, item) {
				bookItem.clone().find(".book-title").text(item.book_source).end()
								.find(".book-author").text(item.book_author).end()
								.find(".book-detail").text(item.book_highlighted_contents).end()
								.find(".book-year").text(item.book_year).end()
								.find("img.book-img-contents").attr("idxImg",count).attr("src",item.book_image_url).end()
								.appendTo(".book-item-container");
								
			});
			

			$("td[class=book-detail]").each(function(idx){
				 $(this).attr("id",count);
				 count+=1;
			});

			var count=0;
			$("td[class=book-detail]").each(function(idx){
				
				 document.getElementById(count.toString()).innerHTML=$(this).text();
				 count+=1;
			});
			
			//keyword
			var keywordList=dat.main_topics;
			
			$.each(keywordList, function(idx, item) {
			topicItem.clone().find(".keyword").text(item.topic_title).end()
							 .appendTo(".topic-container");
			
			});
		});

	}
	
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

function contentsCheck(obj )
{
	
	if (obj.value>obj.max||obj.value<obj.min)
	{
			alert("예상범위 이상의 값을 입력하셨습니다. 다시 입력해주세요.");
			obj.value=0;
			selfCol(obj,obj.name)
	}
	else
	{

	}

}
function changeTable(obj_num)
{
	if(obj_num==1)
	{
		$("tr.table1-space").each(function(idx){
			
			$(this).addClass("active-table");
			$(this).removeClass("inactive-table");
		});
		$("tr.table2-space").each(function(idx){
			$(this).removeClass("active-table");
			$(this).addClass("inactive-table")
		});
		$("tr.table3-space").each(function(idx){
			$(this).removeClass("active-table");
			$(this).addClass("inactive-table")
		});
	}
	else if(obj_num==2)
	{

		$("tr.table2-space").each(function(idx){
			$(this).addClass("active-table");
			$(this).removeClass("inactive-table");
		});
		$("tr.table1-space").each(function(idx){
			$(this).removeClass("active-table");
			$(this).addClass("inactive-table")
		});
		$("tr.table3-space").each(function(idx){
			$(this).removeClass("active-table");
			$(this).addClass("inactive-table")
		});

	}
	else if(obj_num==3)
	{

		$("tr.table3-space").each(function(idx){
			$(this).addClass("active-table");
			$(this).removeClass("inactive-table");
		});
		$("tr.table1-space").each(function(idx){
			$(this).removeClass("active-table");
			$(this).addClass("inactive-table")
		});
		$("tr.table2-space").each(function(idx){
			$(this).removeClass("active-table");
			$(this).addClass("inactive-table")
		});
	}
}

function changeBackground(obj)
{		
	$("input[name="+ obj.name+"]").removeClass("clear-Background");
	$("input[name="+ obj.name+"]").removeClass("red-Background");
	$("input[name="+ obj.name+"]").removeClass("orange-Background");
	$("input[name="+ obj.name+"]").removeClass("yellow-Background");

	if (obj.max==3)
	{
		if (obj.value==3)
		{
			$("input[name="+ obj.name+"]").addClass("red-Background");
		}
		else if(obj.value==2)
		{
			$("input[name="+ obj.name+"]").addClass("orange-Background");
		}
		else if(obj.value==1)
		{
				$("input[name="+ obj.name+"]").addClass("yellow-Background");
		}
		else if(obj.value==0)
		{
				$("input[name="+ obj.name+"]").addClass("clear-Background");
		}
	}
	else if(obj.max==2)
	{		
		if (obj.value==2)
		{
				$("input[name="+ obj.name+"]").addClass("red-Background");
		}
		else if(obj.value==1)
		{
				$("input[name="+ obj.name+"]").addClass("yellow-Background");
		}
		else if(obj.value==0)
		{
				$("input[name="+ obj.name+"]").addClass("clear-Background");
		}

	}
	else if(obj.max==1)
	{
		 if(obj.value==1)
		{
				$("input[name="+ obj.name+"]").addClass("red-Background");
		}
		else if(obj.value==0)
		{
				$("input[name="+ obj.name+"]").addClass("clear-Background");
		}
	}
	else
	{

	}

}

function changeBackground_2(obj_name,obj_value,obj_max)
{		
	$("input[name="+ obj_name+"]").removeClass("clear-Background");
	$("input[name="+ obj_name+"]").removeClass("red-Background");
	$("input[name="+ obj_name+"]").removeClass("orange-Background");
	$("input[name="+ obj_name+"]").removeClass("yellow-Background");

	if (obj_max==3)
	{
		if (obj_value==3)
		{
			$("input[name="+ obj_name+"]").addClass("red-Background");
		}
		else if(obj_value==2)
		{
			$("input[name="+ obj_name+"]").addClass("orange-Background");
		}
		else if(obj_value==1)
		{
				$("input[name="+ obj_name+"]").addClass("yellow-Background");
		}
		else if(obj_value==0)
		{
				$("input[name="+ obj_name+"]").addClass("clear-Background");
		}
	}
	else if(obj_max==2)
	{		
		if (obj_value==2)
		{
				$("input[name="+ obj_name+"]").addClass("red-Background");
		}
		else if(obj_value==1)
		{
				$("input[name="+ obj_name+"]").addClass("yellow-Background");
		}
		else if(obj_value==0)
		{
				$("input[name="+ obj_name+"]").addClass("clear-Background");
		}

	}
	else if(obj_max==1)
	{
		 if(obj_value==1)
		{
				$("input[name="+ obj_name+"]").addClass("red-Background");
		}
		else if(obj_value==0)
		{
				$("input[name="+ obj_name+"]").addClass("clear-Background");
		}
	}
	else
	{

	}

}
function changeBackground_3(currentMorphology)
{	
	if(currentMorphology==1)
	{	

		$("td.rule_value_1").each(function(idx)
		{
			$(this).css('border-color','#ddd');
			$(this).css('border-width','1px');
		});
		$("td.rule_value_1").each(function(idx)
		{	
				var table_value=patientArrVar1[cur_patient_id][idx];

				if( table_value==1)
				{
					$(this).css('border-color','#efef34');
					$(this).css('border-width','5px');
					
				}
				else if(table_value==2)
				{
					$(this).css('border-color','orange');
					$(this).css('border-width','5px');
				}
				else if(table_value==0)
				{
					$(this).css('border-color','#ddd');
					$(this).css('border-width','1px');
				}
				else
				{
					$(this).css('border-color','red');
					$(this).css('border-width','5px');
				}
				
		});
	}
	else if(currentMorphology==2)
	{
		
		$("td.rule_value_2").each(function(idx)
		{
			$(this).css('border-color','#ddd');
			$(this).css('border-width','1px');
		});
		$("td.rule_value_2").each(function(idx)
		{	
				var table_value=patientArrVar2[cur_patient_id][idx];

				if( table_value==1)
				{
					$(this).css('border-color','red');
					$(this).css('border-width','5px');
				}	
		});
	}
	else
	{

	}

}
function removeBookItem()
{	
	
	$(".topic-container").empty();
 	$(".book-item").empty();
	$(".topic-item").empty();
	//$(".book-item-container").empty();

}


function fillSummaryTable()
{	
	//clean the summary table
	$("tr.summary-item").each(function(idx)
	{
		
		$(this).find("td.summary-title").text("");
		$(this).find("td.summary-id").text("");
		$(this).find("td.summary-value span").text("empty");
		
		$(this).find("td.summary-value span").addClass("summary-hidden-item");

	});



	var summaryTableVar=new Array(9);
	var summaryTableTitle=new Array(9);
	var summaryTableid=new Array(9);
	var summaryItemCount=0;
	if(patientcurrentMorphologyArray[cur_patient_id]==1)
	{
		$("td.content-value-1").each(function(idx)
		{	
			if(patientArrVar1[cur_patient_id][idx]!=0)
			{	

				summaryTableTitle[summaryItemCount]=$("td.content-title-1:eq("+idx+")").text();
				summaryTableVar[summaryItemCount]=patientArrVar1[cur_patient_id][idx];

				summaryTableid[summaryItemCount]=$("td.content-id-1:eq("+idx+")").text();
				

				summaryItemCount+=1;
			}

		});

	}
	else if(patientcurrentMorphologyArray[cur_patient_id]==2)
	{
		$("td.content-value-2").each(function(idx)
		{	
			if(patientArrVar2[cur_patient_id][idx]!=0)
			{	

				summaryTableTitle[summaryItemCount]=$("td.content-title-2:eq("+idx+")").text();
				summaryTableVar[summaryItemCount]=patientArrVar2[cur_patient_id][idx];
				summaryTableid[summaryItemCount]=$("td.content-id-2:eq("+idx+")").text();
				summaryItemCount+=1;
			}

		
		});	
	
	}
	else if(patientcurrentMorphologyArray[cur_patient_id]==3)
	{
		$("td.content-value-3").each(function(idx)
		{	
			if(patientArrVar3[cur_patient_id][idx]!=0)
			{	

				summaryTableTitle[summaryItemCount]=$("td.content-title-3:eq("+idx+")").text();
				summaryTableVar[summaryItemCount]=patientArrVar3[cur_patient_id][idx];
				summaryTableid[summaryItemCount]=$("td.content-id-3:eq("+idx+")").text();
				summaryItemCount+=1;
			}

		
		});	
	}
	
	$("tr.summary-item").each(function(idx)
	{
		if(idx==summaryItemCount)
		{
			return false;
		}
		$(this).find("td.summary-title").text(summaryTableTitle[idx]);
		$(this).find("td.summary-id").text(summaryTableid[idx]);
		
		$(this).find("td.summary-value span").text(summaryTableVar[idx]);
		$(this).find("td.summary-value span").removeClass("summary-hidden-item")

	});



}

function removeMicroTable()
{
	$(".micro-table-item").empty();
}
function fillMicroTable()
{	

	removeMicroTable();
	$("tr.summary-item").each(function(idx)
		{	
			var m_value="";
			if(Number($(this).find("td.summary-value span").text())>0)
			{	
				if(patientcurrentMorphologyArray[cur_patient_id]==1)
				{
					if(Number($(this).find("td.summary-value span").text())==0)
					{
						m_value="Absent";
					}
					else if(Number($(this).find("td.summary-value span").text())==1)
					{
						m_value="Mild";
					}
					else if(Number($(this).find("td.summary-value span").text())==2)
					{
						m_value="Moderate";
					}

					else if(Number($(this).find("td.summary-value span").text())==3)
					{
						m_value="Marked";
					}
					
				}
				else if(patientcurrentMorphologyArray[cur_patient_id]==2)
				{
					if(Number($(this).find("td.summary-value span").text())==0)
					{
						m_value="Absent";
					}
					else if(Number($(this).find("td.summary-value span").text())==1)
					{
						m_value="Present";
					}
				}
				else
				{
					if(Number($(this).find("td.summary-value span").text())==0)
					{
						m_value="Absent";
					}
					else if(Number($(this).find("td.summary-value span").text())==1)
					{
						m_value="Well Differentiated";
					}
					else if(Number($(this).find("td.summary-value span").text())==2)
					{
						m_value="Moderately Differentiated";
					}
				}

				microItem.clone().find("td.micro-name").text($("td.summary-title:eq("+idx+")").text()).end()
								.find("td.micro-value").text(m_value).end()
					 			.appendTo("table.micro-table-container");
				
			}

		});

}

function createMemoId()
{	
	var date=new Date();
	
	var memoId=arrName[cur_patient_id]+" "+date.getFullYear()+"."+(date.getMonth()+1)+"."+date.getDate()+" / 판독자: "+$(".p-doctorName").text();
	document.getElementById("memoId").innerHTML=memoId;
}
function saveMemo()
{
	patientMemo[cur_patient_id]=$("#memoTextBox").val() ;


}
function loadMemo()
{
	

	$("#memoTextBox").val(patientMemo[cur_patient_id]);
	
}


function saveDiagnosis(diagnosis)
{
	
	patientDiagnosis[cur_patient_id]=diagnosis;
} 

function loadDiagnosis()
{
	
	document.getElementById("diagnosis-detail-textarea").value = patientDiagnosis[cur_patient_id];
	$("#diagnosis-detail-modal").text(patientDiagnosis[cur_patient_id]);
	$("#diagnosis-detail-rule-1").text(patientDiagnosis[cur_patient_id]);
	$("#diagnosis-detail-rule-2").text(patientDiagnosis[cur_patient_id]);
	$("#diagnosis-detail-rule-3").text(patientDiagnosis[cur_patient_id]);

	

}
function settingRuleTable(type)
{		
	var delay=0;
	if(type=="empty")
	{
		delay=100;
	}
	else if(type=="load")
	{
		delay=1800;
	}

   setTimeout(function() {
   	var temp=document.getElementById("diagnosis-detail-textarea").value 

	//if($("textarea.diagnosis-detail").value()&&($("textarea.diagnosis-detail").value()!="소견이 없습니다."))
	if(temp!="소견이 없습니다."&&temp)
	{	
		
		
		var rule_url="http://143.248.91.119:8080/RestScoreBoard/rest/score/get/";
		if(patientcurrentMorphologyArray[cur_patient_id]==1)
		{	
			$(".rule-table-1").removeClass("inactive-item");
			$(".rule-table-2").addClass("inactive-item");
			$(".rule-table-3").addClass("inactive-item");

			$("td.rule_value_1").each(function(idx)
			{	
				$(this).text(patientArrVar1[cur_patient_id][idx]);

			});

			changeBackground_3(1);
			

			var result="";
			for(var i=0;i<5;i++)
			{	
				var temp=patientArrVar1[cur_patient_id][i]+"";
				result=result+temp;

			}
			rule_url+="gastritis/";
			rule_url+=result;


			client.sendAPI("GET", rule_url, null, function(dat) {
	
			var Atrophy=dat.Probability.Atrophy;
			var Neutrophilic_infiltration=dat.Probability.Mononuclearcellinfiltration;
			var Hpylori=dat.Probability.Hpylori;
			var Intestinal_metaplasia=dat.Probability.Intestinalmetaplasia;
			var Neutrophilicinfiltration=dat.Probability.Neutrophilicinfiltration;
			$("td.HP").text(Hpylori.toString()+"%");
			$("td.AT").text(Atrophy.toString()+"%");
			$("td.NI").text(Neutrophilicinfiltration.toString()+"%");
			$("td.IM").text(Intestinal_metaplasia.toString()+"%");
			$("td.MCI").text(Neutrophilic_infiltration.toString()+"%");

			});
		}
		else if(patientcurrentMorphologyArray[cur_patient_id]==2)
		{
			
			$(".rule-table-2").removeClass("inactive-item");
			$(".rule-table-1").addClass("inactive-item");
			$(".rule-table-3").addClass("inactive-item");

			$("td.rule_value_2").each(function(idx)
			{	
				$(this).text(patientArrVar2[cur_patient_id][idx]);
			});
			changeBackground_3(2);

			var result="";
			for(var i=0;i<2;i++)
			{	
				var temp=patientArrVar2[cur_patient_id][i]+"";
				result=result+temp;

			}
			rule_url+="polyp/";
			rule_url+=result;


			client.sendAPI("GET", rule_url, null, function(dat) {
	
			var Fundicgland=dat.Probability.Fundicgland;
			var Proliferation=dat.Probability.Proliferation;
		
			$("td.FG").text(Fundicgland.toString()+"%");
			$("td.PF").text(Proliferation.toString()+"%");
			

			});
		}
		else if(patientcurrentMorphologyArray[cur_patient_id]==3)
		{
			
			$(".rule-table-3").removeClass("inactive-item");
			$(".rule-table-2").addClass("inactive-item");
			$(".rule-table-1").addClass("inactive-item");

			ruleTable3ValueSetting();


			var result="";
			for(var i=0;i<15;i++)
			{	
				var temp=patientArrVar3[cur_patient_id][i]+"";
				result=result+temp;

			}
			rule_url+="carcinoma/";
			rule_url+=result;


			client.sendAPI("GET", rule_url, null, function(dat) {
			
			//come back 고치기 
			var IncreaseofNCrationtestinalmetaplasia=dat.Probability.IncreaseofNCrationtestinalmetaplasia;
			var Papillary=dat.Probability.Papillary;
			var Atypia=dat.Probability.Atypia;
			var Tubular=dat.Probability.Tubular;
			var Mitoses=dat.Probability.Mitoses;
			var Hyperchromasia=dat.Probability.Hyperchromasia;
			var Nucleolei=dat.Probability.Nucleolei;
			var Papillary=dat.Probability.Spindlecell;
			var Spindlecell=dat.Probability.Papillary;
			var Necrosis=dat.Probability.Necrosis;
			var Smallcell=dat.Probability.Smallcell;
			var Mucinous=dat.Probability.Mucinous;
			var Squamous=dat.Probability.Squamous;
			var Stromalinvasion=dat.Probability.Stromalinvasion;
			var Pleomorphism=dat.Probability.Pleomorphism;
			var Signetringcell=dat.Probability.Signetringcell;

			
			$("td.Hyperchromasia").text(Hyperchromasia.toString()+"%");
			$("td.Pleomorphism").text(Pleomorphism.toString()+"%");
			$("td.Nucleolei").text(Nucleolei.toString()+"%");
			$("td.Mitoses").text(Mitoses.toString()+"%");
			$("td.Increase").text(IncreaseofNCrationtestinalmetaplasia.toString()+"%");
			$("td.Tubular").text(Tubular.toString()+"%");
			$("td.Signet").text(Signetringcell.toString()+"%");
			$("td.Mucinous").text(Mucinous.toString()+"%");
			$("td.Squamous").text(Squamous.toString()+"%");
			$("td.Small").text(Smallcell.toString()+"%");
			$("td.Pleomorphism").text(Pleomorphism.toString()+"%");
			$("td.Spindle").text(Spindlecell.toString()+"%");
			$("td.Papilary").text(Papillary.toString()+"%");
			$("td.Atypia").text(Atypia.toString()+"%");
			$("td.Necrosis").text( Necrosis.toString()+"%");
			$("td.Stromal").text(Stromalinvasion.toString()+"%");
		

			});
		
		}

	}
	else
	{
		$(".rule-table-1").addClass("inactive-item");
		$(".rule-table-2").addClass("inactive-item");
		$(".rule-table-3").addClass("inactive-item"); //addclass 로

	}



   }, delay);



	
}

function ruleTable3ValueSetting(){
	$("tr.rule-3-1-item").each(function(idx)
	{
		$(this).addClass("inactive-item");
	});
	$("tr.rule-3-2-item").each(function(idx)
	{
		$(this).addClass("inactive-item");
	});
	$("tr.rule-3-3-item").each(function(idx)
	{
		$(this).addClass("inactive-item");
	});

	$("tr.rule-3-1-item").each(function(idx)
	{	
		if(patientArrVar3[cur_patient_id][idx]>0)
		{
			$(this).removeClass("inactive-item");
				
			$(this).find("td.rule_value").css('border-color','red');
			$(this).find("td.rule_value").css('border-width','5px');
			$(this).find("td.rule_value").text('1');
		}
	});
	$("tr.rule-3-1-item-hidden").each(function(idx)
	{	
		if(patientArrVar3[cur_patient_id][idx]>0)
		{
			$(this).removeClass("inactive-item");

		}
	});

	$("tr.rule-3-2-item").each(function(idx)
	{	
		if(patientArrVar3[cur_patient_id][idx+5]>0)
		{
			$(this).removeClass("inactive-item");
			
			$(this).find("td.rule_value").css('border-color','red');
			$(this).find("td.rule_value").css('border-width','5px');

			$(this).find("td.rule_value").text('1');
		}
	});
	$("tr.rule-3-2-item-hidden").each(function(idx)
	{	
		if(patientArrVar3[cur_patient_id][idx+5]>0)
		{
			$(this).removeClass("inactive-item");
		}
	});

	$("tr.rule-3-3-item").each(function(idx)
	{	
		if(patientArrVar3[cur_patient_id][idx+12]>0)
		{
			$(this).removeClass("inactive-item");
			if(patientArrVar3[cur_patient_id][idx+12]==1)


			{
				

			$(this).find("td.rule_value").css('border-color','yellow');
			$(this).find("td.rule_value").css('border-width','5px');

				$(this).find("td.rule_value").text('1');
			}
			else if(patientArrVar3[cur_patient_id][idx+12]==2)
			{
				$(this).find("td.rule_value").css('border-color','red');
				$(this).find("td.rule_value").css('border-width','5px');
				$(this).find("td.rule_value").text('2');
			}

		}
	});
	$("tr.rule-3-3-item-hidden").each(function(idx)
	{	
		if(patientArrVar3[cur_patient_id][idx+12]>0)
		{
			$(this).removeClass("inactive-item");

		}
	});


}

function changeTableModal(sig,targetTable) //sig==1 active modal sig==0 inactive modal 
{	

	
	if(sig==1)
	{	
		
		if(targetTable==patientcurrentMorphologyArray[cur_patient_id])
		{
			return false;
		}
		modalChangeTarget=targetTable;
		$(".modal-change-table").removeClass("inactive-item");
		$(".modal-change-table-back").removeClass("inactive-item");
		$(".modal-change-table").addClass("modal-change-table-block");

		var original_table="";
		var target_table="";
		if(patientcurrentMorphologyArray[cur_patient_id]==1)
		{
			original_table="위염";
		}
		else if(patientcurrentMorphologyArray[cur_patient_id]==2)
		{
			original_table="용종";
		}
		else if(patientcurrentMorphologyArray[cur_patient_id]==3)
		{
			original_table="선종/선암/암종";
		}

		if(targetTable==1)
		{
			target_table="위염";
		}
		else if(targetTable==2)
		{
			target_table="용종";	
		}
		else if(targetTable==3)
		{
			target_table="선종/선암/암종";	
		}

		var notice=original_table+"에 이미 값이 입력되어 있습니다.";
		var notice2=target_table+"으로 이동하면 "+original_table+"의 내용은 모두 0으로 변경됩니다."
		$(".change-table-modal-btn").text(target_table+"으로 이동");
		$(".change-table-modal-body1").text(notice);
		$(".change-table-modal-body2").text(notice2);

	}	
	else if(sig==0)
	{
		if(targetTable==0)
		{
			
	
		}
		else if(targetTable==1)
		{	
			beforechange=patientcurrentMorphologyArray[cur_patient_id];
			if(modalChangeTarget==1)
			{
				changeTable(1);
				patientcurrentMorphologyArray[cur_patient_id]=1;
	
			}
			else if(modalChangeTarget==2)
			{
				changeTable(2);
				patientcurrentMorphologyArray[cur_patient_id]=2;
			
			}
			else if(modalChangeTarget==3)
			{
				changeTable(3);
				patientcurrentMorphologyArray[cur_patient_id]=3;
			
			}
			fillSummaryTable();
			eraseTableValue(beforechange);
			removeBookItem();
			$("textarea.diagnosis-detail").val("");
			//document.getElementById("diagnosis-detail-textarea").value =""
			patientDiagnosis[cur_patient_id]="";
			$("#memoTextBox").val("Enter some memo");
			patientMemo[cur_patient_id]=$("#memoTextBox").val();
			settingRuleTable("empty");
			removeModalTarget();
	
	


		}
		$(".modal-change-table").addClass("inactive-item");
		$(".modal-change-table-back").addClass("inactive-item");
		
	}

}

function eraseTableValue(targetTable)
{
	if(targetTable==1)
	{

		$("td.content-value-1").each(function(idx)
		{	
			$(this).find("input").val(0);
			patientArrVar1[cur_patient_id][idx]=$(this).find("input").val();
			
		});	

	}
	else if(targetTable==2)
	{
		$("td.content-value-2").each(function(idx)
		{	
			$(this).find("input").val(0);
			patientArrVar2[cur_patient_id][idx]=$(this).find("input").val();
			
		});	

	}
	else if(targetTable==3)
	{
		$("td.content-value-3").each(function(idx)
		{	
			$(this).find("input").val(0);
			patientArrVar3[cur_patient_id][idx]=$(this).find("input").val();
			
		});	

	}
	$("td.content-value-1").each(function(idx)
	{	
		changeBackground_2($(this).find("input").attr("name"),patientArrVar1[cur_patient_id][idx],$(this).find("input").attr("max"));
		//form here ~ input 넘기기 
	});	
	$("td.content-value-2").each(function(idx)
	{	
		changeBackground_2($(this).find("input").attr("name"),patientArrVar2[cur_patient_id][idx],$(this).find("input").attr("max"));
	});	
	$("td.content-value-3").each(function(idx)
	{	
		changeBackground_2($(this).find("input").attr("name"),patientArrVar3[cur_patient_id][idx],$(this).find("input").attr("max"));
	});	
}



function settingRDR_UI()
{	
	var check="zero_check";
	if(patientcurrentMorphologyArray[cur_patient_id]=="1")
	{
		
		for(var i=0;i<5;i++)
		{
			if(patientArrVar1[cur_patient_id][i]!=0)
			{	
				
				check="not_zero";
			}
		}
		
	}
	else if(patientcurrentMorphologyArray[cur_patient_id]=="2")
	{
			
		for(var i=0;i<2;i++)
		{
			if(patientArrVar2[cur_patient_id][i]!=0)
			{
				check="not_zero";
			}
		}
	}
	else
	{

		for(var i=0;i<15;i++)
		{
			if(patientArrVar3[cur_patient_id][i]!=0)
			{
				check="not_zero";
			}
		}
	}

	if(check=="zero_check")
	{
			$(".RDR-update-button").addClass("inactive-item");
			$(".RDR-add-button").addClass("inactive-item");
	}
	else{

	 setTimeout(function() {
		
		$(".RDR-update-button").addClass("inactive-item");
		$(".RDR-add-button").addClass("inactive-item");
		
		if(document.getElementById("diagnosis-detail-textarea").value!="소견이 없습니다.")
		{
			$(".RDR-update-button").removeClass("inactive-item");
			$(".RDR-add-button").addClass("inactive-item");
		}
		else if(document.getElementById("diagnosis-detail-textarea").value=="소견이 없습니다.")
		{
			
			$(".RDR-update-button").addClass("inactive-item");
			$(".RDR-add-button").removeClass("inactive-item");
		}

	   }, 500);

	}

}

function goToUpdate()
{

	var result="";
	if(patientcurrentMorphologyArray[cur_patient_id]==1)
	{
			for(var i=0;i<5;i++)
		{	
			var temp=patientArrVar1[cur_patient_id][i]+"";
			result=result+temp;

		}	
		window.open("http://143.248.91.99:8080/rdr-biopsy-web/index.htm?morphologyType=0&features="+ result+"&kaMode=edit");
	}
	else if(patientcurrentMorphologyArray[cur_patient_id]==2)
	{
		for(var i=0;i<2;i++)
		{	
			var temp=patientArrVar2[cur_patient_id][i]+"";
			result=result+temp;

		}	
		window.open("http://143.248.91.99:8080/rdr-biopsy-web/index.htm?morphologyType=2&features="+ result+"&kaMode=edit");
	}
	else if(patientcurrentMorphologyArray[cur_patient_id]==3)
	{
		for(var i=0;i<15;i++)
		{	
			var temp=patientArrVar3[cur_patient_id][i]+"";
			result=result+temp;

		}	
	window.open("http://143.248.91.99:8080/rdr-biopsy-web/index.htm?morphologyType=1&features="+ result+"&kaMode=edit");
	}
	
}

function goToAdd()
{


		var result="";
	if(patientcurrentMorphologyArray[cur_patient_id]==1)
	{
			for(var i=0;i<5;i++)
		{	
			var temp=patientArrVar3[cur_patient_id][i]+"";
			result=result+temp;

		}	
		window.open("http://143.248.91.99:8080/rdr-biopsy-web/index.htm?morphologyType=0&features="+ result+"&kaMode=add");
	}
	else if(patientcurrentMorphologyArray[cur_patient_id]==2)
	{
		for(var i=0;i<2;i++)
		{	
			var temp=patientArrVar2[cur_patient_id][i]+"";
			result=result+temp;

		}	
		window.open("http://143.248.91.99:8080/rdr-biopsy-web/index.htm?morphologyType=2&features="+ result+"&kaMode=add");
	}
	else if(patientcurrentMorphologyArray[cur_patient_id]==3)
	{
		for(var i=0;i<15;i++)
		{	
			var temp=patientArrVar3[cur_patient_id][i]+"";
			result=result+temp;

		}	
	window.open("http://143.248.91.99:8080/rdr-biopsy-web/index.htm?morphologyType=1&features="+ result+"&kaMode=add");
	}
	
	
}