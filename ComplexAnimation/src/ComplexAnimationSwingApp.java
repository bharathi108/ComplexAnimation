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

	Thread jumpAnimator;
	
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
		bg.getContentPane().add(draw);
		bg.setSize(1280, 800);
		bg.setVisible(true);
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
			if (man.getVelocityY() == 10 && man.isJumpOnce() == false) {
				man.setJumpOnce(true);
				jumpAnimator = new Thread(new Animator());
				jumpAnimator.start();
			}
			timer.start();
			man.imagesRight[0] = new ImageIcon("walk1.png").getImage();
			man.imagesRight[1] = new ImageIcon("walk2.png").getImage();
			man.imagesRight[2] = new ImageIcon("walk3.png").getImage();
			man.imagesLeft[0] = new ImageIcon("walk2left.png").getImage();
			man.imagesLeft[1] = new ImageIcon("walk1left.png").getImage();
			man.imagesLeft[2] = new ImageIcon("walk3left.png").getImage();
			man.imagesCrouchingLeft[0] = new ImageIcon("crouchleft.png").getImage();
			man.imagesCrouchingLeft[1] = new ImageIcon("crouch2left.png")
					.getImage();
			man.imagesCrouchingLeft[2] = new ImageIcon("crouch3left.png")
					.getImage();
			man.imagesCrouchingRight[0] = new ImageIcon("crouch.png").getImage();
			man.imagesCrouchingRight[1] = new ImageIcon("crouch2right.png")
					.getImage();
			man.imagesCrouchingRight[2] = new ImageIcon("crouch3right.png")
					.getImage();
			
			man.setManWalkingRight(new ImageIcon(man.getNextImageRight())
			.getImage());
			man.setManWalkingLeft(new ImageIcon(man.getNextImageLeft()).getImage());
			man.setManCrouchingAndWalkingLeft(new ImageIcon(
					man.getNextImageCrouchingLeft()).getImage());
			man.setManCrouchingAndWalkingRight(new ImageIcon(
					man.getNextImageCrouchingRight()).getImage());
			man.setManJumpingRight(new ImageIcon("jump.png").getImage());
			man.setManJumpingLeft(new ImageIcon("jumpleft.png").getImage());
			man.setManStillRight(new ImageIcon("walk2.png").getImage());
			man.setManCrouchingRight(new ImageIcon("crouch.png").getImage());
			man.setManStillLeft(new ImageIcon("walk2left.png").getImage());
			man.setManCrouchingLeft(new ImageIcon("crouchleft.png").getImage());

			Image background = new ImageIcon("background.png").getImage();
			g.drawImage(background, man.getxBackground(), 0, this);
			g.drawImage(background, man.getxBackground() + 1280, 0, this);

			try {
				if (man.getVelocityX() > 0) {
					if (man.isWalking() == true && man.isJumping() == false
							&& man.isCrouching() == false)
						g.drawImage(man.getManWalkingRight(), man.getX(), man.getY(), this);
					else if (man.isWalking() == false && man.isJumping() == false
							&& man.isCrouching() == false
							&& man.isCrouchingAndWalking() == false)
						g.drawImage(man.getManStillRight(), man.getX(), man.getY(), this);
					else if (man.isJumping() == true)
						g.drawImage(man.getManJumpingRight(), man.getX(), man.getY(), this);
					else if (man.isCrouching() == true)
						g.drawImage(man.getManCrouchingRight(), man.getX(), man.getY() + 27, this);
					else if (man.isCrouchingAndWalking() == true)
						g.drawImage(man.getManCrouchingAndWalkingRight(), man.getX(), man.getY() + 27,
								this);
				} else if (man.getVelocityX() < 0) {
					if (man.isWalking() == true && man.isJumping() == false
							&& man.isCrouching() == false)
						g.drawImage(man.getManWalkingLeft(), man.getX(), man.getY(), this);
					else if (man.isWalking() == false && man.isJumping() == false
							&& man.isCrouching() == false
							&& man.isCrouchingAndWalking() == false)
						g.drawImage(man.getManStillLeft(), man.getX(), man.getY(), this);
					else if (man.isJumping() == true)
						g.drawImage(man.getManJumpingLeft(), man.getX(), man.getY(), this);
					else if (man.isCrouching() == true)
						g.drawImage(man.getManCrouchingLeft(), man.getX(), man.getY() + 27, this);
					else if (man.isCrouchingAndWalking() == true)
						g.drawImage(man.getManCrouchingAndWalkingLeft(), man.getX(), man.getY() + 27, this);
				} else if (man.getVelocityX() == 0 && man.getDirectionBefore() == 1) {
					if (man.isCrouching() == true)
						g.drawImage(man.getManCrouchingRight(), man.getX(), man.getY() + 27, this);
					else if (man.isJumping() == true)
						g.drawImage(man.getManJumpingRight(), man.getX(), man.getY(), this);
					else if (man.isWalking() == false && man.isJumping() == false
							&& man.isCrouching() == false
							&& man.isCrouchingAndWalking() == false)
						g.drawImage(man.getManStillRight(), man.getX(), man.getY(), this);
				} else if (man.getVelocityX() == 0 && man.getDirectionBefore() == -1) {
					if (man.isCrouching() == true)
						g.drawImage(man.getManCrouchingLeft(), man.getX(), man.getY() + 27, this);
					else if (man.isJumping() == true)
						g.drawImage(man.getManJumpingLeft(), man.getX(), man.getY(), this);
					else if (man.isWalking() == false && man.isJumping() == false
							&& man.isCrouching() == false
							&& man.isCrouchingAndWalking() == false)
						g.drawImage(man.getManStillLeft(), man.getX(), man.getY(), this);
				} else if (man.getVelocityX() == 0 && man.getDirectionBefore() == 0) {
					if (man.isCrouching() == true)
						g.drawImage(man.getManCrouchingRight(), man.getX(), man.getY() + 27, this);
					else if (man.isJumping() == true)
						g.drawImage(man.getManJumpingRight(), man.getX(), man.getY(), this);
					else if (man.isWalking() == false && man.isJumping() == false
							&& man.isCrouching() == false
							&& man.isCrouchingAndWalking() == false)
						g.drawImage(man.getManStillRight(), man.getX(), man.getY(), this);
				}
			} finally {
			}
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
				cycle();
				if (man.getxBackground() > bg.getWidth() * -1) {
					man.setxBackground(man.getxBackground() - man.getVelocityX());
				} else {
					man.setxBackground(0);
				}
				draw.repaint();
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
				cycle();
				draw.repaint();
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
				draw.repaint();
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
				draw.repaint();
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
				draw.repaint();
			}

			else if (pressed.contains(KeyEvent.VK_LEFT)) {
				man.setVelocityX(-10 - man.getSpeed());
				man.setWalking(true);
				man.setJumping(false);
				man.setCrouching(false);
				man.setCrouchingAndWalking(false);
				man.setDirectionBefore(-1);
				man.walk();
				draw.repaint();
			} else if (pressed.contains(KeyEvent.VK_UP)) {
				man.setVelocityY(10);
				man.setWalking(false);
				man.setJumping(true);
				man.setCrouching(false);
				man.setCrouchingAndWalking(false);
				draw.repaint();
				cycle();
				
			} else if (pressed.contains(KeyEvent.VK_DOWN)) {
				man.setVelocityX(0);
				man.setWalking(false);
				man.setJumping(false);
				man.setCrouching(true);
				man.setCrouchingAndWalking(false);
				man.walk();
				draw.repaint();
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
			else if (pressed.contains(KeyEvent.VK_DOWN)) {
				//man.setVelocityX(0);
				man.setWalking(false);
				man.setJumping(false);
				man.setCrouching(false);
				man.setCrouchingAndWalking(false);
			}
			else if (pressed.contains(KeyEvent.VK_UP)
					&& pressed.contains(KeyEvent.VK_LEFT)) {
				man.setVelocityX(0);
				man.setWalking(false);
				man.setJumping(false);
				man.setCrouching(false);
				man.setCrouchingAndWalking(false);
			}
			else if (pressed.contains(KeyEvent.VK_UP)
					&& pressed.contains(KeyEvent.VK_RIGHT)) {
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
	public class Animator implements Runnable {

		@Override
		public void run() {
			long beforeTime, deltaTime, sleepTime;
			beforeTime = System.currentTimeMillis();
			while (man.isDoneJumping() == false) {
				cycle();
				deltaTime = System.currentTimeMillis() - beforeTime;
				sleepTime = 10 - deltaTime;
				if (sleepTime < 0) {
					sleepTime = 2;
				}
				try {
					Thread.sleep(sleepTime);
				} catch (Exception e) {
				}
				beforeTime = System.currentTimeMillis();
			}
			man.setDoneJumping(false);
			man.setAtPeak(false);
			man.setJumpOnce(false);
		}

	}
	
	public void cycle() {

		if (man.isAtPeak() == false) {
			man.setJumping(true);
			man.setY(man.getY() - 4);
		}
		if (man.getY() <= 380) {
			man.setAtPeak(true);
		}
		if (man.isAtPeak() == true && man.getY() <= 510) {
			man.setJumping(true);
			man.setY(man.getY() + 4);
			if (man.getY() == 510) {
				man.setJumping(false);
				man.setDoneJumping(true);
			}
		}
		draw.repaint();
	}
}