
import acmx.export.java.util.List;
import acmx.export.javax.swing.*;

import java.awt.*;
import java.awt.event.*;


/**
 * 主界面
 */
public class GridComponent extends JComponent implements KeyListener {
    //代表没有按键按下
    private static final int NO_KEY = -1;
    //设置奖励，蛇身颜色,背景色,蛇分割线颜色
    public static final Color HEAD_COLOR = Color.GREEN;
    public static final Color BODY_COLOR = Color.PINK;
    public static final Color WIN_COLOR = Color.RED;
    public static final Color LINE_COLOR = Color.BLACK;
    public static final Color BACKGROUND_COLOR = Color.WHITE;

    //捕获的按键
    private int lastKeyPressed;

    //设置网格
    private Cell[][] cells;

    private JFrame frame;

    //初始化
    public GridComponent(int numRows, int numCols) {
        init(numRows, numCols);
    }

    //初始化
    private void init(int numRows, int numCols) {
        int cellSize = initCell(numRows, numCols);

        //设置尺寸
        setPreferredSize(new Dimension(cellSize * numCols, cellSize * numRows));
        frame = new JFrame();
        frame.addKeyListener(this);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);

        lastKeyPressed = NO_KEY;
    }

    //初始化网格数组
    private int initCell(int numRows, int numCols) {
        cells = new Cell[numRows][numCols];

        for (int row = 0; row < numRows; row++)
            for (int col = 0; col < numCols; col++) {
                cells[row][col] = new Cell();
                cells[row][col].setColor(BACKGROUND_COLOR);
            }

        //initial cellSize for determining Window size
        return Math.max(Math.min(700 / numRows, 700 / numCols), 1);


    }

    //得到行数
    public int getNumRows() {
        return cells.length;
    }

    //得到列数
    public int getNumCols() {
        return cells[0].length;
    }


    //得到网格的大小
    private int getCellSize() {
        int cellWidth = getWidth() / getNumCols();
        int cellHeight = getHeight() / getNumRows();
        return Math.min(cellWidth, cellHeight);
    }


    /**
     * 画蛇和奖励
     */
    public void drawSnake(List snake, Location treature){
        Location head= (Location)snake.get(0);
        initCell(getNumRows(),getNumCols());
        for(int i=0;i<snake.size();i++){
            cells[((Location)snake.get(i)).getRow()-1][((Location)snake.get(i)).getCol()-1].setColor(BODY_COLOR);
        }
        cells[head.getRow()-1][head.getCol()-1].setColor(HEAD_COLOR);
        cells[treature.getRow()-1][treature.getCol()-1].setColor(WIN_COLOR);
        repaint();
    }

    //绘制界面
    public void paintComponent(Graphics g) {
        for (int row = 0; row < getNumRows(); row++) {
            for (int col = 0; col < getNumCols(); col++) {

                int cellSize = getCellSize();

                int x = col * cellSize;
                int y = row * cellSize;

                drawCell(g, x, y, new Location(row, col), cellSize);
            }
        }
    }

    /**
     * 画单元格
     *
     * @param g
     * @param x
     * @param y
     * @param loc
     * @param cellSize
     */
    private void drawCell(Graphics g, int x, int y, Location loc, int cellSize) {

        Cell cell = cells[loc.getRow()][loc.getCol()];

        cell.draw(g, x, y, cellSize);
    }

    public void setTitle(String str){
        frame.setTitle(str);
    }

    public void keyTyped(KeyEvent keyEvent) {

    }

    /**
     * 捕获按键事件
     */
    public void keyPressed(KeyEvent e) {
        System.out.println("get"+e.getKeyCode());
        lastKeyPressed = e.getKeyCode();
    }


    public void keyReleased(KeyEvent keyEvent) {

    }

    /**
     * 返回上次的按键
     *
     */
    public int checkLastKeyPressed() {
        int key = lastKeyPressed;
        lastKeyPressed = NO_KEY;
        return key;
    }

    //内部类，表示每个格子的属性
    public class Cell {
        private Color color = null;

        public void setColor(Color c) {
            color = c;
        }

        public Color getColor() {
            return color;
        }

        public void draw(Graphics g, int x, int y, int cellSize) {

            if (color != null)
                g.setColor(color);

            g.fillRect(x, y, cellSize, cellSize);
        }

    }

}
