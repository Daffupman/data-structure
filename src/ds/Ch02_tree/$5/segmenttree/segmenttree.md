树(tree)
---
1. 线段树(SegmentTree)
    - 线段树不是满二叉树，却是一棵平衡二叉树（该树的最大深度与最小深度之差不超过1），但是可以看成一棵满二叉树。对于h层的满二叉树一共有2^h-1个节点（大约是2^h个节点），最后一层（h-1）有2^(h-1)个节点也就是最后一层的节点数大致等于前面所有层节点之和。此时，使用静态数组存储给定的区间数据就需要 `4*data.length`。
    - 对线段树的操作大多数不涉及增删，多为查改。
    - 复杂度分析
      创建：O(n)：4n
      更新：O(logn)
      查询：O(logn)
      
2. 线段树的图示
    - data属性和tree属性：在构建完线段树后的状态
    ![](https://raw.githubusercontent.com/daffupman/markdown-img/master/20190830204111.png)
    - tree数组的树形模拟
    ![](https://raw.githubusercontent.com/daffupman/markdown-img/master/20190830204633.png)