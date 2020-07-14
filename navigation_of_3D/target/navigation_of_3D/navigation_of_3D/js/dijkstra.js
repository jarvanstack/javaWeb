
/**
 * Dijkstra算法：单源最短路径
 * 思路：
 * 1. 将顶点分为两部分：已经知道当前最短路径的顶点集合Q和无法到达顶点集合R。
 * 2. 定义一个距离数组（distance）记录源点到各顶点的距离，下标表示顶点，元素值为距离。源点（start）到自身的距离为0，源点无法到达的顶点的距离就是一个大数（比如Infinity）。
 * 3. 以距离数组中值为非Infinity的顶点V为中转跳点，假设V跳转至顶点W的距离加上顶点V至源点的距离还小于顶点W至源点的距离，那么就可以更新顶点W至源点的距离。即下面distance[V] + matrix[V][W] < distance[W]，那么distance[W] = distance[V] + matrix[V][W]。
 * 4. 重复上一步骤，即遍历距离数组，同时无法到达顶点集合R为空。
 *
 * @param matrix 邻接矩阵，表示图
 * @param start 起点
 *
 *
 *
 * 如果求全图各顶点作为源点的全部最短路径，则遍历使用Dijkstra算法即可，不过时间复杂度就变成O(n^3)了
 * */
 /**
  * 邻接矩阵
  * 值为顶点与顶点之间边的权值，0表示无自环，一个大数表示无边(比如10000)
  * */
 const MAX_INTEGER = Infinity;//没有边或者有向图中无法到达
 const MIN_INTEGER = 0;//没有自环
 const M=MIN_INTEGER,m=MAX_INTEGER;
 /* 邻接矩阵 */
 const matrix=new Array(41);
 for(var i=0;i<41;i++){
 	matrix[i]=new Array(41);
 }
 for(var i=0;i<41;i++){
 	for(var j=0;j<41;j++){
 		if(i==j){
 			matrix[i][j]=MIN_INTEGER;
 		}else{
 			matrix[i][j]=MAX_INTEGER;
 		}
 	}
 }
 matrix[0][1]=3 ;
 
 matrix[1][0]=3 ;
 matrix[1][2]=6 ;
 matrix[1][6]=10 ;
 matrix[1][7]=8 ;
 matrix[1][8]=8 ;
 matrix[1][9]=10 ;
 
 matrix[2][1]=6 ;
 matrix[2][3]=4 ;
 matrix[2][6]=9 ;
 
 matrix[3][2]=4 ;
 matrix[3][4]=2 ;
 
 matrix[4][3]=2 ;
 matrix[4][5]=8 ;
 
 matrix[5][4]=8 ;
 matrix[5][21]=1.5 ;
 
 matrix[6][2]=9 ;
 matrix[6][1]=10 ;
 matrix[6][7]=4 ;
 matrix[6][15]=23 ;
 
 matrix[7][1]=8 ;
 matrix[7][6]=4 ;
 matrix[7][8]=1.5 ;
 matrix[7][10]=8 ;
 
 matrix[8][7]=1.5 ;
 matrix[8][1]=8 ;
 matrix[8][9]=4 ;
 
 matrix[9][8]=4 ;
 matrix[9][1]=10 ;
 matrix[9][16]=23 ;
 
 matrix[10][7]=8 ;
 matrix[10][30]=1.5 ;
 
 matrix[11][12]=7 ;
 
 matrix[12][11]=7 ;
 matrix[12][13]=1.5;
 matrix[12][14]=8 ;
 
 matrix[13][12]=1.5 ;
 matrix[13][15]=7 ;
 
 matrix[14][12]=8 ;
 matrix[14][34]=1.5 ;
 
 matrix[15][13]=7 ;
 matrix[15][6]=23 ;
 matrix[15][16]=9.5 ;
 
 matrix[16][15]=9.5 ;
 matrix[16][9]=23 ;
 matrix[16][17]=7 ;
 
 matrix[17][19]=8 ;
 matrix[17][16]=7 ;
 matrix[17][18]=1.5 ;
 
 matrix[18][17]=1.5 ;
 matrix[18][20]=7 ;
 
 matrix[19][17]=8 ;
 matrix[19][39]=1.5 ;
 
 matrix[20][18]=7 ;
 
 matrix[21][22]=8 ;
 matrix[21][5]=1.5 ;
 
 matrix[22][21]=8 ;
 matrix[22][23]=2 ;
 
 matrix[23][22]=2 ;
 matrix[23][24]=4 ;
 
 matrix[24][23]=4 ;
 matrix[24][26]=9 ;
 matrix[24][25]=12 ;
 
 matrix[25][24]=12 ;
 matrix[25][29]=5 ;
 
 matrix[26][24]=9 ;
 matrix[26][27]=4 ;
 matrix[26][35]=23 ;
 
 matrix[27][26]=4 ;
 matrix[27][28]=1.5 ;
 
 matrix[28][27]=1.5 ;
 matrix[28][29]=4 ;
 matrix[28][30]=8 ;
 
 matrix[29][28]=4 ;
 matrix[29][25]=5 ;
 matrix[29][36]=23 ;
 
 matrix[30][10]=1.5 ;
 matrix[30][28]=8 ;
 
 matrix[31][32]=7 ;
 
 matrix[32][31]=7 ;
 matrix[32][33]=1.5 ;
 
 matrix[33][32]=1.5 ;
 matrix[33][34]=8 ;
 matrix[33][35]=7 ;
 
 matrix[34][14]=1.5 ;
 matrix[34][33]=8 ;
 
 matrix[35][26]=23 ;
 matrix[35][33]=7 ;
 matrix[35][36]=9.5 ;
 
 matrix[36][35]=9.5 ;
 matrix[36][37]=7 ;
 matrix[36][29]=23 ;
 
 matrix[37][36]=7 ;
 matrix[37][38]=1.5 ;
 
 matrix[38][37]=1.5 ;
 matrix[38][39]=8 ;
 matrix[38][40]=7 ;
 
 matrix[39][38]=8 ;
 matrix[39][19]=1.5 ;
 
 matrix[40][38]=7 ;
 
 
 
 
 
 
 
 
function Dijkstra( start,destination) {
    const rows = matrix.length,//rows和cols一样，其实就是顶点个数
        cols = matrix[0].length;
 
    if(rows !== cols || start >= rows) return new Error("邻接矩阵错误或者源点错误");
 
    //初始化distance
    const distance = new Array(rows).fill(Infinity);
	/* 初始化每个点的父节点 */
	const parent = new Array(rows).fill(Infinity);
	/* 定义一个存储到目的地最短路径的数组 */
	var pointsIndex=new Array();

	var bridge=new Array();
	bridge.push(start);

	parent[start]=-1;
    distance[start] = 0;
    while(bridge.length!=0) {
        //达到不了的顶点不能作为中转跳点
			var i=bridge.pop();
            for(let j = 0; j < cols; j++) {
                //比如通过比较distance[i] + matrix[i][j]和distance[j]的大小来决定是否更新distance[j]。
                if(matrix[i][j] + distance[i] < distance[j]) {
					bridge.push(j);
                    distance[j] = matrix[i][j] + distance[i];
					parent[j]=i;
                }
            
            /* console.log(distance);
			console.log(parent); */
        }
    }
	/* 定义一个存储到目的地最短路径的数组(反向) */
	var RpointsIndex=new Array();
	RpointsIndex[0]=destination;
	var temp=destination;
	var flag=1;//数组长度标志
	while(parent[temp]!=-1){
		temp=parent[temp];
		RpointsIndex[flag]=temp;
		flag++;
	}
	for(let i=0;i<flag;i++){
		pointsIndex[i]=RpointsIndex[flag-i-1];
	}
	
    return pointsIndex;
}
 


/* 
const matrix= [
//  [ 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 ,10 ,11 ,12 ,13 ,14 ,15 ,16 ,17 ,18 ,19 ,20 ,21 ,22 ,23 ,24 ,25 ,26 ,27 ,28 ,29 ,30 ,31 ,32 ,33 , m , m , m , m , m , m , m , m],
	
    [ M , 3 , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
    [ 3 , M , 6 , m , m , m ,10 , 8 , 8 ,10 , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , 6 , M , 4 , m , m , 9 , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , 4 , M , 2 , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , 2 , M , 8 , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , 8 , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	
	[ m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m , m],
	
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m , m],
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M , m],
	
	[ m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , m , M],
	
]; */
 
/* console.log(Dijkstra(matrix, 0,4));//[ 0, 5, 2, 7, 6 ] */