import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.api.ui.Tab;
import org.osbot.rs07.listener.MessageListener;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.ConditionalSleep;

@ScriptManifest(author = "Caleb", info = "Runs runes", logo = "", name = "RCRunner", version = 0)
public class main extends Script implements MessageListener{
	boolean Traded;
	boolean PouchFilled = false;
	String Opponent;
	Area inBank = new Area(3084, 3501, 3097, 3486);
	Area inAltar = new Area(3044, 3510, 3058, 3499);
	//Area inAltar = new Area(3331, 3259, 3390, 3204);
	int tradeNumber;
	@Override
	public void onMessage(Message message) {
        if (message != null && message.getType() == Message.MessageType.GAME && (message.getMessage().contains("Other player declined trade."))) {
            Traded = false;
        }
    }
	public int onLoop() throws InterruptedException {
		
		if(inAltar.contains(myPosition()) && tradeNumber < 3){
			TRADE();
		}
		if(inBank.contains(myPosition())) {
			BANKING();
		}
		return 250;
	}

	private void TRADE() throws InterruptedException {
		RS2Widget TradeScreen1 = getWidgets().get(335, 3);
		RS2Widget Accept1 = getWidgets().get(335, 11);
		RS2Widget TradeScreen2 = getWidgets().get(334, 3);
		RS2Widget Accept2 = getWidgets().get(334, 13);
		if(getInventory().isFull()) {
			if(TradeScreen1 == null) {
				if(Traded == false) {
			getPlayers().closest("ToysForTots").interact("Trade with");
			Traded = true;
		    } else {
		    	sleep(100);
		    }
			}
				else {
		    	getTrade().offerAll("Pure essence");
		    	sleep(100);
		    	Accept1.interact("Accept");
			}
		}
			
			if(TradeScreen2 != null) {
				Accept2.interact("Accept");
				
			}
		}
	private void BANKING() throws InterruptedException {
		if(getBank().isOpen()) {
			if(!getInventory().isFull()) {
				getBank().withdrawAll("Rune essence");
			} else if(PouchFilled == false) {
				getInventory().getItem("Small pouch").interact("Fill");
				PouchFilled = true;
				}
		}
		if(getInventory().isFull()) {
			if(PouchFilled = true) {
				altarPath();
			}
		}else 
			getBank().open();
		sleep(1000);
	}
		
	private void altarPath() {
		// TODO Auto-generated method stub
		
	}
	private void DoBones() throws InterruptedException {
}

	private void LeaveHouse() throws InterruptedException {
}
}