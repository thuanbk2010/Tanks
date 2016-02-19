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

import terrain.Terrain;

@SuppressWarnings("serial")
public class UpButton extends JButton implements ActionListener {

	private Terrain terrain;
	private BufferedImage button;

	public UpButton(String label, Terrain terrain) {
		super(label);
		setBorder(null);
		setBorderPainted(false);
		setContentAreaFilled(false);
		setOpaque(false);
		addActionListener(this);
		this.terrain = terrain;
	}


	private void collectButton() {
		try {                
			button = ImageIO.read(getClass().getResourceAsStream("/img/mainGame/button_up.png"));
		} catch (Exception ex) {
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
		return new Dimension(26, 28);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			this.setFocusable(false);
			if ( terrain.currentTank().getLaunchPower() < 80) {
				terrain.currentTank().setLaunchPower(terrain.currentTank().getLaunchPower() + 1);
				terrain.power.setText("" + terrain.currentTank().getLaunchPower());
				terrain.requestFocusInWindow();
			}
		}
		catch (Exception e){Main.error(e);}
	}

}
