package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

public class DoubleReference extends NumberReference<DoubleReference>{
	
	public DoubleReference(double value){
		this.value = value;
	}
	
	private double value;
	
	public double getValue(){
		return value;
	}
	
	public void setValue(double value){
		this.value = value;
	}
	
	@Override
	protected DoubleReference cloneNumber(){
		return new DoubleReference(value);
	}
}
