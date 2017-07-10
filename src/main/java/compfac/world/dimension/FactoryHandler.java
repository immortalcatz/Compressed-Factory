package compfac.world.dimension;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import compfac.CompressedFactory;
import compfac.Reference;
import compfac.blocks.tileentities.TileEntityController;
import compfac.helper.ControllerHelper;
import compfac.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class FactoryHandler extends WorldSavedData{
	
//	private static final String DATA_NAME = Reference.MOD_ID + ":facSavedData";
	
	private int nrFactories = 0;
	private int factoryLvl;
	private ConcurrentHashMap<Integer, TheCompressedFactory> activeFactories = new ConcurrentHashMap<Integer, TheCompressedFactory>();
	private ConcurrentHashMap<Integer, ControllerHelper> controllers = new ConcurrentHashMap<Integer, ControllerHelper>();
	
	public FactoryHandler(String s) {
		super(s);
	}
	public FactoryHandler(int lvl, String name) {
		this(name);
		factoryLvl = lvl;
	}
	
	public TheCompressedFactory getFactory(int id){
		return this.activeFactories.get(id);
	}
	
	public int getFactoryLvl(){
		return this.factoryLvl;
	}
	
	public void generateFactoryCube(int id, int x){
		this.generateFactoryWithId(id, new FactorySize(x), null);
	}
	
	public boolean chunkLoadFactory(boolean load, int id) {
		if(!this.activeFactories.containsKey(id))
			return false;
		this.activeFactories.get(id).loadChunks(load);
		return true;
	}
	
	public boolean destroyFactory(int id){
		if(activeFactories.containsKey(id)){
			TheCompressedFactory f = activeFactories.get(id);
			f.destroySelf();
			activeFactories.remove(id);
			this.nrFactories--;
			this.markDirty();
			return true;
		}
		return false;
	}
	
	public int getNumberFactories(){
		return this.nrFactories;
	}
	
	public World getWorld(){
		return getWorld(this.factoryLvl);
	}
	
	public static WorldServer worldServerForDimension(int dimension) {
        WorldServer ret = DimensionManager.getWorld(dimension);
        if (ret == null) {
            DimensionManager.initDimension(dimension);
            ret = DimensionManager.getWorld(dimension);
        }
        return ret;
    }
	
	public static World getWorld(int lvl){
		// this will only work after the dimension is loaded, so after the game has started
		World world = null;
		switch(lvl){
		case 1:
			world = worldServerForDimension(Reference.l1FactoryDimensionID);
			break;
		case 2:
			world = worldServerForDimension(Reference.l2FactoryDimensionID);
			break;
		case 3:
			world = worldServerForDimension(Reference.l3FactoryDimensionID);
			break;
		}
		if(world == null){
			throw new NullPointerException("World is null");
		}
		return world;
	}
	
	/**
	 * generates a factory with the lowest id possible
	 * @param facS
	 * @return the id of the factory
	 */
	public int generateFactory(FactorySize facS, TileEntityController controller){
		int x = 0;
		while(activeFactories.containsKey(x)) {
			x++;
//			System.out.println("Already exists id: " + x);
		}
		generateFactoryWithId(x, facS, controller);
		return x;
	}
	
	public boolean generateFactoryWithId(int id, FactorySize facS, TileEntityController controller){
		controller.setId(id);
		this.addController(id, controller);
		if(activeFactories.containsKey(id)){
			TheCompressedFactory f = activeFactories.get(id);
//			f.checkDimAndAdjust(facS);
			boolean noBlocksInTheWay = f.adjustDim(facS);
			this.markDirty();
			return noBlocksInTheWay;
		}
		TheCompressedFactory fac = new TheCompressedFactory(id, facS, this, false);
		activeFactories.put(id, fac);
		this.nrFactories++;
		this.markDirty();
		return true;
	}
	
	public BlockPos getPlaceToTp(int id){
		if(activeFactories.containsKey(id)){
			TheCompressedFactory fac = activeFactories.get(id);
			return fac.getTpPlace();
		} else {
			System.out.println("Could not get the place to tp(no factory made)");
		}
		return null;
	}
	
	public Block getMaterialBlock(){
    	if(this.factoryLvl == 1){
    		return ModBlocks.blockStoneUmbreakable;
    	}
    	else if(this.factoryLvl == 2){
    		return ModBlocks.blockStrongStoneUmbreakable;
    	}
    	else if(this.factoryLvl == 3){
    		return Blocks.BEDROCK;
    	}
    	return null;
    }
	
	public static FactoryHandler getHandler(int factoryLvl) {
		World world = getWorld(factoryLvl);
		String dataName = "FactoryHandlerL" + factoryLvl;
		MapStorage storage = world.getPerWorldStorage();
		FactoryHandler instance = (FactoryHandler) storage.getOrLoadData(FactoryHandler.class, dataName);
		if (instance == null) {
			instance = new FactoryHandler(factoryLvl, dataName);
			storage.setData(dataName, instance);
		}
		return instance;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.factoryLvl = compound.getInteger("factoryLvl");
		
		NBTTagList aFactories = compound.getTagList(("AFactories" + this.factoryLvl), 10);
	    for (int i = 0; i < aFactories.tagCount(); i++){
	      NBTTagCompound facCompound = aFactories.getCompoundTagAt(i);
	      if(i != this.nrFactories) {
	    	  //TODO this is here prob for no reason
	    	  throw new NullPointerException("forget the null part, ids are probably wrong");
	      }
	      int facId = facCompound.getInteger("facId");
	      TheCompressedFactory fac = new TheCompressedFactory(facId, new FactorySize(0),this, true);
	      fac.readFromNBT(facCompound);
	      this.activeFactories.put(facId,fac);
	      this.nrFactories++;
	    }
	    
	    NBTTagList controllerList = compound.getTagList(("controllerList" + this.factoryLvl), 10);
	    for (int i = 0; i < controllerList.tagCount(); i++){
	      NBTTagCompound contCompound = controllerList.getCompoundTagAt(i);
	      this.controllers.put(i, new ControllerHelper(contCompound));
	    }
	    
//	    System.out.println("Read factories from nbt");
//	    getFacsInfo();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("factoryLvl", this.factoryLvl);
		
		NBTTagList aFactories = new NBTTagList();
	    for(TheCompressedFactory fac : activeFactories.values()){
//	    	if(!this.activeFactories.containsKey(x))
//	    		System.out.println("Factory Handler NBT writing error");
//	    	TheCompressedFactory fac = this.activeFactories.get(x);
	    	NBTTagCompound facCompound = new NBTTagCompound();
	    	fac.writeToNBT(facCompound);
	    	facCompound.setInteger("facId", fac.getId());
	    	aFactories.appendTag(facCompound);
	    }
	    compound.setTag(("AFactories" + this.factoryLvl), aFactories);
	    
	    NBTTagList controllerList = new NBTTagList();
	    for(int x = 0; x < this.controllers.size(); x++){
	    	if(!this.controllers.containsKey(x))
	    		System.out.println("Factory Handler NBT writing error");
	    	ControllerHelper contrHelper = this.controllers.get(x);
	    	NBTTagCompound contCompound = new NBTTagCompound();
	    	contrHelper.writeToNBT(contCompound);
	    	controllerList.appendTag(contCompound);
	    }
	    compound.setTag(("controllerList" + this.factoryLvl), controllerList);
//	    System.out.println("Wrote factories to nbt");
	    return compound;
	}
	
	private void getFacsInfo() {
		for(TheCompressedFactory facs : this.activeFactories.values()){
			System.out.println("Read Factory with id " + facs.getInfo());
		}
		System.out.println("Number of Factories:  " + this.getNumberFactories());
	}
	
	public static void writeBlockPosToNBT(NBTTagCompound compound, String name, BlockPos pos) {
		compound.setInteger(name+"x", pos.getX());
		compound.setInteger(name+"y", pos.getY());
		compound.setInteger(name+"z", pos.getZ());
	}
	
	/**
	 * 
	 * @return the BlockPos or null if block does not exist
	 */
	public static BlockPos readBlockPosFromNBT(NBTTagCompound compound, String name) {
		if(!(compound.hasKey(name+"x") || compound.hasKey(name+"y") || compound.hasKey(name+"z")))
			return null;
		return new BlockPos(compound.getInteger(name+"x"), compound.getInteger(name+"y"), compound.getInteger(name+"z"));
	}

	public void addController(int id, TileEntityController tileEntityController) {
//		System.out.println("Controller is gonna be added with the id:" + id);
		this.controllers.put(id, new ControllerHelper(tileEntityController));
		this.markDirty();
	}

	public TileEntityController getController(int id) {
		if(!this.controllers.containsKey(id)){
			System.out.println("There is no controller in the map with the id:" + id);
			return null;
		}
		return this.controllers.get(id).getController();
	}
	
	public static void messagePlayer(EntityPlayer player, String msg, int lvl) {
		if(lvl > Reference.chatMessagesLvl)
			return;
		player.addChatMessage(new TextComponentString(msg));
	}
}
