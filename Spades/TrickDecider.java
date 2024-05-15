import java.util.ArrayList;

public class TrickDecider {
    private SpadesPlayer p1;
    private SpadesPlayer p2;
    private SpadesPlayer p3;
    private SpadesPlayer p4;
    private Cards winningCard;
    ArrayList<Cards> cardsPlayed = new ArrayList<Cards>();

    public TrickDecider(SpadesPlayer one, SpadesPlayer two, SpadesPlayer three, SpadesPlayer four) { //constructor takes in four SpadesPlayer objects
        p1 = one;
        p2 = two;
        p3 = three;
        p4 = four;
    }

    public void addCardToPile (Cards card) { //used to add individual cards that each player plays to the pile
        cardsPlayed.add(card);
    }

    public int determineTrickWinner() { //determines the winner of the current trick after adjusting card values
        int winner = 0;

        adjustCardValues(); //makes sure that card values are adjusted so that suits hold different values

        winningCard = cardsPlayed.get(0); //temporarily (unless the card has the highest value) considers the first card to be the winning card

        if (winningCard.getValue() < cardsPlayed.get(1).getValue()) { //checks if the current winning card has less value than the next card
            winningCard = cardsPlayed.get(1); //if the next card has a higher value than the previous winning card, it becomes the new winning card
        }

        if (winningCard.getValue() < cardsPlayed.get(2).getValue()) { //checks if the current winning card has less value than the next card
            winningCard = cardsPlayed.get(2); //if the next card has a higher value than the previous winning card, it becomes the new winning card
        }

        if (winningCard.getValue() < cardsPlayed.get(3).getValue()) { //checks if the current winning card has less value than the next card
            winningCard = cardsPlayed.get(3); //if the next card has a higher value than the previous winning card, it becomes the new winning card
        }

        if (p1.getPlayerDeck().contains(winningCard)) { //checks if the first player was the one who played the winning card
            p1.setCurrentTricksWon(p1.getCurrentTricksWon() + 1); //increments their current tricks won this round if they have winning card
            winner = 1;
        } else if (p2.getPlayerDeck().contains(winningCard)) { //checks if the second player was the one who played the winning card
            p2.setCurrentTricksWon(p2.getCurrentTricksWon() + 1); //increments their current tricks won this round if they have winning card
            winner = 2;
        } else if (p3.getPlayerDeck().contains(winningCard)) { //checks if the third player was the one who played the winning card
            p3.setCurrentTricksWon(p3.getCurrentTricksWon() + 1); //increments their current tricks won this round if they have winning card
            winner = 3;
        } else if (p4.getPlayerDeck().contains(winningCard)) { //checks if the fourth player was the one who played the winning card
            p4.setCurrentTricksWon(p4.getCurrentTricksWon() + 1); //increments their current tricks won this round if they have winning card
            winner = 4;
        }

        for (int i = 0; i < 4; i++) {
            if (p1.getPlayerDeck().contains(cardsPlayed.get(i))) {
                p1.removeCardFromHand(p1.getPlayerDeck().indexOf(cardsPlayed.get(i)));
            } else if (p2.getPlayerDeck().contains(cardsPlayed.get(i))) {
                p2.removeCardFromHand(p2.getPlayerDeck().indexOf(cardsPlayed.get(i)));
            } else if (p3.getPlayerDeck().contains(cardsPlayed.get(i))) {
                p3.removeCardFromHand(p3.getPlayerDeck().indexOf(cardsPlayed.get(i)));
            } else if (p4.getPlayerDeck().contains(cardsPlayed.get(i))) {
                p4.removeCardFromHand(p4.getPlayerDeck().indexOf(cardsPlayed.get(i)));
            }
        }

        cardsPlayed.removeAll(cardsPlayed);

        return winner;
    }

    private void adjustCardValues () { //adjusts card values based on hierarchy system to determine winner of each individual trick
        for (int i = 0; i < 4; i++) {
            if (cardsPlayed.get(i).getSuit() == determineLeadingSuit()) { //checks if the current card is the same suit as the leading suit
                cardsPlayed.get(i).setValue(cardsPlayed.get(i).getValue() + 13); //cards that match the leading suit have value increased by 13 (tier 2)
            } else if (cardsPlayed.get(i).getSuit() == 4) { //checks if the current card is a spade
                cardsPlayed.get(i).setValue(cardsPlayed.get(i).getValue() + 26); //cards that are spades have value increased by 26 (tier 3)
            }
        } //although not mentioned, cards that are not spades or match the leading suit have no increase in value (remain in tier 1)
    } //this creates a system where off-suit cards are valued less than leading suit cards and spades always have the most value regardless

    public int determineLeadingSuit() { //determines suit that other players' played cards must follow based on first player's initial card played
        return cardsPlayed.get(0).getSuit(); //cards are added in order, therefore the leading suit will be based on first card added to ArrayList
    }

    public ArrayList<Cards> getCardsPlayed() {
        return cardsPlayed;
    }
}
