

import acmx.export.java.util.*;


/**
 * 贪吃蛇类
 */
public class Snake {
    //蛇身数组，location[0]表示蛇头
    private List location = new LinkedList();

    //初始化
    public Snake(int x,int y) {
        location.add(new Location(x,y));
    }

    /**
     * 得到蛇头坐标
     *
     */
    public Location getHead(){
        return (Location)location.get(0);
    }

    public List getLocation() {
        return location;
    }

    public void setLocation(List location) {
        this.location = location;
    }

    /**
     * 删除蛇尾
     */
    public void deleteTail(){
        location.remove(location.size()-1);
    }

    /**
     * 添加蛇头
     */
    public void addHead(Location l){
        location.add(0,l);
    }

    /**
     * 吃到奖励
     * @param l  奖励的坐标
     */
    public void eat(Location l){
        location.add(0,l);
    }
}
