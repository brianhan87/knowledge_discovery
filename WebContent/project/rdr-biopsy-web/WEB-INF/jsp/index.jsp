
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="./styles/rdr-styles.css" rel="stylesheet" />
        <link href="./styles/table.css" rel="stylesheet" />
        
        <link rel="stylesheet" href="./styles/bootstrap-3.3.6.min.css">
        <script type="text/javascript" src="./scripts/jquery-1.10.2.min.js"></script>
        <script type="text/javascript" src="./scripts/jquery-1.12.0.min.js"></script>
        <script type="text/javascript" src="./scripts/bootstrap-3.3.6.min.js"></script>
        <script type="text/javascript" src="./scripts/colResizable-1.5.min.js"></script>
        <script type="text/javascript" src="./scripts/main.js"></script>
        <script type="text/javascript" src="./scripts/ajax.js"></script>
        <script type="text/javascript" src="./scripts/function.js"></script>
        <script type="text/javascript" src="./scripts/event.js"></script>
        <script type="text/javascript" src="./scripts/render.js"></script>
        <script>
            var caseId;
			var morphologyType =  '<%= request.getParameter("morphologyType") %>';
			var features = '<%= request.getParameter("features") %>';
            var conclusionId = '<%= request.getParameter("conclusionId") %>';
            var kaMode = "<%= request.getParameter("kaMode") %>";
            $(function () {
            	// rule tree 화면 이동
            	$("#ruletree_btn").click(function(){
            		$(location).attr('href',"index_ruletree.htm");
            	});
           	});
        </script>
        <title>RDR Web Interface - Knowledge Acquisition</title>
    </head>

    <body>
        <div id="rdr-initial-loading">
            <div class="rdr-loading"></div>
            <div class="rdr-initial-loading-text"> 필요한 정보를 불러오는 중입니다.</div>
        </div>
        <div id="rdr-rule-adding-done" hidden>            
            <div class="rdr-rule-adding-done-text"> 
                새로운 경험지식이 추가되었습니다.
            </div>
        </div>
        <div id="rdr-body">
            <div class="rdr-header">
    <!--            <div class="rdr-sss-logo"></div>
                <div class="rdr-header-banner-text">
                    경험 지식 플랫폼
                </div>-->
            </div>
            <div class="rdr-navigation">
                >> <span id="rdr-ka-mode-title">지식 획득</span>
            </div>
            <div>
                <div class="rdr-popup" draggable="true" hidden>
                    <div class="rdr-popup-label" >
                        POPUP
                    </div>
                    <div class="rdr-popup-contents">
                        <a href="javascript:window.open('','_self').close();">close</a>
                    </div>
                </div>
            </div>
            <div class="rdr-main-contents">
                <!-- KA Main Frame -->
                <div class="rdr-layout rdr-border rdr-ka-main-frame-label">

                </div>
                <div class="rdr-layout rdr-border rdr-ka-main-frame">

                    <!-- Left panel -->
                    <div class="rdr-layout rdr-ka-main-left-panel">
                        <!-- Left Top panel -->
                        <div class="rdr-layout rdr-ka-main-left-top-panel">
                            <div class="rdr-ka-new-condition-section">
                            	<div class="rdr-ka-main-controller-section">
                                    <button id="ruletree_btn" class="rdr-ka-next-button">Rule Tree</button>
                                </div>
                                <div class="rdr-ka-new-condition-section-label rdr-border">
                                    소견지식 조건
                                </div>
                                <div class="rdr-ka-new-condition-section-contents rdr-border">
                                    <div class="rdr-ka-left-new-condition-table-container rdr-table-container rdr-border">
                                        <table class="rdr-ka-new-condition-table rdr-table">
                                            <tr>
                                                <th>Attribute</th>
<!--                                                 <th>Converted Name</th> -->
                                                <th>Operator</th>
                                                <th>Value</th>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Left Bottom panel -->
                        <div class="rdr-layout rdr-ka-main-left-bottom-panel">
                            <div class="rdr-ka-new-conclusion-section">
                                <div class="rdr-ka-new-conclusion-label rdr-ka-new-conclusion-section-label rdr-border">
                                    새로운 소견
                                </div>
                                <div class="rdr-ka-new-conclusion-section-contents rdr-border">
                                    <textarea class="rdr-ka-new-conclusion-textarea" disabled></textarea>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Right panel -->
                    <div class="rdr-layout rdr-ka-main-right-panel">
                        <!-- Tab label -->
                        <div class="rdr-ka-main-tab-label-section">
                            <div class="rdr-border-no-bottom rdr-ka-main-tab-label tab-active" id="rdr-tab-0">
                                새로운 소견 선택
                            </div>
                            <div class="rdr-border-no-bottom rdr-ka-main-tab-label tab-inactive" id="rdr-tab-1">
                                소견지식 조건 선택 
                            </div>
                            <div class="rdr-border-no-bottom rdr-ka-main-tab-label tab-inactive" id="rdr-tab-2">
                                기존지식 사례 검증
                            </div>
                            <div class="rdr-border-no-bottom rdr-ka-main-tab-label tab-inactive" id="rdr-tab-3">
                                소견지식 검토
                            </div>
                        </div>

                        <!-- Tab contents -->
                        <div class="rdr-border-no-left-top rdr-ka-main-tab-section">
                            <div class="rdr-border rdr-ka-main-tab-section-contents">

                                <!-- TAB1 Select conclusion 새로운 소견 선택 tab -->
                                <div class="rdr-ka-main-tab-contents-container" id="rdr-ka-main-tab-contents-0" >
                                    <div class="rdr-ka-main-tab-contents">
                                        <div class="rdr-ka-wrong-conclusion-section" >
                                            <div class="rdr-ka-wrong-conclusion-label">
                                                기존 지식 결론(소견)
                                            </div>
                                            <div class="rdr-ka-wrong-conclusion">
                                                <textarea id="rdr-ka-wrong-conclusion-textarea" disabled ></textarea>
                                            </div>
                                        </div>
                                        <div class="rdr-ka-search-conclusion">
                                            기존 소견 찾기<input placeholder="type keyword" id="rdr-ka-search-conclusion-input-box" class="rdr-input-box rdr-ka-search-conclusion-input-box" type="text">
                                        </div>
                                        <div class="rdr-ka-conclusion-selection-section rdr-border">
                                            <div class="rdr-ka-conclusion-selection rdr-table-container">
                                                <div id="rdr-conclusion-table-loading" class="rdr-loading"></div>
                                                <table id="rdr-conclusion-table" class="rdr-table" hidden>
                                                    <tr>
                                                        <th>소견 리스트</th>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                        <div>
                                            <button id="rdr-ka-add-new-conclusion-button" class="rdr-ka-add-new-conclusion-button">새로운 소견 추가</button>
                                        </div>
                                        <div id="rdr-ka-add-new-conclusion-section" class="rdr-ka-add-new-conclusion-section" hidden>
                                            새로운 소견
                                            <div>
                                                <textarea id="rdr-ka-new-conclusion-input-textarea" class="rdr-ka-new-conclusion-input-textarea"></textarea>
                                            </div>
                                            <div class="rdr-ka-new-conclusion-add-button-container">
                                                <button id="rdr-ka-new-conclusion-add-button" class="rdr-ka-new-conclusion-add-button">추가</button>
                                            </div>
                                            <div class="rdr-ka-new-conclusion-add-button-hr">
                                                <hr class="rdr-hr">
                                            </div>
                                        </div>
                                        <div id="rdr-ka-main-controller-section" class="rdr-ka-main-controller-section">
                                            <button id="rdr-ka-tab-1-next-button" class="rdr-ka-next-button">다음</button>
                                        </div>

                                    </div>
                                </div>

                                <!-- TAB2 Select conditions 소견지식 조건 선택 tab -->
                                <div class="rdr-ka-main-tab-contents-container" id="rdr-ka-main-tab-contents-1" hidden>
                                    <div class="rdr-ka-main-tab-contents">
                                        <div class="rdr-ka-current-case-viewer-section">
                                            <div class="rdr-ka-current-case-viewer-label" >
                                                현재 환자 검사 정보
                                            </div>
                                            <div class="rdr-ka-current-case-viewer-search-section">
                                                <div class="rdr-ka-current-case-viewer-search-label">
                                                    검사 정보 찾기 
                                                </div>
                                                <div class="rdr-ka-current-case-viewer-search-input-container">
                                                    <input type="text" id="rdr-ka-current-case-viewer-search-input" class="rdr-input-box" placeholder="type keyword"/>
                                                </div>
                                                <div class="rdr-ka-current-case-viewer-missing-checkbox-container">
                                                    <input id="rdr-ka-current-case-viewer-missing-checkbox" type="checkbox"/> missing 생략
                                                </div>
                                            </div>
                                            <div class="rdr-ka-current-case-viewer-table-section">
                                                <div class="rdr-ka-current-case-viewer-table-container rdr-border rdr-table-container">
                                                    <div id="rdr-ka-current-case-viewer-table-loading" class="rdr-loading"></div>
                                                    <table id="rdr-ka-current-case-viewer-table" class="rdr-table" hidden>
                                                        <tr>
                                                            <th>Attribute</th>
<!--                                                             <th>Converted Name</th> -->
                                                            <th>Type</th>
                                                            <th>Value</th>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="rdr-ka-used-condition-list-section">
                                            <div class="rdr-ka-used-condition-list-label" >
                                                기존 소견지식에 사용된 조건
                                            </div>
                                            <div class="rdr-ka-used-condition-list-table-container rdr-border rdr-table-container">
                                                <table id="rdr-ka-used-condition-list-table" class="rdr-table">
                                                    <tr>
                                                        <th>Attribute</th>
<!--                                                         <th>Converted Name</th> -->
                                                        <th>Operator</th>
                                                        <th>Value</th>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="rdr-ka-new-condition-list-section">
                                            <div class="rdr-ka-new-condition-list-label" >
                                                새로운 소견지식 조건
                                            </div>
                                            <div class="rdr-ka-new-condition-controller" >
                                                <select class="rdr-ka-new-condition-controller-attr">
                                                </select>
                                                <select class="rdr-ka-new-condition-controller-oper">
                                                </select>
                                                <input class="rdr-ka-new-condition-controller-value" type="text"/>
                                                <button class="rdr-ka-new-condition-controller-add-button" >추가</button>
                                            </div>
                                            <div class="rdr-ka-new-condition-table-container rdr-border rdr-table-container">
                                                <table class="rdr-ka-new-condition-table rdr-table">
                                                    <tr>
                                                        <th>Attribute</th>
<!--                                                         <th>Converted Name</th> -->
                                                        <th>Operator</th>
                                                        <th>Value</th>
                                                    </tr>
                                                </table>
                                            </div>
                                            <div class="rdr-ka-new-condition-bottom-controller-section" >
                                                <button class="rdr-ka-new-condition-controller-delete-button" >삭제</button>
                                            </div>
                                        </div>
                                        <div id="rdr-ka-main-controller-section" class="rdr-ka-main-controller-section">
                                            <button id="rdr-ka-tab-2-next-button" class="rdr-ka-next-button">다음</button>
                                        </div>
                                    </div>
                                </div>

                                <!-- TAB 3 Validate cornerstone cases 기존지식 사례 검증 tab -->
                                <div class="rdr-ka-main-tab-contents-container" id="rdr-ka-main-tab-contents-2" hidden>
                                    <div class="rdr-ka-main-tab-contents">
                                        <div class="rdr-ka-validate-case-list-section">
                                            <div class="rdr-ka-validate-case-list-search-section">
                                                <div class="rdr-ka-validate-case-list-search-label">
                                                    검사 정보 찾기 
                                                </div>
                                                <div class="rdr-ka-validate-case-list-search-input-container">
                                                    <input type="text" id="rdr-ka-validate-case-list-search-input" class="rdr-input-box" placeholder="type keyword"/>
                                                </div>
                                            </div>
                                            <div class="rdr-ka-validate-case-list-table-section">
                                                <div class="rdr-ka-validate-case-list-table-container rdr-border rdr-table-container">
                                                    <div id="rdr-ka-validate-case-list-table-loading" class="rdr-loading"></div>
                                                    <table id="rdr-ka-validate-case-list-table" class="rdr-table" hidden>
                                                        <tr>
                                                            <th>Attribute</th>
<!--                                                             <th>Converted Name</th> -->
                                                            <th>Type</th>
                                                            <th>Value</th>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="rdr-ka-validate-case-list-controller-section">
                                            <div class="rdr-ka-validate-case-list-controller-buttons-container">
                                                <button id="rdr-ka-validate-case-list-controller-button-except">제외</button>
                                                <button id="rdr-ka-validate-case-list-controller-button-accept">수락</button>
                                                <button id="rdr-ka-validate-case-list-controller-button-accept-all">모두 수락</button>
                                            </div>
                                        </div>
                                        <div class="rdr-ka-validate-case-new-conclusion-section">
                                            <div class="rdr-ka-new-conclusion-label">
                                                새로운 소견
                                            </div>
                                            <div class="rdr-ka-validate-case-new-conclusion-textarea-container">
                                                <textarea class="rdr-ka-new-conclusion-textarea" disabled></textarea>
                                            </div>
                                        </div>
                                        <div class="rdr-ka-validate-case-other-conclusion-list-table-section">
                                            <div class="rdr-ka-validate-case-other-conclusion-list-table-label">
                                                다른 소견 찾기<input placeholder="type keyword" id="rdr-ka-search-other-conclusion-input-box" class="rdr-input-box rdr-ka-search-conclusion-input-box" type="text">
                                            </div>
                                            <div class="rdr-ka-validate-case-other-conclusion-list-table-container rdr-border rdr-table-container">
                                                <table id="rdr-ka-validate-case-other-conclusion-list-table" class="rdr-table">
                                                    <tr>
                                                        <th>소견 리스트</th>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- TAB 4 Refine rule conditions 소견지식 검토 tab -->
                                <div class="rdr-ka-main-tab-contents-container" id="rdr-ka-main-tab-contents-3" hidden>
                                    <div class="rdr-ka-main-tab-contents">
                                        <div class="rdr-ka-current-case-viewer-search-section">
                                            <div class="rdr-ka-current-case-viewer-search-label">
                                                검사 정보 찾기 
                                            </div>
                                            <div class="rdr-ka-compare-current-case-viewer-search-input-container">
                                                <input type="text" id="rdr-ka-compare-current-case-viewer-search-input" class="rdr-input-box" placeholder="type keyword"/>
                                            </div>
                                        </div>

                                        <div class="rdr-ka-case-compare-section" >
                                            <div class="rdr-ka-compare-current-case-viewer-table-section">
                                                <div class="rdr-ka-compare-current-case-viewer-table-container rdr-border rdr-table-container">
                                                    <table id="rdr-ka-compare-current-case-viewer-table" class="rdr-table">
                                                        <tr>
                                                            <th>Attribute</th>
<!--                                                             <th>Converted Name</th> -->
                                                            <th>Type</th>
                                                            <th>Value</th>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </div>
                                            <div class="rdr-ka-compare-validate-case-viewer-table-section">
                                                <div class="rdr-ka-compare-validate-case-viewer-table-container rdr-border rdr-table-container">
                                                    <table id="rdr-ka-compare-validate-case-viewer-table" class="rdr-table">
                                                        <tr>
                                                            <th>Attribute</th>
<!--                                                             <th>Converted Name</th> -->
                                                            <th>Type</th>
                                                            <th>Value</th>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="rdr-ka-validate-rule-new-condition-section" >
                                            <div class="rdr-ka-validate-rule-new-condition-controller" >
                                                <select class="rdr-ka-new-condition-controller-attr">
                                                    <option></option>
                                                </select>
                                                <select class="rdr-ka-new-condition-controller-oper">
                                                    <option></option>
                                                </select>
                                                <input class="rdr-ka-new-condition-controller-value" type="text"/>
                                                <button class="rdr-ka-new-condition-controller-add-button" >추가</button>
                                            </div>
                                            <div class="rdr-ka-validate-rule-new-condition-table-container rdr-border rdr-table-container">
                                                <table class="rdr-ka-new-condition-table rdr-table">
                                                    <tr>
                                                        <th>Attribute</th>
<!--                                                         <th>Converted Name</th> -->
                                                        <th>Operator</th>
                                                        <th>Value</th>
                                                    </tr>
                                                </table>
                                            </div>
                                            <div class="rdr-ka-new-condition-bottom-controller-section" >
                                                <button class="rdr-ka-new-condition-controller-delete-button" >삭제</button>
                                            </div>
                                            <div class="rdr-ka-validate-rule-controller" >
                                                <button id="rdr-ka-validate-rule-controller-check-again-button">Check again</button>
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
