import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

import com.nokia.mid.ui.gestures.GestureEvent;
import com.nokia.mid.ui.gestures.GestureInteractiveZone;
import com.nokia.mid.ui.gestures.GestureListener;
import com.nokia.mid.ui.gestures.GestureRegistrationManager;

public class ACanvas extends GameCanvas implements Runnable, GestureListener {

	private final AMidlet midlet;

	Player suaraKoin, suaraKuman;
	InputStream mediaKuman, mediaKoin;
	
	Thread t;
	Graphics g;
	Sprite[] simpanKuman1;
	Sprite[] simpanKuman2;
	Sprite[] simpanKuman3;
	Sprite[] simpanNyawa;
	Sprite[] simpanKoin1;
	Sprite[] simpanKoin2;
	Sprite[] simpanKoin3;
	
	Player[] suaraKuman1;
	Player[] suaraKuman2;
	Player[] suaraKuman3;
	Player[] suaraKoin1;
	Player[] suaraKoin2;
	Player[] suaraKoin3;

	int width = 240;
	int height = 400;
	
	int speed = 3;

	public static int skor = 0;
	int nyawa = 3;
	int layar = 0;
	int jarak = 0;
	int posYawan11 = 0, posYawan12 = height, posYawan13 = height*2;
	int posYawan21 = 0, posYawan22 = height, posYawan23 = height*2;
	int posYawan31 = 0, posYawan32 = height, posYawan33 = height*2;
	int posYmatahari1 = 0, posYmatahari2 = height, posYmatahari3 = height * 2;
	int posYRoad1 = 0, posYRoad2 = height, posYRoad3 = height * 2;
	int posYKuman = 0;
	int posYPanci = height * 7;
	boolean bolPause = false;
	Image awan1, awan2, awan3;
	Image matahari;
	// sprite
	Image runImg;
	Sprite spRun;

	int[] seqRun = new int[] { 0, 0, 1, 1, 2, 2 };
	int[] seqRunKoin = new int[] { 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6 };
	int[] seqKuman = new int[] { 0, 0, 1, 1 };
	int cX, cY;

	// gameover
	boolean gameover = false;
	boolean lagiPause = false;
	// gameRunning
	private boolean running;

	// panci
	Image panci;

	// kuman
	Image kuman;

	// koin
	Image koin;

	// hati
	Image hatiNyawa;
	
	//boolean ngecek dia di luar laya
	boolean lagiDiLuar = false;
	
	private static int[][] arrSurvKoin2 = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },// 1
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },// 2
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },// 3
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },// 4
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },// 5
			{ 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },// 6
			{ 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },// 7
			{ 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 },// 8
			{ 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 },// 9
			{ 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 },// 10
			{ 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 },// 11
			{ 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },// 12
			{ 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },// 13
			{ 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0 },// 14
			{ 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0 },// 15
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 },// 16
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0 },// 17
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } // 18
	};
	private static int[][] arrSurvKoin3 = {
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },// 1
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },// 2
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },// 3
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },// 4
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },// 5
			{ 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0 },// 6
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },// 7
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },// 8
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },// 9
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },// 10
			{ 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0 },// 11
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },// 12
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },// 13
			{ 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },// 14
			{ 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0 },// 15
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },// 16
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },// 17
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } // 18
	};

	private static int[] arrNyawa = { 1, 1, 1 };

	// logic of jump
	public int control = 0;
	boolean jumping = false;
	boolean falling = false;

	public int tinggi = 15;

	// public int hMax = 180;
	public int vY;
	public double grav = 0.9;
	public int time = 0;
	public int dY = 0;

	// road
	Image road;

	// button
	Image kanan, kiri, lompat, pause,menuPause, keMenu, resume;

	protected ACanvas(AMidlet midlet) {
		super(true);
		this.midlet = midlet;
		g = getGraphics();
		t = new Thread(this);
		setFullScreenMode(true);

		GestureInteractiveZone giz = new GestureInteractiveZone(
				GestureInteractiveZone.GESTURE_ALL);
		GestureRegistrationManager.register(this, giz);
		GestureRegistrationManager.setListener(this, this);
	}

	public void run() {

		initImage();
		initChar();
		// initKoin();
		// initKuman();
		initSurvival();
		initNyawa();
		while (running) {
			
			while(bolPause){
		    	g.drawRegion(menuPause, 0, 0, menuPause.getWidth(), menuPause.getHeight(), 5, 0, 0, Graphics.LEFT | Graphics.TOP);	
				g.drawRegion(resume, 0, 0, resume.getWidth(), resume.getHeight(), 5, 95,
						120, Graphics.LEFT | Graphics.BOTTOM);
				g.drawRegion(keMenu, 0, 0, keMenu.getWidth(), keMenu.getHeight(), 5, 95,
						320, Graphics.LEFT | Graphics.BOTTOM);
				lagiPause = true;
				flushGraphics();
					
			}
			drawMatahari();
			drawawan();
			drawRoad();
			drawChar();
			drawSurvival();
			// draw();
			survival();
			drawInput();
			drawNyawa();
			String s = " Score: " + skor;
			Image img = Image.createImage(150, 20);
			Graphics gr = img.getGraphics();
			gr.drawString(s, 0, 0, Graphics.TOP | Graphics.LEFT);
			g.drawRegion(img, 0, 0, 150, 20, Sprite.TRANS_ROT90, 220, 0,
					Graphics.TOP | Graphics.LEFT);
			/*String lala = Integer.toString(skor);
			Scoring.drawScore(lala);*/
			if (gameover) {
				midlet.gameOver();
			}
			jump();
			flushGraphics();
			try {
				Thread.sleep(1000 / 30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		RecordHighScore.save(Integer.toString(skor));
	}

	public void startGame() {
		running = true;
		t.start();
	}

	public void stop() {
		running = false;
		speed = 3;
		skor = 0;
	}

	private void initImage() {
		try {
			awan1 = Image.createImage("/sky1.png");
			awan2 = Image.createImage("/sky2.png");
			awan3 = Image.createImage("/sky3.png");
			matahari = Image.createImage("/sun.png");
			runImg = Image.createImage("/baksooo.png");
			road = Image.createImage("/ROAD.png");
			koin = Image.createImage("/koinsp.png");
			//panci = Image.createImage("/pipo_pan.png");
			kuman = Image.createImage("/kuman.png");
			kanan = Image.createImage("/kanan.png");
			kiri = Image.createImage("/kiri.png");
			lompat = Image.createImage("/jump.png");
			pause = Image.createImage("/tombolpause.png");
			menuPause = Image.createImage("/pause.png");
			keMenu = Image.createImage("/menupause.png");
			resume = Image.createImage("/resume.png");
			hatiNyawa = Image.createImage("/hati.png");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void initChar() {
		spRun = new Sprite(runImg, 49, 44);
		spRun.setFrameSequence(seqRun);
		cX = 60;
		cY = 10;
	}
	
	private void initNyawa() {
		simpanNyawa = new Sprite[3];
		int ht = 0;
		for (int i = 0; i < arrNyawa.length; i++) {
			if (arrNyawa[i] == 1) {
				simpanNyawa[ht] = new Sprite(hatiNyawa, 40, 34);
				simpanNyawa[ht].setTransform(5);
				ht++;
			}
		}
	}

	private void drawNyawa() {
		for (int i = 0; i < 3; i++) {
			simpanNyawa[i].setPosition(205, (i * 40) + 275);
			simpanNyawa[i].paint(g);
		}
	}

	private void drawChar() {
		/*
		 * if(!jumping && !falling){ spRun.nextFrame(); } else
		 * spRun.setFrame(0);
		 * 
		 * /*if(jumping){ spRun.setFrame(3); spRun.nextFrame(); } else
		 * if(falling){
		 * 
		 * } else { spRun.nextFrame(); if (control == 2){ spRun.prevFrame();
		 * spRun.prevFrame(); control = 0; } control++; }
		 */
		cY -= 3;
		if (spRun.getX() < 0) {
			nyawa--;
			if (nyawa == 0) {
				gameover = true;
				running = false;
			}
			spRun.setTransform(5);
			spRun.setPosition(30, 30);
			spRun.paint(g);
		} else {
			spRun.setTransform(5);
			spRun.setPosition(cX, cY);
			spRun.paint(g);
		}
		for (int i = 0; i < simpanKoin1.length; i++) {
			if ((simpanKoin1[i].collidesWith(spRun, true))) {
				// System.out.println("bisa");
				skor += 100;
				try {
					if(i>0){
						suaraKoin1[i-1].deallocate();
						suaraKoin1[i-1].stop();
					}
					suaraKoin1[i].start();
				} catch (MediaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				simpanKoin1[i].setVisible(false);
			}
		}
		for (int i = 0; i < simpanKoin2.length; i++) {
			if ((simpanKoin2[i].collidesWith(spRun, true))) {
				// System.out.println("bisa");
				skor += 100;
				try {
					if(i>0){
						suaraKoin2[i-1].deallocate();
						suaraKoin2[i-1].stop();
					}
					suaraKoin2[i].start();
				} catch (MediaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				simpanKoin2[i].setVisible(false);
				
			}
		}
		for (int i = 0; i < simpanKoin3.length; i++) {
			if ((simpanKoin3[i].collidesWith(spRun, true))) {
				// System.out.println("bisa");
				skor += 100;
				try {
					if(i>0){
						suaraKoin3[i-1].deallocate();
						suaraKoin3[i-1].stop();
					}
					suaraKoin3[i].start();
				} catch (MediaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				simpanKoin3[i].setVisible(false);
			}
		}
		for (int i = 0; i < simpanKuman1.length; i++) {
			if ((simpanKuman1[i].collidesWith(spRun, true))) {
				nyawa--;
				try {
					if(i>0){
						suaraKuman1[i-1].deallocate();
						suaraKuman1[i-1].stop();
					}
					suaraKuman1[i].start();
				} catch (MediaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				simpanKuman1[i].setVisible(false);
				simpanNyawa[nyawa].setVisible(false);
				if (nyawa == 0) {
					gameover = true;
					running = false;
				}
			}
		}
		for (int i = 0; i < simpanKuman2.length; i++) {
			if ((simpanKuman2[i].collidesWith(spRun, true))) {
				nyawa--;
				try {
					if(i>0){
						suaraKuman2[i-1].deallocate();
						suaraKuman2[i-1].stop();
					}
					suaraKuman2[i].start();
				} catch (MediaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				simpanKuman2[i].setVisible(false);
				simpanNyawa[nyawa].setVisible(false);
				if (nyawa == 0) {
					gameover = true;
					running = false;
				}
			}
		}
		for (int i = 0; i < simpanKuman3.length; i++) {
			if ((simpanKuman3[i].collidesWith(spRun, true))) {
				nyawa--;
				try {
					if(i>0){
						suaraKuman3[i-1].deallocate();
						suaraKuman3[i-1].stop();
					}
					suaraKuman3[i].start();
				} catch (MediaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				simpanKuman3[i].setVisible(false);
				simpanNyawa[nyawa].setVisible(false);
				if (nyawa == 0) {
					gameover = true;
					running = false;
				}
			}
		}
		
	}

	private void drawInput() {
		g.drawRegion(kiri, 0, 0, kiri.getWidth(), kiri.getHeight(), 5, 0, 60,
				Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(kanan, 0, 0, kanan.getWidth(), kanan.getHeight(), 5, 0,
				160, Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(lompat, 0, 0, lompat.getWidth(), lompat.getHeight(), 5, 0,
				400, Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(pause, 0, 0, pause.getWidth(), pause.getHeight(), 5, 195,
				275, Graphics.LEFT | Graphics.BOTTOM);
		
		
		// speed+=3;
		// posYKuman+=3;

		posYRoad1 -= speed;
		posYRoad2 -= speed;
		posYRoad3 -= speed;

		if (posYRoad1 < -height) {
			posYRoad1 = height * 2;
		}
		if (posYRoad2 < -height) {
			posYRoad2 = height * 2;
		}
		if (posYRoad3 < -height) {
			posYRoad3 = height * 2;
		}

	}

	private void drawawan() {
		g.drawRegion(awan1, 0, 0, awan1.getWidth(), awan1.getHeight(), 5, 0, posYawan11, Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(awan1, 0, 0, awan1.getWidth(), awan1.getHeight(), 5, 0, posYawan12, Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(awan1, 0, 0, awan1.getWidth(), awan1.getHeight(), 5, 0, posYawan13, Graphics.LEFT | Graphics.BOTTOM);

        posYawan11-=(speed-1);
        posYawan12-=(speed-1);
        posYawan13-=(speed-1);

        if (posYawan11 < -height) {
        posYawan11 = height*2;
        }
        if (posYawan12 < -height) {
        posYawan12 = height*2;
        }
        if (posYawan13 < -height) {
        posYawan13 = height*2;
        }
        
        g.drawRegion(awan2, 0, 0, awan2.getWidth(), awan2.getHeight(), 5, 0, posYawan21, Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(awan2, 0, 0, awan2.getWidth(), awan2.getHeight(), 5, 0, posYawan22, Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(awan2, 0, 0, awan2.getWidth(), awan2.getHeight(), 5, 0, posYawan23, Graphics.LEFT | Graphics.BOTTOM);

        posYawan21-=speed;
        posYawan22-=speed;
        posYawan23-=speed;

        if (posYawan21 < -height) {
        posYawan21 = height*2;
        }
        if (posYawan22 < -height) {
        posYawan22 = height*2;
        }
        if (posYawan23 < -height) {
        posYawan23 = height*2;
        }
        g.drawRegion(awan3, 0, 0, awan3.getWidth(), awan3.getHeight(), 5, 0, posYawan31, Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(awan3, 0, 0, awan3.getWidth(), awan3.getHeight(), 5, 0, posYawan32, Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(awan3, 0, 0, awan3.getWidth(), awan3.getHeight(), 5, 0, posYawan33, Graphics.LEFT | Graphics.BOTTOM);

        posYawan31-=(speed+1);
        posYawan32-=(speed+1);
        posYawan33-=(speed+1);

        if (posYawan31 < -height) {
        posYawan31 = height*2;
        }
        if (posYawan32 < -height) {
        posYawan32 = height*2;
        }
        if (posYawan33 < -height) {
        posYawan33 = height*2;
        }
	}

	private void initSurvival() {
		simpanKoin1 = new Sprite[0];
		simpanKoin2 = new Sprite[32];
		simpanKoin3 = new Sprite[29];
		simpanKuman1 = new Sprite[0];
		simpanKuman2 = new Sprite[0];
		simpanKuman3 = new Sprite[0];
		suaraKoin1 = new Player[0];
		suaraKoin2 = new Player[32];
		suaraKoin3 = new Player[29];
		suaraKuman1 = new Player[0];
		suaraKuman2 = new Player[0];
		suaraKuman3 = new Player[0];
		mediaKuman = getClass().getResourceAsStream("/sfx/BAKTERI.mp3");
		mediaKoin = getClass().getResourceAsStream("/sfx/KOIN.mp3");
		try {
			suaraKoin = Manager.createPlayer(mediaKoin, "audio/x-mp3");
			suaraKuman = Manager.createPlayer(mediaKuman, "audio/x-mp3");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int aa = 0;
		for (int i = 0; i < arrSurvKoin2.length; i++) {
			for (int j = 0; j < arrSurvKoin2[i].length; j++) {
				if (arrSurvKoin2[i][j] == 1) {
					simpanKoin2[aa] = new Sprite(koin, 20, 20);
					simpanKoin2[aa].setFrameSequence(seqRunKoin);
					simpanKoin2[aa].setTransform(5);
					simpanKoin2[aa].setPosition(j * 20, 400 + (i * 20));
					suaraKoin2[aa] = suaraKoin;
					aa++;
				}
			}
		}

		int bb = 0;
		for (int ii = 0; ii < arrSurvKoin3.length; ii++) {
			for (int jj = 0; jj < arrSurvKoin3[ii].length; jj++) {
				if (arrSurvKoin3[ii][jj] == 1) {
					simpanKoin3[bb] = new Sprite(koin, 20, 20);
					simpanKoin3[bb].setFrameSequence(seqRunKoin);
					simpanKoin3[bb].setTransform(5);
					simpanKoin3[bb].setPosition(jj * 20, 800 + (ii * 20));
					suaraKoin3[bb] = suaraKoin;
					bb++;
				}
			}
		}

	}

	private void drawSurvival() {
		for (int i = 0; i < simpanKoin1.length; i++) {
			simpanKoin1[i].nextFrame();
			simpanKoin1[i].setPosition(simpanKoin1[i].getX(),
					simpanKoin1[i].getY() - speed);
			simpanKoin1[i].paint(g);
		}
		// speed=0;

		for (int j = 0; j < simpanKoin2.length; j++) {
			simpanKoin2[j].nextFrame();
			simpanKoin2[j].setPosition(simpanKoin2[j].getX(),
					simpanKoin2[j].getY() - speed);
			simpanKoin2[j].paint(g);
		}
		// speed=0;

		for (int k = 0; k < simpanKoin3.length; k++) {
			simpanKoin3[k].nextFrame();
			simpanKoin3[k].setPosition(simpanKoin3[k].getX(),
					simpanKoin3[k].getY() - speed);
			simpanKoin3[k].paint(g);
		}
		// speed=0;

		for (int i = 0; i < simpanKuman1.length; i++) {
			simpanKuman1[i].nextFrame();
			simpanKuman1[i].setPosition(simpanKuman1[i].getX(),
					simpanKuman1[i].getY() - speed);
			simpanKuman1[i].paint(g);
		}
		// speed=0;

		for (int j = 0; j < simpanKuman2.length; j++) {
			simpanKuman2[j].nextFrame();
			simpanKuman2[j].setPosition(simpanKuman2[j].getX(),
					simpanKuman2[j].getY() - speed);
			simpanKuman2[j].paint(g);
		}
		// speed=0;

		for (int k = 0; k < simpanKuman3.length; k++) {
			simpanKuman3[k].nextFrame();
			simpanKuman3[k].setPosition(simpanKuman3[k].getX(),
					simpanKuman3[k].getY() - speed);
			simpanKuman3[k].paint(g);
		}
		// speed=0;

	}

	private void survival() {
		jarak = jarak + speed;
		//System.out.println("jarak" + jarak);

		if(layar%4==0 && jarak > 400){			
			//System.out.println("layar" + layar);
			//System.out.println("speed" + speed);
			speed+=2;			
		}
		
		if (jarak > 400) {
		//	System.out.println("in!");
			layar++;
			jarak = 0;
			Random acak = new Random();
			int hasil = 1 + acak.nextInt(14);
			pilihStage(hasil);

		}
	}
	
	private void resetGame(){
		
	}

	private void pilihStage(int x) {
		Stage ranStage = new Stage(x);
		//System.out.println("x " + x);
		//System.out.println("layar " + layar);
		//System.out.println("jml " + ranStage.getJml());
		//System.out.println("Stage " + ranStage.getStage().toString());
		if (layar % 3 == 1) {
			simpanKoin1 = new Sprite[ranStage.getJml()];			
			simpanKuman1 = new Sprite[ranStage.getJmlKuman()];
			suaraKoin1 = new Player[ranStage.getJml()];
			suaraKuman1 = new Player[ranStage.getJmlKuman()];
			int aa = 0;
			int bb = 0;
			for (int ii = 0; ii < ranStage.getStage().length; ii++) {
				for (int jj = 0; jj < ranStage.getStage()[ii].length; jj++) {
					if (ranStage.getStage()[ii][jj] == 1) {
						simpanKoin1[aa] = new Sprite(koin, 20, 20);
						simpanKoin1[aa].setFrameSequence(seqRunKoin);
						simpanKoin1[aa].setTransform(5);
						simpanKoin1[aa].setPosition(jj * 20, 800 + (ii * 20));
						suaraKoin1[aa] = suaraKoin;
						aa++;
					} else {
						if (ranStage.getStage()[ii][jj] == 2) {
							simpanKuman1[bb] = new Sprite(kuman, 40, 40);
							simpanKuman1[bb].setFrameSequence(seqKuman);
							simpanKuman1[bb].setTransform(5);
							simpanKuman1[bb].setPosition(jj * 20,
									800 + (ii * 20));
							suaraKuman1[bb] = suaraKuman;
							bb++;
						}
					}
				}
			}
		} else {
			if (layar % 3 == 2) {
				simpanKoin2 = new Sprite[ranStage.getJml()];
				simpanKuman2 = new Sprite[ranStage.getJmlKuman()];
				suaraKoin2 = new Player[ranStage.getJml()];
				suaraKuman2 = new Player[ranStage.getJmlKuman()];
				int aa = 0;
				int bb = 0;
				for (int ii = 0; ii < ranStage.getStage().length; ii++) {
					for (int jj = 0; jj < ranStage.getStage()[ii].length; jj++) {
						if (ranStage.getStage()[ii][jj] == 1) {
							simpanKoin2[aa] = new Sprite(koin, 20, 20);
							simpanKoin2[aa].setFrameSequence(seqRunKoin);
							simpanKoin2[aa].setTransform(5);
							simpanKoin2[aa].setPosition(jj * 20,
									800 + (ii * 20));
							suaraKoin2[aa] = suaraKoin;
							aa++;
						} else {
							if (ranStage.getStage()[ii][jj] == 2) {
								simpanKuman2[bb] = new Sprite(kuman, 40, 40);
								simpanKuman2[bb].setFrameSequence(seqKuman);
								simpanKuman2[bb].setTransform(5);
								simpanKuman2[bb].setPosition(jj * 20,
										800 + (ii * 20));
								suaraKuman2[bb] = suaraKuman;
								bb++;
							}
						}
					}
				}
			} else {
				if (layar % 3 == 0) {					
					simpanKoin3 = new Sprite[ranStage.getJml()];
					simpanKuman3 = new Sprite[ranStage.getJmlKuman()];
					suaraKoin3 = new Player[ranStage.getJml()];
					suaraKuman3 = new Player[ranStage.getJmlKuman()];
					int aa = 0;
					int bb = 0;
					for (int ii = 0; ii < ranStage.getStage().length; ii++) {
						for (int jj = 0; jj < ranStage.getStage()[ii].length; jj++) {
							if (ranStage.getStage()[ii][jj] == 1) {
								simpanKoin3[aa] = new Sprite(koin, 20, 20);
								simpanKoin3[aa].setFrameSequence(seqRunKoin);
								simpanKoin3[aa].setTransform(5);
								simpanKoin3[aa].setPosition(jj * 20,
										800 + (ii * 20));
								suaraKoin3[aa] = suaraKoin;
								aa++;
							} else {
								if (ranStage.getStage()[ii][jj] == 2) {
									simpanKuman3[bb] = new Sprite(kuman, 40, 40);
									simpanKuman3[bb].setFrameSequence(seqKuman);
									simpanKuman3[bb].setTransform(5);
									simpanKuman3[bb].setPosition(jj * 20,
											800 + (ii * 20));
									suaraKuman3[bb] = suaraKuman;
									bb++;
								}
							}
						}
					}
				}

			}
		}

	}

	private void drawRoad() {
		g.drawRegion(road, 0, 0, road.getWidth(), road.getHeight(), 5, 0,
				posYRoad1, Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(road, 0, 0, road.getWidth(), road.getHeight(), 5, 0,
				posYRoad2, Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(road, 0, 0, road.getWidth(), road.getHeight(), 5, 0,
				posYRoad3, Graphics.LEFT | Graphics.BOTTOM);
	
		posYPanci -= speed;
	}

	private void drawMatahari() {

		g.setColor(112, 200, 225);
		g.fillRect(0, 0, getWidth(), getHeight());
		/*g.drawRegion(matahari, 0, 0, matahari.getWidth(), matahari.getHeight(), 5, 0,
				posYmatahari1, Graphics.LEFT | Graphics.BOTTOM);*/
		g.drawRegion(matahari, 0, 0, matahari.getWidth(), matahari.getHeight(), 5, 0,
				posYmatahari3, Graphics.LEFT | Graphics.BOTTOM);
		/*g.drawRegion(matahari, 0, 0, matahari.getWidth(), matahari.getHeight(), 5, 0,
				posYmatahari3, Graphics.LEFT | Graphics.BOTTOM);*/

		posYmatahari1 -= 1;
		posYmatahari2 -= 1;
		posYmatahari3 -= 1;

		if (posYmatahari1 < -height) {
			posYmatahari1 = height * 2;
		}
		if (posYmatahari2 < -height) {
			posYmatahari2 = height * 2;
		}
		if (posYmatahari3 < -height) {
			posYmatahari3 = height * 2;
		}

	}

	private void jump() {
		if(cY < -spRun.getWidth()){
			lagiDiLuar = true;
		}
		if(lagiDiLuar){
			nyawa--;
			simpanNyawa[nyawa].setVisible(false);
			if (nyawa == 0) {
				gameover = true;
				//running = false;
			}
			cY = 60;
			cX = 180;
			falling = true;
			lagiDiLuar = false;
		}
		/*if(turun){
			cX-=3;
			if(cX < 60){
				cX = 60;
				turun = false;
			}
		}*/
		if (jumping) {
			// ntar dicoba lagi

			// System.out.println("jumping");
			time++;

			dY = (int) (16 - (time * grav));
			cX += dY;
			if (cX > 170) {
				jumping = false;
				falling = true;
				vY = 0;
				time = 0;
			}

			/*
			 * tinggi--; cX += tinggi; if (tinggi < 0) { jumping = false;
			 * falling = true; }
			 */
		}

		if (falling) {
			// ntar dicoba lagi

			// System.out.println("falling");
			time++;
			dY = (int) (vY * time + (0.5) * (grav) * (time * time));
			cX -= dY;
			if (cX < 60) {
				falling = false;
				vY = 0;
				time = 0;
				cX = 60;
			}

			/*
			 * tinggi++; cX -= tinggi; if (tinggi > 15) { falling = false; cX =
			 * 60; }
			 */
		}
	}

	public void gestureAction(Object container,
			GestureInteractiveZone gestureInteractiveZone,
			GestureEvent gestureEvent) {
		// TODO Auto-generated method stub
		int x = gestureEvent.getStartX();
		int y = gestureEvent.getStartY();

		if (x >= 0 && x <= 60 && y >= 0 && y <= 60) {
			spRun.nextFrame();
			if (control == 2) {
				spRun.prevFrame();
				spRun.prevFrame();
				control = 0;
			}
			control++;
			cY -= 6;
		}
		if (y >= 100 && y <= 160 && x >= 0 && x <= 60) {
			spRun.nextFrame();
			if (control == 2) {
				spRun.prevFrame();
				spRun.prevFrame();
				control = 0;
			}
			control++;
			cY += 6;
		}

		switch (gestureEvent.getType()) {
		/*
		 * case GestureInteractiveZone.GESTURE_ALL: if(x >= 0 && x <= 60 && y >=
		 * 0 && y <= 60){ spRun.nextFrame(); if (control == 2){
		 * spRun.prevFrame(); spRun.prevFrame(); control = 0; } control++;
		 * cY-=3; } if(y >= 100 && y <= 160 && x >= 0 && x <= 60){
		 * spRun.nextFrame(); if (control == 2){ spRun.prevFrame();
		 * spRun.prevFrame(); control = 0; } control++; cY+=3; }
		 */
		case GestureInteractiveZone.GESTURE_TAP:
			if (y >= 340 && y <= 400 && x >= 0 && x <= 60) {
				if (!jumping && !falling) {
					jumping = true;
					// vY = 5;
				}
			}
			
			if (y >= 235 && y <= 280 && x >= 195 && x <= 240) {
				bolPause=true;
			}
			
			if(lagiPause){
				if (x >= 75 && x <= 120 && y >= 95 && y <= 140) {
					bolPause=false;
				}
				if (x >= 75 && x <= 120 && y >= 275 && y <= 320) {
					bolPause=false;
					skor = 0;
					speed = 3;
					midlet.mainMenu();
				}
				
			}
		}
	}
}