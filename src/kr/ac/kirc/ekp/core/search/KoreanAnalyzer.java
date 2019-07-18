package kr.ac.kirc.ekp.core.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import org.snu.ids.ha.index.Keyword;
import org.snu.ids.ha.index.KeywordExtractor;
import org.snu.ids.ha.index.KeywordList;
import org.snu.ids.ha.ma.MorphemeAnalyzer;
import org.snu.ids.ha.ma.Sentence;

import kr.ac.kirc.ekp.config.Path;
import kr.co.shineware.nlp.komoran.core.analyzer.Komoran;
import kr.co.shineware.util.common.model.Pair;





public class KoreanAnalyzer {

	public static void main(String[] args) throws Exception {
		
		KoreanAnalyzer ka = new KoreanAnalyzer();
		//ka.run();
		ka.kkomarun();
		
	}
	public void run()
	{
		Komoran ko = this.getAnalyzer();
		String s = "광배근을 발달시키기 위한 대표적인 운동이다. 턱걸이를 하기 어려운 초보자들에게 좋은 운동이다. 팔만 이용하여 바(bar)를 잡아당기면 이두근의 참여도가 지나치게 높아지므로 동작을 시작하는 시점에서 견갑골(날개뼈)을 우선적으로 내리도록 한다. ";
		System.out.println(this.getNouns(s, ko).toString());
	}
	public void kkomarun() throws Exception
	{
		// string to analyze
		//String string = null;
		String string = "광배근을 발달시키기 위한 대표적인 운동이다. 턱걸이를 하기 어려운 초보자들에게 좋은 운동이다. 팔만 이용하여 바(bar)를 잡아당기면 이두근의 참여도가 지나치게 높아지므로 동작을 시작하는 시점에서 견갑골(날개뼈)을 우선적으로 내리도록 한다. ";
		// init MorphemeAnalyzer
		MorphemeAnalyzer ma = new MorphemeAnalyzer();

		// create logger, null then System.out is set as a default logger
		ma.createLogger(null);

		// analyze morpheme without any post processing 
		List ret = ma.analyze(string);

		// refine spacing
		ret = ma.postProcess(ret);

		// leave the best analyzed result
		ret = ma.leaveJustBest(ret);

		// divide result to setences
		List stl = ma.divideToSentences(ret);

		// print the result
		for( int i = 0; i < stl.size(); i++ ) {
			Sentence st = (Sentence) stl.get(i);
			
			System.out.println("===>  " + st.getSentence());
			for( int j = 0; j < st.size(); j++ ) {
				System.out.println(st.get(j));
			}
		}

		ma.closeLogger();
		
		// string to extract keywords
		String strToExtrtKwrd ="광배근을 발달시키기 위한 대표적인 운동이다. 턱걸이를 하기 어려운 초보자들에게 좋은 운동이다. 팔만 이용하여 바(bar)를 잡아당기면 이두근의 참여도가 지나치게 높아지므로 동작을 시작하는 시점에서 견갑골(날개뼈)을 우선적으로 내리도록 한다. ";

		// init KeywordExtractor
		KeywordExtractor ke = new KeywordExtractor();

		// extract keywords
		KeywordList kl = ke.extractKeyword(strToExtrtKwrd, true);

		// print result
		for( int i = 0; i < kl.size(); i++ ) {
			Keyword kwrd = kl.get(i);
			System.out.println(kwrd.getString() + "\t" + kwrd.getCnt());
		}
	}
	public Komoran getAnalyzer()
	{
		Path path = new Path();
		Komoran komoran = new Komoran(path.getKomoran_path());
		//Komoran komoran = new Komoran("C:/models-full");
		return komoran;
	}
	public void clearKomoran(Komoran komoran)
	{
		komoran = null;
	}
	public ArrayList<String> getNouns(String document,Komoran komoran)
	{
		ArrayList<String> nouns = new ArrayList<String>();
		//Komoran komoran = new Komoran("C:/models-full");
		List<List<Pair<String,String>>> result = komoran.analyze(document);
		for (List<Pair<String, String>> eojeolResult : result) {
		for (Pair<String, String> wordMorph : eojeolResult) {
			//System.out.println(wordMorph);
			if (wordMorph.getSecond().equals("NNG") || wordMorph.getSecond().equals("NNP") || wordMorph.getSecond().equals("SL"))
			{
				String s = wordMorph.getFirst();
				//Term term = new Term();
				//term.setTerm(wordMorph.getFirst());
				//term.setMorpheme(wordMorph.getSecond());
				nouns.add(s);
				//System.out.println(wordMorph);
			}
		}
		//System.out.println();
		}
		return nouns;
	}	
}
