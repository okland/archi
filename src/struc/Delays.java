package struc;


public class Delays {
	
	//int_delays
 	private int int_delay;
	//add_delays
	private int add_delay;
	//mul_delays
	private int mul_delay;
	//mem_delays
	private int mem_delay;
		




	public Delays(int intD, int addD, int mulD, int memD) {
		int_delay = intD;
		add_delay = addD;
		mul_delay = mulD;
		mem_delay = memD;
	}

	
	public int getIntDelay() {
		return int_delay;
	}

	public int getAddDelay() {
		return add_delay;
	}

	public int getMulDelay() {
		return mul_delay;
	}

	public int getMemDelay() {
		return mem_delay;
	}

}
