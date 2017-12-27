package spell;
  
public class Trie implements ITrie {
    private int word_count;
    private int node_count;
    private Node root;
    
    public Trie() {
    	node_count = 1;
    	root = new Node();
    	root.setLetter('@');
    	root.setArray();
    }
    
    public void add(String word) {
    	int res = root.add(word, 0);
    	if (res != -1) {
    		node_count += res;
    	}
    	if (res > 0 || res == -1) {
    		word_count++;
    	}
    }
 
    public Node find(String word) {
    	if (word == "") {
    		return null;
    	}
    	Node n = root.find(word);
        return n;
    }

    public int getWordCount() {
        return word_count;
    }
    
    public int getNodeCount() {
        return node_count;
    }
 
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	StringBuilder sb2 = new StringBuilder();
    	sb = strRec(root, sb, sb2);
    	return sb.toString();
    }
    
    public StringBuilder strRec(Node n, StringBuilder sb, StringBuilder sb2) {
    	for (int i = 0; i < 26; i++) {
    		if (n.getArray()[i] != null) {
    			sb.append(n.getArray()[i].getLetter());
    			if (n.getArray()[i].getValue() > 0) {
    				sb2.append(sb); 
    				sb2.append('\n');
        		}
    			StringBuilder x = strRec(n.getArray()[i], sb, sb2);
    			if (i == 25) {
        			return x;
    			}
    		}
    		if (i == 25) {
    			if (sb.length() > 0) {
					sb.setLength(sb.length() - 1);
				}
    		}
    	}
    	return sb2;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + node_count;
        result = prime * result + word_count;
        if (root != null) {
        	for (int i = 0; i < 26; i++) {
            	if (root.getArray()[i] != null) {
            		result = prime * result + root.getArray()[i].getValue();
            		result = prime * result + i;
            		break;
            	}
            }
            for (int i = 0; i < 26; i++) {
            	if (root.getArray()[25-i] != null) {
            		result = prime * result + root.getArray()[i].getValue();
            		result = prime * result + (25 - i);
            		break;
            	}
            }
        }
        return result;
    }
      
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Trie other = (Trie) obj;
        if (node_count != other.node_count) {
        	return false;
        } 
        if (word_count != other.word_count) {
        	return false;
        }
        if (root == null) {
            if (other.root != null)
                return false;
        }
        return eqRec(root, other.root);
    }
    
    public boolean eqRec(Node n1, Node n2) {
    	for (int i = 0; i < 26; i++) {
    		Node new_n1 = n1.getArray()[i];
    		Node new_n2 = n2.getArray()[i];
    		if ((new_n1 == null && new_n2 != null) || (new_n1 != null && new_n2 == null)) {
    			return false;
    		}
    		else if (new_n1 != null && new_n2 != null) {
    			if (new_n1.getLetter() != new_n2.getLetter()) {
    				return false;
    			}
    			if (new_n1.getValue() != new_n2.getValue()) {
    				return false;
    			}
    			if (!eqRec(new_n1, new_n2)) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
}
