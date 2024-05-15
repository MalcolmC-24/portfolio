import java.util.ArrayList;

public class SpadesPlayer {
    private int currentBid;
    private int currentTricksWon;
    ArrayList<Cards> playerDeck = new ArrayList<Cards>();
    ArrayList<CardDrawing> playerCards = new ArrayList<CardDrawing>();

    public SpadesPlayer() { //default constructor sets all player values to 0 when it is called at the beginning of the game
        currentBid = 0;
        currentTricksWon = 0;
    }

    public int getCurrentBid() { //getter for current bid placed by player (number of tricks they predict to win)
        return currentBid;
    }

    public int getCurrentTricksWon() { //getter for current tricks won by player
        return currentTricksWon;
    }

    public ArrayList<Cards> getPlayerDeck() { //getter for the cards currently in player's hand
        return playerDeck;
    }

    public ArrayList<CardDrawing> getPlayerCards() { //getter for the cards currently in player's hand
        return playerCards;
    }

    public void setCurrentBid(int bid) { //setter for the current bid placed by player (number of tricks they predict to win)
        currentBid = bid;
    }

    public void setCurrentTricksWon(int tricks) { //setter for current tricks won by player (used primarily to increment after each win)
        currentTricksWon = tricks;
    }

    public void addCardToHand(Cards newCard) { //adds a card to the player's hand after randomly being drawn from deck
        playerDeck.add(newCard);
        playerCards.add(new CardDrawing(newCard));
    }

    public void removeCardFromHand(int index) { //removes a card from the player's hand after it is played
        playerDeck.remove(index);
        playerCards.remove(index);
    }

    public void removeHand() {
        playerDeck.removeAll(playerDeck);
        playerCards.removeAll(playerCards);
    }

}
