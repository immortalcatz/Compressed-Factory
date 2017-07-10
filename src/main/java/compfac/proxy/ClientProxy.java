package compfac.proxy;

import compfac.blocks.tileentities.TileEntityItemBuffer;
import compfac.init.ModBlocks;
import compfac.init.ModItems;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy implements CommonProxy{

	@Override
	public void init() {
		ModItems.registerRenders();
		ModBlocks.registerRenders();
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityJar.class, new RendererJar());
//		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityItemBuffer.class, new RendererItemBuffer());
	}
}
