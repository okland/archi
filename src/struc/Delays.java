package struc;


public class Delays {
	
	//int_delays
 	private static int int_delay;
	//add_delays
	private static int add_delay;
	//mul_delays
	private static int mul_delay;
	//mem_delays
	private static int mem_delay;
		




	public Delays(int intD, int addD, int mulD, int memD) {
		int_delay = intD;
		add_delay = addD;
		mul_delay = mulD;
		mem_delay = memD;
	}

	
	public static int getIntDelay() {
		return int_delay;
	}

	public static int getAddDelay() {
		return add_delay;
	}

	public static int getMulDelay() {
		return mul_delay;
	}

	public static int getMemDelay() {
		return mem_delay;
	}

}
