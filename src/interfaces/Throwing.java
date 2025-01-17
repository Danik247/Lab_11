package interfaces;

import persons.Person;

@FunctionalInterface
public interface Throwing {
    boolean attack(Person player, String name);
}
