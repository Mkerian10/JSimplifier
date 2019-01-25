package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

public class ShortReference extends NumberReference<ShortReference>{
	
	public ShortReference(short value){
		this.value = value;
	}
	
	private final short value;
	
	@Override
	protected ShortReference cloneNumber(){
		return new ShortReference(value);
	}
}
