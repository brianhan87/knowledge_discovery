package kr.ac.kirc.ekp.experiment;

//import org.json.simple.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.PrintWriter;

import java.net.HttpURLConnection;
import com.google.gson.*;

/**
 * Created by christinebalili on 4/14/16.
 */
public class TestRun {

    /*getTopic is o longer relevant to the task*/
    private static String getTopic(int topicNumber, String form) {

        String query = new String();
        try {
            File inputFile = new File("D:/ekp_result/trec2014.xml");
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("topic");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                //System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE && temp == topicNumber) {
                    Element eElement = (Element) nNode;

                    query = eElement.getElementsByTagName("description").item(0).getTextContent();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return query;
    }

    private static ArrayList<String> getExpansionTerms(String topic,int numTerms) throws Exception{

        ArrayList<String> expansionTerms = new ArrayList<String>();

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection= DriverManager.getConnection("jdbc:mysql://ekp.kaist.ac.kr/kaist_munyi", "admin", "rudgjawltlr1!");
        Statement stmt = connection.createStatement();
        ResultSet results = stmt.executeQuery("SELECT token,score from test_qe where query="+topic+" order by score;");

        while(results.next()){
            expansionTerms.add(results.getString(1));
        }

        return expansionTerms;
    }

    private static ArrayList<String> getResults(String query) throws IOException{
        ArrayList<String> results = new ArrayList<String>();

        query = query.replace(" ","%20");
        String qurl = "http://ekp.kaist.ac.kr/apis/getPapers?q="+query+"&prev=1&next=1000";

        //String encodedURL=java.net.URLEncoder.encode(qurl,"UTF-8");


        // Connect to the URL using java's native library
        URL url = new URL(qurl);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        // Convert to a JSON object to print data
        JsonParser jp = new JsonParser(); //from gson
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        JsonObject result = root.getAsJsonObject(); //May be an array, may be an object.
        JsonArray resultArr = result.getAsJsonArray("journals");


        for(int i=0;i<resultArr.size();i++){

            JsonElement j = resultArr.get(i);
            JsonObject k = j.getAsJsonObject();
            String raw_url = k.get("pmc_url").getAsString();
            String[] holder = raw_url.split("/");
            String pmc_id = holder[5].replaceAll("\\D+","");
            int rank = k.get("journal_rank").getAsInt();

            results.add(pmc_id + " " + rank);
        }
        request.disconnect();
        return results;
    }


    public static void main(String args[])throws Exception{

        //TODO: Change runName, format is "[des/summ][Year][QE]"
        String runName = "2014summ";
        int topicNumber;
        String query;

        //TODO:Change filename accordingly
        BufferedReader br = new BufferedReader(new FileReader("D:/ekp_result/2014inp_summ.txt"));

        //TODO: Change filename of output file accordingly
        PrintWriter writer = new PrintWriter("D:/ekp_result/2014summ_result.txt", "UTF-8");

        try {
            String line = br.readLine();
            topicNumber = Integer.parseInt(line);
            System.out.println(topicNumber);
            query = br.readLine();

            ArrayList<String> results = getResults(query);

            for(int i=0;i<results.size();i++){
                writer.println(topicNumber + "      " + "Q0" + "      " + results.get(i)+ "      " + "0" + "      " + runName);
            }

            while (line != null) {
               line = br.readLine();
               System.out.println(line);
               topicNumber = Integer.parseInt(line);
               query = br.readLine();

               results = getResults(query);

               for(int i=0;i<results.size();i++){
                   writer.println(topicNumber + "      " + "Q0" + "      " + results.get(i)+ "      " + "0" + "      " + runName);
               }
            }


        } finally {
            br.close();
            writer.close();
        }


    }
}
