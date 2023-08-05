import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.sql.Array;

public class Player {
    //Player needs to have a lot of attributes
    //Name, chip count, base card 1, base card 2, any other amount of card, blackjack value, boolean bust, boolean turn, etc.

    //Possibly need values for hit and stay, hitting blackjack

    String name;
    int chipCount;
    int bet;
    int bjValue;
    boolean bust;
    List<Card> cards = new ArrayList<Card>();

    public Player(){
        name = "Default Player";
        chipCount = 1000;
        bet = 0;
        bjValue = 0;
        bust = false;
    }

    public Player(String name, int chipCount){
        this.name = name;
        this.chipCount = chipCount;
        bet = 0;
        bjValue = 0;
        bust = false;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public boolean getBust(){
        return bust;
    }

    //get the list of player's current cards
    public List<Card> getCards(){
        return cards;
    }

    //add a card to players hand
    public void addCard(Card c){
        cards.add(c);
    }

    //reset players hand after a round is done
    public void removeCards(){
        //reset all aces to regular values just in case there was a converted ace
        for(int i = 0; i < cards.size(); i++){
            cards.get(i).setAceDowngrade(false);
        }
        cards.clear();
        bjValue = 0;
        bust = false;
    }

    public int getChipCount(){
        return chipCount;
    }

    public void setChipCount(int c){
        chipCount = c;
    }

    public void addChips(int c){
        chipCount += c;
    }

    public void loseChips(int c){
        chipCount -= c;
    }

    public int getBet(){
        return bet;
    }

    public void setBet(int b){
        if(b > chipCount){
            System.out.println("Not enough chips. Instead will bet the max amount");
            bet = chipCount;
            chipCount = 0;
        }
        else{
            bet = b;
            chipCount -= bet;
        }
    }

    public int getPlayerBJValue(){
        return bjValue;
    }

    public boolean doubleDown(){
        if(bet > chipCount){
            return false;
        }
        else{
            chipCount -= bet;
            bet += bet;
            return true;
        }
    }

    public boolean insurance(){
        if((bet/2) > chipCount || bjValue == 21){
            return false;
        }
        else{
            return true;
            //gotta figure out how this works
            //chipCount -= bet;
            //bet += bet;
            //return true;
        }
    }

    public boolean hasBlackjack(){
        if(cards.get(0).getBJValue() + cards.get(1).getBJValue() == 21){
            return true;
        }
        else{
            return false;
        }
    }

    public void addBJValue(int n){
        //do ace math here
        bjValue += n;
        if(bjValue > 21){
            boolean saved = false;
            for(int i = 0; i < cards.size(); i++){
                //if over 21 but one of the cards is an ace, convert ace from 11 to 1
                if(cards.get(i).getBJValue() == 11 && cards.get(i).getAceDowngrade() == false){
                    //checks to make sure that blackjack value is still above 21
                    //prevents needlessly converting ace value to a 1 in the case of having numerous aces in hand
                    if(bjValue > 21){
                        bjValue -= 10;
                        cards.get(i).setAceDowngrade(true);
                        saved = true;
                    }
                }
            }
            //if you truly busted
            if(!saved){
                bust = true;
            }
        }
        if(bjValue == 21){
            //do something about hitting blackjack
        }
        if(bjValue < 21){
            //still have option to hit, etc.
        }
    }

    
}
