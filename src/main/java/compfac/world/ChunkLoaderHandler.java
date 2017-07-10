package compfac.world;

import java.util.List;

import compfac.helper.TicketHolder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class ChunkLoaderHandler implements LoadingCallback{

	@Override
	public void ticketsLoaded(List<Ticket> tickets, World world) {
//		System.out.println("Re-forcing the chunks on load");
		int c = 0;
		for(Ticket tic : tickets){
			NBTTagCompound data = tic.getModData();
//			System.out.println("Ticket " + ++c + " stillUsing: " + data.getBoolean("stillUsing"));
			int facLvl = data.getInteger("facLvl");
			int id = data.getInteger("id");
			if(data.getBoolean("stillUsing")){
//				System.out.println("Am re-forcing chunk");
				int x = data.getInteger("chunkPosX");
				int z = data.getInteger("chunkPosZ");
//				System.out.println("Factory Lvl: " + facLvl);
				for(int i = x; i < x+3; i++){
					for(int j = z; j < z+3; j++){
//						System.out.println("Am re-forcing this chunk on load - x: " + i + ", z: " + j);
						ForgeChunkManager.forceChunk(tic, new ChunkPos(i,j));
					}
				}
				TicketHolder.addTicket(facLvl, id, tic);
			} else {
//				System.out.println("Releasing ticket " + c);
				TicketHolder.releaseTicket(facLvl, id); //i dont think this is needed, but it seems kinda persistent
															// although it cant, i hope, :/ TODO test if this is needed
				ForgeChunkManager.releaseTicket(tic);
			}
		}	
	}

}
