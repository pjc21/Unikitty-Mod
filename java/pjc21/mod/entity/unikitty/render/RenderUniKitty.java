package pjc21.mod.entity.unikitty.render;

import javax.annotation.Nonnull;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import pjc21.mod.entity.unikitty.EntityUniKitty;
import pjc21.mod.entity.unikitty.layers.LayerUniKittyFur;
import pjc21.mod.entity.unikitty.model.ModelUniKitty;
import pjc21.mod.util.Reference;

public class RenderUniKitty extends RenderLiving<EntityUniKitty>
{
	private ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID + ":textures/entity/unikitty/unikitty.png");

    public static final Factory FACTORY = new Factory();

    public RenderUniKitty(RenderManager rendermanagerIn) 
    {
        super(rendermanagerIn, new ModelUniKitty(), 0.4F);
        this.addLayer(new LayerUniKittyFur(this));
    }

    @Override
    @Nonnull
    protected ResourceLocation getEntityTexture(@Nonnull EntityUniKitty entity) 
    {
        return TEXTURES;
    }

    public static class Factory implements IRenderFactory<EntityUniKitty> 
    {
        @Override
        public Render<? super EntityUniKitty> createRenderFor(RenderManager manager) 
        {
            return new RenderUniKitty(manager);
        }

    }
}
