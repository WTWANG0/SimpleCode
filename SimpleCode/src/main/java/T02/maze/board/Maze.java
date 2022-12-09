package T02.maze.board;

import T02.maze.Constant;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * 广度优先遍历
 * 队列
 */
public class Maze {
    //所有路径的节点信息
    private MazeNode[][] maze;
    //路径节点，
    private LinkedList<MazeNode> nodeQue;
    //
    private MazeNode[][] nodeRecord;

    private int row;

    private int col;

    public Maze(int row, int col) {
        this.row = row;
        this.col = col;
        this.maze = new MazeNode[this.row][this.col];
        this.nodeRecord = new MazeNode[this.row][this.col];
        this.nodeQue = new LinkedList<>();
    }

    private void setMaze(int data, int i, int j) {
        this.maze[i][j] = new MazeNode(data, i, j);
    }

    private void initMaze() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                //节点为1，不用调整【0表示可以行走，1表示不能行走】
                if (maze[i][j].data == 1) {
                    continue;
                }

                //RIGHT
                if (j < this.col - 1 && this.maze[i][j + 1].data == 0) {
                    this.maze[i][j].state[Constant.RIGHT] = Constant.OK;
                }

                //UP
                if (i < this.row - 1 && this.maze[i + 1][j].data == 0) {
                    this.maze[i][j].state[Constant.DOWN] = Constant.OK;
                }

                //LEFT
                if (j > 0 && this.maze[i][j - 1].data == 0) {
                    this.maze[i][j].state[Constant.LEFT] = Constant.OK;
                }

                //DOWN
                if (i > 0 && this.maze[i - 1][j].data == 0) {
                    this.maze[i][j].state[Constant.UP] = Constant.OK;
                }
            }
        }

    }

    public boolean check(int x, int y) {
        return x == this.row - 1 && y == this.col - 1;
    }

    private void findMaze() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                if (maze[0][0].data == 1) {
                    return;
                }

                this.nodeQue.offer(this.maze[0][0]);

                while (!this.nodeQue.isEmpty()) {
                    MazeNode front = this.nodeQue.peek();
                    int x = front.x;
                    int y = front.y;

                    //right
                    if (this.maze[x][y].state[Constant.RIGHT] == Constant.OK) {
                        this.maze[x][y].state[Constant.RIGHT] = Constant.NO;
                        this.maze[x][y + 1].state[Constant.LEFT] = Constant.NO;
                        this.nodeQue.offer(this.maze[x][y + 1]);
                        this.nodeRecord[x][y + 1] = this.maze[x][y];

                        if (check(x, y + 1)) {
                            return;
                        }
                    }

                    //up
                    if (this.maze[x][y].state[Constant.DOWN] == Constant.OK) {
                        this.maze[x][y].state[Constant.DOWN] = Constant.NO;
                        this.maze[x + 1][y].state[Constant.UP] = Constant.NO;
                        this.nodeQue.offer(this.maze[x + 1][y]);
                        this.nodeRecord[x + 1][y] = this.maze[x][y];

                        if (check(x + 1, y)) {
                            return;
                        }
                    }

                    //
                    if (this.maze[x][y].state[Constant.LEFT] == Constant.OK) {
                        this.maze[x][y].state[Constant.LEFT] = Constant.NO;
                        this.maze[x][y - 1].state[Constant.RIGHT] = Constant.NO;
                        this.nodeQue.offer(this.maze[x][y - 1]);
                        this.nodeRecord[x][y - 1] = this.maze[x][y];

                        if (check(x, y - 1)) {
                            return;
                        }
                    }

                    //
                    if (this.maze[x][y].state[Constant.UP] == Constant.OK) {
                        this.maze[x][y].state[Constant.UP] = Constant.NO;
                        this.maze[x - 1][y].state[Constant.DOWN] = Constant.NO;
                        this.nodeQue.offer(this.maze[x - 1][y]);
                        this.nodeRecord[x - 1][y] = this.maze[x][y];

                        if (check(x - 1, y)) {
                            return;
                        }
                    }

                    // 队头元素的所有方向都处理过了，直接出队
                    this.nodeQue.poll();//弹出

                }
            }
        }


    }

    private void showMaze() {
        if (this.nodeQue.isEmpty()) {
            System.out.println("迷宫不存在有效路径!");
        } else {
            // 把最终的迷宫路径节点的值改成*
            int x = this.row - 1;
            int y = this.col - 1;
            for (; ; ) {
                this.maze[x][y].data = '*';
                if (x == 0 && y == 0) {
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
                    if (this.maze[i][j].data == '*') {
                        System.out.print('*' + " ");
                    } else {
                        System.out.print(this.maze[i][j].data + " ");
                    }
                }
                System.out.println();
            }
        }
    }


    //MazeNode
    static class MazeNode {
        int data;
        int x;
        int y;
        int[] state;//四个方向的状态

        public MazeNode(int data, int x, int y) {
            this.data = data;
            this.x = x;
            this.y = y;
            this.state = new int[Constant.NUMBER];
            for (int i = 0; i < Constant.NUMBER; i++) {
                this.state[i] = Constant.NO;//初始值四个状态都是NO
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
