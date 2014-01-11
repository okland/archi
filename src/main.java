
import java.io.IOException;
import java.util.Properties;
import java.util.Stack;

import struc.Environment;
import struc.Execute;
import struc.Operation;

public class main {
		
	public static void main(String[] args){
		
		Stack<Operation> stPrint = new Stack<Operation>(); 
		
		Environment Env = new Environment();
		
		
		// check if we have get 6 inputs
		if(args.length != 6){
			System.out.println("error: less that 6 argument have given");
			return;
		}
		
		// read the config file
		try {
			Env.initConfig(args[0]);
		} catch (IOException e){
			System.out.println("error: initing Con");
			return;
		}
		
	
		// init the memory
		try {
			Env.initmem(args[1]);
		} catch (IOException e){
			System.out.println("error: initing memory");
			return;
		}
		
		
		Execute exet = new Execute();
		stPrint = exet.ExecuteEnv(Env);

		
		while(!stPrint.empty()){
			System.out.println(stPrint.pop().OpPrint());
		}
				
		
	}		
}
