import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardDrawing extends JPanel implements ActionListener
{
    public Image suitImage;
    public ImageIcon suitIcon;
    public int suit;
    private Cards currentCard;
    private Color color;
    public CardDrawing(Cards card) {
        currentCard = card;
        suit = card.getSuit();

        if(suit == 1)
        {
            suitIcon = new ImageIcon(getClass().getResource( "clubs.png" ));
            color = Color.BLACK;
        }
        else if(suit == 2)
        {
            suitIcon = new ImageIcon(getClass().getResource( "diamonds.png" ));
            color = Color.RED;
        }
        else if(suit == 3)
        {
            suitIcon = new ImageIcon(getClass().getResource( "hearts.png" ));
            color = Color.RED;
        }
        else if(suit == 4)
        {
            suitIcon = new ImageIcon(getClass().getResource( "spade.png" ));
            color = Color.BLACK;
        }
    }


    public int getSuit()
    {
        return suit;
    }
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g ); // call superclass's paintComponent
        g.setColor(color);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawRoundRect( (getWidth()/2)-50, 10, 100, 160 ,30 ,30 );
        suitImage = suitIcon.getImage();
        Image smallSpade = suitImage.getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH);
        suitIcon = new ImageIcon(smallSpade);
        suitImage = suitIcon.getImage();
        g.drawImage(suitImage, (getWidth()/2)-45, 15, this);
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 2.2F);
        g.setFont(newFont);
        g.drawString (currentCard.getCardLabel(), getWidth()/2, 50);
        addMouseListener( 
            new MouseAdapter() {
               public void mouseReleased( MouseEvent e )
               {
                   currentCard.setClicked(true);
               } // end method mouseReleased
            } // end anonymous inner class
         ); // end call to addMouseListener
    }
    public void actionPerformed(ActionEvent e)
    {

    }
}