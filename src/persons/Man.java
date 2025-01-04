package persons;

import helpfulClasses.Console;

public class Man extends Person {

    public Man(String name, float dexterity, float speed, float stamina, float eloquence) {
        super(name, dexterity, speed, stamina, eloquence, 3, 5);
    }
    public Man(String name, float dexterity) {
        this(name, dexterity, dexterity, dexterity * 4, dexterity * 1);
        boringStart();
    }
    @Override
    void boringStart() {
        Console.describe("    " + name + ": только ловкость?!\n    XXXX: А ну тихо, мы начинаем");
    }
}
