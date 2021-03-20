import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class Card {
    String code;
    String suit;
    String value;
    static final HashMap<Character, String> valueMap  = new HashMap<>();
    static final HashMap<Character, String> suitMap  = new HashMap<>();
    static final HashMap<Character, Integer> blackJack = new HashMap<>();
    static{
        valueMap.put('A', "Ace");
        valueMap.put('2', "Two");
        valueMap.put('3', "Three");
        valueMap.put('4', "Four");
        valueMap.put('5', "Five");
        valueMap.put('6', "Six");
        valueMap.put('7', "Seven");
        valueMap.put('8', "Eight");
        valueMap.put('9', "Nine");
        valueMap.put('0', "Ten");
        valueMap.put('J', "Jack");
        valueMap.put('Q', "Queen");
        valueMap.put('K', "King");

        suitMap.put('D', "Diamonds");
        suitMap.put('C', "Clubs");
        suitMap.put('H', "Hearts");
        suitMap.put('S', "Spades");

        blackJack.put('A', 11);
        blackJack.put('2', 2);
        blackJack.put('3', 3);
        blackJack.put('4', 4);
        blackJack.put('5', 5);
        blackJack.put('6', 6);
        blackJack.put('7', 7);
        blackJack.put('8', 8);
        blackJack.put('9', 9);
        blackJack.put('0', 10);
        blackJack.put('J', 10);
        blackJack.put('Q', 10);
        blackJack.put('K', 10);
    }

    Card(String code){
        value =  valueMap.get(code.charAt(0));
        suit = suitMap.get(code.charAt(1));
        this.code = code;

    }

    @Override
    public String toString(){
        return new String(value + " of " + suit);
    }
}
