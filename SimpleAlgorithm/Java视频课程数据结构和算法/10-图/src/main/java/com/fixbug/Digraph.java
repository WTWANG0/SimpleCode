package com.fixbug;

import sun.awt.image.ImageWatched;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 实现不带权值的有向图，用邻接表组织
 */
public class Digraph
{
    private ArrayList<Vertic> adj; // 邻接表结构

    /**
     * 邻接表初始化
     */
    public Digraph() {
        this.adj = new ArrayList<>();
    }

    /**
     * 从文件中读取城市定点和边的信息
     * @param reader
     */
    public void read(BufferedReader reader) throws Exception{

        // 把adj的第0号位置占用，让顶点编号和adj数组的位置一一对应
        adj.add(new Vertic("", null));

        String city = null;
        String[] vertic = null;
        for(;;){
            city = reader.readLine();
            if(city == null){
                break;
            }

            LinkedList<Integer> list = new LinkedList<>();
            vertic = reader.readLine().split(",");
            for (int i = 0; i < vertic.length; i++) {
                list.add(Integer.parseInt(vertic[i].trim()));
            }

            adj.add(new Vertic(city, list));
        }
    }

    /**
     * 打印有向图的邻接表结构
     */
    public void showAdj(){
        for(int i=1; i < adj.size(); ++i){
            System.out.print(adj.get(i).data + " : ");
            for (Integer integer : adj.get(i).adjList) {
                System.out.print(integer + " ");
            }
            System.out.println();
        }
    }

    /**
     * 从指定的start顶点开始，深度遍历有向图
     * @param start
     */
    public void dfs(int start){
        System.out.println("深度优先遍历:");
        boolean[] visited = new boolean[adj.size()];
        dfs(start, visited);
    }

    private void dfs(int start, boolean[] visited) {
        // 表示当前顶点已经被访问过
        if(visited[start]){
            return;
        }

        // 访问当前顶点
        System.out.print(adj.get(start).data + " ");
        visited[start] = true;

        // 深度遍历，访问其它顶点信息
        for(int i=0; i < adj.get(start).adjList.size(); ++i){
            dfs(adj.get(start).adjList.get(i), visited);
        }
    }

    /**
     * 从指定的start顶点开始，进行广度优先遍历
     * @param start
     */
    public void bfs(int start){
        System.out.println("广度优先遍历:");
        boolean[] visited = new boolean[adj.size()];
        LinkedList<Vertic> queue = new LinkedList<>();

        // 把入口顶点信息入队列
        queue.offer(adj.get(start));
        visited[start] = true;

        while (!queue.isEmpty()){
            Vertic front = queue.poll();
            System.out.print(front.data + " ");

            LinkedList<Integer> list = front.adjList;
            for (int i = 0; i < list.size(); i++) {
                // 表示front.adjList.get(i)顶点没有被访问过
                if(!visited[list.get(i)]){
                    queue.offer(adj.get(list.get(i)));
                    visited[list.get(i)] = true;
                }
            }
        }
    }

    /**
     * 找start到end的最短路径信息
     * @param start
     * @param end
     */
    public void shortestPath(int start, int end){
        int[] path = new int[adj.size()];
        boolean[] visited = new boolean[adj.size()];
        LinkedList<Integer> queue = new LinkedList<>();
        boolean flag = false;

        // 把入口顶点信息入队列
        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()){
            int front = queue.poll();
            if(front == end){
                flag = true;
                break;
            }

            LinkedList<Integer> list = adj.get(front).adjList;
            for (int i = 0; i < list.size(); i++) {
                // 表示front.adjList.get(i)顶点没有被访问过
                if(!visited[list.get(i)]){
                    queue.offer(list.get(i));
                    // 记录当前顶点的前驱顶点信息
                    path[list.get(i)] = front;
                    visited[list.get(i)] = true;
                }
            }
        }

        if(!flag){
            System.out.println(adj.get(start).data + " => "
             + adj.get(end).data + " 不存在路径!");
            return;
        }

        printShortestPath(start, end, path);
    }

    /**
     * 打印start到end的最短的路径信息
     * @param start
     * @param end
     * @param path
     */
    private void printShortestPath(int start, int end, int[] path) {
        if(start == end){
            System.out.print(adj.get(end).data + " -> ");
            return;
        }

        printShortestPath(start, path[end], path);
        System.out.print(adj.get(end).data + " -> ");
    }

    /**
     * 定义有向图的定点类型
     */
    static class Vertic{

        public Vertic(String data, LinkedList<Integer> adjList) {
            this.data = data;
            this.adjList = adjList;
        }

        String data; // 表示邻接表数组的数据(城市名称)
        LinkedList<Integer> adjList; // 描述定点出度的链表结构
    }

    public static void main(String[] args) throws Exception{
        BufferedReader reader = new BufferedReader(new FileReader("citys.txt"));
        Digraph dig = new Digraph();
        dig.read(reader);
        dig.showAdj();
        reader.close();

        dig.dfs(1);
        System.out.println();
        dig.bfs(1);

        System.out.println();
        dig.shortestPath(1, 9);
    }
}