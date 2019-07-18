window.API_MAIN = "http://api.ouniwang.cn/v1_1/";
window.API_CONTENTS = "contents";
window.GAST="http://kyjk3.cafe24.com/project/data_1.txt";
window.PLOYP="http://kyjk3.cafe24.com/project/data_2.txt";
window.ADENOMA="http://kyjk3.cafe24.com/project/data_3.txt";

//VARIABLE
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




var currentMorphology=1; //1,2,3
var currentAddtionalPage=new Array();
currentAddtionalPage=[0,0,0];


//EVENT

$(".gast-get-contents").click(function(){
	client.sendAPI("GET",GAST, null, 
		function(dat){
			currentMorphology=1;
			var data = JSON.parse(dat);
			setTableVar();
			
			//deleteTable();
			//test
			$("[class=content-value]").attr("required",false);
			$("[name=input_1]").attr("required",true);
			$("[name=input_2]").attr("required",true);
			$("[name=input_3]").attr("required",true);
			$("[name=input_4]").attr("required",true);
			$("[name=input_5]").attr("required",true);

			$("td.btn3-header").addClass("inactive-item");
			$("td.empty-item").removeClass("inactive-item");

			tableSetting();
			var contentsList=data.contents_list;
			$.each(contentsList, function(idx, item) {
				if(idx==0)
				{
					$(".content-id-1").text(item.id);
					$(".content-title-1").text(item.title);
					$(".content-range-1").text(item.range);
					$(".content-description-1").text(item.description);
				}
				else if(idx==1)
				{
					$(".content-id-2").text(item.id);
					$(".content-title-2").text(item.title);
					$(".content-range-2").text(item.range);
					$(".content-description-2").text(item.description);
				}
				else if(idx==2)
				{
					$(".content-id-3").text(item.id);
					$(".content-title-3").text(item.title);
					$(".content-range-3").text(item.range);
					$(".content-description-3").text(item.description);
				}
				else if(idx==3)
				{
					$(".content-id-4").text(item.id);
					$(".content-title-4").text(item.title);
					$(".content-range-4").text(item.range);
					$(".content-description-4").text(item.description);
				}
				else if(idx==4)
				{
					$(".content-id-5").text(item.id);
					$(".content-title-5").text(item.title);
					$(".content-range-5").text(item.range);
					$(".content-description-5").text(item.description);
				}
				
			});
	});
});

$(".ployp-get-contents").click(function(){
	client.sendAPI("GET",PLOYP, null, 
		function(dat){
			currentMorphology=2;
			var data = JSON.parse(dat);
			setTableVar();

			$("[class=content-value]").attr("required",false);
			$("[name=input_1]").attr("required",true);
			$("[name=input_2]").attr("required",true);

			$("[name=input_1]").attr("autofocus",false);
			$("[name=input_1]").attr("autofocus",true);
			deleteTable();
			tableSetting();

			$("td.btn3-header").addClass("inactive-item");
			$("td.empty-item").removeClass("inactive-item");

			var contentsList=data.contents_list;
			$.each(contentsList, function(idx, item) {
				if(idx==0)
				{
					$(".content-id-1").text(item.id);
					$(".content-title-1").text(item.title);
					$(".content-range-1").text(item.range);
					$(".content-description-1").text(item.description);
				}
				else if(idx==1)
				{
					$(".content-id-2").text(item.id);
					$(".content-title-2").text(item.title);
					$(".content-range-2").text(item.range);
					$(".content-description-2").text(item.description);
				}
				
			});
	});
});
$(".adenoma-get-contents").click(function(){
	client.sendAPI("GET",ADENOMA, null, 
		function(dat){
			currentMorphology=3;
			var data = JSON.parse(dat);
			deleteTable();
			setTableVar();
			tableSetting();


			$("[class=content-value]").attr("required",false);
			$("[name=input_1]").attr("required",true);
			$("[name=input_2]").attr("required",true);
			$("[name=input_3]").attr("required",true);
			$("[name=input_4]").attr("required",true);
			$("[name=input_5]").attr("required",true);
			$("[name=input_6]").attr("required",true);
			$("[name=input_7]").attr("required",true);
			$("[name=input_8]").attr("required",true);
			$("[name=input_11]").attr("required",true);
			$("[name=input_12]").attr("required",true);
			$("[name=input_13]").attr("required",true);
			$("[name=input_14]").attr("required",true);
			$("[name=input_15]").attr("required",true);
			$("[name=input_16]").attr("required",true);
			$("[name=input_17]").attr("required",true);

			$("[name=input_1]").attr("autofocus",false);
			$("[name=input_1]").attr("autofocus",true);


			$("td.btn3-header").removeClass("inactive-item");
			$("td.empty-item").addClass("inactive-item");


			var contentsList=data.contents_list;
			$.each(contentsList, function(idx, item) {
				if(idx==0)
				{
					$(".content-id-1").text(item.id);
					$(".content-title-1").text(item.title);
					$(".content-range-1").text(item.range);
					$(".content-description-1").text(item.description);
				}
				else if(idx==1)
				{
					$(".content-id-2").text(item.id);
					$(".content-title-2").text(item.title);
					$(".content-range-2").text(item.range);
					$(".content-description-2").text(item.description);
				}
				else if(idx==2)
				{
					$(".content-id-3").text(item.id);
					$(".content-title-3").text(item.title);
					$(".content-range-3").text(item.range);
					$(".content-description-3").text(item.description);
				}
				else if(idx==3)
				{
					$(".content-id-4").text(item.id);
					$(".content-title-4").text(item.title);
					$(".content-range-4").text(item.range);
					$(".content-description-4").text(item.description);
				}
				else if(idx==4)
				{
					$(".content-id-5").text(item.id);
					$(".content-title-5").text(item.title);
					$(".content-range-5").text(item.range);
					$(".content-description-5").text(item.description);
				}
				else if(idx==5)
				{
					$(".content-id-6").text(item.id);
					$(".content-title-6").text(item.title);
					$(".content-range-6").text(item.range);
					$(".content-description-6").text(item.description);
				}
				else if(idx==6)
				{
					$(".content-id-7").text(item.id);
					$(".content-title-7").text(item.title);
					$(".content-range-7").text(item.range);
					$(".content-description-7").text(item.description);
				}
				else if(idx==7)
				{
					$(".content-id-8").text(item.id);
					$(".content-title-8").text(item.title);
					$(".content-range-8").text(item.range);
					$(".content-description-8").text(item.description);
				}
				else if(idx==8)
				{
					$(".content-id-11").text(item.id);
					$(".content-title-11").text(item.title);
					$(".content-range-11").text(item.range);
					$(".content-description-11").text(item.description);
				}
				else if(idx==9)
				{
					$(".content-id-12").text(item.id);
					$(".content-title-12").text(item.title);
					$(".content-range-12").text(item.range);
					$(".content-description-12").text(item.description);
				}
				else if(idx==10)
				{
					$(".content-id-13").text(item.id);
					$(".content-title-13").text(item.title);
					$(".content-range-13").text(item.range);
					$(".content-description-13").text(item.description);
				}
				else if(idx==11)
				{
					$(".content-id-14").text(item.id);
					$(".content-title-14").text(item.title);
					$(".content-range-14").text(item.range);
					$(".content-description-14").text(item.description);
				}
				else if(idx==12)
				{
					$(".content-id-15").text(item.id);
					$(".content-title-15").text(item.title);
					$(".content-range-15").text(item.range);
					$(".content-description-15").text(item.description);
				}
				else if(idx==13)
				{
					$(".content-id-16").text(item.id);
					$(".content-title-16").text(item.title);
					$(".content-range-16").text(item.range);
					$(".content-description-16").text(item.description);
				}
				else if(idx==14)
				{
					$(".content-id-17").text(item.id);
					$(".content-title-17").text(item.title);
					$(".content-range-17").text(item.range);
					$(".content-description-17").text(item.description);
				}
				

			});
	});
});


$("body").on("click", ".contents-item", function() {
	$(".contents-item").removeClass("active-item");
	$(this).addClass("active-item");

	client.sendAPI("GET", API_MAIN + API_CONTENTS + "?idx=" + $(this).attr("idx"), null, function(dat) {
		var resData = dat.DAT;
		var contents = resData.contents;
		$(".contents-detail-title").text(contents.title);
		$(".contents-detail-keywords").text(contents.keywords);
		$(".contents-detail-regdt").text(contents.regdt);
		$(".contents-detail-moddt").text(contents.moddt);
	});
});


$(".val-get-contents").click(function(){

	
	addTableItem();
	//setTableVar();
	//getTableVals();
 });




loadInitialTable=function(){

	client.sendAPI("GET",GAST, null, 
		function(dat){
			currentMorphology=1;
			var data = JSON.parse(dat);
			setTableVar();
			
			deleteTable();
			//test
			$("[class=content-value]").attr("required",false);
			$("[name=input_1]").attr("required",true);
			$("[name=input_2]").attr("required",true);
			$("[name=input_3]").attr("required",true);
			$("[name=input_4]").attr("required",true);
			$("[name=input_5]").attr("required",true);



			tableSetting();
			var contentsList=data.contents_list;
			$.each(contentsList, function(idx, item) {
				if(idx==0)
				{
					$(".content-id-1").text(item.id);
					$(".content-title-1").text(item.title);
					$(".content-range-1").text(item.range);
					$(".content-description-1").text(item.description);
				}
				else if(idx==1)
				{
					$(".content-id-2").text(item.id);
					$(".content-title-2").text(item.title);
					$(".content-range-2").text(item.range);
					$(".content-description-2").text(item.description);
				}
				else if(idx==2)
				{
					$(".content-id-3").text(item.id);
					$(".content-title-3").text(item.title);
					$(".content-range-3").text(item.range);
					$(".content-description-3").text(item.description);
				}
				else if(idx==3)
				{
					$(".content-id-4").text(item.id);
					$(".content-title-4").text(item.title);
					$(".content-range-4").text(item.range);
					$(".content-description-4").text(item.description);
				}
				else if(idx==4)
				{
					$(".content-id-5").text(item.id);
					$(".content-title-5").text(item.title);
					$(".content-range-5").text(item.range);
					$(".content-description-5").text(item.description);
				}
				
			});
	});
	
}

deleteTable = function(){
	
	$("input[class=input-text]").each(function(idx)
	{
		$(this).text("");

	});
	$("input[class=content-value]").each(function(idx)
	{
		$(this).val(0);

	});


	if(currentMorphology==1)
	{
		
	}
	else if(currentMorphology==2)
	{

	}
	else
	{

	}


	
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

saveValueToTable=function(idx)
{
	if(currentMorphology==1)
	{
		arrVar1[idx]=$("input[class=content-value]:eq("+idx+")").val();
		
		

	}
	else if(currentMorphology==2)
	{
		arrVar2[idx]=$("input[class=content-value]:eq("+idx+")").val();
	}
	else
	{
		arrVar3[idx]=$("input[class=content-value]:eq("+idx+")").val();
	}
}

saveTextValToArr=function()
{
	if(currentMorphology==1)
	{		
		$("input.text-title").each(function(idx)
		{			
			console.log($(this).val());
			arrTitle1[idx]=$(this).val();
			for(var i=0;i<arrTitle1.length;i++)
			{		
				console.log("idx "+i+arrTitle1[i]);
			}

		});	
	}
	else if(currentMorphology==2)
	{
		$("input.text-title").each(function(idx)
		{			
			console.log($(this).val());
			arrTitle2[idx]=$(this).val();

		});	
	}
	else
	{
		$("input.text-title").each(function(idx)
		{			
			console.log($(this).val());
			arrTitle3[idx]=$(this).val();

		});	

	}
}

setTableVar=function()
{
	for(var i=0;i<20;i++)
	{	
		if(currentMorphology==1)
		{
			$("input[class=content-value]:eq("+i+")").val(arrVar1[i]);

		}
		else if(currentMorphology==2)
		{
			$("input[class=content-value]:eq("+i+")").val(arrVar2[i]);
		}
		else
		{
			$("input[class=content-value]:eq("+i+")").val(arrVar3[i]);
		}
	}

	//text
	if(currentMorphology==1)
	{		
		$("input.text-title").each(function(idx)
		{			
			$(this).val(arrTitle1[idx]);

		});	
	}
	else if(currentMorphology==2)
	{
		$("input.text-title").each(function(idx)
		{			
			$(this).val(arrTitle2[idx]);

		});	
	}
	else
	{
		$("input.text-title").each(function(idx)
		{			
			$(this).val(arrTitle3[idx]);

		});	

	}	
}


function tableSetting()
{
	if(currentMorphology==1)
	{	
		
		$("tr.additional-2").each(function(idx)
		{	
			
			$(this).removeClass("inactive-item-gray");
			$(this).find("input").attr("disabled",false);
			$(this).find("input").removeClass("inactive-item-gray");

			
		});	

		$("tr.additional-3").each(function(idx)
		{	
			
			$(this).removeClass("inactive-item-gray");
			$(this).find("input").attr("disabled",false);
			$(this).find("input").removeClass("inactive-item-gray");
		});	

		$("tr.additional-1").each(function(idx)
		{	
			
			$(this).addClass("inactive-item-gray");
			$(this).find("input").attr("disabled",true);
			$(this).find("input").addClass("inactive-item-gray");


		});

		$("tr.additional-1").each(function(idx)
		{
			if(idx==currentAddtionalPage[0])
			{
				return false;	
			}
			$(this).removeClass("inactive-item-gray");
			$(this).find("input").attr("disabled",false);
			$(this).find("input").removeClass("inactive-item-gray");

		});
		
		$("td.content-id-1").each(function(idx)
		{
			if(idx==5)
			{
				break;

			}
			else
			{
				$(this).val(idx);
			}


		});

	}
	else if(currentMorphology==2)
	{	

		$("tr.additional-1").each(function(idx)
		{	
			
			$(this).removeClass("inactive-item-gray");
			$(this).find("input").attr("disabled",false);
			$(this).find("input").removeClass("inactive-item-gray");

			
		});	

		$("tr.additional-3").each(function(idx)
		{	
			
			$(this).removeClass("inactive-item-gray");
			$(this).find("input").attr("disabled",false);
			$(this).find("input").removeClass("inactive-item-gray");
		});	

		$("tr.additional-2").each(function(idx)
		{	
			
			$(this).addClass("inactive-item-gray");
			$(this).find("input").attr("disabled",true);
			$(this).find("input").addClass("inactive-item-gray");
		});	

		$("tr.additional-2").each(function(idx)
		{
			if(idx==currentAddtionalPage[1])
			{
				return false;	
			}
			$(this).removeClass("inactive-item-gray");
			$(this).find("input").attr("disabled",false);
			$(this).find("input").removeClass("inactive-item-gray");

		});
		

	}
	else
	{
		$("tr.additional-1").each(function(idx)
		{	
			
			$(this).removeClass("inactive-item-gray");
			$(this).find("input").attr("disabled",false);
			$(this).find("input").removeClass("inactive-item-gray");

			
		});	

		$("tr.additional-2").each(function(idx)
		{	
			
			$(this).removeClass("inactive-item-gray");
			$(this).find("input").attr("disabled",false);
			$(this).find("input").removeClass("inactive-item-gray");
		});	

		$("tr.additional-3").each(function(idx)
		{	
			
			$(this).addClass("inactive-item-gray");
			$(this).find("input").attr("disabled",true);
			$(this).find("input").addClass("inactive-item-gray");
		});	

		$("tr.additional-3").each(function(idx)
		{
			if(idx==currentAddtionalPage[2])
			{
				return false;	
			}
			$(this).removeClass("inactive-item-gray");
			$(this).find("input").attr("disabled",false);
			$(this).find("input").removeClass("inactive-item-gray");

		});
		
	}
}

function addTableItem()
{
	if(currentMorphology==1)
	{	
		currentAddtionalPage[0]+=1;
		tableSetting();	
	}
	else if(currentMorphology==2)
	{
		currentAddtionalPage[1]+=1;
		tableSetting();
	}
	else
	{
		currentAddtionalPage[2]+=1;
		tableSetting();
	}
}
function nextCol(number,obj,nextcol)
{	
	var frm=document.all;
	if(obj.value.length>=number)
	{	if(currentMorphology==1||currentMorphology==2)
		{
			next_c="frm."+nextcol+".focus();"
			eval(next_c);
		}
		else
		{
			if(nextcol=="input_9")
			{
				next_c="frm."+"input_11"+".focus();"
				eval(next_c);
			}
			else if(nextcol=="input_10")
			{
				next_c="frm."+"input_12"+".focus();"
				eval(next_c);
			}
			else
			{
				next_c="frm."+nextcol+".focus();"
				eval(next_c);
			}
			
		}
	}
}

function selfCol(obj,nextcol)
{	
	var frm=document.all;
	if(obj.value.length>=number)
	{	
			next_c="frm."+nextcol+".focus();"
			eval(next_c);
		
		
	}
}