import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

public class MySweeper extends JFrame
{
    private Game game;
    private JPanel panel;
    private JLabel label;
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args)
    {
        new MySweeper();
    }

    private MySweeper()
    {
        game = new Game(COLS, ROWS, BOMBS);
        game.Start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel() {
        label = new JLabel("Welcom!");
        add(label, BorderLayout.SOUTH);
    }

    private void initPanel() {
        panel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for(Coord coord:Ranges.getAllCoords()){
                    g.drawImage((Image)game.getBox(coord).image, coord.x*IMAGE_SIZE, coord.y*IMAGE_SIZE, this);
                }
            }
        };
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x,y);
                if(e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton(coord);
                if(e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton(coord);
                label.setText(getMassege());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x*IMAGE_SIZE, Ranges.getSize().y*IMAGE_SIZE));
        add(panel);
    }

    private String getMassege() {
        switch (game.getState())
        {
            case played: return "Think twice";
            case bombed:return "BA-DA-BOOM";
            case winner: return "Congratulations";
            default: return "Welcome";
        }
    }

    private void initFrame() {
        setDefaultCloseOperation ( WindowConstants . EXIT_ON_CLOSE );
        setTitle ( "Sweeper" );
        setResizable ( false );
        setVisible ( true );
        pack();
        setIconImage (getImage ( "icon" ));
        setLocationRelativeTo ( null );

    }

    private void setImages ()
    {
        for (Box box : Box.values()) {
            box.image = getImage(box.name().toLowerCase());
        }
    }
    private Image getImage (String name){
        String filename = "/img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
