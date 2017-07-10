package compfac.world.dimension;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import compfac.Reference;
import compfac.init.ModBlocks;

import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenScatteredFeature;
import net.minecraft.world.gen.structure.MapGenStronghold;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraft.world.gen.structure.StructureOceanMonument;
import net.minecraftforge.event.ForgeEventFactory;

public class FactoryChunkProvider implements IChunkGenerator
{
	private final int factoryLvl;
    private final World worldObj;
    private final Random random;
    private final IBlockState[] cachedBlockIDs = new IBlockState[256];
    private final FlatGeneratorInfo flatWorldGenInfo;
    private Block umbreakableStone = ModBlocks.blockStoneUmbreakable;
    private Block umbreakableObsdian = ModBlocks.blockStrongStoneUmbreakable;
    private Block umbreakableGlowstone = ModBlocks.blockGlowstoneUmbreakable;
    private Block bedRock = Blocks.BEDROCK;
    
    public FactoryChunkProvider(int factoryLvl, World worldIn, long seed)
    {
    	this.factoryLvl = factoryLvl;
        this.worldObj = worldIn;
        this.random = new Random(seed);
        
        FlatGeneratorInfo flatgeneratorinfo = new FlatGeneratorInfo();
        flatgeneratorinfo.setBiome(Biome.getIdForBiome(Biomes.VOID));
        flatgeneratorinfo.getFlatLayers().add(new FlatLayerInfo(1, this.getMaterialBlock()));
        flatgeneratorinfo.getFlatLayers().add(new FlatLayerInfo(Reference.maxFactoryDimension, Blocks.AIR));
        flatgeneratorinfo.getFlatLayers().add(new FlatLayerInfo(1, getMaterialBlock()));
        flatgeneratorinfo.updateLayers();
        
        this.flatWorldGenInfo = flatgeneratorinfo;

        for (FlatLayerInfo flatlayerinfo : this.flatWorldGenInfo.getFlatLayers())
        {
            for (int i = flatlayerinfo.getMinY(); i < flatlayerinfo.getMinY() + flatlayerinfo.getLayerCount(); ++i)
            {
                IBlockState iblockstate = flatlayerinfo.getLayerMaterial();

//                if (iblockstate.getBlock() != Blocks.AIR){
                    this.cachedBlockIDs[i] = iblockstate;
//                }
            }
        }

        worldIn.setSeaLevel(0);
    }
    
    private IBlockState getMaterialBlockState(){
    	return this.getMaterialBlock().getDefaultState();
    }
    
    private Block getMaterialBlock(){
    	if(this.factoryLvl == 1){
    		return this.umbreakableStone;
    	}
    	else if(this.factoryLvl == 2){
    		return this.umbreakableObsdian;
    	}
    	else if(this.factoryLvl == 3){
    		return this.bedRock;
    	}
    	return null;
    }

    public Chunk provideChunk(int x, int z)
    {
        ChunkPrimer chunkprimer = new ChunkPrimer();
        	
        int typeOfBox = 0;
        int typeOfChunk = 0;
        
        int x3 = x;
        int z3 = z;
        if(x<0){
        	x3 += 1;
        }
        if(z<0){
        	z3 += 1;
        }
        
        int intToDeductTypeOfBoxX = ((x3 / 3) % 2);
        int intToDeductTypeOfBoxZ = ((z3 / 3) % 2);
        boolean isTypeOfBoxEven = false;
        if(intToDeductTypeOfBoxX == 0){
        	if(x >= 0){
        		//type of box is 2 or 4
        		isTypeOfBoxEven = true;
        	}
        	else{
        		//type of box is 1 or 3
        		isTypeOfBoxEven = false;
        	}
        }
        else if(intToDeductTypeOfBoxX == 1){
        	//type of box is 1 or 3
        	isTypeOfBoxEven = false;
        }
        else if(intToDeductTypeOfBoxX == -1){
        	//type of box is 2 or 4
        	isTypeOfBoxEven = true;
        }
        else{
        	System.out.println("This is not good, X factory boxes not happy");
        }
        
        if(intToDeductTypeOfBoxZ == 0){
        	if(z >= 0){
        		//type of box is 3 or 4
        		typeOfBox = (isTypeOfBoxEven?4:3);
        	}
        	else{
        		//type of box is 1 or 2
        		typeOfBox = (isTypeOfBoxEven?2:1);
        	}
        }
        else if(intToDeductTypeOfBoxZ == -1){
        	//type of box is 3 or 4
        	typeOfBox = (isTypeOfBoxEven?4:3);
        }
        else if(intToDeductTypeOfBoxZ == 1){
        	//type of box is 1 or 2
        	typeOfBox = (isTypeOfBoxEven?2:1);
        }
        else{
        	System.out.println("This is not good, Z factory boxes not happy");
        }
        int x2 = x;
        int z2 = z;
        if(x<0){
        	x2 = x * -1;
        	x2 -= 1;
        }
        if(z<0){
        	z2 = z * -1;
        	z2 -= 1;
        }
        if((z2 % 3) == 0){
        	if((x2 % 3) == 0){
        		typeOfChunk = 1;
        	}
        	else if((x2 % 3) == 1){
        		typeOfChunk = 2;
        	}
        	else if((x2 % 3) == 2){
        		typeOfChunk = 3;
        	}
        }
        else if((z2 % 3) == 1){
        	if((x2 % 3) == 0){
        		typeOfChunk = 4;
        	}
        	else if((x2 % 3) == 1){
        		typeOfChunk = 5;
        	}
        	else if((x2 % 3) == 2){
        		typeOfChunk = 6;
        	}	        	
        }
        else if((z2 % 3) == 2){
        	if((x2 % 3) == 0){
        		typeOfChunk = 7;
        	}
        	else if((x2 % 3) == 1){
        		typeOfChunk = 8;
        	}
        	else if((x2 % 3) == 2){
        		typeOfChunk = 9;
        	}	        	
        } 
        if(x<0){
            if((typeOfChunk % 3) == 1){
                typeOfChunk += 2;
            }
            else if((typeOfChunk % 3) == 0){
                typeOfChunk -= 2;
            }
        }
        if(z<0){
            if(typeOfChunk <= 3){
                typeOfChunk += 6;
            }
            else if(typeOfChunk >= 7){
                typeOfChunk -= 6;
            }
        }
        
        int typeOfBoxPlusChunk = 0;
        if(typeOfChunk == 1){
        	if(typeOfBox == 1){
        		typeOfBoxPlusChunk = 2;
            }
            else if(typeOfBox == 2){
            	typeOfBoxPlusChunk = 15;
            }
    		else if(typeOfBox == 3){
    			typeOfBoxPlusChunk = 11;
    		}
    		else if(typeOfBox == 4){
    			typeOfBoxPlusChunk = 9;
    		}
        }
        else if(typeOfChunk == 2){
        	if(typeOfBox == 1){
        		typeOfBoxPlusChunk = 19;
            }
            else if(typeOfBox == 2){
            	typeOfBoxPlusChunk = 19;
            }
    		else if(typeOfBox == 3){
    			typeOfBoxPlusChunk = 23;
    		}
    		else if(typeOfBox == 4){
    			typeOfBoxPlusChunk = 23;
    		}
        }
		else if(typeOfChunk == 3){
			if(typeOfBox == 1){
        		typeOfBoxPlusChunk = 14;
            }
            else if(typeOfBox == 2){
            	typeOfBoxPlusChunk = 3;
            }
    		else if(typeOfBox == 3){
    			typeOfBoxPlusChunk = 8;
    		}
    		else if(typeOfBox == 4){
    			typeOfBoxPlusChunk = 13;
    		}
		}
		else if(typeOfChunk == 4){
			if(typeOfBox == 1){
        		typeOfBoxPlusChunk = 18;
            }
            else if(typeOfBox == 2){
            	typeOfBoxPlusChunk = 25;
            }
    		else if(typeOfBox == 3){
    			typeOfBoxPlusChunk = 18;
    		}
    		else if(typeOfBox == 4){
    			typeOfBoxPlusChunk = 25;
    		}
		}
		else if(typeOfChunk == 5){
			typeOfBoxPlusChunk = 1;
		}
		else if(typeOfChunk == 6){
			if(typeOfBox == 1){
        		typeOfBoxPlusChunk = 24;
            }
            else if(typeOfBox == 2){
            	typeOfBoxPlusChunk = 20;
            }
    		else if(typeOfBox == 3){
    			typeOfBoxPlusChunk = 24;
    		}
    		else if(typeOfBox == 4){
    			typeOfBoxPlusChunk = 20;
    		}
		}
		else if(typeOfChunk == 7){
			if(typeOfBox == 1){
        		typeOfBoxPlusChunk = 10;
            }
            else if(typeOfBox == 2){
            	typeOfBoxPlusChunk = 7;
            }
    		else if(typeOfBox == 3){
    			typeOfBoxPlusChunk = 4;
    		}
    		else if(typeOfBox == 4){
    			typeOfBoxPlusChunk = 17;
    		}
		}
		else if(typeOfChunk == 8){
			if(typeOfBox == 1){
        		typeOfBoxPlusChunk = 22;
            }
            else if(typeOfBox == 2){
            	typeOfBoxPlusChunk = 22;
            }
    		else if(typeOfBox == 3){
    			typeOfBoxPlusChunk = 21;
    		}
    		else if(typeOfBox == 4){
    			typeOfBoxPlusChunk = 21;
    		}
		}
		else if(typeOfChunk == 9){
			if(typeOfBox == 1){
        		typeOfBoxPlusChunk = 6;
            }
            else if(typeOfBox == 2){
            	typeOfBoxPlusChunk = 12;
            }
    		else if(typeOfBox == 3){
    			typeOfBoxPlusChunk = 16;
    		}
    		else if(typeOfBox == 4){
    			typeOfBoxPlusChunk = 5;
    		}
		}
        
        for (int i = 0; i < this.cachedBlockIDs.length; ++i)
        {
            IBlockState iblockstate = this.cachedBlockIDs[i];
            if (iblockstate != null)
            {
                for (int j = 0; j < 16; ++j)
                {
                    for (int k = 0; k < 16; ++k)
                    {
                    	chunkprimer.setBlockState(j, i, k, iblockstate);
                    	if(iblockstate.getBlock() == Blocks.AIR){
                    		if(typeOfBoxPlusChunk == 2){
                    			chunkprimer.setBlockState(0, i, k, this.getMaterialBlockState());
                    			chunkprimer.setBlockState(j, i, 0, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 3){
                    			chunkprimer.setBlockState(15, i, k, this.getMaterialBlockState());
                    			chunkprimer.setBlockState(j, i, 0, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 4){
                    			chunkprimer.setBlockState(0, i, k, this.getMaterialBlockState());
                    			chunkprimer.setBlockState(j, i, 15, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 5){
                    			chunkprimer.setBlockState(15, i, k, this.getMaterialBlockState());
                    			chunkprimer.setBlockState(j, i, 15, this.getMaterialBlockState());                 			
                    		}
                    		else if(typeOfBoxPlusChunk == 6){
                    			if(k!=15)chunkprimer.setBlockState(14, i, k, this.getMaterialBlockState());
                    			if(j!=15)chunkprimer.setBlockState(j, i, 14, this.getMaterialBlockState());                    		
                    		}
                    		else if(typeOfBoxPlusChunk == 7){
                    			if(k!=15)chunkprimer.setBlockState(1, i, k, this.getMaterialBlockState());
                    			if(j!=0)chunkprimer.setBlockState(j, i, 14, this.getMaterialBlockState());                    			
                    		}
                    		else if(typeOfBoxPlusChunk == 8){
                    			if(k!=0)chunkprimer.setBlockState(14, i, k, this.getMaterialBlockState());
                    			if(j!=15)chunkprimer.setBlockState(j, i, 1, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 9){
                    			if(k!=0)chunkprimer.setBlockState(1, i, k, this.getMaterialBlockState());
                    			if(j!=0)chunkprimer.setBlockState(j, i, 1, this.getMaterialBlockState());             
                    		}
                    		else if(typeOfBoxPlusChunk == 10){
                    			if(k!=15)chunkprimer.setBlockState(0, i, k, this.getMaterialBlockState());
                    			chunkprimer.setBlockState(j, i, 14, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 11){
                    			if(k!=0)chunkprimer.setBlockState(0, i, k, this.getMaterialBlockState());
                    			chunkprimer.setBlockState(j, i, 1, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 12){
                    			if(k!=15)chunkprimer.setBlockState(15, i, k, this.getMaterialBlockState());
                    			chunkprimer.setBlockState(j, i, 14, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 13){
                    			if(k!=0)chunkprimer.setBlockState(15, i, k, this.getMaterialBlockState());
                    			chunkprimer.setBlockState(j, i, 1, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 14){
                    			chunkprimer.setBlockState(14, i, k, this.getMaterialBlockState());
                    			if(j!=15)chunkprimer.setBlockState(j, i, 0, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 15){
                    			chunkprimer.setBlockState(1, i, k, this.getMaterialBlockState());
                    			if(j!=0)chunkprimer.setBlockState(j, i, 0, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 16){
                    			chunkprimer.setBlockState(14, i, k, this.getMaterialBlockState());
                    			if(j!=15)chunkprimer.setBlockState(j, i, 15, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 17){
                    			chunkprimer.setBlockState(1, i, k, this.getMaterialBlockState());
                    			if(j!=0)chunkprimer.setBlockState(j, i, 15, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 18){
                    			chunkprimer.setBlockState(0, i, k, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 19){
                    			chunkprimer.setBlockState(j, i, 0, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 20){
                    			chunkprimer.setBlockState(15, i, k, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 21){
                    			chunkprimer.setBlockState(j, i, 15, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 22){
                    			chunkprimer.setBlockState(j, i, 14, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 23){
                    			chunkprimer.setBlockState(j, i, 1, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 24){
                    			chunkprimer.setBlockState(14, i, k, this.getMaterialBlockState());
                    		}
                    		else if(typeOfBoxPlusChunk == 25){
                    			chunkprimer.setBlockState(1, i, k, this.getMaterialBlockState());
                    		}
                    	}
                    }
                }
            }
        }
        if(typeOfBoxPlusChunk == 6){
        	chunkprimer.setBlockState(15, 0, 15, this.umbreakableGlowstone.getDefaultState());
			chunkprimer.setBlockState(14, 1, 15, Blocks.TORCH.getDefaultState());
			chunkprimer.setBlockState(15, 1, 14, Blocks.TORCH.getDefaultState());
		}
        else if(typeOfBoxPlusChunk == 7){
        	chunkprimer.setBlockState(0, 0, 15, this.umbreakableGlowstone.getDefaultState());
			chunkprimer.setBlockState(1, 1, 15, Blocks.TORCH.getDefaultState());
			chunkprimer.setBlockState(0, 1, 14, Blocks.TORCH.getDefaultState());
		}
        else if(typeOfBoxPlusChunk == 8){
        	chunkprimer.setBlockState(15, 0, 0, this.umbreakableGlowstone.getDefaultState());
			chunkprimer.setBlockState(14, 1, 0, Blocks.TORCH.getDefaultState());
			chunkprimer.setBlockState(15, 1, 1, Blocks.TORCH.getDefaultState());
		}
        else if(typeOfBoxPlusChunk == 9){
        	chunkprimer.setBlockState(0, 0, 0, this.umbreakableGlowstone.getDefaultState());
			chunkprimer.setBlockState(1, 1, 0, Blocks.TORCH.getDefaultState());
			chunkprimer.setBlockState(0, 1, 1, Blocks.TORCH.getDefaultState());
		}

        Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
        Biome[] abiome = this.worldObj.getBiomeProvider().getBiomes((Biome[])null, x * 16, z * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();

        for (int l = 0; l < abyte.length; ++l)
        {
            abyte[l] = (byte)Biome.getIdForBiome(abiome[l]);
        }

//        chunk.generateSkylightMap();
        return chunk;
    }

    public void populate(int x, int z)
    {
    }

    public boolean generateStructures(Chunk chunkIn, int x, int z)
    {
        return false;
    }

    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
    	return null;
    }

    @Nullable
    public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position)
    {
        return null;
    }

    public void recreateStructures(Chunk chunkIn, int x, int z)
    {
    }
}
