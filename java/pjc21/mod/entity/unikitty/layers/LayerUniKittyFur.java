package pjc21.mod.entity.unikitty.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import pjc21.mod.entity.unikitty.EntityUniKitty;
import pjc21.mod.entity.unikitty.model.ModelUniKitty;
import pjc21.mod.entity.unikitty.render.RenderUniKitty;
import pjc21.mod.util.Reference;

public class LayerUniKittyFur implements LayerRenderer<EntityUniKitty>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID + ":textures/entity/unikitty/unikitty_fur.png");
    private final RenderUniKitty unikittyRenderer;
    private final ModelUniKitty unikittyModel = new ModelUniKitty();

    public LayerUniKittyFur(RenderUniKitty rendermanagerIn)
    {
        this.unikittyRenderer = rendermanagerIn;
    }

    @Override
    public void doRenderLayer(EntityUniKitty entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entitylivingbaseIn.isInvisible())
        {
            this.unikittyRenderer.bindTexture(TEXTURE);

            if (entitylivingbaseIn.hasCustomName() && "kaena".equals(entitylivingbaseIn.getCustomNameTag()))
            {
                int i1 = 25;
                int i = entitylivingbaseIn.ticksExisted / 25 + entitylivingbaseIn.getEntityId();
                int j = EnumDyeColor.values().length;
                int k = i % j;
                int l = (i + 1) % j;
                float f = ((float)(entitylivingbaseIn.ticksExisted % 25) + partialTicks) / 25.0F;
                float[] afloat1 = EntityUniKitty.getDyeRgb(EnumDyeColor.byMetadata(k));
                float[] afloat2 = EntityUniKitty.getDyeRgb(EnumDyeColor.byMetadata(l));
                GlStateManager.color(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f);
            }
            else
            {
                float[] afloat = EntityUniKitty.getDyeRgb(entitylivingbaseIn.getFurColor());
                GlStateManager.color(afloat[0], afloat[1], afloat[2]);
            }

            this.unikittyModel.setModelAttributes(this.unikittyRenderer.getMainModel());
            this.unikittyModel.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
            this.unikittyModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}
