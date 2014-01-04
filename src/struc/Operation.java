package struc;

public class Operation {
	
	private String instruction;
 	
	private int cycle_issued;
	
	private int cycle_execute;
	
	private int cycle_write;
	
	private int tempValue = 0;
	
	private int executeCounter = 0;
	
	
	public Operation(String Instruction, int cycleIssued , int cycleExecute, int cycleWrite) {
		this.instruction = Instruction;
		this.cycle_issued = cycleIssued;
		this.cycle_execute = cycleExecute; 
		this.cycle_write= cycleWrite;
	}
	
	public Operation(String line) {
		String.format("%s %d %d %d", instruction, cycle_issued, cycle_execute, cycle_write);
	}

}
