package lyqydperipherals.common.peripheral;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IPeripheral;

public class PeripheralBase implements IPeripheral {

	private ArrayList<ILuaMethod> methods = new ArrayList<ILuaMethod>();
	private Map<Integer,IComputerAccess> attached = Collections.synchronizedMap(new HashMap());
	
	public void queueEvent(String eventName, Object[] args) {
		int sideIndex = -1;
		for (int i=0; i<args.length; ++i) {
			if (args[i] == null) {
				sideIndex = i;
				break;
			}
		}
		synchronized(this.attached) {
			Iterator<IComputerAccess> iter = attached.values().iterator();
			while (iter.hasNext()) {
				IComputerAccess computer = iter.next();
				Object[] argsCopy = args.clone();
				if (sideIndex >= 0) {argsCopy[sideIndex] = computer.getAttachmentName();}
				computer.queueEvent(eventName, argsCopy);
			}
		}
	}
	
	public void addMethod(ILuaMethod method) {
		methods.add(method);
	}
	
	@Override
	public String getType() {
		return "counter";
	}

	@Override
	public String[] getMethodNames() {
		int len = methods.size();
		String[] names = new String[len];
		for (int i = 0; i < len; i++) {
			names[i] = methods.get(i).getName();
		}
		return names;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {
		return methods.get(method).execute(computer, context, arguments);
	}

	@Override
	public void attach(IComputerAccess computer) {
		synchronized(this.attached) {
			attached.put(computer.getID(), computer);
		}
	}

	@Override
	public void detach(IComputerAccess computer) {
		synchronized(this.attached) {
			attached.remove(computer.getID());
		}
	}

	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}

}