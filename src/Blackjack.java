import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Blackjack {


    //Overall notes:
    
    //Players need to rotate when they go first
    //Should add 6 decks and shuffle when necessary
    //Need to figure out split
    //Our house doesn't let you take insurance when you have blackjack

    //How to work with buttons? Does everybody see their own buttons? How does that work?
    //Is there a universal "button board" on the bottom? Hit, Stay, DD (if first move), Split (if same cards and first move), Insurance (if no BJ and dealer has Ace face up)
    

    //Bonus things to add eventually
    //rummy
    //triple 7s

    public static void main(String[] args) throws Exception {
        //System.out.println("Hello, World!");
        //System.out.println();

        Scanner obj = new Scanner(System.in);

        Deck testDeck = new Deck();
        //System.out.println();
        testDeck.Shuffle();
        //System.out.println();
        //System.out.println("The top card is " + testDeck.getCard(0) + ".");
        //System.out.println(testDeck.Deal().toString());
        //System.out.println("The top card is " + testDeck.getCard(0) + ".");
        //System.out.println("The bottom card is " + testDeck.getCard(51) + ".");

        //should make a list of players, then iterate through players instead of hardcoding

        List<Player> playersList = new ArrayList<Player>();
        Player ethan = new Player("Ethan", 1000);
        Player courtney = new Player("Courtney", 1000);
        Dealer mac = new Dealer("Mac", 5000);

        playersList.add(ethan);
        playersList.add(courtney);
        
        boolean play = true;
        while(play){
            System.out.println();
            System.out.println(ethan.getName() + ", how much would you like to bet?");
            int ethanBet = Integer.parseInt(obj.nextLine());
            ethan.setBet(ethanBet);

            //ethan.setBet(500);
            System.out.println("Ethan has bet " + ethan.getBet() + " chips and has a total of " + ethan.getChipCount() + " chips left.");

            System.out.println(courtney.getName() + ", how much would you like to bet?");
            int courtneyBet = Integer.parseInt(obj.nextLine());
            courtney.setBet(courtneyBet);
            //courtney.setBet(200);
            System.out.println("Courtney has bet " + courtney.getBet() + " chips and has a total of " + courtney.getChipCount() + " chips left.");

            //ethan.addCard(testDeck.Deal()); could work and skip a step.
            
            ethan.addCard(testDeck.getCard(0));
            testDeck.Deal();
            courtney.addCard(testDeck.getCard(0));
            testDeck.Deal();
            mac.addCard(testDeck.getCard(0));
            testDeck.Deal();

            ethan.addCard(testDeck.getCard(0));
            testDeck.Deal();
            courtney.addCard(testDeck.getCard(0));
            testDeck.Deal();
            //need to add dealer to get secret card
            mac.addCard(testDeck.getCard(0));
            testDeck.Deal();

            for(int i = 0; i < ethan.getCards().size(); i++){
                System.out.println("Ethan has " + ethan.getCards().get(i) + ".");
                ethan.addBJValue(ethan.getCards().get(i).getBJValue());
            }
            System.out.println("Ethan has a total Blackjack value of " + ethan.getPlayerBJValue());
            System.out.println();

            for(int i = 0; i < courtney.getCards().size(); i++){
                System.out.println("Courtney has " + courtney.getCards().get(i) + ".");
                courtney.addBJValue(courtney.getCards().get(i).getBJValue());
            }
            System.out.println("Courtney has a total Blackjack value of " + courtney.getPlayerBJValue());
            System.out.println();

            
            for(int i = 0; i < mac.getCards().size(); i++){
                mac.addBJValue(mac.getCards().get(i).getBJValue());
            }
            System.out.println("Mac the Dealers first card is " + mac.getCards().get(0) + ".");
            System.out.println();

            //need to figure out quirks
            //perhaps add a list of booleans and do something with that
            if(mac.needInsurance()){
                for(int i = 0; i < playersList.size(); i++){
                    if(playersList.get(i).getPlayerBJValue() == 21){
                        System.out.println(playersList.get(i).getName() + " has a natural and therefore cannot take insurance");
                    }
                    else{
                        System.out.println(playersList.get(i).getName() + ", the dealer has an Ace. \nTake insurance? Yes or No.");
                        String insure = obj.nextLine();
                        if(insure.equals("yes") || insure.equals("Yes")){
                            if(playersList.get(i).insurance()){
                                System.out.println(playersList.get(i).getName() + " has taken insurance for " + playersList.get(i).getBet()/2 + ".");
                                if(mac.getCards().get(1).getBJValue() == 10){
                                    playersList.get(i).addChips(playersList.get(i).getBet());
                                    mac.loseChips(playersList.get(i).getBet());
                                }
                                else{
                                    playersList.get(i).loseChips(playersList.get(i).getBet()/2);
                                    mac.addChips(playersList.get(i).getBet()/2);
                                }
                            }
                            else{
                                System.out.println(playersList.get(i).getName() + " does not have enough chips to buy insurance.");
                            }
                        }
                        else{
                            System.out.println(playersList.get(i).getName() + " has chosen not to take insurance.");
                        }
                    }
                    System.out.println();
                }
            }

            if(mac.needInsurance() && !mac.hasBlackjack()){
                System.out.println("The Dealer did not hit Blackjack. \nThose with insurance have lost their insurance bet");
            }

            if(!mac.hasBlackjack()){
                for(int i = 0; i < playersList.size(); i++){
                    if(playersList.get(i).hasBlackjack()){
                        System.out.println(playersList.get(i).getName() + " has a natural and will be paid out 3/2 instantly");
                        playersList.get(i).addChips((int)(playersList.get(i).getBet() + playersList.get(i).getBet()*(1.5)));
                        mac.loseChips((int)(playersList.get(i).getBet()*(1.5)));
                    }
                }
            }

            if(mac.needInsurance() && mac.hasBlackjack()){
                System.out.println("The Dealer hit Blackjack. \nThose with insurance have been paid out");
                //add more ifs so that if dealer hit blackjack with ace first that it instantly skips next portion of code
            }
            else{
                //make this a for loop iterating through list of players
                for(int i = 0; i < playersList.size(); i++){
                    if(playersList.get(i).hasBlackjack()){

                    }
                    else{
                        System.out.println();
                        boolean choice = true;
                        //checking for double down
                        System.out.println(playersList.get(i).getName() + ", you currently have a " + playersList.get(i).getPlayerBJValue() + " but can double down. \nDouble down? Yes or No.");
                        String dd = obj.nextLine();
                        if(dd.equals("yes") || dd.equals("Yes")){
                            if(playersList.get(i).doubleDown()){
                                System.out.println(playersList.get(i).getName() + " has doubled down and is now betting " + playersList.get(i).getBet() + ".");
                                System.out.println(playersList.get(i).getName() + " got " + testDeck.getCard(0).toString());
                                playersList.get(i).addCard(testDeck.getCard(0));
                                playersList.get(i).addBJValue(testDeck.getCard(0).bjValue);
                                testDeck.Deal();
                                if(playersList.get(i).getBust()){
                                    choice = false;
                                    System.out.println(playersList.get(i).getName() + " has busted with a score of " + playersList.get(i).getPlayerBJValue());
                                    //mac.addChips(playersList.get(i).getBet());
                                }
                                else{
                                    choice = false;
                                    System.out.println(playersList.get(i).getName() + " ends with a " + playersList.get(i).getPlayerBJValue());                            }
                                }
                            else{
                                System.out.println(playersList.get(i).getName() + " does not have enough chips to double down.");
                            }
                        }
                        else{
                            System.out.println(playersList.get(i).getName() + " has chosen not to double down.");
                        }

                        //this will be for splitting
                        if(playersList.get(i).getCards().get(0).getValue() == playersList.get(i).getCards().get(1).getValue()){
                    
                        }
                        while(choice){
                            System.out.println(playersList.get(i).getName() + ", you currently have a " + playersList.get(i).getPlayerBJValue() + ". Hit or Stay?");
                            String decision = obj.nextLine();
                            if(decision.equals("Hit") || decision.equals("hit")){
                                System.out.println(playersList.get(i).getName() + " got " + testDeck.getCard(0).toString());
                                playersList.get(i).addCard(testDeck.getCard(0));
                                playersList.get(i).addBJValue(testDeck.getCard(0).bjValue);
                                testDeck.Deal();
                                if(playersList.get(i).getBust()){
                                    choice = false;
                                    System.out.println(playersList.get(i).getName() + " has busted with a score of " + playersList.get(i).getPlayerBJValue());
                                    //mac.addChips(playersList.get(i).getBet());
                                    break;
                                }
                            }
                            else if(decision.equals("Stay") || decision.equals("stay")){
                                choice = false;
                                break;
                            }
                            else{
                                System.out.println("Try again");
                            }
                        }
                    }
                }

                System.out.println();
                for(int i = 0; i < playersList.size(); i++){
                    System.out.println(playersList.get(i).getName() + ", ends with a " + playersList.get(i).getPlayerBJValue() + ".");
                }

                //time for the dealer

                System.out.println();
                System.out.println(mac.getName() + " the Dealers 1st card is " + mac.getCards().get(0));
                System.out.println(mac.getName() + " the Dealers 2nd card is " + mac.getCards().get(1));

                boolean dealerChoice = true;
                while(dealerChoice){
                    System.out.println(mac.getName() + " the Dealer has a score of " + mac.getDealerBJValue() + ".");
                    if(mac.getBust()){
                        System.out.println(mac.getName() + " busted!");
                        dealerChoice = false;
                        break;
                    }
                    else if(mac.getStay()){
                        System.out.println(mac.getName() + " ends with a score of " + mac.getDealerBJValue() + ".");
                        dealerChoice = false;
                        break;
                    }
                    else{
                        System.out.println(mac.getName() + " got " + testDeck.getCard(0).toString());
                        mac.addCard(testDeck.getCard(0));
                        mac.addBJValue(testDeck.getCard(0).bjValue);
                        testDeck.Deal();
                    }
                }

            }

            for(int i = 0; i < playersList.size(); i++){
                System.out.println();
                String name = playersList.get(i).getName();
                int bet = playersList.get(i).getBet();
                if(playersList.get(i).getBust()){
                    System.out.println(name + " busted and lost " + bet + " chips.");
                    System.out.println(mac.getName() + " won " + bet + " chips.");
                    mac.addChips(bet);
                }
                else if(!playersList.get(i).hasBlackjack() && (playersList.get(i).getPlayerBJValue() > mac.getDealerBJValue() || mac.getBust())){
                    System.out.println(name + " beat the dealer and won " + bet + " chips.");
                    System.out.println(mac.getName() + " lost " + bet + " chips.");
                    playersList.get(i).addChips(bet);
                    playersList.get(i).addChips(bet);
                    mac.loseChips(bet);
                }
                else if(!playersList.get(i).hasBlackjack() && (playersList.get(i).getPlayerBJValue() < mac.getDealerBJValue())){
                    System.out.println(name + " lost to the dealer and lost " + bet + " chips.");
                    System.out.println(mac.getName() + " won " + bet + " chips.");
                    mac.addChips(bet);
                }
                else if(playersList.get(i).hasBlackjack()){
                    //already paid out but don't want to push
                }
                else{
                    System.out.println(name + " tied the dealer, therefore it is a push.");
                    playersList.get(i).addChips(bet);
                }
            }

            System.out.println();
            for(int i = 0; i < playersList.size(); i++){
                System.out.println(playersList.get(i).getName() + " has " + playersList.get(i).getChipCount() + " chips.");
            }
            System.out.println(mac.getName() + " has " + mac.getChipCount() + " chips.");
            System.out.println();

            System.out.println("Play again? Type yes, otherwise type anything");
            String again = obj.nextLine();
            if(again.equals("Yes") || again.equals("yes")){
                System.out.println();
                for(int i = 0; i < playersList.size(); i++){
                    playersList.get(i).removeCards();
                }
                mac.removeCards();
                testDeck.Shuffle();
            }
            else{
                System.out.println();
                play = false;
                break;
            }


        /*
        System.out.println("Mac (the dealer) has a total Blackjack value of " + mac.getPlayerBJValue());
        System.out.println();
        */

        }

    }
}
