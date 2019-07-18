package kr.ac.kirc.ekp.config;

public class Path {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*
	 * 
	 *  Please change the paths below according to new server environment. 
	 *  
	 *  2016_10_21, commented by Keejun Han
	 */
	final String KSLAB_DB_ADDRESS = "jdbc:mysql://kirc.kaist.ac.kr:3306/pmcc";
	final String KSLAB_JOURNAL_DB_ADDRESS = "jdbc:mysql://kirc.kaist.ac.kr:3306/journal";
	final String ONTOLOGY_DB_ADDRESS = "jdbc:mysql://kirc.kaist.ac.kr:3306/ekp_db";
	
	final String KSLAB_DB_USER = "root";
	final String KSLAB_DB_PW = "kslab!1204";
	
	final String EKP_HOJIN_DB_ADDRESS = "jdbc:mysql://ekp.kaist.ac.kr:3306/kaist_hojinchoi";
	final String EKP_MUNYI_DB_ADDRESS = "jdbc:mysql://ekp.kaist.ac.kr:3306/kaist_munyi";
		
	final String EKP_DB_USER = "admin";
	final String EKP_DB_PW = "rudgjawltlr1!";
	
	final String journal_path = "D:/ekp_final_index/";
	//final String journal_path = "C:/index/";
	final String book_path = "C:/index/";
	final String nlp_path = "D:/ekp_final_index/";
	//final String nlp_path = "C:/index/";
	final String graph_data_path = "C:/ekp/workspace/kirc_ekp/WebContent/graph/data/";

	final String komoran_path = nlp_path + "models-full/";
	final String para_book1_index_path =  book_path +"para_book1_index/";
	//final String para_book2_index_path = "C:/ekp/workspace/kirc_ekp/data/para_book2_index/";
	final String para_book2_index_path = book_path +"para_book2_index/";
	final String para_book3_index_path = book_path +"para_book3_index/";
	//final String para_book3_index_path = "C:/ekp/workspace/kirc_ekp/data/para_book3_index/";
	final String journal_index_path = journal_path +"ekp_journal_index";
	final String journal_index_path1=journal_path +"ekp_journal_index1";
	final String journal_index_path2=journal_path +"ekp_journal_index2";
	
	public String getKSLAB_DB_ADDRESS() {
		return KSLAB_DB_ADDRESS;
	}

	public String getKSLAB_JOURNAL_DB_ADDRESS() {
		return KSLAB_JOURNAL_DB_ADDRESS;
	}

	public String getONTOLOGY_DB_ADDRESS() {
		return ONTOLOGY_DB_ADDRESS;
	}

	public String getKSLAB_DB_USER() {
		return KSLAB_DB_USER;
	}

	public String getKSLAB_DB_PW() {
		return KSLAB_DB_PW;
	}

	public String getEKP_HOJIN_DB_ADDRESS() {
		return EKP_HOJIN_DB_ADDRESS;
	}

	public String getEKP_MUNYI_DB_ADDRESS() {
		return EKP_MUNYI_DB_ADDRESS;
	}

	public String getEKP_DB_USER() {
		return EKP_DB_USER;
	}

	public String getEKP_DB_PW() {
		return EKP_DB_PW;
	}

	public String getJournal_index_path1() {
		return journal_index_path1;
	}
					
	public String getJournal_index_path2() {
		return journal_index_path2;
	}




	public String getKomoran_path() {
		return komoran_path;
	}

	public String getJournal_index_path() {
		return journal_index_path;
	}

	public String getPara_book2_index_path() {
		return para_book2_index_path;
	}

	public String getGraph_data_path() {
		return graph_data_path;
	}

	public String getPara_book1_index_path() {
		return para_book1_index_path;
	}


	public String getPara_book3_index_path() {
		return para_book3_index_path;
	}
	public String getPara_book_index_path(int book_id)
	{
		String path = "";
		if (book_id ==1)
		{
			path = para_book1_index_path;
		}
		else if(book_id ==2)
		{
			path = para_book2_index_path;
		}
		else if(book_id ==3)
		{
			path = para_book3_index_path;
		}
		return path;
	}
	
}
