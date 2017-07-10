package compfac.blocks.tileentities;

import java.util.UUID;

import compfac.CompressedFactory;
import compfac.Reference;
import compfac.blocks.BlockCasing;
import compfac.blocks.BlockController;
import compfac.blocks.BlockEnergyOutlet;
import compfac.blocks.BlockFactoryDoor;
import compfac.blocks.BlockItemInput;
import compfac.blocks.BlockItemOutput;
import compfac.blocks.BlockMultiBlockPart;
import compfac.init.ModItems;
import compfac.world.dimension.FactoryHandler;
import compfac.world.dimension.FactorySize;
import compfac.world.dimension.TheCompressedFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class TileEntityController extends TileEntityCompressorBase{

	//this are probably going to need to be persistent
	private boolean isCompFacFormed = false;
	private boolean isStructureOkay = false;
	private boolean isOn = false;
	private int ID = -1;
	private boolean dimensionNrIsSet = false;
	private int dimensionNr;
	private BlockPos posB4Teleport = new BlockPos(0,0,0);
	private BlockPos itemInputPos;
	private BlockPos itemOutputPos;
	private BlockPos energyOutletPos;
	private BlockPos teleporterPos;
	private UUID playerUUID;
	private FactoryHandler handler;
	
	//this are probably not going to need to be persistent
	private BlockPos casingTopControlBlock;
	private BlockPos casingDownBackControlBlock;
	private int layerNumber = 0;
	private int targetDimension;
//	private TheCompressedFactory compFac;
	private EntityPlayerMP player;
	private boolean firstTeleporter = true;
	private boolean firstEnergyOutlet = true;
	private boolean firstItemInput = true;
	private boolean firstItemOutput = true;
	private boolean firstController = true;
	private int xSize;
	private int ySize;
	private int zSize;
	
	public int getId(){
		return ID;
	}
	
	public void setId(int id2) {
		this.ID = id2;
	}
	
	public boolean hasTileEntityItemInput(){
		return itemInputPos != null;
	}
	public TileEntityItemBuffer getTileEntityItemInput(){
		if(!hasTileEntityItemInput()) return null;
		return (TileEntityItemBuffer) this.getWorld().getTileEntity(itemInputPos);
	}
	
	public boolean hasTileEntityItemOutput(){
		return itemOutputPos != null;
	}
	public TileEntityItemBuffer getTileEntityItemOutput(){
		if(!hasTileEntityItemOutput()) return null;
		return (TileEntityItemBuffer) this.getWorld().getTileEntity(itemOutputPos);
	}
	
	public boolean hasTileEntityEnergyOutlet(){
		return energyOutletPos != null;
	}
	public TileEntityEnergyOutlet getTileEntityEnergyOutlet(){
		if(!hasTileEntityEnergyOutlet()) return null;
		return (TileEntityEnergyOutlet) this.getWorld().getTileEntity(energyOutletPos);
	}
	
	public boolean setdimensionNr(int i){
		dimensionNr = i;
		targetDimension = getTargetDimension(i);
		if(targetDimension == 0) return false;
		handler = getHandler(targetDimension);
		dimensionNrIsSet = true;
		return dimensionNrIsSet;
	}
	
	public static FactoryHandler getHandler(int targetDim) {
		if(targetDim == Reference.l1FactoryDimensionID){
			return FactoryHandler.getHandler(1);
		} else if(targetDim == Reference.l2FactoryDimensionID){
			return FactoryHandler.getHandler(2);
		} else if(targetDim == Reference.l3FactoryDimensionID){
			return FactoryHandler.getHandler(3);
		} else {
			throw new NullPointerException("Error in getting the facHandler for the dimension: " + targetDim);
		}
	}

	public int getSourceDimension(){
		return dimensionNr;
	}
	
	public FactoryHandler getFactoryHandler(){
		return handler;
	}
	
	public int getTargetDimension(){
		return targetDimension;
	}
	
	private int getTargetDimension(int i) {
		if(i == Reference.l1FactoryDimensionID){
			return Reference.l2FactoryDimensionID;
		} else if(i == Reference.l2FactoryDimensionID){
			return Reference.l3FactoryDimensionID;
		} else if(i == Reference.l3FactoryDimensionID){
			this.messagePlayer("You are at the last level, you cannot compress space more", -1);
			return 0;
		} else {
			return Reference.l1FactoryDimensionID;
		}
	}
	
	public boolean activateFactoryDestruction() {
		if(!isCompFacFormed){
			this.messagePlayer("Factory already destroyed", 0);
			return true;
		}
//		this.messagePlayer("Factory will be destroyed");
		isCompFacFormed = false;
		isOn = false;
		chunkLoad(isOn);
		if(this.handler.destroyFactory(this.ID)){
			this.messagePlayer(this.facInfo() + "was destroyed", 0);
			return true;
		}
		return false;
	}
	
	private String facInfo(){
		return "Factory Nr:" + this.ID + " of Lvl:" + this.handler.getFactoryLvl();
	}
	
	public void resize() {
		this.messagePlayer("Factory resizing will begin", 0);
		isCompFacFormed = false;
		isOn = false;
	}
	
	public boolean isCompFacFormed(){
		return this.isCompFacFormed;
	}
	
	public int onActivation() {	
		if(!isCompFacFormed){
			this.messagePlayer("Checking compreessor integrity...", 1);
			if(isStructureOkay  = this.checkStructure()){
				this.messagePlayer("Compressing Space...", 1);
				if(Reference.energyOutletMandatory && !this.getTileEntityEnergyOutlet().readyAndExtractForCompression()){
					this.messagePlayer("Not enough energy for compressing procedure", -1);
					this.messagePlayer("At least "
							+ Math.max(Reference.energyToCompress, Reference.energyToMaintainCompression) 
							+ " energy required", -1);
					this.messagePlayer("Space Compression failed", 0);
					return 1;
				}
				if(this.isCompFacFormed = this.formCompressedFac()){
					this.messagePlayer("Space Compression Successfull", 1);
					isOn = true;
					chunkLoad(isOn);
					return 0;
				} 
				else {
					this.messagePlayer("Space Compression failed", 0);
					return 1;
				}
			}
			else{
				this.messagePlayer("Compressor structure is not well formed", -1);
				return 1;
			}
		}
		
		if (isOn) {
			this.messagePlayer("Turning Compressor off", -1);
			this.turnFacOff();
		} else {
			this.messagePlayer("Turning Compressor on", -1);
			if(!isStructureStillValid()){
				this.messagePlayer("Failed to turn the compressor on", -1);
			}
		}
		return 0;
	}
	
	public boolean isStructureStillValid(){
		this.isOn = ((this.isStructureOkay = this.checkAndFormStructure()) && isCompFacFormed);
//		if(isOn)
//			isOn = this.isCompFacFormed;
		this.publishStatus(isOn);
		chunkLoad(isOn);
		return isOn;
	}
	
	private void turnFacOff(){
		this.isOn = false;
		this.publishStatus(isOn);
		chunkLoad(isOn);
	}
	
	public void notEnoughEnergy() {
		turnFacOff();
	}
	
	private boolean chunkLoad(boolean load) {
		return this.handler.chunkLoadFactory(load, this.ID);
	}

	private boolean formCompressedFac() {
		if(this.handler == null)
			return false;
		if(this.ID == -1){
			this.handler.generateFactory(new FactorySize(xSize, ySize, zSize, false), this);
			if(!checkAndFormStructure()){
				System.out.println("[ERROR] Structure was not formed well (right structure, error on formation of mb)");
				return false;
			}
			this.messagePlayer("Created Factory with Id: " + this.ID, 1);
			this.posB4Teleport = this.player.getPosition();
		} else {
//			System.out.println("My Id is " + this.ID);
			if(!this.handler.generateFactoryWithId(this.ID, new FactorySize(xSize, ySize, zSize, false), this)){
				this.messagePlayer("Error changing Factory with Id: " + this.ID, -1);
				this.messagePlayer("You are probably trying to reduce the size of the factory, "
						+ "and that would make it so the walls of the factory would replace the blocks you previous put there.", -1);
				this.messagePlayer("Return the factory to the previous size or larger, remove the blocks farther away from the"
						+ "teleporter so that your blocks are only in the area of the factory"
						+ "you were trying to make, and try again", -1);
				return false;
			}
			if(!checkAndFormStructure()){
				System.out.println("[ERROR] Structure was not formed well (right structure, error on formation of mb)");
				return false;
			}
			this.messagePlayer("Changed Factory with Id: " + this.ID, 1);
		}
		this.messagePlayer("Number of factories: " + this.handler.getNumberFactories(), 1);
		return true;
	}
	
	private boolean checkAndFormStructure() {
		return this.checkAndFormStructure(true);
	}
	
	private boolean checkStructure() {
		return this.checkAndFormStructure(false);
	}

	private boolean checkAndFormStructure(boolean shouldFormStructure) {
		this.firstTeleporter = true;
		this.firstEnergyOutlet = true;
		this.firstItemInput = true;
		this.firstItemOutput = true;
		this.firstController = true;
		
//		this.teleporterPos = null;
//		this.energyOutletPos = null;
//		this.itemInputPos = null;
//		this.itemOutputPos = null;
		
		if(!checkControllerFace()) {
			System.out.println("Controller Face says no");
			return false;
		}
		int xDiference = this.casingTopControlBlock.getX() - this.casingDownBackControlBlock.getX();
		int yDiference = this.casingTopControlBlock.getY() - this.casingDownBackControlBlock.getY();
		int zDiference = this.casingTopControlBlock.getZ() - this.casingDownBackControlBlock.getZ();
//		System.out.println("X diference:" + xDiference + " Y diference:" + yDiference + " Z diference:" + zDiference);
		boolean xTopBiggerDown = true;
		boolean yTopBiggerDown = true;
		boolean zTopBiggerDown = true;
		if(xDiference<0){
			xDiference=xDiference*-1;
			xTopBiggerDown = false;
		}
		if(yDiference<0){
			yDiference=yDiference*-1;
			yTopBiggerDown = false;
		}
		if(zDiference<0){
			zDiference=zDiference*-1;
			zTopBiggerDown = false;
		}
		if(xDiference == 0 || yDiference == 0 || zDiference == 0){
			return false;
		}
		if(!this.setLayerNumber(xDiference > yDiference? 
				(xDiference > zDiference? 
						xDiference+1 : 
						zDiference+1):
				(yDiference > zDiference? 
						yDiference+1 : 
						zDiference+1))){
			
			this.messagePlayer("Layer Number is Incorrect, more layers needed", -1);
			this.messagePlayer("Or the Compressor is too small, minimum size is a 3x3x3 cube", -1);
			return false;
		}
//		System.out.println("Layer Number: " + layerNumber);
		
		int xTop = this.casingTopControlBlock.getX();
		int yTop = this.casingTopControlBlock.getY();
		int zTop = this.casingTopControlBlock.getZ();
		int xDown = this.casingDownBackControlBlock.getX();
		int yDown = this.casingDownBackControlBlock.getY();
		int zDown = this.casingDownBackControlBlock.getZ();
		int l = 0;
		int m = 0;
		int n = 0;
		boolean hasAirInside = false;
		for(int i = (xTopBiggerDown?xDown:xTop); i <= (xTopBiggerDown?xTop:xDown); i++){
			for(int j = (yTopBiggerDown?yDown:yTop); j <= (yTopBiggerDown?yTop:yDown); j++){
				for(int k = (zTopBiggerDown?zDown:zTop); k <= (zTopBiggerDown?zTop:zDown); k++){
					if(n > this.layerNumber-1 && n < zDiference-this.layerNumber+1 &&
							m > this.layerNumber-1 && m < yDiference-this.layerNumber+1 &&
							l > this.layerNumber-1 && l < xDiference-this.layerNumber+1){
						if(this.worldObj.getBlockState(new BlockPos( i, j, k)).getBlock() != Blocks.AIR){
							this.messagePlayer("This block is not air and it should.", -1);
							this.messagePlayer("X:" + i + " Y:" + j + " Z:" + k, -1);
							return false;
						}
						
						hasAirInside = true;
					}
					else if(!this.isBlockPartOfCompressorSpecial(this.worldObj.getBlockState(new BlockPos( i, j, k)).getBlock(), new BlockPos( i, j, k), shouldFormStructure)){
						this.messagePlayer("This block is not a compressor part and it should.", -1);
						this.messagePlayer("X:" + i + " Y:" + j + " Z:" + k, -1);		
						return false;
					}
					n++;
				}
				n = 0;
				m++;
			}
			m = 0;
			l++;
		}
		//this might be useless
		if(!hasAirInside){
			this.messagePlayer("No air inside. The Compresser should be hollow, minimum size is 3x3x3 and hollow inside", -1);
			return false;
		}
		
		if(firstTeleporter){
			this.messagePlayer("There needs to be at least one Teleporter", -1);
			return false;
		}
		
		if(Reference.energyOutletMandatory && firstEnergyOutlet){
			this.messagePlayer("There needs to be at least one EnergyOutlet to provide energy", -1);
			return false;
		}
		
		this.xSize = xDiference+1-layerNumber*2;
		this.ySize = yDiference+1-layerNumber*2;
		this.zSize = zDiference+1-layerNumber*2;
				
		return true;
	}

	/**
	 * Sets the number of layers of casing the structure needs to have
	 * @param i
	 * @return true if the i given is valid
	 */
	private boolean setLayerNumber(int i){
//		System.out.println("this is max " + i + " dimension");
		if(layerNumber==0){
//			if(i == 1 || i == 2 || i == 7 || i == 8 || i == 12 || i == 13){
//				return false;
//			}
			if(i > 2 && i < 7){
				this.layerNumber = 1;
				return true;
			}
			if(i > 8 && i < 12){
				this.layerNumber = 2;
				return true;
			}
			if(i > 13 && i < 16){
				this.layerNumber = 3;
				return true;
			}
			return false;
		}
		return true;
	}
	
	private boolean checkControllerFace() {
		int upDimension = 0;
		int leftDimension = 0;
		int downDimension = 0;
		int rightDimension = 0;
		BlockPos selfPos = this.getPos();
		BlockPos pos;
		int i = 0;
		
		int dir = 0; //1 for with the xy plane, 2 for with the zy plane, 3 for with the xz plane
		pos = selfPos.add(1, 0, 0);
		if(!this.isBlockPartOfCompressor(pos)){
			dir = 2;
		}
		pos = selfPos.add(-1, 0, 0);
		if(!this.isBlockPartOfCompressor(pos)){
			dir = 2;
		}
		pos = selfPos.add(0, 1, 0);
		if(!this.isBlockPartOfCompressor(pos)){
			dir = 3;
		}
		pos = selfPos.add(0, -1, 0);
		if(!this.isBlockPartOfCompressor(pos)){
			dir = 3;
		}
		pos = selfPos.add(0, 0, 1);
		if(!this.isBlockPartOfCompressor(pos)){
			dir = 1;
		}
		pos = selfPos.add(0, 0, -1);
		if(!this.isBlockPartOfCompressor(pos)){
			dir = 1;
		}
		if(dir == 0){
			return false;
		}
//		System.out.println("dir:" + dir);
		//distance to top
		for(i = 1; i < 15; i++){
			if(dir == 1){
				pos = selfPos.add(0, i, 0);
			}
			if(dir == 2){
				pos = selfPos.add(0, i, 0);
			}
			if(dir == 3){
				pos = selfPos.add(i, 0, 0);
			}
			if(!this.isBlockPartOfCompressor(pos)){
				upDimension = i-1;
//				upDimension++;
				break;
			}
		}
		for(i = 1; i < 15; i++){
			if(dir == 1){
				pos = selfPos.add(0, -i, 0);
			}
			if(dir == 2){
				pos = selfPos.add(0, -i, 0);
			}
			if(dir == 3){
				pos = selfPos.add(-i, 0, 0);
			}
			if(!this.isBlockPartOfCompressor(this.worldObj.getBlockState(pos).getBlock())){
				downDimension = i-1;
//				downDimension++;
				break;
			}
		}
		for(i = 1; i < 15; i++){
			if(dir == 1){
				pos = selfPos.add(-i, 0, 0);
			}
			if(dir == 2){
				pos = selfPos.add(0, 0, i);
			}
			if(dir == 3){
				pos = selfPos.add(0, 0, -i);
			}
			if(!this.isBlockPartOfCompressor(this.worldObj.getBlockState(pos).getBlock())){
				rightDimension = i-1;
//				rightDimension++;
				break;
			}
		}
		for(i = 1; i < 15; i++){
			if(dir == 1){
				pos = selfPos.add(i, 0, 0);
			}
			if(dir == 2){
				pos = selfPos.add(0, 0, -i);
			}
			if(dir == 3){
				pos = selfPos.add(0, 0, i);
			}
			if(!this.isBlockPartOfCompressor(this.worldObj.getBlockState(pos).getBlock())){
				leftDimension = i-1;
//				leftDimension++;
				break;
			}
		}
//		System.out.println("upDimension: " + upDimension);
//		System.out.println("downDimension: " + downDimension);
//		System.out.println("rightDimension: " + rightDimension);
//		System.out.println("leftDimension: " + leftDimension);
		BlockPos upLeftBlock = null;
		BlockPos downRightBlock = null;
		if(dir == 1){
			 upLeftBlock = selfPos.add(leftDimension, upDimension, 0);
			 downRightBlock = selfPos.add(-rightDimension, -downDimension, 0);
		}
		if(dir == 2){
			 upLeftBlock = selfPos.add(0, upDimension, -leftDimension);
			 downRightBlock = selfPos.add(0, -downDimension, rightDimension);
		}
		if(dir == 3){
			 upLeftBlock = selfPos.add(upDimension, 0, leftDimension);
			 downRightBlock = selfPos.add(-downDimension, 0, -rightDimension);
		}
		int backDir = 0;
		BlockPos checkBackDir = null;
		if(dir == 1){
			checkBackDir = downRightBlock.add(0, 0, -1);
		}
		if(dir == 2){
			checkBackDir = downRightBlock.add(-1, 0, 0);
		}
		if(dir == 3){
			checkBackDir = downRightBlock.add(0, -1, 0);
		}
		if(!this.isBlockPartOfCompressor(this.worldObj.getBlockState(checkBackDir).getBlock())){
			backDir = 1;
		}
			
		BlockPos downRightBackBlock = downRightBlock;
		Block block = new BlockCasing();
		i = -1;
		while(this.isBlockPartOfCompressor(block)){
			if(dir == 1){
				if(backDir == 0){
					downRightBackBlock = downRightBackBlock.add(0, 0, -1);
				}
				else if(backDir == 1){
					downRightBackBlock = downRightBackBlock.add(0, 0, 1);
				}
			}
			if(dir == 2){
				if(backDir == 0){
					downRightBackBlock = downRightBackBlock.add(-1, 0, 0);
				}
				else if(backDir == 1){
					downRightBackBlock = downRightBackBlock.add(1, 0, 0);
				}
			}
			if(dir == 3){
				if(backDir == 0){
					downRightBackBlock = downRightBackBlock.add(0, -1, 0);
				}
				else if(backDir == 1){
					downRightBackBlock = downRightBackBlock.add(0, 1, 0);
				}
			}
			block = worldObj.getBlockState(downRightBackBlock).getBlock();
			i++;
		}
		if(i>14) return false;
		if(dir == 1){
			if(backDir == 0){
				downRightBackBlock = downRightBackBlock.add(0, 0, 1);
			}
			else if(backDir == 1){
				downRightBackBlock = downRightBackBlock.add(0, 0, -1);
			}
		}
		if(dir == 2){
			if(backDir == 0){
				downRightBackBlock = downRightBackBlock.add(1, 0, 0);
			}
			else if(backDir == 1){
				downRightBackBlock = downRightBackBlock.add(-1, 0, 0);
			}
		}
		if(dir == 3){
			if(backDir == 0){
				downRightBackBlock = downRightBackBlock.add(0, 1, 0);
			}
			else if(backDir == 1){
				downRightBackBlock = downRightBackBlock.add(0, -1, 0);
			}
		}
		
		this.casingTopControlBlock = upLeftBlock;
		this.casingDownBackControlBlock = downRightBackBlock;
//		System.out.println("ControlBlocks");
//		System.out.println("ControlBlockTop: x: " + upLeftBlock.getX() + " y:" + upLeftBlock.getY() + " z:" + upLeftBlock.getZ());
//		System.out.println("ControlBlockBottom: x: " + downRightBlock.getX() + " y:" + downRightBlock.getY() + " z:" + downRightBlock.getZ());
//		System.out.println("ControlBlockTop: x: " + downRightBackBlock.getX() + " y:" + downRightBackBlock.getY() + " z:" + downRightBackBlock.getZ());
		return true;
	}
	
	private boolean isBlockPartOfCompressor(BlockPos pos) {
		return this.isBlockPartOfCompressor(worldObj.getBlockState(pos).getBlock());
	}
	
	private boolean isBlockPartOfCompressor(Block block){
		if(block instanceof BlockMultiBlockPart){
			return true;
		}
		return false;
	}
	
	private boolean isBlockPartOfCompressorSpecial(Block block, BlockPos pos, boolean shouldSetController){
		boolean isBlockPartOfCompressor = isBlockPartOfCompressorSpecial(block, pos);
		if(isBlockPartOfCompressor && shouldSetController && !(block instanceof BlockController)){
			((TileEntitySyncController)this.worldObj.getTileEntity(pos)).setController(this);
		}
		return isBlockPartOfCompressor;
	}
	
	private boolean isBlockPartOfCompressorSpecial(Block block, BlockPos pos){
		if(block instanceof BlockCasing){
			return true;
		} else if(block instanceof BlockController){
			if(firstController){
				firstController = false;
				return true;
			}
			this.messagePlayer("There can only be one Controller in the structure", -1);
			return false;
		} else if(block instanceof BlockEnergyOutlet){
			if(firstEnergyOutlet){
//				this.energyOutletEnt = ((TileEntityEnergyOutlet) this.getWorld().getTileEntity(pos));
				//this is important, cause this blocks in the compFac will use the controller to know their tileEntities
				this.energyOutletPos = pos;
				firstEnergyOutlet = false;
				return true;
			}
			this.messagePlayer("There can only be one EnergyOutlet in the structure", -1);
			return false;
		} else if(block instanceof BlockItemInput){
			if(firstItemInput){
//				this.itemBufferEnt = ((TileEntityItemBuffer) this.getWorld().getTileEntity(pos));
				//this is important, cause this blocks in the compFac will use the controller to know their tileEntities
				this.itemInputPos = pos;
				firstItemInput = false;
				return true;
			}
			this.messagePlayer("There can only be one ItemInput in the structure", -1);
			return false;
		} else if(block instanceof BlockItemOutput){
			if(firstItemOutput){
//				this.itemBufferEnt = ((TileEntityItemBuffer) this.getWorld().getTileEntity(pos));
				//this is important, cause this blocks in the compFac will use the controller to know their tileEntities
				this.itemOutputPos = pos;
				firstItemOutput = false;
				return true;
			}
			this.messagePlayer("There can only be one ItemOutput in the structure", -1);
			return false;
		} else if(block instanceof BlockFactoryDoor){
//			((TileEntityFactoryDoor) this.getWorld().getTileEntity(pos)).setController(this);
//			return true;
			if(firstTeleporter){
				this.teleporterPos = pos;
				firstTeleporter = false;
				return true;
			}
			this.messagePlayer("There can only be one Teleporter in the structure", -1);
			return false;
		}
		return false;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setBoolean("isStructureOkay", this.isStructureOkay);
		compound.setBoolean("isCompFacFormed", this.isCompFacFormed);
		compound.setBoolean("dimensionNrIsSet", this.dimensionNrIsSet);
		if(dimensionNrIsSet) {
			compound.setInteger("controllerDimension", this.dimensionNr);
		}
		compound.setBoolean("isOn", this.isOn);
		compound.setInteger("controllerID", this.ID);
		compound.setString("playerUUID", this.playerUUID.toString());
		if (isStructureOkay) {
			if(energyOutletPos != null)
				this.handler.writeBlockPosToNBT(compound, "EnergyOutlet", this.energyOutletPos);
			if(itemInputPos != null)
				this.handler.writeBlockPosToNBT(compound, "ItemInput", this.itemInputPos);
			if(itemOutputPos != null)
				this.handler.writeBlockPosToNBT(compound, "ItemOutput", this.itemOutputPos);
			this.handler.writeBlockPosToNBT(compound, "Teleporter", this.teleporterPos);
		}
		this.handler.writeBlockPosToNBT(compound, "PosB4Teleport", this.posB4Teleport);
		return compound;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		// care with this, dont know it the client ever needs this, but since it does not write, i will assume not
//		if(this.worldObj.isRemote)
//			return;
		this.isStructureOkay = compound.getBoolean("isStructureOkay");
		this.isCompFacFormed = compound.getBoolean("isCompFacFormed");
		this.dimensionNrIsSet = compound.getBoolean("dimensionNrIsSet");
		if (dimensionNrIsSet){
			this.dimensionNr = compound.getInteger("controllerDimension");
			this.setdimensionNr(dimensionNr);
		}
		this.isOn = compound.getBoolean("isOn");
		this.ID = compound.getInteger("controllerID");
		this.playerUUID = UUID.fromString(compound.getString("playerUUID"));
		if(this.handler != null) {
			if(this.ID != -1){
				this.handler.addController(ID, this);
			}
			if(isStructureOkay){
				this.energyOutletPos = this.handler.readBlockPosFromNBT(compound, "EnergyOutlet");
				this.itemInputPos = this.handler.readBlockPosFromNBT(compound, "ItemInput");
				this.itemOutputPos = this.handler.readBlockPosFromNBT(compound, "ItemOutput");
				this.teleporterPos = this.handler.readBlockPosFromNBT(compound, "Teleporter");
			}
			this.posB4Teleport = this.handler.readBlockPosFromNBT(compound, "PosB4Teleport");
		}
	}
	
	//this is used by teleporter to know if it can teleport
	public boolean isOff() {
		return !isOn;
	}
	
	public void publishStatus(boolean status){
		if(this.teleporterPos == null)
			throw new NullPointerException("The teleporter position cant be null");
		TileEntitySyncController tl = ((TileEntitySyncController)this.worldObj.getTileEntity(this.teleporterPos));
		if((!tl.controllerIsOff()) == status)
			return;
		tl.setControllerStatus(status);
		if(this.hasTileEntityEnergyOutlet())
			this.getTileEntityEnergyOutlet().setControllerStatus(status);
		if(this.hasTileEntityItemInput())
			this.getTileEntityItemInput().setControllerStatus(status);
		if(this.hasTileEntityItemOutput())
			this.getTileEntityItemOutput().setControllerStatus(status);
	}

	public void setPlayer(EntityPlayer playerIn) {
		player = (EntityPlayerMP) playerIn;
		this.playerUUID = player.getUniqueID();
	}
	
	public void messagePlayer(String msg, int lvl) {
		if(lvl <= Reference.chatMessagesLvl)
			return;
		if(this.player == null)
			this.player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(this.playerUUID);
		if(!(this.player == null))
			FactoryHandler.messagePlayer(player, msg, lvl);
	}

	public void setPositionBeforeTeleport(BlockPos blockPos) {
		posB4Teleport = blockPos;
	}

	public BlockPos getPositionBeforeTeleport() {
		return posB4Teleport;
	}

	public static TileEntityController getcontroller(int controllerId, int controlTargetDimension) {
		return TileEntityController.getHandler(controlTargetDimension).getController(controllerId);
	}
}
