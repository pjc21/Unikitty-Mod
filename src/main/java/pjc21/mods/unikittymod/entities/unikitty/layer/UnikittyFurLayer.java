package pjc21.mods.unikittymod.entities.unikitty.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import pjc21.mods.unikittymod.UnikittyMod;
import pjc21.mods.unikittymod.config.UnikittyConfig;
import pjc21.mods.unikittymod.entities.unikitty.UnikittyEntity;
import pjc21.mods.unikittymod.entities.unikitty.model.UnikittyModel;

import java.util.List;
import java.util.Objects;

public class UnikittyFurLayer extends LayerRenderer<UnikittyEntity, UnikittyModel<UnikittyEntity>> {

    private static final ResourceLocation UNIKITTY_FUR_LOCATION = new ResourceLocation(UnikittyMod.MODID, "textures/entity/unikitty/unikitty_fur.png");
    private final UnikittyModel<UnikittyEntity> model = new UnikittyModel(0.0F);

    public UnikittyFurLayer(IEntityRenderer<UnikittyEntity, UnikittyModel<UnikittyEntity>> p_i50925_1_) {
        super(p_i50925_1_);
    }

    @SuppressWarnings("NullableProblems")
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, UnikittyEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        if (!p_225628_4_.isInvisible()) {
            float f;
            float f1;
            float f2;
            List<? extends String> names = UnikittyConfig.entitySettings.unikittyEntity.unikittyNames.get();
            if (p_225628_4_.hasCustomName() && names.contains(Objects.requireNonNull(p_225628_4_.getCustomName()).getContents())) {
                int i = p_225628_4_.tickCount / 25 + p_225628_4_.getId();
                int j = DyeColor.values().length;
                int k = i % j;
                int l = (i + 1) % j;
                float f3 = ((float)(p_225628_4_.tickCount % 25) + p_225628_7_) / 25.0F;
                float[] afloat1 = UnikittyEntity.getColorArray(DyeColor.byId(k));
                float[] afloat2 = UnikittyEntity.getColorArray(DyeColor.byId(l));
                f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
                f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
                f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
            } else {
                float[] afloat = UnikittyEntity.getColorArray(p_225628_4_.getColor());
                f = afloat[0];
                f1 = afloat[1];
                f2 = afloat[2];
            }

            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, UNIKITTY_FUR_LOCATION, p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_, p_225628_7_, f, f1, f2);
        }
    }
}
