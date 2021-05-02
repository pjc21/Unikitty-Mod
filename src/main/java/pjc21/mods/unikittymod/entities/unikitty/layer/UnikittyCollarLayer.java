package pjc21.mods.unikittymod.entities.unikitty.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import pjc21.mods.unikittymod.entities.unikitty.UnikittyEntity;
import pjc21.mods.unikittymod.entities.unikitty.model.UnikittyModel;

public class UnikittyCollarLayer extends LayerRenderer<UnikittyEntity, UnikittyModel<UnikittyEntity>> {

    private static final ResourceLocation UMIKITTY_COLLAR_LOCATION = new ResourceLocation("textures/entity/cat/cat_collar.png");
    private final UnikittyModel<UnikittyEntity> unikittyModel = new UnikittyModel<>(0.01F);

    public UnikittyCollarLayer(IEntityRenderer<UnikittyEntity, UnikittyModel<UnikittyEntity>> p_i50948_1_) {
        super(p_i50948_1_);
    }

    @SuppressWarnings("NullableProblems")
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, UnikittyEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        if (p_225628_4_.isTame()) {
            float[] afloat = p_225628_4_.getCollarColor().getTextureDiffuseColors();
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.unikittyModel, UMIKITTY_COLLAR_LOCATION, p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_, p_225628_7_, afloat[0], afloat[1], afloat[2]);
        }
    }
}
