public class Cards {
    private final int suit;
    private int value;
    private final String cardLabel;
    private final int cardID;
    private boolean clicked;
    public Cards(int s, int v, int id) { //parameterized constructor takes in integer values for suit and value
        suit = s; //suits are on a range of 1-4 (1 for clubs, 2 for diamonds, 3 for hearts, 4 for spades)
        value = v; //values are on a range of 1-13 (there are 13 cards for each suit)
        cardID = id;

        if (value <= 9) { //if-else statement used to implement value system and assign card labels for each value
            Integer tempValue = value + 1; //creates a temporary value used for non-face card labels (adds 1 since there is no "1" card)
            cardLabel = tempValue.toString(); //values 1-9 are non-face cards (cards 2-10), toString is used to convert integer to a string
        } else if (value == 10) {
            cardLabel = "J"; //value 10 is the "Jack" face card denoted by the character 'J'
        } else if (value == 11) {
            cardLabel = "Q"; //value 11 is the "Queen" face card denoted by the character 'Q'
        } else if (value == 12) {
            cardLabel = "K"; //value 12 is the "King" face card denoted by the character 'K'
        } else {
            cardLabel = "A"; //value 13 is the "Ace" face card denoted by the character 'A'
        }
    }
    public void setClicked(boolean tf) { //getter for suit integer value (1 for clubs, 2 for diamonds, 3 for hearts, 4 for spades)
        clicked = tf;
    }
    
    public boolean getClicked() { //getter for suit integer value (1 for clubs, 2 for diamonds, 3 for hearts, 4 for spades)
        return clicked;
    }
    
    public int getSuit() { //getter for suit integer value (1 for clubs, 2 for diamonds, 3 for hearts, 4 for spades)
        return suit;
    }
    
    public int getValue() { //getter for card integer value (initially runs on a 1-13 scale, then adjusted later on based on suits)
        return value;
    }

    public String getCardLabel() { //getter for card label (such as 'Q' for a Queen)
        return cardLabel;
    }

    public int getCardID() {
        return cardID;
    }

    public void setValue(int v) { //setter for card integer value (used to adjust card values based on suit)
        value = v;
    }

}
