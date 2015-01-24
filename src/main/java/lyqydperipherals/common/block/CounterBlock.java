package lyqydperipherals.common.block;

import lyqydperipherals.common.tileentity.CounterTile;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CounterBlock extends BlockContainer {

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
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
