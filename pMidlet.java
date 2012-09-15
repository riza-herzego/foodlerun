import java.io.IOException;
//import java.io.InputStream;

import javax.microedition.lcdui.Display;
//import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
//import javax.microedition.media.Player;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


public class AMidlet extends MIDlet {
	
	ACanvas canvas;
	AMenu menu;
	SplashScreen ss;
	SGO go;
	Aboutlagi about;
	HowToPlayCanvas htp;
	HighScore hs;
	RecordHighScore rs;
	//static InputStream media;
	//Player game, soundmenu, gameover;
	
	Display d = Display.getDisplay(this);

	public AMidlet() throws MediaException {
		ss();
		if (rs==null){
			rs = new RecordHighScore();
		}
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub	
		/*media = getClass().getResourceAsStream("/sfx/goldrush.mp3");
		
			try {
				game = Manager.createPlayer(media, "audio/x-mp3");
				game.setLoopCount(-1);
				game.realize();
				game.prefetch();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MediaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		media = getClass().getResourceAsStream("/sfx/Menu.mp3");
			try {
				soundmenu = Manager.createPlayer(media, "audio/x-mp3");
				soundmenu.setLoopCount(-1);
				soundmenu.realize();
				soundmenu.prefetch();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MediaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		media = getClass().getResourceAsStream("/sfx/GameOver.mp3");
		try {
			gameover = Manager.createPlayer(media, "audio/x-mp3");
			gameover.setLoopCount(-1);
			gameover.realize();
			gameover.prefetch();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	public void mainMenu(){
		emptyScreen();
		try {
			menu = new AMenu(this);
			if(!AMenu.OFF){
				SoundManager.playSound(SoundManager.MENU);
				//soundmenu.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MediaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		menu.start();
		d.setCurrent(menu);		
	}
	
	public void level1(){
		emptyScreen();
		canvas = new ACanvas(this);
		if(!AMenu.OFF){
			SoundManager.playSound(SoundManager.GAME);
			/*try {
				game.start();
			} catch (MediaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		canvas.startGame();
		d.setCurrent(canvas);
	}
		
	public void gameOver(){
		emptyScreen();
		go = new SGO(this);if(!AMenu.OFF){
			SoundManager.playSound(SoundManager.GAMEOVER);
			/*try {
				gameover.start();
			} catch (MediaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		d.setCurrent(go);
	}
	
	public void ss(){
		emptyScreen();
		ss = new SplashScreen(this);
		ss.start();
		d.setCurrent(ss);
	}
	
	public void about(){
		emptyScreen();
		about = new Aboutlagi(this);
		about.start();
		d.setCurrent(about);
	}
	
	public void htp(){
		emptyScreen();
		htp = new HowToPlayCanvas(this);
		d.setCurrent(htp);
	}
	
	public void hs(){
		emptyScreen();
		hs = new HighScore(this);
		d.setCurrent(hs);
	}
	
	public void emptyScreen(){
		SoundManager.cleanUp();
		if(canvas!=null){
			//game.deallocate();
			//game.close();
			canvas.stop();			
			canvas = null;
		}
		if(menu != null){
			//soundmenu.deallocate();
			//soundmenu.close();
			menu.stop();			
			menu = null;
		}
		if(go != null){
			//gameover.deallocate();
			//gameover.close();
			go = null;
		}
		if(ss != null){
			ss = null;
		}
		if(about != null){
			about = null;
		}
		if(htp != null){
			htp = null;
		}
		if(hs != null){
			hs = null;
		}
	}
	
}
