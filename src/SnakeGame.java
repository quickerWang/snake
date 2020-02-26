


import acm.util.*;
import acmx.export.java.util.*;

import javax.swing.*;
import java.awt.event.*;

public class SnakeGame {
    //主网格
    private GridComponent gridComponent;
    //蛇类
    private Snake snake;
    //现在蛇的运行方向
    private Direction direction;
    //分数
    private int score=0;
    //是否结束
    private boolean Gameover;
    //当前奖励
    private Location treature;
    //行数
    private int numRows = 30;
    //列数
    private int numCols = 30;
    //速度
    private int speed = 1;

    public  SnakeGame(int speed){
        this.speed=speed;
        init();
    }

    /**
     * 游戏初始化
     */
    public void init(){
        snake = new Snake(3,3);
        direction=Direction.RIGHT;
        gridComponent = new GridComponent(numRows,numCols);
        treature=null;
        score=0;
        Gameover=false;
        updateTitle();
    }

    /**
     * 游戏开始循环运行
     */
    public void play() {
        //更新奖励
        this.updateTreature();
        gridComponent.drawSnake(snake.getLocation(),treature);
        while(!Gameover){
            //睡眠1s，防止蛇走的太快了
            try {
                Thread.sleep(1000/speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //响应按键
            this.handleKeyPress();
            //按照方向移动
            this.handleDerection();
            //更新奖励
            this.updateTreature();
            //重画
            gridComponent.drawSnake(snake.getLocation(),treature);
            //更新标题
            this.updateTitle();
        }
        //死亡之后的选择框
        int selection = JOptionPane.showConfirmDialog(null,"死亡，是否重来一局？");
        if(selection==JOptionPane.OK_OPTION){
            init();
            play();
        }else{
            System.exit(0);
        }
    }

    public void handleKeyPress(){
        if(gridComponent==null)
            System.out.println("good");
        int var1 = gridComponent.checkLastKeyPressed();
        if(var1== KeyEvent.VK_DOWN) {
            direction=Direction.DOWN;
        }
        if(var1== KeyEvent.VK_UP) {
            direction=Direction.UP;
        }
        if(var1== KeyEvent.VK_LEFT) {
            direction=Direction.LEFT;
        }
        if(var1== KeyEvent.VK_RIGHT) {
            direction=Direction.RIGHT;
        }
    }

    //根据方向行事
    public void handleDerection(){
        Location head = snake.getHead();
        //向下走
        if(direction== Direction.DOWN){
            //撞墙
            if(head.getRow()==numRows){
                Gameover=true;
                return;
            }
            //得到奖励
            else if(head.getCol()==treature.getCol()&&head.getRow()==treature.getRow()-1){
                snake.addHead(treature);
                score+=1;
                treature=null;
            }
            //正常运行
            else{
                snake.addHead(new Location(head.getRow()+1,head.getCol()));
                snake.deleteTail();
            }
        }
        //向上走
        else if(direction== Direction.UP){
            //撞墙
            if(head.getRow()==1){
                Gameover=true;
                return;
            }
            //得到奖励
            else if(head.getCol()==treature.getCol()&&head.getRow()==treature.getRow()+1){
                snake.addHead(treature);
                score+=1;
                treature=null;
            }
            //正常运行
            else{
                snake.addHead(new Location(head.getRow()-1,head.getCol()));
                snake.deleteTail();
            }
        }
        //向左走
        else if(direction== Direction.LEFT){
            //撞墙
            if(head.getCol()==1){
                Gameover=true;
                return;
            }
            //得到奖励
            else if(head.getRow()==treature.getRow()&&head.getCol()==treature.getCol()+1){
                snake.addHead(treature);
                score+=1;
                treature=null;
            }
            //正常运行
            else{
                snake.addHead(new Location(head.getRow(),head.getCol()-1));
                snake.deleteTail();
            }
        }
        //向右走
        else if(direction== Direction.RIGHT){
            //撞墙
            if(head.getCol()==numCols){
                Gameover=true;
                return;
            }
            //得到奖励
            else if(head.getRow()==treature.getRow()&&head.getCol()==treature.getCol()-1){
                snake.addHead(treature);
                score+=1;
                treature=null;
            }
            //正常运行
            else{
                snake.addHead(new Location(head.getRow(),head.getCol()+1));
                snake.deleteTail();
            }
        }
    }


    public void updateTreature(){
        //如果奖励已经被吃掉，更新奖励
        RandomGenerator random = new RandomGenerator();
        while (treature==null){
            int x = random.nextInt(numRows)+1;
            int y = random.nextInt(numRows)+1;
            //如果和蛇身相撞，寻找下一个
            List list = snake.getLocation();
            int number=0;
            for(int i=0;i<list.size();i++){
                Location l = (Location)list.get(i);
                if( l.getCol()==y&&l.getRow()==x){
                    number++;
                }
            }
            if(number==0) {
                treature = new Location(x, y);
            }
        }
    }

    public void updateTitle(){
        gridComponent.setTitle("贪吃蛇游戏  score:"+score);
    }
}
