package src.com.showtimedev.wrappers;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.FieldNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

public class FieldWrapper{
	
	public FieldWrapper(@NotNull FieldNode fieldNode, @NotNull ClassNode owner){
		this(fieldNode, owner, null);
	}
	
	public FieldWrapper(@NotNull FieldNode fieldNode, @NotNull ClassNode owner, MethodNode optOwner){
		this.fieldNode = fieldNode;
		this.owner = owner;
		this.optOwner = optOwner;
	}
	
	private final FieldNode fieldNode;
	
	private final ClassNode owner;
	
	private final MethodNode optOwner;
	
	public FieldNode getFieldNode(){
		return fieldNode;
	}
	
	public ClassNode getOwner(){
		return owner;
	}
	
	@Nullable
	public MethodNode getOptOwner(){
		return optOwner;
	}
}
