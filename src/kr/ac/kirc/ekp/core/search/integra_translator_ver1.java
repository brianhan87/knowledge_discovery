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
	// �޼ҵ�ȭ�� ���� main class ����// �׽�Ʈ�� �����Լ� : �Ʒ��� ���� ���·� ȣ���Ͽ� ����ϸ� ����Ʈ ���� ������
		public static void main(String[] args) {
			
			String diagnosis="�ƹж����(Amylase)�� ���� ���ľ���(Lipase)�� ���� �ʽ��ϴ�. ħ��, ����, ��, ��ũ�ξƹж��� Ȥ�� �幰�� ������ ��ȯ�� ������ �ֽ��ϴ�.";
			 
			ArrayList<String> expendedlist = new ArrayList<String>();
			expendedlist = getData(diagnosis);
			
			for (int r=0; r<expendedlist.size(); r++) {
				System.out.print(expendedlist.get(r)+", ");
			}
			
		}
		
	
	//sql ������ ���� �Լ�
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		String url= "jdbc:mysql://ekp.kaist.ac.kr:3306/ekp_db_v2";
	    String user = "admin";
		String pass= "rudgjawltlr1!";
		
		Connection conn=null;
		
		Class.forName("org.gjt.mm.mysql.Driver");
		conn=DriverManager.getConnection(url,user,pass);
		//System.out.println("*******���ӿϷ�***********");
		
		return conn;
	}
	
	// ���� �Է� ��
	public static String navertranslator(String query) {
		
		String clientId = "9OCPynC2P3XqfptdPqhl";//���ø����̼� Ŭ���̾�Ʈ ���̵�";
        String clientSecret = "YCwSQsHmlC";//���ø����̼� Ŭ���̾�Ʈ ��ũ����";
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
            if(responseCode==200) { // ���� ȣ��
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // ���� �߻�
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            //System.out.println(response.toString());
            
            //���� ������
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
            
            //System.out.println("��������� : "+ query);
            
            return realresult;
        } catch (Exception e) {
            System.out.println(e);
        }
		return null;			
	}
	
	
	
	
//���̹� ��� ���� ��Ʈ	
	
	public static ArrayList<String> getData(String targettext) {
       
        try {
        	//String targettext = "������Ư���׿�(PSA)�� ���������, �������� ������ �ֽ��ϴ�.";
        	//String targettext = "�ƹж����(Amylase)�� ���� ���ľ���(Lipase)�� ���� �ʽ��ϴ�. ħ��, ����, ��, ��ũ�ξƹж��� Ȥ�� �幰�� ������ ��ȯ�� ������ �ֽ��ϴ�.";
        	
        	
        	//System.out.println("*************��������� ������*************");
            Connection conn = getConnection();
    		
    		StringBuilder sb = new StringBuilder();
    		sb=new StringBuilder("");
    		
    		String sql0 = sb.append("SELECT * FROM ekp_db_v2.conclusions_en where conclusion_kr='"+targettext +"';").toString();
        	
    		ResultSet res0=null;
    		Statement stmt0=null;
    		
    		stmt0 = conn.createStatement();    		 		
    		res0 = stmt0.executeQuery(sql0);
    		
    		
    		String response ="";
    		
    		if(res0.next()){ //�˻������ ���� �ƴ϶��
    			//System.out.println("�˻��� : "+ targettext);
    			//System.out.println("������� ����:"+ res0.getString(4));
    			
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
    			//System.out.println("����� �����ϴ�. �������� �����մϴ�.");
    			
    			//���̹� ���� ����
    			response = navertranslator(targettext).toString();           
                //System.out.println("������� : "+response);
                
                //������� DB�� ����
                try {
                	
                	//Pkey�� ��������
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
            		
            		//System.out.println("------DB�� ������ �Է��Ͽ����ϴ�.-----");
            		
                	
                	
                	
                }catch(Exception e) {
                	e.printStackTrace();

                	System.out.println("member ���̺� ���ο� ���ڵ� �߰��� �����߽��ϴ�.");

                	
                }
                
                // ������� DB�� ����
    		}
    		
        	
        	//������ DB���� �˻��� ������-> �� ��������
        	
        	//������� Ȯ��
            //System.out.println(response.toString());
        	
            
            //���� ��� ����
            String realresult= response;
            realresult =realresult.replace("(", "");
            realresult =realresult.replace(")", "");
            realresult =realresult.replace(".", "");
                        
            //�ܾ� ������ �˻� �ǽ�
            String[] inputlist1 = realresult.split(" ");
            String[] inputlist2 = new String[inputlist1.length-1];
            
            //System.out.print(inputlist1.length-1);
            //System.out.println("���� ���ڿ��� ���� �˻��� �ǽ��մϴ�.");
            
            //2�ܾ�� ������ ����Ʈ ����
            for(int i=0; i<inputlist1.length-1;i++ )
            {
            	inputlist2[i]=inputlist1[i].toString() +" "+inputlist1[i+1].toString();
            	//System.out.println(inputlist2[i]);
            }
            
            ///2�ܾ� ����Ʈ�� 'in the'��� ���ڿ��� ���� ���ִ� �ɷ�
           
            for(int p=0; p<inputlist2.length;p++) {
            	String check= inputlist2[p].toString();
            	           	
            	if(check.equalsIgnoreCase("in the")){
            		inputlist2[p]= inputlist2[p].replace(inputlist2[p], "---@��#!��@$��#---");
            		}          	  
            	            	    	
            }
            
           //���� ��� �ƿ�ǫ�� ���� ArrayList ����
            ArrayList<String> expendedlist = new ArrayList<String>();
            
            
            //2�ܾ ���� �˻� �κ�    
    		for(int i=0;i<inputlist2.length;i++) {//���߿� �ݺ������� ���� �ʿ�
    		String query = inputlist2[i];
    		sb=new StringBuilder("");  
    		
    		String sql = sb.append("SELECT * FROM ekp_db_v2.word_dictionary_v2 where Word like '%"+query +"%';").toString();
    		//System.out.println(sql);//�˻��� Ȯ�ο�
    		
    		
    		ResultSet res=null;
    		Statement stmt=null;
    		
    		stmt = conn.createStatement();
    		 		
    		res = stmt.executeQuery(sql);
    				
    		
    		if(res.next()){ //�˻������ ���� �ƴ϶��
    			
    			//System.out.println("�˻��� : "+ query);    			//�˻��� Ȯ�ο�
    			//System.out.println("�˻��� code:"+ res.getString(2));        	//�˻��� �ڵ� Ȯ�ο�	
        		String qcode = res.getString(2);        		
        		StringBuilder qsb = new StringBuilder();        		
        		String qsql = qsb.append("select Word from ekp_db_v2.word_dictionary_v2 where Code='"+qcode+"' and lang = 'EN';").toString();
        		        			
        		ResultSet qres=null;
        		Statement qstmt=null;
        		
        		qstmt = conn.createStatement();
        		qres = qstmt.executeQuery(qsql);
        		
        		//��� ������
        		while(qres.next()) {
        			String code =  qres.getString(1);        			
        			//System.out.print(code+", ");
        			expendedlist.add(code);
        												
        		}
        		//System.out.println("");
        		//System.out.println("--------------------------------- ");
        		//���� ��� ���ڿ� üũ��
        		
        		//System.out.println("���ø� ���");
        		//System.out.println(query);
        		
        		String[] delwords = query.split(" ");
        		
                ///for(int i=0;i<delwords.length;i++) {        			
        			///System.out.println(delwords[i]);
        		///}
        		
        		//2�ܾ� ������ ���ڿ��� �����ϴ� �ܾ� ����
        		for(int k=0;k<2;k++) {
        			String deltar = delwords[k].toString();
        			///System.out.println("�����۵�");
        			for(int j=0; j<inputlist1.length ;j++) {
        				//System.out.println("�񱳴��");
        				//System.out.print(deltar);
        				//System.out.print(" VS ");
        				//System.out.println(inputlist1[j]);
        				if(inputlist1[j].equalsIgnoreCase(deltar)) {
        					///System.out.println("�񱳽���");
        					 inputlist1[j]= inputlist1[j].replace(inputlist1[j], "");
        					 //System.out.print(deltar);
        					 //System.out.println("��(��) ���ŵ�");
        						
        						
        					}
        					
        					
        					
        				}
        			}
        				
        		// ����Ʈ ���� Ȯ�ο�
        		///System.out.println("���� �ܾ� ����Ʈ üũ");
        		///for(int i=0;i<inputlist1.length;i++) {
        			
        		///	System.out.println(inputlist1[i]);
        		///}
        		
        		if(res != null) res.close();
        		if(stmt != null) stmt.close();
        		if(qres != null) qres.close();
        		if(qstmt != null) qstmt.close();
        		
        		//if(conn != null) conn.close();/// ���߿� ��ġ ���� �ʿ� �Ұ� ������ ���� �ʿ� ����
        		
    		}else {
    			//System.out.print("�˻������ �����ϴ�");
    		}
    		
    		}
    		//���� �ܾ ���� �˻� Ȯ��(exact ��Ī ���)
    		
    		for(int z=0;z<inputlist1.length;z++) {
    		
    		String query2 = inputlist1[z];
    		
    		//System.out.println("�˻��� : "+ query2);// �˻��� Ȯ�ο�
    		sb=new StringBuilder("");
    		
    		String sql2 = sb.append("SELECT * FROM ekp_db_v2.word_dictionary_v2 where Word ='"+query2 +"';").toString();
    		//System.out.println(sql2);//�˻��� Ȯ�ο�
    		    		
    		ResultSet res2=null;
    		Statement stmt2=null;
    		
    		stmt2 = conn.createStatement();    		 		
    		res2 = stmt2.executeQuery(sql2);
    				
    		
    		if(res2.next()){ //�˻������ ���� �ƴ϶��
    			//System.out.println("�˻��� : "+ query2);
    			//System.out.println("�˻��� code:"+ res2.getString(2));
        		
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
    			//System.out.print(query2+"�� ����");
    			//System.out.println("�˻������ �����ϴ�");
    		}
    		}
    		
    		//������ �迭 ����
    		//���� �ٸ� ��Ʈ �κа� �����Ͽ� ������ ���� ����
    		/*
    		System.out.println("");
    		System.out.println("=============================================================================");
    		System.out.print("Ȯ��� �˻��� ����Ʈ: ");
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
