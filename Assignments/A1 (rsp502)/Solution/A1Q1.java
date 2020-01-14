import lib280.list.LinkedList280;

import java.util.Random;

public class A1Q1 {
public static Sack[] generatePlunder(int amount){
    Random generator = new Random();
    Sack grain[] =new Sack[amount];
    for(int i=0;i<amount;i++){
        grain[i]= new Sack( Grain.values()[generator.nextInt(Grain.values().length)] , generator.nextDouble() * 100);
    }
    return grain;
}
public static void main(String args[]){
    Sack sacks[]= generatePlunder(5);
    LinkedList280<Sack> holds[] =new LinkedList280[Grain.values().length];
    for(int i=0; i<holds.length;i++){
        holds[i] = new LinkedList280<Sack>();
    }
    for(int j=0;j<sacks.length; j++){
        holds[sacks[j].type.ordinal()].insert(sacks[j]);
    }
    for(int k=0; k<holds.length;k++){
        double sum=0;
        if(!holds[k].isEmpty()){
            holds[k].goFirst();
            while(holds[k].itemExists()){
                sum = holds[k].item().getWeight();
                holds[k].goForth();
            }
        }
        System.out.println("Jack plundered " + sum + " pounds of " + Grain.values()[k]);
    }
}
}

