package com.peeko32213.unusualprehistory.client.screen.util;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Axis;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.screen.LinkPlantButton;
import com.peeko32213.unusualprehistory.client.screen.PlantLinkData;
import com.peeko32213.unusualprehistory.common.data.*;
import com.peeko32213.unusualprehistory.common.entity.IBookEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.NonNullList;
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
import net.minecraftforge.client.gui.widget.ExtendedButton;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.io.IOUtils;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

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
    private final List<PlantLinkData> plantLinkData = new ArrayList<>();
    private final Map<String, Entity> renderedEntites = new HashMap<>();
    private final List<ItemRenderData> itemRenders = new ArrayList<>();
    private final List<LineData> lines = new ArrayList<>();
    private String pageToGo = "root";
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
    private List<PlantIndexCodec> plantIndex;
    private String entityTooltip;
    public BookScreen(ResourceLocation resourceLocation, int currentPage) {
        super(Component.translatable("encyclopedia.title"));
        this.currentResourceLocation = resourceLocation;

        if (resourceLocation.equals(prefix("root"))) {
            this.currentEntry = EncyclopediaJsonManager.getRootPage();
           //EncyclopediaJsonManager.getEncyclopediaEntries().values().forEach(encyclopediaCodec -> {
           //    encyclopediaCodec.getEntityButtons().forEach(entityRenderDataCodec -> {
           //        this.entityLinkData.add(entityRenderDataCodec);
           //    });
           //});

           //Collections.sort(this.entityLinkData, Comparator.comparing(EntityLinkData::getEntity));
        }else {
            this.currentEntry = EncyclopediaJsonManager.getEncyclopediaEntries().get(resourceLocation);
        }
        if (resourceLocation.equals(prefix("plants"))) {
            EncyclopediaJsonManager.getEncyclopediaEntries().values().forEach(encyclopediaCodec -> {
                encyclopediaCodec.getPlantButtons().forEach(plantLinkData -> {
                    this.plantLinkData.add(plantLinkData);
                });
            });

            Collections.sort(this.plantLinkData, Comparator.comparing(PlantLinkData::getPlant));
        }
        if (resourceLocation.equals(prefix("dinosaurs"))) {
            EncyclopediaJsonManager.getEncyclopediaEntries().values().forEach(encyclopediaCodec -> {
                encyclopediaCodec.getEntityButtons().forEach(entityRenderDataCodec -> {
                    this.entityLinkData.add(entityRenderDataCodec);
                });
            });

            Collections.sort(this.entityLinkData, Comparator.comparing(EntityLinkData::getEntity));
        }
        if(this.currentEntry == null){
            this.currentEntry =  EncyclopediaJsonManager.getEncyclopediaEntries().get(prefix("help"));
        }
        this.pageToGo = this.currentEntry.getPageToGo();
        this.entityRenders.clear();
        this.entityRenders.addAll(currentEntry.getEntityRenders());
        this.entityIndex = currentEntry.getEntityIndex();
        this.plantIndex = currentEntry.getPlantIndex();
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
    public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float partialTicks) {
        this.mouseX = pMouseX;
        this.mouseY = pMouseY;
        int color = getBindingColor();
        int r = (color & 0xFF0000) >> 16;
        int g = (color & 0xFF00) >> 8;
        int b = (color & 0xFF);
        this.renderBackground(guiGraphics);
        int i = (this.width - this.xSize) / 2;
        int p = (this.height - this.ySize + 128) / 2;

        int maxHeight = p + 162;
        int maxWidth = this.xSize + 30;
        //RenderSystem.enableBlend();
        //RenderSystem.setShaderTexture(0, BOOK_BIND);
        BookBlit.blitWithColor(guiGraphics,BOOK_BIND , i, p, 0.0F, 0.0F, this.xSize, this.ySize, this.xSize, this.ySize, r, g, b, 255);
        BookBlit.blitWithColor(guiGraphics, BOOK_LOCATION, i, p, 0.0F, 0.0F, this.xSize, this.ySize, this.xSize, this.ySize, 255, 255, 255, 255);

        for (EncyclopediaPictureCodec pictures : this.pictures) {
            if (pictures.getPageNr() != this.currentPage) continue;
            int offsetX = pictures.getxLocation();
            int offsetY = pictures.getyLocation();
            int xSize = pictures.getXSize();
            int ySize = pictures.getYSize();
            addPicture(guiGraphics, offsetX, offsetY, xSize, ySize, pictures.getPictureLocation());
        }

        refreshSpacing();

        for (LineData line : this.lines) {
            if (line.getPage() == this.currentPage) {
                guiGraphics.drawString(font, line.getText(), i + 10 + line.getxIndex(), p + 10 + line.getyIndex() * 12, getTextColor(), false);
            }
        }

        for (LineData lines : lines) {
            if (this.nextPageToStartNrCache < lines.getPage()) {
                this.nextPageToStartNrCache = lines.getPage();
            }
        }
        if (this.currentResourceLocation.equals(prefix("dinosaurs"))) {
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

        if (this.currentResourceLocation.equals(prefix("plants"))) {
            double dataSize = plantLinkData.size();
            for (PlantIndexCodec linkData : plantIndex) {
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

        writePageText(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, partialTicks);
        addWidgets(guiGraphics);
        if (this.entityTooltip != null) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0, 0, 550);
            guiGraphics.renderTooltip(font, Minecraft.getInstance().font.split(Component.translatable(entityTooltip), Math.max(this.width / 2 - 43, 170)), pMouseX, pMouseY);
            entityTooltip = null;
            guiGraphics.pose().popPose();
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


    /**
     * Adds a picture to the book page.
     *
     * @param graphics The matrix stack for rendering transformations.
     * @param offsetX The X offset for the picture.
     * @param offsetY The Y offset for the picture.
     * @param sizeX The width of the picture.
     * @param sizeY The height of the picture.
     * @param resourceLocation The resource location of the picture.
     */
    public void addPicture(GuiGraphics graphics, int offsetX, int offsetY, int sizeX, int sizeY, ResourceLocation resourceLocation) {
        int i = (this.width - this.xSize) / 2;
        int p = (this.height - this.ySize + 128) / 2;
        BookBlit.blitWithColor(graphics, resourceLocation, i + offsetX, p + offsetY, 0, 0, sizeX, sizeY, sizeX, sizeY,0,0);
    }

    /**
     * Checks if the given coordinates are within the specified offsets.
     *
     * @param currentHeight The current Y coordinate.
     * @param currentWidth The current X coordinate.
     * @param heightToSkipStart The starting height to skip.
     * @param heightToSkipEnd The ending height to skip.
     * @param widthToSkipStart The starting width to skip.
     * @param widthToSkipEnd The ending width to skip.
     * @return Whether the coordinates are within the specified offsets.
     */
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

    /**
     * Adds widgets, such as recipe displays and entity renders, to the book page.
     *
     * @param graphics The matrix stack for rendering transformations.
     */
    private void addWidgets(GuiGraphics graphics) {
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize + 128) / 2;
        PoseStack matrixStack = graphics.pose();
        // Iterate over recipe data and render recipe displays
        for (RecipeCodec recipeData : recipes) {
            if (recipeData.getPageNr() == this.currentPage) {
                matrixStack.pushPose();
                matrixStack.translate(k + recipeData.getXLocation(), l + recipeData.getYLocation(), 0);
                float scale = (float) recipeData.getScale();
                matrixStack.scale(scale, scale, scale);
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                graphics.blit(getBookWidgetTexture(), 0, 0, 0, 88, 116, 53);
                matrixStack.popPose();
            }
        }

        // Iterate over recipe data again and render ingredients and result items
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
                    graphics.pose().pushPose();
                    graphics.pose().translate(k, l, 0);
                    graphics.pose().scale(scale, scale, scale);
                    graphics.renderItem(stack, itemRenderData.getX(), itemRenderData.getY());
                    graphics.pose().popPose();
                }
            }
        }

        // Iterate over entity render data and render entity models
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
                    if (data.getEntityData() != null && !data.getEntityData().equals("")) {
                        try {
                            CompoundTag tag = TagParser.parseTag(data.getEntityData());
                            model.load(tag);
                        } catch (CommandSyntaxException e) {
                            e.printStackTrace();
                        }
                    }

                    drawEntityOnScreen(graphics, k + data.getX(), l + data.getY(), 30 * scale, data.isFollow_cursor(), data.getRot_x(), data.getRot_y(), data.getRot_z(), mouseX, mouseY, (LivingEntity) model);
                }
            }
        }
        for (RecipeCodec recipeData : recipes) {
            if (recipeData.getPageNr() == this.currentPage) {
                Recipe recipe = getRecipeByName(recipeData.getRecipe());
                if (recipe != null) {
                    renderRecipe(graphics, recipe, recipeData, k, l);
                }
            }
        }
        // Iterate over item render data and render item models
        for (ItemRenderData itemRenderData : itemRenders) {
            if (itemRenderData.getPage() == this.currentPage) {
                //drawItemOnScreen(graphics, k, l, (float) itemRenderData.getScale(), false, 0, 0, 0, 0, 0, itemRenderData);
            }
        }
    }

    protected void renderRecipe(GuiGraphics guiGraphics, Recipe recipe, RecipeCodec recipeData, int k, int l) {
        int playerTicks = Minecraft.getInstance().player.tickCount;
        float scale = (float) recipeData.getScale();
        NonNullList<Ingredient> ingredients =  recipe.getIngredients();
        NonNullList<ItemStack> displayedStacks = NonNullList.create();

        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ing = ingredients.get(i);
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
                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate(k, l, 32.0F);
                guiGraphics.pose().translate((int) (recipeData.getXLocation() + (i % 3) * 20 * scale), (int) (recipeData.getYLocation() + (i / 3) * 20 * scale), 0);
                guiGraphics.pose().scale(scale, scale, scale);
                guiGraphics.renderItem(stack, 0, 0);
                guiGraphics.pose().popPose();
            }
            displayedStacks.add(i, stack);
        }
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(k, l, 32.0F);
        float finScale = scale * 1.5F;
        guiGraphics.pose().translate(recipeData.getXLocation() + 70 * finScale, recipeData.getYLocation() + 10 * finScale, 0);
        guiGraphics.pose().scale(finScale, finScale, finScale);
        ItemStack result = recipe.getResultItem(Minecraft.getInstance().level.registryAccess());
        guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
        guiGraphics.renderItem(result, 0, 0);
        guiGraphics.pose().popPose();
    }

    /**
     * Retrieves an item from the registry based on its registry name.
     *
     * @param registryName The registry name of the item.
     * @return The corresponding Item instance.
     */
    private static Item getItemByRegistryName(String registryName) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(registryName));
    }
    /**
     * Draws an item on the screen at the specified position with given attributes.
     *
     * @param graphics The matrix stack for rendering transformations.
     * @param posX The X position to render the item.
     * @param posY The Y position to render the item.
     * @param scale The scaling factor for the item.
     * @param follow Whether the item should follow the cursor.
     * @param xRot The X rotation of the item.
     * @param yRot The Y rotation of the item.
     * @param zRot The Z rotation of the item.
     * @param mouseX The current X position of the mouse.
     * @param mouseY The current Y position of the mouse.
     * @param itemRenderData The data for rendering the item.
     */
    public static void drawItemOnScreen(GuiGraphics graphics, int posX, int posY, float scale, boolean follow, double xRot, double yRot, double zRot, float mouseX, float mouseY, ItemRenderData itemRenderData){
        PoseStack matrixStack = graphics.pose();
        Item item = BookScreen.getItemByRegistryName(itemRenderData.getItem());
        if (item != null) {;
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
            matrixStack.pushPose();
            PoseStack poseStack = RenderSystem.getModelViewStack();
            poseStack.pushPose();
            poseStack.translate(posX, posY, 0);
            poseStack.scale(scale, scale, scale);
            graphics.renderItem(stack, itemRenderData.getX(), itemRenderData.getY());
            poseStack.popPose();
            matrixStack.popPose();
            RenderSystem.applyModelViewMatrix();
        }
    }
    /**
     * Draws an entity on the screen at the specified position with given attributes.
     *
     * @param posX The X position to render the entity.
     * @param posY The Y position to render the entity.
     * @param scale The scaling factor for the entity.
     * @param follow Whether the entity should follow the cursor.
     * @param xRot The X rotation of the entity.
     * @param yRot The Y rotation of the entity.
     * @param zRot The Z rotation of the entity.
     * @param mouseX The current X position of the mouse.
     * @param mouseY The current Y position of the mouse.
     * @param entity The LivingEntity instance to render.
     */
    public static void drawEntityOnScreen(GuiGraphics graphics, int posX, int posY, float scale, boolean follow, double xRot, double yRot, double zRot, float mouseX, float mouseY, LivingEntity entity) {

        if(entity instanceof IBookEntity bookEntity) {
            bookEntity.setFromBook(true);
        }

        PoseStack posestack =  graphics.pose();
        posestack.pushPose();
        posestack.translate((float)posX, (float)posY, 100);
        posestack.mulPoseMatrix((new Matrix4f()).scaling(scale, scale, -scale));
        Quaternionf quaternion = Axis.ZP.rotationDegrees(180.0F);
        Quaternionf quaternion1 = Axis.XP.rotationDegrees(0.0F);
        quaternion.mul(quaternion1);
        posestack.mulPose(quaternion);
        posestack.mulPose(Axis.XP.rotationDegrees((float) xRot + 125));
        posestack.mulPose(Axis.YP.rotationDegrees((float) yRot + 100));
        posestack.mulPose(Axis.ZP.rotationDegrees((float) zRot));
        posestack.mulPose(quaternion);

        Vector3f light0 = (new Vector3f(1.0F, -1.0F, -1.0F)).normalize();
        Vector3f light1 = (new Vector3f(-1.0F, 1.0F, 1.0F)).normalize();
        RenderSystem.setShaderLights(light0, light1);
        //if(entity instanceof EntityPlant) {
        //    Lighting.setupForFlatItems();
        //} else {
        //    Lighting.setupForEntityInInventory();
        //}

        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conjugate();

        entityrenderdispatcher.overrideCameraOrientation(quaternion1);
        entityrenderdispatcher.setRenderShadow(true);

        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> entityrenderdispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, posestack, multibuffersource$buffersource, 15728880));

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

    /**
     * Copy
     * **/
    private static final Quaternionf ENTITY_ROTATION = (new Quaternionf()).rotationXYZ((float) Math.toRadians(30), (float) Math.toRadians(130), (float) Math.PI);
    public static void drawEntityOnScreenCopy(GuiGraphics graphics, int posX, int posY, float scale, boolean follow, double xRot, double yRot, double zRot, float mouseX, float mouseY, LivingEntity entity) {

        if(entity instanceof IBookEntity bookEntity) {
            bookEntity.setFromBook(true);
        }

        graphics.pose().pushPose();
        graphics.pose().translate((double)posX, (double)posY, 50.0D);
        graphics.pose().mulPoseMatrix((new Matrix4f()).scaling(scale, scale,  (-scale)));
        graphics.pose().mulPose(ENTITY_ROTATION);

        Vector3f light0 = new Vector3f(1, -1.0F, -1.0F).normalize();
        Vector3f light1 = new Vector3f(-1, 1.0F, 1.0F).normalize();
        RenderSystem.setShaderLights(light0, light1);
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        entityrenderdispatcher.setRenderShadow(false);
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, graphics.pose(), graphics.bufferSource(), 15728880);
        });
        graphics.flush();
        entityrenderdispatcher.setRenderShadow(true);
        graphics.pose().popPose();
        Lighting.setupFor3DItems();
    }


    /**
     * Adds link buttons to the screen based on the current page and index data.
     */
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
                this.addRenderableWidget(new ExtendedButton(k + linkData.getX() - maxLength / 2, l + linkData.getY(), maxLength, 20, Component.translatable(linkData.getTitleText()) , (p_213021_1_) -> {
                    Minecraft.getInstance().setScreen(new BookScreen(new ResourceLocation(linkData.linked_page), 0));
                    addNextPreviousButtons();
                }));
            }
            if (linkData.getPage() > this.maxPagesFromPrinting) {
                this.maxPagesFromPrinting = linkData.getPage();
            }
        }
        if (this.currentResourceLocation.equals(prefix("dinosaurs"))) {
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
                this.addRenderableWidget(new LinkButton(this, linkData, (int) (startingOffsetX + k + (columnCount * 24 * linkData.getScale())), (int) (startingOffsetY + l + (rowCount * 24 * linkData.getScale())),(p_213021_1_) -> {
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



        if (this.currentResourceLocation.equals(prefix("plants"))) {
            int rowCount = 0;
            int columnCount = 0;


            PlantIndexCodec plantIndex = this.plantIndex.get(0);
            int linkDataSize = this.plantLinkData.size();
            int startingEntity = 0;
            if (this.currentPage != plantIndex.getPageNr()) {

                int startingSize = plantIndex.getColums() * plantIndex.getRows();

                if (this.currentPage == plantIndex.getPageNr() + 1) {
                    startingEntity = linkDataSize - (linkDataSize - (startingSize * (currentPage - plantIndex.getPageNr())));
                } else {
                    startingEntity = linkDataSize - (linkDataSize - ((startingSize * (currentPage - plantIndex.getPageNr() * 2) * 2)));
                }

                if (startingEntity < 0) {
                    return;
                }
            }
            int startingOffsetX = 16;
            int startingOffsetY = 16;
            if (this.currentPage == plantIndex.getPageNr()) {
                startingOffsetX += plantIndex.getxLocation();
            }
            startingOffsetY += plantIndex.getyLocation();
            if (currentPage < plantIndex.getPageNr()) {
                return;
            }
            for (int i = startingEntity; i < linkDataSize; i++) {
                PlantLinkData linkData = this.plantLinkData.get(i);
                if (rowCount == plantIndex.getRows()) {
                    break;
                }
                if (this.currentPage != plantIndex.getPageNr()) {
                    if (i > (startingEntity + (plantIndex.getColums() * plantIndex.getRows() * 2))) {
                        break;
                    }
                }
                yIndexesToSkip.add(new Whitespace(this.currentPage, (int) (startingOffsetX + (columnCount * 24 * linkData.getScale() - 12)), (int) (startingOffsetY + l + rowCount * 24 * linkData.getScale()), 100, 20));
                this.addRenderableWidget(new LinkPlantButton(this, linkData, (int) (startingOffsetX + k + (columnCount * 24 * linkData.getScale())), (int) (startingOffsetY + l + (rowCount * 24 * linkData.getScale())), (p_213021_1_) -> {
                    Minecraft.getInstance().setScreen(new BookScreen(new ResourceLocation(linkData.linked_page), 0));
                    addNextPreviousButtons();
                }));


                if (linkData.getPage() > this.maxPagesFromPrinting) {
                    this.maxPagesFromPrinting = linkData.getPage();
                }
                columnCount++;


                if (columnCount == plantIndex.getColums()) {
                    rowCount++;
                    columnCount = 0;
                }

                if (plantIndex.getPageNr() != currentPage && rowCount >= plantIndex.getRows()) {
                    startingOffsetX = 16 + 200;
                    columnCount = 0;
                    rowCount = 0;
                }
                if (rowCount == plantIndex.getRows()) {
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

    /**
     * Refreshes the spacing and layout of widgets based on the current entry's language,
     * adds next and previous buttons, widget spacing, and reads in page text.
     */
    private void refreshSpacing() {
        if (this.currentEntry != null) {
            String lang = Minecraft.getInstance().getLanguageManager().getSelected().toLowerCase();
            currentPageText = new ResourceLocation(getTextFileDirectory() + lang + "/" + this.currentEntry.getTextLocation());
            boolean invalid = false;
            try {
                // Test if the language file exists. If no exception is thrown, then the language is supported.
                InputStream is = Minecraft.getInstance().getResourceManager().open(currentPageText);
                is.close();
            } catch (Exception e) {
                invalid = true;
                UnusualPrehistory.LOGGER.warn("Could not find language file for translation, defaulting to English");
                currentPageText = new ResourceLocation(getTextFileDirectory() + "en_us/" + this.currentEntry.getTextLocation());
            }
            addNextPreviousButtons();
            addWidgetSpacing();
            readInPageText(currentPageText);
        }
    }

    /**
     * Draws the page text on the screen using the specified matrix stack and font.
     *
     * @param guiGraphics The graphics used for rendering.
     */

    protected void writePageText(GuiGraphics guiGraphics) {
        Font font = this.font;
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize + 128) / 2;
        for (LineData line : this.lines) {
            if (line.getPage() == this.currentPage) {
                guiGraphics.drawString(font, line.getText(), k + 10 + line.getxIndex(), l + 10 + line.getyIndex() * 12, getTextColor(), false);
            }
        }
        if (this.currentPage == 0 && !writtenTitle.isEmpty()) {
            String actualTitle = I18n.get(writtenTitle);
            guiGraphics.pose().pushPose();
            float scale = 2F;
            if (font.width(actualTitle) > 80) {
                scale = 2.0F - Mth.clamp((font.width(actualTitle) - 80) * 0.011F, 0, 1.95F);
            }
            guiGraphics.pose().translate(k + 10, l + 10, 0);
            guiGraphics.pose().scale(scale, scale, scale);
            guiGraphics.drawString(font, actualTitle, 0, 0, getTitleColor(), false);
            guiGraphics.pose().popPose();
        }
    }

    /**
     * Adds widget spacing based on items, recipes, pictures, and entry titles.
     * Stores whitespace information in yIndexesToSkip.
     */
    private void addWidgetSpacing() {
        yIndexesToSkip.clear();

        // Add widget spacing for item renders
        for (ItemRenderData itemRenderData : itemRenders) {
            Item item = getItemByRegistryName(itemRenderData.getItem());
            if (item != null) {
                yIndexesToSkip.add(new Whitespace(itemRenderData.getPage(), itemRenderData.getX(), itemRenderData.getY(),
                        (int) (itemRenderData.getScale() * 17), (int) (itemRenderData.getScale() * 15)));
            }
        }

        // Add widget spacing for recipes
        for (RecipeCodec recipeData : recipes) {
            Recipe recipe = getRecipeByName(recipeData.getRecipe());
            if (recipe != null) {
                yIndexesToSkip.add(new Whitespace(recipeData.getPageNr(), recipeData.getXLocation(), recipeData.getYLocation() - (int) (recipeData.getScale() * 15),
                        (int) (recipeData.getScale() * 35), (int) (recipeData.getScale() * 60), true));
            }
        }

        // Add widget spacing for pictures
        for (EncyclopediaPictureCodec imageData : this.pictures) {
            if (imageData != null) {
                yIndexesToSkip.add(new Whitespace(imageData.getPageNr(), imageData.getxLocation(), imageData.getyLocation(),
                        imageData.getXSize(), imageData.getYSize() - 9));
            }
        }

        // Add widget spacing for entry titles
        if (!this.currentEntry.getTitle().isEmpty()) {
            yIndexesToSkip.add(new Whitespace(0, 20, 5, 70, 15));
        }
    }


    /**
     * Reads and processes page text from a specified resource location.
     * The text is split into lines and added to the 'lines' list for rendering.
     *
     * @param res The resource location of the page text.
     */
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

    /**
     * Adds next and previous buttons for navigation.
     * The buttons' positions are calculated based on the screen dimensions.
     */
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

    /**
     * Handles page navigation when switching to the next or previous page.
     *
     * @param next True if navigating to the next page, false if navigating to the previous page.
     */
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
                Minecraft.getInstance().setScreen(new BookScreen(new ResourceLocation(this.pageToGo), 0));
                return;
            }
            Minecraft.getInstance().setScreen(new BookScreen(this.currentResourceLocation, 0));


        }
    }

    public void setEntityTooltip(String hoverText) {
        this.entityTooltip = hoverText;
    }
}
