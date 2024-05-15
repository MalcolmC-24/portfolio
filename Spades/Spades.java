import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Spades extends JFrame implements ActionListener {

    CardLayout crd;
    String labelText;
    int turnCounter, screenCounter, cardsAdded;
    JButton play, rules, back, next, nextBid, nextRound;
    JButton buttons[]; // array of buttons
    Container cPane;
    JLabel  turnText, gameTitle;
    JTextArea ruleText;
    JTextPane winnerText;
    JTextField bidText, bidText2, bidOne, bidTwo, bidThree, bidFour;
    JPanel startScreen, ruleScreen, startBorder, bufferScreen, topScreen, roundScreen;
    JPanel[] playerScreen, playerButtons, gameScreen;
    SpadesPlayer[] players = new SpadesPlayer[4];
    SpadesPlayer playerOne = new SpadesPlayer();
    SpadesPlayer playerTwo = new SpadesPlayer();
    SpadesPlayer playerThree = new SpadesPlayer();
    SpadesPlayer playerFour = new SpadesPlayer();
    TrickDecider decider = new TrickDecider(playerOne, playerTwo, playerThree, playerFour);
    private int teamOnePoints;
    private int teamTwoPoints;
    private boolean cardPlaced = true;
    private boolean cardsInPile = false;
    private Font smallBold = new Font("SansSerif", Font.BOLD, 20);
    private Font mediumBold = new Font("SansSerif", Font.BOLD, 40);
    private Font bigBold = new Font("SansSerif", Font.BOLD, 120);

    Spades() {
        //adds values to players[] array to allow for-loop integration to iterate through player objects
        players[0] = playerOne;
        players[1] = playerTwo;
        players[2] = playerThree;
        players[3] = playerFour;

        //initiateGame() is used to shuffle the cards in the deck again and distribute new cards to the players
        initiateGame();

        //default constructor used, therefore components will cover the whole area
        crd = new CardLayout();

        //initialize content pane then add the CardLayout object that was just initialized
        cPane = getContentPane();
        cPane.setLayout(crd);

        //initialize all JPanels used in the program
        roundScreen = new JPanel();
        startScreen = new JPanel();
        ruleScreen = new JPanel();
        startBorder = new JPanel();
        playerScreen = new JPanel[5];
        playerButtons = new JPanel[5];
        bufferScreen = new JPanel();
        gameScreen = new JPanel[14];
        topScreen = new JPanel();

        for (int count = 0; count < 14; count++) {
            gameScreen[count] = new JPanel();
            gameScreen[count].setLayout(new BoxLayout(gameScreen[count], BoxLayout.X_AXIS));
        }

        for (int count = 0; count < 13; count++) {
            gameScreen[13].add(gameScreen[count]);
        }


        //sets layouts for all the JPanels
        roundScreen.setLayout(new BoxLayout(roundScreen, BoxLayout.Y_AXIS));
        topScreen.setLayout(new BoxLayout(topScreen, BoxLayout.X_AXIS));
        startScreen.setLayout(new BoxLayout(startScreen, BoxLayout.Y_AXIS));
        ruleScreen.setLayout(new BoxLayout(ruleScreen, BoxLayout.Y_AXIS));
        startBorder.setLayout(new GridLayout(4, 1));
        bufferScreen.setLayout(new GridLayout(3, 1));

        for (int count = 0; count < 5; count++) {
            playerScreen[count] = new JPanel();
            playerButtons[count] = new JPanel();
            playerScreen[count].setLayout(new BoxLayout(playerScreen[count], BoxLayout.X_AXIS));
            playerButtons[count].setLayout(new BoxLayout(playerButtons[count], BoxLayout.X_AXIS));
        }

        //initialize buttons and align them
        startScreen.add(Box.createVerticalStrut(250));
        topScreen.add(Box.createHorizontalStrut(710));
        play = new JButton("Play");
        play.setAlignmentX(Component.CENTER_ALIGNMENT);
        rules = new JButton("Rules");
        rules.setAlignmentX(Component.CENTER_ALIGNMENT);
        back = new JButton("Back");
        next = new JButton("Next");
        next.setFont(mediumBold);
        nextBid = new JButton("Next");
        nextBid.setVisible(false);
        nextRound = new JButton( "Next Round");
        nextRound.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons = new JButton[52]; // create array of JButtons

        for (int count = 0; count < 52; count++) {
            String buttonText = "Select";
            buttons[count] = new JButton(buttonText);
            buttons[count].addActionListener(this); // register listener
        }

        //initialize int counters
        cardsAdded = 0;
        screenCounter = 0;
        turnCounter = 0;
        teamOnePoints = 0;
        teamTwoPoints = 0;
        //initialize labels used for game UI
        winnerText = new JTextPane();
        winnerText.setEditable(false);
        winnerText.setOpaque(false);
        winnerText.setFont(mediumBold);
        StyledDocument styleDocument = winnerText.getStyledDocument();
        SimpleAttributeSet centerAlignment = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAlignment, StyleConstants.ALIGN_CENTER);
        styleDocument.setParagraphAttributes(0, styleDocument.getLength(), centerAlignment, false);
        JScrollPane scrollPane = new JScrollPane(winnerText);
        scrollPane.setAlignmentX(CENTER_ALIGNMENT);
        ruleText = new JTextArea();
        addRules();
        ruleText.setOpaque(false);
        labelText = "Player " + turnCounter + " turn";
        turnText = new JLabel(labelText);
        turnText.setFont(mediumBold);
        turnText.setHorizontalAlignment(JLabel.CENTER);
        gameTitle = new JLabel("SPADES");
        gameTitle.setFont(bigBold);
        gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        //initialize text fields for the bids
        bidText = new JTextField(15);
        bidText.setFont(smallBold);
        bidText.setText("Team One (Player 1 and 3) Insert Bid Here");
        bidText2 = new JTextField(15);
        bidText2.setFont(smallBold);
        bidText2.setText("Team Two (Player 2 and 4) Insert Bid Here");
        bidOne = new JTextField(15);
        bidOne.setFont(smallBold);
        bidOne.setText("Player 1 Insert Bid Here");
        bidTwo = new JTextField(15);
        bidTwo.setFont(smallBold);
        bidTwo.setText("Player 2 Insert Bid Here");
        bidThree = new JTextField(15);
        bidThree.setFont(smallBold);
        bidThree.setText("Player 3 Insert Bid Here");
        bidFour = new JTextField(15);
        bidFour.setFont(smallBold);
        bidFour.setText("Player 4 Insert Bid Here");
        //adding listeners to each button
        play.addActionListener(this);
        rules.addActionListener(this);
        back.addActionListener(this);
        next.addActionListener(this);
        nextBid.addActionListener(this);
        nextRound.addActionListener(this);
        bidText.addActionListener(this);
        bidText2.addActionListener(this );
        bidOne.addActionListener(this);
        bidTwo.addActionListener(this);
        bidThree.addActionListener(this);
        bidFour.addActionListener(this);
        //set visibility of bid text fields
        bidText.setEditable(false);
        bidText2.setEditable(false);
        bidText.setVisible(false);
        bidText2.setVisible(false);
        bidOne.setVisible(true);
        bidTwo.setVisible(false);
        bidThree.setVisible(false);
        bidFour.setVisible(false);
        //adds each element to their respective JPanel where they will be used
        startScreen.add(gameTitle);
        startScreen.add(play);
        startScreen.add(rules);
        ruleScreen.add(ruleText);
        ruleScreen.add(back);
        bufferScreen.add(next);
        bufferScreen.add(turnText);
        topScreen.add(nextBid);
        topScreen.add(bidOne);
        topScreen.add(bidTwo);
        topScreen.add(bidThree);
        topScreen.add(bidFour);
        roundScreen.add(nextRound);
        roundScreen.add(scrollPane);
        bufferScreen.add(bidText);
        bufferScreen.add(bidText2);
        startBorder.add(topScreen);
        startBorder.add(gameScreen[13]);
        startBorder.add(playerButtons[4]);
        startBorder.add(playerScreen[4]);

        //adds each JPanel to the content pane
        cPane.add("a", startScreen);  // first card is the button btn3  
        cPane.add("b", ruleScreen);
        cPane.add("c", startBorder);
        cPane.add("d", bufferScreen);
        cPane.add("e", roundScreen);
        
        addMouseListener( 
            new MouseAdapter() {
               public void mouseReleased( MouseEvent e )
               {
                   for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < players[j].getPlayerDeck().size(); k++) { //used to iterate through each player's deck
                            if (players[j].getPlayerDeck().get(k).getClicked() == true) { //used to find card ID that matches the corresponding button
                                if (decider.getCardsPlayed().size() == 0) { //checks if cards have been played yet (in the pile)
                                    cardsInPile = false;
                                } else {
                                    cardsInPile = true;
                                }

                                int eligibleCards = 0; //keeps track of eligible cards (cards able to be played based on suit)
                                int currentSpades = 0; //keeps track of how many spades a player has

                                for (int l = 0; l < players[j].getPlayerDeck().size(); l++) { //for-loop to check for eligible cards in player's hand
                                    if (cardsInPile == true) {
                                        if (players[j].getPlayerDeck().get(l).getSuit() == decider.determineLeadingSuit()) {
                                            eligibleCards++;
                                        }
                                    }

                                    if(players[j].getPlayerDeck().get(l).getSuit() == 4) { //for-loop to check for spades in player's hand
                                        currentSpades++;
                                    }
                                }

                                /* if-statements makes sure that a spade cannot be played as the FIRST card (if pile is empty)
                                   unless the player only has spades remaining in the hand */
                                if (!(cardsInPile == false && players[j].getPlayerCards().get(k).getSuit() == 4) ||
                                        (players[j].getPlayerDeck().size() == currentSpades)) {
                                    if (cardsInPile == false ||
                                            players[j].getPlayerDeck().get(k).getSuit() == decider.determineLeadingSuit() ||
                                            eligibleCards == 0) { /* if-statement checks if the card selected is able to be played
                                                                     (checks if they are first player, then if the card has the same
                                                                     suit as the leading suit, then checks if the player has no other
                                                                     eligible cards */
                                        gameScreen[screenCounter].add(players[j].getPlayerCards().get(k));
                                        decider.addCardToPile(players[j].getPlayerDeck().get(k));
                                        cardsAdded++;
                                        buttons[players[j].getPlayerDeck().get(k).getCardID()].setVisible(false);
                                        cardPlaced = true;
                                        players[j].getPlayerDeck().get(k).setClicked(false);
                                        crd.show(cPane, "d");
                                        System.out.println(cardsAdded);
                                        turnCounter++;
                                    } else {
                                        cardPlaced = false; //does not play a card if criteria is not met
                                    }
                                } else {
                                    cardPlaced = false; //does not play a card if criteria is not met
                                }
                            }
                        }
                    }
               } // end method mouseReleased
            } // end anonymous inner class
         );
    }
    
    //Adds Panels and cards to panel
    public void addPanels() {
        for (int i = 0; i < 4; i++) {
            for (int count = 0; count < 6; count++) {
                playerButtons[i].add(Box.createHorizontalStrut(10));
                playerButtons[i].add(buttons[players[i].getPlayerDeck().get(count).getCardID()]);
                playerButtons[i].add(players[i].getPlayerCards().get(count));
            }
            for (int count = 6; count < 13; count++) {
                playerScreen[i].add(buttons[players[i].getPlayerDeck().get(count).getCardID()]);
                playerScreen[i].add(players[i].getPlayerCards().get(count));
            }
            playerButtons[4].add(playerButtons[i]);
            playerScreen[4].add(playerScreen[i]);
        }
        for (int i = 0; i < 13; i++) {
            gameScreen[13].add(gameScreen[i]);
        }
    }
    // Shows Correct Cards for player
    public void showCards(int turn) {
        turn = turn % 4;
        if (turn == 0) {
            turn = 4;
            //roundCounter++;
        }
        for (int count = 0; count < 4; count++) {
            if (turn - 1 == count) {
                playerButtons[count].setVisible(true);
                playerScreen[count].setVisible(true);
            } else {
                playerButtons[count].setVisible(false);
                playerScreen[count].setVisible(false);
            }
        }
    }
    //Removes Panel content for next round or game
    public void removePanel()
    {
        for (int i = 0; i < 5; i ++)
        {
            playerButtons[i].removeAll();
            playerScreen[i].removeAll();
        }
        for (int i = 0; i < 13; i ++)
        {
            gameScreen[i].removeAll();
            gameScreen[i].setVisible(true);

        }
    }
    //Shuffles for next round
    public void shuffle()
    {
        //removes players hands
        players[1].removeHand();
        players[2].removeHand();
        players[3].removeHand();
        players[0].removeHand();
        initiateGame(); // Deals Cards
        removePanel();  // Clears Panels
        addPanels();    //adds cards to panel
        turnCounter = 1;    //sets game to beginning
        screenCounter = 0;
        //Resets bids textbox
        bidText.setEditable(false);
        bidText2.setEditable(false);
        bidText.setVisible(false);
        bidText2.setVisible(false);
        bidText.setText("Team One (Player 1 and 3) Insert Bid Here");
        bidText2.setText("Team Two (Player 2 and 4) Insert Bid Here");
        bidOne.setEditable(true);
        bidTwo.setEditable(true);
        bidThree.setEditable(true);
        bidFour.setEditable(true);
        bidOne.setVisible(true);
        bidTwo.setVisible(false);
        bidThree.setVisible(false);
        bidFour.setVisible(false);
        bidOne.setText("Player 1 Insert Bid Here");
        bidTwo.setText("Player 2 Insert Bid Here");
        bidThree.setText("Player 3 Insert Bid Here");
        bidFour.setText("Player 4 Insert Bid Here");
    }
    // Switches Screens and Performs Gameplay
    public void actionPerformed(ActionEvent event)
    {
        if(event.getSource() == bidOne) {  //Sets Bids for Players
            try {   //Exception handles for correct input
                if (Integer.parseInt(bidOne.getText()) >= 1 && Integer.parseInt(bidOne.getText()) <= 13) {
                    players[0].setCurrentBid(Integer.parseInt(bidOne.getText())); //Integer.parseInt(bid1)
                    bidOne.setEditable(false);
                    nextBid.setVisible(true);
                } else {
                    bidOne.setText("Invalid input! Enter a number 1-13.");
                }
            } catch (NumberFormatException e) {
                bidOne.setText("Invalid input! Enter a number 1-13.");
            }
        }
        else if(event.getSource() == bidTwo) {  //Sets Bids for Players
            try {   //Exception handles for correct input
                if (Integer.parseInt(bidTwo.getText()) >= 1 && Integer.parseInt(bidTwo.getText()) <= 13) {
                    players[1].setCurrentBid(Integer.parseInt(bidTwo.getText())); //Integer.parseInt(bid1)
                    bidTwo.setEditable(false);
                    nextBid.setVisible(true);
                } else {
                    bidTwo.setText("Invalid input! Enter a number 1-13.");
                }
            } catch (NumberFormatException e) {
                bidTwo.setText("Invalid input! Enter a number 1-13.");
            }
        }
        else if(event.getSource() == bidThree) {  //Sets Bids for Players
            try {   //Exception handles for correct input
                if (players[0].getCurrentBid() == 13) {
                    bidThree.setText("Your teamate bid 13 so your bid is 0");
                    players[2].setCurrentBid(0); //Integer.parseInt(bid1)
                    bidThree.setEditable(false);
                    nextBid.setVisible(true);
                } else if (players[0].getCurrentBid() + Integer.parseInt(bidThree.getText()) > 13) {
                    bidThree.setText("Invalid input! Your team cannot bid higher than 13. Bid " + (13 - players[0].getCurrentBid()) + " or Lower");
                } else if (Integer.parseInt(bidThree.getText()) >= 1 && Integer.parseInt(bidThree.getText()) <= 13) {
                    players[2].setCurrentBid(Integer.parseInt(bidThree.getText())); //Integer.parseInt(bid1)
                    bidThree.setEditable(false);
                    nextBid.setVisible(true);
                }  else {
                    bidThree.setText("Invalid input! Enter a number 1-13.");
                }
            } catch (NumberFormatException e) {
                bidThree.setText("Invalid input! Enter a number 1-13.");
            }
        }
        else if(event.getSource() == bidFour) {  //Sets Bids for Players
            try {   //Exception handles for correct input
                if(players[1].getCurrentBid() == 13) {
                    bidFour.setText("Your teamate bid 13 so your bid is 0");
                    players[3].setCurrentBid(0); //Integer.parseInt(bid1)
                    bidFour.setEditable(false);
                    nextBid.setVisible(true); 
                } else if(players[1].getCurrentBid() + Integer.parseInt(bidFour.getText()) > 13) {
                    bidFour.setText("Invalid input! Your team cannot bid higher than 13. Bid " + (13 - players[1].getCurrentBid()) + " or Lower");
                } else if (Integer.parseInt(bidFour.getText()) >= 1 && Integer.parseInt(bidFour.getText()) <= 13) {
                    players[3].setCurrentBid(Integer.parseInt(bidFour.getText())); //Integer.parseInt(bid1)
                    bidFour.setEditable(false);
                    nextBid.setVisible(true); 
                } else {
                    bidFour.setText("Invalid input! Enter a number 1-13.");
                }
            } catch (NumberFormatException e) {
                bidFour.setText("Invalid input! Enter a number 1-13.");
            }
        } 
        else if (event.getSource() == play) { //Starts Game
            turnCounter++;
            addPanels();
            turnText.setText("Player 1 Turn");
            crd.show(cPane, "d");
        } 
        else if (event.getSource() == rules) {    //Sends user to rules screen
            crd.show(cPane, "b");
        } 
        else if (event.getSource() == next) {     //Sends user to game screen
            if (turnCounter < 5) {  //Makes Buttons Invisible if Bids have not been cast
                //nextBid.setVisible(true);
                for (int count = 0; count < 52; count++) {
                    buttons[count].setVisible(false);
                }
            } else if (turnCounter == 5) {      //Sets buttons visible after bids are made
                for (int count = 0; count < 52; count++) {
                    buttons[count].setVisible(true);
                }
            }
            showCards(turnCounter);     // Makes sure correct cards are shown
            crd.show(cPane, "c");

        } 
        else if (back == event.getSource()) { //Sends user back to start screen
            crd.show(cPane, "a");
        } 
        else if (nextRound == event.getSource()) {    //Sends user to buffer Screen
            if (turnCounter % 4 != 0) { // Shows which player is next
                labelText = "Player " + turnCounter % 4 + " turn";
            } else {
                labelText = "Player 4 Turn";
            }
            turnText.setText(labelText);
            gameScreen[screenCounter -1 ].setVisible(false);
            crd.show(cPane, "d");
            if (players[0].getPlayerDeck().size() == 0) {   //Shuffles When game is over
                labelText = "Player 1 Turn";
                turnText.setText(labelText);
                shuffle();
            }
        } 
        else {
            if(turnCounter < 5)
            {
                turnCounter++;
            }
            for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < players[j].getPlayerDeck().size(); k++) { //used to iterate through each player's deck
                            if (players[j].getPlayerDeck().get(k).getClicked() == true) { //used to find card ID that matches the corresponding button
                                if (decider.getCardsPlayed().size() == 0) { //checks if cards have been played yet (in the pile)
                                    cardsInPile = false;
                                } else {
                                    cardsInPile = true;
                                }

                                int eligibleCards = 0; //keeps track of eligible cards (cards able to be played based on suit)
                                int currentSpades = 0; //keeps track of how many spades a player has

                                for (int l = 0; l < players[j].getPlayerDeck().size(); l++) { //for-loop to check for eligible cards in player's hand
                                    if (cardsInPile == true) {
                                        if (players[j].getPlayerDeck().get(l).getSuit() == decider.determineLeadingSuit()) {
                                            eligibleCards++;
                                        }
                                    }

                                    if(players[j].getPlayerDeck().get(l).getSuit() == 4) { //for-loop to check for spades in player's hand
                                        currentSpades++;
                                    }
                                }

                                /* if-statements makes sure that a spade cannot be played as the FIRST card (if pile is empty)
                                   unless the player only has spades remaining in the hand */
                                if (!(cardsInPile == false && players[j].getPlayerCards().get(k).getSuit() == 4) ||
                                        (players[j].getPlayerDeck().size() == currentSpades)) {
                                    if (cardsInPile == false ||
                                            players[j].getPlayerDeck().get(k).getSuit() == decider.determineLeadingSuit() ||
                                            eligibleCards == 0) { /* if-statement checks if the card selected is able to be played
                                                                     (checks if they are first player, then if the card has the same
                                                                     suit as the leading suit, then checks if the player has no other
                                                                     eligible cards */
                                        gameScreen[screenCounter].add(players[j].getPlayerCards().get(k));
                                        decider.addCardToPile(players[j].getPlayerDeck().get(k));
                                        cardsAdded++;
                                        buttons[players[j].getPlayerDeck().get(k).getCardID()].setVisible(false);
                                        cardPlaced = true;
                                        players[j].getPlayerDeck().get(k).setClicked(false);
                                        crd.show(cPane, "d");
                                        //System.out.println(cardsAdded);
                                        turnCounter++;
                                    } else {
                                        cardPlaced = false; //does not play a card if criteria is not met
                                    }
                                } else {
                                    cardPlaced = false; //does not play a card if criteria is not met
                                }
                            }
                        }
                    }/*
            for (int i = 0; i < 52; i++) { //checks all 52 potential buttons (each button is linked to a card in the deck)
                if (buttons[i] == event.getSource()) {  // When Card Button is clicked
                    for (int j = 0; j < 4; j++) {
                        for (int k = 0; k < players[j].getPlayerDeck().size(); k++) { //used to iterate through each player's deck
                            if (players[j].getPlayerDeck().get(k).getCardID() == i) { //used to find card ID that matches the corresponding button
                                if (decider.getCardsPlayed().size() == 0) { //checks if cards have been played yet (in the pile)
                                    cardsInPile = false;
                                } else {
                                    cardsInPile = true;
                                }

                                int eligibleCards = 0; //keeps track of eligible cards (cards able to be played based on suit)
                                int currentSpades = 0; //keeps track of how many spades a player has

                                for (int l = 0; l < players[j].getPlayerDeck().size(); l++) { //for-loop to check for eligible cards in player's hand
                                    if (cardsInPile == true) {
                                        if (players[j].getPlayerDeck().get(l).getSuit() == decider.determineLeadingSuit()) {
                                            eligibleCards++;
                                        }
                                    }

                                    if(players[j].getPlayerDeck().get(l).getSuit() == 4) { //for-loop to check for spades in player's hand
                                        currentSpades++;
                                    }
                                }

                                // if-statements makes sure that a spade cannot be played as the FIRST card (if pile is empty)
                                //   unless the player only has spades remaining in the hand
                                if (!(cardsInPile == false && players[j].getPlayerCards().get(k).getSuit() == 4) ||
                                        (players[j].getPlayerDeck().size() == currentSpades)) {
                                    if (cardsInPile == false ||
                                            players[j].getPlayerDeck().get(k).getSuit() == decider.determineLeadingSuit() ||
                                            eligibleCards == 0) { // if-statement checks if the card selected is able to be played
                                                                  //   (checks if they are first player, then if the card has the same
                                                                  //   suit as the leading suit, then checks if the player has no other
                                                                //  eligible cards 
                                        gameScreen[screenCounter].add(players[j].getPlayerCards().get(k));
                                        decider.addCardToPile(players[j].getPlayerDeck().get(k));
                                        cardsAdded++;
                                        buttons[i].setVisible(false);
                                        cardPlaced = true;
                                        turnCounter++;
                                    } else {
                                        cardPlaced = false; //does not play a card if criteria is not met
                                    }
                                } else {
                                    cardPlaced = false; //does not play a card if criteria is not met
                                }
                            }
                        }
                    }
                }
            }*/
            if (turnCounter == 1) {
                nextBid.setVisible(false);
            }
            else if (turnCounter == 2) {
                bidOne.setVisible(false);
                bidTwo.setVisible(true);
                nextBid.setVisible(false);
            }
            else if (turnCounter == 3) {
                bidTwo.setVisible(false);
                bidThree.setVisible(true);
                nextBid.setVisible(false);
            }
            else if (turnCounter == 4) {
                bidThree.setVisible(false);
                bidFour.setVisible(true);
                nextBid.setVisible(false);
            }
            if (turnCounter == 5) { //Allows users to place bids
                //next.setVisible(false);
                bidFour.setVisible(false);
                bidText.setVisible(true);
                bidText2.setVisible(true);
                String points = "Team 1 Current Bid: " + (players[2].getCurrentBid() + players[0].getCurrentBid()) + "   ~   " +
                                "Team 1 Total Tricks: " + (players[0].getCurrentTricksWon() + players[2].getCurrentTricksWon());
                bidText.setText( points );
                points = "Team 2 Current Bid: " + (players[1].getCurrentBid() + players[3].getCurrentBid()) + "   ~   " +
                                "Team 2 Total Tricks: " + (players[1].getCurrentTricksWon() + players[3].getCurrentTricksWon());
                bidText2.setText( points );
                //labelText = "Place Team Bids";
                nextBid.setVisible(false);
            } else {    //Sets turnText so players know whose turn it is
                next.setVisible(true);
                if (turnCounter % 4 != 0) {
                    labelText = "Player " + turnCounter % 4 + " turn";
                } else {
                    labelText = "Player 4 Turn";
                }
            }

            turnText.setText(labelText);
            if (cardsAdded == 4) {  //When 4 cards are played determines winner
                int trickWinner = decider.determineTrickWinner();
                turnCounter = trickWinner + 8;  //Winner of round to go first
                if (players[0].getPlayerDeck().size() == 0) {
                    turnCounter = 0;
                    //Gives teams correct amount of points based on bids and tricks won
                    if(players[0].getCurrentTricksWon() + players[2].getCurrentTricksWon() >= players[0].getCurrentBid()) {
                        teamOnePoints = teamOnePoints + ((players[0].getCurrentBid() + players[2].getCurrentBid()) * 10);
                        teamOnePoints = teamOnePoints + ((players[0].getCurrentTricksWon() + players[2].getCurrentTricksWon()) - players[0].getCurrentBid());
                    }

                    if(players[1].getCurrentTricksWon() + players[3].getCurrentTricksWon() >= players[1].getCurrentBid()) {
                        teamTwoPoints = teamTwoPoints + ((players[1].getCurrentBid() + players[3].getCurrentBid()) * 10);
                        teamTwoPoints = teamTwoPoints + ((players[1].getCurrentTricksWon() + players[3].getCurrentTricksWon()) - players[1].getCurrentBid());
                    }

                    winnerText.setText("Round Over \nTeam One Points: " + teamOnePoints + "\nTeam Two Points: " + teamTwoPoints);

                    for (int i = 0; i < 4; i++) {   //Updates the amount of tricks a player has won
                        players[i].setCurrentTricksWon(0);
                    }
                    if(teamTwoPoints >= 200 || teamOnePoints >= 200)    //Checks if Game Score has been reached
                    {
                        if (teamOnePoints > teamTwoPoints) {        //Prints who won the game
                            winnerText.setText("Game Over \nTeam One Points: " + teamOnePoints + "\nTeam Two Points: " + teamTwoPoints + "\nTeam One Wins!");
                        } else if (teamTwoPoints > teamOnePoints) {
                            winnerText.setText("Game Over \nTeam One Points: " + teamOnePoints + "\nTeam Two Points: " + teamTwoPoints + "\nTeam Two Wins!");
                        } else {
                            winnerText.setText("Game Over \nTeam One Points: " + teamOnePoints + "\nTeam Two Points: " + teamTwoPoints + "\nIt's a Tie!");
                        }
                        nextRound.setText("Play Again");
                        teamOnePoints = 0;
                        teamTwoPoints = 0;
                    }
                }
                else {  //prints who won the round
                    winnerText.setText("Player " + trickWinner + " won the trick!");
                }
                crd.show(cPane, "e");   //goes to round Screen
                cardsAdded = 0;
                roundScreen.add(gameScreen[screenCounter]); // switches to next game screen
                screenCounter++;
                if (!bidText.isEditable() && !bidText2.isEditable())    //Shows current bids and tricks won
                    {
                        String points = "Team 1 Current Bid: " + (players[2].getCurrentBid() + players[0].getCurrentBid()) + "   ~   " +
                                "Team 1 Total Tricks: " + (players[0].getCurrentTricksWon() + players[2].getCurrentTricksWon()) + 
                                "Team 1 Points " + teamOnePoints;
                        bidText.setText( points );
                        points = "Team 2 Current Bid: " + (players[1].getCurrentBid() + players[3].getCurrentBid()) + "   ~   " +
                                "Team 2 Total Tricks: " + (players[1].getCurrentTricksWon() + players[3].getCurrentTricksWon()) + 
                                "Team 2 Points " + teamTwoPoints;
                        bidText2.setText( points );
                    }
            } else if (cardPlaced == true) {    //Goes to buffer Screen
                crd.show(cPane, "d");
            }
        }
    }

    //creates a new deck (which shuffles the cards itself) and then distributes 13 cards to each player
    public void initiateGame()
    {
        CardsDeck deckTest = new CardsDeck();

        int currentCardInDeck = 51;

        for (int i = 0; i < 13; i++) {
            playerOne.addCardToHand(deckTest.getCard(currentCardInDeck));
            deckTest.removeCard(currentCardInDeck);
            currentCardInDeck--;

            playerTwo.addCardToHand(deckTest.getCard(currentCardInDeck));
            deckTest.removeCard(currentCardInDeck);
            currentCardInDeck--;

            playerThree.addCardToHand(deckTest.getCard(currentCardInDeck));
            deckTest.removeCard(currentCardInDeck);
            currentCardInDeck--;

            playerFour.addCardToHand(deckTest.getCard(currentCardInDeck));
            deckTest.removeCard(currentCardInDeck);
            currentCardInDeck--;
        }
    }

    // main method  
    public static void main(String argvs[])
    {
        // creating an object of the class CardLayoutExample1  
        Spades crdl = new Spades();

        // size is 300 * 300          
        crdl.setSize(1500, 750);
        crdl.setVisible(true);
        crdl.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    //simply appends the rules to the rules text area (first portion is copied from bicyclecard.com and the URL is provided)
    public void addRules() {
        ruleText.append("The Pack\n" +
                "The standard 52-card pack is used.\n\n" +
                "Rank of Suits\n" +
                "The spade suit is always trump.\n\n" +
                "Rank of Cards\n" +
                "A (high), K, Q, J, 10, 9, 8, 7, 6, 5, 4, 3, 2.\n\n" +
                "Object of the Game\n" +
                "To win at least the number of tricks bid.\n\n" +
                "The Bidding\n" +
                "Each player decides how many tricks they will be able to take. The player to the dealer's left starts the bidding and, in turn, each player states how many tricks they expect to win. There is only one round of bidding, and the minimum bid is One. Every player must make a bid; " +
                "\nno player may pass. No suit is named in the bid, for as the name of the game implies, spades are always trump.\n\n" +
                "The Play\n" +
                "The game is scored by hands, and the winner must make a certain number of points, which is decided before the game begins. Five hundred points is common, but 200 points is suitable for a short game. The player on the dealer's left makes the opening lead, and players " +
                "\nmust follow suit, if possible. If a player cannot follow suit, they may play a trump or discard. The trick is won by the player who plays the highest trump or if no trump was played, the player who played the highest card in the suit led. The player who wins the trick leads next. " +
                "\nPlay continues until none of the players have any cards left. Each hand is worth 13 tricks. Spades cannot be led unless played previously or player to lead has nothing but Spades in his hand.\n\n" +
                "How to Keep Score\n" +
                "For making the contract (the number of tricks bid), the player scores 10 points for each trick bid, plus 1 point for each overtrick.\n\n" +
                "For example, if the player's bid is Seven and they make seven tricks, the score would be 70. If the bid was Five and the player won eight tricks, the score would be 53 points: 50 points for the bid, and 3 points for the three overtricks. In some games, overtricks are " +
                "\ncalled \"bags\". Thus, the object is always to fulfill the bid exactly.\n\n" +
                "If the player \"breaks contract,\" that is, if they take fewer than the number of tricks bid, the score is 0. For example, if a player bids Four and wins only three tricks, no points are awarded.\n" +
                "One of the players is the scorer and writes the bids down, so that during the play and for the scoring afterward, this information will be available to all the players. When a hand is over, the scores should be recorded next to the bids, and a running score should be " +
                "\nkept so that players can readily see each other's total points. If there is a tie, then all players participate in one more round of play.");

        ruleText.append("\n\nAbove Rules Copied From: https://bicyclecards.com/how-to-play/spades" +
                        "\n\nSide Notes (Unique to our Implementation):" +
                        "\n     -Games are decided after a team hits 200." +
                        "\n     -Player 1 and Player 3 are automatically considered teammates and a part of Team 1." +
                        "\n     -Player 2 and Player 4 are automatically considered teammates and a part of Team 2." +
                        "\n     -At the beginning of each bid phase, each player is shown their respective hands one-by-one." +
                        "\n     -During play phase, each individual player's screen is revealed in order including cards in the pile and their current hand." +
                        "\n     -Before and after each player's turn, a screen is revealed showing which player's turn is next." +
                        "\n     -Game is intended to be played at initial window size. Resizing may cause performance issues." +
                        "\n     -Card's corresponding \"Select\" button will always be to the left");
    }

}