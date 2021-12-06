//A hash table class that uses DIB for collision handling.
//This class is written mainly for string data types.
public class DIBHashTable<K,V> extends AbstractHashTable<K,V> {
	private int collisionCount;
	protected DIBHashNode<K,V>[] table;
	public DIBHashTable() {
		super();
	}
	public DIBHashTable(int size) {
		this(size,37);
	}
	public DIBHashTable(int size, int prime) {
		this(size,prime,0.50);
	}
	public DIBHashTable(double loadFactor) {
		//For changing between load factors
		this(997,37,loadFactor);
	}
	public DIBHashTable(int size, int prime, double loadFactor) {
		super(size,prime,loadFactor);
		collisionCount = 0;
	}
	public int getCollisionCount() {
		return collisionCount;
	}
	protected void createTable() {
		table = (DIBHashNode<K,V>[]) new DIBHashNode[size];
	}
	
	protected DIBHashNode<K,V> get(K k) {//Searches the given key in the hash table. 
									  //If the key is not in the table returns a hash node object with null values.
		int key = getKey(k);//Finds the key integer of the given key
		int index = key % size;
		if (index < 0) {//There might be a overflow
			index += size;
		}
		DIBHashNode<K,V> node = table[index];
		while (!(node == null)) {//Searches the key in the table till finding it or when it encounters an empty space
								//or when it reaches the end of the table
			if (node.getkey().equals(k)) {
				return node;
			}
			else {
				index++;
				if (index >= size) {
					return null;
				}
				node = table[index];
			}
		}
		return node;
	}
	protected int getKey(K k) {//Chooses a hash function according to option selected by user 
		try {
			int key = 0;
			return key;
		} catch (ClassCastException e) {
			throw e;
		}
	}
	protected void setCount(int count, K k) {//For rearranging counts after resize function
		DIBHashNode<K,V> arrayNode = get(k);
		arrayNode.setCount(count);
	}
	protected void put(K k, V v) {//inserts the given key in table
		DIBHashNode<K,V> arrayNode = get(k);
		DIBHashNode<K,V> node = new DIBHashNode<K,V>(k,v);
		boolean resize = false;//Program might have called the resize function while trying to insert the node
								//so that node has to be inserted to the table again. This is used for checking that
		if (arrayNode == null) {
			int key = getKey(k);
			int index = key % size;
			if (index < 0) {//Might be a overflow so index will be negative. This way it will be positive
				index += size;
			}
			node.setDistanceFromOriginal(0);
			while(true) {
				if (table[index] == null) {//Find an empty spot
					table[index] = node;
					n++;
					break;
				}
				else{
					collisionCount++;//For performance monitoring
					if (table[index].getDistanceFromOriginal() < node.getDistanceFromOriginal()) {
						//When the node that is inserted in table has lower DIB than the node that has already
						//inserted it changes the nodes and tries to insert the other one.
						DIBHashNode<K,V> tempNode = table[index];
						table[index] = new DIBHashNode<K,V>(node.getkey(), node.getcontent(), node.getCount(), node.getDistanceFromOriginal());
						node = tempNode;
					}
					index++;
					node.addDistanceFromOriginal();
					if (index >= size) {//It reached the end of the table
						resize();
						resize = true;
						break;
					}
				}
			}
		}
		else {
			arrayNode.addCount();
			if (!arrayNode.getcontent().equals(v)) {//If the key is the same but the content is changed
				arrayNode.setcontent(v);
			}
		}
		if (resize) {//When the key could not be inserted because it reached the end of the table and forced to call
					//the resize function it tries to insert the key in table again
			put(node.getkey(),node.getcontent());
			DIBHashNode<K,V> resizeNode = get(node.getkey());
			resizeNode.setCount(node.getCount());
		}
		double db = (double) n / size;
		if (db >= loadFactor) {//If the table is reached to the load factor. Using a variable because it does not gives an appropriate value otherwise
			resize();
		}
	}
	public Object[] valueGet(K k) {//Returns an printable object array
		//Index 0 is the key string thats searched, index 1 is integer value of the string key
		//index 2 is the count value, index 3 is keys current index on the table.
		//index 4 is time passed for searching in nanoseconds
		Object[] values = new Object[5];
		long startTime = System.nanoTime();
		DIBHashNode<K,V> node = get(k);
		long endTime = System.nanoTime();
		values[4] = endTime - startTime; 
		values[0] = k;
		values[1] = getKey(k);
		if (node == null) {//If the searched key is not in the hash table
			values[2] = 0;
			values[3] = null;
		}
		else {
			values[2] = node.getCount();
			if (getKey(k) < 0) {
				values[3] = (getKey(k) % size) + node.getDistanceFromOriginal() + size;	
			}
			else {
				values[3] = (getKey(k) % size) + node.getDistanceFromOriginal();
			}
		}
		return values;
	}
	public void resize() {
		DIBHashNode<K,V>[] buffer = (DIBHashNode<K,V>[]) new DIBHashNode[size]; 
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = table[i];
		}
		n = 0;
		size = size * 2;
		createTable();
		for (int i = 0; i < buffer.length; i++) {
			try {
				put(buffer[i].getkey(),buffer[i].getcontent());
				setCount(buffer[i].getCount(),buffer[i].getkey());
			} catch (NullPointerException e) {//Some indexes of the buffer will be null. 
			}
		}
	}
}
