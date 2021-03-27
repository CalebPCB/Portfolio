import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;
import java.awt.event.KeyEvent;
 
@ScriptManifest(author = "Caleb", info = "Herb Farmer", name = "Harvesting", version = 0, logo = "")
public class Trial extends Script {
 boolean Composted = false;
 int Tele = 0;
 Area cath = new Area(2811, 3465, 2816, 3461);
 Area farm = new Area(3057, 3313, 3060, 3309);
 Area ard = new Area(2668, 3376, 2674, 3372);
 Area bank = new Area(3009, 3357, 3017, 3355);

    @Override
    public void onStart() {
    }
 
    private enum State {
        PLANT, COMPOST, RAKE, CAMMY, FALLY, ARDY, HARVEST, BANK, NOTE
    };
 
    private State getState() {
    	RS2Object herbs = getObjects().closest("Herbs");
        RS2Object patch = getObjects().closest("Herb patch");
        RS2Object dead = getObjects().closest("Dead herbs");
        if (dead != null && dead.hasAction("Clear"))
        	return State.HARVEST;
        if (herbs != null && herbs.hasAction("Pick"))
            return State.HARVEST;
        if (getInventory().isFull() && herbs != null)
        	return State.NOTE;
        if (patch != null && patch.hasAction("Rake"))
            return State.RAKE;
        if (patch != null && !patch.hasAction("Rake") && Composted != true)
            return State.COMPOST;
        if (patch != null && !patch.hasAction("Rake") && Composted == true)
        	return State.PLANT;
        if (bank.contains(myPosition()))
        	return State.BANK;
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
        	    Composted = true;
        	    sleep(random(1300, 2500));
        	}else{
        	    getInventory().interact("Use", "Supercompost");
        	}
            break;
        case PLANT:
	    if(getInventory().isItemSelected()){
    		RS2Object patch = getObjects().closest("Herb patch");
    	    if(patch != null)
    	        patch.interact("Use");
    	    sleep(4000);
    	    doTele();
	    }else{
    	    getInventory().interact("Use", "Harralander seed");
	}
	    break;
        case HARVEST:
        	RS2Object herbs = getObjects().closest("Herbs");
        	RS2Object dead = getObjects().closest("Dead herbs");
        	if (herbs != null && herbs.hasAction("Pick") && !myPlayer().isAnimating()) {
            	herbs.interact("Pick");
        	sleep(5000);
        	}else {
            	dead.interact("Clear");
            	sleep(9000);
        	}
        	break;
        case RAKE:
        	RS2Object patch1 = getObjects().closest("Herb patch");
            if (patch1.hasAction("Rake") & !myPlayer().isAnimating()) {
            	patch1.interact("Rake");
            	sleep(1000);
            }
            break;
        case NOTE:
        	if(getInventory().isItemSelected()){
        		NPC Lep = getNpcs().closest("Tool Leprechaun");
        		if(Lep != null)
    	        Lep.interact("Use");
    	    sleep(random(1300, 2500));
    	}else{
    	    getInventory().interact("Use", "Grimy Harralander");
    	}
   
            break;
        case BANK:
        	if (getBank().isOpen()) {
        		getBank().depositAll("Grimy Harralander", "Bucket", "Weeds");
        		sleep(random(800, 1300));
        		getBank().withdraw("Supercompost", 3);
        		getBank().close();
        		getWalking().webWalk(farm);
        		sleep(5000);
        	}else { getBank().open();
        	}
        case FALLY:
        	if (!farm.contains(myPosition()) && Tele == 1) {
        		Composted = false;
        	getWalking().webWalk(bank);
        	
        	}
            break;
        case CAMMY:
        	if (!cath.contains(myPosition()) && Tele == 2) {
        		Composted = false;
        	getWalking().webWalk(cath);
        	}
        	break;
        case ARDY:
        	if (ard.contains(myPosition()) && Tele == 3) {
        		Composted = false;
        		getLogoutTab().logOut();
        		sleep(random(5400000, 6000000));
        		Tele = 0;
        		getKeyboard().pressKey((char)KeyEvent.VK_ENTER);
        		sleep(random(600, 900));
            	getKeyboard().typeString("trimmed hp");
            	getKeyboard().typeString("caleb123");
            	sleep(5000);
        	}
            break;
        }
        return random(200, 300);
    } 
    private void doTele() {
    	Tele ++;
    	if (Tele == 1) {
    		getInventory().interact("Break", "Falador teleport");
    	}
    	if (Tele == 2) {
    		getInventory().interact("Break", "Camelot teleport");
    	}
    	if(Tele == 3) {
    		getWalking().webWalk(ard);
    	}
    }
    private void LogIn() {
    	getKeyboard().pressKey((char)KeyEvent.VK_ENTER);
    	getKeyboard().typeString("trimmed hp");
    	getKeyboard().typeString("caleb123");
    }
    @Override
    public void onExit() {
    }
 
    @Override
    public void onPaint(Graphics2D g) {
 
    }
 
}