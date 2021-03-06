package ds.Ch02_tree.$7.unionfind.impl;

import ds.Ch02_tree.$7.unionfind.UF;

/**
 * @description 第四版并查集，孩子节点指向父节点的结构，数组模拟
 * 				基于rank的优化
 * @author Daffupman
 * @date 2019/07/09
 */
public class UnionFind4 implements UF {

	private int[] parent;		//用于存储下标对应的元素所在的集合parent
	private int[] rank;			//存储对应地，以sz[i]为根的树的高度

	public UnionFind4(int capacity) {
		parent = new int[capacity];
		rank = new int[capacity];
		for (int i = 0; i < parent.length; i++) {
			//初始化状态：parent数组中的父节点指向都是指向自己
			parent[i] = i;
			//sz初始化为1
			rank[i] = 1;
		}
	}
	
	@Override
	public int size() {
		return parent.length;
	}
	
	//返回指定的p和q的元素是否同属一个集合
	@Override
	public boolean isConnected(int p, int q) {
		return find(p) == find(q);
	}
	
	//返回索引p上的元素所在的集合id
	private int find(int p) {
		if(p < 0 || p > size()-1) {
			throw new IllegalArgumentException("index out of bound!");
		}
		return parent[p];
	}
	
	//联接p和q
	@Override
	public void unionElements(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);
		
		if(pRoot == qRoot)	return;
		
		//修改p索引处的父节点指向即可
		parent[p] = qRoot;

		// 比较两树的rank
		if(rank[pRoot] < rank[qRoot]) {
			parent[pRoot] = qRoot;
		} else if(rank[pRoot] > rank[qRoot]){
			parent[qRoot] = pRoot;
		} else {
			// 只有两树的rank相等时，rank才会增加
			parent[qRoot] = pRoot;
			rank[pRoot] += 1;
		}
	}
	
	public static void main(String[] args) {
		UnionFind4 uf = new UnionFind4(10);
		System.out.println(uf.isConnected(1, 3));
		uf.unionElements(1, 3);
		System.out.println(uf.isConnected(1, 3));
	}

}
