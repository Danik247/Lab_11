package helpfulClasses;

public class Consol {
    public static void describe(String message){
        System.out.println(message+"\n...");
    }
    public static void hello(String name, float dexterity, float speed, float stamina, float eloquence, float size, float weight){
        System.out.println(STR."""
\{name} начинает свое сомнительное путешествие...
вот его удручающие характеристики:
    скорость     >> \{speed}
    выносливость >> \{stamina}
    ловкость     >> \{dexterity}
    харизма      >> \{eloquence}
    вес          >> \{weight}
    размер       >> \{size}
""");//printf  переделать
    }
}
