import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class StickMan implements ActionListener {

	int x = 0;
	int y = 510;
	int velocityX;
	int velocityY;
	int speed = 0;
	int directionBefore = 0;
	int xBackground = 0;

	boolean isWalking = false;
	boolean isJumping = false;
	boolean isCrouching = false;
	boolean isCrouchingAndWalking = false;

	public int getxBackground() {
		return xBackground;
	}

	public void setxBackground(int xBackground) {
		this.xBackground = xBackground;
	}

	Thread animator;

	boolean k = false;
	boolean doneJumping = false;
	boolean isAtPeak = false;

	Timer timer = new Timer(500, this);

	DrawPanel draw = new DrawPanel();

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(int velocityX) {
		this.velocityX = velocityX;
	}

	public int getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(int velocityY) {
		this.velocityY = velocityY;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDirectionBefore() {
		return directionBefore;
	}

	public void setDirectionBefore(int directionBefore) {
		this.directionBefore = directionBefore;
	}

	public boolean isWalking() {
		return isWalking;
	}

	public void setWalking(boolean isWalking) {
		this.isWalking = isWalking;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	public boolean isCrouching() {
		return isCrouching;
	}

	public void setCrouching(boolean isCrouching) {
		this.isCrouching = isCrouching;
	}

	public boolean isCrouchingAndWalking() {
		return isCrouchingAndWalking;
	}

	public void setCrouchingAndWalking(boolean isCrouchingAndWalking) {
		this.isCrouchingAndWalking = isCrouchingAndWalking;
	}

	public boolean isK() {
		return k;
	}

	public void setK(boolean k) {
		this.k = k;
	}

	public boolean isDoneJumping() {
		return doneJumping;
	}

	public void setDoneJumping(boolean doneJumping) {
		this.doneJumping = doneJumping;
	}

	public boolean isAtPeak() {
		return isAtPeak;
	}

	public void setAtPeak(boolean isAtPeak) {
		this.isAtPeak = isAtPeak;
	}

	public void walk() {

		x = x + velocityX;
		if (x == 1250 && velocityX > 0) {
			velocityX = 0;
			x = x + velocityX;
		} else if (x == 10 && velocityX < 0) {
			velocityX = 0;
			x = x + velocityX;
		}
		draw.repaint();
	}

	public void jump() {

		if (isAtPeak == false) {
			isJumping = true;
			y -= 4;
		}
		if (y <= 380) {
			isAtPeak = true;
		}
		if (isAtPeak == true && y <= 510) {
			isJumping = true;
			y += 4;
			if (y == 510) {
				isJumping = false;
				doneJumping = true;
			}
		}
		draw.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		draw.repaint();

	}

	class DrawPanel extends JPanel {

		private static final long serialVersionUID = -9091787230503558275L;
		private Image[] imagesRight = new Image[3];
		private Image[] imagesLeft = new Image[3];
		private Image[] imagesCrouchingLeft = new Image[3];
		private Image[] imagesCrouchingRight = new Image[3];

		private int current = 0;

		public Image getNextImageRight() {
			if (current == imagesRight.length) {
				current = 0;
			}
			return imagesRight[current++];
		}

		public Image getNextImageLeft() {
			if (current == imagesLeft.length) {
				current = 0;
			}
			return imagesLeft[current++];
		}

		public Image getNextImageCrouchingLeft() {
			if (current == imagesCrouchingLeft.length) {
				current = 0;
			}
			return imagesCrouchingLeft[current++];
		}

		public Image getNextImageCrouchingRight() {
			if (current == imagesCrouchingRight.length) {
				current = 0;
			}
			return imagesCrouchingRight[current++];
		}

		public void paintComponent(Graphics g) {

			if (velocityY == 10 && k == false) {
				k = true;
				animator = new Thread(new animator());
				animator.start();
			}
			timer.start();
			Image background = new ImageIcon("background.png").getImage();
			imagesRight[0] = new ImageIcon("walk1.png").getImage();
			imagesRight[1] = new ImageIcon("walk2.png").getImage();
			imagesRight[2] = new ImageIcon("walk3.png").getImage();
			imagesLeft[0] = new ImageIcon("walk2left.png").getImage();
			imagesLeft[1] = new ImageIcon("walk1left.png").getImage();
			imagesLeft[2] = new ImageIcon("walk3left.png").getImage();
			imagesCrouchingLeft[0] = new ImageIcon("crouchleft.png").getImage();
			imagesCrouchingLeft[1] = new ImageIcon("crouch2left.png")
					.getImage();
			imagesCrouchingLeft[2] = new ImageIcon("crouch3left.png")
					.getImage();
			imagesCrouchingRight[0] = new ImageIcon("crouch.png").getImage();
			imagesCrouchingRight[1] = new ImageIcon("crouch2right.png")
					.getImage();
			imagesCrouchingRight[2] = new ImageIcon("crouch3right.png")
					.getImage();
			Image manWalkingRight = new ImageIcon(getNextImageRight())
					.getImage();
			Image manWalkingLeft = new ImageIcon(getNextImageLeft()).getImage();
			Image manCrouchingAndWalkingLeft = new ImageIcon(
					getNextImageCrouchingLeft()).getImage();
			Image manCrouchingAndWalkingRight = new ImageIcon(
					getNextImageCrouchingRight()).getImage();
			Image manJumpingRight = new ImageIcon("jump.png").getImage();
			Image manStillRight = new ImageIcon("walk2.png").getImage();
			Image manCrouchingRight = new ImageIcon("crouch.png").getImage();
			Image manJumpingLeft = new ImageIcon("jumpleft.png").getImage();
			Image manStillLeft = new ImageIcon("walk2left.png").getImage();
			Image manCrouchingLeft = new ImageIcon("crouchleft.png").getImage();
			g.drawImage(background, xBackground, 0, this);
			g.drawImage(background, xBackground + 1280, 0, this);
			g.drawImage(background, xBackground - 1280, 0, this);

			// Image cloud1 = new ImageIcon("cloud.png").getImage();
			try {
				if (velocityX > 0) {
					if (isWalking == true && isJumping == false
							&& isCrouching == false)
						g.drawImage(manWalkingRight, x, y, this);
					else if (isWalking == false && isJumping == false
							&& isCrouching == false
							&& isCrouchingAndWalking == false)
						g.drawImage(manStillRight, x, y, this);
					else if (isJumping == true)
						g.drawImage(manJumpingRight, x, y, this);
					else if (isCrouching == true)
						g.drawImage(manCrouchingRight, x, y + 27, this);
					else if (isCrouchingAndWalking == true)
						g.drawImage(manCrouchingAndWalkingRight, x, y + 27,
								this);
				} else if (velocityX < 0) {
					if (isWalking == true && isJumping == false
							&& isCrouching == false)
						g.drawImage(manWalkingLeft, x, y, this);
					else if (isWalking == false && isJumping == false
							&& isCrouching == false
							&& isCrouchingAndWalking == false)
						g.drawImage(manStillLeft, x, y, this);
					else if (isJumping == true)
						g.drawImage(manJumpingLeft, x, y, this);
					else if (isCrouching == true)
						g.drawImage(manCrouchingLeft, x, y + 27, this);
					else if (isCrouchingAndWalking == true)
						g.drawImage(manCrouchingAndWalkingLeft, x, y + 27, this);
				} else if (velocityX == 0 && directionBefore == 1) {
					if (isCrouching == true)
						g.drawImage(manCrouchingRight, x, y + 27, this);
					else if (isJumping == true)
						g.drawImage(manJumpingRight, x, y, this);
					else if (isWalking == false && isJumping == false
							&& isCrouching == false
							&& isCrouchingAndWalking == false)
						g.drawImage(manStillRight, x, y, this);
				} else if (velocityX == 0 && directionBefore == -1) {
					if (isCrouching == true)
						g.drawImage(manCrouchingLeft, x, y + 27, this);
					else if (isJumping == true)
						g.drawImage(manJumpingLeft, x, y, this);
					else if (isWalking == false && isJumping == false
							&& isCrouching == false
							&& isCrouchingAndWalking == false)
						g.drawImage(manStillLeft, x, y, this);
				} else if (velocityX == 0 && directionBefore == 0) {
					if (isCrouching == true)
						g.drawImage(manCrouchingRight, x, y + 27, this);
					else if (isJumping == true)
						g.drawImage(manJumpingRight, x, y, this);
					else if (isWalking == false && isJumping == false
							&& isCrouching == false
							&& isCrouchingAndWalking == false)
						g.drawImage(manStillRight, x, y, this);
				}
			} finally {
			}
		}
	}

	public class animator implements Runnable {

		@Override
		public void run() {
			long beforeTime, deltaTime, sleepTime;
			beforeTime = System.currentTimeMillis();
			while (doneJumping == false) {
				jump();
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
			doneJumping = false;
			isAtPeak = false;
			k = false;
		}

	}

}
