package T02.maze.deep;

import T02.maze.Constant;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * 描述: 迷宫问题
 * 程序运行以后，步骤如下：
 * 1. 提示用户输入迷宫的行列数(如：5 5): 5 5
 * 2. 提示用户输入具体的迷宫路径信息(0表示可以行走，1表示不能行走)：
 * 0 0 1 1 1 1
 * 1 0 0 0 1 1
 * 1 0 1 1 1 1
 * 1 0 1 1 1 1
 * 1 0 1 1 1 1
 * 1 0 0 0 0 0
 * 找一条从左上角0进到右下角0出的一条路径信息，输出如下：
 * 如果路径不存在，输出信息
 * 如果路径存在，再次打印迷宫路径信息，把迷宫的正确路径用字符*打印出来
 * * * 1 1 1 1
 * 1 * 0 0 1 1
 * 1 * 1 1 1 1
 * 1 * 1 1 1 1
 * 1 * 1 1 1 1
 * 1 * * * * *
 */
public class Maze {

    // 存储迷宫的所有路径节点信息
    private MazeNode[][] maze;
    // 存储迷宫的路径节点
    private LinkedList<MazeNode> nodeStack;
    // 迷宫的行数
    int row;
    // 迷宫的列数
    int col;


    /**
     * 迷宫初始化操作
     *
     * @param row
     * @param col
     */
    public Maze(int row, int col) {
        this.row = row;
        this.col = col;
        this.maze = new MazeNode[this.row][this.col];
        this.nodeStack = new LinkedList<>();
    }

    /**
     * 生成迷宫路径的节点信息
     *
     * @param data
     * @param i
     * @param j
     */
    private void setMaze(int data, int i, int j) {
        this.maze[i][j] = new MazeNode(data, i, j);
    }

    private void initMaze() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                // 节点为1，不用调整
                if (maze[i][j].data == 1) {
                    continue;
                }

                if (j < this.col - 1 && this.maze[i][j + 1].data == 0) {
                    this.maze[i][j].state[Constant.RIGHT] = Constant.OK;
                }

                if (i < this.row - 1 && this.maze[i + 1][j].data == 0) {
                    this.maze[i][j].state[Constant.DOWN] = Constant.OK;
                }

                if (j > 0 && this.maze[i][j - 1].data == 0) {
                    this.maze[i][j].state[Constant.LEFT] = Constant.OK;
                }

                if (i > 0 && this.maze[i - 1][j].data == 0) {
                    this.maze[i][j].state[Constant.UP] = Constant.OK;
                }
            }
        }
    }

    public void findMaze() {
        // 不存在迷宫的入口节点
        if (this.maze[0][0].data == 1) {
            return;
        }

        // 把入口节点入栈
        this.nodeStack.push(this.maze[0][0]);

        while (!this.nodeStack.isEmpty()) {
            MazeNode top = this.nodeStack.peek();
            int x = top.x;
            int y = top.y;

            if (x == this.row - 1 && y == this.col - 1) {
                // 找到迷宫的出口节点，返回
                return;
            }

            if (this.maze[x][y].state[Constant.RIGHT] == Constant.OK) {
                this.maze[x][y].state[Constant.RIGHT] = Constant.NO;
                this.maze[x][y + 1].state[Constant.LEFT] = Constant.NO;
                this.nodeStack.push(this.maze[x][y + 1]);
                continue;
            }

            if (this.maze[x][y].state[Constant.DOWN] == Constant.OK) {
                this.maze[x][y].state[Constant.DOWN] = Constant.NO;
                this.maze[x + 1][y].state[Constant.UP] = Constant.NO;
                this.nodeStack.push(this.maze[x + 1][y]);
                continue;
            }


            if (this.maze[x][y].state[Constant.LEFT] == Constant.OK) {
                this.maze[x][y].state[Constant.LEFT] = Constant.NO;
                this.maze[x][y - 1].state[Constant.RIGHT] = Constant.NO;
                this.nodeStack.push(this.maze[x][y - 1]);
                continue;
            }

            if (this.maze[x][y].state[Constant.UP] == Constant.OK) {
                this.maze[x][y].state[Constant.UP] = Constant.NO;
                this.maze[x - 1][y].state[Constant.DOWN] = Constant.NO;
                this.nodeStack.push(this.maze[x - 1][y]);
                continue;
            }

            this.nodeStack.pop();//弹出
        }
    }


    private void showMaze() {
        if (this.nodeStack.isEmpty()) {
            System.out.println("迷宫不存在有效路径!");
            return;
        }

        while (!this.nodeStack.isEmpty()) {
            this.nodeStack.pop().data = '*';
        }

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                if (this.maze[i][j].data == '*') {
                    System.out.print('*' + " ");
                } else {
                    System.out.print(this.maze[i][j].data + " ");
                }
            }
            System.out.println();
        }

    }


    /**
     * 迷宫的节点类型定义
     */
    static class MazeNode {
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
