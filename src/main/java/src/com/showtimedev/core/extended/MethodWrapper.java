package src.com.showtimedev.core.extended;

import lombok.RequiredArgsConstructor;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

@RequiredArgsConstructor
public class MethodWrapper{
	
	public final ClassNode owner;
	
	public final MethodNode mn;
	
	public String getFormatted(){
		return owner.name + "#" + mn.name + mn.desc;
	}
	
	@Override
	public String toString(){
		return "Owner: " + owner.name + " Method: " + mn.name;
	}
}
