package lyqydperipherals.common.tileentity;

import lyqydperipherals.common.peripheral.CounterBlockPeripheral;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class CounterTile extends TileEntity implements IPeripheral, IInventory, ISidedInventory {
	
	public CounterTile() {
		this.peripheral = new CounterBlockPeripheral(this);
	}
	
	private ItemStack[] inventory = new ItemStack[2];
	private CounterBlockPeripheral peripheral;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (!worldObj.isRemote) {
			if (this.inventory[1] == null && this.inventory[0] != null) {
				peripheral.queueEvent("item_count", new Object[] {inventory[0].getUnlocalizedName(), inventory[0].getItemDamage(), inventory[0].stackSize});
				this.inventory[1] = this.inventory[0];
				this.inventory[0] = null;
				this.markDirty();
			}
		}
	}
	
	@Override
	public String getType() {
		return peripheral.getType();
	}

	@Override
	public String[] getMethodNames() {
		return peripheral.getMethodNames();
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context,
			int method, Object[] arguments) throws LuaException,
			InterruptedException {
		return peripheral.callMethod(computer, context, method, arguments);
	}

	@Override
	public void attach(IComputerAccess computer) {
		peripheral.attach(computer);
	}

	@Override
	public void detach(IComputerAccess computer) {
		peripheral.detach(computer);
	}

	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] {0, 1};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		if (slot == 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		if (slot == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int count) {
		if (this.inventory[slot] != null) {
            ItemStack itemstack;

            if (this.inventory[slot].stackSize <= count) {
                itemstack = this.inventory[slot];
                this.inventory[slot] = null;
                this.markDirty();
                return itemstack;
            } else {
                itemstack = this.inventory[slot].splitStack(count);

                if (this.inventory[slot].stackSize == 0) {
                    this.inventory[slot] = null;
                }

                this.markDirty();
                return itemstack;
            }
        } else {
            return null;
        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.inventory[slot] != null) {
            ItemStack itemstack = this.inventory[slot];
            this.inventory[slot] = null;
            return itemstack;
        } else {
            return null;
        }
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack item) {
		if (slot == 0) {
			this.inventory[slot] = item;
	
	        if (item != null && item.stackSize > this.getInventoryStackLimit()) {
	            item.stackSize = this.getInventoryStackLimit();
	        }
	
	        this.markDirty();
		}
	}

	@Override
	public String getInventoryName() {
		return "container.counter";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return false;
	}

	@Override
	public void openInventory() {		
	}

	@Override
	public void closeInventory() {		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return slot == 0;
	}

}
