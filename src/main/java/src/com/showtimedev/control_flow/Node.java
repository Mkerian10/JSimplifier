package src.com.showtimedev.control_flow;

import src.com.showtimedev.instructions.Instruction;

public interface Node{
	Instruction current();
	Instruction[] next();
}
