package NMZ;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
 
import java.awt.*;
import java.util.List;
 
@ScriptManifest(author = "You", info = "My first script", name = "Tea thiever", version = 0, logo = "")
public class Main extends Script {
 boolean Composted = false;
 int Tele;
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
        PLANT, COMPOST, RAKE, CAMMY, FALLY, ARDY
    };
 
    private State getState() {
        RS2Object patch = getObjects().closest("Herb patch");
        if (patch != null && patch.hasAction("Rake"))
            return State.RAKE;
        if (patch != null && !patch.hasAction("Rake") && Composted != true)
            return State.COMPOST;
        if (patch != null && !patch.hasAction("Rake") && Composted == true)
        	return State.PLANT;
        if (patch == null && Tele == 1)
        return State.FALLY;
        if (patch == null && Tele == 2)
        return State.CAMMY;
        if (patch == null && Tele == 3);
        return State.ARDY;
    }
 
    @Override
    public int onLoop() throws InterruptedException {
        switch (getState()) {
        case COMPOST:
        	if(getInventory().isItemSelected()){
        		RS2Object patch = getObjects().closest("Herb patch");
        	    if(patch != null)
        	        patch.interact("Use");
        	}else{
        	    getInventory().interact("Use", "Supercompost");
        	    Composted = true;
        	    sleep(random(1300, 2500));
        	}
            break;
        case PLANT:
	    if(getInventory().isItemSelected()){
    		RS2Object patch = getObjects().closest("Herb patch");
    	    if(patch != null)
    	        patch.interact("Use");
	    }else{
    	    getInventory().interact("Use", "Guam seed");
    	    Tele ++;
    	    sleep(random(1300, 2000));
	}
        case RAKE:
        	RS2Object patch1 = getObjects().closest("Herb patch");
            if (patch1.hasAction("Rake") & !myPlayer().isAnimating()) {
            	patch1.interact("Rake");
            	sleep(1000);
            }
            break;
        case FALLY:
        	getInventory().interact("Break", "Varrock teleport");
        	getWalking().webWalk(FallyPath);
            break;
        case CAMMY:
        	getInventory().interact("Break", "Camelot teleport");
        	getWalking().webWalk(CammyPath);
            break;
        case ARDY:
        	getInventory().interact("Break", "Camelot teleport");
        	getWalking().webWalk(ArdyPath);
        	Tele = 0;
            break;
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