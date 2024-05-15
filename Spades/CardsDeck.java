import java.util.ArrayList;
import java.util.Collections;

public class CardsDeck {

    ArrayList<Cards> deck = new ArrayList<Cards>();

    public CardsDeck() { //default constructor creates the whole deck of 52 cards with each card assigned to a key

        for (int i = 1; i <= 52; i++) { //for-loop to add 52 cards in total to the deck
            if (i <= 13) { //the first 13 cards are clubs (first parameter in Cards() constructor)
                deck.add(new Cards(1, i, i-1));
            } else if (i <= 26) { //the second 13 cards are diamonds (first parameter in Cards() constructor)
                deck.add(new Cards(2, i - 13, i-1)); // 13 is subtracted to synchronize card values on 1-13 scale per suit
            } else if (i <= 39) { //the third 13 cards are hearts (first parameter in Cards() constructor)
                deck.add(new Cards(3, i - 26, i-1)); // 26 is subtracted to synchronize card values on 1-13 scale per suit
            } else { //the last 13 cards are spades (first parameter in Cards() constructor)
                deck.add(new Cards(4, i - 39, i-1)); // 39 is subtracted to synchronize card values on 1-13 scale per suit
            }
        }

        Collections.shuffle(deck);
    }

    public Cards getCard(int index) { //getter for individual cards in the deck
        return deck.get(index);
    }
    public void removeCard(int index) { //removes a card from the deck after it is drawn
        deck.remove(index);
    }
}