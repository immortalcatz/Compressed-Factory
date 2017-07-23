package compfac.blocks.tileentities;

import compfac.Reference;
import compfac.world.dimension.FactoryHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityEnergyOutlet extends TileEntityMultiBlockPart implements ITickable{
	
	private EnergyStorage energy = new EnergyStorage(Reference.energyOutletCapacity, Reference.energyOutletMaxReceive, Reference.energyOutletMaxExtract);
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY){
			if (energy == null){
				assert false : "Energy object cannot be null";
			} else {
				return (T) energy;
			}
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == net.minecraftforge.energy.CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
	}
	
	public int getEnergyStored() {
		return energy.getEnergyStored();
	}
	
	public IEnergyStorage getEnergyStorage() {
		return this.energy;
	}
	
	public static boolean transferEnergy(IEnergyStorage source, IEnergyStorage target){
		return transferEnergy(source, target, source.getEnergyStored());
	}
	
	public static boolean transferEnergy(IEnergyStorage source, IEnergyStorage target, int ammount){
		if(source != null && target != null && ammount > 0
				&& source.canExtract() && target.canReceive()){
			return source.extractEnergy(target.receiveEnergy(ammount, false), false) > 0;
		}
		return false;
	}
	
	public boolean readyAndExtractForCompression() {
		int energyNeeded = Math.max(Reference.energyToCompress, Reference.energyToMaintainCompression);
		if(energyNeeded == this.energy.extractEnergy(energyNeeded, true)){
			int energyNeededToCompress = Reference.energyToCompress;
			return energyNeededToCompress == this.energy.extractEnergy(energyNeededToCompress, false);
		}
		return false;
	}
	
	@Override
	public void update(){
		if(this.worldObj.isRemote || !this.setupWasDone()) 
			return;
		if(this.getController().isOff())
			return;
		if(Reference.energyToMaintainCompression > 0){
			int energyNeeded = Reference.energyToMaintainCompression;
			if(energyNeeded == this.energy.extractEnergy(energyNeeded, true)){
				this.energy.extractEnergy(energyNeeded, false);
			} else {
				this.getController().notEnoughEnergy();
			}
		}
		for (EnumFacing facing : EnumFacing.VALUES) {
			BlockPos pos = this.pos.offset(facing);
			TileEntity te = this.getWorld().getTileEntity(pos);
			if(te == null) continue;
			if (te.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
				IEnergyStorage energyStorage = te.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
				if (energyStorage != null && energyStorage.canReceive()) {
					TileEntityEnergyOutlet.transferEnergy(this.energy, energyStorage);
				}
			}
		}
	}
}
