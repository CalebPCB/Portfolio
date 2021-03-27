import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.api.ui.Message.MessageType;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.listener.MessageListener;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import java.awt.*;
 
@ScriptManifest(author = "Caleb", info = "Nmz Bot", name = "Nightmare Zone", version = 0, logo = "")
public class NMZv2 extends Script implements MessageListener {
	boolean Overloaded;
    public int AbsPoints() {
        RS2Widget widget = getWidgets().get(202, 1, 9);
        if(widget != null && widget.isVisible() && widget.getMessage() != null)
            return Integer.parseInt(widget.getMessage().replace(",", ""));
        return 0;
    }
    private final String[] ABSORBTION_POTIONS = {"Absorption (1)", "Absorption (2)", "Absorption (3)", "Absorption (4)"};
     
    private final String[] OVERLOAD_POTIONS = {"Overload (1)", "Overload (2)", "Overload (3)", "Overload (4)"};
    @Override
    public void onStart() {
    }
 
    private enum State {
        Overload, Absorbtion, Cake, Stop, Wait
    };
 
    private State getState() {
        if (skills.getDynamic(Skill.STRENGTH) == skills.getStatic(Skill.STRENGTH))
            return State.Overload;
        if (AbsPoints() <= 50 && getInventory().contains(ABSORBTION_POTIONS) && skills.getDynamic(Skill.STRENGTH) > skills.getStatic(Skill.STRENGTH))
            return State.Absorbtion;
        if (Overloaded == true && skills.getDynamic(Skill.HITPOINTS) >= 2)
            return State.Cake;
        if (areaHasPlayers())
            return State.Stop;
        return State.Wait;
   
    }
    @Override
    public void onMessage(Message message) {
        if (message != null && message.getType() == Message.MessageType.GAME && (message.getMessage().contains("You need to wait "))) {
            Overloaded = false;
        }
    }
    @Override
    public int onLoop() throws InterruptedException {
        switch (getState()) {
        case Overload:
            if (skills.getDynamic(Skill.HITPOINTS) >= 51 && Overloaded == false){
                inventory.interact("Drink", OVERLOAD_POTIONS);
                Overloaded = true;
                sleep(random(8000, 12000));
               
            }
            break;
        case Absorbtion:
            inventory.interact("Drink", ABSORBTION_POTIONS);
            inventory.interact("Drink", ABSORBTION_POTIONS);
            inventory.interact("Drink", ABSORBTION_POTIONS);
            break;
        case Cake:
            sleep(random(1000, 8000));
            while(skills.getDynamic(Skill.HITPOINTS) >= 2) {
            inventory.interact("Guzzle" , "Dwarven rock cake");
            }
            sleep(random(500,900));
            break;
        case Stop:
              stop();
        case Wait:
            int minMilliSecond = 500;
            int maxMillisecond = 5000;
           sleep(random(minMilliSecond, maxMillisecond));
 
            int i=random(1,100);
            switch (i){
                case 1: tabs.open(Tab.ATTACK);
                    break;
                case 2: tabs.open(Tab.CLANCHAT);
                    break;
                case 4: tabs.open(Tab.FRIENDS);
                    break;
                case 5: tabs.open(Tab.MAGIC);
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
 
                        default:
                            break;
                    }
                    break;
    }
        }
        return random(200, 600);
    }
     public boolean areaHasPlayers()
     {
             Area area = myPlayer().getArea(10);
             int amount = 0;
 
             for (Player player : players.getAll())
             {
                     if (player != null && !player.getName().equalsIgnoreCase(myPlayer().getName()))
                     {
                             if (area.contains(player))
                             {
                                     amount++;
                             }
                     }
             }
             return amount > 0;
     }
    @Override
    public void onExit() {
    }
 
    @Override
    public void onPaint(Graphics2D g) {
 
    }
 
}