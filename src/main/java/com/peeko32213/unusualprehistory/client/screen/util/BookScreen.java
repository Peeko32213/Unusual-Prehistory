package com.peeko32213.unusualprehistory.client.screen.util;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.*;
import com.peeko32213.unusualprehistory.common.entity.*;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseAquaticAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.*;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class BookScreen extends Screen {

    public static final ResourceLocation BOOK_LOCATION = prefix("textures/gui/book/book_pages.png");
    public static final ResourceLocation BOOK_BIND = prefix("textures/gui/book/book_binding.png");
    private static final ResourceLocation BOOK_WIDGET_TEXTURE = new ResourceLocation("unusualprehistory:textures/gui/book/widgets.png");



    private final List<EncyclopediaCodec> encyclopediaEntries = EncyclopediaJsonManager.getEncyclopediaEntries().values().stream().toList();
    private List<FormattedCharSequence> cachedPageComponents = Collections.emptyList();
    private List<FormattedCharSequence> nextPageComponents = Collections.emptyList();
    //private List<FormattedCharSequence> cachedPageComponents = Collections.emptyList();
    private EncyclopediaCodec currentEntry;
    private List<EncyclopediaPictureCodec> pictures;
    private BookPageButton buttonNextPage;
    private BookPageButton buttonPreviousPage;
    private int page = 0;
    protected int xSize = 390;
    protected int ySize = 320;
    protected int nextPageToStartNr;
    protected int nextPageToStartNrCache;
    protected int previousStartNrCache;
    private int currentPage;
    private ResourceLocation currentResourceLocation;
    private List<RecipeCodec> recipes;
    private Map<Integer, Integer> previousMap = new HashMap<>();
    private final List<EntityRenderDataCodec> entityRenders = new ArrayList<>();
    private final List<EntityLinkData> entityLinkData = new ArrayList<>();
    private final Map<String, Entity> renderedEntites = new HashMap<>();
    private final List<ItemRenderData> itemRenders = new ArrayList<>();
    private final List<LineData> lines = new ArrayList<>();
    protected int linesFromJSON = 0;
    protected int linesFromPrinting = 0;
    protected int maxPagesFromPrinting = 0;
    protected ResourceLocation currentPageText = null;
    private final List<Whitespace> yIndexesToSkip = new ArrayList<>();
    private String writtenTitle;
    private final List<LinkData> links = new ArrayList<>();
    private int mouseX;
    private int mouseY;
    private List<EntityIndexCodec> entityIndex;
    private String entityTooltip;
    public BookScreen(ResourceLocation resourceLocation, int currentPage) {
        super(Component.translatable("encyclopedia.title"));
        this.currentResourceLocation = resourceLocation;

        if (resourceLocation.equals(prefix("root"))) {
            this.currentEntry = EncyclopediaJsonManager.getRootPage();
            EncyclopediaJsonManager.getEncyclopediaEntries().values().forEach(encyclopediaCodec -> {
                encyclopediaCodec.getEntityButtons().forEach(entityRenderDataCodec -> {
                    this.entityLinkData.add(entityRenderDataCodec);
                });
            });

            Collections.sort(this.entityLinkData, Comparator.comparing(EntityLinkData::getEntity));
        } else {
            this.currentEntry = EncyclopediaJsonManager.getEncyclopediaEntries().get(resourceLocation);
        }
        if(this.currentEntry == null){
            this.currentEntry =  EncyclopediaJsonManager.getEncyclopediaEntries().get(prefix("help"));
        }

        this.entityRenders.clear();
        this.entityRenders.addAll(currentEntry.getEntityRenders());
        this.entityIndex = currentEntry.getEntityIndex();
        this.pictures = this.currentEntry.getPictures();
        this.currentPage = currentPage;
        this.recipes = currentEntry.getRecipes();
        this.writtenTitle = currentEntry.getTitle();
        this.links.clear();
        this.links.addAll(currentEntry.getLinkButtons());
        this.itemRenders.clear();
        this.itemRenders.addAll(currentEntry.getItemRenders());
    }

    @Override
    public void render(PoseStack matrixStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.mouseX = pMouseX;
        this.mouseY = pMouseY;
        int color = getBindingColor();
        int r = (color & 0xFF0000) >> 16;
        int g = (color & 0xFF00) >> 8;
        int b = (color & 0xFF);
        this.renderBackground(matrixStack);
        int i = (this.width - this.xSize) / 2;
        int p = (this.height - this.ySize + 128) / 2;
        int maxHeight = p + 162;
        int maxWidth = this.xSize + 30;
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, BOOK_BIND);
        BookBlit.setRGB(r, g, b, 255);
        BookBlit.blit(matrixStack, i, p, 0, 0, xSize, ySize, xSize, ySize);
        RenderSystem.setShaderTexture(0, BOOK_LOCATION);
        BookBlit.setRGB(255, 255, 255, 255);
        BookBlit.blit(matrixStack, i, p, 0, 0, xSize, ySize, xSize, ySize);

        for (EncyclopediaPictureCodec pictures : this.pictures) {
            if (pictures.getPageNr() != this.currentPage) continue;
            int offsetX = pictures.getxLocation();
            int offsetY = pictures.getyLocation();
            int xSize = pictures.getXSize();
            int ySize = pictures.getYSize();
            addPicture(matrixStack, offsetX, offsetY, xSize, ySize, pictures.getPictureLocation());
        }

        refreshSpacing();

        for (LineData line : this.lines) {
            if (line.getPage() == this.currentPage) {
                font.draw(matrixStack, line.getText(), i + 10 + line.getxIndex(), p + 10 + line.getyIndex() * 12, 1234567);
            }
        }

        for (LineData lines : lines) {
            if (this.nextPageToStartNrCache < lines.getPage()) {
                this.nextPageToStartNrCache = lines.getPage();
            }
        }
        if (this.currentResourceLocation.equals(prefix("root"))) {
            double dataSize = entityLinkData.size();
            for (EntityIndexCodec linkData : entityIndex) {
                double entriesPerPage = (linkData.getColums() * linkData.getRows()) * 2;
                double entriesFirstPage = (linkData.getColums() * linkData.getRows());
                double entries = dataSize / entriesPerPage;
                if (dataSize < entriesFirstPage) {
                    this.nextPageToStartNrCache = 0;
                } else {
                    this.nextPageToStartNrCache = linkData.getPageNr() + (int) Mth.ceil(entries);
                }
            }
        }
        addNextPreviousButtons();

        writePageText(matrixStack);
        super.render(matrixStack, pMouseX, pMouseY, pPartialTick);
        addWidgets(matrixStack);
        if (this.entityTooltip != null) {
            matrixStack.pushPose();
            matrixStack.translate(0, 0, 550);
            renderTooltip(matrixStack, Minecraft.getInstance().font.split(Component.translatable(entityTooltip), Math.max(this.width / 2 - 43, 170)), pMouseX, pMouseY);
            entityTooltip = null;
            matrixStack.popPose();
        }
        addNextPreviousButtons();
        addLinkButtons();
    }


    public ResourceLocation getBookWidgetTexture() {
        return BOOK_WIDGET_TEXTURE;
    }

    protected int getBindingColor() {
        return 0X6A3B16;
    }

    public int getWidgetColor() {
        return getBindingColor();
    }


    protected int getTextColor() {
        return 0X303030;
    }

    protected int getTitleColor() {
        return 0XBAAC98;
    }


    public String getTextFileDirectory() {
        return "unusualprehistory:encyclopedia/";
    }


    public void addPicture(PoseStack poseStack, int offsetX, int offsetY, int sizeX, int sizeY, ResourceLocation resourceLocation) {
        int i = (this.width - this.xSize) / 2;
        int p = (this.height - this.ySize + 128) / 2;
        RenderSystem.setShaderTexture(0, resourceLocation);
        BookBlit.blit(poseStack, i + offsetX, p + offsetY, 0, 0, sizeX, sizeY, sizeX, sizeY);
    }

    public boolean checkOffsets(int currentHeight, int currentWidth, int heightToSkipStart, int heightToSkipEnd, int widthToSkipStart, int widthToSkipEnd) {
        int i = (this.width - this.xSize) / 2;
        int p = (this.height - this.ySize + 128) / 2;
        int maxWidth = 385;
        boolean isHeightInRange = currentHeight >= p + heightToSkipStart && currentHeight <= p + heightToSkipEnd;
        if ((currentWidth < ((maxWidth / 2) - 10))) {
            if (widthToSkipStart > ((maxWidth / 2) - 10)) return false;
            boolean isWidthInRange = currentWidth <= i + widthToSkipStart;
            return isHeightInRange && isWidthInRange;
        } else if (currentWidth >= ((maxWidth / 2) - 10)) {
            if (widthToSkipStart < ((maxWidth / 2) - 10)) return false;
            boolean isWidthInRange = currentWidth <= i + widthToSkipStart;
            return isHeightInRange && isWidthInRange;
        }
        return false;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void addWidgets(PoseStack matrixStack) {
//
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize + 128) / 2;




        for (RecipeCodec recipeData : recipes) {
            if (recipeData.getPageNr() == this.currentPage) {
                matrixStack.pushPose();
                matrixStack.translate(k + recipeData.getXLocation(), l + recipeData.getYLocation(), 0);
                float scale = (float) recipeData.getScale();
                matrixStack.scale(scale, scale, scale);
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, getBookWidgetTexture());
                this.blit(matrixStack, 0, 0, 0, 88, 116, 53);
                matrixStack.popPose();
            }
        }
        for (RecipeCodec recipeData : recipes) {
            if (recipeData.getPageNr() == this.currentPage) {
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
                            poseStack.translate((int) (recipeData.getXLocation() + (i % 3) * 20 * scale), (int) (recipeData.getYLocation() + (i / 3) * 20 * scale), 0);
                            poseStack.scale(scale, scale, scale);
                            this.itemRenderer.blitOffset = 0.0F;
                            addNextPreviousButtons();
                            this.itemRenderer.renderAndDecorateItem(stack, 0, 0);
                            this.itemRenderer.blitOffset = 0.0F;
                            addNextPreviousButtons();
                            poseStack.popPose();
                        }
                    }
                    poseStack.pushPose();
                    poseStack.translate(k, l, 32.0F);
                    float finScale = scale * 1.5F;
                    poseStack.translate(recipeData.getXLocation() + 70 * finScale, recipeData.getYLocation() + 10 * finScale, 0);
                    poseStack.scale(finScale, finScale, finScale);
                    this.itemRenderer.blitOffset = 0.0F;
                    addNextPreviousButtons();
                    this.itemRenderer.renderAndDecorateItem(recipe.getResultItem(), 0, 0);
                    this.itemRenderer.blitOffset = 0.0F;
                    addNextPreviousButtons();
                    poseStack.popPose();
                }
            }
        }
        for (EntityRenderDataCodec data : entityRenders) {
            if (data.getPage() == this.currentPage) {
                Entity model = null;
                EntityType type = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(data.getEntity()));
                if (type != null) {
                    model = renderedEntites.putIfAbsent(data.getEntity(), type.create(Minecraft.getInstance().level));
                }
                if (model != null) {
                    float scale = (float) data.getScale();
                    model.tickCount = Minecraft.getInstance().player.tickCount;
                    if (!data.getEntityData().equals("")) {
                        try {
                            CompoundTag tag = TagParser.parseTag(data.getEntityData());
                            model.load(tag);
                        } catch (CommandSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                    drawEntityOnScreen(matrixStack, k + data.getX(), l + data.getY(), 30 * scale, data.isFollow_cursor(), data.getRot_x(), data.getRot_y(), data.getRot_z(), mouseX, mouseY, (LivingEntity) model);
                }
            }
        }


        for (ItemRenderData itemRenderData : itemRenders) {
            if (itemRenderData.getPage() == this.currentPage) {
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

//
    }

    private Item getItemByRegistryName(String registryName) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(registryName));
    }
    public static void drawEntityOnScreen(PoseStack stackIn, int posX, int posY, float scale, boolean follow, double xRot, double yRot, double zRot, float mouseX, float mouseY, LivingEntity entity) {
        //Ew Todo Fix this mess some day
        if(entity instanceof EntityBaseDinosaurAnimal dinosaurAnimal) {
            dinosaurAnimal.setIsFromBook(true);
        }
        if(entity instanceof EntityTameableBaseDinosaurAnimal dinosaurAnimal) {
            dinosaurAnimal.setIsFromBook(true);
        }
        if(entity instanceof EntityDunkleosteus dinosaurAnimal) {
            dinosaurAnimal.setIsFromBook(true);
        }
        if(entity instanceof EntityEncrusted dinosaurAnimal) {
            dinosaurAnimal.setIsFromBook(true);
        }

        if(entity instanceof EntityAmmonite dinosaurAnimal) {
            dinosaurAnimal.setIsFromBook(true);
        }

        if(entity instanceof EntityScaumenacia dinosaurAnimal) {
            dinosaurAnimal.setIsFromBook(true);
        }

        if(entity instanceof EntityStethacanthus dinosaurAnimal) {
            dinosaurAnimal.setIsFromBook(true);
        }

        if(entity instanceof EntityEryon dinosaurAnimal) {
            dinosaurAnimal.setIsFromBook(true);
        }

        if(entity instanceof EntityAnurognathus dinosaurAnimal) {
            dinosaurAnimal.setIsFromBook(true);
        }

        if(entity instanceof EntityBaseAquaticAnimal dinosaurAnimal) {
            dinosaurAnimal.setIsFromBook(true);
        }


        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        posestack1.translate(posX, posY, 120.0D);
        posestack1.scale((float)scale, (float)scale, (float)scale);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(0.0F);
        quaternion.mul(quaternion1);
        posestack1.mulPose(quaternion);
        posestack1.mulPose(Vector3f.XP.rotationDegrees((float) xRot - 55F));
        posestack1.mulPose(Vector3f.YP.rotationDegrees((float) yRot - 10));
        posestack1.mulPose(Vector3f.ZP.rotationDegrees((float) zRot));

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
        ((LivingEntity) entity).yBodyRot = 0;
        ((LivingEntity) entity).yHeadRotO = 0;
        ((LivingEntity) entity).yHeadRot = 0;

        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }

    public static void renderEntityInInventoryRaw(int pPosX, int pPosY, double xRot, double yRot, double zRot, int pScale, float angleXComponent, float angleYComponent, LivingEntity pLivingEntity) {
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        //posestack.translate((double)pPosX, (double)pPosY, 1050.0D);
        posestack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        posestack1.translate(pPosY, pPosY, 120.0D);

        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(0.0F);
        quaternion.mul(quaternion1);
        posestack1.mulPose(quaternion);
        posestack1.mulPose(Vector3f.XP.rotationDegrees((float) xRot - 65));
        posestack1.mulPose(Vector3f.YP.rotationDegrees((float) yRot));
        posestack1.mulPose(Vector3f.ZP.rotationDegrees((float) zRot));


        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conj();
        entityrenderdispatcher.overrideCameraOrientation(quaternion1);
        entityrenderdispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(pLivingEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, posestack1, multibuffersource$buffersource, 15728880);
        });
        multibuffersource$buffersource.endBatch();
        entityrenderdispatcher.setRenderShadow(true);
        pLivingEntity.yBodyRot = 0;
        pLivingEntity.setYRot(0);
        pLivingEntity.setXRot(0);
        pLivingEntity.yHeadRotO = 0;
        pLivingEntity.yHeadRot = 0;
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }

    private void addLinkButtons() {
        this.renderables.clear();
        this.clearWidgets();
        addNextPreviousButtons();
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize + 128) / 2;

        for (LinkData linkData : links) {
            if (linkData.getPage() == this.currentPage) {
                int maxLength = Math.max(100, Minecraft.getInstance().font.width(linkData.getTitleText()) + 20);
                yIndexesToSkip.add(new Whitespace(linkData.getPage(), linkData.getX() - maxLength / 2, linkData.getY(), 100, 20));
                this.addRenderableWidget(new Button(k + linkData.getX() - maxLength / 2, l + linkData.getY(), maxLength, 20, Component.translatable(linkData.getTitleText()), (p_213021_1_) -> {
                    Minecraft.getInstance().setScreen(new BookScreen(new ResourceLocation(linkData.linked_page), 0));
                    addNextPreviousButtons();
                }));
            }
            if (linkData.getPage() > this.maxPagesFromPrinting) {
                this.maxPagesFromPrinting = linkData.getPage();
            }
        }
        if (this.currentResourceLocation.equals(prefix("root"))) {
            int rowCount = 0;
            int columnCount = 0;


            EntityIndexCodec entityIndex = this.entityIndex.get(0);
            int linkDataSize = this.entityLinkData.size();
            int startingEntity = 0;
            if (this.currentPage != entityIndex.getPageNr()) {

                int startingSize = entityIndex.getColums() * entityIndex.getRows();

                if (this.currentPage == entityIndex.getPageNr() + 1) {
                    startingEntity = linkDataSize - (linkDataSize - (startingSize * (currentPage - entityIndex.getPageNr())));
                } else {
                    startingEntity = linkDataSize - (linkDataSize - ((startingSize * (currentPage - entityIndex.getPageNr() * 2) * 2)));
                }

                if (startingEntity < 0) {
                    return;
                }
            }
            int startingOffsetX = 16;
            int startingOffsetY = 16;
            if (this.currentPage == entityIndex.getPageNr()) {
                startingOffsetX += entityIndex.getxLocation();
            }
            startingOffsetY += entityIndex.getyLocation();
            if (currentPage < entityIndex.getPageNr()) {
                return;
            }
            for (int i = startingEntity; i < linkDataSize; i++) {
                EntityLinkData linkData = this.entityLinkData.get(i);
                if (rowCount == entityIndex.getRows()) {
                    break;
                }
                if (this.currentPage != entityIndex.getPageNr()) {
                    if (i > (startingEntity + (entityIndex.getColums() * entityIndex.getRows() * 2))) {
                        break;
                    }
                }
                yIndexesToSkip.add(new Whitespace(this.currentPage, (int) (startingOffsetX + (columnCount * 24 * linkData.getScale() - 12)), (int) (startingOffsetY + l + rowCount * 24 * linkData.getScale()), 100, 20));
                this.addRenderableWidget(new LinkButton(this, linkData, (int) (startingOffsetX + k + (columnCount * 24 * linkData.getScale())), (int) (startingOffsetY + l + (rowCount * 24 * linkData.getScale())), (p_213021_1_) -> {
                    Minecraft.getInstance().setScreen(new BookScreen(new ResourceLocation(linkData.linked_page), 0));
                    addNextPreviousButtons();
                }));


                if (linkData.getPage() > this.maxPagesFromPrinting) {
                    this.maxPagesFromPrinting = linkData.getPage();
                }
                columnCount++;


                if (columnCount == entityIndex.getColums()) {
                    rowCount++;
                    columnCount = 0;
                }

                if (entityIndex.getPageNr() != currentPage && rowCount >= entityIndex.getRows()) {
                    startingOffsetX = 16 + 200;
                    columnCount = 0;
                    rowCount = 0;
                }
                if (rowCount == entityIndex.getRows()) {
                    break;
                }
            }
        }
    }


    private Recipe getRecipeByName(ResourceLocation registryName) {
        try {
            RecipeManager manager = Minecraft.getInstance().level.getRecipeManager();
            if (manager.byKey(registryName).isPresent()) {
                return manager.byKey(registryName).get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void refreshSpacing() {
        if (this.currentEntry != null) {
            String lang = Minecraft.getInstance().getLanguageManager().getSelected().getCode().toLowerCase();
            currentPageText = new ResourceLocation(getTextFileDirectory() + lang + "/" + this.currentEntry.getTextLocation());
            boolean invalid = false;
            try {
                //test if it exists. if no exception, then the language is supported
                InputStream is = Minecraft.getInstance().getResourceManager().open(currentPageText);
                is.close();
            } catch (Exception e) {
                invalid = true;
                UnusualPrehistory.LOGGER.warn("Could not find language file for translation, defaulting to english");
                currentPageText = new ResourceLocation(getTextFileDirectory() + "en_us/" + this.currentEntry.getTextLocation());
            }
            addNextPreviousButtons();
            addWidgetSpacing();
            readInPageText(currentPageText);
        }
    }



    private void writePageText(PoseStack matrixStack) {
        Font font = this.font;
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize + 128) / 2;
        if (this.currentPage == 0 && !writtenTitle.isEmpty()) {
            String actualTitle = I18n.get(writtenTitle);
            matrixStack.pushPose();
            float scale = 2F;
            if (font.width(actualTitle) > 80) {
                scale = 2.0F - Mth.clamp((font.width(actualTitle) - 80) * 0.012F, 0, 1.95F);
            }
            matrixStack.translate(k + 10, l + 10, 0);
            matrixStack.scale(scale, scale, 1.0F);
            font.draw(matrixStack, actualTitle, 0, 0, getTextColor());
            matrixStack.popPose();
        }

        for (LineData line : this.lines) {
            if (line.getPage() == this.currentPage) {
                font.draw(matrixStack, line.getText(), k + 10 + line.getxIndex(), l + 10 + line.getyIndex() * 12, getTextColor());
            }
        }
    }


    private void addWidgetSpacing() {
        yIndexesToSkip.clear();
        for (ItemRenderData itemRenderData : itemRenders) {
            Item item = getItemByRegistryName(itemRenderData.getItem());
            if (item != null) {
                yIndexesToSkip.add(new Whitespace(itemRenderData.getPage(), itemRenderData.getX(), itemRenderData.getY(), (int) (itemRenderData.getScale() * 17), (int) (itemRenderData.getScale() * 15)));
            }
        }
        for (RecipeCodec recipeData : recipes) {
            Recipe recipe = getRecipeByName(recipeData.getRecipe());
            if (recipe != null) {
                yIndexesToSkip.add(new Whitespace(recipeData.getPageNr(), recipeData.getXLocation(), recipeData.getYLocation() - (int) (recipeData.getScale() * 15), (int) (recipeData.getScale() * 35), (int) (recipeData.getScale() * 60), true));
            }
        }
        for (EncyclopediaPictureCodec imageData : this.pictures) {
            if (imageData != null) {
                yIndexesToSkip.add(new Whitespace(imageData.getPageNr(), imageData.getxLocation(), imageData.getyLocation(), imageData.getXSize(), imageData.getYSize() - 9));
            }
        }
        if (!this.currentEntry.getTitle().isEmpty()) {
            yIndexesToSkip.add(new Whitespace(0, 20, 5, 70, 15));
        }
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
                        this.lines.add(new LineData(xIndex, yIndex, lineToPrint.trim().strip(), page));
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
                            this.lines.add(new LineData(xIndex, yIndex, lineToPrint.trim().strip(), page));
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


    private void addNextPreviousButtons() {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize + 128) / 2;

        if (this.currentPage < this.nextPageToStartNrCache) {
            this.buttonNextPage = this.addRenderableWidget(new BookPageButton(this, k + 365, l + 180, true, (p_214205_1_) -> {
                this.onSwitchPage(true);
            }, true));
        }

        if (!(this.currentPage <= 0 && this.currentResourceLocation.equals(prefix("root")))) {
            this.buttonPreviousPage = this.addRenderableWidget(new BookPageButton(this, k + 10, l + 180, false, (p_214208_1_) -> {
                this.onSwitchPage(false);
            }, true));
        }
        if (!this.currentResourceLocation.equals(prefix("root"))) {
            this.buttonPreviousPage = this.addRenderableWidget(new BookPageButton(this, k + 10, l + 180, false, (p_214208_1_) -> {
                this.onSwitchPage(false);
            }, true));
        }


    }

    private void onSwitchPage(boolean next) {
        if (next) {
            this.previousMap.put(this.currentPage, this.nextPageToStartNr);
            Minecraft.getInstance().setScreen(new BookScreen(this.currentResourceLocation, this.currentPage + 1));
        } else {
            if (this.currentResourceLocation.equals(prefix("root"))) {
                Minecraft.getInstance().setScreen(new BookScreen(this.currentResourceLocation, this.currentPage - 1));
                return;
            }
            if (this.currentPage == 0) {
                Minecraft.getInstance().setScreen(new BookScreen(prefix("root"), 0));
                return;
            }
            Minecraft.getInstance().setScreen(new BookScreen(this.currentResourceLocation, 0));


        }
    }

    public void setEntityTooltip(String hoverText) {
        this.entityTooltip = hoverText;
    }
}
