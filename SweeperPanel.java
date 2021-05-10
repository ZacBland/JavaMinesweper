import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SweeperPanel extends JPanel{
    private Toolkit kit = Toolkit.getDefaultToolkit();
    private final Image mineImg =  kit.getImage("images/mine.png");
    private final Image flagImg =  kit.getImage("images/flagged.png");
    private final Image[] imgNum = { kit.getImage("images/one.png"),
        kit.getImage("images/two.png"),
        kit.getImage("images/three.png"),
        kit.getImage("images/four.png"),
        kit.getImage("images/five.png"),
        kit.getImage("images/six.png"),
        kit.getImage("images/seven.png"),
        kit.getImage("images/eight.png")};
    private SweeperFrame frame;
    private MouseAdapter ml;

    public SweeperPanel(SweeperFrame f){
        frame = f;
        SweeperModel model = frame.getModel();
        setPreferredSize(new Dimension(frame.getModel().getTileWidth() * (frame.getModel().getTileArray())[0].length,
            frame.getModel().getTileHeight() * (frame.getModel().getTileArray()).length));

        ml = new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                int x = e.getX()/model.getTileWidth();
                int y = e.getY()/model.getTileHeight();

                if(frame.getModel().getFirstClick())
                    frame.getModel().setFirstClick(false);
    
                if(e.getModifiers() == MouseEvent.BUTTON1_MASK){
                    if(model.getTileArray()[y][x].getFlagged())
                        return;
                    if(model.getTileArray()[y][x].getBomb())
                        model.endGame();
                    model.getTileArray()[y][x].setRevealed(true);
    
                    if(model.getTileArray()[y][x].getAdjacentBombs(model.getTileArray()) == 0)
                        model.getTileArray()[y][x].revealAdjacentZeroes(model.getTileArray());
                }
                else if(e.getModifiers() == MouseEvent.BUTTON3_MASK){
                    if(model.getTileArray()[y][x].getRevealed())
                        return;
                    else
                        model.getTileArray()[y][x].setFlagged(!(model.getTileArray()[y][x].getFlagged()));
                }
                repaint();
                }
            };
        this.addMouseListener(ml);
    }

    public void rebuild(){
        SweeperModel model = frame.getModel();

        this.removeMouseListener(ml);
        ml = new MouseAdapter(){
            public void mousePressed(MouseEvent e) {
                int x = e.getX()/model.getTileWidth();
                int y = e.getY()/model.getTileHeight();

                if(frame.getModel().getFirstClick())
                    frame.getModel().setFirstClick(false);
    
                if(e.getModifiers() == MouseEvent.BUTTON1_MASK){
                    if(model.getTileArray()[y][x].getFlagged())
                        return;
                    if(model.getTileArray()[y][x].getBomb())
                        model.endGame();
                    model.getTileArray()[y][x].setRevealed(true);
    
                    if(model.getTileArray()[y][x].getAdjacentBombs(model.getTileArray()) == 0)
                        model.getTileArray()[y][x].revealAdjacentZeroes(model.getTileArray());
                }
                else if(e.getModifiers() == MouseEvent.BUTTON3_MASK){
                    if(model.getTileArray()[y][x].getRevealed())
                        return;
                    else
                        model.getTileArray()[y][x].setFlagged(!(model.getTileArray()[y][x].getFlagged()));
                }
                repaint();
                }
            };
        this.addMouseListener(ml);
        repaint();
        validate();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2D = (Graphics2D) g;
        
        SweeperModel model = frame.getModel();
        for(Tile[] row : model.getTileArray()){
            for(Tile t : row){
                int x = t.getX()*model.getTileWidth();
                int y = t.getY()*model.getTileHeight();
                t.updateTile(model.getTileArray());

                int[] topX = {x, x + model.getTileWidth(), x};
                int[] topY = {y, y, y + model.getTileHeight()};

                int[] botX = {x+model.getTileWidth(), x, x+model.getTileWidth()};
                int[] botY = {y+model.getTileHeight(), y+model.getTileHeight(), y};

                if(!t.getRevealed()){
                    g2D.setColor(Color.WHITE);
                    g2D.fillPolygon(topX, topY, 3);

                    g2D.setColor(Color.GRAY);
                    g2D.fillPolygon(botX, botY, 3);

                    int border = 4;
                    g2D.setColor(Color.LIGHT_GRAY);
                    g2D.fillRect(x + border, 
                        y + border, 
                        model.getTileWidth() - 2 * border, 
                        model.getTileHeight() - 2 * border);

                    if(t.getFlagged())
                        g2D.drawImage(flagImg,
                            t.getX() * model.getTileWidth(), 
                            t.getY() * model.getTileHeight(), 
                            model.getTileWidth(), 
                            model.getTileHeight(),
                            this);
                }
                else{
                    if(t.getBomb())
                        g2D.drawImage(mineImg, 
                            t.getX() * model.getTileWidth(), 
                            t.getY() * model.getTileHeight(), 
                            model.getTileWidth(), 
                            model.getTileHeight(),
                            this);
                    else if(t.getAdjacentBombs(model.getTileArray()) != 0)
                        g2D.drawImage(imgNum[t.getAdjacentBombs(model.getTileArray()) - 1],
                            t.getX() * model.getTileWidth(),
                            t.getY() * model.getTileHeight(),
                            model.getTileWidth(), 
                            model.getTileHeight(),
                            this);
                    
                    g2D.setColor(Color.GRAY);
                    g2D.setStroke(new BasicStroke(4));
                    g2D.drawRect(x, y, model.getTileWidth(), model.getTileHeight());
                }
            }
        }
    }
}