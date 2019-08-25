package galaxyspace.core.client.gui.book.pages.blocks;

import asmodeuscore.core.astronomy.gui.book.Page_WithCraftMatrix;
import asmodeuscore.core.utils.BookUtils.Book_Cateroies;
import galaxyspace.api.IPage;
import galaxyspace.core.registers.blocks.GSBlocks;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import micdoodle8.mods.galacticraft.core.GCItems;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@IPage
public class Page_Wind_Generator extends Page_WithCraftMatrix{

	@Override
	public String titlePage() {
		return "wind_generator";
	}

	@Override
	public ResourceLocation iconTitle() {
		return null;
	}

	@Override
	public int getMaxScroll() {
		return 0;
	}

	@Override
	public String getCategory() {
		return Book_Cateroies.BLOCKS.getName();
	}

	@Override
	public void drawPage(int x, int y, FontRenderer font, int mouseX, int mouseY)
	{
		super.drawPage(x, y, font, mouseX, mouseY);
	}
	
	@Override
	public ItemStack getItem() {		
		return new ItemStack(GSBlocks.WIND_GENERATOR);
	}

	@Override
	public Recipe_Type getRecipeType() {
		return Recipe_Type.CRAFTING_TABLE;
	}
}
