import java.io.IOException;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

public class pCanvas extends GameCanvas implements Runnable {
	Thread t;
	Graphics g;

	int width = 240;
    int height = 320;

    int skor = 0;

	int posYimg1 = 0, posYimg2 = height, posYimg3 = height*2;
	int posYAwan1 = 0, posYAwan2 = height, posYAwan3 = height*2;
	int posYRoad1 = 0, posYRoad2 = 80, posYRoad3 = width+50;

	Image bg;
	Image awan;
	//sprite
	Image runImg;
	Sprite spRun;

	int[] seqRun = new int[] {0,0,1,1,2,2};
	int cX, cY;

	//koin
	Image koin;
	TiledLayer koins;
	
	private static int[][] arrKoin = {
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,2,1,1,1,1,1,1,1},
		{1,1,1,1,2,1,1,1,1,1,1,1},
		{1,1,1,1,2,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1},
		{1,1,1,1,1,1,1,1,1,1,1,1}
	};
	
	//logic of jump
	boolean jumping = false;
	boolean falling = false;
	int tinggi = 12;

	//road
	Image road;
	Image road2;
	Image road3;
	Sprite roads, roads2, roads3;

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
		initKoin();
		while (true) {			
			drawMatahari();
			drawAwan();
			drawRoad();
			drawChar();
			g.setColor(0, 0, 0);
			g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
			g.drawString("Skor: "+skor, width, 0, Graphics.TOP | Graphics.RIGHT);
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
			road = Image.createImage("/1nyah.png");				
			road2 = Image.createImage("/road1-tile.png");
			road3 = Image.createImage("/road3-tile.png");
			koin = Image.createImage("/koinn.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void initChar(){
		spRun = new Sprite(runImg, 44, 44);
		roads = new Sprite (road, 108, 60);
		roads2 = new Sprite (road2, 132, 80);
		roads3 = new Sprite (road3, 124, 80);
		spRun.setFrameSequence(seqRun);
		cX = 30;
		cY = 10;
	}
	
	void initKoin(){
		koins = new TiledLayer(12, 16, koin, 20, 20);
		for(int row = 0; row < 16; ++row) {
			for(int column = 0; column < 12; ++column) {
				koins.setCell(column, row, arrKoin[row][column]);
			}
		}
		
	}

	void drawChar(){
		spRun.setTransform(5);
		spRun.setPosition(cX, cY);
		spRun.paint(g);
		if(spRun.collidesWith(koin, 0, 0, true)){
			skor+=100;
		}
	}

	void gameInput(){
		int keystate = getKeyStates();

		if (((keystate & UP_PRESSED) != 0)) {
			spRun.setTransform(2);
			if (!jumping && !falling) {
				spRun.nextFrame();
			}else
				spRun.setFrame(0);
			
			posYimg1+=3;
			posYimg2+=3;
			posYimg3+=3;

			if (posYimg1 < -height) {
				posYimg1 = height;
			}
			if (posYimg2 < -height) {
				posYimg2 = height;
			}
			if (posYimg3 < -height) {
				posYimg3 = height;
			}

			posYAwan1+=1;
			posYAwan2+=1;
			posYAwan3+=1;

			if (posYAwan1 < -height) {
				posYAwan1 = height;
			}
			if (posYAwan2 < -height) {
				posYAwan2 = height;
			}
			if (posYAwan3 < -height) {
				posYAwan3 = height;
			}
			
			posYRoad1+=3;
			posYRoad2+=3;
			posYRoad3+=3;

			if (posYRoad1 < -width) {
				posYRoad1 = width;
			}
			if (posYRoad2 < -width) {
				posYRoad2 = width;
			}
			if (posYRoad3 < -width) {
				posYRoad3 = width;
			}		

			koins.move(0, 3);

		}
		
		if (((keystate & DOWN_PRESSED) != 0)) {
			
			spRun.setTransform(0);
			if (!jumping && !falling) {
				spRun.nextFrame();
			}else
				spRun.setFrame(0);
			
			posYimg1-=3;
			posYimg2-=3;
			posYimg3-=3;

			if (posYimg1 < -height) {
				posYimg1 = height;
			}
			if (posYimg2 < -height) {
				posYimg2 = height;
			}
			if (posYimg3 < -height) {
				posYimg3 = height;
			}

			posYAwan1-=1;
			posYAwan2-=1;
			posYAwan3-=1;

			if (posYAwan1 < -height) {
				posYAwan1 = height;
			}
			if (posYAwan2 < -height) {
				posYAwan2 = height;
			}
			if (posYAwan3 < -height) {
				posYAwan3 = height;
			}
			
			posYRoad1-=3;
			posYRoad2-=3;
			posYRoad3-=3;

			if (posYRoad1 < -width) {
				posYRoad1 = width;
			}
			if (posYRoad2 < -width) {
				posYRoad2 = width;
			}
			if (posYRoad3 < -width) {
				posYRoad3 = width;
			}		

			koins.move(0, -3);
			
		}
		if ((keystate & FIRE_PRESSED) != 0) {
			if (!jumping && !falling) {
				jumping = true;
			}
		}
	}

	void drawAwan(){
			g.drawRegion(bg, 0, 0, bg.getWidth(), bg.getHeight(), 5, 0, posYimg1, Graphics.LEFT | Graphics.BOTTOM);
			g.drawRegion(bg, 0, 0, bg.getWidth(), bg.getHeight(), 5, 0, posYimg2, Graphics.LEFT | Graphics.BOTTOM);
			g.drawRegion(bg, 0, 0, bg.getWidth(), bg.getHeight(), 5, 0, posYimg3, Graphics.LEFT | Graphics.BOTTOM);			
			
            if(koins.getY() + 120 < 0) {
                koins.setPosition(koins.getY(), height);
            }
            koins.paint(g);
	}
	
	void drawRoad(){
		
			roads.setPosition(0, posYRoad1);
			roads.setTransform(5);
			roads.paint(g);
			roads2.setPosition(0, posYRoad2);
			roads2.setTransform(5);
			roads2.paint(g);
			roads3.setPosition(0, posYRoad3);
			roads3.setTransform(5);
			roads3.paint(g);
			
	}

	void drawMatahari(){

		g.setColor(112, 200, 225);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawRegion(awan, 0, 0, awan.getWidth(), awan.getHeight(), 5, 0, posYAwan1, Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(awan, 0, 0, awan.getWidth(), awan.getHeight(), 5, 0, posYAwan2, Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(awan, 0, 0, awan.getWidth(), awan.getHeight(), 5, 0, posYAwan3, Graphics.LEFT | Graphics.BOTTOM);			

		
	}

	void jump(){
		if (jumping) {
			tinggi--;
			cX += tinggi;
			if (tinggi < 0) {
				jumping = false;
				falling = true;
			}
		}

		if (falling) {
			tinggi++;
			cX -= tinggi;
			if (tinggi > 12) {
				falling = false;
				cX = 30;
				onRoad = false;
			}
			//biar menapak ke-roadnya
			else if(spRun.collidesWith(roads2, true)){
					falling = false;
	                onRoad = true;
	                cX = spRun.getX();
			}
		}
		
		if (onRoad) {
        	if ((!spRun.collidesWith(roads2, true) && !jumping)) {
        			falling = true;
        	}
        }

	}

}