package src.com.showtimedev.simulation;

/**
 * Class to interact with the given Java Environment. This takes in instructions and manages the environment accordingly.
 * It handles the stack, local variables and investigates branching and dead spots.
 */
public class JavaEnvironmentProcessor{
	
	public JavaEnvironmentProcessor(JavaEnvironment environment){
		this.environment = environment;
	}
	
	/**
	 * Java Environment given to interact with
	 */
	private final JavaEnvironment environment;
	
	void process(short opcode){
		process(opcode, null);
	}
	
	void process(short opcode, Object... obj){
		switch(opcode){
			case 0x0:
				return;
			case 0x1:
				push(null);
				return;
			case 0x2:
			case 0x3:
			case 0x4:
			case 0x5:
			case 0x6:
			case 0x7:
			case 0x8:
				push(opcode - 3);
				return;
			case 0x9:
			case 0xa:
				push((long)(opcode - 9));
				return;
			case 0xb:
			case 0xc:
			case 0xd:
				push((float)(opcode - 0xb));
				return;
			case 0xe:
				push(0d);
				return;
			case 0xf:
				push(1d);
				return;
			case 0x10:
				push((Byte)obj[0]);
				return;
			case 0x11:
				int num = (Byte)obj[0] << 8 | (Byte)obj[1];
				push(num);
				return;
			case 0x12:
				push(pop((int)obj[0]));
				return;
			case 0x13:
			case 0x14:
				int wide = (int)obj[0] << 8 | (int)obj[1];
				push(pop(wide));
				return;
			case 0x15:
			case 0x16:
			case 0x17:
			case 0x18:
			case 0x19:
				push(pop((int)obj[0]));
				return;
			case 0x1a:
			case 0x1b:
			case 0x1c:
			case 0x1d:
				push(environment.localVars[opcode - 0x1a]);
				return;
			case 0x1e:
			case 0x1f:
			case 0x20:
			case 0x21:
				push(environment.localVars[opcode - 0x1e]);
				return;
			case 0x22:
			case 0x23:
			case 0x24:
			case 0x25:
				push(environment.localVars[opcode - 0x22]);
				return;
			case 0x26:
			case 0x27:
			case 0x28:
			case 0x29:
				push(environment.localVars[opcode - 0x26]);
				return;
			case 0x2a:
			case 0x2b:
			case 0x2c:
			case 0x2d:
				push(environment.localVars[opcode - 0x2d]);
				return;
			case 0x2e:
			case 0x2f:
			case 0x30:
			case 0x31:
			case 0x32:
			case 0x33:
			case 0x34:
			case 0x35:
				int index =
			
		}
	}
	
	/**
	 * Adds an element to the stack
	 * @param o Object to be added
	 */
	private void push(Object o){
		environment.stack.add(o);
	}
	
	/**
	 * Pops an Object from the constant pool from the given index
	 * @param index Index to retrieve from
	 * @return Object
	 */
	private Object pop(int index){
		return environment.constantPool.get(index);
	}
	
	
}
