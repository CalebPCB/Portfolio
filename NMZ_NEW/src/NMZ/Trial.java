package NMZ;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
 
@ScriptManifest(author = "You", info = "My first script", name = "walking2", version = 0, logo = "")
public class Trial extends Script {
 boolean Composted = false;
 int Tele;
 Area cath = new Area(2811, 3465, 2816, 3461);
 Area farm = new Area(3057, 3313, 3060, 3309);
 Area ard = new Area(2668, 3376, 2674, 3372);
 Area bank = new Area(3009, 3357, 3017, 3355);

 private Position[] FallyPath = {
         new Position(2982, 3374, 0), new Position(2996, 3367, 0), new Position(3004, 3354, 0),
         new Position(3007, 3335, 0), new Position(3011, 3319, 0), new Position(3029, 3317, 0),
         new Position(3046, 3316, 0), new Position(3055, 3309, 0)
 };
 private Position[] CammyPath = {
         new Position(2772, 3474, 0), new Position(2784, 3465, 0), new Position(2794, 3462, 0),
         new Position(2811, 3463, 0)
 };
 private Position[] ArdyPath = {
         new Position(2798, 3455, 0), new Position(2996, 3367, 0), new Position(2789, 3444, 0),
         new Position(2773, 3439, 0), new Position(2749, 3420, 0), new Position(2736, 3414, 0),
         new Position(2726, 3403, 0), new Position(2714, 3393, 0), new Position(2702, 3382, 0), 
         new Position(2688, 3375, 0), new Position(2673, 3374, 0)
 };
    @Override
    public void onStart() {
        log("Welcome to Simple Tea Thiever by Apaec.");
        log("If you experience any issues while running this script please report them to me on the forums.");
        log("Enjoy the script, gain some thieving levels!.");
    }
 
    private enum State {
        BANK, FALLY
    };
 
    private State getState() {
    	if (bank.contains(myPosition()))
        	return State.BANK;
        return State.FALLY;
    }
 
    @Override
    public int onLoop() throws InterruptedException {
        switch (getState()) {
        case BANK:
        	if (getBank().isOpen()) {
        		getBank().depositAll("Grimy guam leaf", "Bucket");
        		sleep(random(800, 1300));
        		getBank().withdraw("Supercompost", 3);
        		getBank().close();
        		getWalking().webWalk(farm);
        		sleep(5000);
        	}else { getBank().open();
        	}
        case FALLY:
        	if (!farm.contains(myPosition())) {
        		//Composted = false;
        	getWalking().webWalk(bank);
        	
        	}
        }
        return random(200, 300);
        
    }
 
    @Override
    public void onExit() {
        log("Thanks for running my Tea Thiever!");
    }
 
    @Override
    public void onPaint(Graphics2D g) {
 
    }
 
}