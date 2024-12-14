package rec;

import persons.person;

import java.util.ArrayList;

public record crowd(person cont,int count) {
    public static ArrayList<person> ent = new ArrayList<>();
    public crowd{
        for (var i=0; i <= count;i++){
            ent.add(cont);
        }
    }
    public void play(person pers){
        boolean win = true;
        for (var i=0; i <= count;i++){
            if (!pers.dodge(ent.get(i))){
                win = false;
            }
        }
        System.out.println(win ? "поздравляю!!! ты победил\n..." : "хаха ты проиграл\n...");
    }
    @Override
    public String toString(){
        String total = ent + " = [";
        for (var i=0; i <= count;i++){
            total = total + ent.get(i).toString();
        }
        return total += "]";
    }
}
