package lyqydperipherals.common.tileentity;

import cpw.mods.fml.common.registry.GameData;
import lyqydperipherals.common.peripheral.CounterBlockPeripheral;
import lyqydperipherals.common.util.LPLog;
import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class CounterTile extends TileEntity implements IPeripheral, ISidedInventory {
	
	public CounterTile() {
		this.peripheral = new CounterBlockPeripheral(this);
	}
	
	private ItemStack[] inventory = new ItemStack[2];
	private CounterBlockPeripheral peripheral;
	
	public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        NBTTagList nbttaglist = tag.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < 2; ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.inventory.length) {
                this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < 2; ++i) {
            if (this.inventory[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        tag.setTag("Items", nbttaglist);
    }
    
    private void moveItems() {
    	if (!worldObj.isRemote) {
    		if (this.inventory[1] == null && this.inventory[0] != null) {
				peripheral.queueEvent("item_count", new Object[] {null, GameData.getItemRegistry().getNameForObject(inventory[0].getItem()), inventory[0].getItemDamage(), inventory[0].stackSize});
				this.inventory[1] = this.inventory[0].copy();
				this.inventory[0] = null;
				this.markDirty();
			}
    	}
    }
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		this.moveItems();
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
		return peripheral.equals(other);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
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
		if (slot == 1) {
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
	        this.moveItems();
		} else {
			if (slot == 1 && (item == null) || (item.areItemStacksEqual(item, this.inventory[1]) && item.stackSize <= this.inventory[1].stackSize)) {
				this.inventory[slot] = item;
				this.markDirty();
			}
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
