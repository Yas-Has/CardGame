import org.json.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class CardGame {
    public static void main(String[] args){
        Scanner console = new Scanner(System.in);
        System.out.print("Join or Host, enter j or h: ");
        String jorh = console.next();

        System.out.print("Enter Username: ");
        String username = console.next();

        Deck newDeck;

        if(jorh.equals("j")){
            System.out.print("Enter join code: ");
            String joinCode = console.next();
            newDeck = new Deck(joinCode);
        }
        else{
            newDeck = new Deck();
        }
        //Deck pi = new Deck();
        System.out.println("Join code is: " + newDeck.deckid);
        Card[] cards = newDeck.getCards(2);

        newDeck.addToPile(username, cards);
        Card[] pile = newDeck.getCardsInPile(username);
        System.out.println("Your hand is: ");
        for(Card s: pile){
            System.out.println(s);

        }
        System.out.println("Your Score: " + blackJackValue(pile));

        String nextTurn = "";
        while(!nextTurn.equals("stop")){
            System.out.println("hit or stop? ");
            nextTurn = console.next();
            if(nextTurn.equals("hit")){
                Card[] c = newDeck.getCards(1);
                newDeck.addToPile(username, c);

                pile = newDeck.getCardsInPile(username);
                System.out.println("Your hand is: ");
                for(Card s: pile){
                    System.out.println(s);

                }
                int blackJValue = blackJackValue(pile);
                System.out.println("Your Score: " + blackJValue);
                if(blackJValue > 21){
                    System.out.println("Bust! ");
                    nextTurn = "stop";

                }

            }
        }

        System.out.print("Wait for all players to finish then type any key and hit enter. ");
        String finished = console.next();
        JSONObject otherUsers = makeAPICall("https://deckofcardsapi.com/api/deck/" + newDeck.deckid + "/pile/" + username + "/list/").getJSONObject("piles");
        System.out.println("---------------------------");
        System.out.println("---------------------------");
        Iterator<String> piles = otherUsers.keys();
        while(piles.hasNext()){

            String pileName = piles.next();
            Card[] newUserCards = newDeck.getCardsInPile(pileName);
            int newUserScore = blackJackValue(newUserCards);

            System.out.println(pileName + " hand is: ");
            for(Card s: newUserCards){
                System.out.println(s);

            }
            System.out.println(pileName + " score is: " + newUserScore);
            System.out.println("---------------------------");
            System.out.println("---------------------------");
        }





    }

    public static int blackJackValue(Card[] hand){
        int total = 0;
        for(Card c: hand){
            total += Card.blackJack.get(c.code.charAt(0));
            if(c.value.equals("Ace") && total > 21){
                total -= 10;
            }
        }
        return total;
    }

    public static JSONObject makeAPICall(String urlString){
        //System.out.println(urlString);
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            String resultString = "";
            if(conn.getResponseCode() == 200){
                Scanner sc = new Scanner(conn.getInputStream());
                while(sc.hasNext()){
                    resultString += sc.nextLine();
                }
                //System.out.println(resultString);
                //JSONParser parser = new JSONParser();
                JSONObject j = new JSONObject(resultString);
                //System.out.println(j);
                return j;
                //return (JSONObject) parser.parse(resultString);
            }

        }catch(IOException e){
            System.out.println(e);
        }
        finally {
            if (conn != null){
                conn.disconnect();
            }
        }
        return null;
    }

}


