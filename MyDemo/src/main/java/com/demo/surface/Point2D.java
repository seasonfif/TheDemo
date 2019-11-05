package com.demo.surface;

public class Point2D {

    private int x;
    private int y;

    public Point2D(){
        x = y = 0;
    }

    public Point2D(int v){
        x = y = v;
    }

    public Point2D(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    /**
     * Allows addition of a passed point to this point
     *
     * @param point
     */
    public void add(Point2D point){
        x += point.x;
        y += point.y;
    }

    /**
     * Allows subtraction of a passed point to this point
     * @param point
     */
    public void sub(Point2D point){
        x -= point.x;
        y -= point.y;
    }

}

