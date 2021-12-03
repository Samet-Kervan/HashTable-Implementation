
public class DIBHashTableYHF<K,V> extends DIBHashTable<K,V> {
	public DIBHashTableYHF(int size) {
		super(size);
	}
	public DIBHashTableYHF(int size, int prime) {
		super(size,prime);
	}
	public DIBHashTableYHF(double loadFactor) {
		//For changing between load factors
		super(loadFactor);
	}
	protected int getKey(K k) {//(word length + ASCII of the character at index) * (indexNo + 3) 
		if(k instanceof String) {
			k = (K) ((String) k).toLowerCase();
			char characters[] = ((String) k).toCharArray();
			int key = 0;
			for (int i = 0; i < characters.length; i++) {
				int expression = (characters.length + characters[i]) * (i + 3);
				key += expression;
			}
			return key;
		}
		return (Integer) null;
	}
}
