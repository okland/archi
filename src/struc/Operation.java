package struc;

public class Operation {
	
	private long instruction;
 	
	private int cycle_issued;
	
	private int cycle_execute;
	
	private int cycle_write;
	
	private int tempValue = 0;
	
	private int executeCounter = 0;
	
	
	public Operation(long op) {
		instruction=op;
	}
	
	
	public int getCycleIssue() {
		return cycle_issued;
	}
	
	public void setCycleIssue(int ci) {
		cycle_issued = ci;
	}
	
	public int getCycleExectue() {
		return cycle_execute;
	}
	
	public void setCycleExectue(int ce) {
		cycle_execute = ce;
	}
	
	public int getCycleWrite() {
		return cycle_write;
	}
	
	public void setCycleWrite(int cw) {
		cycle_write = cw;
	}
	
	public void setTempValue(int temp) {
		tempValue = temp;
	}
	
	// get tempValue
	public int getTempValue() {
		return tempValue;
	}
	
	// get Imm
	public int getImm() {
		return Integer.valueOf(Long.toHexString(instruction % 65536),16).shortValue();
	}
	
	// get SRC1
	public int getSrc1() {
		return (int)((instruction / 65536) % 16);
	}
	
	// get SRC0
	public int getSrc0() {
		return (int)((instruction / 1048576) % 16);
	}
	
	// get DST
	public int getDst() {
		return (int)((instruction / 16777216) % 16);
	}
	
	// get Op code
	public int getOpCode() {
		return (int)(instruction / 268435456);
	}
	
	// get Trace
	public String getTrace() {
		return instruction + " " + cycle_issued + " " + cycle_execute + " " + cycle_write;
	}
	
	public void setCounter(int count) {
		executeCounter = count;
	}
	
	// get tempValue
	public int getCounter() {
		return executeCounter;
	}
	
	// get tempValue
	public long getInst() {
		return instruction;
	}
	
	public String OpPrint() {
		return String.format("%8s %d %d %d", Long.toHexString(instruction), cycle_issued, cycle_execute, cycle_write);
		
	}

}
