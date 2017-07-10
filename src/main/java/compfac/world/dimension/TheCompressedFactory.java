package compfac.world.dimension;

import compfac.CompressedFactory;
import compfac.blocks.BlockFactoryPart;
import compfac.blocks.tileentities.TileEntityController;
import compfac.blocks.tileentities.TileEntityEnergyOutlet;
import compfac.blocks.tileentities.TileEntityEnergyOutletFactory;
import compfac.blocks.tileentities.TileEntityFactoryDoor;
import compfac.blocks.tileentities.TileEntityItemBuffer;
import compfac.blocks.tileentities.TileEntityItemBufferFactory;
import compfac.blocks.tileentities.TileEntitySyncController;
import compfac.helper.TicketHolder;
import compfac.init.ModBlocks;
import compfac.world.ChunkLoaderHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class TheCompressedFactory{
	
//	private static int ringNr = 0; //one day this might be useful for infinite factories per level, as intended :(
	private int id = 0;
	private FactoryHandler facHandler;
	private FactorySize factorySize;
	private FactoryBigChunk factoryBigChunk;
	private World world;
	private int facT;
	private BlockPos teleporterPos;
	private BlockPos energyOutletPos;
	private BlockPos itemInputPos;
	private BlockPos itemOutputPos;
	private final IBlockState doorBlock = ModBlocks.blockStoneDoorUmbreakable.getDefaultState();
//	private final Block stone = Blocks.STONE;
//	private final IBlockState stoneStateUmbreakable = stone.setBlockUnbreakable().getDefaultState();
//	private final IBlockState facHandler.getMaterialBlock().getDefaultState() = stone.getDefaultState();
	
	
	public TheCompressedFactory(int id, FactorySize facS, FactoryHandler facHandler, boolean alreadyGenerated) {
		this.facHandler = facHandler;
		this.id = id;
		this.factorySize = facS;
		factoryBigChunk = new FactoryBigChunk(id);
		facT = calculateFacType(this.factoryBigChunk.getX(), this.factoryBigChunk.getZ());
		this.generateSelf(alreadyGenerated);
	}
	
	public String getInfo(){
		return "" + this.id;
	}

	private void generateSelf(boolean isGenerated) {
		this.world = this.facHandler.getWorld();
		int x = factorySize.getX();
		int y = factorySize.getY();
		int z = factorySize.getZ();
		int chunkOriginXBlock = factoryBigChunk.getXCord();
		int chunkOriginZBlock = factoryBigChunk.getZCord();
		
		
		
//		if(!isGenerated){
//			System.out.println("Im gonna build around here: " + chunkOriginXBlock + chunkOriginZBlock);
//		}
		
		if(this.facT == 1){
			teleporterPos = new BlockPos(chunkOriginXBlock+47-2, 3, chunkOriginZBlock+47-2);
			energyOutletPos = new BlockPos(chunkOriginXBlock+45 -x/2, 3, chunkOriginZBlock+47 -2);
			itemInputPos = new BlockPos(chunkOriginXBlock+47 -2, 3, chunkOriginZBlock+45 -z/2);
			itemOutputPos = new BlockPos(chunkOriginXBlock+47 -2 -x+1, 3, chunkOriginZBlock+45 -z/2);
			if(!isGenerated){
				doTheFactorySpace(x, y, z, chunkOriginXBlock + 47 - x - 2, chunkOriginZBlock + 47 - x - 2);
				doTheBreakableDoor(chunkOriginXBlock+46, chunkOriginZBlock+46);
			}
		}
		else if(this.facT == 2){
			teleporterPos = new BlockPos(chunkOriginXBlock+2, 3, chunkOriginZBlock+47-2);
			energyOutletPos = new BlockPos(chunkOriginXBlock+1 +x/2, 3, chunkOriginZBlock+47 -2);
			itemInputPos = new BlockPos(chunkOriginXBlock+2, 3, chunkOriginZBlock+45 -z/2);
			itemOutputPos = new BlockPos(chunkOriginXBlock+2 +x-1, 3, chunkOriginZBlock+45 -z/2);
			if(!isGenerated){
				doTheFactorySpace(x, y, z, chunkOriginXBlock + 1, chunkOriginZBlock + 47 - z - 2);
				doTheBreakableDoor(chunkOriginXBlock+1, chunkOriginZBlock+46);
			}
		}
		else if(this.facT == 3){
			teleporterPos = new BlockPos(chunkOriginXBlock+47-2, 3, chunkOriginZBlock+2);
			energyOutletPos = new BlockPos(chunkOriginXBlock+45 - x/2, 3, chunkOriginZBlock+2);
			itemInputPos = new BlockPos(chunkOriginXBlock+47 -2, 3, chunkOriginZBlock+1 +z/2);
			itemOutputPos = new BlockPos(chunkOriginXBlock+47 -2 -x+1, 3, chunkOriginZBlock+1 +z/2);
			if(!isGenerated){
				doTheFactorySpace(x, y, z, chunkOriginXBlock + 47 - x - 2, chunkOriginZBlock + 1);
				doTheBreakableDoor(chunkOriginXBlock+46, chunkOriginZBlock+1);
			}
		}
		else if(this.facT == 4){
			teleporterPos = new BlockPos(chunkOriginXBlock+2, 3, chunkOriginZBlock+2);
			energyOutletPos = new BlockPos(chunkOriginXBlock+1+x/2, 3, chunkOriginZBlock+2);
			itemInputPos = new BlockPos(chunkOriginXBlock+2, 3, chunkOriginZBlock+1+z/2);
			itemOutputPos = new BlockPos(chunkOriginXBlock+2 +x-1, 3, chunkOriginZBlock+1+z/2);
			if(!isGenerated){
				doTheFactorySpace(x, y, z, chunkOriginXBlock + 1, chunkOriginZBlock + 1);
				doTheBreakableDoor(chunkOriginXBlock+1, chunkOriginZBlock+1);
			}
		}
	}
	
	private int getValueToAddX(){
		int value = 0;
		if(this.facT == 1){
			value = -1;
		} else if(this.facT == 2){
			value = 1;
		} else if(this.facT == 3){
			value = -1;
		} else if(this.facT == 4){
			value = 1;
		}
		return value;
	}
	
	private int getValueToAddZ(){
		int value = 0;
		if(this.facT == 1){
			value = -1;
		} else if(this.facT == 2){
			value = -1;
		} else if(this.facT == 3){
			value = 1;
		} else if(this.facT == 4){
			value = 1;
		}
		return value;
	}
	
	private void doTheBreakableDoor(int originX, int originZ){
		doTheBreakableDoor(originX, originZ, true);
	}
	
	private void undoTheBreakableDoor(int originX, int originZ){
		doTheBreakableDoor(originX, originZ, false);
	}
	
	private void doTheBreakableDoor(int originX, int originZ, boolean make){
		makeDoorBlock(new BlockPos(originX, 1, originZ), make);
		makeDoorBlock(new BlockPos(originX, 2, originZ), make);
		
		makeDoorBlock(new BlockPos(originX + getValueToAddX(), 1, originZ), make);
		makeDoorBlock(new BlockPos(originX + getValueToAddX(), 2, originZ), make);
		
		makeDoorBlock(new BlockPos(originX, 1, originZ + getValueToAddZ()), make);
		makeDoorBlock(new BlockPos(originX, 2, originZ + getValueToAddZ()), make);
		
		makeDoorBlock(new BlockPos(originX + getValueToAddX() + getValueToAddX(), 1, originZ), make);
		
		makeDoorBlock(new BlockPos(originX, 1, originZ + getValueToAddZ() + getValueToAddZ()), make);
	}
	
	private void makeDoorBlock(BlockPos pos, boolean make){
		if(make){
			this.world.setBlockState(pos, this.doorBlock);
		} else {
			this.world.setBlockState(pos, this.facHandler.getMaterialBlock().getDefaultState());
		}
	}
	
//	private void doTheFactoryCube(int x, int cordOX, int cordOZ){
//		doTheFactorySpace(x, x, x, cordOX, cordOZ);
//	}

//	private void setTileEntities(BlockPos telePos, BlockPos enerOutletPos, BlockPos itemBuffPos){
//		((TileEntityFactoryDoor)this.world.getTileEntity(telePos)).setReturnTeleportal(true).setController(controller);
//		if (controller.getTileEntityEnergyOutlet() != null){
//			this.world.setTileEntity(enerOutletPos, controller.getTileEntityEnergyOutlet());
//		}
//		if (controller.getTileEntityItemBuffer() != null){
//			this.world.setTileEntity(itemBuffPos, controller.getTileEntityItemBuffer());
//		}
//	}
	
	private void doTheFactorySpace(int x, int y, int z, int cordOX, int cordOZ){
		//precisa das coordenadas de origem do cubo, e precisa do tamanho dele
//		System.out.println("Factory size is x:" + x + " y:" + y + " z:" + z);
	
		
		
		TileEntityController controller = this.facHandler.getController(this.id);
		
		if(controller == null){
			throw new NullPointerException("Controller is null");
		}
			
//		System.out.printf("Making teleporter at x:%d y:%d z:%d%n", teleporterPos.getX(), teleporterPos.getY(), teleporterPos.getZ());
		this.world.setBlockState(this.teleporterPos, ModBlocks.mbFactoryDoor.getDefaultState());
		((TileEntityFactoryDoor)this.world.getTileEntity(this.teleporterPos)).setReturnTeleportal().setController(controller);
//		this.world.notifyLightSet(this.teleporterPos);
//		this.world.getChunkFromBlockCoords(this.teleporterPos).checkLight();
//		System.out.println("I set the controller in teleporter sucessfully");
		
		if(controller.hasTileEntityEnergyOutlet()){
//			System.out.printf("Making Energy outlet at x:%d y:%d z:%d%n", energyOutletPos.getX(), energyOutletPos.getY(), energyOutletPos.getZ());
			this.world.setBlockState(this.energyOutletPos, ModBlocks.energyOutletFactory.getDefaultState());
			((TileEntitySyncController)this.world.getTileEntity(this.energyOutletPos)).setController(controller);
//			System.out.println("I set the controller in Energy outlet sucessfully");
		}
		
		if(controller.hasTileEntityItemInput()){
//			System.out.printf("Making ItemInput at x:%d y:%d z:%d%n", itemInputPos.getX(), itemInputPos.getY(), itemInputPos.getZ());
			this.world.setBlockState(this.itemInputPos, ModBlocks.itemInputFactory.getDefaultState());
			((TileEntitySyncController)this.world.getTileEntity(this.itemInputPos)).setController(controller);
//			System.out.println("I set the controller in ItemInput sucessfully");
		}
		
		if(controller.hasTileEntityItemOutput()){
//			System.out.printf("Making ItemOutput at x:%d y:%d z:%d%n", itemOutputPos.getX(), itemOutputPos.getY(), itemOutputPos.getZ());
			this.world.setBlockState(this.itemOutputPos, ModBlocks.itemOutputFactory.getDefaultState());
			((TileEntitySyncController)this.world.getTileEntity(this.itemOutputPos)).setController(controller);
//			System.out.println("I set the controller in ItemOutput sucessfully");
		}
		
		for(int k = 1; k < y+2; k++){
			for(int i = 0; i < x+2; i++){
				this.world.setBlockState(new BlockPos(i + cordOX, k, cordOZ + z + 1), facHandler.getMaterialBlock().getDefaultState());
				this.world.setBlockState(new BlockPos(i + cordOX, k, cordOZ), facHandler.getMaterialBlock().getDefaultState());
				
			}
			for(int j = 0; j < z+2; j++){
				this.world.setBlockState(new BlockPos(cordOX + x + 1, k, j + cordOZ), facHandler.getMaterialBlock().getDefaultState());
				this.world.setBlockState(new BlockPos(cordOX, k, j + cordOZ), facHandler.getMaterialBlock().getDefaultState());
			}
		}	
		
		for(int i = 0; i < x+2; i++){
			for(int j = 0; j < z+2; j++){
				this.world.setBlockState(new BlockPos(i + cordOX, y + 1, j + cordOZ), facHandler.getMaterialBlock().getDefaultState());
			}
		}
	}
	
	public void destroySelf(){
		this.world = this.facHandler.getWorld();
		int x = factorySize.getX();
		int y = factorySize.getY();
		int z = factorySize.getZ();
		int chunkOriginXBlock = factoryBigChunk.getXCord();
		int chunkOriginZBlock = factoryBigChunk.getZCord();
		
		if(x == 0 && y == 0 && z == 0){
			return;
		}
		if(this.facT == 1){
			destroyTheFactorySpace(x, y, z, chunkOriginXBlock + 47 - x - 2, chunkOriginZBlock + 47 - x - 2);
			undoTheBreakableDoor(chunkOriginXBlock+46, chunkOriginZBlock+46);
		}
		else if(this.facT == 2){
			destroyTheFactorySpace(x, y, z, chunkOriginXBlock + 1, chunkOriginZBlock + 47 - z - 2);
			undoTheBreakableDoor(chunkOriginXBlock+1, chunkOriginZBlock+46);
		}
		else if(this.facT == 3){
			destroyTheFactorySpace(x, y, z, chunkOriginXBlock + 47 - x - 2, chunkOriginZBlock + 1);
			undoTheBreakableDoor(chunkOriginXBlock+46, chunkOriginZBlock+1);
		}
		else if(this.facT == 4){
			destroyTheFactorySpace(x, y, z, chunkOriginXBlock+1, chunkOriginZBlock+1);
			undoTheBreakableDoor(chunkOriginXBlock+1, chunkOriginZBlock+1);
		}
		
		this.world.setBlockToAir(this.teleporterPos);
		this.world.setBlockToAir(this.energyOutletPos);
		this.world.setBlockToAir(this.itemInputPos);
		this.world.setBlockToAir(this.itemOutputPos);
		
		this.factorySize = new FactorySize(0);
	}

	private void destroyTheFactorySpace(int x, int y, int z, int cordOX, int cordOZ) {
		IBlockState air = Blocks.AIR.getDefaultState();
		for(int k = 1; k < y+2; k++){
			if(z != 45){
				for(int i = 0; i < x+2; i++){
					if(facT == 3){
						if(i==x+1)continue;
						this.world.setBlockState(new BlockPos(i + cordOX, k, cordOZ + z + 1), air);
					}
					else if(facT == 1){
						if(i==x+1)continue;
						this.world.setBlockState(new BlockPos(i + cordOX, k, cordOZ), air);
					}
					else if(facT == 2){
						if(i==0)continue;
						this.world.setBlockState(new BlockPos(i + cordOX, k, cordOZ), air);
					}
					else if(facT == 4){
						if(i==0)continue;
						this.world.setBlockState(new BlockPos(i + cordOX, k, cordOZ + z + 1), air);
					}
				}
			}
			if(x != 45){
				for(int j = 0; j < z+2; j++){
					if(facT == 2){
						if(j==z+1)continue;
						this.world.setBlockState(new BlockPos(cordOX + x + 1, k, j + cordOZ), air);
					}
					else if(facT == 4){
						if(j==0)continue;
						this.world.setBlockState(new BlockPos(cordOX + x + 1, k, j + cordOZ), air);
					}
					else if(facT == 1){
						if(j==z+1)continue;
						this.world.setBlockState(new BlockPos(cordOX, k, j + cordOZ), air);
					}
					else if(facT == 3){
						if(j==0)continue;
						this.world.setBlockState(new BlockPos(cordOX, k, j + cordOZ), air);
					}
				}
			}	
		}	
		
		for(int i = 1; i < x+1; i++){
			for(int j = 1; j < z+1; j++){
				if(y!=45){
				this.world.setBlockState(new BlockPos(i + cordOX, y + 1, j + cordOZ), air);
				}
			}
		}
	}
	
	private boolean checkIfCanGenerate() {
		this.world = this.facHandler.getWorld();
		int x = factorySize.getX();
		int y = factorySize.getY();
		int z = factorySize.getZ();
		int chunkOriginXBlock = factoryBigChunk.getXCord();
		int chunkOriginZBlock = factoryBigChunk.getZCord();

		if(this.facT == 1){
			if(!checkTheFactorySpace(x, y, z, chunkOriginXBlock + 47 - x - 2, chunkOriginZBlock + 47 - x - 2))
				return false;
		}
		else if(this.facT == 2){
			if(!checkTheFactorySpace(x, y, z, chunkOriginXBlock + 1, chunkOriginZBlock + 47 - z - 2))
				return false;
		}
		else if(this.facT == 3){
			if(!checkTheFactorySpace(x, y, z, chunkOriginXBlock + 47 - x - 2, chunkOriginZBlock + 1))
				return false;
		}
		else if(this.facT == 4){
			if(!checkTheFactorySpace(x, y, z, chunkOriginXBlock + 1, chunkOriginZBlock + 1))
				return false;
		}
		return true;
	}
	
	

	private boolean checkTheFactorySpace(int x, int y, int z, int cordOX, int cordOZ){
		//precisa das coordenadas de origem do cubo, e precisa do tamanho dele
//		System.out.println("Factory size is x:" + x + " y:" + y + " z:" + z);
		
		for(int k = 1; k < y+2; k++){
			for(int i = 0; i < x+2; i++){
				if(!this.isFactoryPart(new BlockPos(i + cordOX, k, cordOZ + z + 1)))
						return false;
				if(!this.isFactoryPart(new BlockPos(i + cordOX, k, cordOZ)))
					return false;
			}
			for(int j = 0; j < z+2; j++){
				if(!this.isFactoryPart(new BlockPos(cordOX + x + 1, k, j + cordOZ)))
					return false;
				if(!this.isFactoryPart(new BlockPos(cordOX, k, j + cordOZ)))
					return false;
			}
		}	
		
		for(int i = 0; i < x+2; i++){
			for(int j = 0; j < z+2; j++){
				if(!this.isFactoryPart(new BlockPos(i + cordOX, y + 1, j + cordOZ)))
					return false;
			}
		}
		return true;
	}

	private boolean isFactoryPart(BlockPos blockPos) {
		return (this.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK 
				|| this.world.getBlockState(blockPos).getBlock() instanceof BlockFactoryPart);
	}

	private boolean sameDim(FactorySize facS) {
		return factorySize.same(facS);
	}

	public boolean adjustDim(FactorySize facS) {
		destroySelf();
		this.factorySize = facS;
		if(!checkIfCanGenerate())
			return false;
		generateSelf(false);
		return true;
	}

	public void checkDimAndAdjust(FactorySize facS) {
		if(!this.sameDim(facS)){
			adjustDim(facS);
		}	
	}

	public BlockPos getTpPlace() {
//		if(this.facT == 1){
//			return teleporterPos.add(-1, -2, -1);
//		}
//		else if(this.facT == 2){
//			return teleporterPos.add(1, -2, -1);
//		}
//		else if(this.facT == 3){
//			return teleporterPos.add(-1, -2, 1);
//		}
//		else if(this.facT == 4){
//			return teleporterPos.add(1, -2, 1);
//		}
//		return null;
		return teleporterPos.add(0, -2, 0);
	}
	
	private int calculateFacType(int x, int z) {
		int facType = 0;
		int x3 = x;
        int z3 = z;
        if(x<0){
        	x3 += 1;
        }
        if(z<0){
        	z3 += 1;
        }
        
        int intToDeductfacTypeX = ((x3 / 3) % 2);
        int intToDeductfacTypeZ = ((z3 / 3) % 2);
        boolean isfacTypeEven = false;
        if(intToDeductfacTypeX == 0){
        	if(x >= 0){
        		//type of box is 2 or 4
        		isfacTypeEven = true;
        	}
        	else{
        		//type of box is 1 or 3
        		isfacTypeEven = false;
        	}
        }
        else if(intToDeductfacTypeX == 1){
        	//type of box is 1 or 3
        	isfacTypeEven = false;
        }
        else if(intToDeductfacTypeX == -1){
        	//type of box is 2 or 4
        	isfacTypeEven = true;
        }
        else{
        	System.out.println("This is not good, X factory boxes not happy");
        }
        
        if(intToDeductfacTypeZ == 0){
        	if(z >= 0){
        		//type of box is 3 or 4
        		facType = (isfacTypeEven?4:3);
        	}
        	else{
        		//type of box is 1 or 2
        		facType = (isfacTypeEven?2:1);
        	}
        }
        else if(intToDeductfacTypeZ == -1){
        	//type of box is 3 or 4
        	facType = (isfacTypeEven?4:3);
        }
        else if(intToDeductfacTypeZ == 1){
        	//type of box is 1 or 2
        	facType = (isfacTypeEven?2:1);
        }
        else{
        	System.out.println("This is not good, Z factory boxes not happy");
        }
		return facType;
	}
	
	public boolean loadChunks(boolean load) {
//		System.out.println("Load Chunks: " + load);
		Ticket ticket = TicketHolder.getTicket(this.facHandler.getFactoryLvl(), this.id);
		if(ticket == null){
//			System.out.println("New ticket");
			ticket = ForgeChunkManager.requestTicket(CompressedFactory.instance, 
					this.facHandler.getWorld(), 
					ForgeChunkManager.Type.NORMAL);
			ticket.getModData().setBoolean("stillUsing", false);
			TicketHolder.addTicket(this.facHandler.getFactoryLvl(), this.id, ticket);
		}
		int x = this.factoryBigChunk.getX();
		int z = this.factoryBigChunk.getZ();
//		System.out.println("Factory Lvl: " + this.facHandler.getFactoryLvl());
//		System.out.println("Before choice, stillUsing:" + ticket.getModData().getBoolean("stillUsing"));
		if(load && !ticket.getModData().getBoolean("stillUsing")){
//			System.out.println("Going to force chunks");
			for(int i = x; i < x+3; i++){
				for(int j = z; j < z+3; j++){
//					System.out.println("forcing chunk - x: " + i + ", z: " + j);
					ForgeChunkManager.forceChunk(ticket, new ChunkPos(i,j));
				}
			}
			ticket.getModData().setBoolean("stillUsing", true);
			ticket.getModData().setInteger("chunkPosX", x);
			ticket.getModData().setInteger("chunkPosZ", z);
			ticket.getModData().setInteger("facLvl", this.facHandler.getFactoryLvl());
			ticket.getModData().setInteger("id", this.id);
			return true;
			
		} else if (!load && ticket.getModData().getBoolean("stillUsing")){
//			System.out.println("Going to unforce chunks");
			for(int i = x; i < x+3; i++){
				for(int j = z; j < z+3; j++){
					ForgeChunkManager.unforceChunk(ticket, new ChunkPos(i,j));
				}
			}
			ticket.getModData().setBoolean("stillUsing", false);
//			System.out.println("Finished unforce chunks, stillUsing:" + ticket.getModData().getBoolean("stillUsing"));
			return true;
		}
//		System.out.println("Releasing ticket");
//		ForgeChunkManager.releaseTicket(ticket);
		return true;
	}

	public void readFromNBT(NBTTagCompound facCompound) {
		
		this.factorySize = new FactorySize(facCompound.getInteger("sizeX"), facCompound.getInteger("sizeY"), facCompound.getInteger("sizeZ"));
		this.generateSelf(true);
		
//		if(this.id != facCompound.getInteger("idFac")) {
//			This is wrong 
			//System.out.println("[ERROR] Problem with nbts and ids, change so id is stored here and this puts itself on handler");
//		}
	}

	public void writeToNBT(NBTTagCompound facCompound) {
		// i want to get the size, for now this works
		int sizeX = this.factorySize.getX();
		int sizeY = this.factorySize.getY();
		int sizeZ = this.factorySize.getZ();
		
		facCompound.setInteger("sizeX", sizeX);
		facCompound.setInteger("sizeY", sizeY);
		facCompound.setInteger("sizeZ", sizeZ);
		
//		facCompound.setInteger("idFac", this.id);
	}
	
	public int getId(){
		return this.id;
	}
}
