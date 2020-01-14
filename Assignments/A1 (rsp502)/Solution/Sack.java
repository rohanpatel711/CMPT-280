enum Grain{
    WHEAT, BARLEY, OATS,RYE,OTHER
}

public class Sack {
    protected Grain type;
    protected double weight;
    public Sack(Grain type, double weight){
        super();
        this.type=type;
        this.weight=weight;
    }
    public Grain getType(){
        return type;
    }
    public void setType(Grain type){
        this.type=type;
    }
    public double getWeight(){
        return weight;
    }
    public void setWeight(double weight){
        this.weight=weight;
    }

    public String toString(){
        return "(" + type.toString() + "," + weight + ")";
    }
}
