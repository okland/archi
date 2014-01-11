package struc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;

import java.lang.Float;

public class Execute {
	
	// define the instructions
	final int LOAD = 0; final int STORE = 1; final int JUMP = 2; final int BEQ = 3;
	final int BNE = 4; final int ADD = 5; final int ADDI = 6; final int SUB = 7;
	final int SUBI = 8; final int ADDS = 9; final int SUBS = 10; final int MULTS = 11;
	final int HALT = 12;
	
	private Hashtable<String, ResStationItem<?>> regStatus;
	static ArrayList<ResStationItem<?>> opWait2Exec;
	Stack<Operation> stPrint = new Stack<Operation>(); 
	int cyNum = 1; 
	boolean flag;
	int[] intRegisters;
	float[] floatRegisters;
	int oldPC = -1;
	 
	public Execute() {
	}
	
	public Stack<Operation> ExecuteEnv(Environment env) {

		 // Status table of registers
		 regStatus = new Hashtable<String, ResStationItem<?>>();
		 opWait2Exec = new ArrayList<ResStationItem<?>>();
		 
		 
		intRegisters = new int [16];
	    floatRegisters = new float[16];
		  
		// init the float registers
		for(int i=0; i<floatRegisters.length; i++){
			floatRegisters[i] = i;
		}
		// insert first operation from memory to opertion list 
		env.newOperation(env.mem[env.pc]);
		
		while(!env.opList.isEmpty()){
			flag = false;
			execStore(env);
			Operation op = env.opList.get(0);
			System.out.println(String.format("pc %d  intrsuction %d opcode %d dst %d src0 %d src1 %d imm %d"  ,env.pc , op.getInst(), op.getOpCode(), op.getDst(), op.getSrc0(), op.getSrc1(), op.getImm()));

			switch (op.getOpCode()) {
				case LOAD: 
					loadComp(env);
					break;
					
				case STORE:
					storeComp(env);
					break;
					
				case ADD:
					addComp(env, false);
					break;
					
				case ADDI:
					addComp(env, true);
					break;
					
				case SUB:
					addComp(env, false);
					break;
					
				case SUBI:
					addComp(env, true);
					break;
				
				case ADDS:
					sComp(env, 0);
					break;
					
				case SUBS:
					sComp(env, 1);
					break;
					
				case MULTS:
					sComp(env, 2);
					break;
				
				case BEQ:
					equalComp(env, true);
					break;
					
				case BNE:
					equalComp(env, false);
					break;
					
				case JUMP:
					env.opList.remove(0);
					env.pc += op.getImm() - 1;
					op.setCycleExectue(cyNum);
					op.setCycleWrite(-1);
					flag = true;
					break;
					
				case HALT:
					env.opList.clear();
					op.setCycleExectue(cyNum);
					op.setCycleWrite(-1);
					break;
					
					
				default: break;
					
			}
			
			// decode have occur successfuly
			if(flag){
				env.pc++;
                //add operation from memory if still has
                if(env.pc < env.memSize) {
                    Operation opTemp = new Operation(env.mem[env.pc]);
                    opTemp.setCycleIssue(cyNum + 1);
                    env.opList.add(opTemp);
                }
			}
			
			executeCycle(env);
			cyNum++;
            System.out.println("cyNum");
            System.out.println(cyNum);
		}
		
		return stPrint;
	}
	
	/* load function compute
	 * 
	 * */
	
	public void loadComp(Environment env) {
		
		ResStationItem<Integer> newStation;
		Operation op = env.opList.get(0);
		newStation = env.loadResStation.getFreeStation();
		
		if(newStation != null){
			flag = true;
			boolean occ = false;
			String key = "R" + op.getSrc0();
			
			if(regStatus.containsKey(key)){
				occ = true;
				newStation.setQi((ResStationItem<Integer>) regStatus.get(key));
			}
			else{
				newStation.setVi(intRegisters[op.getSrc0()]);
			}
			
			newStation.setVk(op.getImm());
			if(occ){
				newStation.execAtCycle = -1; // set to -1 so we should run it later 
			}
			else{
				op.setCycleExectue(cyNum);
				newStation.execAtCycle = cyNum + env.delay.getMemDelay() + 1; 
			}

            env.opList.remove(0);
            newStation.setOp(op);
			newStation.setBusy(true);
			opWait2Exec.add(newStation);
			regStatus.put("F" + op.getDst(), newStation);	
		}		
	}
	
	
	
	/* store function compute
	 * 
	 * */
	
	public void storeComp(Environment env) {
		
		ResStationItem<Float> newStation;
		Operation op = env.opList.get(0);
		newStation = env.storeResStation.getFreeStation();
		
		if(newStation != null){
			flag = true;
			boolean occ = false;
			String key = "R" + op.getSrc0();
			
			if(regStatus.containsKey(key)){
				occ = true;
				newStation.setQi((ResStationItem<Float>) regStatus.get(key));
			}
			else{
				newStation.setVi((float)intRegisters[op.getSrc0()]);
			}
			
			key =  "F" + op.getSrc1();
			if(regStatus.containsKey(key)){
				occ = true;
				newStation.setQk((ResStationItem<Float>) regStatus.get(key));
			}
			else{
				newStation.setVk(floatRegisters[op.getSrc1()]);
			}
			
			
			
			newStation.setVk((float)op.getImm());
			if(occ){
				newStation.execAtCycle = -1; // set to -1 so we should run it later 
			}
			else{
				op.setCycleExectue(cyNum);
				newStation.execAtCycle = cyNum + env.delay.getMemDelay() + 1; 
			}

            env.opList.remove(0);
            newStation.setOp(op);
			newStation.setBusy(true);
			opWait2Exec.add(newStation);
			regStatus.put("F" + op.getSrc1(), newStation);
		}		
	}
	
	
	/* Add function compute
	 * 
	 * */
	public void addComp(Environment env, boolean imm){
		ResStationItem<Integer> newStation;
		Operation op = env.opList.get(0);
		newStation = env.intResStation.getFreeStation();
		
		if(newStation != null){
			flag = true;
			boolean occ = false;
			String key = "R" + op.getSrc0();
			
			if(regStatus.containsKey(key)){
				occ = true;
				newStation.setQk((ResStationItem<Integer>) regStatus.get(key));
			}
			else{
				newStation.setVk(intRegisters[op.getSrc0()]);
			}
			
			if(imm) {
				newStation.setVi(op.getImm());
			} else {
				key = "R" + op.getSrc1();
				if(regStatus.containsKey(key)){
					occ = true;
					newStation.setQi((ResStationItem<Integer>) regStatus.get(key));
				}
				else{
					newStation.setVi(intRegisters[op.getSrc1()]);
				}
			}
			
			if(occ){
				newStation.execAtCycle = -1; // set to -1 so we should run it later 
			}
			else{
				op.setCycleExectue(cyNum + 1);
				newStation.execAtCycle = cyNum + env.delay.getIntDelay() + 1;
			}

            env.opList.remove(0);
            newStation.setOp(op);
			newStation.setBusy(true);
			opWait2Exec.add(newStation);
			regStatus.put("R" + op.getDst(), newStation);
		}	
	}
	
	
	
	/* ADDS,SUBS,MULTS function compute
	 * 
	 * */
	public void sComp(Environment env, int type){
		ResStationItem<Float> newStation;
		int delay = env.delay.getAddDelay();
		Operation op = env.opList.get(0);
		
		if(type == 2){
			newStation = env.mulResStation.getFreeStation();
			delay = env.delay.getMulDelay();
		}
		
		else{
			newStation = env.addResStation.getFreeStation();
		}
		
		if(newStation != null){
			flag = true;
			boolean occ = false;
			String key = "F" + op.getSrc0();
			
			if(regStatus.containsKey(key)){
				occ = true;
				newStation.setQk((ResStationItem<Float>) regStatus.get(key));
			}
			else{
				newStation.setVk(floatRegisters[op.getSrc0()]);
			}
			
			key = "F" + op.getSrc1();
			if(regStatus.containsKey(key)){
				occ = true;
				newStation.setQi((ResStationItem<Float>) regStatus.get(key));
			}
			else{
				newStation.setVi(floatRegisters[op.getSrc1()]);
			}
							
			if(occ){
				newStation.execAtCycle = -1; // set to -1 so we should run it later 
			}
			else{
				op.setCycleExectue(cyNum + 1);
				newStation.execAtCycle = cyNum + delay + 1;
            }

            env.opList.remove(0);
			newStation.setOp(op);
			newStation.setBusy(true);
			opWait2Exec.add(newStation);
			regStatus.put("F" + op.getDst(), newStation);
		}	
	}
	
	
	/* BEQ and BNE function compute
	 * 
	 * */	
	public void equalComp(Environment env, boolean taken){
		boolean branchT;
		Operation op = env.opList.get(0);


        op.setCycleIssue(cyNum);
        op.setCycleExectue(cyNum);
		
		if(!(regStatus.containsKey("R" + op.getSrc0())) && 
				!(regStatus.containsKey("R" + op.getSrc1()))){
            if(oldPC == -1){
                oldPC = env.pc;
            }
			flag = true;
            op.setCycleWrite(-1);
            stPrint.push(op);
			env.opList.remove(op);
            branchT = intRegisters[op.getSrc0()] == intRegisters[op.getSrc1()];
            if(!taken){
                branchT = !branchT;
            }

            if(branchT){
                env.opList.clear();
                env.pc = oldPC + op.getImm();
                System.out.println("newPc");
                cyNum = cyNum + 2;
                System.out.println(env.pc);
                oldPC = -1;
            }
		}
	}


	
	/* Store execute
	 * 
	 * */

	public void execStore(Environment env){
		Iterator<ResStationItem<?>> iter = opWait2Exec.iterator();
		
		while(iter.hasNext()){
			ResStationItem curr = iter.next();
			Operation op = curr.getOp();
			String key = "F" + op.getSrc1();
			
			
			if((curr.execAtCycle != cyNum) || (op.getOpCode() != STORE)){
				continue;
			}

			env.mem[intRegisters[op.getSrc0()] + op.getImm()] = (int)floatRegisters[(int)(float)curr.getVk() -1];
			op.setCycleWrite(cyNum);
			stPrint.push(op);
			curr.clean();
			regStatus.remove(key);
			iter.remove();	
		}					
	}
	
	
	/* Other operation execute
	 * 
	 * */
	
	public void executeCycle(Environment env){
		Iterator<ResStationItem<?>> iter = opWait2Exec.iterator();
		float result = 0;
		
		while(iter.hasNext()){
			ResStationItem curr = iter.next();
			Operation op = curr.getOp();
			
			String key = "";
			
			
			if(curr.execAtCycle != cyNum){
				continue;
			}
			
			
			switch (op.getOpCode()) {
				case LOAD:
					result = (float)env.mem[(int)curr.getVi() + (int)curr.getVk()];
					floatRegisters [op.getDst()] = result;
					key = "F" + op.getDst();
					break;
				
				case ADD:
				case ADDI:
                    result = (float)((int)curr.getVi() + (int)curr.getVk());
					intRegisters[op.getDst()] = (int)result;
					key = "R" + + op.getDst();
					break;
				
				case SUB:
				case SUBI:
					result = (int)curr.getVi() - (int)curr.getVk();
					intRegisters[op.getDst()] = (int)result;
					key = "R" + op.getDst();
					break;
				
				case ADDS:
					result = (float)curr.getVi() + (float)curr.getVk();
					floatRegisters[op.getDst()] = result;
					key = "R" + op.getDst();
					break;
				
				case SUBS:
					result = (float)curr.getVi() - (float)curr.getVk();
					floatRegisters[op.getDst()] = result;
					key = "R" + op.getDst();
					break;
				
				case MULTS:
					result = (float)curr.getVi() * (float)curr.getVk();
					floatRegisters[op.getDst()] = result;
					key = "R" + op.getDst();
					break;
				
				default: break;	
			}

			updateResStationFloat(curr,env.storeResStation,env.delay.getMemDelay(), result);
			updateResStationFloat(curr,env.addResStation,env.delay.getAddDelay(), result);
			updateResStationFloat(curr,env.mulResStation,env.delay.getMulDelay(), result);
			updateResStationInt(curr,env.loadResStation,env.delay.getMemDelay(),(int)result);
			updateResStationInt(curr,env.intResStation,env.delay.getIntDelay(),(int)result);
			
			op.setCycleWrite(cyNum);
			stPrint.push(op);
			curr.clean();
			regStatus.remove(key);
			iter.remove();	
		}					
	}
	
	/* update Integer stations
	 * 
	 * */
	
	public void updateResStationInt(ResStationItem<Integer> item, ResStation<Integer> resStaion, int delay, int value){
		
		for(ResStationItem<Integer> tempItem: resStaion){
			if(tempItem.getQk() == item){
				tempItem.setVk(value);
                tempItem.setQk(null);
			}
			
			if(tempItem.getQi() == item){
				tempItem.setVi(value);
                tempItem.setQi(null);
			}
			
			if(tempItem.execAtCycle == -1){
				if((tempItem.getQi()) == null && (tempItem.getQk() == null) && (tempItem.getBusy())){
					tempItem.execAtCycle = cyNum + delay + 1;
					tempItem.getOp().setCycleExectue(cyNum);
				}
			}
		}
	}
	
	
	/* update Float stations
	 * 
	 * */
	
public void updateResStationFloat(ResStationItem<Float> item, ResStation<Float> resStaion, int delay, float value){
		
		for(ResStationItem<Float> tempItem: resStaion){
			if(tempItem.getQk() == item){
				tempItem.setVk(value);
                tempItem.setQk(null);
			}
			
			if(tempItem.getQi() == item){
				tempItem.setVi(value);
                tempItem.setQi(null);
			}
			
			if(tempItem.execAtCycle == -1){
				if((tempItem.getQi()) == null && (tempItem.getQk() == null) && (tempItem.getBusy())){
					tempItem.execAtCycle = cyNum + delay + 1;
					tempItem.getOp().setCycleExectue(cyNum);
				}
			}
		}
	}
}