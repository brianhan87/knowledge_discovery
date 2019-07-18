<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>RDR Web Interface - Knowledge Acquisition</title>
        <link href="./styles/rdr-styles.css" rel="stylesheet" />
        <link href="./styles/table.css" rel="stylesheet" />
        <link rel="stylesheet" href="./styles/bootstrap-3.3.6.min.css">
        <link rel="stylesheet" href="./styles/style.css">
        <script type="text/javascript" src="./scripts/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="./scripts/jquery-1.12.0.min.js"></script>
        <script type="text/javascript" src="./scripts/bootstrap-3.3.6.min.js"></script>
<!--         <script type="text/javascript" src="./scripts/colResizable-1.5.min.js"></script> -->
<!--         <script type="text/javascript" src="./scripts/ajax.js"></script> -->
<!--         <script type="text/javascript" src="./scripts/function.js"></script> -->
<!--         <script type="text/javascript" src="./scripts/event.js"></script> -->
<!--         <script type="text/javascript" src="./scripts/render.js"></script> -->
        <script type="text/javascript" src="./scripts/jstree.js"></script>
        <script type="text/javascript">
        $(function () {
        	
        	//jstree
			$.ajax({
			type:"get",
			url:  "Biopsy?mode=getRuleTree",
			success: function(data){
				console.log(data);
				$('#rule_tree').jstree({
					'core' : data
				}).bind("loaded.jstree", function(event, data){
					//load 시 전체 노드 open
				    data.instance.open_all();
			    }).bind('select_node.jstree', function(event, data){
					//node 선택 시 data 가져오기
				    var id = data.instance.get_node(data.selected).id;
				    var condition = data.instance.get_node(data.selected).original.condition;
				    var conclusion = data.instance.get_node(data.selected).original.conclusion;
				    $("#rdr_ruleid").val(id);
				    $("#rdr_condition").val(condition);
				    $("#rdr_conclusion").val(conclusion);
				})

			 }
			});
        	//rules Tab 활성화
        	$("#tab-0, #tab-1").click(function(){
        		$(".rdr-ka-main-tab-label").removeClass("tab-active");
        		$(".rdr-ka-main-tab-label").addClass("tab-inactive");
        		$(this).removeClass("tab-inactive");
    	        $(this).addClass("tab-active");
    	        
    	        if($(this).attr("id") == "tab-0") {
    	        	$("#rule_tree_fired").hide();
    	        	$("#rule_tree").show();
    	        } else {
    	        	$("#rule_tree").hide();
    	        	$("#rule_tree_fired").show();
    	        }
        	});
        	// 테이블 클릭시 이벤트
        	$("#rdr-ka-validate-case-list-table").on("click","tr",function(){
                if(!$(this).children().is("th")){
                    $("#rdr-ka-validate-case-list-table").find("tr").removeClass("tr-selected");
                    $("#rdr-ka-validate-case-list-table").find("tr").removeClass("tr-not-selected");            
                    $(this).removeClass("tr-not-selected");
                    $(this).addClass("tr-selected");
                    var caseid = $(this).find("td").eq(0).text();
                    $("#selectedCaseId").val(caseid);
                }
        	});

        	getCaseBaseAjax();
       	});
        
        function getCaseBaseAjax(){
            $.ajax({
                url: 'Biopsy',
                dataType: "json",
                data: {
                    mode : 'getCaseBase'
                },
                success: function(data) {
                	console.log(data.cases);
                	var header = "<tr><th>Case ID</th><th>Atrophy<br></th><th>Atypia<br></th><th>Fundic gland<br></th><th>H. pylori<br></th><th>Hyperchromasia<br></th><th>Increase of N/C ratio<br></th>" +
            		"<th>Intestinal metaplasia<br></th><th>Mitoses<br></th><th>Mononuclear cell infiltration<br></th><th>Mucinous<br></th><th>Necrosis<br></th><th>Neutrophilic infiltration<br></th>" +
            		"<th>Nucleolei<br></th><th>Papillary<br></th><th>Pleomorphism<br></th><th>Proliferation<br></th><th>Signet ring cell<br></th><th>Small cell<br></th><th>Spindle cell<br></th>" +
            		"<th>Squamous<br></th><th>Stromal invasion<br></th><th>Tubular<br></th><th>class<br></th></tr>"
            		
                	$("#rdr-ka-validate-case-list-table").append(header);
            		
                	var row = "<tr>";
                	
                	for(var i=0; i<data.cases.length; i++) {
            			
            			row += "<td>" + data.cases[i].caseId + "</td>";
            			row += "<td>" + data.cases[i].attribute[0]["Atrophy "] + "</td>";
            			row += "<td>" + data.cases[i].attribute[1]["Atypia"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[2]["Fundic gland"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[3]["H. pylori"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[4]["Hyperchromasia"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[5]["Increase of N/C ratio"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[6]["Intestinal metaplasia"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[7]["Mitoses"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[8]["Mononuclear cell infiltration"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[9]["Mucinous"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[10]["Necrosis"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[11]["Neutrophilic infiltration"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[12]["Nucleolei"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[13]["Papillary"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[14]["Pleomorphism"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[15]["Proliferation"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[16]["Signet ring cell"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[17]["Small cell"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[18]["Spindle cell"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[19]["Squamous"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[20]["Stromal invasion"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[21]["Tubular"] + "</td>";
            			row += "<td>" + data.cases[i].attribute[22]["class"] + "</td></tr>";
            			
                	}
                	
         			$("#rdr-ka-validate-case-list-table").append(row);
                	
                	$("#rdr-ka-validate-case-list-table-loading").hide();
                    $("#rdr-ka-validate-case-list-table").show();
                    
                }
            });
        }
        </script>
        
    </head>

    <body>
        <div id="rdr-body">
            <div class="rdr-header">
            <div class="rdr-main-contents">
                <!-- KA Main Frame -->
                <div class="rdr-layout rdr-border rdr-ka-main-frame-label">

                </div>
                <div class="rdr-layout rdr-border rdr-ka-main-frame">

                    <!-- Left panel -->
                    <div class="rdr-layout rdr-ka-main-left-panel" style="margin-top: 0;">
                        <!-- Left Top panel -->
						
                        <!-- Left Bottom panel -->
                        <div class="rdr-layout rdr-ka-main-left-bottom-panel">
                        	
                            <div class="rdr-ka-new-conclusion-section">
                                <div class="rdr-ka-new-conclusion-label rdr-ka-new-conclusion-section-label rdr-border">
                                	<div class="rdr-ka-main-tab-label-section">
			                            <div class="rdr-border-no-bottom rdr-ka-main-tab-label tab-active" id="tab-0">
			                                KB Rules
			                            </div>
			                            <div class="rdr-border-no-bottom rdr-ka-main-tab-label tab-inactive" id="tab-1">
			                                Fired Rules
			                            </div>
			                        </div>
                                </div>
                                
                                
                            </div>
                            <div id="rule_tree" class="rdr-ka-new-conclusion-section-contents2 rdr-border" style="background-color: white;position: relative;overflow: auto;" >
                            </div>
                            <div id="rule_tree_fired" class="rdr-ka-new-conclusion-section-contents2 rdr-border" style="background-color: white;position: relative;overflow: auto;" hidden>
                            </div>
                        </div>
                    </div>

                    <!-- Right panel -->
                    <div class="rdr-layout rdr-ka-main-right-panel">

                        <!-- Tab contents -->
                        <div class="rdr-border-no-left-top rdr-ka-main-tab-section">
                            <div class="rdr-border rdr-ka-main-tab-section-contents">

                                <!-- TAB1 Select conclusion 새로운 소견 선택 tab -->
                                <div class="rdr-ka-main-tab-contents-container">
                                    <div class="rdr-ka-main-tab-contents">
                                       	<div class="rdr-ka-wrong-conclusion-section" style="padding-top: 0;">
                                            <div class="rdr-ka-wrong-conclusion-label">
                                                Conclusion List
                                            </div>
                                       	</div>
                                       	<div class="rdr-ka-new-condition-controller" >
                                             <select style="width: 750px;">
                                             	<option>NULL</option>
                                             </select>
                                             <button class="rdr-ka-new-condition-controller-add-button">Display only this</button>
                                        </div>
                                        <div class="rdr-ka-validate-case-list-section">
	                                        <div class="rdr-ka-validate-case-list-search-section">
		                                        <div class="rdr-ka-wrong-conclusion-label">
		                                                Case Browser
	                                        	<button class="rdr-ka-new-condition-controller-add-button">Add new case</button>
                                                <button class="rdr-ka-new-condition-controller-add-button">Eidt case</button>
		                                        </div>
	                                        	
	                                        </div>
	                                        
	                                        <div class="rdr-ka-validate-case-list-table-section">
	                                            <div class="rdr-ka-validate-case-list-table-container rdr-border rdr-table-container">
	                                                <div id="rdr-ka-validate-case-list-table-loading" class="rdr-loading"></div>
	                                                <table id="rdr-ka-validate-case-list-table" class="rdr-table" hidden>
	                                                </table>
	                                            </div>
	                                            
	                                        </div>
	                                        
                                        </div>
                                        <div class="rdr-ka-current-case-viewer-search-section">
			                                <div class="rdr-ka-current-case-viewer-search-label2" style="width: 100px;">
											         Selected Case ID
											</div>
											<div class="rdr-ka-current-case-viewer-search-input-container" style="width: 700px;">
												<input type="text" id="selectedCaseId" class="rdr-input-box rdr-input-width"/>
											</div>
										</div>
										<div class="rdr-ka-current-case-viewer-search-section">
			                                <div class="rdr-ka-current-case-viewer-search-label2">
											         Inferenced Conclusion(s)
											</div>
											<div style="height: 100px;">
                                                <textarea class="rdr-ka-new-conclusion-textarea"></textarea>
                                            </div>
										</div>
										<div style="margin-top: 90px;">
                                        	<button class="rdr-ka-new-condition-controller-add-button">Add conclusion</button>
                                        	<button class="rdr-ka-new-condition-controller-add-button">Delete conclusion</button>
                                            <button class="rdr-ka-new-condition-controller-add-button">Modify conclusion</button>
                                       	</div>
                                        <div class="rdr-border rdr-ka-main-left-bottom-panel2">
                                        	<div class="rdr-ka-new-conclusion-section-label2">Rule Details</div>
			                                <div class="rdr-ka-current-case-viewer-search-section">
				                                <div class="rdr-ka-current-case-viewer-search-label" style="width: 50px;padding-left: 5px;">
	                                                    Rule ID
	                                            </div>
	                                            <div class=rdr-ka-current-case-viewer-search-input-container style="width: 750px;">
	                                            	<input type="text" id="rdr_ruleid" class="rdr-input-box rdr-input-width"/>
	                                            </div>
                                            </div>
                                            <div class="rdr-ka-current-case-viewer-search-section">
	                                            <div class="rdr-ka-current-case-viewer-search-label" style="width: 50px;padding-left: 5px;">
	                                                    Condition
	                                            </div>
	                                            <div class="rdr-ka-current-case-viewer-search-input-container" style="width: 750px;">
	                                            	<input type="text" id="rdr_condition" class="rdr-input-box rdr-input-width"/>
	                                            </div>
                                            </div>
                                            <div class="rdr-ka-current-case-viewer-search-section">
	                                            <div class="rdr-ka-current-case-viewer-search-label" style="width: 50px;padding-left: 5px;">
	                                                    Conclusion
	                                            </div>
	                                            <div class="rdr-ka-current-case-viewer-search-input-container" style="width: 750px;">
	                                            	<input type="text" id="rdr_conclusion" class="rdr-input-box rdr-input-width"/>
	                                            </div>
                                       		</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        </div>
    </body>
</html>
