import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(author = "Caleb", info = "Sacrafises bones", logo = "", name = "Rimmy Altar", version = 0)
public class main extends Script {
	Position Store = new Position(2949, 3213, 0);
	Position Poh = new Position(2953, 3222, 0);
	@Override
	public int onLoop() throws InterruptedException {
		if(myPlayer().getZ() == 1) {
			DoBones();
		} else {
			RunBones();
		}
		return 250;
	}

	private void RunBones() throws InterruptedException {
		RS2Widget BoneExchange = getWidgets().get(162, 549);
		NPC Phials = npcs.closest("Phials");
	if(!getInventory().contains(536)) {
		if(getInventory().contains(537)) {
			if(!BoneExchange.isVisible()) {
			if(Phials.isVisible()) {
				if(getInventory().isItemSelected()){
        	        Phials.interact("Use");
        	    sleep(random(400,800));
        	}else{
        	    getInventory().interact("Use", 537);
			}
		}else {
			getWalking().walk(Store);
		}
		}else {
			getKeyboard().typeKey('3');
		}
		}
	} else {
		EnterPOH();
	}
	}

	private void EnterPOH() throws InterruptedException {
		RS2Object Portal = getObjects().closest("Portal");
		RS2Widget PohName = getWidgets().get(162, 32, 0);
		if(getInventory().contains(536)) {
			if(Portal.isVisible()) {
				Portal.interact("Friend's house");
				sleep(700);
				if(!PohName.isVisible()) {
					PohName.interact();
	}else {
		PohName.interact();
	}
				} else {
					getWalking().walk(Poh);
				}
			while(myPlayer().isMoving()) {
				sleep(500);
			}
		}
				}
		

	private void DoBones() throws InterruptedException {
		RS2Object Altar = getObjects().closest("Altar");
			if(!myPlayer().isAnimating()) {
				if(getInventory().contains(536)) {
					if(getInventory().isItemSelected()){
		        	        Altar.interact("Use");
		        	    sleep(random(400,800));
		        	}else{
		        	    getInventory().interact("Use", 536);
		}
					while(myPlayer().isAnimating()) {
						sleep(5000);
					}
	}else {
		LeaveHouse();
	}
	
	}
}

	private void LeaveHouse() throws InterruptedException {
		RS2Widget HouseOptions = getWidgets().get(261, 78);
		RS2Widget LeaveHouse = getWidgets().get(370, 18);
		if(HouseOptions.isVisible()) {
			HouseOptions.interact("View House Options");
		}else {	getTabs().open(Tab.SETTINGS);
		if(LeaveHouse.isVisible()) {
		}
			LeaveHouse.interact("Leave House");
			sleep(1000);
		}
	}
}