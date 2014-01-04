package struc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Environment {
	
	private static OperExe Op;
	public static Delays delay;
	public static int PC = 1;
	
	private Environment() {
		Op = new OperExe();
	}	
	
	/*
	 init the Configuration
	*/
	
	public void initConfig(String fileName) throws FileNotFoundException, IOException{
		String line;
		int[] values = new int[9];
		int i = 0;
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
			while((line =br.readLine()) != null){
				String[] res = line.split(" = ");
				values[i] = Integer.parseInt(res[1]);
				i++;
			}
		}
		delay = new Delays(values[0],values[1],values[2],values[3]);
	}
	
	
	/*
	Add new Operation
	*/
	public static void newOperation(String instruction, int cycleIssued, int cycleExecute, int cycleWrite) {
		Op.add(new Operation(instruction, cycleIssued, cycleExecute, cycleWrite));
	}
	
	
	// get PC
	public int getPC() {
		return PC;
	}
	
	// get PC
	public void setPC(int pc) {
		PC = pc;
	}
	
}
