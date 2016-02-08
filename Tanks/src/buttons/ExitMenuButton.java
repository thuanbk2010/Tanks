package buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import Main.*;
import javax.imageio.ImageIO;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class ExitMenuButton extends JButton implements ActionListener {


	private BufferedImage button;
	
    public ExitMenuButton(String label) {
        super(label);
        setBorder(null);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        addActionListener(this);
    }
	
    
	private void collectButton() {
		try {                
			button = ImageIO.read(getClass().getResourceAsStream("/img/MainMenu/exit button.png"));
		} catch (Exception ex) {
//			System.out.println("The testButton file requested does not exist! Please fix this before contueing!");
			Main.error(ex);
		}
	}
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        collectButton();
        g.drawImage(button, 0, 0, null);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(166, 41);
    }


	@Override
	public void actionPerformed(ActionEvent arg0) {
		Main.exitGame();
	}
    
}