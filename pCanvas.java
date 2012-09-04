import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

import com.nokia.mid.ui.gestures.GestureEvent;
import com.nokia.mid.ui.gestures.GestureInteractiveZone;
import com.nokia.mid.ui.gestures.GestureListener;
import com.nokia.mid.ui.gestures.GestureRegistrationManager;

public class pCanvas extends GameCanvas implements Runnable, GestureListener {
	
        private final AMidlet midlet;
        
        Thread t;
        Graphics g;
	    Sprite[] simpanKoin;
	    Sprite[] simpanKuman;
	    Sprite[] simpanNyawa;
	    
        int width = 240;
	    int height = 400;
	
	    int skor = 0;
	    int nyawa = 3;

        int posYimg1 = 0, posYimg2 = height, posYimg3 = height*2;
        int posYAwan1 = 0, posYAwan2 = height, posYAwan3 = height*2;
        int posYRoad1 = 0, posYRoad2 = height, posYRoad3 = height*2;
        int posYKoin = 0; int posYKuman = 0;
        int posYPanci = height*7;
        
        Image bg;
        Image awan;
        //sprite
        Image runImg;
        Sprite spRun;

        int[] seqRun = new int[] {0,0,1,1,2,2};
        int[] seqRunKoin = new int[] {0,0,1,1,2,2,3,3,4,4,5,5,6,6};
        int[] seqKuman = new int[] {0,0,1,1};
        int cX, cY;

        //gameover
        boolean gameover = false;

        //gameRunning
        private boolean running;

        //panci
        Image panci;

        //kuman
        Image kuman;

        //koin
        Image koin;
        
        //hati
        Image hatiNyawa;

        private static int[][] arrKoin = {
           //1,2,3,4,5,6,7,8,9,10,11,12
	        {0,0,0,0,0,0,0,0,0,0,0,0},//1
	        {0,0,0,0,0,0,0,0,0,0,0,0},//2
	        {0,0,0,0,0,0,0,0,0,0,0,0},//3
	        {0,0,0,0,0,0,0,0,0,0,0,0},//4
	        {0,0,0,0,0,0,0,0,0,0,0,0},//5
	        {0,0,0,0,0,0,0,0,0,0,0,0},//6
	        {0,0,0,0,0,0,1,0,0,0,0,0},//7
	        {0,0,0,0,0,1,0,0,1,0,0,0},//8
	        {0,0,0,0,0,0,0,0,0,0,0,0},//9
	        {0,0,0,0,0,0,0,0,0,0,0,0},//10
	        {0,0,0,0,0,1,0,0,1,0,0,0},//11
	        {0,0,0,0,0,0,0,1,0,0,0,0},//12
	        {0,0,0,0,0,0,0,0,0,0,0,0},//13
	        {0,0,0,0,0,0,0,0,0,0,0,0},//14
	        {0,0,0,0,0,0,0,0,0,0,0,0},//15
	        {0,0,0,0,0,0,0,0,0,0,0,0},//16
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,1,0,1,0,0,1,0,0},//17
	        {0,0,0,0,0,0,0,0,0,0,0,0},//18
	        {0,0,0,0,0,0,0,0,0,1,0,0},//19
	        {0,0,0,0,1,0,0,0,0,0,0,0},//20
	        {0,0,0,0,0,0,0,0,0,0,0,0},//21
	        {0,0,0,0,1,0,0,1,0,1,0,0},//22
	        {0,0,0,0,0,0,0,0,0,0,0,0},//23
	        {0,0,0,0,0,0,0,0,0,0,0,0},//24
	        {0,0,0,0,0,0,0,0,0,0,0,0},//25
	        {0,0,0,0,0,0,0,0,0,0,0,0},//26
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},//27
	        {0,0,0,0,0,0,0,0,0,0,0,0},//28
	        {0,0,0,0,0,0,0,0,0,1,0,0},//29
	        {0,0,0,0,0,0,0,0,0,1,0,0},//30
	        {0,0,0,0,0,0,0,0,0,0,0,0},//31
	        {0,0,0,0,0,0,0,0,0,0,0,0},//32
	        {0,0,0,0,0,0,0,0,0,0,0,0},//33
	        {0,0,0,0,0,0,0,0,0,0,0,0},//34
	        {0,0,0,0,0,0,0,0,0,0,0,0},//35
	        {0,0,0,0,0,0,0,0,0,0,0,0},//36
	        {0,0,0,0,0,0,0,0,0,0,0,0},//37
	        {0,0,0,0,0,0,0,0,0,0,0,0},//38
	        {0,0,0,0,1,0,0,0,0,1,0,0},//39
	        {0,0,0,0,0,1,0,0,1,0,0,0},//40
	        {0,0,0,0,0,0,0,0,0,0,0,0},//41
	        {0,0,0,0,0,0,0,0,0,0,0,0},//42
	        {0,0,0,0,0,1,0,0,1,0,0,0},//43
	        {0,0,0,0,1,0,0,0,0,1,0,0},//44
	        {0,0,0,0,0,0,0,0,0,0,0,0},//45
	        {0,0,0,0,0,0,0,0,0,0,0,0},//46
	        {0,0,0,0,0,0,0,0,0,0,0,0},//47
	        {0,0,0,0,0,0,0,0,0,0,0,0},//48
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},//49
	        {0,0,0,0,0,0,1,0,0,0,0,0},//50
	        {0,0,0,0,1,0,0,0,0,0,0,0},//51
	        {0,0,0,0,0,0,1,0,0,0,0,0},//52
	        {0,0,0,0,1,0,0,0,0,0,0,0},//53
	        {0,0,0,0,0,0,1,0,0,0,0,0},//54
	        {0,0,0,0,0,0,0,0,0,0,0,0},//55
	        {0,0,0,0,0,0,0,0,0,0,0,0},//56
	        {0,0,0,0,0,0,0,0,0,0,0,0},//57
	        {0,0,0,0,0,0,0,0,0,0,0,0},//58
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},//59
	        {0,0,0,0,0,0,0,1,1,0,0,0},//60
	        {0,0,0,0,0,0,0,1,1,0,0,0},//61
	        {0,0,0,0,0,0,0,0,0,0,0,0},//62
	        {0,0,0,0,0,0,0,0,0,0,0,0},//63
	        {0,0,0,0,0,0,0,0,0,0,0,0},//64
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},//65
	        {0,0,0,0,0,0,0,0,0,0,0,0},//66
	        {0,0,0,0,0,0,0,0,0,0,0,0},//67
	        {0,0,0,0,0,1,1,1,1,0,0,0},//68
	        {0,0,0,0,0,0,0,0,0,0,0,0},//69
	        {0,0,0,0,0,0,0,0,0,0,0,0},//70
	        {0,0,0,0,0,0,0,0,0,0,0,0},//71
	        {0,0,0,0,0,0,0,0,0,0,0,0},//72
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},//73
	        {0,0,0,0,0,1,1,1,1,1,1,1},//74
	        {0,0,0,0,0,1,0,0,0,1,0,0},//75
	        {0,0,0,0,0,1,0,0,1,0,0,0},//76
	        {0,0,0,0,0,1,1,1,0,0,0,0},//77
	        {0,0,0,0,0,0,0,0,0,0,0,0},//78
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,1,0,0,0,0,0,0},//79
	        {0,0,0,0,0,0,1,0,0,0,0,0},//80
	        {0,0,0,0,0,0,0,0,1,0,0,0},//81
	        {0,0,0,0,0,0,0,0,1,0,0,0},//82
	        {0,0,0,0,0,0,1,0,0,0,0,0},//83
	        {0,0,0,0,0,1,0,0,0,0,0,0},//84
	        {0,0,0,0,0,0,0,0,0,0,0,0},//85
	        {0,0,0,0,0,0,0,0,0,0,0,0},//86
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,1,1,0,0,1,1,0,0},//87
	        {0,0,0,0,0,0,0,0,0,0,0,0},//88
	        {0,0,0,0,0,1,0,0,1,0,0,0},//89
	        {0,0,0,0,1,0,0,0,0,1,0,0},//90
	        {0,0,0,0,0,0,0,0,0,0,0,0},//91
	        {0,0,0,0,0,0,0,0,0,0,0,0},//92
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},//93
	        {0,0,0,0,0,1,0,1,1,1,0,0},//94
	        {0,0,0,0,0,1,0,1,0,1,0,0},//95
	        {0,0,0,0,0,1,1,1,0,1,0,0},//96
	        {0,0,0,0,0,0,0,0,0,0,0,0},//97
	        {0,0,0,0,0,0,0,0,0,0,0,0},//98
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,0,0,0,0,0,0},
	        {0,0,0,0,0,0,1,0,0,0,0,0},//99
	        {0,0,0,0,0,0,0,0,0,0,0,0},//100
	        {0,0,0,0,0,0,0,0,0,1,0,0},//101
	        {0,0,0,0,1,0,0,0,0,0,0,0},//102
	        {0,0,0,0,0,0,0,0,0,0,0,0},//103
	        {0,0,0,0,0,0,0,1,0,0,0,0}//104
	        };

        private static int[][] arrKuman = { 
        	   //1,2,3,4,5,6
                {0,0,0,0,0,0},//1
                {0,0,0,0,0,0},//2
                {0,0,0,0,0,0},//3
                {0,0,0,0,0,0},//4
                {0,0,0,1,0,0},//5
                {0,0,0,0,0,0},//6
                {0,0,0,0,0,0},//7
                {0,0,0,0,0,0},//8
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},//9
                {0,0,0,1,0,0},//10
                {0,0,0,0,0,0},//11
                {0,0,0,0,0,0},//12
                {0,0,0,0,0,0},//13
                {0,0,0,0,0,0},
                {0,0,1,0,0,0},//14
                {0,0,0,1,0,0},//15
                {0,0,1,0,0,0},//16
                {0,0,0,0,0,0},//17
                {0,0,0,0,0,0},//18
                {0,0,0,0,0,0},//19
                {0,0,0,0,0,0},//20
                {0,0,0,1,0,0},//21
                {0,0,0,0,0,0},//22
                {0,0,0,0,0,0},//23
                {0,0,0,0,0,0},//24
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},//25
                {0,0,0,0,1,0},//26
                {0,0,0,0,0,0},//27
                {0,0,0,0,0,0},//28
                {0,0,0,0,0,0},//29
                {0,0,0,0,0,0},
                {0,0,1,0,0,0},//30
                {0,0,1,0,0,0},//31
                {0,0,0,0,0,0},//32
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,1,0,0},//33
                {0,0,0,0,0,0},//34
                {0,0,0,1,0,0},//35
                {0,0,0,0,0,0},//36
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},//37
                {0,0,0,1,0,0},//38
                {0,0,0,0,0,0},//39
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},//40
                {0,0,0,1,0,0},//41
                {0,0,0,0,0,0},//42
                {0,0,0,0,0,0},//43
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,1,0,0},//44
                {0,0,0,0,0,0},//45
                {0,0,0,0,0,0},//46
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},//47
                {0,0,0,0,0,0},//48
                {0,0,0,0,0,0},//49
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},//50
                {0,0,0,1,0,0}//51
                };
        
        private static int[] arrNyawa = {1,1,1};

        //logic of jump
    	public int control = 0;
        boolean jumping = false;
        boolean falling = false;
        
        	public int tinggi = 15;
        	
        //public int hMax = 180;
        //public int vY;
        //public double grav = 0.9;
        //public int time = 0;
        //public int dY = 0;

        //road
        Image road;
        
        //button
        Image kanan, kiri, lompat;
        
        protected pCanvas(AMidlet midlet) {
                super(true);
                this.midlet = midlet;
                g = getGraphics();
                t = new Thread(this);
                setFullScreenMode(true);                
                
                GestureInteractiveZone giz = new GestureInteractiveZone(GestureInteractiveZone.GESTURE_ALL);
                GestureRegistrationManager.register(this, giz);
                GestureRegistrationManager.setListener(this, this);
        }

        public void run() {
                initImage();
                initChar();
                initKoin();
                initKuman();
                initNyawa();
                while (running) {
                        drawMatahari();
                        drawAwan();
                        drawRoad();
                        drawChar();
                        draw();
                        drawInput();
                        drawNyawa();
                        String s=" Score: " + skor;
                        Image img=Image.createImage(100,20);
                        Graphics gr=img.getGraphics();
                        gr.drawString(s, 0, 0, Graphics.TOP|Graphics.LEFT);
                        g.drawRegion(img, 0, 0, 100, 20, Sprite.TRANS_ROT90, 220, 0, Graphics.TOP|Graphics.LEFT); if(gameover){
                                midlet.gameOver();                                
                        }
                        jump();
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
                        bg = Image.createImage("/sky.png");
                        awan = Image.createImage("/sun.png");
                        runImg = Image.createImage("/baksooo.png");
                        road = Image.createImage("/ROAD.png");
                        koin = Image.createImage("/koinsp.png");
                        panci = Image.createImage("/pipo_pan.png");
                        kuman = Image.createImage("/kuman.png");
                        kanan = Image.createImage("/kanan.png");
                        kiri = Image.createImage("/kiri.png");
                        lompat = Image.createImage("/jump.png");
                        hatiNyawa = Image.createImage("/hati.png");
                } catch (IOException e) {
                        e.printStackTrace();
                }

        }

        private void initChar(){
                spRun = new Sprite(runImg, 49, 44);
                spRun.setFrameSequence(seqRun);
                cX = 60;
                cY = 10;
        }

        private void initKoin(){
                simpanKoin = new Sprite[80];
                int aa = 0;
                for(int i = 0; i < arrKoin.length; i++){
                        for(int j = 0; j < arrKoin[i].length; j++){
                                if(arrKoin[i][j]==1){
                                        simpanKoin[aa]= new Sprite(koin, 20, 20);
                                        simpanKoin[aa].setFrameSequence(seqRunKoin);
                                        simpanKoin[aa].setTransform(5);
                                        simpanKoin[aa].setPosition(j*20, i*20);
                                        aa++;
                                }
                        }
                }

        }

        private void initKuman(){
                simpanKuman = new Sprite[15];
                int aa = 0;
                for(int i = 0; i < arrKuman.length; i++){
                        for(int j = 0; j < arrKuman[i].length; j++){
                                if(arrKuman[i][j]==1){
                                        simpanKuman[aa]= new Sprite(kuman, 40, 40);
                                        simpanKuman[aa].setFrameSequence(seqKuman);
                                        simpanKuman[aa].setTransform(5);
                                        simpanKuman[aa].setPosition(j*40, i*40);
                                        aa++;
                                }
                        }
                }

        }
        
        private void initNyawa(){
        	simpanNyawa = new Sprite[3];
        	int ht = 0;
        	for(int i = 0; i < arrNyawa.length; i++){
        		if(arrNyawa[i]==1){
        			simpanNyawa[ht] = new Sprite(hatiNyawa, 40, 34);
        			simpanNyawa[ht].setTransform(5);
        			ht++;
        		}
        	}
        }
        
        private void drawNyawa(){
        	for(int i = 0; i < 3; i++){
				simpanNyawa[i].setPosition(205, (i*40)+275);
				simpanNyawa[i].paint(g);
        	}
        }

        private void draw(){
                for(int i = 0; i < 80; i++){
                simpanKoin[i].nextFrame();
                simpanKoin[i].setPosition(simpanKoin[i].getX(),simpanKoin[i].getY()-posYKoin);
                simpanKoin[i].paint(g);
            }
                posYKoin=0;
                for(int i = 0; i < 15; i++){
                simpanKuman[i].nextFrame();
                simpanKuman[i].setPosition(simpanKuman[i].getX(),simpanKuman[i].getY()-posYKuman);
                simpanKuman[i].paint(g);
            }
                posYKuman=0;
        }

        private void drawChar(){
        	/*if(!jumping && !falling){
        		spRun.nextFrame();
        	}
        	else
        		spRun.setFrame(0);
        	
        	/*if(jumping){
        		spRun.setFrame(3);
        		spRun.nextFrame();
        	}
        	else if(falling){
        		
        	}
        	else
        	{
        		spRun.nextFrame();
        		if (control == 2){
        			spRun.prevFrame();
        			spRun.prevFrame();
        			control = 0;        			
        		}
        		control++;
        	}*/
        	cY -=3;
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
            for(int i = 0; i < 80; i++){
                        if((simpanKoin[i].collidesWith(spRun, true)) ){                        	
                                //      System.out.println("bisa");
                        skor+=100;
                        simpanKoin[i].setVisible(false);
                        }
            }
            for(int i = 0; i < 15; i++){
                if((simpanKuman[i].collidesWith(spRun, true)) ){
                	nyawa--;
                	simpanKuman[i].setVisible(false);
                	simpanNyawa[nyawa].setVisible(false);
                    if(nyawa == 0){
                            gameover = true;
                            running = false;
                    }
                }
    }
            if(spRun.collidesWith(panci, 60, posYPanci, true)){
                midlet.menang();
            }
        }

        private void drawInput(){
        	g.drawRegion(kiri, 0, 0, kiri.getWidth(), kiri.getHeight(), 5, 0, 60, Graphics.LEFT | Graphics.BOTTOM);
            g.drawRegion(kanan, 0, 0, kanan.getWidth(), kanan.getHeight(), 5, 0, 160, Graphics.LEFT | Graphics.BOTTOM);
            g.drawRegion(lompat, 0, 0, lompat.getWidth(), lompat.getHeight(), 5, 0, 400, Graphics.LEFT | Graphics.BOTTOM);
            posYKoin+=3;
            posYKuman+=3;

            posYRoad1-=3;
            posYRoad2-=3;
            posYRoad3-=3;
            
            if (posYRoad1 < -height) {
            posYRoad1 = height*2;
            }
            if (posYRoad2 < -height) {
                posYRoad2 = height*2;
                }
            if (posYRoad3 < -height) {
                posYRoad3 = height*2;
                }
            

        }

        private void drawAwan(){
                g.drawRegion(bg, 0, 0, bg.getWidth(), bg.getHeight(), 5, 0,posYimg1, Graphics.LEFT | Graphics.BOTTOM);
                g.drawRegion(bg, 0, 0, bg.getWidth(), bg.getHeight(), 5, 0,posYimg2, Graphics.LEFT | Graphics.BOTTOM);
                g.drawRegion(bg, 0, 0, bg.getWidth(), bg.getHeight(), 5, 0,posYimg3, Graphics.LEFT | Graphics.BOTTOM);

                posYimg1-=3;
                posYimg2-=3;
                posYimg3-=3;

                if (posYimg1 < -height) {
                posYimg1 = height*2;
                }
                if (posYimg2 < -height) {
                posYimg2 = height*2;
                }
                if (posYimg3 < -height) {
                posYimg3 = height*2;
                }
        }

        private void drawRoad(){
        		g.drawRegion(road, 0, 0, road.getWidth(), road.getHeight(), 5, 0, posYRoad1, Graphics.LEFT | Graphics.BOTTOM);
        		g.drawRegion(road, 0, 0, road.getWidth(), road.getHeight(), 5, 0, posYRoad2, Graphics.LEFT | Graphics.BOTTOM);
        		g.drawRegion(road, 0, 0, road.getWidth(), road.getHeight(), 5, 0, posYRoad3, Graphics.LEFT | Graphics.BOTTOM);
                g.drawRegion(panci, 0, 0, panci.getWidth(), panci.getHeight(), 5, 60, posYPanci, Graphics.LEFT | Graphics.TOP);

                posYPanci-=3;
        }

        private void drawMatahari(){

                g.setColor(112, 200, 225);
                g.fillRect(0, 0, getWidth(), getHeight());
                g.drawRegion(awan, 0, 0, awan.getWidth(), awan.getHeight(), 5, 0,posYAwan1, Graphics.LEFT | Graphics.BOTTOM);
                g.drawRegion(awan, 0, 0, awan.getWidth(), awan.getHeight(), 5, 0,posYAwan2, Graphics.LEFT | Graphics.BOTTOM);
                g.drawRegion(awan, 0, 0, awan.getWidth(), awan.getHeight(), 5, 0,posYAwan3, Graphics.LEFT | Graphics.BOTTOM);
                
                posYAwan1-=1;
                posYAwan2-=1;
                posYAwan3-=1;

                if (posYAwan1 < -height) {
                posYAwan1 = height*2;
                }
                if (posYAwan2 < -height) {
                posYAwan2 = height*2;
                }
                if (posYAwan3 < -height) {
                posYAwan3 = height*2;
                }
                
        }
        
        private void jump(){
    		if (jumping) {
    			//ntar dicoba lagi
    			/*
    			System.out.println("jumping");
    			time++;
    			dY = (int) (vY*time + (0.5)*(-grav)*(time*time));
    			cX += dY;
    			if(cX > 150){
    				jumping = false;
    				falling = true;
    				vY = 0;
    				time = 0;
    			}
    			*/
    			tinggi--;
                cX += tinggi;
                if (tinggi < 0) {
                        jumping = false;
                        falling = true;
                }
    		}
    		
    		if (falling) {
    			//ntar dicoba lagi
    			/*
    			System.out.println("falling");
    			time++;
    			dY = (int) (vY*time + (0.5)*(grav)*(time*time));
    			cX -= dY;
    			if(cX < 60){
    				falling = false;
    				vY = 0;
    				time = 0;
    				cX = 60;
    			}
    			*/
    			tinggi++;
                cX -= tinggi;
                if (tinggi > 15) {
	                falling = false;
	                cX = 60;
                }
    		}
    	}

		public void gestureAction(Object container,
				GestureInteractiveZone gestureInteractiveZone,
				GestureEvent gestureEvent) {
			// TODO Auto-generated method stub
			int x = gestureEvent.getStartX();
			int y = gestureEvent.getStartY();
			
			if(x >= 0 && x <= 60 && y >= 0 && y <= 60){
				spRun.nextFrame();
        		if (control == 2){
        			spRun.prevFrame();
        			spRun.prevFrame();
        			control = 0;        			
        		}
        		control++;
				cY-=6;
			}
			if(y >= 100 && y <= 160 && x >= 0 && x <= 60){
				spRun.nextFrame();
        		if (control == 2){
        			spRun.prevFrame();
        			spRun.prevFrame();
        			control = 0;        			
        		}
        		control++;
				cY+=6;
			}
			
			switch(gestureEvent.getType()){
			/*case GestureInteractiveZone.GESTURE_ALL:
				if(x >= 0 && x <= 60 && y >= 0 && y <= 60){
					spRun.nextFrame();
	        		if (control == 2){
	        			spRun.prevFrame();
	        			spRun.prevFrame();
	        			control = 0;        			
	        		}
	        		control++;
					cY-=3;
				}
				if(y >= 100 && y <= 160 && x >= 0 && x <= 60){
					spRun.nextFrame();
	        		if (control == 2){
	        			spRun.prevFrame();
	        			spRun.prevFrame();
	        			control = 0;        			
	        		}
	        		control++;
					cY+=3;
				}*/
			case GestureInteractiveZone.GESTURE_TAP:
				if(y >= 340 && y <= 400 && x >= 0 && x <= 60){
					if (!jumping && !falling) {
	                    jumping = true;
	                    //vY = 5;
					}
				}
			}	
		}
}