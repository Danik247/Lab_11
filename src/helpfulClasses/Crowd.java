package helpfulClasses;

import persons.Person;


public class Crowd {
    public final int count;
    public final Person content;
    public Crowd(Person content, int count){
        this.content = content;
        this.count = count;
    }
    public void play(Person pers){
        System.out.println("толпа собралась кидать в тебя мячи, приготовься\n...");
        boolean win = true;
        for (var i=0; i <= count;i++){
            if (!pers.dodge(content)){
                win = false;
            }
        }
        System.out.println(win ? "поздравляю!!! ты победил\n..." : "хаха ты проиграл\n...");
    }
}
