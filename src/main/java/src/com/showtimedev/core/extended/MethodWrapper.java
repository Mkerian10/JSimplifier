package src.com.showtimedev.core.extended;

import lombok.RequiredArgsConstructor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TryCatchBlockNode;

@RequiredArgsConstructor
public class MethodWrapper{
	
	public final ClassNode owner;
	
	public final MethodNode mn;
	
	@Override
	public String toString(){
		return "Owner: " + owner.name + " Method: " + mn.name;
	}
}
