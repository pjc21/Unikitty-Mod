package pjc21.mods.unikittymod.items.armor.unikitty;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class UnikittyArmorModel extends BipedModel<LivingEntity> {
    public UnikittyArmorModel(float modelSize) {
        super(modelSize, 0.0F, 64, 64);

        ModelRenderer horn = new ModelRenderer(this);
        horn.setPos(0.0F, -1.0F, 0.0F);
        head.addChild(horn);
        horn.texOffs(0, 33).addBox(-0.5F, -14.0F, -2.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);

        ModelRenderer maine = new ModelRenderer(this);
        maine.setPos(0.0F, -1.0F, 1.0F);
        head.addChild(maine);
        maine.texOffs(0, 41).addBox(-0.5F, -9.0F, 1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        maine.texOffs(4, 33).addBox(-0.5F, -9.0F, 4.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        maine.texOffs(8, 41).addBox(-0.5F, -10.0F, 2.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        maine.texOffs(0, 45).addBox(-0.5F, -10.0F, 5.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        maine.texOffs(16, 42).addBox(-0.5F, -11.0F, 3.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        maine.texOffs(4, 45).addBox(-0.5F, -9.0F, 6.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
    }
}
