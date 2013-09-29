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
	
	boolean isDoneJumping;
	boolean isAtPeak;
	boolean jumpOnce = false;

	public Image[] imagesRight = new Image[3];
	public Image[] imagesLeft = new Image[3];
	public Image[] imagesCrouchingLeft = new Image[3];
	public Image[] imagesCrouchingRight = new Image[3];
	Image manWalkingRight;
	Image manWalkingLeft;
	Image manCrouchingAndWalkingLeft;
	Image manCrouchingAndWalkingRight;
	Image manJumpingRight;
	Image manStillRight;
	Image manCrouchingRight;
	Image manJumpingLeft;
	Image manStillLeft;
	Image manCrouchingLeft;
	private int current = 0;

	public StickMan() {
	}

	public Image getManWalkingRight() {
		return manWalkingRight;
	}

	public void setManWalkingRight(Image manWalkingRight) {
		this.manWalkingRight = manWalkingRight;
	}

	public Image getManWalkingLeft() {
		return manWalkingLeft;
	}

	public void setManWalkingLeft(Image manWalkingLeft) {
		this.manWalkingLeft = manWalkingLeft;
	}

	public Image getManCrouchingAndWalkingLeft() {
		return manCrouchingAndWalkingLeft;
	}

	public void setManCrouchingAndWalkingLeft(Image manCrouchingAndWalkingLeft) {
		this.manCrouchingAndWalkingLeft = manCrouchingAndWalkingLeft;
	}

	public Image getManCrouchingAndWalkingRight() {
		return manCrouchingAndWalkingRight;
	}

	public void setManCrouchingAndWalkingRight(Image manCrouchingAndWalkingRight) {
		this.manCrouchingAndWalkingRight = manCrouchingAndWalkingRight;
	}

	public Image getManJumpingRight() {
		return manJumpingRight;
	}

	public void setManJumpingRight(Image manJumpingRight) {
		this.manJumpingRight = manJumpingRight;
	}

	public Image getManStillRight() {
		return manStillRight;
	}

	public void setManStillRight(Image manStillRight) {
		this.manStillRight = manStillRight;
	}

	public Image getManCrouchingRight() {
		return manCrouchingRight;
	}

	public void setManCrouchingRight(Image manCrouchingRight) {
		this.manCrouchingRight = manCrouchingRight;
	}

	public Image getManJumpingLeft() {
		return manJumpingLeft;
	}

	public void setManJumpingLeft(Image manJumpingLeft) {
		this.manJumpingLeft = manJumpingLeft;
	}

	public Image getManStillLeft() {
		return manStillLeft;
	}

	public void setManStillLeft(Image manStillLeft) {
		this.manStillLeft = manStillLeft;
	}

	public Image getManCrouchingLeft() {
		return manCrouchingLeft;
	}

	public void setManCrouchingLeft(Image manCrouchingLeft) {
		this.manCrouchingLeft = manCrouchingLeft;
	}

	
	public int getxBackground() {
		return xBackground;
	}

	public void setxBackground(int xBackground) {
		this.xBackground = xBackground;
	}

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

	public boolean isDoneJumping() {
		return isDoneJumping;
	}

	public void setDoneJumping(boolean isDoneJumping) {
		this.isDoneJumping = isDoneJumping;
	}

	public boolean isAtPeak() {
		return isAtPeak;
	}

	public void setAtPeak(boolean isAtPeak) {
		this.isAtPeak = isAtPeak;
	}

	public boolean isJumpOnce() {
		return jumpOnce;
	}

	public void setJumpOnce(boolean jumpOnce) {
		this.jumpOnce = jumpOnce;
	}

	
	public void walk() {

		x = x + velocityX;
		if (x >= 0 && x <= 1220){
			x = x + velocityX;	
		}
		else if (x >= 1220 && velocityX > 0) {
			x = 1220;
		} else if (x <= 0  && velocityX < 0) {
			x = 0;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}
}
