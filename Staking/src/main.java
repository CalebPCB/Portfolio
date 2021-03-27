import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(author = "Caleb", info = "Sacrafises bones", logo = "", name = "Staking", version = 0)
public class main extends Script {
	String Opponent;
	Area inArena = new Area(3331, 3259, 3390, 3204);
	@Override
	public int onLoop() throws InterruptedException {
		if(inArena.contains(myPosition())) {
			FIGHT();
		}
		else {
			DuelRules();
		}
		return 250;
	}

	private void FIGHT() throws InterruptedException {
		while(!getPlayers().myPlayer().isAnimating() && !getPlayers().myPlayer().isUnderAttack()) {
		getPlayers().closest(Opponent).interact("Fight");
		//getKeyboard().typeString(Opponent);
		}
	}

	private void DuelRules() throws InterruptedException {
		RS2Widget DuelScreen = getWidgets().get(482, 1);
		RS2Widget DuelScreen2 = getWidgets().get(481, 5);
		RS2Widget DuelScreen3 = getWidgets().get(476, 3);
		RS2Widget SavedOptions = getWidgets().get(482, 114);
		RS2Widget PresetOptions = getWidgets().get(482, 110);
		RS2Widget Accept = getWidgets().get(482, 103);
		RS2Widget Accept2 = getWidgets().get(481, 74);
		RS2Widget Accept3 = getWidgets().get(476, 72);
		RS2Widget Decline = getWidgets().get(482, 103);
		RS2Widget DuelOffer = getWidgets().get(481, 17);
		RS2Widget DuelOffer2 = getWidgets().get(481, 27);
		RS2Widget DuelOffer3 = getWidgets().get(481, 27);
		if(!DuelScreen.isHidden()) {
		if(SavedOptions.isHidden()) {
			PresetOptions.interact("Load Preset Settings");
			sleep(500);
		}
		else {
			Accept.interact("Accept");
		}
		}
		if(DuelScreen2 != null) {
			Opponent = getWidgets().get(481, 24).getMessage().replaceAll("'s stake:","");
			if(DuelOffer.getMessage().contains("0")) {
				Accept2.interact("Accept");
			}
		}
			if(Accept3 != null) {
			Accept3.interact("Accept");
		}
	}

	private void DoBones() throws InterruptedException {
}

	private void LeaveHouse() throws InterruptedException {
}
}