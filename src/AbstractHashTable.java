//An abstract hash table for general use
public abstract class AbstractHashTable<K,V> {
	protected int n = 0;//Number of elements in the table
	protected int prime, size;//Prime is for PAF hash function
	protected double loadFactor;//States when the resize function should be called
	protected HashNode<K,V>[] table;
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
	protected abstract void put(K k, V v);//Creates a node with the given key and puts in the table if its not already in table
	protected abstract void resize();//Creates a new table with double size
}
