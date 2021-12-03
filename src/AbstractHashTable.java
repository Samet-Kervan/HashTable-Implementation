//An abstract hash table for general use
public abstract class AbstractHashTable<K,V> {
	protected int n = 0;//Number of elements in the table
	protected int prime, size;//Prime is for PAF hash function
	protected double loadFactor;//States when the resize function should be called
	protected HashNode<K,V>[] table;
	protected class HashNode<A,B> {//Nested node class for elements
		private A key;
		private B content;
		private int count, distanceFromOriginal;
		protected HashNode(A key, B content) {
			this(key, content, 0);
		}
		protected HashNode(A key, B content, int distanceFromOriginal) {
			this(key, content, 1, distanceFromOriginal);
		}
		protected HashNode(A key, B content, int count, int distanceFromOriginal) {
			this.key = key;
			this.content = content;
			this.count = count;
			this.distanceFromOriginal = distanceFromOriginal;
		}
		protected A getkey() {
			return key;
		}
		protected void setkey(A key) {
			this.key = key;
		}
		protected B getcontent() {
			return content;
		}
		protected void setcontent(B content) {
			this.content = content;
		}
		protected int getCount() {
			return count;
		}
		protected void setCount(int count) {
			this.count = count;
		}
		protected void addCount() {
			this.count++;
		}
		public int getDistanceFromOriginal() {
			return distanceFromOriginal;
		}
		public void setDistanceFromOriginal(int distanceFromOriginal) {
			this.distanceFromOriginal = distanceFromOriginal;
		}
		public void addDistanceFromOriginal() {
			distanceFromOriginal++;
		}
	}//End of nested class
	public AbstractHashTable(){
		this(997);
	}
	public AbstractHashTable(int size){
		this(size,37,0.5);
	}
	public AbstractHashTable(int size, int prime, double loadFactor){
		this.size = size;
		this.prime = prime;
		this.loadFactor = loadFactor;
		createTable();
	}
	public int getSize() {
		return size;
	}
	public int getPrime() {
		return prime;
	}
	public double getLoadFactor() {
		return loadFactor;
	}
	public void setLoadFactor(double newLoadFactor) {
		this.loadFactor = newLoadFactor;
	}
	//Abstract functions for extended classes
	protected abstract void createTable();//Creates a new table
	protected abstract HashNode<K,V> get(K k);//Finds and returns the node with the given key. Returns null if it does not exist
	protected abstract int getKey(K k);//Returns the integer value of the given key
	protected abstract void setCount(int count, K k);//Sets the count of the node with the given key
	protected abstract void put(K k, V v);//Creates a node with the given key and puts in the table if its not already in table
	protected abstract void resize();//Creates a new table with double size
}
