package compfac.blocks.tileentities;

import compfac.world.dimension.FactoryHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityEnergyOutletFactory extends TileEntitySyncController implements ITickable{
	
	/**
	 * this is a copycat kinda of a real one, this gets the real one
	 * @return the real one
	 */
	private TileEntityEnergyOutlet getReal(){
		TileEntityEnergyOutlet ent;
		ent = this.getController().getTileEntityEnergyOutlet();
		if(ent == null) {
			System.out.println("Real one is null"); 
//			return new TileEntityEnergyOutlet();
		}
		return ent;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return this.getReal().getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(this.getReal() == null) return false;
		return this.getReal().hasCapability(capability, facing);
	}
	
	public int getEnergyStored() {
		return this.getReal().getEnergyStored();
	}
	
	@Override
	public void update(){
		if(this.worldObj.isRemote || !this.setupWasDone()) 
			return;
		if(this.getController().isOff()) 
			return;
		for (EnumFacing facing : EnumFacing.VALUES) {
			BlockPos pos = this.pos.offset(facing);
			TileEntity te = this.getWorld().getTileEntity(pos);
			if(te == null) continue;
			if (te.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
				IEnergyStorage energyStorage = te.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
				if (energyStorage != null && energyStorage.canReceive()) {
					TileEntityEnergyOutlet.transferEnergy(this.getReal().getEnergyStorage(), energyStorage);
				}
			}
		}
	}
}
