import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
 
import java.awt.*;
 
@ScriptManifest(author = "You", info = "My first script", name = "Tea thiever", version = 0, logo = "")
public class NMZ2 extends Script {
 
    @Override
    public void onStart() {
        log("Welcome to Simple Tea Thiever by Apaec.");
        log("If you experience any issues while running this script please report them to me on the forums.");
        log("Enjoy the script, gain some thieving levels!.");
    }
 
    private enum State {
        STEAL, DROP, WAIT
    };
 
    private State getState() {
        RS2Object stall = getObjects().closest("Tea stall");
        if (!inventory.isEmpty())
            return State.DROP;
        if (stall != null)
            return State.STEAL;
        return State.WAIT;
    }
 
    @Override
    public int onLoop() throws InterruptedException {
        switch (getState()) {
        case STEAL:
            RS2Object stall = getObjects().closest("Tea stall");
            if (stall != null) {
                stall.interact("Steal-from");
            }
            break;
        case DROP:
            inventory.dropAll();
            break;
        case WAIT:
            sleep(random(500, 700));
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