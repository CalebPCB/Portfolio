import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Skill;
 
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.api.ui.EquipmentSlot;
 
@ScriptManifest(author = "Caleb", info = "Picks Fungus", logo = "", name = "Fungus Picker", version = 0)
public class Fungus extends Script {
    int config;
	Position prebank = new Position(3376, 3165, 0);
    Position BloomSpot = new Position(3421, 3439, 0);
    Position[] Bankpath = {
    		new Position(3369, 3169, 0),
    	    new Position(3370, 3167, 0),
    	    new Position(3372, 3165, 0),
    	    new Position(3375, 3163, 0),
    	    new Position(3378, 3162, 0),
    	    new Position(3382, 3160, 0)
    	};
    Position[] Portalpath = {
    	    new Position(3367, 3171, 0),
    	    new Position(3358, 3166, 0),
    	    new Position(3354, 3164, 0)
    	};
    Area ClanWars = new Area(3379, 3162, 3390, 3153);
    Area SwampTele = new Area(3449, 3456, 3427, 3465);
    Area Swamp = new Area(3414, 3444, 3429, 3433);
    Area Bank = new Area(3350, 3176, 3377, 3160);
    Area BloomArea = new Area(3424, 3438, 3416, 3443);
    Area GateArea = new Area(3445, 3458, 3441, 3459);
    Area FFAportal = new Area(3351, 3163, 3352, 3164);
 
    Position FFA = new Position(3327, 4751, 0);
 
	@Override
	public int onLoop() throws InterruptedException {
		RS2Object log = getObjects().closest(BloomArea, "Fungi on log");
		if(Swamp.contains(myPosition())){
			if(getInventory().isFull() || getSkills().getDynamic(Skill.PRAYER) == 0) {
			if(log == null) {
			getEquipment().interact(EquipmentSlot.RING, "Clan Wars");
			sleep(600);
			while(myPlayer().isAnimating()) {
				sleep(150);
			}
			}
		}
		}
			if(Bank.contains(myPosition())) {
				if(!getInventory().contains("Mort myre fungus")) {
					GoFFA();
				} else { 
					TeleBank();
				}
			}
			if(myPlayer().getPosition().equals(FFA)) {
				getInventory().getItem("Salve graveyard teleport").interact("Break");
				sleep(600);
				while(myPlayer().isAnimating()) {
					sleep(150);
				}
			}
			if(Swamp.contains(myPosition())) {
				if(!getInventory().isFull()) {
				if(log != null) {
					log.interact("Pick");
					sleep(random(650,850));
				} else if(myPlayer().getPosition().equals(BloomSpot)) {
					getEquipment().interact(EquipmentSlot.WEAPON, "Bloom");
					sleep(random(1000,1300));
				} else { BloomSpot.interact(bot, "Walk here");
					sleep(random(500,800));
				}
				}
			}
			if(SwampTele.contains(myPosition())) {
				if(myPlayer().getLocalY() >= 3548) {
				RS2Object gate = getObjects().closest("Gate");
				getWalking().webWalk(GateArea);
				gate.interact("Open");
				sleep(random(600,900));
				} else {
				getWalking().webWalk(BloomArea);
				}
			}
			if(ClanWars.contains(myPosition())) {
				getWalking().webWalk(Bankpath);
				sleep(random(600,1300));
				getBank().open();
			}
			
			return 250;
	}
	private void TeleBank() throws InterruptedException {
		if(Bank.contains(myPosition())){
			if(getInventory().contains("Mort myre fungus")) {
				getBank().depositAllExcept("Salve graveyard teleport");
			if(!getEquipment().isWearingItem(EquipmentSlot.RING, item -> item.getName().startsWith("Ring of dueling"))) {
			if(getBank().isOpen()) {
				getBank().withdraw("Ring of dueling(8)", 1);
				sleep(600);
				getBank().close();
				sleep(400);
				getInventory().getItem("Ring of dueling(8)").interact("Wear");
			}
			}
			}
			if(!getBank().isOpen()) {
				if(getInventory().contains("Mort myre fungus")) {
				getWalking().webWalk(Bankpath);
				sleep(2000);
			getBank().open();
			}
		} 
		}
	}
	private void GoFFA() throws InterruptedException {
		RS2Object portal = getObjects().closest("Free-for-all portal");
		if(!getInventory().isFull()) {
		if(portal != null) {
		if(!myPlayer().getPosition().equals("FFA")) {
			getWalking().webWalk(Portalpath);
			portal.interact("Enter");
			sleep(random(1000,3000));}
		}
		
		}
	}
}