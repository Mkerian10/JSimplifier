package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

public class MethodHandle extends StackReference<MethodHandle>{
	
	public MethodHandle(String methodHandle){
		this.methodHandle = methodHandle;
	}
	
	private final String methodHandle;
	
	@Override
	protected MethodHandle cloneSelf(){
		return new MethodHandle(methodHandle);
	}
}
