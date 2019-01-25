package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

public class IntegerReference extends NumberReference<IntegerReference>{
	
	public IntegerReference(int value){
		this.value = value;
	}
	
	private int value;
	
	public int getValue(){
		return value;
	}
	
	public void setValue(int value){
		this.value = value;
	}
	
	@Override
	protected IntegerReference cloneNumber(){
		return new IntegerReference(value);
	}
}
