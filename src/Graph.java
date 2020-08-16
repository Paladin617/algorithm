import java.util.LinkedList;

public class Graph {//有向有权图的邻接表表示
    private LinkedList<Edge> adj[];//邻接表
    private int v; //顶点个数

    public Graph(int v) {
        this.v = v;
        this.adj = new LinkedList[v];
        for (int i = 0; i < v; i++) {
            this.adj[i] = new LinkedList<Edge>();
        }
    }

    public void addEdge(int s, int t, int w) {
        this.adj[s].add(new Edge(s, t, w));
    }

    private class Edge {
        public int sid; // 边的起始顶点编号
        public int tid; // 边的终止顶点编号
        public int w; // 权重

        public Edge(int sid, int tid, int w) {
            this.sid = sid;
            this.tid = tid;
            this.w = w;
        }
    }

    private static class Vertex {
        public int id;  // 顶点编号ID
        public int dist;    // 从起始顶点到这个顶点的距离

        public Vertex(int id, int dist) {
            this.id = id;
            this.dist = dist;
        }
    }

    private class PriorityQueue {// 根据vertex.dist构建小顶堆
        public Vertex[] nodes;
        private int maxSize;
        private int count;

        public PriorityQueue(int v) {
            this.count = 0;
            this.maxSize = v;
            this.nodes = new Vertex[maxSize+1];
        }

        public Vertex poll(){
            Vertex t = nodes[1];
            nodes[1] = nodes[count--];
            int i,c;
            for(i = 1; (c = i*2) <=count; i =c){
                if (c+1<=count && nodes[c+1].dist<nodes[c].dist)
                    c++;
                if (nodes[i].dist < nodes[c].dist)
                    break;
                Vertex v = nodes[i];
                nodes[i] = nodes[c];
                nodes[c] = v;
            }
            return t;
        }

        public void add(Vertex vertex){
            if(count == maxSize) return;
            nodes[++count] = vertex;
            int i = count;

            while (true) {
                if(i == 1) break;
                int j = i / 2;
                if(nodes[j].dist > nodes[i].dist){
                    Vertex v = nodes[i];
                    nodes[i] = nodes[j];
                    nodes[j] = v;
                }
                i = j;
            }
        }
        public void update(Vertex vertex){
            for (int i = 1; i < count; i++) {
                if(nodes[i].id == vertex.id){
                    nodes[i] = vertex;
                }
            }
        }
        public boolean isEmpty(){
            return this.count == 0;
        }

    }

    public void dijkstra(int s, int t) { // 从顶点s到顶点t的最短路径
        int[] predecessor = new int[this.v]; // 用来还原最短路径
        Vertex[] vertexes = new Vertex[this.v];
        for (int i = 0; i < this.v; i++) {
            vertexes[i] = new Vertex(i, Integer.MAX_VALUE);
        }
        PriorityQueue queue = new PriorityQueue(this.v); // 小顶堆
        boolean[] inqueue = new boolean[this.v]; // 标记是否进入过队列
        vertexes[s].dist = 0;
        queue.add(vertexes[s]);
        inqueue[s] = true;
        while (!queue.isEmpty()) {
            Vertex minVertex = queue.poll(); // 取堆顶元素并删除
            if (minVertex.id == t) break;
            for (int i = 0; i < adj[minVertex.id].size(); i++) {
                Edge e = adj[minVertex.id].get(i); // 取出一条minVetex相连的边
                Vertex nextVertex = vertexes[e.tid]; // minVertex-->nextVertex
                if (minVertex.dist + e.w < nextVertex.dist) { // 更新next的dist
                    nextVertex.dist = minVertex.dist + e.w;
                    predecessor[nextVertex.id] = minVertex.id;
                    if (inqueue[nextVertex.id] == true) {
                        queue.update(nextVertex); // 更新队列中的dist值
                    } else {
                        queue.add(nextVertex);
                        inqueue[nextVertex.id] = true;
                    }
                }
            }
        }
        // 输出最短路径
        System.out.println(s);
        print(s, t, predecessor);
    }

    private void print(int s, int t, int[] predecessor) {
        if (s == t) return;
        print(s, predecessor[t], predecessor);
        System.out.println("->" + t);
    }

    public static void main(String[] args) {
        Graph graph = new Graph(6);
        graph.addEdge(0,1,10);
        graph.addEdge(0,4,15);
        graph.addEdge(1,2,15);
        graph.addEdge(1,3,2);
        graph.addEdge(2,5,5);
        graph.addEdge(3,2,1);
        graph.addEdge(3,5,12);
        graph.addEdge(4,5,10);
        graph.dijkstra(0,5);
    }
}
