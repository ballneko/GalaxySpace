package galaxyspace.core.prefab.entities;

import galaxyspace.GalaxySpace;
import galaxyspace.core.client.gui.entity.GuiAstroWolfInventory;
import galaxyspace.core.network.packet.GSPacketSimple;
import galaxyspace.core.network.packet.GSPacketSimple.GSEnumSimplePacket;
import galaxyspace.core.prefab.inventory.InventoryAstroWolf;
import galaxyspace.core.util.GSUtils;
import micdoodle8.mods.galacticraft.api.entity.IEntityBreathable;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.planets.mars.MarsModuleClient;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import micdoodle8.mods.galacticraft.planets.mars.network.PacketSimpleMars;
import micdoodle8.mods.galacticraft.planets.mars.network.PacketSimpleMars.EnumSimplePacketMars;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;

public class EntityAstroWolf extends EntityWolf implements IEntityBreathable {

	public InventoryAstroWolf wolfInventory = new InventoryAstroWolf(this);
	private boolean isHelmet;
	 
	public EntityAstroWolf(World worldIn) {
		super(worldIn);
	}

	@Override
	public boolean canBreath() {
		return true;
	}
	
	@Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
		System.out.println(this.wolfInventory.getStackInSlot(0));
		
		if(player.isSneaking()) {
			if (player == this.getOwner() && this.isTamed())	{
				player.openGui(GalaxySpace.MODID, 1005, this.world, this.getEntityId(), 0, 0);
				
			}
			return true;
		}
		else return super.processInteract(player, hand);
		
		
    }
	
	@Override
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
		super.writeEntityToNBT(nbt);
		ItemStackHelper.saveAllItems(nbt, this.wolfInventory.getInventory());
		//nbt.setTag("WolfInventory", this.wolfInventory.writeToNBT(new NBTTagList()));
    }

	@Override
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        ItemStackHelper.loadAllItems(nbt, this.wolfInventory.getInventory());
        //this.wolfInventory.readFromNBT(nbt.getTagList("WolfInventory", 10));
    }
	
	@Override
	public void onDeath(DamageSource p_70645_1_) {
		super.onDeath(p_70645_1_);

		if (!this.world.isRemote) {
			ItemStack bag = this.wolfInventory.getStackInSlot(1);
			this.wolfInventory.decrStackSize(1, 64);
			this.entityDropItem(bag, 0.5F);
			
		}
	}
}
