package com.fixbug.maze.broad;

import com.fixbug.maze.Constant;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * 描述: 广度优先遍历搜索迷宫最短的路径 - 队列的应用
 *
 * @Author shilei
 * @Date 2019/8/30
 */
public class Maze {
    // 存储迷宫的所有路径节点信息
    private MazeNode[][] maze;
    // 存储迷宫的路径节点，借助该队列，以左上角入口节点为中心，层层向外扩张找最短的路径节点
    private LinkedList<MazeNode> nodeQue;
    // 用来记录广度优先搜索过程中，节点的前后行走关系
    private MazeNode[][] nodeRecord;
    // 迷宫的行数
    int row;
    // 迷宫的列数
    int col;

    /**
     * 迷宫初始化操作
     * @param row
     * @param col
     */
    public Maze(int row, int col){
        this.row = row;
        this.col = col;
        this.maze = new MazeNode[this.row][this.col];
        this.nodeRecord = new MazeNode[this.row][this.col];
        this.nodeQue = new LinkedList<>();
    }

    /**
     * 生成迷宫路径的节点信息
     * @param data
     * @param i
     * @param j
     */
    private void setMaze(int data, int i, int j) {
        this.maze[i][j] = new MazeNode(data, i, j);
    }

    /**
     * 调整迷宫所有节点的方向行走状态
     */
    private void initMaze() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                // 节点为1，不用调整
                if(this.maze[i][j].data == 1){
                    continue;
                }

                // 当前节点的右边是0，设置当前节点的右方向可以行走
                if(j < this.col-1 && this.maze[i][j+1].data == 0){
                    this.maze[i][j].state[Constant.RIGHT] = Constant.OK;
                }

                if(i < this.row-1 && this.maze[i+1][j].data == 0){
                    this.maze[i][j].state[Constant.DOWN] = Constant.OK;
                }

                if(j > 0 && this.maze[i][j-1].data == 0){
                    this.maze[i][j].state[Constant.LEFT] = Constant.OK;
                }

                if(i > 0 && this.maze[i-1][j].data == 0){
                    this.maze[i][j].state[Constant.UP] = Constant.OK;
                }
            }
        }
    }

    /**
     * 找迷宫路径
     */
    private void findMaze() {
        // 不存在迷宫的入口节点
        if(this.maze[0][0].data == 1){
            return;
        }

        // 把入口节点入队
        this.nodeQue.offer(this.maze[0][0]);

        while(!this.nodeQue.isEmpty()){
            // 查看队头元素
            MazeNode front = this.nodeQue.peek();
            int x = front.x;
            int y = front.y;

            // 当前节点右方向
            if(this.maze[x][y].state[Constant.RIGHT] == Constant.OK){
                this.maze[x][y].state[Constant.RIGHT] = Constant.NO;
                this.maze[x][y+1].state[Constant.LEFT] = Constant.NO;
                // 把右边的节点入队
                this.nodeQue.offer(this.maze[x][y+1]);
                // 记录节点行走信息
                this.nodeRecord[x][y+1] = this.maze[x][y];
                // 检查是否找到右下角的出口节点
                if(check(x, y+1)){
                    return;
                }
            }

            // 当前节点下方向
            if(this.maze[x][y].state[Constant.DOWN] == Constant.OK){
                this.maze[x][y].state[Constant.DOWN] = Constant.NO;
                this.maze[x+1][y].state[Constant.UP] = Constant.NO;
                // 把下边的节点入队
                this.nodeQue.offer(this.maze[x+1][y]);
                // 记录节点行走信息
                this.nodeRecord[x+1][y] = this.maze[x][y];
                // 检查是否找到右下角的出口节点
                if(check(x+1, y)){
                    return;
                }
            }

            // 当前节点左方向
            if(this.maze[x][y].state[Constant.LEFT] == Constant.OK){
                this.maze[x][y].state[Constant.LEFT] = Constant.NO;
                this.maze[x][y-1].state[Constant.RIGHT] = Constant.NO;
                // 把左边的节点入队
                this.nodeQue.offer(this.maze[x][y-1]);
                // 记录节点行走信息
                this.nodeRecord[x][y-1] = this.maze[x][y];
                // 检查是否找到右下角的出口节点
                if(check(x, y-1)){
                    return;
                }
            }

            // 当前节点上方向
            if(this.maze[x][y].state[Constant.UP] == Constant.OK){
                this.maze[x][y].state[Constant.UP] = Constant.NO;
                this.maze[x-1][y].state[Constant.DOWN] = Constant.NO;
                // 把上边的节点入队
                this.nodeQue.offer(this.maze[x-1][y]);
                // 记录节点行走信息
                this.nodeRecord[x-1][y] = this.maze[x][y];
                // 检查是否找到右下角的出口节点
                if(check(x-1, y)){
                    return;
                }
            }

            // 队头元素的所有方向都处理过了，直接出队
            this.nodeQue.poll();
        }
    }

    /**
     * 检查坐标是否是迷宫路径的出口节点的坐标
     * @param x
     * @param y
     * @return
     */
    private boolean check(int x, int y) {
        return x == this.row-1 && y == this.col-1;
    }

    /**
     * 打印迷宫路径的搜索结果
     */
    private void showMaze() {
        if(this.nodeQue.isEmpty()){
            System.out.println("迷宫不存在有效路径!");
        } else {
            // 把最终的迷宫路径节点的值改成*
            int x = this.row-1;
            int y = this.col-1;
            for(;;){
                this.maze[x][y].data = '*';
                if(x == 0 && y == 0){
                    break;
                }
                // node -> (x,y)
                MazeNode node = this.nodeRecord[x][y];
                x = node.x;
                y = node.y;
            }

            // 输出迷宫路径
            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < this.col; j++) {
                    if(this.maze[i][j].data == '*'){
                        System.out.print('*' + " ");
                    } else {
                        System.out.print(this.maze[i][j].data + " ");
                    }
                }
                System.out.println();
            }
        }
    }

    /**
     * 迷宫的节点类型定义
     */
    static class MazeNode{
        int data; // 存储节点的值
        int x; // 节点的x坐标
        int y; // 节点的y坐标
        int[] state; // 存储四个方向的状态

        public MazeNode(int data, int i, int j) {
            this.data = data;
            this.x = i;
            this.y = j;
            this.state = new int[Constant.NUMBER];
            // 给节点的四个方向初始化成不能行走状态
            for (int k = 0; k < Constant.NUMBER; k++) {
                this.state[k] = Constant.NO;
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("输入迷宫的行列数(如5 5):");
        int row, col;
        row = in.nextInt();
        col = in.nextInt();

        Maze maze = new Maze(row, col); // 创建迷宫对象

        System.out.println("输入具体的迷宫路径信息(0表示可以行走，1表示不能行走):");
        int data = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                data = in.nextInt(); // 一个个读取输入的迷宫路径节点
                maze.setMaze(data, i, j);
            }
        }

        // 初始化迷宫所有路径信息
        maze.initMaze();
        // 寻找迷宫路径
        maze.findMaze();
        // 打印结果
        maze.showMaze();
    }
}
