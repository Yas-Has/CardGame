import org.json.JSONArray;
import org.json.JSONObject;

public class Deck {
    String deckid;
    Deck(){
        JSONObject deck = CardGame.makeAPICall("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1");
        //System.out.println(deck);
        deckid = deck.getString("deck_id");
    }
    Deck(String id){
        deckid = id;
    }

    Card[] getCards(int numCards){
        Card[] cards = new Card[numCards];
        JSONArray newCards = CardGame.makeAPICall("https://deckofcardsapi.com/api/deck/" + deckid + "/draw/?count="+ numCards).getJSONArray("cards");
        for(int i = 0; i< numCards; i++){
            cards[i] = new Card(newCards.getJSONObject(i).getString("code"));
        }
        return cards;
    }

    void addToPile(String user, Card[] c){
        String output = "";
        for(int i = 0; i < c.length - 1; i++){
            output += c[i].code +",";
        }
        output += c[c.length-1].code;
        JSONObject reply = CardGame.makeAPICall("https://deckofcardsapi.com/api/deck/"+ deckid + "/pile/"+ user +"/add/?cards=" + output);
    }

    void addToPile(String user, Card c){
        JSONObject reply = CardGame.makeAPICall("https://deckofcardsapi.com/api/deck/"+ deckid + "/pile/"+ user +"/add/?cards=" + c.code);
    }

    Card[] getCardsInPile(String pileName){
        JSONArray reply = CardGame.makeAPICall("https://deckofcardsapi.com/api/deck/"+ deckid +"/pile/"+ pileName +"/list/").getJSONObject("piles").getJSONObject(pileName).getJSONArray("cards");
        Card[] output = new Card[reply.length()];
        for(int i = 0; i < reply.length(); i++){
            output[i] = new Card(reply.getJSONObject(i).getString("code"));
        }
        return output;

    }




    /*
    number of cards in deck
    draw function

     */
}
