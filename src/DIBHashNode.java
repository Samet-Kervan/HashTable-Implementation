
public class DIBHashNode<K,V> extends HashNode<K,V> {
	private int count, distanceFromOriginal;
	public DIBHashNode(K key, V content, int count, int distanceFromOriginal) {
		super(key,content);
		this.count = count;
		this.distanceFromOriginal = distanceFromOriginal;
	}
	public DIBHashNode(K key, V content) {
		this(key, content, 0);
	}
	public DIBHashNode(K key, V content, int distanceFromOriginal) {
		this(key, content, 1, distanceFromOriginal);
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void addCount() {
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
}
