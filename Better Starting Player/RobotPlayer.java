import battlecode.common.RobotController;
import team016.Units.Soldier;
import team016.Units.HQ;


public class RobotPlayer {
	
	public static void run(RobotController rc) {
		while (1) {
			try {
				Unit u = typeSwitch(rc.getType());
				if (u != null) {
					u.run(); //this function needs to *never* end, unless error is thrown.
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Unit typeSwitch(RobotType rt) {
		switch(rt) {
			case HQ: return new HQ(rc);
			case SOLDIER: return new Soldier(rc);
			default: return null;
		}
	}

}