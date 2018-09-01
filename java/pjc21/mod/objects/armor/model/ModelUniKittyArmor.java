package pjc21.mod.objects.armor.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelUniKittyArmor extends ModelBiped
{
	//fields
    ModelRenderer unihorn;
    ModelRenderer main1;
    ModelRenderer main4;
    ModelRenderer main2;
    ModelRenderer main5;
    ModelRenderer main3;
    ModelRenderer main6;
    ModelRenderer leftshoulder;
    ModelRenderer rightshoulder;
    
  
    public ModelUniKittyArmor(float scale)
    {
    	super(scale, 0, 64, 64);
    	textureWidth = 64;
    	textureHeight = 64;
    
    	unihorn = new ModelRenderer(this, 0, 32);
    	unihorn.addBox(-0.5F, -12F, -2F, 1, 4, 1);
    	unihorn.setRotationPoint(0F, 0F, 0F);
    	unihorn.setTextureSize(64, 32);
    	unihorn.mirror = true;
    	setRotation(unihorn, 0F, 0F, 0F);
    	main1 = new ModelRenderer(this, 5, 32);
    	main1.addBox(-0.5F, -9F, 1F, 1, 1, 3);
    	main1.setRotationPoint(0F, 0F, 0F);
    	main1.setTextureSize(64, 32);
    	main1.mirror = true;
    	setRotation(main1, 0F, 0F, 0F);
    	main4 = new ModelRenderer(this, 30, 32);
    	main4.addBox(-0.5F, -9F, 4F, 1, 6, 1);
    	main4.setRotationPoint(0F, 0F, 0F);
    	main4.setTextureSize(64, 32);
    	main4.mirror = true;
    	setRotation(main4, 0F, 0F, 0F);
    	main2 = new ModelRenderer(this, 14, 32);
    	main2.addBox(-0.5F, -10F, 2F, 1, 1, 3);
    	main2.setRotationPoint(0F, 0F, 0F);
    	main2.setTextureSize(64, 32);
    	main2.mirror = true;
    	setRotation(main2, 0F, 0F, 0F);
    	main5 = new ModelRenderer(this, 35, 32);
    	main5.addBox(-0.5F, -10F, 5F, 1, 8, 1);
    	main5.setRotationPoint(0F, 0F, 0F);
    	main5.setTextureSize(64, 32);
    	main5.mirror = true;
    	setRotation(main5, 0F, 0F, 0F);
    	main3 = new ModelRenderer(this, 23, 32);
    	main3.addBox(-0.5F, -11F, 3F, 1, 1, 2);
    	main3.setRotationPoint(0F, 0F, 0F);
    	main3.setTextureSize(64, 32);
    	main3.mirror = true;
    	setRotation(main3, 0F, 0F, 0F);
    	main6 = new ModelRenderer(this, 40, 32);
    	main6.addBox(-0.5F, -9F, 6F, 1, 8, 1);
    	main6.setRotationPoint(0F, 0F, 0F);
    	main6.setTextureSize(64, 32);
    	main6.mirror = true;
    	setRotation(main6, 0F, 0F, 0F);
    	leftshoulder = new ModelRenderer(this, 0, 38);
    	leftshoulder.addBox(-0.8F, -2.5F, -2.5F, 5, 5, 5);
    	leftshoulder.setRotationPoint(0F, 0F, 0F);
    	leftshoulder.setTextureSize(64, 32);
    	leftshoulder.mirror = true;
    	setRotation(leftshoulder, 0F, 0F, 0F);
    	rightshoulder = new ModelRenderer(this, 0, 38);
    	rightshoulder.addBox(-4.2F, -2.5F, -2.5F, 5, 5, 5);
    	rightshoulder.setRotationPoint(0F, 0F, 0F);
    	rightshoulder.setTextureSize(64, 32);
    	rightshoulder.mirror = true;
    	setRotation(rightshoulder, 0F, 0F, 0F);

    	bipedHead.addChild(unihorn);
    	bipedHead.addChild(main1);
    	bipedHead.addChild(main2);
    	bipedHead.addChild(main3);
    	bipedHead.addChild(main4);
    	bipedHead.addChild(main5);
    	bipedHead.addChild(main6);
    	bipedLeftArm.addChild(leftshoulder);
    	bipedRightArm.addChild(rightshoulder);
    }
  
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
    	super.render(entity, f, f1, f2, f3, f4, f5);
    	setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
  
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
    	model.rotateAngleX = x;
    	model.rotateAngleY = y;
    	model.rotateAngleZ = z;
    }

}
