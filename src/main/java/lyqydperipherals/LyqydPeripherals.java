package lyqydperipherals;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import lyqydperipherals.common.CommonProxy;
import lyqydperipherals.common.block.CounterBlock;
import lyqydperipherals.common.util.LPLog;

@Mod(modid = "LyqydPeripherals", name = "LyqydPeripherals", version = "0.0.4")
public class LyqydPeripherals {
	
	public static class Blocks {
		public static CounterBlock counterBlock;
	}
	
	public static class Config {

	}
	

	
	@Instance(value = "LyqydPeripherals")
	public static LyqydPeripherals instance;
	
	@SidedProxy(clientSide = "lyqydperipherals.client.ClientProxy", serverSide = "lyqydperipherals.common.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LPLog.init();
		proxy.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}
}