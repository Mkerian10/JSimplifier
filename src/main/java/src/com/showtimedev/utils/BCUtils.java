package src.com.showtimedev.utils;

import jdk.internal.org.objectweb.asm.tree.*;
import src.com.showtimedev.instructions.*;

public class BCUtils{
	
	public static void printInstructions(MethodNode mn){
		for(AbstractInsnNode ain : mn.instructions.toArray()){
			System.out.println(makeInstruction(ain).toString());
		}
	}
	
	public static Instruction makeInstruction(AbstractInsnNode node){
		if(node instanceof LdcInsnNode){
			return new ConstantInstruction((LdcInsnNode) node);
		}else if(node instanceof FieldInsnNode){
			return new FieldInstruction((FieldInsnNode) node);
		}else if(node instanceof IincInsnNode){
			return new IncrementInstruction((IincInsnNode) node);
		}else if(node instanceof IntInsnNode){
			return new IntegerInstruction((IntInsnNode) node);
		}else if(node instanceof InvokeDynamicInsnNode){
			return new InvokeDynamicInstruction((InvokeDynamicInsnNode) node);
		}else if(node instanceof JumpInsnNode){
			return new JumpInstruction((JumpInsnNode) node);
		}else if(node instanceof LabelNode){
			return new LabelInstruction((LabelNode) node);
		}else if(node instanceof LookupSwitchInsnNode){
			return new LookupSwitchInstruction((LookupSwitchInsnNode) node);
		}else if(node instanceof MethodInsnNode){
			return new MethodInstruction((MethodInsnNode) node);
		}else if(node instanceof MultiANewArrayInsnNode){
			return new MultiNewArrayNode((MultiANewArrayInsnNode) node);
		}else if(node instanceof TableSwitchInsnNode){
			return new TableSwitchInstruction((TableSwitchInsnNode) node);
		}else if(node instanceof TypeInsnNode){
			return new TypeInstruction((TypeInsnNode) node);
		}else if(node instanceof VarInsnNode){
			return new VarInstruction((VarInsnNode) node);
		}else if(node instanceof InsnNode){
			return new ZInstruction((InsnNode) node);
		}
		throw new NullPointerException(node.toString() + " Opcode: " + node.getOpcode());
	}
	
	public static String className(ClassNode cn){
		return className(cn.name);
	}
	
	public static String className(String s){
		if(s.contains("/")){
			String[] split = s.split("/");
			return split[split.length - 1];
		}else{
			return s;
		}
	}
}
