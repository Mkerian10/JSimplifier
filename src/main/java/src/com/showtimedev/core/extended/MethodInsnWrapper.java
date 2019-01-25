package src.com.showtimedev.core.extended;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.objectweb.asm.tree.MethodInsnNode;

@RequiredArgsConstructor
public class MethodInsnWrapper{
	
	@Getter
	private final MethodInsnNode instruction;
	
	@Getter
	private final MethodWrapper wrapper;
}
