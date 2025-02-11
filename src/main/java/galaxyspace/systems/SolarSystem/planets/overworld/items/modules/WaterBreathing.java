package galaxyspace.systems.SolarSystem.planets.overworld.items.modules;

import galaxyspace.core.prefab.items.modules.ItemModule;
import galaxyspace.core.util.GSUtils.Module_Type;
import micdoodle8.mods.galacticraft.core.GCItems;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class WaterBreathing extends ItemModule {

	@Override
	public String getName() {
		return "water_breathing";
	}

	@Override
	public ItemStack getIcon() {
		return getItemsForModule()[0];
	}

	@Override
	public EntityEquipmentSlot getEquipmentSlot() {
		return EntityEquipmentSlot.HEAD;
	}

	@Override
	public boolean isActiveModule() {
		return false;
	}

	@Override
	public ItemStack[] getItemsForModule() {
		return new ItemStack[] { new ItemStack(Items.FISH, 1, 3) };
	}

	@Override
	public ItemModule[] getForrbidenModules() {
		return new ItemModule[] {new SensorLens()};
	}

	@Override
	public Module_Type getType() {
		return Module_Type.SPACESUIT;
	}

}
