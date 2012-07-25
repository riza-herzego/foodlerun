import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


public class pMidlet extends MIDlet {
	
	pCanvas canvas;

	public pMidlet() {
		// TODO Auto-generated constructor stub
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		if (canvas == null) {
			canvas = new pCanvas();
			canvas.startGame();
			Display.getDisplay(this).setCurrent(canvas);
		}
		
	}

}
