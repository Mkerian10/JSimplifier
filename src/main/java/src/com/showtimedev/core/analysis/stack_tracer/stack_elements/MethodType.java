package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

public class MethodType extends StackReference<MethodType>{
	
	public MethodType(String methodType){
		this.methodType = methodType;
	}
	
	private final String methodType;
	
	@Override
	protected MethodType cloneSelf(){
		return new MethodType(methodType);
	}
}
