package src.com.showtimedev.core.analysis.stack_tracer.stack_elements;

import javafx.util.Pair;
import src.com.showtimedev.core.analysis.stack_tracer.stack_elements.math.MathOp;

import java.util.ArrayList;
import java.util.List;

public abstract class NumberReference<K extends NumberReference<K>> extends StackReference<K>{
	
	private final List<Pair<MathOp, NumberReference>> modifiers = new ArrayList<>();
	
	public List<Pair<MathOp, NumberReference>> getModifiers(){
		return modifiers;
	}
	
	public void addModifier(MathOp operation, NumberReference<?> reference){
		modifiers.add(new Pair<>(operation, reference));
		if(reference.isUnknown()) setUnknown(true);
	}
	
	public void copyModifiers(NumberReference<?> numberReference){
		this.modifiers.addAll(numberReference.getModifiers());
	}
	
	@Override
	protected K cloneSelf(){
		K num = cloneNumber();
		num.getModifiers().addAll(modifiers);
		return num;
	}
	
	protected abstract K cloneNumber();
}
