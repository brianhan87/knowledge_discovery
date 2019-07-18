package kr.ac.kirc.ekp.restful;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;


public class BioPortalAccessor {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BioPortalAccessor access = new BioPortalAccessor();
		String query = "AST is normal";
		String output = access.getAnnotations(query);
		ArrayList<String> nouns = access.JsonParserforAnnotation(output);
		
		
		for (int i=0;i<nouns.size();i++)
		{
			System.out.println(nouns.toString());
			System.out.println("Processing for " + nouns.get(i));
			String output2 = access.getSemanticsTypes(nouns.get(i));
			ArrayList<String> semantic_types = access.JsonParserforSemanticTypes(output2);
			ArrayList<String> synonyms = access.JsonParserforSynonyms(output2);
			
			for (int j=0;j<semantic_types.size();j++)
			{
				System.out.println(semantic_types.get(j));
			}
			System.out.println("...");
			System.out.println(nouns.get(i) + " has following synonyms: ");
			for (int j=0;j<synonyms.size();j++)
			{
				System.out.println(synonyms.get(j));
			}
			System.out.println("============================");
		}		
	}
	public ArrayList<String> getAvailableUMLSCodes(String path)
	{
		ArrayList<String> available_umls_codes = new ArrayList<String>();
		try {
            File f = new File(path);
            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";
            while ((readLine = b.readLine()) != null) {
            	String[] parts = readLine.split("\\|");
            	available_umls_codes.add(parts[2]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
		return available_umls_codes;
	}	
	public ArrayList<String> getNouns(String query)
	{
		String output = this.getAnnotations(query);
		ArrayList<String> nouns = this.JsonParserforAnnotation(output);
		return nouns;
	}
	public String getXMLforNOUN(String noun)
	{
		return this.getSemanticsTypes(noun);
	}
	public ArrayList<String> getUMLSTypes(String xml_for_noun)
	{
		ArrayList<String> semantic_types = this.JsonParserforSemanticTypes(xml_for_noun);
		return semantic_types;
	}
	public ArrayList<String> getSynonyms(String xml_for_noun)
	{
		//String output = this.getSemanticsTypes(noun);
		ArrayList<String> synonyms = this.JsonParserforSynonyms(xml_for_noun);
		return synonyms;
	}
	public String getSemanticsTypes(String query) 
	{
		String url = "http://data.bioontology.org/search?require_exact_match=true&apikey=021abed1-697b-43d2-a9d8-9dba2dc89524";
		String url_to_request = "";
		
		try {
			url_to_request = url+"&q="+URLEncoder.encode(query,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		String output = "";
		try {

			Client client = Client.create();

			WebResource webResource = client
			   .resource(url_to_request);

			ClientResponse response = webResource.accept("application/json")
	                   .get(ClientResponse.class);

			if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}

			output = response.getEntity(String.class);

			//System.out.println("Output from Server .... \n");
			//System.out.println(output);
			return output;

		  } catch (Exception e) {

			e.printStackTrace();

		  }
		return output;
	}
	public ArrayList<String> JsonParserforSemanticTypes(String output)
	{
		//System.out.println("IT BEGINS");
		ArrayList<String> semantic_types = new ArrayList<String>();
		try
		{
			JSONParser parser = new JSONParser();
			JSONObject term_object = (JSONObject) parser.parse(output);
			JSONArray jsonCollection = (JSONArray) term_object.get("collection");
			for (int i=0;i<jsonCollection.size();i++)
			{
				JSONObject json_object = (JSONObject) jsonCollection.get(i);
				JSONArray semanticTypes = new JSONArray();
				//System.out.println(semanticTypes.toString());
				semanticTypes = (JSONArray) json_object.get("semanticType");
				//System.out.println("NOW LOOK" + jsonCollection.get(i).toString());
				
				if(semanticTypes != null)
				{
					//System.out.println("ALEART: " + semanticTypes.toString());
					//System.out.println("JSON_OBJECT" + json_object.toString());
					//System.out.println(semanticTypes.toString());
					
					//System.out.println("Semantic Type Size: " + semanticTypes.size());
					for (int j=0;j<semanticTypes.size();j++)
					{
						semantic_types.add((String) semanticTypes.get(j));
					}
				}
			}
			//System.out.println(semantic_types.toString());
			HashSet hs = new HashSet(semantic_types);
			semantic_types = new ArrayList<String>(hs);
		//	System.out.println(semantic_types.toString());
			//System.out.println(jsonCollection.toString());
			
		}
		catch (org.json.simple.parser.ParseException e)
		{
			e.printStackTrace();
		}
		return semantic_types;
	}
	public ArrayList<String> JsonParserforSynonyms(String output)
	{
		//System.out.println("IT BEGINS");
		ArrayList<String> semantic_types = new ArrayList<String>();
		try
		{
			JSONParser parser = new JSONParser();
			JSONObject term_object = (JSONObject) parser.parse(output);
			JSONArray jsonCollection = (JSONArray) term_object.get("collection");
			for (int i=0;i<jsonCollection.size();i++)
			{
				JSONObject json_object = (JSONObject) jsonCollection.get(i);
				JSONArray semanticTypes = new JSONArray();
				//System.out.println(semanticTypes.toString());
				semanticTypes = (JSONArray) json_object.get("synonym");
				//System.out.println("NOW LOOK" + jsonCollection.get(i).toString());
				
				if(semanticTypes != null)
				{
					//System.out.println("ALEART: " + semanticTypes.toString());
					//System.out.println("JSON_OBJECT" + json_object.toString());
					//System.out.println(semanticTypes.toString());
					
					//System.out.println("Semantic Type Size: " + semanticTypes.size());
					for (int j=0;j<semanticTypes.size();j++)
					{
						semantic_types.add((String) semanticTypes.get(j));
					}
				}
			}
			//System.out.println(semantic_types.toString());
			HashSet hs = new HashSet(semantic_types);
			semantic_types = new ArrayList<String>(hs);
		//	System.out.println(semantic_types.toString());
			//System.out.println(jsonCollection.toString());
			
		}
		catch (org.json.simple.parser.ParseException e)
		{
			e.printStackTrace();
		}
		return semantic_types;
	}
	
	
	public String getAnnotations(String query) 
	{
		String url = "http://data.bioontology.org/annotator?longest_only=true&apikey=021abed1-697b-43d2-a9d8-9dba2dc89524";
		String url_to_request = "";
		
		try {
			url_to_request = url+"&text="+URLEncoder.encode(query,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		//System.out.println(url_to_request);
		String output = "";
		try {

			Client client = Client.create();

			WebResource webResource = client
			   .resource(url_to_request);

			ClientResponse response = webResource.accept("application/json")
	                   .get(ClientResponse.class);

			if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}

			output = response.getEntity(String.class);

			//System.out.println("Output from Server .... \n");
			//System.out.println(output);
			return output;

		  } catch (Exception e) {

			e.printStackTrace();

		  }
		return output;
	}
	
	public ArrayList<String> JsonParserforAnnotation(String output)
	{
		ArrayList<String> nouns = new ArrayList<String>();
		try
		{
			JSONParser parser = new JSONParser();
			JSONArray jsonArray = (JSONArray) parser.parse(output);
			//System.out.println(jsonArray.size());
	
			
			for (int i=0;i<jsonArray.size();i++)
			{
				JSONObject annotation_object = (JSONObject) jsonArray.get(i);
				//System.out.println("=annotated_class"+i+" ===================== ");
				//System.out.println(annotation_object.toString());
				//System.out.println("annotated_class: " + annotation_object.get("id"));
				JSONArray annotations = (JSONArray) annotation_object.get("annotations");
				JSONObject item = (JSONObject) annotations.get(0);
				String text = (String) item.get("text");
				//System.out.println(annotations.toString());
				//System.out.println(item.toString());
				//System.out.println(text);
				nouns.add(text);
				
			}
			//System.out.println(nouns.toString());
			HashSet hs = new HashSet(nouns);
			nouns = new ArrayList<String>(hs);
			//System.out.println(nouns.toString());
			
			
		}
		catch (org.json.simple.parser.ParseException e)
		{
			e.printStackTrace();
		}
		return nouns;
	}
	

}
