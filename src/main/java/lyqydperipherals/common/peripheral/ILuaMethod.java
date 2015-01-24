package lyqydperipherals.common.peripheral;

import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.lua.ILuaContext;

public interface ILuaMethod {
	public String getName();
	public Object[] execute(IComputerAccess computer, ILuaContext context, Object[] args);
}
