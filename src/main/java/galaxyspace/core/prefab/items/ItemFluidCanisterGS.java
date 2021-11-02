package galaxyspace.core.prefab.items;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Nullable;

import galaxyspace.core.handler.GSItemCanisterGenericHandler;
import galaxyspace.core.util.GSCreativeTabs;
import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.core.items.ISortableItem;
import micdoodle8.mods.galacticraft.core.items.ItemCanisterGeneric;
import micdoodle8.mods.galacticraft.core.util.CompatibilityManager;
import micdoodle8.mods.galacticraft.core.util.EnumSortCategoryItem;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.core.util.JavaUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemFluidCanisterGS extends ItemCanisterGeneric implements ISortableItem{

	private Fluid fluid;
	private int amount;
	
	public ItemFluidCanisterGS(String assetName, Fluid fluid)
    {
       this(assetName, fluid, EMPTY);
    }
	
	public ItemFluidCanisterGS(String assetName, Fluid fluid, int amount)
    {
        super(assetName);
        this.fluid = fluid;
        this.amount = amount;
        this.setAllowedFluid(fluid.getName());
        this.setMaxDamage(amount);
    }
	
	@Override
    public CreativeTabs getCreativeTab()
    {
    	return GSCreativeTabs.GSItemsTab;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return EnumRarity.COMMON;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (tab == GSCreativeTabs.GSItemsTab || tab == CreativeTabs.SEARCH) {
			list.add(new ItemStack(this, 1, 1));
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() > 0)
        {
            tooltip.add(GCCoreUtil.translate(this.fluid.getUnlocalizedName()) + ": " + (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage()));
        }
    }

    @Override
    public EnumSortCategoryItem getCategory(int meta)
    {
        return EnumSortCategoryItem.CANISTER;
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
    {
        return new GSItemCanisterGenericHandler(stack, this.amount);
    }
    
    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        //Workaround for strange behaviour in TE Transposer
        if (CompatibilityManager.isTELoaded())
        {
            if (JavaUtil.instance.isCalledBy("thermalexpansion.block.machine.TileTransposer"))
            {
                return ItemStack.EMPTY;
            }
        }

        return new ItemStack(this.getContainerItem(), 1, this.amount);
    }
    
    @Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if (this.amount == par1ItemStack.getItemDamage())
        {
            if (par1ItemStack.getItem() != GCItems.oilCanister)
            {
                this.replaceEmptyCanisterItem(par1ItemStack, GCItems.oilCanister);
            }
            par1ItemStack.setTagCompound(null);
        }
        else if (par1ItemStack.getItemDamage() <= 0)
        {
            par1ItemStack.setItemDamage(1);
        }
    }
    
    @Override
    public int fill(ItemStack container, FluidStack resource, boolean doFill)
    {
        if (resource == null || resource.getFluid() == null || resource.amount <= 0 || container == null || container.getItemDamage() <= 1 || !(container.getItem() instanceof ItemCanisterGeneric))
        {
            return 0;
        }
        String fluidName = resource.getFluid().getName();

        int capacityPlusOne = container.getItemDamage();
        if (capacityPlusOne <= 1)
        {
        	if (capacityPlusOne < 1)
        	{
	            //It shouldn't be possible, but just in case, set this to a proper filled item
        		container.setItemDamage(1);
        	}
        	return 0;
        }
        if (capacityPlusOne >= this.amount)
        {
            //Empty canister - find a new canister to match the fluid
            for (ItemCanisterGeneric i : GCItems.canisterTypes)
            {
                if (fluidName.equalsIgnoreCase(i.getAllowedFluid()))
                {
                    if (!doFill)
                    {
                        return Math.min(resource.amount, this.capacity);
                    }

                    this.replaceEmptyCanisterItem(container, i);
                    break;
                }
            }
            if (capacityPlusOne > this.amount)
            {
	            //It shouldn't be possible, but just in case, set this to a proper empty item
            	capacityPlusOne = ItemCanisterGeneric.EMPTY;
	            container.setItemDamage(capacityPlusOne);
            }
        }
        
        if (fluidName.equalsIgnoreCase(((ItemCanisterGeneric) container.getItem()).getAllowedFluid()))
        {
            int added = Math.min(resource.amount, capacityPlusOne - 1);
            if (doFill && added > 0)
            {
                container.setItemDamage(Math.max(1, container.getItemDamage() - added));
            }
            return added;
        }

        return 0;
    }

    @Override
    public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain)
    {
        if (this.getAllowedFluid() == null || container.getItemDamage() >= this.amount)
        {
            return null;
        }

        FluidStack used = this.getFluid(container);
        if (used != null && used.amount > maxDrain) used.amount = maxDrain;
        if (doDrain && used != null && used.amount > 0)
        {
            this.setNewDamage(container, container.getItemDamage() + used.amount);
        }
        return used;
    }

    @Override
    protected void setNewDamage(ItemStack container, int newDamage)
    {
        newDamage = Math.min(newDamage, this.amount);
        container.setItemDamage(newDamage);
        if (newDamage == this.amount)
        {
            if (container.getItem() != GCItems.oilCanister)
            {
                this.replaceEmptyCanisterItem(container, GCItems.oilCanister);
                return;
            }
        }
    }
    
    private void replaceEmptyCanisterItem(ItemStack container, Item newItem)
    {
    	try
    	{
    		Class<? extends ItemStack> itemStack = container.getClass();
    		Field itemId = itemStack.getDeclaredField(GCCoreUtil.isDeobfuscated() ? "item" : "field_151002_e");
    		itemId.setAccessible(true);
    		itemId.set(container, newItem);
    		Method forgeInit = itemStack.getDeclaredMethod("forgeInit");
    		forgeInit.setAccessible(true);
    		forgeInit.invoke(container);
    	}
    	catch (Exception ignore) { }
    }
}
