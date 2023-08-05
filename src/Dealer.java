import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.sql.Array;


public class Dealer {
    //Player needs to have a lot of attributes
    //Name, chip count, base card 1, base card 2, any other amount of card, blackjack value, boolean bust, boolean turn, etc.

    //Possibly need values for hit and stay, hitting blackjack

    String name;
    int chipCount;
    int bet;
    int bjValue;
    boolean bust;
    boolean stay;
    List<Card> cards = new ArrayList<Card>();

    public Dealer(){
        name = "Default Dealer";
        chipCount = 1000;
        bet = 0;
        bjValue = 0;
        bust = false;
        stay = false;
    }

    public Dealer(String name, int chipCount){
        this.name = name;
        this.chipCount = chipCount;
        bet = 0;
        bjValue = 0;
        bust = false;
        stay = false;
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

    public boolean getStay(){
        return stay;
    }

    //get the list of dealer's current cards
    public List<Card> getCards(){
        return cards;
    }

    //add a card to dealer's hand
    public void addCard(Card c){
        cards.add(c);
    }

    //reset dealer's hand after a round is done
    public void removeCards(){
        //reset all aces to regular values just in case there was a converted ace
        for(int i = 0; i < cards.size(); i++){
            cards.get(i).setAceDowngrade(false);
        }
        cards.clear();
        bjValue = 0;
        bust = false;
        stay = false;
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
        bet = b;
        chipCount -= bet;
    }

    public int getDealerBJValue(){
        return bjValue;
    }

    public boolean needInsurance(){
        if(cards.get(0).getBJValue() == 11){
            return true;
        }
        else{
            return false;
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
        else if(bjValue >= 17 ){
            stay = true;
            //do something about hitting blackjack
        }
        else{
            //still have option to hit, etc.
        }
    }
}
