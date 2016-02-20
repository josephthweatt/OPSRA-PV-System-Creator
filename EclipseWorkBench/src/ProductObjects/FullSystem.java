package ProductObjects;

import java.util.HashMap;

import org.json.JSONException;

/* This class can be used to hold variations of the 
 * existing objects in this class. It is used outside 
 * of the package to attain API information and to 
 * provide information on the prospective PV system. */

public class FullSystem implements java.io.Serializable {
	// Objects of the system's parts
	public Panel panel;
	public Inverter inverter;
	public Racking rack;
	public Battery battery;
	public BatteryController batteryControl;
	public BatteryMeter batteryMeter;
	public DCACDisconnect dcacDisconnect;
	public BatteryWire batteryWire;
	public PVWires pvWires;

	// all the table names that one could expect from the database:
	public static Object[] productType = { "Panels", "Inverters", "Racking",
			"Batteries", "BatteryControllers", "BatteryMeter", "DCACDisconnect",
			"BatteryWires", "PVWires" };

	// System qualities
	public double cost;
	public double loss;
	public double monthlyDC, yearlyDC;
	public double monthlyAC, yearlyAC;

	public String address;
	// this is a test case, delete after testing
	public double latitude = 111.9431;;
	public double longitude = 33.4294;
	
	// for nonspecific initialization
	public FullSystem() {
	}

	// constructor to initialize with products
	public FullSystem(Object... product) {
		addProduct(product);

		// things to do if the user has entered a full system
		if (isComplete()) {
			calculateCost();
			calculateLoss();
		}
	}

	// primary method for loading products into the system
	public void addProduct(Object... product) {
		for (int i = 0; i < product.length; i++) {
			if (product[i].getClass() == Panel.class) {
				panel = (Panel) product[i];
			} else if (product[i].getClass() == Inverter.class) {
				inverter = (Inverter) product[i];
			} else if (product[i].getClass() == Racking.class) {
				rack = (Racking) product[i];
			} else if (product[i].getClass() == Battery.class) {
				battery = (Battery) product[i];
			} else if (product[i].getClass() == BatteryController.class) {
				batteryControl = (BatteryController) product[i];
			} else if (product[i].getClass() == BatteryMeter.class) {
				batteryMeter = (BatteryMeter) product[i];
			} else if (product[i].getClass() == DCACDisconnect.class) {
				dcacDisconnect = (DCACDisconnect) product[i];
			} else if (product[i].getClass() == BatteryWire.class) {
				batteryWire = (BatteryWire) product[i];
			} else if (product[i].getClass() == PVWires.class) {
				pvWires = (PVWires) product[i];
			}
		}
	}

	// get the total cost of the system
	// returns zero if the system is incomplete
	public double calculateCost() {
		if (isComplete()) {
			this.cost += panel.price;
			this.cost += inverter.price;
			this.cost += rack.price;
			this.cost += battery.price;
			this.cost += batteryControl.price;
			this.cost += batteryMeter.price;
			this.cost += dcacDisconnect.price;
			this.cost += batteryWire.price;
			this.cost += pvWires.price;
			return cost;
		}
		return 0.0;
	}

	// calculates percent of energy one expects to lose from
	// the system (percent stored as a double from 0 to 100)
	public void calculateLoss() {
		if (isComplete() == false) {
			System.out.println(
					"Incomplete system. Complete this system to use this method.");
			return;
		}
		final int WIRE_LOSS = 2; // 2% lost from wires
		final double LIGHT_DEGREDATION = 1.5;
		final int AGE = 0; // we assume the panels will be new for the first
							// year
		int invLoss = 100 - inverter.efficiency;

		this.loss = WIRE_LOSS + LIGHT_DEGREDATION + AGE + invLoss
				+ panel.powerTolerance;
	}

	// method to verify that the system has all the essential parts
	public boolean isComplete() {
		if (panel == null)
			return false;
		if (inverter == null)
			return false;
		if (rack == null)
			return false;
		if (battery == null)
			return false;
		if (batteryMeter == null)
			return false;
		if (batteryControl == null)
			return false;
		if (dcacDisconnect == null)
			return false;
		if (batteryWire == null)
			return false;
		if (pvWires == null)
			return false;

		return true;
	}

	/************************************************************************
	 * The Following methods will be used to get data from the PVWatts API
	 *************************************************************************/
	// gets power data from the API and inputs extra data with the hashmap
	public void getDataFromAPI(HashMap<String, Double> extraData) {
		// method check to see if any additional system data must be entered
		if (extraData != null) {
			
		}
		
		PVWattsManager pvWatts = new PVWattsManager(this, latitude, longitude);
		try {
			pvWatts.getData();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
