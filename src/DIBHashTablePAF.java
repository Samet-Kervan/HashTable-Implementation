
public class DIBHashTablePAF<K,V> extends DIBHashTable<K,V>{
	public DIBHashTablePAF(int size) {
		super(size);
	}
	public DIBHashTablePAF(int size, int prime) {
		super(size,prime);
	}
	public DIBHashTablePAF(double loadFactor) {
		//For changing between load factors
		super(loadFactor);
	}
	protected int getKey(K k) {//Finding keys for string with polynomial hash function
		if (k instanceof String) {
			k = (K) ((String) k).toLowerCase();
			char characters[] = ((String) k).toCharArray();
			int key = 0, power = characters.length - 1;
			for (int i = 0; i < characters.length; i++) {
				int  expression = (int) ((characters[i] - 96)* (Math.pow(prime, power)));
				//lower case letters start at the 97th in the ASCII table so -96 gives the letters alphabet location
				key += expression;//When I directly make key += than key does not overflow so I made it this way
				power--;
			}
			return key;
		}
		int x =5;
		return (Integer) null;
	}
}
