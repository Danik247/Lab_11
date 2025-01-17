package helpfulClasses;

import interfaces.Throwing;
import persons.Person;

import static java.lang.StrictMath.random;


public class Crowd {
    public final int count;
    String[] names = {"Незнайка", "Знайка", "Пончик", "Сиропчик", "Винтик",
            "Шпунтик", "Тюбик", "Пилюлькин", "Стекляшкин", "Гуся", "Мисюсь",
            "Торопыжка", "Знайка", "Пестренький", "Селедочка", "Коротышка-капитан",
            "Пончик", "Сиропчик", "Кнопочка", "Фонарик", "Ромашка", "Авоська", "Кваксик", "Пулька"};
    public Crowd(int count){
        this.count = count;
    }
    public void play(Person player) {
        System.out.println("толпа собралась кидать в тебя мячи, приготовься\n...");
        boolean win = true;
        Throwing attacker = (Person deffender, String name) -> {
            boolean success = deffender.dodge();
            System.out.println(success ? deffender.name + " увернулся" : name + " попал в тебя");
            return success;
        };
        for (var i=0; i <= count;i++){
            win = attacker.attack(player, names[(int) (random() * names.length - 1)]);
            if (!win){
                break;
            }
        }
        System.out.println(win ? "поздравляю!!! ты победил\n..." : "хаха ты проиграл\n...");
    }
}
