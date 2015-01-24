package lyqydperipherals.common;

import dan200.computercraft.api.ComputerCraftAPI;
import lyqydperipherals.LyqydPeripherals;
import lyqydperipherals.common.block.CounterBlock;

public class CommonProxy {

	public void preInit() {
		registerBlocks();
	}
	
	public void init() {
		ComputerCraftAPI.registerPeripheralProvider(LyqydPeripherals.Blocks.counterBlock);
	}
	
	private void registerBlocks() {
		LyqydPeripherals.Blocks.counterBlock = new CounterBlock();
	}
}
