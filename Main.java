package spell;
  
import java.io.IOException;
  
import spell.ISpellCorrector.NoSimilarWordFoundException;
  
public class Main {
      
    public static void main(String[] args) throws NoSimilarWordFoundException, IOException {
        String dicFileName = args[0];
        String inputWord = args[1];
        String dic2 = args[2];
        SpellCorrector corrector = new SpellCorrector();
        SpellCorrector corrector2 = new SpellCorrector();
        corrector.useDictionary(dicFileName);
        corrector2.useDictionary(dic2);
        
        if (corrector.getDictionary().equals(corrector2.getDictionary())) {
        	 System.out.println("Dictionaries match");
        }
        else {
        	System.out.println("Dictionaries DON'T match");
        }
        
        String suggestion = corrector.suggestSimilarWord(inputWord);
        System.out.println("Suggestion is: " + suggestion);
        System.out.println("Word count: " + corrector.getDictionary().getWordCount());
        System.out.println("Node count: " + corrector.getDictionary().getNodeCount());
        System.out.println("toString:");
        System.out.println(corrector.getDictionary().toString());
    }
     
}