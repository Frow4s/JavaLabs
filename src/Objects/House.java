package Objects;
import Enums.Flowers;

public class House {
    public Integer floors;
    public String looking;
    public House(Integer floors,String looking){
        this.floors=floors;
        this.looking=looking;
    }
    public String getLooking(){
        return(" c "+this.floors.toString()+" этажами "+this.looking+" с клумбой из "+Flowers.reseda.getName()+","+Flowers.nasturtiums.getName()+","+Flowers.pansies.getName()+","+Flowers.daisies.getName()+","+Flowers.asters.getName()+".");
    }


}
