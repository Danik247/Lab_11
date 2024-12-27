package persons;

import helpfulClasses.Console;

public class Man extends Person {

    public Man(String name, float dexterity, float speed, float stamina, float eloquence) {
        super(name, dexterity, speed, stamina, eloquence, 3, 5);
    }

    @Override
    void boringStart() {
        Console.describe("    " + name + ": только ловкость?!\n    XXXX: А ну тихо, мы начинаем");
    }


    public Man(String name, float dexterity) {
        float stamina = dexterity * 4;
        float eloquence = dexterity * 1;
        this(name, dexterity, dexterity, stamina, eloquence);
        boringStart();
    }
}
