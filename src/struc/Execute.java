package struc;

import Enviroment;
import FloatReg;
import Registers;

public class Execute {
	
	
	private ResStation<Integer> IntRegisters;
	private ResStation<Float> FloatRegisters;
	
	
	public static void exec(Operation op, Enviroment env, Registers[] registers, FloatReg[] floatReg){
		int opcode = op.getOpCode();
		
		switch (opcode) {
			case 0:
				load(op, env, registers, floatReg);
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			case 9:
				break;
			case 10:
				break;
			case 11:
				break;
			case 12:
				break;
			default: break;
				
		}
		
				
	}
	
	static void load(Operation op, Enviroment env, Registers[] registers, FloatReg[] floatReg){
		int tempValue;
		tempValue = registers[op.getSrc0()].getVi() + op.getImm();
		op.setTempValue(env.Mem[tempValue]);
	}
	
	
	public static void write(Operation op, Enviroment env, Registers[] registers, FloatReg[] floatReg){
		
		int opcode = op.getOpCode();
		
		switch (opcode) {
			case 0: /* Load instruction */
				floatReg[op.getDst()].setVi(op.getTempValue());
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			case 9:
				break;
			case 10:
				break;
			case 11:
				break;
			case 12:
				break;
			default: break;
				
		}
		
	}


}