package lyqydperipherals.common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import dan200.computercraft.api.ComputerCraftAPI;
import lyqydperipherals.LyqydPeripherals;
import lyqydperipherals.common.block.CounterBlock;

public class CommonProxy {

	public void preInit() {
		registerBlocks();
	}
	
	public void init() {
		ComputerCraftAPI.registerPeripheralProvider(LyqydPeripherals.Blocks.counterBlock);
		registerRecipes();
	}
	
	private void registerBlocks() {
		LyqydPeripherals.Blocks.counterBlock = new CounterBlock();
	}
	
	private void registerRecipes() {
		ItemStack counterBlock = new ItemStack(LyqydPeripherals.Blocks.counterBlock);
		ItemStack hopper = new ItemStack((Block)Block.blockRegistry.getObject("hopper"));
		ItemStack stone = new ItemStack((Block)Block.blockRegistry.getObject("stone"));
		ItemStack comparator = new ItemStack((Item)Item.itemRegistry.getObject("comparator"));
		GameRegistry.addRecipe(counterBlock,
				"s s",
				" hc",
				"s s",
		        's', stone, 'h', hopper, 'c', comparator);
	}
}
