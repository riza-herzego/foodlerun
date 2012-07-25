import java.io.IOException;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

public class pCanvas extends GameCanvas implements Runnable {
	Thread t;
	Graphics g;
	
	int height = Math.max(super.getWidth(), super.getHeight());
    int width = Math.min(super.getWidth(), super.getHeight());
    
    int skor = 0;
	
	int posXimg1 = 0, posXimg2 = height, posXimg3 = height*2;
	int posXAwan = 0, posXAwan2 = height, posXAwan3 = height*2;
	
	Image bg;
	Image awan;
	//sprite
	Image runImg;
	Sprite spRun;	
    
	int[] seqRun = new int[] {0,0,1,1,2,2};
	int cX, cY;

	//koin
	Image koin;
	Sprite koins;
	boolean tampak = true;	
	
	//logic of jump
	boolean jumping = false;
	boolean falling = false;
	int tinggi = 12;
		
	//road
	Image road;
	
	//boolean untuk memeriksa apakah char sedang di atas road atau tidak
    boolean onRoad = false;
	
	protected pCanvas() {
		super(true);
		g = getGraphics();
		t = new Thread(this);
		setFullScreenMode(true);
	}

	public void run() {
		initImage();
		initChar();
		while (true) {
			drawMatahari();
			drawAwan();
			drawChar();
			drawKoin();
			g.setColor(0, 0, 0);
			g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
			g.drawString("Skor: "+skor, width, 0, Graphics.TOP | Graphics.RIGHT );
			jump();
			gameInput();
			flushGraphics();
			try {
				Thread.sleep(1000/30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	void startGame(){
		t.start();
	}
	
	void initImage(){
		try {
			bg = Image.createImage("/sky-tile.png");
			awan = Image.createImage("/matahari.png");
			runImg = Image.createImage("/bakso.png");	
			koin = Image.createImage("/koinsp.png");
			road = Image.createImage("/road1-tile.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}	
	
	void initChar(){
		spRun = new Sprite(runImg, 44, 44);
		koins = new Sprite(koin,20,20);
		spRun.setFrameSequence(seqRun);
		koins.setFrameSequence(seqRun);
		cX = 10;
		cY = height-runImg.getHeight();
	}
	
	void drawChar(){
		spRun.setPosition(cX, cY);
		if (!jumping && !falling) {
			spRun.nextFrame();
		}else
			spRun.setFrame(0);	
		spRun.paint(g);			
	}
	
	void drawKoin(){
		koins.setPosition(posXimg2, height-100);
		koins.nextFrame();
		koins.paint(g);
		if(spRun.collidesWith(koins, true)){
			skor+=100;
			koins.setVisible(!tampak);			
		} 
	}	
	
	void gameInput(){
		int keystate = getKeyStates();
		
		if (((keystate & LEFT_PRESSED) != 0)) {
				spRun.setTransform(2);
				cX -= 3;
		}
		if (((keystate & RIGHT_PRESSED) != 0)) {
				spRun.setTransform(0);
				cX += 3;
		}
		if ((keystate & FIRE_PRESSED) != 0) {
			if (!jumping && !falling) {
				jumping = true;
			}
		}
	}
	
	void drawAwan(){
			
			g.drawImage(bg, posXimg1, height, Graphics.LEFT | Graphics.BOTTOM);
			g.drawImage(bg, posXimg2, height, Graphics.LEFT | Graphics.BOTTOM);
			g.drawImage(bg, posXimg3, height, Graphics.LEFT | Graphics.BOTTOM);			
			
			posXimg1-=3;
			posXimg2-=3;
			posXimg3-=3;
			
			if (posXimg1 < -height) {
				posXimg1 = height;
			}
			if (posXimg2 < -height) {
				posXimg2 = height;
			}
			if (posXimg3 < -height) {
				posXimg3 = height;
			}
			
	}
	
	void drawMatahari(){
		
		g.setColor(112, 200, 225);
		g.fillRect(0, 0, getWidth(), getHeight());		
		g.drawImage(awan, posXAwan, 0, Graphics.LEFT | Graphics.TOP);
		g.drawImage(awan, posXAwan2, 0, Graphics.LEFT | Graphics.TOP);
		g.drawImage(awan, posXAwan3, 0, Graphics.LEFT | Graphics.TOP);
		
		posXAwan-=1;
		posXAwan2-=1;
		posXAwan3-=1;
		
		if (posXAwan < -height) {
			posXAwan = height;
		}
		if (posXAwan2 < -height) {
			posXAwan2 = height;
		}
		if (posXAwan3 < -height) {
			posXAwan3 = height;
		}
	}
		
	void jump(){		
		if (jumping) {
			tinggi--;
			cY -= tinggi;
			if (tinggi < 0) {
				jumping = false;
				falling = true;
			}
		}
		
		if (falling) {
			tinggi++;
			cY += tinggi;
			if (tinggi > 12) {
				falling = false;
				cY = height-runImg.getHeight();
				koins.setVisible(tampak);
				onRoad = false;
			}
			
		}
		
	}
	
}