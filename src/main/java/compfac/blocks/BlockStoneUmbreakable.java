package compfac.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import compfac.CompressedFactory;
import compfac.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

public class BlockStoneUmbreakable extends BlockFactoryPart {
	
	public BlockStoneUmbreakable() {
		super("stone_umbreakable", "StoneUmbreakable");
	}
}
