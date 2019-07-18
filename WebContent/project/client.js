////////////////////////////////////////////////////////////////////////////////
// OUNIWANG CLASS - Singleton
//
function Client() {
	this.sendAPI = function(method, url, data, successCallback, errorCallback) {
		if (data == null) {
			data = {};
		}

		var ajaxOpt = { 
				type: method,
				data : data,
				crossDomain: true,
				url: url,
				success: function(data){        
					successCallback(data);
				}, 
				error : function(jqXHR, textStatus, errorThrown) {
					if(errorCallback) {
						errorCallback(jqXHR, textStatus, errorThrown);
					}
				}
			}

		if(method == "POST" || method == "GET")	{
			ajaxOpt["contentType"] = false;
			ajaxOpt["processData"] = false;
		}

		$.ajax(ajaxOpt);
	};
}

client = new Client();
