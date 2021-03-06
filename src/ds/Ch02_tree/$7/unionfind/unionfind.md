树(tree)
---
1. 并查集（UnionFind）
    - 与之前大多数的树形结构不同的是，并查集中的父亲节点都是由孩子节点指向的。而这样的结构适合解决连接问题（Connectivity Problem）。
    - 连接问题  
    ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190417103436.png?token=AlacKIhY1inC6KXb2cFKLzsJ1lEPuX3vks5ctpD5wA%3D%3D)  
    
    问：图中任意的两点是否连接？  
    像这种判断网络中节点间的连接状态，并查集是一个高效快速的解决这类问题的数据结构。
    - 对于一组数据，并查集主要支持两个动作
        - union(p,q)
        - isConnected(p,q)
    - 并差集的接口设计
        ```java
        public interface UF {
            int getSize();
            boolean isConnected(int p, int q);
            void unionElements(int p, int q);
        }
        ```
    并差集的基本数据表示  
    ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190417105707.png)
    
    - 并查集的实现
        - 1). Quick Find
            ```java
            public class UnionFind1 implements UF {
            
              private int[] id;   //存储着数据对应着的集合编号
              
              public UnionFind1(int size) {
                  //初始化指定size的id数组
                  id = new int[size];  
                  for(int i = 0; i < id.length ; i++) {
                      //id数组的初始化状态为：任意两个元素都不在一个集合中
                      id[i] = i;
                  }
              }
        
              //返回并差集中的元素个数
              @Override
              public int getSize() {
                  return id.length;
              }
        
              //返回元素p和元素q是否连接
              @Override
              public boolean isConnected(int p, int q) {
                  return find(p) == find(q);
              }
        
              //返回元素p的集合编号
              private int find(int p) {
                  //索引检查
                  if(p < 0 || p >= id.length) {
                      throw new IllegalArgumentException("p is out of bound");
                  }
                  return id[p];    
              }
        
              //将元素p和元素q合并到一个集合，O(n)
              @Override
              public void unionElements(int p, int q) {
                  int pId = find(p);
                  int qId = find(q);
            
                  if(pId == qId) {
                      //p和q已经在一个集合中了
                      return;
                  }
            
                  //合并的过程就是遍历这个数组，将两个元素的集合编号修改为同一个id
                  for(int i = 0; i < id.length ; i++) {
                      if(id[i] == pId) {
                          id[i] = qId;    
                      }
                  }
              }
              
            }
            ```
            快速查找版的，使用数组模拟的并查集：  
            unionElements(p,q)    ->  O(n)  
            isConnected(p,q)      ->  O(1)  
            判断并查集中两个元素是否连接是否连接的速度极快
        
        - 2）Quick Union  
        将每个元素看做是一个节点,但是实际上还是使用数组实现
        ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190417113425.png)  
        并查集中的每个节点：孩子节点指向父亲节点，
        节点2是并查集的根节点，根节点指向自己。将两个元素合并的过程，就是先找到这两个元素的根节点，将其中的一个根节点的指向修改成另一个根节点  
        Quick Union下的数据表示  
        ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190417113805.png)
            ```java
            public class UnionFind2 {
              
              private int[] parent;   //存储节点的父节点的下标
              private int[] sz;       //sz[i]表示以i为根的集合元素的个数
        
              public UnionFind2(int size) {
                  parent = new int[size];
                  sz = new int[size];
            
                  for(int i = 0; i < parent.length; i++) {
                      parent[i] = i;
                      sz[i] = 1;
                  }
              }
        
              @Override
              public int getSize() {
                  return parent.length;
              }
        
              @Override
              public boolean isConnected(int p, int q) {
                  return find(p) == find(q);
              }
        
              //返回元素p的父亲节点的下标
              private int find(int p) {
                  //索引检查
                  if( p<0 || p >= parent.length) {
                      throw new IllegalArgumentException("p is out of bound.");        
                  }
            
                  while(p != parent[p]) {
                      p = parent[p];
                  }
            
                  return p;
              }
        
              //将p和q合并
              @Override
              public void unionElements(int p, int q) {
                  int pRoot = find(p);
                  int qRoot = find(q);
            
                  if(pRoot == qRoot) {
                      return;
                  }
            
                  parent[pRoot] = qRoot;
              }
        
            }
            ```
        - 3）基于size的优化  
        版本二的并查集在union操作的时候，生成的树有可能退化成链表结构。在版本二的基础上，对sz数组的维护可以避免树的退化。
            ```java
            public class UnionFind3 {
                      
              private int[] parent;   //存储节点的父节点的下标
        
              public UnionFind3(int size) {
                  parent = new int[size];
                  for(int i = 0; i < parent.length; i++) {
                      parent[i] = i;
                  }
              }
        
              @Override
              public int getSize() {
                  return parent.length;
              }
        
              @Override
              public boolean isConnected(int p, int q) {
                  return find(p) == find(q);
              }
        
              //返回元素p的父亲节点的下标
              private int find(int p) {
                  //索引检查
                  if( p<0 || p >= parent.length) {
                      throw new IllegalArgumentException("p is out of bound.");        
                  }
            
                  while(p != parent[p]) {
                      p = parent[p];
                  }
            
                  return p;
              }
        
              //将p和q合并
              @Override
              public void unionElements(int p, int q) {
                  int pRoot = find(p);
                  int qRoot = find(q);
              
                  if(pRoot == qRoot) {
                      return;
                  }
              
                  //根据两个元素所在树的元素个数判断合并的方向
                  //将元素少的集合合并到元素个数多的集合上
                  if(sz[pRoot] < sz[qRoot]) {
                      parent[pRoot] = qRoot;
                      sz[qRoot] += sz[pRoot];
                  } else {
                      parent[qRoot] = pRoot;
                      sz[pRoot] += sz[pRoot];
                  }
              }
        
            }
            ```
            基于size的优化：
            sz变量存储着当前节点中元素的个数，在unionElements操作的时候会对合并的两个节点进行判断，节点元素少的合并到节点了元素多的集合上。
        - 4）基于rank的优化  
        在基于size的优化中，union的合并存在这样的情况：元素多的树的深度很小，而元素少的深度很大。按照合并逻辑，元素少的集合合并到元素多的集合上。这样合并之后的树深度增加了，这是不合理的。  
        显然在合并时的逻辑应该改为，深度小的合并到深度大的集合，可以降低合并后的树的深度。
        ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190417150900.png)
            ```java
              public class UnionFind4 {
                                
                private int[] parent;   //存储节点的父节点的下标
                private int[] rank;     //rank[i]表示以i为根的集合所表示的树的层数
          
                public UnionFind4(int size) {
                    parent = new int[size];
                    rank = new int[size];
              
                    for(int i = 0; i < parent.length; i++) {
                        parent[i] = i;
                        rank[i] = i;
                    }
                }
          
                //其他的操作同版本3
          
                //将p和q合并
                @Override
                public void unionElements(int p, int q) {
                    int pRoot = find(p);
                    int qRoot = find(q);
                
                    if(pRoot == qRoot) {
                        return;
                    }
                
                    //根据两个元素所在树的rank判断合并的方向
                    //将元素少的集合合并到rank高的集合上
                    if(rank[pRoot] > rank[qRoot]) {
                        parent[pRoot] = qRoot;
                    } else if(rank[qRoot] < rank[pRoot]){
                        parent[qRoot] = pRoot;
                    } else {
                        parent[qRoot] = pRoot;
                        rank[pRoot] += 1;
                    }
                }
          
              }
            ```
        - 5）路径压缩  
        ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190417152733.png)  
        对于find(4)操作，上面三种结构的结构是一样的，但在效率上存在差异。路径压缩的原理就是尽量让高的树变成矮的树。
            ```java
              public class UnionFind5 {
                                
                private int[] parent;   //存储节点的父节点的下标
                private int[] rank;     //rank[i]表示以i为根的集合所表示的树的层数
          
                public UnionFind5(int size) {
                    parent = new int[size];
                    rank = new int[size];
              
                    for(int i = 0; i < parent.length; i++) {
                        parent[i] = i;
                        rank[i] = i;
                    }
                }
          
                //其他的操作同版本4
          
                //返回元素p的父亲节点的下标
                private int find(int p) {
                    //索引检查
                    if( p<0 || p >= parent.length) {
                        throw new IllegalArgumentException("p is out of bound.");        
                    }
              
                    while(p != parent[p]) {
                        //将父节点的指向为父节点的父节点
                        parent[p] = parent[parent[p]];
                        p = parent[p];
                    }
              
                    return p;
                }
              }
            ```
            路径压缩的操作放在了find的操作中，将当前节点的父节点指向为父节点的父节点  
        - 6）路径压缩的优化，将当前节点下的所有节点的父节点指向修改成当前节点
        修改版本5的find操作
            ```java
              public class UnionFind5 {
            
                  //其他的操作同版本5
            
                  //返回元素p的父亲节点的下标
                  private int find(int p) {
                      //索引检查
                      if( p<0 || p >= parent.length) {
                          throw new IllegalArgumentException("p is out of bound.");        
                      }
                
                      while(p != parent[p]) {
                          //将父节点的指向直接改成根节点
                          parent[p] = find(parent[p]);
                      }
                
                      return p;
                  }
              }
            ```
    - 并查集的时间复杂度分析  
      结论：O(h)  
      严格上O(log*n)，一个近乎于O(1)的复杂度。