package galaxyspace.systems.SolarSystem.planets.mars.dimension.sky;

import org.lwjgl.opengl.GL11;

import asmodeuscore.api.dimension.IAdvancedSpace.StarColor;
import asmodeuscore.core.astronomy.sky.SkyProviderBase;
import galaxyspace.GalaxySpace;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;

public class SkyProviderMars  extends SkyProviderBase
{

	private static final ResourceLocation phobosTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/phobos.png");
	private static final ResourceLocation deimosTexture = new ResourceLocation(GalaxySpace.ASSET_PREFIX, "textures/gui/celestialbodies/sol/moons/deimos.png");
	  
	@Override
	protected void rendererSky(Tessellator tessellator, BufferBuilder worldRenderer, float f10, float ticks) {
		
		GL11.glPushMatrix();

		GL11.glEnable(GL11.GL_BLEND);
		// OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE,
		// GL11.GL_ZERO);

		f10 = 3.5F;
		GL11.glScalef(0.8F, 0.8F, 0.8F);

		GL11.glRotatef(this.mc.world.getCelestialAngle(ticks) * 360.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(80.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(40.0F, 1.0F, 0.0F, 0.0F);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.phobosTexture);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
		worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
		tessellator.draw();

		f10 = 1.5F;
		GL11.glScalef(0.8F, 0.8F, 0.8F);
		GL11.glRotatef(-80.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(140.0F, 0.0F, 0.0F, 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.deimosTexture);
		worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		worldRenderer.pos(-f10, -100.0D, f10).tex(0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, f10).tex(1.0, 1.0).endVertex();
		worldRenderer.pos(f10, -100.0D, -f10).tex(1.0, 0).endVertex();
		worldRenderer.pos(-f10, -100.0D, -f10).tex(0, 0).endVertex();
		tessellator.draw();

		GL11.glDisable(GL11.GL_BLEND);

		GL11.glPopMatrix();
	}

	@Override
	protected boolean enableBaseImages() {
		return true;
	}

	@Override
	protected float sunSize() {
		return 17.5F;
	}

	@Override
	protected boolean enableStar() {
		return true;
	}

	@Override
	protected ResourceLocation sunImage() {
		return null;
	}

	@Override
	protected ModeLight modeLight() {
		return ModeLight.DEFAULT;
	}

	@Override
	protected StarColor colorSunAura() {
		return StarColor.WHITE;
	}

	@Override
	protected Vector3 getAtmosphereColor() {
		float f = mc.world.getSunBrightness(ticks) - 0.2F;
		return new Vector3(160 / 255.0F * f, 120 / 255.0F * f, 120 / 255.0F * f);
	}
	
	@Override
	public boolean enableSmoothRender() {return false;}

}