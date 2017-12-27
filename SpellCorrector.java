package spell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Scanner;
import spell.ISpellCorrector.NoSimilarWordFoundException;
 
public class SpellCorrector implements ISpellCorrector {
	
	private Trie dictionary = new Trie();
	private TreeMap<String, Integer> variations = new TreeMap<String, Integer>();
	private TreeMap<String, Integer> variations2 = new TreeMap<String, Integer>();
	private char[] alph;
	
	public Trie getDictionary() {
		return dictionary;
	}
	
	public SpellCorrector() {
		alph = new char[26];
		for (int i = 0; i < 26; i++) {
			int num = 97 + i;
			char c = (char) num;
			alph[i] = c;
		}
	}
	
    public void useDictionary(String dicFileName) throws IOException {
    	Scanner sc = new Scanner(new File(dicFileName));
        while (sc.hasNext()) {
        	String word = sc.next();
        	word = word.toLowerCase();
            dictionary.add(word);
        }
        sc.close();
        System.out.println();
    }
     
    public String suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException {
    	inputWord = inputWord.toLowerCase();
        if (dictionary.find(inputWord) != null) {
            return inputWord;
        }
        else {
        	alteration(inputWord, variations);
        	deletion(inputWord, variations);
        	insertion(inputWord, variations);
        	transposition(inputWord, variations);
        	Node n = isComplete(variations);
        	if (n != null) {
        		return highest(variations);
        	}
        	for (Entry<String, Integer> entry : variations.entrySet()) {
        		alteration(entry.getKey(), variations2);
            	deletion(entry.getKey(), variations2);
            	insertion(entry.getKey(), variations2);
            	transposition(entry.getKey(), variations2);
        	}
        	n = isComplete(variations2);
        	if (n != null) {
        		return highest(variations2);
        	}
        }
        throw new NoSimilarWordFoundException();
    }
    
    public String highest(TreeMap<String,Integer> map) {
    	int highest = 0;
    	String highS = "";
    	for (Entry<String, Integer> entry : map.entrySet()) {
    		String temp = entry.getKey().toLowerCase();
    		Node n = dictionary.find(temp);
        		if (n != null) {
    			entry.setValue(n.getValue());
        		if (entry.getValue() > highest) {
        			highest = entry.getValue();
        			highS = entry.getKey();
        		}
    		}
    	}
    	return highS;
    }
    
    public Node isComplete(TreeMap<String,Integer> map) {
    	for (Entry<String, Integer> entry : map.entrySet()) {
    		String temp = entry.getKey().toLowerCase();
    		Node n = dictionary.find(temp);
    		if (n != null) {
    			return n;
    		}
    	}
    	return null;
    }
    
    public void alteration(String word, TreeMap<String,Integer> map) {
    	for (int i = 0; i < word.length(); i++) {
    		for (int j = 0; j < 26; j++) {
    			if (alph[j] != word.charAt(i)) {
        			StringBuilder newStringB = new StringBuilder(word);
        			newStringB.setCharAt(i, alph[j]);
        			String newString = newStringB.toString();
        			map.put(newString, 0);
    			}
    		}
    	}
    }
    
    public void deletion(String word, TreeMap<String,Integer> map) {
    	for (int i = 0; i < word.length(); i++) {
    		StringBuilder newStringB = new StringBuilder(word);
			newStringB.deleteCharAt(i);
			String newString = newStringB.toString();
			map.put(newString, 0);
    	}
    }

    public void insertion(String word, TreeMap<String,Integer> map) {
    	for (int i = 0; i < word.length() + 1; i++) {
    		for (int j = 0; j < 26; j++) {
    			StringBuilder newStringB = new StringBuilder(word);
    			newStringB.insert(i, alph[j]);
    			String newString = newStringB.toString();
    			map.put(newString, 0);
    		}
    	}
    }
    
    public void transposition(String word, TreeMap<String,Integer> map) {
    	for (int i = 0; i < word.length() - 1; i++) {
    		StringBuilder newStringB = new StringBuilder(word);
            char l = newStringB.charAt(i), r = newStringB.charAt(i + 1);
            newStringB.setCharAt(i, r);
            newStringB.setCharAt(i + 1, l);
			String newString = newStringB.toString();
			map.put(newString, 0);
    	}
    }
    
}
