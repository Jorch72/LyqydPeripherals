package lyqydperipherals.common.peripheral;

import net.minecraft.tileentity.TileEntity;
import lyqydperipherals.common.tileentity.CounterTile;
import dan200.computercraft.api.peripheral.IPeripheral;

public class CounterBlockPeripheral extends PeripheralBase implements IPeripheral {

	
	public CounterBlockPeripheral(TileEntity tile) {
		if (tile instanceof CounterTile) {
			CounterTile counterTile = (CounterTile) tile;
		}
		
	}
	
}
