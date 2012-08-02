import java.io.IOException;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

public class pCanvas extends GameCanvas implements Runnable {
	private final pMidlet midlet;
	
	Thread t;
	Graphics g;
    Sprite[] simpanKoin;
        
    // ArrayList<Sprite> a = new ArrayList<Sprite>();   
	int width = 240;
    int height = 320;

    int skor = 0;
    int nyawa = 3;

	int posYimg1 = 0, posYimg2 = height, posYimg3 = height*2;
	int posYAwan1 = 0, posYAwan2 = height, posYAwan3 = height*2;
	int posYRoad1 = 0, posYRoad2 = 80, posYRoad3 = width+40;
	int posYKoin = 0;
	int posYpanci = (height*2)+250;
	Image bg;
	Image awan;
	//sprite
	Image runImg;
	Sprite spRun;

	int[] seqRun = new int[] {0,0,1,1,2,2};
	int[] seqRunKoin = new int[]{0,0,1,1,2,2,3,3,4,4,5,5,6,6};
	int cX, cY;
	
	//gameover
	boolean gameover = false;
	
	//gameRunning
	private boolean running;
	
	//panci
	Image panci;

	//koin
	Image koin;
	
	//layermanager
	LayerManager lm;
	
	private static int[][] arrKoin = {
	{0,0,0,0,0,0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0,0,0,0,0,0},
	{0,0,0,0,1,0,0,1,0,0,0,0},
	{0,0,0,0,1,0,0,0,0,0,0,0},
	{0,0,0,0,1,0,0,0,1,0,0,0},
	{0,0,0,0,1,0,0,0,0,0,0,0},
	{0,0,0,0,1,0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0,0,0,1,0,0},
	{0,0,0,0,0,0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0,0,1,0,0,0},
	{0,0,0,0,1,0,0,0,0,0,0,0},	
	{0,0,0,0,1,0,0,0,0,1,0,0},
	{0,0,0,0,1,0,0,0,0,0,0,0},
	{0,0,0,0,1,0,0,0,0,0,0,0},
	{0,0,0,0,1,0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0,0,0,0,0,0},
	{0,0,0,0,0,1,0,0,1,0,0,0},
	{0,0,0,0,0,0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0,0,0,0,0,0},
	{0,0,0,0,0,0,0,0,0,0,0,0},
	{0,0,0,0,1,0,0,0,0,0,0,0}
	};
	
	//logic of jump
	boolean jumping = false;
	boolean falling = false;
	public int tinggi = 15;

	//road
	Image road;
	Image road2;
	Image road3;
	Sprite roads, roads2, roads3;

	//boolean untuk memeriksa apakah char sedang di atas road atau tidak
    boolean onRoad = false;

	protected pCanvas(pMidlet midlet) {
		super(true);
		this.midlet = midlet;
		g = getGraphics();
		t = new Thread(this);
		setFullScreenMode(true);
	}

	public void run() {
		initImage();
		initChar();
		initKoin();
		while (running) {	
			drawMatahari();
			drawAwan();
			drawRoad();
			drawChar();
			draw();
			g.setColor(0, 0, 0);
			g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
			g.drawString("Skor: "+skor, width, 0, Graphics.TOP | Graphics.RIGHT);
			g.drawString("Nyawa: "+nyawa, width, 20, Graphics.TOP | Graphics.RIGHT);
			if(gameover){
				midlet.gameOver();
			}
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

	public void startGame(){
		running = true;
		t.start();
	}

	public void stop(){
		running = false;
	}
	
	private void initImage(){
		try {
			bg = Image.createImage("/sky-tile.png");
			awan = Image.createImage("/matahari.png");
			runImg = Image.createImage("/bakso.png");
			road = Image.createImage("/1nyah.png");	
			road2 = Image.createImage("/road1-tile.png");
			road3 = Image.createImage("/road3-tile.png");
			koin = Image.createImage("/koinsp.png");
			panci = Image.createImage("/pipo_pan.png");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void initChar(){
		spRun = new Sprite(runImg, 44, 44);
		roads = new Sprite (road, 108, 60);
		roads2 = new Sprite (road2, 132, 80);
		roads3 = new Sprite (road3, 124, 80);
		spRun.setFrameSequence(seqRun);
		cX = 30;
		cY = 10;
	}
	
	private void initKoin(){
		simpanKoin = new Sprite[18];
	    int aa = 0;
		lm = new LayerManager();
		for(int i = 0; i < arrKoin.length; i++){
			for(int j = 0; j < arrKoin[i].length; j++){
				if(arrKoin[i][j]==1){
					simpanKoin[aa]= new Sprite(koin, 20, 20);
				    simpanKoin[aa].setFrameSequence(seqRunKoin);
					simpanKoin[aa].setTransform(5);
					simpanKoin[aa].setPosition(j*20, i*20);
				                                        
					lm.append(simpanKoin[aa]);
					aa++;
				}
			}
		}

	}
	
	private void draw(){
		for(int i = 0; i < 18; i++){
	        simpanKoin[i].nextFrame();
	                	
	        simpanKoin[i].setPosition(simpanKoin[i].getX(), simpanKoin[i].getY()-posYKoin);
	        simpanKoin[i].paint(g);
	    }
		posYKoin=0;
	}

	private void drawChar(){
		if(spRun.getX() < 0){
			nyawa--;
			if(nyawa == 0){
				gameover = true;
				running = false; 
			}
				spRun.setTransform(5);
				spRun.setPosition(30, 30);
				spRun.paint(g);
		}
		else{
			spRun.setTransform(5);
			spRun.setPosition(cX, cY);
			spRun.paint(g);
		}
	    for(int i = 0; i < 18; i++){    
	        	if((simpanKoin[i].collidesWith(spRun, true)) ){ 
	        		//	System.out.println("bisa");
	        	skor+=100;
	        	simpanKoin[i].setVisible(false);
	        	}
	    }
	    if(spRun.collidesWith(panci, 60, posYimg3, true)){
	    	midlet.menang();
	    }
	}
	
	private void gameInput(){
		int keystate = getKeyStates();
	
		if (((keystate & UP_PRESSED) != 0)) {
			jalanKeBelakang();
		}
		
		if (((keystate & DOWN_PRESSED) != 0)) {
			jalanKeDepan();	
		}
		if (((keystate & FIRE_PRESSED) != 0)) {
			if (!jumping && !falling) {
				jumping = true;
			}
		}
	}

	private void drawAwan(){
		g.drawRegion(bg, 0, 0, bg.getWidth(), bg.getHeight(), 5, 0, posYimg1, Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(bg, 0, 0, bg.getWidth(), bg.getHeight(), 5, 0, posYimg2, Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(bg, 0, 0, bg.getWidth(), bg.getHeight(), 5, 0, posYimg3, Graphics.LEFT | Graphics.BOTTOM);	
		
	}
	
	private void drawRoad(){

		roads.setPosition(0, posYRoad1);
		roads.setTransform(5);
		roads.paint(g);
		roads2.setPosition(0, posYRoad2);
		roads2.setTransform(5);
		roads2.paint(g);
		roads3.setPosition(0, posYRoad3);
		roads3.setTransform(5);
		roads3.paint(g);	
		g.drawRegion(panci, 0, 0, panci.getWidth(), panci.getHeight(), 5, 60, posYimg3, Graphics.LEFT | Graphics.TOP);	
		
	}

	private void drawMatahari(){

		g.setColor(112, 200, 225);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawRegion(awan, 0, 0, awan.getWidth(), awan.getHeight(), 5, 0, posYAwan1, Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(awan, 0, 0, awan.getWidth(), awan.getHeight(), 5, 0, posYAwan2, Graphics.LEFT | Graphics.BOTTOM);
		g.drawRegion(awan, 0, 0, awan.getWidth(), awan.getHeight(), 5, 0, posYAwan3, Graphics.LEFT | Graphics.BOTTOM);	

	
	}
	
	private void jalanKeDepan(){
		if (!jumping && !falling) {
			spRun.nextFrame();
		}else
			spRun.setFrame(0);
			
		posYKoin+=3;
		
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
	}
	
	private void jalanKeBelakang(){
		spRun.setTransform(Sprite.TRANS_MIRROR);
		if (!jumping && !falling) {
			spRun.nextFrame();
		}else
			spRun.setFrame(0);
		
		posYKoin-=3;
		
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

	}

	private void jump(){
	
		if (jumping) {
			//System.out.println("lompat");
			tinggi--;
			cX += tinggi;
			if (tinggi < 0) {
				jumping = false;
				falling = true;
			}
		}
	
		if (falling) {
			//System.out.println("jatoh");
			tinggi++;
			cX -= tinggi;
			/*if (tinggi > 15) {
			falling = false;
			cX = 30;
			onRoad = false;
			}*/
			if(spRun.collidesWith(roads, true)){
				//System.out.println("nabrak road1");
				tinggi = 15;
				falling = false;
			                onRoad = true;
			                cX = 30;
			}
			else if(spRun.collidesWith(roads2, true)){
				//System.out.println("nabrak road2");
				tinggi = 15;
				falling = false;
				                onRoad = true;
				                cX = 60;
			}
			else if(spRun.collidesWith(roads3, true)){
				//System.out.println("nabrak road3");
				tinggi = 15;
				falling = false;
			                onRoad = true;
			                cX = 60;
			}
			//kurang satu logic lagi, yaitu klo dia jatuh ga napak mana2, 
			//harusnya langsung ngurangin nyawa dan mulai dari awal lagi
		
		}
	
		if (onRoad) {
	        	if ((!spRun.collidesWith(roads2, true) && !jumping && !spRun.collidesWith(roads3, true) && !spRun.collidesWith(roads, true))) {
	        		falling = true;
	        		onRoad = false;
	        	}
	    }
	}

}