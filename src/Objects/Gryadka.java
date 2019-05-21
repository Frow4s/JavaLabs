package Objects;


import Interfaces.ForGryadka;
import java.time.LocalDateTime;

import java.util.Date;


public class Gryadka implements ForGryadka {
    private String type;
    private int count;
    private LocalDateTime CreateTime=LocalDateTime.now();

    public Gryadka(int count, String type){
        this.type=type;
        this.count=count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /*public void setName(String name) {
        this.name = name;
    }*/

    public String getName() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getCreateTime(){return CreateTime;}

    public void setCreateTime(LocalDateTime date){CreateTime=date;}




}
