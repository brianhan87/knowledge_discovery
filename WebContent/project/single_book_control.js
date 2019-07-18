
var book_isbn="";
var book_id="";
var diagnosis="";


String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
}

loadInitialStat=function(){
		
	
	$(document).ready(function () {
	 
	    var fullUrl=window.location.href;
	    console.log(fullUrl);

	    var parameter=fullUrl.split("=");
	    
	    console.log(parameter);
	    book_isbn=parameter[2];
	    book_id=parameter[1];
	    diagnosis=parameter[3];
	    
	    var temp=diagnosis.split("/");
	    diagnosis=temp[0];
	    diagnosis=diagnosis.replaceAll("%20"," ");
	    diagnosis=diagnosis.replaceAll("0"," ");
	    diagnosis=diagnosis.replaceAll("1"," ");
	    diagnosis=diagnosis.replaceAll("2"," ");
	    diagnosis=diagnosis.replaceAll("3"," ");
	    diagnosis=diagnosis.replaceAll("4"," ");
	    diagnosis=diagnosis.replaceAll("5"," ");
	    diagnosis=diagnosis.replaceAll("6"," ");
	    diagnosis=diagnosis.replaceAll("7"," ");
	    diagnosis=diagnosis.replaceAll("8"," ");
	    diagnosis=diagnosis.replaceAll("9"," ");
	
	    bringBookResult(book_isbn,book_id,"next");
	});
	
	
}


function bringBookResult(isbn,book_id,type)
{	
	
	query_want="http://143.248.92.77:8080/kirc_ekp/apis/getdetails?id="+book_id+"&isbn="+isbn;
	
	client.sendAPI("GET", query_want, null, function(dat) {

		
		if(dat.book_contents=="none")
		{
			if(type=="previous")
			{	
				book_id=Number(book_id)-1;
				//bringBookResult(isbn,book_id,"previous");
				location.replace("http://143.248.92.77:8080/kirc_ekp/project/single_book_detail.html?="+book_id+"="+book_isbn+"="+diagnosis); 
				
			}
			else if(type=="next")
			{
				console.log(book_id);
				book_id=Number(book_id)+1;
				//bringBookResult(isbn,book_id,"next");
				location.replace("http://143.248.92.77:8080/kirc_ekp/project/single_book_detail.html?="+book_id+"="+book_isbn+"="+diagnosis); 
				
			}

		}
		else{
		console.log(query_want);
		console.log("내용뿌릴때");
		console.log(book_id);
		var book_year=dat.book_year;
		var book_content=dat.book_contents;
		var book_image_url=dat.book_image_url;
		var book_title=dat.book_source;
		var book_author=dat.book_author;
		$(".book_title").text(book_title);
		$(".book_year").text(book_year);
		$(".book_author").text(book_author);
		$(".image_part").attr("src",book_image_url);
		$(".book_content").text(book_content);
		$(".diagnosis").text(diagnosis);

		
		}
	});

}

function click_previous()
{
	$(".book_content").empty();
	var new_bookid=Number(book_id);
	new_bookid-=1;
	book_id=Number(book_id)-1;
	bringBookResult(book_isbn,new_bookid,"previous");
}
function click_next()
{
	$(".book_content").empty();
	var new_bookid=Number(book_id);
	new_bookid+=1;
	book_id=Number(book_id)+1;
	bringBookResult(book_isbn,new_bookid,"next");
}
