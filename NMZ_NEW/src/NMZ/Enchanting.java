package NMZ;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;


import java.awt.*;
 
@ScriptManifest(author = "You", info = "My first script", name = "Enchanting", version = 0, logo = "")
public class Enchanting extends Script {
 boolean Composted = false;
 int Tele;

    @Override
    public void onStart() {
        log("Welcome to Simple Tea Thiever by Apaec.");
        log("If you experience any issues while running this script please report them to me on the forums.");
        log("Enjoy the script, gain some thieving levels!.");
    }
 
    private enum State {
        WAIT, ENCHANT, BANK, ANTIBAN
    };
 
    private State getState() throws InterruptedException {
    	if(getInventory().contains("Cosmic rune") && getInventory().contains("Sapphire ring")) 
        	return State.ENCHANT;
        	if(!myPlayer().isAnimating() && !getInventory().contains("Sapphire ring"))
        return State.BANK;
        	if(getInventory().contains("Ring of recoil"))
        return State.ANTIBAN;
			return State.WAIT;
			
    }
 
    @Override
    public int onLoop() throws InterruptedException {
        switch (getState()) {
        case BANK:
        	if (getBank().isOpen() && getBank().contains("Sapphire ring")) {
        		getBank().depositAll("Ring of recoil");
        		sleep(random(800, 1300));
        		getBank().withdraw("Sapphire ring", 9000);
        		getBank().close();
        		sleep(random(1222, 7000));
        	}
        	else { getBank().open();
        	}
        case WAIT:
        	//stop();
        case ANTIBAN:
        	RS2Widget Mage = getWidgets().get(320, 6);
        	int i=random(1,100);
            switch (i){
                case 1: tabs.open(Tab.SKILLS);
                Mage.hover();
                sleep(random(3222, 6444));
                    break;
                case 2: getMouse().moveOutsideScreen();
                sleep(random(5000,23000));
                    break;
                case 4: tabs.open(Tab.FRIENDS);
                    break;
                    
                    default:
        	int ii=random(1,50);
            switch (ii){
                case 1:
                    getCamera().moveYaw(random(1, 450));
                    break;
                case 2:
                    getCamera().movePitch(random(1, 450));
                    break;

                case 3:
                    getCamera().moveYaw(random(10, 500));
                    getCamera().movePitch(random(10, 500));
                    break;
                case 4:
                    getCamera().moveYaw(random(20, 300));
                    break;
            }
            }
        case ENCHANT:
        	RS2Widget Spell = getWidgets().get(218, 6);
        	if (!getMagic().isSpellSelected() && getInventory().contains("Cosmic rune") && getInventory().contains("Sapphire ring")) {
        		sleep(random(300, 967));
        		tabs.open(Tab.MAGIC);
                Spell.interact("Cast");
                sleep(random(304, 712));
        		
        	} else {
        		getInventory().interact("Cast", "Sapphire ring");
        	}
        	if (getMagic().isSpellSelected() && !getInventory().contains("Sapphire ring")) {
        		getMagic().deselectSpell();
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