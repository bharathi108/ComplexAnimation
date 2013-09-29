import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class ComplexAnimationSwingApp extends JFrame implements ActionListener {

	private static final long serialVersionUID = 623383181217513898L;
	Timer timer = new Timer(100, this);
	DrawPanel draw = new DrawPanel();

	JFrame bg = new JFrame();
	JFrame manDrawing = new JFrame();
	StickMan man = new StickMan();

	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ComplexAnimationSwingApp gui = new ComplexAnimationSwingApp();
				gui.playMusic();
			}
		});
	}

	public ComplexAnimationSwingApp() {

		bg.addKeyListener(new ListenToKeys());
		bg.setFocusable(true);
		bg.setFocusTraversalKeysEnabled(false);
		bg.setResizable(false);
		bg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bg.getContentPane().add(man.draw);
		bg.setSize(1280, 800);
		bg.setVisible(true);
		man.draw.repaint();
	}

	public synchronized void playMusic() {
		try {
			InputStream in = new FileInputStream("aeon_0.wav");
			AudioStream as = new AudioStream(in);
			AudioPlayer.player.start(as);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	class DrawPanel extends JPanel {
		private static final long serialVersionUID = -9091687230503558275L;

		public void paintComponent(Graphics g) {

			timer.start();

//			Image background = new ImageIcon("background.png").getImage();
//			g.drawImage(background, xBackground, 0, this);
//			g.drawImage(background, xBackground + 1280, 0, this);
//			g.drawImage(background, xBackground - 1280, 0, this);
			
			man.draw.repaint();

		}
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	class ListenToKeys implements KeyListener {

		private final Set<Integer> pressed = new HashSet<Integer>();

		public synchronized void keyPressed(KeyEvent e) {
			pressed.add(e.getKeyCode());
			if (pressed.contains(KeyEvent.VK_UP)
					&& pressed.contains(KeyEvent.VK_RIGHT)) {
				man.setVelocityX(10 + man.getSpeed()); 
				man.setVelocityY(10);
				man.setWalking(false);
				man.setJumping(true);
				man.setCrouching(false);
				man.setCrouchingAndWalking(false);
				man.setDirectionBefore(1);
				man.walk();
				man.jump();
				if (man.getxBackground() > bg.getWidth() * -1) {
					man.setxBackground(man.getxBackground() - man.getVelocityX());
				} else {
					man.setxBackground(0);
				}

			}

			else if (pressed.contains(KeyEvent.VK_UP)
					&& pressed.contains(KeyEvent.VK_LEFT)) {
				// repaint();
				man.setVelocityX(-10 - man.getSpeed());
				man.setVelocityY(10);
				man.setWalking(false);
				man.setJumping(true);
				man.setCrouching(false);
				man.setCrouchingAndWalking(false);
				man.setDirectionBefore(-1);
				man.walk();
				man.jump();
				} else if (pressed.contains(KeyEvent.VK_DOWN)
					&& pressed.contains(KeyEvent.VK_RIGHT)) {
				man.setVelocityX(10 + man.getSpeed());
				man.setWalking(false);
				man.setJumping(false);
				man.setCrouching(false);
				man.setCrouchingAndWalking(true);
				man.setDirectionBefore(1);
				man.walk();
				if (man.getxBackground() > bg.getWidth() * -1) {
					man.setxBackground(man.getxBackground() - man.getVelocityX());
				} else {
					man.setxBackground(0);
				}
			}

			else if (pressed.contains(KeyEvent.VK_DOWN)
					&& pressed.contains(KeyEvent.VK_LEFT)) {
				man.setVelocityX(-10 - man.getSpeed());
				man.setWalking(false);
				man.setJumping(false);
				man.setCrouching(false);
				man.setCrouchingAndWalking(true);
				man.setDirectionBefore(-1);
				man.walk();
			}

			else if (pressed.contains(KeyEvent.VK_RIGHT)) {
				man.setVelocityX(10 + man.getSpeed());
				man.setWalking(true);
				man.setJumping(false);
				man.setCrouching(false);
				man.setCrouchingAndWalking(false);
				man.setDirectionBefore(1);
				man.walk();
				if (man.getxBackground() > bg.getWidth() * -1) {
					man.setxBackground(man.getxBackground() - man.getVelocityX());
				} else {
					man.setxBackground(0);
				}
			}

			else if (pressed.contains(KeyEvent.VK_LEFT)) {
				man.setVelocityX(-10 - man.getSpeed());
				man.setWalking(true);
				man.setJumping(false);
				man.setCrouching(false);
				man.setCrouchingAndWalking(false);
				man.setDirectionBefore(-1);
				man.walk();
			} else if (pressed.contains(KeyEvent.VK_UP)) {
				man.setVelocityY(10);
				man.jump();
				man.setWalking(false);
				man.setJumping(true);
				man.setCrouching(false);
				man.setCrouchingAndWalking(false);
			} else if (pressed.contains(KeyEvent.VK_DOWN)) {
				man.setVelocityX(0);
				man.setWalking(false);
				man.setJumping(false);
				man.setCrouching(true);
				man.setCrouchingAndWalking(false);
				man.walk();
			} else if (pressed.contains(KeyEvent.VK_D)) {
				man.setSpeed(man.getSpeed() + 2);
			} else if (pressed.contains(KeyEvent.VK_A)) {
				man.setSpeed(man.getSpeed() - 2);
				if (man.getSpeed() < 0) {
					man.setSpeed(0);
				}
			}
		}

		public synchronized void keyReleased(KeyEvent e) {
			if (pressed.contains(KeyEvent.VK_UP)) {
				man.setVelocityY(0);
			}
			else if (pressed.contains(KeyEvent.VK_LEFT)) {
				man.setVelocityX(0);
				man.setWalking(false);
				man.setJumping(false);
				man.setCrouching(false);
				man.setCrouchingAndWalking(false);
			}
			else if (pressed.contains(KeyEvent.VK_RIGHT)) {
				man.setVelocityX(0);
				man.setWalking(false);
				man.setJumping(false);
				man.setCrouching(false);
				man.setCrouchingAndWalking(false);
			}
			pressed.remove(e.getKeyCode());

		}

		public synchronized void keyTyped(KeyEvent e) {
		}
	}

}