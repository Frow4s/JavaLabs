package Enums;

public enum  Flowers {
    daisies("маргаритки"),
    pansies("анютины глазки"),
    nasturtiums("настурции"),
    reseda("лунная резеда"),
    asters("астры") ;

    String name;

    Flowers(String name){
        this.name=name;
    }
    public String getName(){return name;}
}