package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

public class CharReference extends NumberReference<CharReference>{
	
	public CharReference(char value){
		this.value = value;
	}
	
	private final char value;
	
	@Override
	protected CharReference cloneNumber(){
		return new CharReference(value);
	}
}
