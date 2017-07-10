package compfac.init;

import compfac.blocks.BlockCasing;
import compfac.blocks.BlockController;
import compfac.blocks.BlockEnergyOutlet;
import compfac.blocks.BlockEnergyOutletFactory;
import compfac.blocks.BlockFactoryDoor;
import compfac.blocks.BlockGlowstoneUmbreakable;
import compfac.blocks.BlockItemInput;
import compfac.blocks.BlockItemInputFactory;
import compfac.blocks.BlockItemOutput;
import compfac.blocks.BlockItemOutputFactory;
import compfac.blocks.BlockStoneDoorUmbreakable;
import compfac.blocks.BlockStoneUmbreakable;
import compfac.blocks.BlockStrongStoneUmbreakable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	
	//blocks
	
	//mb
	public static Block mbCasing;
	public static Block mbController;
	public static Block mbEnergyOutlet;
	public static Block mbItemInput;
	public static Block mbItemOutput;
	public static Block mbFactoryDoor;
	
	//copies
	public static Block energyOutletFactory;
	public static Block itemInputFactory;
	public static Block itemOutputFactory;
	
	//Dimension Materials
	public static Block blockStoneUmbreakable;
	public static Block blockGlowstoneUmbreakable;
	public static Block blockStrongStoneUmbreakable;
	public static Block blockStoneDoorUmbreakable;
	
	public static void init(){
		initBlocks();
		register();
	}
	
	//methods to add the blocks
	private static void initBlocks(){
		
		//Factory Multiblock blocks
		mbCasing = new BlockCasing();
		mbController = new BlockController();
		mbEnergyOutlet = new BlockEnergyOutlet();
		mbItemInput = new BlockItemInput();
		mbItemOutput = new BlockItemOutput();
		mbFactoryDoor = new BlockFactoryDoor();
		
		energyOutletFactory = new BlockEnergyOutletFactory();
		itemInputFactory = new BlockItemInputFactory();
		itemOutputFactory = new BlockItemOutputFactory();
		
		//Dimension materials
		blockStoneUmbreakable = new BlockStoneUmbreakable();
		blockGlowstoneUmbreakable = new BlockGlowstoneUmbreakable();
		blockStrongStoneUmbreakable = new BlockStrongStoneUmbreakable();
		blockStoneDoorUmbreakable = new BlockStoneDoorUmbreakable();
		
	}
	
	private static void register(){
		
		registerBlock(mbCasing);
		registerBlock(mbController);
		registerBlock(mbEnergyOutlet);
		registerBlock(mbItemInput);
		registerBlock(mbItemOutput);
		registerBlock(mbFactoryDoor);
		
		registerBlock(energyOutletFactory);
		registerBlock(itemInputFactory);
		registerBlock(itemOutputFactory);
		
		registerBlock(blockStoneUmbreakable);
		registerBlock(blockGlowstoneUmbreakable);
		registerBlock(blockStrongStoneUmbreakable);
		registerBlock(blockStoneDoorUmbreakable);
	}
	
	public static void registerRenders(){

		registerRender(mbCasing);
		registerRender(mbController);
		registerRender(mbEnergyOutlet);
		registerRender(mbItemInput);
		registerRender(mbItemOutput);
		registerRender(mbFactoryDoor);
		
		registerRender(energyOutletFactory);
		registerRender(itemInputFactory);
		registerRender(itemOutputFactory);
		
		registerRender(blockStoneUmbreakable);
		registerRender(blockGlowstoneUmbreakable);
		registerRender(blockStrongStoneUmbreakable);
		registerRender(blockStoneDoorUmbreakable);
	}
	
	//methods to use
	private static void registerBlock(Block block){
		GameRegistry.register(block);
		ItemBlock item = new ItemBlock(block);
		item.setRegistryName(block.getRegistryName());
		GameRegistry.register(item);
	}
	
	private static void registerRender(Block block){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
}