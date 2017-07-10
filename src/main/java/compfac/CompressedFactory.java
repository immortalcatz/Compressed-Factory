package compfac;

import java.io.File;

import compfac.blocks.tileentities.TileEntityItemBuffer;
import compfac.init.ModBlocks;
import compfac.init.ModCrafting;
import compfac.init.ModItems;
import compfac.init.TileEntityReg;
import compfac.proxy.CommonProxy;
import compfac.world.ChunkLoaderHandler;
import compfac.world.dimension.DimensionRegistry;
import compfac.world.dimension.FactoryHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.SidedProxy;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class CompressedFactory {
	
	@Instance
	public static CompressedFactory instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	private static File configDir;
	
	public static File getConfigDir(){
		return configDir;
	}

	//can make a loop that makes an array of handlers for custom quantity of levels
	public static final CreativeTabs FACTORY_TAB = new FactoryTab();
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		System.out.println("CompFac - Pre Init");
		
		configDir = new File(event.getModConfigurationDirectory() + "/" + Reference.MOD_ID);
		configDir.mkdirs();
		ConfigHandler.init(new File(configDir.getPath(), Reference.MOD_ID + ".cfg"));
		
		ForgeChunkManager.setForcedChunkLoadingCallback(CompressedFactory.instance, new ChunkLoaderHandler());
		
		ModItems.init();
		ModBlocks.init();
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		System.out.println("CompFac - Init");
		
		proxy.init();
		ModCrafting.register();
		
		TileEntityReg.doEntitiesRegistration();
		DimensionRegistry.MainRegistry();
		
//		l1FacHandler = new FactoryHandler(1, "FactoryHandlerL1");
//		l2FacHandler = new FactoryHandler(2, "FactoryHandlerL2");
//		l3FacHandler = new FactoryHandler(3, "FactoryHandlerL3");
		
//		l1FacHandler = FactoryHandler.getHandler(1, "FactoryHandlerL1");
//		l2FacHandler = FactoryHandler.getHandler(2, "FactoryHandlerL2");
//		l3FacHandler = FactoryHandler.getHandler(3, "FactoryHandlerL3");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		System.out.println("CompFac - Post Init");
//		MinecraftForge.EVENT_BUS.register(new CompFacEventHandler());
//		Blocks.STONE.setHardness(1.5F); // This is here temporarily cause goof
	}
	
	@EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
//		event.registerServerCommand(new DimensionTp());
//		event.registerServerCommand(new MakeCompFac());
//		event.registerServerCommand(new DestroyCompFac());
	}
}
