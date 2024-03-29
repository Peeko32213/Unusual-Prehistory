package com.peeko32213.unusualprehistory.client.screen.util;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractBookScreenOld extends Screen {

    private static final ResourceLocation BOOK_PAGE_TEXTURE = new ResourceLocation("unusualprehistory:textures/gui/book/book_pages.png");
    private static final ResourceLocation BOOK_BINDING_TEXTURE = new ResourceLocation("unusualprehistory:textures/gui/book/book_binding.png");
    private static final ResourceLocation BOOK_WIDGET_TEXTURE = new ResourceLocation("unusualprehistory:textures/gui/book/widgets.png");
    private final List<LineData> lines = new ArrayList<>();
    private final List<LinkData> links = new ArrayList<>();
    private final List<ItemRenderData> itemRenders = new ArrayList<>();
    private final List<RecipeData> recipes = new ArrayList<>();
    private final List<EntityRenderData> entityRenders = new ArrayList<>();
    private final List<EntityLinkData> entityLinks = new ArrayList<>();
    private final List<ImageData> images = new ArrayList<>();
    private final List<Whitespace> yIndexesToSkip = new ArrayList<>();
    private final Map<String, Entity> renderedEntites = new HashMap<>();
    private final Map<String, ResourceLocation> textureMap = new HashMap<>();
    protected ItemStack bookStack;
    protected int xSize = 390;
    protected int ySize = 320;
    protected int currentPageCounter = 0;
    protected int maxPagesFromPrinting = 0;
    protected int linesFromJSON = 0;
    protected int linesFromPrinting = 0;
    protected ResourceLocation prevPageJSON;
    protected ResourceLocation currentPageJSON;
    protected ResourceLocation currentPageText = null;
    private BookPageButtonOld buttonNextPage;
    private BookPageButtonOld buttonPreviousPage;
    private BookPage internalPage = null;
    private String writtenTitle = "";
    private int preservedPageIndex = 0;
    private String entityTooltip;
    private int mouseX;
    private int mouseY;

    public AbstractBookScreenOld(ItemStack bookStack, Component title) {
        super(title);
        this.bookStack = bookStack;
        this.currentPageJSON = getRootPage();
    }

    public static void drawEntityOnScreen(PoseStack stackIn, int posX, int posY, float scale, boolean follow, double xRot, double yRot, double zRot, float mouseX, float mouseY, Entity entity) {
        float customYaw = posX - mouseX;
        float customPitch = posY - mouseY;
        float f = (float) Math.atan(customYaw / 40.0F);
        float f1 = (float) Math.atan(customPitch / 40.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        posestack1.translate(posX, posY, 120.0D);
        posestack1.scale(scale, scale, scale);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(follow ? -f1 * 20.0F : 0.0F);
        quaternion.mul(quaternion1);
        posestack1.mulPose(quaternion);
        posestack1.mulPose(Vector3f.XP.rotationDegrees((float) xRot));
        posestack1.mulPose(Vector3f.YP.rotationDegrees((float) yRot - 270F));
        posestack1.mulPose(Vector3f.ZP.rotationDegrees((float) zRot));
        if (follow) {
            float yaw = -f * 20.0F - (float)yRot;
            entity.setYRot(yaw);
            entity.setXRot(f1 * 20.0F);
            if (entity instanceof LivingEntity) {
                ((LivingEntity) entity).yBodyRot = yaw;
                ((LivingEntity) entity).yBodyRotO = yaw;
                ((LivingEntity) entity).yHeadRot = yaw;
                ((LivingEntity) entity).yHeadRotO = yaw;
            }
            quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
            quaternion.mul(quaternion1);
        }
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conj();

        entityrenderdispatcher.overrideCameraOrientation(quaternion1);
        entityrenderdispatcher.setRenderShadow(true);
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> entityrenderdispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, posestack1, multibuffersource$buffersource, 15728880));
        multibuffersource$buffersource.endBatch();
        entityrenderdispatcher.setRenderShadow(false);
        entity.setYRot(0);
        entity.setXRot(0);
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).yBodyRot = 0;
            ((LivingEntity) entity).yHeadRotO = 0;
            ((LivingEntity) entity).yHeadRot = 0;
        }
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }

    protected void init() {
        super.init();
        playBookOpeningSound();
        addNextPreviousButtons();
        addLinkButtons();
    }

    private void addNextPreviousButtons() {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize + 128) / 2;
        this.buttonPreviousPage = this.addRenderableWidget(new BookPageButtonOld(this, k + 10, l + 180, false, (p_214208_1_) -> {
            this.onSwitchPage(false);
        }, true));
        this.buttonNextPage = this.addRenderableWidget(new BookPageButtonOld(this, k + 365, l + 180, true, (p_214205_1_) -> {
            this.onSwitchPage(true);
        }, true));
    }

    private void addLinkButtons() {
        this.renderables.clear();
        this.clearWidgets();
        addNextPreviousButtons();
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize + 128) / 2;

        for (LinkData linkData : links) {
            if (linkData.getPage() == this.currentPageCounter) {
                int maxLength = Math.max(100, Minecraft.getInstance().font.width(linkData.getTitleText()) + 20);
                yIndexesToSkip.add(new Whitespace(linkData.getPage(), linkData.getX() - maxLength / 2, linkData.getY(), 100, 20));
                this.addRenderableWidget(new Button(k + linkData.getX() - maxLength / 2, l + linkData.getY(), maxLength, 20, Component.translatable(linkData.getTitleText()), (p_213021_1_) -> {
                    prevPageJSON = this.currentPageJSON;
                    currentPageJSON = new ResourceLocation(getTextFileDirectory() + linkData.getLinkedPage());
                    preservedPageIndex = this.currentPageCounter;
                    currentPageCounter = 0;
                    addNextPreviousButtons();
                }));
            }
            if (linkData.getPage() > this.maxPagesFromPrinting) {
                this.maxPagesFromPrinting = linkData.getPage();
            }
        }

        for (EntityLinkData linkData : entityLinks) {
            if (linkData.getPage() == this.currentPageCounter) {
                yIndexesToSkip.add(new Whitespace(linkData.getPage(), linkData.getX() - 12, linkData.getY(), 100, 20));
                //this.addRenderableWidget(new LinkButton(this, linkData, k, l, (p_213021_1_) -> {
                //    prevPageJSON = this.currentPageJSON;
                //    currentPageJSON = new ResourceLocation(getTextFileDirectory() + linkData.getLinkedPage());
                //    preservedPageIndex = this.currentPageCounter;
                //    currentPageCounter = 0;
                //    addNextPreviousButtons();
                //}));
            }
            if (linkData.getPage() > this.maxPagesFromPrinting) {
                this.maxPagesFromPrinting = linkData.getPage();
            }
        }
    }

    private void onSwitchPage(boolean next) {
        if (next) {
            if (currentPageCounter < maxPagesFromPrinting) {
                currentPageCounter++;
            }
        } else {
            if (currentPageCounter > 0) {
                currentPageCounter--;
            } else {
                if (this.internalPage != null && !this.internalPage.getParent().isEmpty()) {
                    prevPageJSON = this.currentPageJSON;
                    currentPageJSON = new ResourceLocation(getTextFileDirectory() + this.internalPage.getParent());
                    currentPageCounter = preservedPageIndex;
                    preservedPageIndex = 0;
                }
            }
        }
        refreshSpacing();
    }

    @Override
    public void render(PoseStack matrixStack, int x, int y, float partialTicks) {
        this.mouseX = x;
        this.mouseY = y;
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int color = getBindingColor();
        int r = (color & 0xFF0000) >> 16;
        int g = (color & 0xFF00) >> 8;
        int b = (color & 0xFF);
        this.renderBackground(matrixStack);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize + 128) / 2;
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, getBookBindingTexture());
        BookBlit.setRGB(r, g, b, 255);
        BookBlit.blit(matrixStack, k, l, 0, 0, xSize, ySize, xSize, ySize);
        RenderSystem.setShaderTexture(0, getBookPageTexture());
        BookBlit.setRGB(255, 255, 255, 255);
        BookBlit.blit(matrixStack, k, l, 0, 0, xSize, ySize, xSize, ySize);
        if (internalPage == null || currentPageJSON != prevPageJSON || prevPageJSON == null) {
            internalPage = generatePage(currentPageJSON);
            if (internalPage != null) {
                refreshSpacing();
            }
        }
        if (internalPage != null) {
            writePageText(matrixStack, x, y);
        }
        prevPageJSON = currentPageJSON;
        super.render(matrixStack, x, y, partialTicks);
        if (internalPage != null) {
            matrixStack.pushPose();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            renderOtherWidgets(matrixStack, x, y, internalPage);
            matrixStack.popPose();
        }
        if (this.entityTooltip != null) {
            matrixStack.pushPose();
            matrixStack.translate(0, 0, 550);
            renderTooltip(matrixStack, Minecraft.getInstance().font.split(Component.translatable(entityTooltip), Math.max(this.width / 2 - 43, 170)), x, y);
            entityTooltip = null;
            matrixStack.popPose();
        }
    }

    private void refreshSpacing() {
        if (internalPage != null) {
            String lang = Minecraft.getInstance().getLanguageManager().getSelected().getCode().toLowerCase();
            currentPageText = new ResourceLocation(getTextFileDirectory() + lang + "/" + internalPage.getTextFileToReadFrom());
            boolean invalid = false;
            try {
                //test if it exists. if no exception, then the language is supported
                InputStream is = Minecraft.getInstance().getResourceManager().open(currentPageText);
                is.close();
            } catch (Exception e) {
                invalid = true;
                UnusualPrehistory.LOGGER.warn("Could not find language file for translation, defaulting to english");
                currentPageText = new ResourceLocation(getTextFileDirectory() + "en_us/" + internalPage.getTextFileToReadFrom());
            }

            readInPageWidgets(internalPage);
            addWidgetSpacing();
            addLinkButtons();
            readInPageText(currentPageText);
        }
    }

    private Item getItemByRegistryName(String registryName) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(registryName));
    }

    private Recipe getRecipeByName(String registryName) {
        try {
            RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
            if (manager.byKey(new ResourceLocation(registryName)).isPresent()) {
                return manager.byKey(new ResourceLocation(registryName)).get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addWidgetSpacing() {
        yIndexesToSkip.clear();
        for (ItemRenderData itemRenderData : itemRenders) {
            Item item = getItemByRegistryName(itemRenderData.getItem());
            if (item != null) {
                yIndexesToSkip.add(new Whitespace(itemRenderData.getPage(), itemRenderData.getX(), itemRenderData.getY(), (int) (itemRenderData.getScale() * 17), (int) (itemRenderData.getScale() * 15)));

            }
        }
        for (RecipeData recipeData : recipes) {
            Recipe recipe = getRecipeByName(recipeData.getRecipe());
            if (recipe != null) {
                yIndexesToSkip.add(new Whitespace(recipeData.getPage(), recipeData.getX(), recipeData.getY() - (int) (recipeData.getScale() * 15), (int) (recipeData.getScale() * 35), (int) (recipeData.getScale() * 60), true));
            }
        }
        for (ImageData imageData : images) {
            if (imageData != null) {
                yIndexesToSkip.add(new Whitespace(imageData.getPage(), imageData.getX(), imageData.getY(), (int) (imageData.getScale() * imageData.getWidth()), (int) (imageData.getScale() * imageData.getHeight() * 0.8F)));
            }
        }
        if (!writtenTitle.isEmpty()) {
            yIndexesToSkip.add(new Whitespace(0, 20, 5, 70, 15));
        }
    }

    private void renderOtherWidgets(PoseStack matrixStack, int x, int y, BookPage page) {
        int color = getBindingColor();
        int r = (color & 0xFF0000) >> 16;
        int g = (color & 0xFF00) >> 8;
        int b = (color & 0xFF);

        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize + 128) / 2;

        for (ImageData imageData : images) {
            if (imageData.getPage() == this.currentPageCounter) {
                if (imageData != null) {
                    ResourceLocation tex = textureMap.get(imageData.getTexture());
                    if (tex == null) {
                        tex = new ResourceLocation(imageData.getTexture());
                        textureMap.put(imageData.getTexture(), tex);
                    }
                    // yIndexesToSkip.put(imageData.getPage(), new Whitespace(imageData.getX(), imageData.getY(),(int) (imageData.getScale() * imageData.getWidth()), (int) (imageData.getScale() * imageData.getHeight() * 0.8F)));
                    float scale = (float) imageData.getScale();
                    matrixStack.pushPose();
                    matrixStack.translate(k + imageData.getX(), l + imageData.getY(), 0);
                    matrixStack.scale(scale, scale, scale);
                    RenderSystem.setShader(GameRenderer::getPositionTexShader);
                    RenderSystem.setShaderTexture(0, tex);
                    this.blit(matrixStack, 0, 0, imageData.getU(), imageData.getV(), imageData.getWidth(), imageData.getHeight());
                    matrixStack.popPose();
                }
            }
        }
        for(RecipeData recipeData : recipes){
            if (recipeData.getPage() == this.currentPageCounter) {
                matrixStack.pushPose();
                matrixStack.translate(k + recipeData.getX(), l + recipeData.getY(), 0);
                float scale = (float) recipeData.getScale();
                matrixStack.scale(scale, scale, scale);
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, getBookWidgetTexture());
                this.blit(matrixStack, 0, 0, 0, 88, 116, 53);
                matrixStack.popPose();
            }
        }
        for (RecipeData recipeData : recipes) {
            if (recipeData.getPage() == this.currentPageCounter) {
                Recipe recipe = getRecipeByName(recipeData.getRecipe());
                int playerTicks = Minecraft.getInstance().player.tickCount;
                if (recipe != null) {
                    float scale = (float) recipeData.getScale();
                    PoseStack poseStack = RenderSystem.getModelViewStack();

                    for (int i = 0; i < recipe.getIngredients().size(); i++) {
                        Ingredient ing = (Ingredient) recipe.getIngredients().get(i);
                        ItemStack stack = ItemStack.EMPTY;
                        if (!ing.isEmpty()) {
                            if (ing.getItems().length > 1) {
                                int currentIndex = (int) ((playerTicks / 20F) % ing.getItems().length);
                                stack = ing.getItems()[currentIndex];
                            } else {
                                stack = ing.getItems()[0];
                            }
                        }
                        if (!stack.isEmpty()) {
                            poseStack.pushPose();
                            poseStack.translate(k, l, 32.0F);
                            poseStack.translate((int) (recipeData.getX() + (i % 3) * 20 * scale), (int) (recipeData.getY() + (i / 3) * 20 * scale), 0);
                            poseStack.scale(scale, scale, scale);
                            this.itemRenderer.blitOffset = 100.0F;
                            this.itemRenderer.renderAndDecorateItem(stack, 0, 0);
                            this.itemRenderer.blitOffset = 0.0F;
                            poseStack.popPose();
                        }
                    }
                    poseStack.pushPose();
                    poseStack.translate(k, l, 32.0F);
                    float finScale = scale * 1.5F;
                    poseStack.translate(recipeData.getX() + 70 * finScale, recipeData.getY() + 10 * finScale, 0);
                    poseStack.scale(finScale, finScale, finScale);
                    this.itemRenderer.blitOffset = 100.0F;
                    this.itemRenderer.renderAndDecorateItem(recipe.getResultItem(), 0, 0);
                    this.itemRenderer.blitOffset = 0.0F;
                    poseStack.popPose();
                }
            }
        }

        for (EntityRenderData data : entityRenders) {
            if (data.getPage() == this.currentPageCounter) {
                Entity model = null;
                EntityType type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(data.getEntity()));
                if (type != null) {
                    model = renderedEntites.putIfAbsent(data.getEntity(), type.create(Minecraft.getInstance().level));
                }
                if (model != null) {
                    float scale = (float) data.getScale();
                    model.tickCount = Minecraft.getInstance().player.tickCount;
                    if(data.getEntityData() != null){
                        try {
                            CompoundTag tag = TagParser.parseTag(data.getEntityData());
                            model.load(tag);
                        } catch (CommandSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                    drawEntityOnScreen(matrixStack, k + data.getX(), l + data.getY(), 30 * scale, data.isFollow_cursor(), data.getRot_x(), data.getRot_y(), data.getRot_z(), mouseX, mouseY, model);
                }
            }
        }

        for (ItemRenderData itemRenderData : itemRenders) {
            if (itemRenderData.getPage() == this.currentPageCounter) {
                Item item = getItemByRegistryName(itemRenderData.getItem());
                if (item != null) {
                    float scale = (float) itemRenderData.getScale();
                    ItemStack stack = new ItemStack(item);
                    if (itemRenderData.getItemTag() != null && !itemRenderData.getItemTag().isEmpty()) {
                        CompoundTag tag = null;
                        try {
                            tag = TagParser.parseTag(itemRenderData.getItemTag());
                        } catch (CommandSyntaxException e) {
                            e.printStackTrace();
                        }
                        stack.setTag(tag);
                    }
                    this.itemRenderer.blitOffset = 100.0F;
                    matrixStack.pushPose();
                    PoseStack poseStack = RenderSystem.getModelViewStack();
                    poseStack.pushPose();
                    poseStack.translate(k, l, 0);
                    poseStack.scale(scale, scale, scale);
                    this.itemRenderer.renderAndDecorateItem(stack, itemRenderData.getX(), itemRenderData.getY());
                    this.itemRenderer.blitOffset = 0.0F;
                    poseStack.popPose();
                    matrixStack.popPose();
                    RenderSystem.applyModelViewMatrix();
                }
            }
        }
    }

    private void writePageText(PoseStack matrixStack, int x, int y) {
        Font font = this.font;
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize + 128) / 2;
        if (this.currentPageCounter == 0 && !writtenTitle.isEmpty()) {
            String actualTitle = I18n.get(writtenTitle);
            matrixStack.pushPose();
            float scale = 2F;
            if (font.width(actualTitle) > 80) {
                scale = 2.0F - Mth.clamp((font.width(actualTitle) - 80) * 0.011F, 0, 1.95F);
            }
            matrixStack.translate(k + 10, l + 10, 0);
            matrixStack.scale(scale, scale, 1.0F);
            font.draw(matrixStack, actualTitle, 0, 0, getTitleColor());
            matrixStack.popPose();
        }
        this.buttonNextPage.visible = currentPageCounter < maxPagesFromPrinting;
        boolean rootPage = currentPageJSON.equals(this.getRootPage());
        this.buttonPreviousPage.visible = currentPageCounter > 0 || !rootPage;
        for (LineData line : this.lines) {
            if (line.getPage() == this.currentPageCounter) {
                font.draw(matrixStack, line.getText(), k + 10 + line.getxIndex(), l + 10 + line.getyIndex() * 12, getTextColor());
            }
        }
    }

    public boolean isPauseScreen() {
        return false;
    }

    protected void playBookOpeningSound() {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
    }

    protected void playBookClosingSound() {
    }

    protected abstract int getBindingColor();

    protected int getWidgetColor() {
        return getBindingColor();
    }

    protected int getTextColor() {
        return 0X303030;
    }

    protected int getTitleColor() {
        return 0XBAAC98;
    }

    public abstract ResourceLocation getRootPage();

    public abstract String getTextFileDirectory();

    protected ResourceLocation getBookPageTexture() {
        return BOOK_PAGE_TEXTURE;
    }

    protected ResourceLocation getBookBindingTexture() {
        return BOOK_BINDING_TEXTURE;
    }

    protected ResourceLocation getBookWidgetTexture() {
        return BOOK_WIDGET_TEXTURE;
    }

    protected void playPageFlipSound() {
    }

    @Nullable
    protected BookPage generatePage(ResourceLocation res) {
        Optional<Resource> resource = null;
        BookPage page = null;
        try {
            resource = Minecraft.getInstance().getResourceManager().getResource(res);
            try {
                resource = Minecraft.getInstance().getResourceManager().getResource(res);
                if (resource.isPresent()) {
                    BufferedReader inputstream = resource.get().openAsReader();
                    page = BookPage.deserialize(inputstream);
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            return null;
        }
        return page;
    }


    protected void readInPageWidgets(BookPage page) {
        links.clear();
        itemRenders.clear();
        recipes.clear();
        entityRenders.clear();
        images.clear();
        entityLinks.clear();
        links.addAll(page.getLinkedButtons());
        entityLinks.addAll(page.getLinkedEntities());
        itemRenders.addAll(page.getItemRenders());
        recipes.addAll(page.getRecipes());
        entityRenders.addAll(page.getEntityRenders());
        images.addAll(page.getImages());
        writtenTitle = page.generateTitle();
    }

    protected void readInPageText(ResourceLocation res) {
        Resource resource = null;
        int xIndex = 0;
        int actualTextX = 0;
        int yIndex = 0;
        try {
            BufferedReader bufferedreader = Minecraft.getInstance().getResourceManager().openAsReader(res);
            try {
                List<String> readStrings = IOUtils.readLines(bufferedreader);
                this.linesFromJSON = readStrings.size();
                this.lines.clear();
                List<String> splitBySpaces = new ArrayList<>();
                for (String line : readStrings) {
                    splitBySpaces.addAll(Arrays.asList(line.split(" ")));
                }
                String lineToPrint = "";
                linesFromPrinting = 0;
                int page = 0;
                for (int i = 0; i < splitBySpaces.size(); i++) {
                    String word = splitBySpaces.get(i);
                    int cutoffPoint = xIndex > 100 ? 30 : 35;
                    boolean newline = word.equals("<NEWLINE>");
                    for (Whitespace indexes : yIndexesToSkip) {
                        int indexPage = indexes.getPage();
                        if (indexPage == page) {
                            int buttonX = indexes.getX();
                            int buttonY = indexes.getY();
                            int width = indexes.getWidth();
                            int height = indexes.getHeight();
                            if (indexes.isDown()) {
                                if (yIndex >= (buttonY) / 12F && yIndex <= (buttonY + height) / 12F) {
                                    if (buttonX < 90 && xIndex < 90 || buttonX >= 90 && xIndex >= 90) {
                                        yIndex += 2;
                                    }
                                }
                            } else {
                                if (yIndex >= (buttonY - height) / 12F && yIndex <= (buttonY + height) / 12F) {
                                    if (buttonX < 90 && xIndex < 90 || buttonX >= 90 && xIndex >= 90) {
                                        yIndex++;
                                    }
                                }
                            }
                        }
                    }
                    boolean last = i == splitBySpaces.size() - 1;
                    actualTextX += word.length() + 1;
                    if (lineToPrint.length() + word.length() + 1 >= cutoffPoint || newline) {
                        linesFromPrinting++;
                        if (yIndex > 13) {
                            if (xIndex > 0) {
                                page++;
                                xIndex = 0;
                                yIndex = 0;
                            } else {
                                xIndex = 200;
                                yIndex = 0;
                            }
                        }
                        if (last) {
                            lineToPrint = lineToPrint + " " + word;
                        }
                        this.lines.add(new LineData(xIndex, yIndex, lineToPrint, page));
                        yIndex++;
                        actualTextX = 0;
                        if (newline) {
                            yIndex++;
                        }
                        lineToPrint = "" + (word.equals("<NEWLINE>") ? "" : word);
                    } else {
                        lineToPrint = lineToPrint + " " + word;
                        if (last) {
                            linesFromPrinting++;
                            this.lines.add(new LineData(xIndex, yIndex, lineToPrint, page));
                            yIndex++;
                            actualTextX = 0;
                            if (newline) {
                                yIndex++;
                            }
                        }
                    }
                }
                maxPagesFromPrinting = page;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            UnusualPrehistory.LOGGER.warn("Could not load in page .txt from json from page, page: " + res);
        }
    }

    public void setEntityTooltip(String hoverText) {
        this.entityTooltip = hoverText;
    }
}
