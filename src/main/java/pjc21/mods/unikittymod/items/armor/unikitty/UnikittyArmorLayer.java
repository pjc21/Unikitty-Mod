package pjc21.mods.unikittymod.items.armor.unikitty;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import pjc21.mods.unikittymod.UnikittyMod;
import pjc21.mods.unikittymod.config.UnikittyConfig;
import pjc21.mods.unikittymod.entities.unikitty.UnikittyEntity;

import java.util.List;

public class UnikittyArmorLayer <T extends LivingEntity, M extends BipedModel<T>, A extends BipedModel<T>> extends BipedArmorLayer<T, M, A> {

    public UnikittyArmorLayer(IEntityRenderer<T, M> entityRendererIn, A innerModel, A outerModel) {
        super(entityRendererIn, innerModel, outerModel);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        this.renderArmorPiece(p_225628_1_, p_225628_2_, p_225628_4_, EquipmentSlotType.CHEST, p_225628_3_, UnikittyMod.PROXY.getUnikittyArmorModel(EquipmentSlotType.CHEST), p_225628_7_);
        this.renderArmorPiece(p_225628_1_, p_225628_2_, p_225628_4_, EquipmentSlotType.LEGS, p_225628_3_, UnikittyMod.PROXY.getUnikittyArmorModel(EquipmentSlotType.LEGS), p_225628_7_);
        this.renderArmorPiece(p_225628_1_, p_225628_2_, p_225628_4_, EquipmentSlotType.FEET, p_225628_3_, UnikittyMod.PROXY.getUnikittyArmorModel(EquipmentSlotType.FEET), p_225628_7_);
        this.renderArmorPiece(p_225628_1_, p_225628_2_, p_225628_4_, EquipmentSlotType.HEAD, p_225628_3_, UnikittyMod.PROXY.getUnikittyArmorModel(EquipmentSlotType.HEAD), p_225628_7_);
    }

    private void renderArmorPiece(MatrixStack p_241739_1_, IRenderTypeBuffer p_241739_2_, T p_241739_3_, EquipmentSlotType p_241739_4_, int p_241739_5_, A p_241739_6_, float p_225628_7_) {
        ItemStack itemstack = p_241739_3_.getItemBySlot(p_241739_4_);
        if (itemstack.getItem() instanceof UnikittyArmorItem) {
            UnikittyArmorItem armorItem = (UnikittyArmorItem)itemstack.getItem();
            if (armorItem.getSlot() == p_241739_4_) {
                p_241739_6_ = getArmorModelHook(p_241739_3_, itemstack, p_241739_4_, p_241739_6_);
                this.getParentModel().copyPropertiesTo(p_241739_6_);
                this.setPartVisibility(p_241739_6_, p_241739_4_);
                boolean flag1 = itemstack.hasFoil();
                List<? extends String> names = UnikittyConfig.armorSettings.armorNames.get();
                if (names.contains(itemstack.getHoverName().getContents())) {
                    float f;
                    float f1;
                    float f2;
                    int i = p_241739_3_.tickCount / 25 + p_241739_3_.getId();
                    int j = DyeColor.values().length;
                    int k = i % j;
                    int l = (i + 1) % j;
                    float f3 = (((p_241739_3_.tickCount % 25) + p_225628_7_) / 25.0F);
                    float[] afloat1 = UnikittyEntity.getColorArray(DyeColor.byId(k));
                    float[] afloat2 = UnikittyEntity.getColorArray(DyeColor.byId(l));
                    f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
                    f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
                    f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
                    this.renderModel(p_241739_1_, p_241739_2_, p_241739_5_, flag1, p_241739_6_, f, f1, f2, this.getArmorResource(p_241739_3_, itemstack, p_241739_4_, null));
                } else {
                    int i = ((IDyeableArmorItem) armorItem).getColor(itemstack);
                    float f = (float) (i >> 16 & 255) / 255.0F;
                    float f1 = (float) (i >> 8 & 255) / 255.0F;
                    float f2 = (float) (i & 255) / 255.0F;
                    this.renderModel(p_241739_1_, p_241739_2_, p_241739_5_, flag1, p_241739_6_, f, f1, f2, this.getArmorResource(p_241739_3_, itemstack, p_241739_4_, null));
                }
                this.renderModel(p_241739_1_, p_241739_2_, p_241739_5_, flag1, p_241739_6_, 1.0F, 1.0F, 1.0F, this.getArmorResource(p_241739_3_, itemstack, p_241739_4_, "overlay"));
            }
        }
    }

    private void renderModel(MatrixStack p_241738_1_, IRenderTypeBuffer p_241738_2_, int p_241738_3_, boolean p_241738_5_, A p_241738_6_, float p_241738_8_, float p_241738_9_, float p_241738_10_, ResourceLocation armorResource) {
        IVertexBuilder ivertexbuilder = ItemRenderer.getArmorFoilBuffer(p_241738_2_, RenderType.armorCutoutNoCull(armorResource), false, p_241738_5_);
        p_241738_6_.renderToBuffer(p_241738_1_, ivertexbuilder, p_241738_3_, OverlayTexture.NO_OVERLAY, p_241738_8_, p_241738_9_, p_241738_10_, 1.0F);
    }
}
