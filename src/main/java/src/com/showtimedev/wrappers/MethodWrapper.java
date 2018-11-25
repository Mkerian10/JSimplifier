package src.com.showtimedev.wrappers;

import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

public class MethodWrapper{
	
	public MethodWrapper(MethodNode node, ClassNode owner){
		this.node = node;
		this.owner = owner;
	}
	
	private final MethodNode node;
	
	private final ClassNode owner;
	
	public MethodNode getNode(){
		return node;
	}
	
	public ClassNode getOwner(){
		return owner;
	}
}
