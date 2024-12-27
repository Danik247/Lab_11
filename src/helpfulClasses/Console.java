package helpfulClasses;

import persons.Person;

public class Console {
    public static void describe(String message){
        System.out.println(message+"\n...");
    }

    public static void hello(String name, Person object){
        System.out.printf("я " + name + " и мои статы такие: " + object.getStatistics().toString() + "\n");
    }
}
