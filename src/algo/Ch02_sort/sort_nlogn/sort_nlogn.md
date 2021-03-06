## 排序基础    
### O(nlogn)的排序
1. 归并排序（Merge Sort）
    - 基本思路：先折半分，每份都是一个元素，再合并。
        - 归并过程：在两个排好序的数组，头部元素逐个比较，小的元素插入已归并的数组。
    - 自底向上的归并
    - 思路总结
        - 总方针：先分再合。
        - 细节问题：
            - 每次的划分都是对半划分的，
            - 在合并时，需要申请与本次范围相当大小的数组作为辅助函数，将辅助函数中的元素比较，逐个插入到原数组。
    
2. 快速排序（Quick Sort）
    - 基本思路（Partition子过程，即将数组分成两个部分的过程）：
        1. 选取数组的第一个位置作为标志点,记为 `v` ，记录这个位置的变量为 `l` ;
        ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190604162532.png)
        2. 遍历 `l` 之后的元素，在遍历的过程中，整理这个数组。让这个数组一部分大于选取的元素，一本分小于选取的元素。
        ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190604162744.png)
        3. 记录两部分的分界点为 `j` ，当前访问的元素记为 `e` 。那么在数组[l+1...j]都是小于v的，数组[j+1...i-1]都是大于 `v` 的。
        ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190604163133.png)
        4. 如果当前元素 `e` 是大于 `v` 的，那么只要 `j ++` 即可。如果当前元素 `e` 小于 `v` 。那么需要将当前元素`e`和j后面的元素交换。直到数组遍历完成。
        5. 将选取的元素 `v` 和 `j` 位置上的元素交换；
        ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190604163721.png)
        
    - 在一个近乎有序的数组中排序，快排的效率要比归并差很多。原因在于，虽然两个排序都是将数组一分为二，但是区别在于，归并每次都是平均分配，而快排却不能保证两个数组的长度。对于一个完全有序的数组，快排的效率会退化成O(n^2)。
        - 优化方案：在选取元素的时候才用随机选取。
        ```java_holder_method_tree
        // 随机在arr[l...r]的范围中, 选择一个数值作为标定点v
         swap( arr, l , (int)(Math.random()*(r-l+1))+l );
    - 在一个拥有大量重复元素的数组中排序，快排的效率要比归并差很多。也是由于快排分开的两个数组存在极度不平衡的原因
        - 优化方案：更换partition的实现思路
            1. 选取v元素,将两个数组置为首尾两端。定义小于v的数组的结尾下标为 `i` ,大于v的数组的开始下标为 `j`。
            ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190604175115.png)
            2. 下标 `i` 向后扫描，直到第一个大于v的元素。下标 `j` 向前扫面，直到第一个小于v的元素。
            ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190604175645.png)
            3. 两个下标的元素交换并前进,直到 `i==j` 。
            ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190604175914.png)
    - 三路快排
        - 思路：将数组分成三个区域
        ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190604181141.png)
            1. 第一种情况： `e == v`，下标 `i` 加一
            ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190604181415.png)
            2. 第二种情况： `e < v`，当前元素和等于v区域的第一个元素交换，`lt` 索引后移
           ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190604181552.png)
            3. 第三种情况： `e > v`，当前元素和 `gt` 下标的前一位交换，`gt` 前移
            ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190604181736.png)
            4. 处理完毕
            ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190604181829.png)
            5. `l` 位置和 `lt` 位置交换位置，然后再对不等于v的两个区域快排
            ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190604181954.png)
        - 这种思路对数组中包含大量重复元素的情况十分有效。
        - 和归并排序、快排相比，三路快排效率总体上是不错的。
        
    - 思路总结
        - 单路快排：大致思路如下，partition操作之后得到分界点，去往两边，再次partition，一直循环到左右指针越界（在这里的意思是左指针大于右指针），排序结束。  
            > 核心函数partition：  
              选取首元素为基准点,设立j指针，该指针为区域的分界点，本次的遍历范围将划分成：`[l+1...j] < v  和 (j...r] >= v`，j指向的元素属于小于基准元素的范围,遍历数组划分区域，最后交换基准元素和分界点元素的，返回分界指针j。
              
        - 双路快排：
            - 单路快排的改进版，由于单路快排在划分区域时，很容易将两区域划分的很不平衡，这样的时间复杂度更加地趋向O(n^2)。
            - 双路快排的partition函数在划分区域上，将区域划分为两个区域：`[l+1,i) <= v 和 (j,r] >= v`,将与基准点相等的值分别放在两个区域中，这样的分配相比单排的会平衡一些。
                > 核心函数partition：  
                  选取基准点（随机选取的性能会更好），设立指针i和j，分别代表着两个区域的排头兵，i指针往尾部遍历，在第一个大于基准元素处停下，而j指针往头部遍历，在第一个小于基准元素处停下。交换两处的元素，两指针分别前进。一直循环。其中的循环跳出条件为`i > j`。本次while循环结束后交换基准元素和j指针处的元素，并返回指针j。
                 
        - 三路快排：
            - 双路快排的改进，对于处理有大量重复元素的数组，改进的双路快排依然存在缺陷，如果在一次的partition中，有大量与基准元素相等元素存在，而在这次的partition中相同的元素会被划分进两个区域。而这些元素在这之后仍会比较，这样效率就被拉了下来。
            - 三路快排的思路，将区域划分为小于v的区域，等于v的区域和大于v的区域，即：`[l+1...lt] < e, (lt...gt) == v和[gt, r] > v` 
                > 核心函数partition：
                   选取基准点，设立分界指针lt和gt,以及遍历指针i，在i指针的遍历过程中，划分区域。最后交换基准元素和lt上的元素，然后分别两边递归
        
3. 归并排序和快排的衍生问题
    - 两种排序都使用了分治算法
    - 归并排序思路求逆序对。
    - 快排思路取出数组中第n大的元素
    
4. 堆排序（Heap Sort）
    - 适合动态数据的维护；
    - 优先队列：出队顺序和入队顺序无关，和优先级有关；
        1. 主要操作
            - 入队
            - 出队（取出优先级最高的元素）
        2. 优先队列的实现  
        |      |   入队   |   出队   |  
        | ---- | ---- | ---- |  
        |   普通数组   |   O(1)     |   O(n)     |  
        |   顺序数组   |   O(n)     |   O(1)     |  
        |   堆        |   O(lgn)   |   O(lgn)   |
    - 使用堆实现优先队列
        1. 二叉树：堆中某个节点的值总是不大于其父节点的值（最大堆），堆总是一个完全二叉树。
        ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190605084649.png)
        2. 使用数组实现堆，下标为0的地方不存储数据
        ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190605090152.png)
        3. 添加操作的核心操作：Shift Up。把新元素放数组末尾，然后让新元素和父辈节点比较，大的话上移。
            ```java_holder_method_tree
              void shiftUp(int k){
                  while( k > 1 && data[k/2].compareTo(data[k]) < 0 ){
                      swap(k, k/2);
                      k /= 2;
                  }
              }
            ```  
        4. 取出堆中的元素（根节点元素）：Shift Down。从堆中取出元素（只能取出最大的元素），把堆最后的元素替补根元素，堆大小减一。然后执行ShiftDown操作可以维持最大堆的性质。
            ```java_holder_method_tree
            private void shiftDown(int k){ 
                while( 2*k <= count ){
                    int j = 2*k; // 在此轮循环中,data[k]和data[j]交换位置
                    if( j+1 <= count && data[j+1].compareTo(data[j]) > 0 )
                        j ++;
                    // data[j] 是 data[2*k]和data[2*k+1]中的最大值
           
                    if( data[k].compareTo(data[j]) >= 0 ) break;
                    swap(k, j);
                    k = j;
                }
            }
            ```
        5. 把一个数组转化成堆，核心操作Heapify。从最后一个不是节点元素开始，执行shiftDown，直到根节点。
            ```java_holder_method_tree
                // 构造函数, 通过一个给定数组创建一个最大堆
                // 该构造堆的过程, 时间复杂度为O(n)
                public MaxHeap(Item arr[]){
            
                    int n = arr.length;
            
                    //数组索引0处不使用
                    data = (Item[])new Comparable[n+1];
                    capacity = n;
            
                    for( int i = 0 ; i < n ; i ++ )
                        data[i+1] = arr[i];
                    count = n;
            
                    for( int i = count/2 ; i >= 1 ; i -- )
                        shiftDown(i);
                }
            ```
            Heapify的时间复杂度为O(nlogn),而将n个元素逐一插入空堆中的复杂度为O(n)
    - 原地堆排序
        1. 数组下标为0的位置也利用进来
        ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190605095813.png)
        最后一个非叶子节点为：`(count-1)/2`
        2. 在每次把堆顶元素（最大元素）放在后面之后，将堆的数组表示范围缩小一个单位，然后再对索引0处做shiftDown直到范围右界缩小到索引1处。至此原地堆排序结束。
        
        原地堆排序比普通的堆排序的效率会更高一点，原因在于原地堆排不需要开辟新的空间和处理那些空间。
        
    - 索引堆（Index Heap）
        1. 堆是使用数组表示的，但之前数组中的索引没用利用上。要想把索引利用上，这样可以通过索引得到heapify之前保持原有顺序的元素
        ![](https://raw.githubusercontent.com/Daffupman/markdown-img/master/20190713225053.png)
        实际上是将index信息放在一个新的数组中，构建堆的过程只是索引发生交换。如果data域存储着复杂的元素，那么这样的操作效率是很高的
    
    - 思路总结
        - 使用堆这种数据结构，堆顶元素始终是堆中的最值。只要获取堆顶元素即可完成排序。
        - 普通堆排：将元素逐个加入堆中，加入的同时会进行shiftUp
        - 原地堆排：数组heapify操作
        - 堆的核心操作：
            - `shiftUp`:子元素主动与父元素比较，只要子元素大于父元素，交换俩元素位置，指针上移。、
            - `shiftDown`：父元素主动与子元素比较。选取子元素中较大的那个与父元素比较，父元素小的话，交换两个元素，并且指针移过来；否则停止，
        - 索引堆：
            - 与普通的堆相比，多了一个`indexes`数组，这个数组替代了`data`数组。所以我们在操作数组时，只需操作`indexes`数组即可，包括交换，上浮和下沉。
            - 而真正存储元素的数组`data`数组是不动的，我们只管添加元素即可。为`indexes`数组做查询使用。