package ProductObjects;

public class Panel {
	public String name;
	public double price;
	public int systemCap; // this is in KW
	public double amps;
	public double volts;
	public int powerTolerance;
	public int moduleType;
	public String dimensions;

	public double heightInInches;
	public double widthInInches;
	public double areaInMeters; // the area of the panel
	public int panelCount = 1; // panels needed for the system
	private double estimatedEnergyPerPanel;

	// for nonspecific initialization
	public Panel() {
	}

	public Panel(String name, double price, int systemCap, double amps,
			double volts, int powerTolerance, int moduleType, String dimensions) {
		this.name = name;
		this.price = price;
		this.systemCap = systemCap;
		this.amps = amps;
		this.volts = volts;
		this.powerTolerance = powerTolerance;
		this.moduleType = moduleType;
		this.dimensions = dimensions;
		getDimensions();

		dimensionsToMeters();
	}

	/*
	 * This method takes the first two integers within the dimensions case (they
	 * are separated by '/') and multiplies them to get the Panel's square
	 * meters. This is passed in as the PVWatts 'size' parameter
	 */
	public double dimensionsToMeters() {
		double heightMeters = heightInInches / 39.370;
		double widthMeters = widthInInches / 39.370;

		return areaInMeters = heightMeters * widthMeters;
	}

	/*
	 * gives an estimate of how much energy one can expect per panel by using
	 * the panels efficiency and systemCapacity. This is used when trying to
	 * estimate the Panels performance, and does not account for factors like
	 * location and other parts of the system. This returns energy in KWatts
	 */
	public double estimatedEnergyPerPanel() {
		if (estimatedEnergyPerPanel < 0) {
			return estimatedEnergyPerPanel;
		}
		// if est. EnergyPerPanel hasn't been found yet, we find it here...
		double approximateEfficiency = 0;
		double wattToKW = .001;

		if (moduleType == 0) {
			// moduleType 0 is standard efficiency (15%)
			approximateEfficiency = .15;
		} else if (moduleType == 1) {
			// moduleType 1 is premium efficiency (19%)
			approximateEfficiency = .19;
		} else if (moduleType == 2) {
			// moduleType 2 is thin film Panels (10% efficiency)
			approximateEfficiency = .10;
		} else {
			// if the module type is something else, print the error and
			// exit the program
			System.out.println("Error: Invalid module type on the panel: "
					+ name + "\nType given: " + moduleType);
			System.exit(0);
		}
		return estimatedEnergyPerPanel = systemCap * approximateEfficiency
				* wattToKW;
	}
	
	private void getDimensions() {
		String[] dims = dimensions.split("/");
		if (heightInInches == 0) {
			heightInInches = Double.parseDouble(dims[0]);
		}
		if (widthInInches == 0) {
			widthInInches = Double.parseDouble(dims[1]);
		}
	}
}