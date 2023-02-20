package com.fixbug.maze;

/**
 * 描述: 迷宫路径搜索问题的常量定义
 *
 * @Author shilei
 * @Date 2019/8/30
 */
public interface Constant {
    // 下面定义四个方向
    int RIGHT = 0;
    int DOWN = 1;
    int LEFT = 2;
    int UP = 3;
    int NUMBER = 4;

    // 节点方向的行走状态值
    int OK = 5;
    int NO = 6;
}
