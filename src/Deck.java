import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.sql.Array;

public class Deck {

    List<Card> deck = new ArrayList<Card>();

    /*
     * creates an ordered deck of cards, starting with the Ace of Spades
     * and increasing in value until the King of Spades, then repeats with 
     * Hearts, Clubs, and Diamonds
     */
    public Deck(){
        for(int i = 0; i < 4; i++){
            for(int j = 1; j < 14; j++){
                Card c = new Card(i, j);
                deck.add(c);
            }
        }

        /*
        for(int i = 0; i < deck.size(); i++){
            System.out.println("Card " + (i+1) + " is " + deck.get(i).toString());
        }
        */
    }

    /*
     * returns the card at index i
     */
    public Card getCard(int i){
        return deck.get(i);
    }

    public void Shuffle(){
        for(int i = deck.size()-1; i > 0; i--){
            int random = (int)(Math.random()*(i+1));
            Card temp = deck.get(i);
            deck.set(i, deck.get(random));
            deck.set(random, temp);
        }

        /*
        for(int i = 0; i < deck.size(); i++){
            System.out.println("Card " + (i+1) + " is " + deck.get(i).toString());
        }
        */
    }

    public Card Deal(){
        Card top = deck.get(0);
        Collections.rotate(deck, -1);
        return top;
    }

}
