public class HashNode<K,V> {//Nested node class for elements
	private K key;
	private V content;
	public HashNode(K key, V content) {
		this.key = key;
		this.content = content;
	}
	public K getkey() {
		return key;
	}
	public void setkey(K key) {
		this.key = key;
	}
	public V getcontent() {
		return content;
	}
	public void setcontent(V content) {
		this.content = content;
	}
}