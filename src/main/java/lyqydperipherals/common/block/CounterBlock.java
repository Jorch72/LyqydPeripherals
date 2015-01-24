package lyqydperipherals.common.block;

import lyqydperipherals.common.tileentity.CounterTile;
import cpw.mods.fml.common.registry.GameRegistry;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CounterBlock extends BlockContainer implements IPeripheralProvider {

	public CounterBlock() {
		super(Material.ground);
		setHardness(0.5F);
		setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerBlock(this, "counterBlock");
		GameRegistry.registerTileEntity(CounterTile.class, "counterBlock");
		setBlockName("lyqydperipherals.counterblock");
		this.setBlockTextureName("lyqydperipherals:counterblock");
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register) {
		this.blockIcon = register.registerIcon("lyqydperipherals:counterblock");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
		return new CounterTile();
	}
	
	@Override
	public IPeripheral getPeripheral(World world, int x, int y, int z, int side) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity instanceof CounterTile) {
		return (IPeripheral)entity;
		}
		return null;
	}
	
}
