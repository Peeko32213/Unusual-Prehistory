package com.peeko32213.unusualprehistory.client.screen;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.peeko32213.unusualprehistory.client.screen.util.BookBlit;
import com.peeko32213.unusualprehistory.client.screen.util.BookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;

public class LinkPlantButton extends Button {

    private static final Map<String, Entity> renderedEntites = new HashMap<>();
    private static final Quaternionf ENTITY_ROTATION = (new Quaternionf()).rotationXYZ((float)Math.toRadians(30.0), (float)Math.toRadians(130.0), 3.1415927F);

    private final PlantLinkData data;
    //private final AbstractBookScreen bookGUI;
    private final BookScreen bookGUITest;
    private static final ResourceLocation BOOK_WIDGET_TEXTURE = new ResourceLocation("unusualprehistory:textures/gui/book/widgets.png");
   // public LinkButton(AbstractBookScreen bookGUI, EntityLinkData linkData, int k, int l, Button.OnPress o) {
   //     super(k + linkData.getX() - 12, l + linkData.getY(), (int) (24 * linkData.getScale()), (int) (24 * linkData.getScale()), CommonComponents.GUI_DONE, o);
   //     this.data = linkData;
   //     //this.bookGUI = bookGUI;
   // }

    public LinkPlantButton(BookScreen bookGUI, PlantLinkData linkData, int k, int l, OnPress o) {
        super(k, l, (int) (24 * linkData.getScale()), (int) (24 * linkData.getScale()), CommonComponents.GUI_DONE, o, DEFAULT_NARRATION);
        this.data = linkData;
        this.bookGUITest = bookGUI;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int lvt_5_1_ = 0;
        int lvt_6_1_ = 30;
        float f = (float)this.data.getScale();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate((float)this.getX(), (float)this.getY(), 0.0F);
        guiGraphics.pose().scale(f, f, 1.0F);
        this.drawBtn(false, guiGraphics, 0, 0, lvt_5_1_, lvt_6_1_, 24, 24);
        Entity model = null;
        EntityType type = (EntityType)ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(this.data.getPlant()));
        if (type != null) {
            model = (Entity)renderedEntites.putIfAbsent(this.data.getPlant(), type.create(Minecraft.getInstance().level));
        }

        guiGraphics.enableScissor(this.getX() + Math.round(f * 4.0F), this.getY() + Math.round(f * 4.0F), this.getX() + Math.round(f * 20.0F), this.getY() + Math.round(f * 20.0F));
        if (model != null) {
            model.tickCount = Minecraft.getInstance().player.tickCount;
            float renderScale = (float)(this.data.getPlantScale() * (double)f * 10.0);
            this.renderEntityInInventory(guiGraphics, 11 + (int)((double)this.data.getOffset_x() * this.data.getPlantScale()), 22 + (int)((double)this.data.getOffset_y() * this.data.getPlantScale()), renderScale, ENTITY_ROTATION, model);
        }

        guiGraphics.disableScissor();
        if (this.isHovered) {
            this.bookGUITest.setEntityTooltip(this.data.getHoverText());
            lvt_5_1_ = 48;
        } else {
            lvt_5_1_ = 24;
        }

        this.drawBtn(!this.isHovered, guiGraphics, 0, 0, lvt_5_1_, lvt_6_1_, 24, 24);
        guiGraphics.pose().popPose();
    }

    public void drawBtn(boolean color, GuiGraphics guiGraphics, int p_238474_2_, int p_238474_3_, int p_238474_4_, int p_238474_5_, int p_238474_6_, int p_238474_7_) {
        if (color) {
            int widgetColor = this.bookGUITest.getWidgetColor();
            int r = (widgetColor & 16711680) >> 16;
            int g = (widgetColor & '\uff00') >> 8;
            int b = widgetColor & 255;
            BookBlit.blitWithColor(guiGraphics, this.bookGUITest.getBookWidgetTexture(), p_238474_2_, p_238474_3_, 0, (float)p_238474_4_, (float)p_238474_5_, p_238474_6_, p_238474_7_, 256, 256, r, g, b, 255);
        } else {
            guiGraphics.blit(this.bookGUITest.getBookWidgetTexture(), p_238474_2_, p_238474_3_, 0, (float)p_238474_4_, (float)p_238474_5_, p_238474_6_, p_238474_7_, 256, 256);
        }

    }


    public void renderEntityInInventory(GuiGraphics guiGraphics, int xPos, int yPos, float scale, Quaternionf rotation, Entity entity) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate((double)xPos, (double)yPos, 50.0);
        guiGraphics.pose().mulPoseMatrix((new Matrix4f()).scaling(scale, scale, -scale));
        guiGraphics.pose().mulPose(rotation);
        Vector3f light0 = (new Vector3f(1.0F, -1.0F, -1.0F)).normalize();
        Vector3f light1 = (new Vector3f(-1.0F, 1.0F, 1.0F)).normalize();
        RenderSystem.setShaderLights(light0, light1);
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        entityrenderdispatcher.setRenderShadow(false);
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, guiGraphics.pose(), guiGraphics.bufferSource(), 15728880);
        });
        guiGraphics.flush();
        entityrenderdispatcher.setRenderShadow(true);
        guiGraphics.pose().popPose();
        Lighting.setupForFlatItems();
    }
}
