package compfac.blocks.tileentities;

import compfac.world.dimension.FactoryHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityItemBufferFactory extends TileEntitySyncController implements IInventory{

	private boolean isOutput = false;
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean("isOutput", this.isOutput);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.isOutput = compound.getBoolean("isOutput");
	}
	
	public TileEntityItemBufferFactory setOutput() {
		this.isOutput = true;
		return this;
	}
	
	/**
	 * this is a copycat kinda of a real one, this gets the real one
	 * @return the real one
	 */
	private TileEntityItemBuffer getReal(){
		TileEntityItemBuffer ent;
		if(!this.isOutput){
			ent = this.getController().getTileEntityItemInput();
		} else {
			ent = this.getController().getTileEntityItemOutput();
		}
		if(ent == null) {
			System.out.println("Real one is null"); 
//			return new TileEntityItemBuffer();
		}
		return ent;
	}
	
	public String getCustomName(){
		return this.getReal().getCustomName();
	}

	public void setCustomName(String cn){
		this.getReal().setCustomName(cn);
	}

	@Override
	public String getName() {
		return this.getReal().getName();
	}

	@Override
	public boolean hasCustomName() {
		return this.getReal().hasCustomName();
	}

	@Override
	public int getSizeInventory() {
		return this.getReal().getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.getReal().getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return this.getReal().decrStackSize(index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return this.getReal().removeStackFromSlot(index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.getReal().setInventorySlotContents(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return this.getReal().getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return false;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(this.getReal() == null) return false;
		return this.getReal().isItemValidForSlot(index, stack);
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		this.getReal().clear();
	}
}
