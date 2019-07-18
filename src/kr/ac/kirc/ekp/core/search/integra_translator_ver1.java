package kr.ac.kirc.ekp.core.search;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class integra_translator_ver1 {	
	// 메소드화를 위한 main class 생성// 테스트용 메인함수 : 아래와 같은 형태로 호출하여 사용하면 리스트 값을 리턴함
		public static void main(String[] args) {
			
			String diagnosis="아밀라아제(Amylase)가 높고 리파아제(Lipase)가 높지 않습니다. 침샘, 소장, 간, 마크로아밀라제 혹은 드물게 췌장의 질환과 관련이 있습니다.";
			 
			ArrayList<String> expendedlist = new ArrayList<String>();
			expendedlist = getData(diagnosis);
			
			for (int r=0; r<expendedlist.size(); r++) {
				System.out.print(expendedlist.get(r)+", ");
			}
			
		}
		
	
	//sql 연결을 위한 함수
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		String url= "jdbc:mysql://ekp.kaist.ac.kr:3306/ekp_db_v2";
	    String user = "admin";
		String pass= "rudgjawltlr1!";
		
		Connection conn=null;
		
		Class.forName("org.gjt.mm.mysql.Driver");
		conn=DriverManager.getConnection(url,user,pass);
		//System.out.println("*******접속완료***********");
		
		return conn;
	}
	
	// 문장 입력 부
	public static String navertranslator(String query) {
		
		String clientId = "9OCPynC2P3XqfptdPqhl";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "YCwSQsHmlC";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(query, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/language/translate";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source=ko&target=en&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            //System.out.println(response.toString());
            
            //문장 나누기
            String resultstr = response.toString();
            String[] words = resultstr.split("translatedText");
            //System.out.println(words[1]);
            
            String stringresult = words[1].toString();
            
            stringresult =stringresult.replace("\"", "");
            //System.out.println(stringresult);
            String[] words2 = stringresult.split(":");
            String realresult = words2[1].toString();
            //System.out.println(realresult);
            realresult =realresult.replace("srcLangType", "");
            realresult =realresult.replace(",", "");
            realresult =realresult.replace(".", "");
            
            //System.out.println("웹번역대상 : "+ query);
            
            return realresult;
        } catch (Exception e) {
            System.out.println(e);
        }
		return null;			
	}
	
	
	
	
//네이버 기반 번역 파트	
	
	public static ArrayList<String> getData(String targettext) {
       
        try {
        	//String targettext = "전립선특이항원(PSA)가 정상아지만, 전립선에 문제가 있습니다.";
        	//String targettext = "아밀라아제(Amylase)가 높고 리파아제(Lipase)가 높지 않습니다. 침샘, 소장, 간, 마크로아밀라제 혹은 드물게 췌장의 질환과 관련이 있습니다.";
        	
        	
        	//System.out.println("*************온톨로지에 접속중*************");
            Connection conn = getConnection();
    		
    		StringBuilder sb = new StringBuilder();
    		sb=new StringBuilder("");
    		
    		String sql0 = sb.append("SELECT * FROM ekp_db_v2.conclusions_en where conclusion_kr='"+targettext +"';").toString();
        	
    		ResultSet res0=null;
    		Statement stmt0=null;
    		
    		stmt0 = conn.createStatement();    		 		
    		res0 = stmt0.executeQuery(sql0);
    		
    		
    		String response ="";
    		
    		if(res0.next()){ //검색결과가 널이 아니라면
    			//System.out.println("검색어 : "+ targettext);
    			//System.out.println("온톨로지 번역:"+ res0.getString(4));
    			
    			response = res0.getString(4);        		
        		        			
        		ResultSet qres0=null;
        		Statement qstmt0=null;
        		
        		//System.out.println("--------------------------------- ");
        		//System.out.println("");
    		
        		if(res0 != null) res0.close();
        		if(stmt0 != null) stmt0.close();
        		if(qres0 != null) qres0.close();
        		if(qstmt0 != null) qstmt0.close();
        		//if(conn != null) conn.close();
    		
    		}else {
    			//System.out.println("결과가 없습니다. 웹번역을 실행합니다.");
    			
    			//네이버 번역 실행
    			response = navertranslator(targettext).toString();           
                //System.out.println("번역결과 : "+response);
                
                //번역결과 DB에 저장
                try {
                	
                	//Pkey값 가져오기
                	sb=new StringBuilder("");
                	String sql98 =sb.append("select count(*) from `ekp_db_v2`.`conclusions_en`").toString();
                	ResultSet res98=null;
                	Statement stmt98=null;
            		
            		stmt98 = conn.createStatement();    		 		
            		res98 = stmt98.executeQuery(sql98);
            		int rowcount = 0;
            		if(res98.next()) {
            			rowcount=res98.getInt(1);
            			
            		}
            		
            		//System.out.println(rowcount);
            		
            		if(res98 != null) res98.close();
            		if(stmt98 != null) stmt98.close();
                	
                	int pKey= rowcount+1;
                	
                	
                	sb=new StringBuilder("");
                	String sql99=sb.append("INSERT INTO `ekp_db_v2`.`conclusions_en` (`con_pid`, `conclusion_kr`, `tran_conclu`) VALUES ('"+pKey+"','"+targettext+"','"+response+"');").toString();
                	//String sql99 =sb.append("INSERT INTO `ekp_db_v2`.`conclusions_en` (`conclusion_kr`, `tran_conclu`) VALUES ("+targettext+","+response+");").toString();
                	
                	ResultSet res99=null;
                	Statement stmt99=null;
            		
                	 //System.out.println(sql99.toString());
            		stmt99 = conn.createStatement();    		 		
            		stmt99.executeUpdate(sql99);
            		
            		if(res99 != null) res99.close();
            		if(stmt99 != null) stmt99.close();
            		
            		//System.out.println("------DB에 번역을 입력하였습니다.-----");
            		
                	
                	
                	
                }catch(Exception e) {
                	e.printStackTrace();

                	System.out.println("member 테이블에 새로운 레코드 추가에 실패했습니다.");

                	
                }
                
                // 번역결과 DB에 저장
    		}
    		
        	
        	//번역문 DB에서 검색후 없으면-> 웹 번역으로
        	
        	//번역결과 확인
            //System.out.println(response.toString());
        	
            
            //번역 결과 정리
            String realresult= response;
            realresult =realresult.replace("(", "");
            realresult =realresult.replace(")", "");
            realresult =realresult.replace(".", "");
                        
            //단어 형태의 검색 실시
            String[] inputlist1 = realresult.split(" ");
            String[] inputlist2 = new String[inputlist1.length-1];
            
            //System.out.print(inputlist1.length-1);
            //System.out.println("개의 문자열에 대해 검색을 실시합니다.");
            
            //2단어로 구성된 리스트 생성
            for(int i=0; i<inputlist1.length-1;i++ )
            {
            	inputlist2[i]=inputlist1[i].toString() +" "+inputlist1[i+1].toString();
            	//System.out.println(inputlist2[i]);
            }
            
            ///2단어 리스트중 'in the'라는 문자열은 제거 해주는 걸로
           
            for(int p=0; p<inputlist2.length;p++) {
            	String check= inputlist2[p].toString();
            	           	
            	if(check.equalsIgnoreCase("in the")){
            		inputlist2[p]= inputlist2[p].replace(inputlist2[p], "---@노#!이@$즈#---");
            		}          	  
            	            	    	
            }
            
           //최종 결과 아웃푹을 위한 ArrayList 생성
            ArrayList<String> expendedlist = new ArrayList<String>();
            
            
            //2단어에 대한 검색 부분    
    		for(int i=0;i<inputlist2.length;i++) {//나중에 반복문으로 변경 필요
    		String query = inputlist2[i];
    		sb=new StringBuilder("");  
    		
    		String sql = sb.append("SELECT * FROM ekp_db_v2.word_dictionary_v2 where Word like '%"+query +"%';").toString();
    		//System.out.println(sql);//검색식 확인용
    		
    		
    		ResultSet res=null;
    		Statement stmt=null;
    		
    		stmt = conn.createStatement();
    		 		
    		res = stmt.executeQuery(sql);
    				
    		
    		if(res.next()){ //검색결과가 널이 아니라면
    			
    			//System.out.println("검색어 : "+ query);    			//검색어 확인용
    			//System.out.println("검색된 code:"+ res.getString(2));        	//검색된 코드 확인용	
        		String qcode = res.getString(2);        		
        		StringBuilder qsb = new StringBuilder();        		
        		String qsql = qsb.append("select Word from ekp_db_v2.word_dictionary_v2 where Code='"+qcode+"' and lang = 'EN';").toString();
        		        			
        		ResultSet qres=null;
        		Statement qstmt=null;
        		
        		qstmt = conn.createStatement();
        		qres = qstmt.executeQuery(qsql);
        		
        		//결과 프린팅
        		while(qres.next()) {
        			String code =  qres.getString(1);        			
        			//System.out.print(code+", ");
        			expendedlist.add(code);
        												
        		}
        		//System.out.println("");
        		//System.out.println("--------------------------------- ");
        		//삭제 대상 문자열 체크용
        		
        		//System.out.println("스플릿 대상");
        		//System.out.println(query);
        		
        		String[] delwords = query.split(" ");
        		
                ///for(int i=0;i<delwords.length;i++) {        			
        			///System.out.println(delwords[i]);
        		///}
        		
        		//2단어 구성된 문자열을 구성하는 단어 제거
        		for(int k=0;k<2;k++) {
        			String deltar = delwords[k].toString();
        			///System.out.println("정상작동");
        			for(int j=0; j<inputlist1.length ;j++) {
        				//System.out.println("비교대상");
        				//System.out.print(deltar);
        				//System.out.print(" VS ");
        				//System.out.println(inputlist1[j]);
        				if(inputlist1[j].equalsIgnoreCase(deltar)) {
        					///System.out.println("비교시작");
        					 inputlist1[j]= inputlist1[j].replace(inputlist1[j], "");
        					 //System.out.print(deltar);
        					 //System.out.println("이(가) 제거됨");
        						
        						
        					}
        					
        					
        					
        				}
        			}
        				
        		// 리스트 제거 확인용
        		///System.out.println("단일 단어 리스트 체크");
        		///for(int i=0;i<inputlist1.length;i++) {
        			
        		///	System.out.println(inputlist1[i]);
        		///}
        		
        		if(res != null) res.close();
        		if(stmt != null) stmt.close();
        		if(qres != null) qres.close();
        		if(qstmt != null) qstmt.close();
        		
        		//if(conn != null) conn.close();/// 나중에 위치 변경 필요 할것 접속을 끊을 필요 없음
        		
    		}else {
    			//System.out.print("검색결과가 없습니다");
    		}
    		
    		}
    		//단일 단어에 대한 검색 확장(exact 메칭 사용)
    		
    		for(int z=0;z<inputlist1.length;z++) {
    		
    		String query2 = inputlist1[z];
    		
    		//System.out.println("검색어 : "+ query2);// 검색어 확인용
    		sb=new StringBuilder("");
    		
    		String sql2 = sb.append("SELECT * FROM ekp_db_v2.word_dictionary_v2 where Word ='"+query2 +"';").toString();
    		//System.out.println(sql2);//검색식 확인용
    		    		
    		ResultSet res2=null;
    		Statement stmt2=null;
    		
    		stmt2 = conn.createStatement();    		 		
    		res2 = stmt2.executeQuery(sql2);
    				
    		
    		if(res2.next()){ //검색결과가 널이 아니라면
    			//System.out.println("검색어 : "+ query2);
    			//System.out.println("검색된 code:"+ res2.getString(2));
        		
        		String qcode2 = res2.getString(2);        		
        		StringBuilder qsb2= new StringBuilder();
        		
        		String qsql2 = qsb2.append("select Word from ekp_db_v2.word_dictionary_v2 where Code='"+qcode2+"' and lang = 'EN';").toString();
        		        			
        		ResultSet qres2=null;
        		Statement qstmt2=null;
        		
        		qstmt2 = conn.createStatement();
        		qres2 = qstmt2.executeQuery(qsql2);
        		
        		
        		while(qres2.next()) {        			
        			String code =  qres2.getString(1);        			
        			//System.out.print(code+", ");
        			expendedlist.add(code);
        		}
        		
        		//System.out.println("");
        		//System.out.println("--------------------------------- ");
    		
        		if(res2 != null) res2.close();
        		if(stmt2 != null) stmt2.close();
        		if(qres2 != null) qres2.close();
        		if(qstmt2 != null) qstmt2.close();
        		//if(conn != null) conn.close();
    		
    		}else {
    			//System.out.print(query2+"에 대한");
    			//System.out.println("검색결과가 없습니다");
    		}
    		}
    		
    		//생성된 배열 리턴
    		//추후 다른 파트 부분과 연계하여 리턴의 형태 정리
    		/*
    		System.out.println("");
    		System.out.println("=============================================================================");
    		System.out.print("확장된 검색어 리스트: ");
    		for (int r=0; r<expendedlist.size(); r++) {
    			System.out.print(expendedlist.get(r)+", ");
    		}
    		System.out.println("");
    		System.out.println("=============================================================================");
    		 */
    		return expendedlist;
  
        } catch (Exception e) {
            System.out.println(e);
        }
		return null;
     
    }
	
	 
}
