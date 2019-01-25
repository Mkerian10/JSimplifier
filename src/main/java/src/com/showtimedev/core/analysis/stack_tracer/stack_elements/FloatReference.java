package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

public class FloatReference extends NumberReference<FloatReference>{
	
	public FloatReference(float value){
		this.value = value;
	}
	
	private float value;
	
	public float getValue(){
		return value;
	}
	
	public void setValue(float value){
		this.value = value;
	}
	
	@Override
	protected FloatReference cloneNumber(){
		return new FloatReference(value);
	}
}
