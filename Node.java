package spell;
 
import spell.ITrie.INode;
 
public class Node implements INode {
    private int frequency = 0;
    private char letter;
    private Node[] array;
     
    public Node() {}
    
    public void incFreq() {
        frequency++;
    }
     
    public int getValue() {
        return frequency;
    }
     
    public void setLetter(char c) {
    	letter = c;
    }
    
    public char getLetter() {
        return letter;
    }
     
    public void setArray() {
    	array = new Node[26];
    }
    
    public void addToArray(int index, Node N) {
    	array[index] = N;
    }
    
    public Node[] getArray() {
        return array;
    }
    
    public int add(String word, int nodes) {
    	if (word.length() > 0) {
    		char c = word.charAt(0);
            int pos = c - 'a';
            if (array[pos] == null) {
            	nodes++;
                Node n = new Node();
                n.setLetter(c);
                n.setArray();
                addToArray(pos, n);
                if (word.length() > 1) {
                	word = word.substring(1);
                }
                else {
                	word = "";
                	n.incFreq();
                	return nodes;
                }
                return n.add(word, nodes);
            }
            else {
            	if (word.length() > 1) {
                	word = word.substring(1);
                }
                else {
                	word = "";
                	if (array[pos].frequency == 0) {
                		nodes = -1;
                	}
                	array[pos].incFreq();
                	return nodes;
                }
            	return array[pos].add(word, nodes);
            }
    	}
    	return nodes;
    }
    
    public Node find(String word) {
    	char c = word.charAt(0);
        int pos = c - 'a';
        if (array[pos] == null) {
        	return null;
        }
        else {
        	if (word.length() > 1) {
        		word = word.substring(1);
            }
            else {
            	word = "";
            	if (array[pos].getValue() > 0) {
            		return array[pos];
            	}
            	else {
            		return null;
            	}
            }
            return array[pos].find(word);
        }
    }
}