package pjc21.mod.entity.unikitty.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import pjc21.mod.entity.unikitty.EntityUniKitty;

public class ModelUniKitty extends ModelBase
{
	private final ModelRenderer unikittyBackLeftLeg;
    private final ModelRenderer unikittyBackRightLeg;
    private final ModelRenderer unikittyFrontLeftLeg;
    private final ModelRenderer unikittyFrontRightLeg;
    private final ModelRenderer unikittyTail;
    private final ModelRenderer unikittyTail2;
    private final ModelRenderer unikittyHead;
    private final ModelRenderer unikittyBody;
    private int state = 1;
    
    
    public ModelUniKitty()
    {
        this.setTextureOffset("head.main", 0, 0);
        this.setTextureOffset("head.nose", 0, 24);
        this.setTextureOffset("head.ear1", 0, 10);
        this.setTextureOffset("head.ear2", 6, 10);
        this.setTextureOffset("head.horn", 13, 22);
        this.unikittyHead = new ModelRenderer(this, "head");
        this.unikittyHead.addBox("main", -2.5F, -2.0F, -3.0F, 5, 4, 5);
        this.unikittyHead.addBox("nose", -1.5F, 0.0F, -4.0F, 3, 2, 2);
        this.unikittyHead.addBox("ear1", -2.0F, -3.0F, 0.0F, 1, 1, 2);
        this.unikittyHead.addBox("ear2", 1.0F, -3.0F, 0.0F, 1, 1, 2);
        this.unikittyHead.addBox("horn", -0.5F, -6.0F, 0.0F, 1, 4, 1);
        this.unikittyHead.setRotationPoint(0.0F, 15.0F, -9.0F);
        this.unikittyBody = new ModelRenderer(this, 20, 0);
        this.unikittyBody.addBox(-2.0F, 3.0F, -8.0F, 4, 16, 6, 0.0F);
        this.unikittyBody.setRotationPoint(0.0F, 12.0F, -10.0F);
        this.unikittyTail = new ModelRenderer(this, 0, 15);
        this.unikittyTail.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
        this.unikittyTail.rotateAngleX = 0.9F;
        this.unikittyTail.setRotationPoint(0.0F, 15.0F, 8.0F);
        this.unikittyTail2 = new ModelRenderer(this, 4, 15);
        this.unikittyTail2.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1);
        this.unikittyTail2.setRotationPoint(0.0F, 20.0F, 14.0F);
        this.unikittyBackLeftLeg = new ModelRenderer(this, 8, 13);
        this.unikittyBackLeftLeg.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2);
        this.unikittyBackLeftLeg.setRotationPoint(1.1F, 18.0F, 5.0F);
        this.unikittyBackRightLeg = new ModelRenderer(this, 8, 13);
        this.unikittyBackRightLeg.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2);
        this.unikittyBackRightLeg.setRotationPoint(-1.1F, 18.0F, 5.0F);
        this.unikittyFrontLeftLeg = new ModelRenderer(this, 40, 0);
        this.unikittyFrontLeftLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2);
        this.unikittyFrontLeftLeg.setRotationPoint(1.2F, 13.8F, -5.0F);
        this.unikittyFrontRightLeg = new ModelRenderer(this, 40, 0);
        this.unikittyFrontRightLeg.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2);
        this.unikittyFrontRightLeg.setRotationPoint(-1.2F, 13.8F, -5.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);

        if (this.isChild)
        {
            float f = 2.0F;
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            GlStateManager.translate(0.0F, 10.0F * scale, 4.0F * scale);
            this.unikittyHead.render(scale);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            this.unikittyBody.render(scale);
            this.unikittyBackLeftLeg.render(scale);
            this.unikittyBackRightLeg.render(scale);
            this.unikittyFrontLeftLeg.render(scale);
            this.unikittyFrontRightLeg.render(scale);
            this.unikittyTail.render(scale);
            this.unikittyTail2.render(scale);
            GlStateManager.popMatrix();
        }
        else
        {
            this.unikittyHead.render(scale);
            this.unikittyBody.render(scale);
            this.unikittyTail.render(scale);
            this.unikittyTail2.render(scale);
            this.unikittyBackLeftLeg.render(scale);
            this.unikittyBackRightLeg.render(scale);
            this.unikittyFrontLeftLeg.render(scale);
            this.unikittyFrontRightLeg.render(scale);
        }
    }

    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        this.unikittyHead.rotateAngleX = headPitch * 0.017453292F;
        this.unikittyHead.rotateAngleY = netHeadYaw * 0.017453292F;

        if (this.state != 3)
        {
            this.unikittyBody.rotateAngleX = ((float)Math.PI / 2F);

            if (this.state == 2)
            {
                this.unikittyBackLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;
                this.unikittyBackRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 0.3F) * limbSwingAmount;
                this.unikittyFrontLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI + 0.3F) * limbSwingAmount;
                this.unikittyFrontRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount;
                this.unikittyTail2.rotateAngleX = 1.7278761F + ((float)Math.PI / 10F) * MathHelper.cos(limbSwing) * limbSwingAmount;
            }
            else
            {
                this.unikittyBackLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;
                this.unikittyBackRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount;
                this.unikittyFrontLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * limbSwingAmount;
                this.unikittyFrontRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;

                if (this.state == 1)
                {
                    this.unikittyTail2.rotateAngleX = 1.7278761F + ((float)Math.PI / 4F) * MathHelper.cos(limbSwing) * limbSwingAmount;
                }
                else
                {
                    this.unikittyTail2.rotateAngleX = 1.7278761F + 0.47123894F * MathHelper.cos(limbSwing) * limbSwingAmount;
                }
            }
        }
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third float params here are the same second
     * and third as in the setRotationAngles method.
     */
    public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTickTime)
    {
        EntityUniKitty entitycustomcat = (EntityUniKitty)entitylivingbaseIn;
        this.unikittyBody.rotationPointY = 12.0F;
        this.unikittyBody.rotationPointZ = -10.0F;
        this.unikittyHead.rotationPointY = 15.0F;
        this.unikittyHead.rotationPointZ = -9.0F;
        this.unikittyTail.rotationPointY = 15.0F;
        this.unikittyTail.rotationPointZ = 8.0F;
        this.unikittyTail2.rotationPointY = 20.0F;
        this.unikittyTail2.rotationPointZ = 14.0F;
        this.unikittyFrontLeftLeg.rotationPointY = 13.8F;
        this.unikittyFrontLeftLeg.rotationPointZ = -5.0F;
        this.unikittyFrontRightLeg.rotationPointY = 13.8F;
        this.unikittyFrontRightLeg.rotationPointZ = -5.0F;
        this.unikittyBackLeftLeg.rotationPointY = 18.0F;
        this.unikittyBackLeftLeg.rotationPointZ = 5.0F;
        this.unikittyBackRightLeg.rotationPointY = 18.0F;
        this.unikittyBackRightLeg.rotationPointZ = 5.0F;
        this.unikittyTail.rotateAngleX = 0.9F;

        if (entitycustomcat.isSneaking())
        {
            ++this.unikittyBody.rotationPointY;
            this.unikittyHead.rotationPointY += 2.0F;
            ++this.unikittyTail.rotationPointY;
            this.unikittyTail2.rotationPointY += -4.0F;
            this.unikittyTail2.rotationPointZ += 2.0F;
            this.unikittyTail.rotateAngleX = ((float)Math.PI / 2F);
            this.unikittyTail2.rotateAngleX = ((float)Math.PI / 2F);
            this.state = 0;
        }
        else if (entitycustomcat.isSprinting())
        {
            this.unikittyTail2.rotationPointY = this.unikittyTail.rotationPointY;
            this.unikittyTail2.rotationPointZ += 2.0F;
            this.unikittyTail.rotateAngleX = ((float)Math.PI / 2F);
            this.unikittyTail2.rotateAngleX = ((float)Math.PI / 2F);
            this.state = 2;
        }
        
        else if (entitycustomcat.isSitting())
        {
            this.unikittyBody.rotateAngleX = ((float)Math.PI / 4F);
            this.unikittyBody.rotationPointY += -4.0F;
            this.unikittyBody.rotationPointZ += 5.0F;
            this.unikittyHead.rotationPointY += -3.3F;
            ++this.unikittyHead.rotationPointZ;
            this.unikittyTail.rotationPointY += 8.0F;
            this.unikittyTail.rotationPointZ += -2.0F;
            this.unikittyTail2.rotationPointY += 2.0F;
            this.unikittyTail2.rotationPointZ += -0.8F;
            this.unikittyTail.rotateAngleX = 1.7278761F;
            this.unikittyTail2.rotateAngleX = 2.670354F;
            this.unikittyFrontLeftLeg.rotateAngleX = -0.15707964F;
            this.unikittyFrontLeftLeg.rotationPointY = 15.8F;
            this.unikittyFrontLeftLeg.rotationPointZ = -7.0F;
            this.unikittyFrontRightLeg.rotateAngleX = -0.15707964F;
            this.unikittyFrontRightLeg.rotationPointY = 15.8F;
            this.unikittyFrontRightLeg.rotationPointZ = -7.0F;
            this.unikittyBackLeftLeg.rotateAngleX = -((float)Math.PI / 2F);
            this.unikittyBackLeftLeg.rotationPointY = 21.0F;
            this.unikittyBackLeftLeg.rotationPointZ = 1.0F;
            this.unikittyBackRightLeg.rotateAngleX = -((float)Math.PI / 2F);
            this.unikittyBackRightLeg.rotationPointY = 21.0F;
            this.unikittyBackRightLeg.rotationPointZ = 1.0F;
            this.state = 3;
        }
        
        else
        {
            this.state = 1;
        }
    }
}
