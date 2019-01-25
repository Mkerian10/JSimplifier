package src.com.showtimedev.core.analysis.stack_tracer;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import src.com.showtimedev.core.analysis.stack_tracer.stack_elements.*;
import src.com.showtimedev.core.analysis.stack_tracer.stack_elements.math.MathOp;
import src.com.showtimedev.utils.DescParser;

public abstract class StackTracer implements Opcodes{
	
	private final ByteCodeStack stack = new ByteCodeStack();
	
	private final ByteCodeLocals locals = new ByteCodeLocals();
	
	protected final void resolveInsn(AbstractInsnNode insn) throws LocalReferenceNotFoundException, UnexpectedStackElementException{
		final int opcode = insn.getOpcode();
		switch(opcode){
			case NOP:
				return;
			case ACONST_NULL:
				stack.push(new NullReference());
				return;
			case ICONST_M1:
				stack.push(new IntegerReference(-1));
				return;
			case ICONST_0:
			case ICONST_1:
			case ICONST_2:
			case ICONST_3:
			case ICONST_4:
			case ICONST_5:
				stack.push(new IntegerReference(opcode - 3));
				return;
			case LCONST_0:
			case LCONST_1:
				stack.push(new LongReference(opcode - 9));
				return;
			case FCONST_0:
			case FCONST_1:
			case FCONST_2:
				stack.push(new FloatReference(opcode - 11));
				return;
			case DCONST_0:
			case DCONST_1:
				stack.push(new DoubleReference(opcode - 14));
				return;
			case BIPUSH:
				stack.push(new IntegerReference(((IntInsnNode) insn).operand));
				return;
			case SIPUSH:
				stack.push(new IntegerReference(((IntInsnNode) insn).operand));
				return;
			case LDC:
				stack.push(resolveLdc((LdcInsnNode) insn));
				return;
			case ILOAD:
			case LLOAD:
			case FLOAD:
			case DLOAD:
			case ALOAD:
				VarInsnNode varInsnNode = (VarInsnNode) insn;
				StackReference ref = locals.retrieveVariable(varInsnNode.var);
				checkVarType(varInsnNode, ref);
				stack.push(ref);
				return;
			case IALOAD:
				stack.pop(ArrayReference.class);
				stack.pop(IntegerReference.class);
				IntegerReference integerReference = new IntegerReference(-1);
				integerReference.setUnknown(true);
				stack.push(integerReference);
				return;
			case LALOAD:
				stack.pop(ArrayReference.class);
				stack.pop(IntegerReference.class);
				LongReference longReference = new LongReference(-1);
				longReference.setUnknown(true);
				stack.push(longReference);
				return;
			case FALOAD:
				stack.pop(ArrayReference.class);
				stack.pop(IntegerReference.class);
				FloatReference floatReference = new FloatReference(-1);
				floatReference.setUnknown(true);
				stack.push(floatReference);
				return;
			case DALOAD:
				stack.pop(ArrayReference.class);
				stack.pop(IntegerReference.class);
				DoubleReference doubleReference = new DoubleReference(-1);
				doubleReference.setUnknown(true);
				stack.push(doubleReference);
				return;
			case AALOAD:
				stack.pop(ArrayReference.class);
				stack.pop(IntegerReference.class);
				ObjectReference objectReference = new ObjectReference();
				objectReference.setUnknown(true);
				stack.push(objectReference);
				return;
			case BALOAD:
				stack.pop(ArrayReference.class);
				stack.pop(IntegerReference.class);
				ByteReference byteReference = new ByteReference((byte)-1);
				byteReference.setUnknown(true);
				stack.push(byteReference);
				return;
			case CALOAD:
				stack.pop(ArrayReference.class);
				stack.pop(IntegerReference.class);
				CharReference charReference = new CharReference((char)-1);
				charReference.setUnknown(true);
				stack.push(charReference);
				return;
			case SALOAD:
				stack.pop(ArrayReference.class);
				stack.pop(IntegerReference.class);
				ShortReference shortReference = new ShortReference((short)-1);
				shortReference.setUnknown(true);
				stack.push(shortReference);
				return;
			case ISTORE:
				VarInsnNode varInsnNode1 = (VarInsnNode)insn;
				IntegerReference integerReference1 = stack.pop(IntegerReference.class);
				locals.updateVariable(integerReference1, varInsnNode1.var);
				return;
			case LSTORE:
				VarInsnNode varInsnNode2 = (VarInsnNode)insn;
				LongReference longReference1 = stack.pop(LongReference.class);
				locals.updateVariable(longReference1, varInsnNode2.var);
				return;
			case FSTORE:
				VarInsnNode varInsnNode3 = (VarInsnNode)insn;
				FloatReference floatReference1 = stack.pop(FloatReference.class);
				locals.updateVariable(floatReference1, varInsnNode3.var);
				return;
			case DSTORE:
				VarInsnNode varInsnNode4 = (VarInsnNode)insn;
				DoubleReference doubleReference1 = stack.pop(DoubleReference.class);
				locals.updateVariable(doubleReference1, varInsnNode4.var);
				return;
			case ASTORE:
				VarInsnNode varInsnNode5 = (VarInsnNode)insn;
				ObjectReference objectReference1 = stack.pop(ObjectReference.class);
				locals.updateVariable(objectReference1, varInsnNode5.var);
				return;
			case IASTORE:
			case LASTORE:
			case FASTORE:
			case DASTORE:
			case AASTORE:
			case BASTORE:
			case CASTORE:
			case SASTORE:
				stack.pop(); //value
				stack.pop(IntegerReference.class); //Index
				stack.pop(ArrayReference.class); //Array
				//Arrays don't have much support right now, so no operation is done after popping
				//TODO: Add arrays?
				return;
			case POP2:
				StackReference popped = stack.pop();
				//pop2 only pops one reference if it's a long or double
				if(popped instanceof LongReference || popped instanceof DoubleReference){
					return;
				}
			case POP:
				stack.pop();
				return;
			case DUP:
				StackReference<?> reference = stack.pop();
				StackReference<?> reference1 = reference.duplicate();
				stack.push(reference);
				stack.push(reference1);
				return;
			case DUP_X1:
				StackReference<?> reference2 = stack.pop();
				StackReference<?> unused = stack.pop();
				StackReference<?> cloned = reference2.duplicate();
				stack.push(cloned);
				stack.push(unused);
				stack.push(reference2);
				return;
			case DUP_X2:
				StackReference<?> reference3 = stack.pop();
				StackReference<?> unused1 = stack.pop();
				StackReference<?> unused2 = null;
				if(!(unused1 instanceof LongReference || unused1 instanceof DoubleReference)){
					unused2 = stack.pop();
				}
				StackReference<?> duplicate = reference3.duplicate();
				stack.push(duplicate);
				if(unused2 != null) stack.push(unused2);
				stack.push(unused1);
				stack.push(reference3);
				return;
			case DUP2:
				StackReference<?> reference4 = stack.pop();
				if(reference4 instanceof DoubleReference || reference4 instanceof LongReference){
					StackReference<?> cloned1 = reference4.duplicate();
					stack.push(cloned1);
					stack.push(reference4);
				}else{
					StackReference<?> reference5 = stack.pop();
					StackReference<?> firstCloned = reference4.duplicate();
					StackReference<?> secondCloned = reference5.duplicate();
					stack.push(secondCloned);
					stack.push(firstCloned);
					stack.push(reference5);
					stack.push(reference4);
				}
				return;
			case DUP2_X1:
			case DUP2_X2:
				//TODO: DO THIS LATER
				throw new UnsupportedOperationException("Do this when I don't feel like killing myself");
			case SWAP:
				StackReference<?> reference5 = stack.pop();
				StackReference<?> reference6 = stack.pop();
				stack.push(reference6);
				stack.push(reference5);
				return;
			case IADD:
			case LADD:
			case FADD:
			case DADD:
			case ISUB:
			case LSUB:
			case FSUB:
			case DSUB:
			case IMUL:
			case LMUL:
			case FMUL:
			case DMUL:
			case IDIV:
			case LDIV:
			case DDIV:
			case FDIV:
			case IREM:
			case LREM:
			case DREM:
			case FREM:
			case ISHL:
			case ISHR:
			case IUSHR:
			case LSHL:
			case LUSHR:
			case LSHR:
			case IAND:
			case LAND:
			case IOR:
			case LOR:
			case IXOR:
			case LXOR:
				NumberReference<?> numberReference = stack.pop(NumberReference.class);
				NumberReference<?> numberReference1 = stack.pop(NumberReference.class);
				numberReference.addModifier(getMathOp(opcode), numberReference1);
				stack.push(numberReference);
				return;
			case INEG:
				IntegerReference integerReference2 = stack.pop(IntegerReference.class);
				integerReference2.setValue(~integerReference2.getValue());
				stack.push(integerReference2);
				return;
			case LNEG:
				LongReference longReference2 = stack.pop(LongReference.class);
				longReference2.setValue(~longReference2.getValue());
				stack.push(longReference2);
				return;
			case DNEG:
				DoubleReference doubleReference2 = stack.pop(DoubleReference.class);
				doubleReference2.setValue(-doubleReference2.getValue());
				stack.push(doubleReference2);
				return;
			case FNEG:
				FloatReference floatReference2 = stack.pop(FloatReference.class);
				floatReference2.setValue(floatReference2.getValue());
				stack.push(floatReference2);
				return;
			case IINC:
				IincInsnNode iincInsnNode = (IincInsnNode)insn;
				StackReference<?> reference7 = locals.retrieveVariable(iincInsnNode.var);
				if(reference7 instanceof IntegerReference){
					((IntegerReference) reference7).setValue(((IntegerReference) reference7).getValue() + iincInsnNode.incr);
				}else{
					throw new UnexpectedStackElementException("Iinc requires var to be integer at index " + iincInsnNode.var + ", " + reference7.getClass().getSimpleName() + " found.");
				}
				return;
			case I2L:
				IntegerReference integerReference3 = stack.pop(IntegerReference.class);
				LongReference longReference3 = new LongReference(integerReference3.getValue());
				longReference3.copyModifiers(integerReference3);
				stack.push(longReference3);
				return;
			case I2F:
				IntegerReference integerReference4 = stack.pop(IntegerReference.class);
				FloatReference floatReference3 = new FloatReference(integerReference4.getValue());
				floatReference3.copyModifiers(integerReference4);
				stack.push(floatReference3);
				return;
			case I2D:
				IntegerReference integerReference5 = stack.pop(IntegerReference.class);
				DoubleReference doubleReference3 = new DoubleReference(integerReference5.getValue());
				doubleReference3.copyModifiers(integerReference5);
				stack.push(doubleReference3);
				return;
			case L2I:
				LongReference longReference4 = stack.pop(LongReference.class);
				IntegerReference integerReference6 = new IntegerReference((int)longReference4.getValue());
				integerReference6.copyModifiers(longReference4);
				stack.push(integerReference6);
				return;
			case L2F:
				LongReference longReference5 = stack.pop(LongReference.class);
				FloatReference floatReference4 = new FloatReference((float)longReference5.getValue());
				floatReference4.copyModifiers(longReference5);
				stack.push(floatReference4);
				return;
			case L2D:
				LongReference longReference6 = stack.pop(LongReference.class);
				DoubleReference doubleReference4 = new DoubleReference((double)longReference6.getValue());
				doubleReference4.copyModifiers(longReference6);
				stack.push(doubleReference4);
				return;
			case F2I:
				FloatReference floatReference5 = stack.pop(FloatReference.class);
				IntegerReference integerReference7 = new IntegerReference((int)floatReference5.getValue());
				integerReference7.copyModifiers(floatReference5);
				stack.push(integerReference7);
				return;
			case F2D:
				FloatReference floatReference6 = stack.pop(FloatReference.class);
				DoubleReference doubleReference5 = new DoubleReference((double)floatReference6.getValue());
				doubleReference5.copyModifiers(floatReference6);
				stack.push(doubleReference5);
				return;
			case F2L:
				FloatReference floatReference7 = stack.pop(FloatReference.class);
				LongReference longReference7 = new LongReference((long)floatReference7.getValue());
				longReference7.copyModifiers(floatReference7);
				stack.push(longReference7);
				return;
			case D2F:
				DoubleReference doubleReference6 = stack.pop(DoubleReference.class);
				FloatReference floatReference8 = new FloatReference((float)doubleReference6.getValue());
				floatReference8.copyModifiers(doubleReference6);
				stack.push(floatReference8);
				return;
			case D2I:
				DoubleReference doubleReference7 = stack.pop(DoubleReference.class);
				IntegerReference integerReference8 = new IntegerReference((int)doubleReference7.getValue());
				integerReference8.copyModifiers(doubleReference7);
				stack.push(integerReference8);
				return;
			case D2L:
				DoubleReference doubleReference8 = stack.pop(DoubleReference.class);
				LongReference longReference8 = new LongReference((long)doubleReference8.getValue());
				longReference8.copyModifiers(doubleReference8);
				stack.push(longReference8);
				return;
			case I2B:
				IntegerReference integerReference9 = stack.pop(IntegerReference.class);
				ByteReference byteReference1 = new ByteReference((byte)integerReference9.getValue());
				byteReference1.copyModifiers(integerReference9);
				stack.push(byteReference1);
				return;
			case I2C:
				IntegerReference integerReference10 = stack.pop(IntegerReference.class);
				CharReference charReference1 = new CharReference((char)integerReference10.getValue());
				charReference1.copyModifiers(integerReference10);
				stack.push(charReference1);
				return;
			case I2S:
				IntegerReference integerReference11 = stack.pop(IntegerReference.class);
				ShortReference shortReference1 = new ShortReference((short)integerReference11.getValue());
				shortReference1.copyModifiers(integerReference11);
				stack.push(shortReference1);
				return;
			case LCMP:
				LongReference longReference9 = stack.pop(LongReference.class);
				LongReference longReference10 = stack.pop(LongReference.class);
				IntegerReference integerReference12 = new IntegerReference(Long.compare(longReference9.getValue(), longReference10.getValue()));
				stack.push(integerReference12);
				return;
			case FCMPL:
				FloatReference floatReference9 = stack.pop(FloatReference.class);
				FloatReference floatReference10 = stack.pop(FloatReference.class);
				IntegerReference integerReference13 = new IntegerReference(Float.compare(floatReference9.getValue(), floatReference10.getValue()));
				stack.push(integerReference13);
				return;
			case FCMPG:
				FloatReference floatReference11 = stack.pop(FloatReference.class);
				FloatReference floatReference12 = stack.pop(FloatReference.class);
				IntegerReference integerReference14 = new IntegerReference(Float.compare(floatReference12.getValue(), floatReference11.getValue()));
				stack.push(integerReference14);
				return;
			case DCMPG:
				DoubleReference doubleReference9 = stack.pop(DoubleReference.class);
				DoubleReference doubleReference10 = stack.pop(DoubleReference.class);
				IntegerReference integerReference15 = new IntegerReference(Double.compare(doubleReference9.getValue(), doubleReference10.getValue()));
				stack.push(integerReference15);
				return;
			case DCMPL:
				DoubleReference doubleReference11 = stack.pop(DoubleReference.class);
				DoubleReference doubleReference12 = stack.pop(DoubleReference.class);
				IntegerReference integerReference16 = new IntegerReference(Double.compare(doubleReference12.getValue(), doubleReference11.getValue()));
				stack.push(integerReference16);
				return;
			case IFEQ:
			case IFNE:
			case IFLT:
			case IFGE:
			case IFGT:
			case IFLE:
				stack.pop();
				return;
			case IF_ACMPEQ:
			case IF_ACMPNE:
			case IF_ICMPEQ:
			case IF_ICMPGE:
			case IF_ICMPLE:
			case IF_ICMPLT:
			case IF_ICMPNE:
			case IF_ICMPGT:
				stack.pop();
				stack.pop();
				return;
			case JSR:
				stack.pop();
				return;
			case TABLESWITCH:
			case LOOKUPSWITCH:
				stack.pop();
				return;
			case IRETURN:
			case LRETURN:
			case DRETURN:
			case FRETURN:
			case ARETURN:
				stack.pop();
				return;
			case GETFIELD:
				stack.pop();
			case GETSTATIC:
				FieldInsnNode fieldInsnNode = (FieldInsnNode)insn;
				stack.push(identifyReferenceFromDesc(fieldInsnNode.desc));
				return;
			case PUTFIELD:
				stack.pop();
			case PUTSTATIC:
				stack.pop();
				return;
			case INVOKEDYNAMIC:
			case INVOKEINTERFACE:
			case INVOKEVIRTUAL:
				stack.pop();
			case INVOKESTATIC:
				MethodInsnNode methodInsnNode = (MethodInsnNode)insn;
				DescParser descParser = new DescParser(methodInsnNode.desc);
				for(int i = 0; i < descParser.paramCount(); i++){
					stack.pop();
				}
				stack.push(identifyReferenceFromDesc(descParser.getReturn()));
				return;
			case NEW:
				TypeInsnNode typeInsnNode = (TypeInsnNode)insn;
				stack.push(identifyReferenceFromDesc(typeInsnNode.desc));
				return;
			case NEWARRAY:
				TypeInsnNode typeInsnNode1 = (TypeInsnNode)insn;
				IntegerReference reference8 = stack.pop(IntegerReference.class);
				stack.push(new ArrayReference(null));
				return;
			case ANEWARRAY:
				TypeInsnNode typeInsnNode2 = (TypeInsnNode)insn;
				IntegerReference reference9 = stack.pop(IntegerReference.class);
				stack.push(new ArrayReference(null));
				return;
			case ATHROW:
				ObjectReference objectReference2 = stack.pop(ObjectReference.class);
				stack.clearStack();
				stack.push(objectReference2);
				return;
			case INSTANCEOF:
				TypeInsnNode typeInsnNode3 = (TypeInsnNode)insn;
				ObjectReference objectReference3 = stack.pop(ObjectReference.class);
				//TODO: If I ever implement explicit object type then fix this
				IntegerReference reference10 = new IntegerReference(-1);
				reference10.setUnknown(true);
				stack.push(reference10);
				return;
			case MONITORENTER:
			case MONITOREXIT:
				stack.pop();
				return;
				
				
				
		}
	}
	
	private StackReference resolveLdc(LdcInsnNode insn){
		Object constant = insn.cst;
		if(constant instanceof Integer){
			return new IntegerReference((int) constant);
		}else if(constant instanceof Float){
			return new FloatReference((float) constant);
		}else if(constant instanceof Long){
			return new LongReference((long) constant);
		}else if(constant instanceof Double){
			return new DoubleReference((double) constant);
		}else if(constant instanceof String){
			return new StringReference((String) constant);
		}else if(constant instanceof Type){
			throw new UnsupportedOperationException("Figure out what the fuck type is for LDC");
		}else{
			throw new UnsupportedOperationException("Unknown LDC: " + insn.cst);
		}
	}
	
	private void checkVarType(VarInsnNode varInsnNode, StackReference reference) throws UnexpectedStackElementException{
		Class<? extends StackReference> expectedClass;
		
		switch(varInsnNode.getOpcode()){
			case ILOAD:
				expectedClass = IntegerReference.class;
				break;
			case LLOAD:
				expectedClass = LongReference.class;
				break;
			case FLOAD:
				expectedClass = FloatReference.class;
				break;
			case DLOAD:
				expectedClass = DoubleReference.class;
				break;
			case ALOAD:
				expectedClass = ObjectReference.class;
				break;
			default:
				throw new RuntimeException("Opcode not found: " + varInsnNode.getOpcode());
		}
		
		if(!expectedClass.isInstance(reference)){
			throw new UnexpectedStackElementException("Local variable returned unexpected stack element type. Expected: " + expectedClass.getSimpleName() + " Actual: " + reference.getClass().getSimpleName());
		}
	}
	
	private MathOp getMathOp(int opcode){
		switch((opcode & 0b11100) >> 2){
			case 0:
				return MathOp.ADD;
			case 1:
				return MathOp.SUB;
			case 2:
				return MathOp.MUL;
			case 3:
				return MathOp.DIV;
			case 5:
				return MathOp.REM;
		}
		
		switch(opcode){
			case 0x78:
			case 0x79:
				return MathOp.SHL;
			case 0x7a:
			case 0x7b:
				return MathOp.SHR;
			case 0x7c:
			case 0x7d:
				return MathOp.LSH;
			case 0x7e:
			case 0x7f:
				return MathOp.AND;
			case 0x80:
			case 0x81:
				return MathOp.OR;
			case 0x82:
			case 0x83:
				return MathOp.XOR;
		}
		throw new RuntimeException("Error getting MathOp from opcode, opcode: " + opcode);
	}
	
	private StackReference<?> identifyReferenceFromDesc(String desc){
		StackReference<?> reference;
		if(desc.startsWith("L")){
			return new ObjectReference();
		}else if(desc.equalsIgnoreCase("I")){
			reference = new IntegerReference(-1);
		}else if(desc.equalsIgnoreCase("J")){
			reference = new LongReference(-1);
		}else if(desc.equalsIgnoreCase("D")){
			reference = new DoubleReference(-1);
		}else if(desc.equalsIgnoreCase("Z")){
			reference = new IntegerReference(-1);
		}else if(desc.equalsIgnoreCase("S")){
			reference = new ShortReference((short)-1);
		}else if(desc.equalsIgnoreCase("F")){
			reference =  new FloatReference(-1);
		}else if(desc.equalsIgnoreCase("B")){
			reference = new ByteReference((byte)-1);
		}else if(desc.contains("[")){
			reference = new ArrayReference(null);
		}else if(desc.equalsIgnoreCase("V")){
			reference = new VoidReference();
		}else{
			throw new RuntimeException("Couldn't identify: " + desc);
		}
		
		reference.setUnknown(true);
		return reference;
	}
}
