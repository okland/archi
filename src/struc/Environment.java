package struc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class Environment {
	public int memSize = 1024;
	public long[] mem = new long[memSize];
	public OperExe opList;
	public Delays delay;
	public int pc = 0;
	
	public ResStation<Integer> intResStation;
	public ResStation<Integer> loadResStation;
	public ResStation<Float> storeResStation;
	public ResStation<Float> addResStation;
	public ResStation<Float> mulResStation;
	
	
	public Environment() {
		opList = new OperExe();
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
		
		// init the stations 
		addResStation = new ResStation<Float>(values[4], "ADD");
		mulResStation = new ResStation<Float>(values[5], "MUL");
		intResStation = new ResStation<Integer>(values[6], "ALU");
		loadResStation = new ResStation<Integer>(values[7], "LOAD");
		storeResStation = new ResStation<Float>(values[8], "STORE");
	}
	
	
	/*
	Add new Operation
	*/
	public void newOperation(long instruction) {
		Operation op = new Operation(instruction);
		op.setCycleIssue(1);
		opList.add(op);
	}
	
	
	public void initmem(String fileName) throws FileNotFoundException, IOException{
		String line;
		int i = 0;
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
			while((line =br.readLine()) != null){
				mem[i] = new BigInteger(line,16).longValue();
				i++;
			}
		}
	}
	
	
	// get PC
	public int getPC() {
		return pc;
	}
	
	// get PC
	public void setPC(int _pc) {
		pc = _pc;
	}
	
}
