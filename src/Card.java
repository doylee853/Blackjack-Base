import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Card {
    
    //What attributes should I give each card? Obviously a suit, a value, but do I add the blackjack value as well? I think so.
    //Three attributes:
    //  Suit -> int ranging from 0-3
    //  Value -> int ranging from 1-13
    // BJValue -> int rangin from 1-11? Ace will be tricky

    public final static int SPADES = 0;
    public final static int HEARTS = 1;
    public final static int CLUBS = 2;
    public final static int DIAMONDS = 3;
    public final static int ETHANISUITS = 4;

    public final static int ACE = 1;
    public final static int JACK = 11;
    public final static int QUEEN = 12;
    public final static int KING = 13;
    public final static int GOAT = 0;

    int suit;
    int value;
    int bjValue;

    // value to determine if an ace has been lowered to a one
    boolean aceDowngrade;

    public Card(){
        suit = 4;
        value = 0;
        bjValue = 0;
        aceDowngrade = false;
    }

    public Card(int suit, int value){
        this.suit = suit;
        this.value = value;
        if(value == 11 || value == 12 || value == 13){
            bjValue = 10;
        }
        //to start just make ace 11, will deal with ace wackiness later
        else if(value == 1){
            bjValue = 11;
        }
        else{
            bjValue = value;
        }
        aceDowngrade = false;
    }

    public int getSuit(){
        return suit;
    }

    public int getValue(){
        return value;
    }

    public int getBJValue(){
        return bjValue;
    }

    public void setBJValue(int bjValue){
        this.bjValue = bjValue;
    }

    public boolean getAceDowngrade(){
        return aceDowngrade;
    }

    public void setAceDowngrade(boolean b){
        aceDowngrade = b;
    }

    public String getStringValue(){
        if(value == 1){
            return "Ace";
        }
        else if(value == 2){
            return "Two";
        }
        else if(value == 3){
            return "Three";
        }
        else if(value == 4){
            return "Four";
        }
        else if(value == 5){
            return "Five";
        }
        else if(value == 6){
            return "Six";
        }
        else if(value == 7){
            return "Seven";
        }
        else if(value == 8){
            return "Eight";
        }
        else if(value == 9){
            return "Nine";
        }
        else if(value == 10){
            return "Ten";
        }
        else if(value == 11){
            return "Jack";
        }
        else if(value == 12){
            return "Queen";
        }
        else if(value == 13){
            return "King";
        }
        else if(value == 0){
            return "Goat";
        }
        else{
            return "You messed up the value";
        }
    }

    public String getStringSuit(){
        if(suit == 0){
            return "Spades";
        }
        else if(suit == 1){
            return "Hearts";
        }
        else if(suit == 2){
            return "Clubs";
        }
        else if(suit == 3){
            return "Diamonds";
        }
        else if(suit == 4){
            return "Ethanisuits";
        }
        else{
            return "You messed up the suit";
        }
    }

    public String toString(){
        return "The " + getStringValue() + " of " + getStringSuit() + ".";
    }

    //public static void main(String[] args){
        /*List<Card> deck = new ArrayList<Card>();
        for(int i = 0; i < 4; i++){
            for(int j = 1; j < 14; j++){
                Card c = new Card(i, j);
                deck.add(c);
            }
        }
        */


    //}

}
