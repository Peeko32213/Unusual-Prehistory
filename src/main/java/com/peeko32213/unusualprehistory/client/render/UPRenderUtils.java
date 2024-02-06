package com.peeko32213.unusualprehistory.client.render;

import com.peeko32213.unusualprehistory.client.render.dinosaur_renders.DinosaurRenderer;
import com.peeko32213.unusualprehistory.client.render.dinosaur_renders.TameableDinosaurRenderer;
import com.peeko32213.unusualprehistory.client.render.layer.BaseDinosaurSaddleLayer;
import com.peeko32213.unusualprehistory.client.render.layer.ItemHoldingLayer;
import com.peeko32213.unusualprehistory.client.render.layer.JebLayer;
import com.peeko32213.unusualprehistory.client.render.layer.TamableDinosaurSaddleLayer;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityTameableBaseDinosaurAnimal;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.ArrayList;
import java.util.List;

public class UPRenderUtils {
    public static <T extends EntityTameableBaseDinosaurAnimal> TamableDinosaurRendererBuilder<T> createTamableDinosaurRenderer(EntityRendererProvider.Context context, GeoModel<T> model) {
        return new TamableDinosaurRendererBuilder<>(context, model);

    }

    public static <T extends EntityBaseDinosaurAnimal> DinosaurRendererBuilder<T> createDinosaurRenderer(EntityRendererProvider.Context context, GeoModel<T> model) {
        return new DinosaurRendererBuilder<>(context, model);
    }

    public static class TamableDinosaurRendererBuilder<T extends EntityTameableBaseDinosaurAnimal> {
        private final EntityRendererProvider.Context context;
        private final GeoModel<T> model;
        private final TameableDinosaurRenderer<T> dinoRenderer;
        private final List<GeoRenderLayer<T>> layerFactories = new ArrayList<>();
        private ResourceLocation modelLocation;

        private TamableDinosaurRendererBuilder(EntityRendererProvider.Context context, GeoModel<T> model) {
            this.context = context;
            this.model = model;
            this.dinoRenderer = new TameableDinosaurRenderer<>(context, model);
        }

        public TamableDinosaurRendererBuilder<T> withLayers(ResourceLocation modelLocation) {
            this.modelLocation = modelLocation;
            return this;
        }

        public TamableDinosaurRendererBuilder<T> withSaddleLayer(ResourceLocation saddleOverlay) {
            checkLayers();
            layerFactories.add(new TamableDinosaurSaddleLayer<>(dinoRenderer, saddleOverlay, modelLocation));
            return this;
        }

        public TamableDinosaurRendererBuilder<T> withJebLayer(ResourceLocation jebOverlay) {
            checkLayers();
            layerFactories.add(new JebLayer<>(dinoRenderer, jebOverlay, modelLocation));
            return this;
        }

        // Add more layer methods here if needed

        public TameableDinosaurRenderer<T> build() {
            checkLayers();
            for (GeoRenderLayer<T> factory : layerFactories) {
                dinoRenderer.addRenderLayer(factory);
            }
            return dinoRenderer;
        }

        private void checkLayers() {
            if (modelLocation == null) {
                throw new IllegalStateException("withLayers() must be called before adding other layers.");
            }
        }
    }

    public static class DinosaurRendererBuilder<T extends EntityBaseDinosaurAnimal> {
        private final EntityRendererProvider.Context context;
        private final GeoModel<T> model;
        private final DinosaurRenderer<T> dinoRenderer;
        private final List<GeoRenderLayer<T>> layerFactories = new ArrayList<>();
        private ResourceLocation modelLocation;

        public DinosaurRendererBuilder(EntityRendererProvider.Context context, GeoModel<T> model) {
            this.context = context;
            this.model = model;
            this.dinoRenderer = new DinosaurRenderer<>(context, model);
        }

        public DinosaurRendererBuilder<T> withSaddleLayer(ResourceLocation saddleOverlay) {
            checkLayers();
            layerFactories.add(new BaseDinosaurSaddleLayer<>(dinoRenderer, saddleOverlay, modelLocation));
            return this;
        }

        public DinosaurRendererBuilder<T> withItemHoldingLayer() {
            layerFactories.add(new ItemHoldingLayer<>(dinoRenderer));
            return this;
        }

        public DinosaurRendererBuilder<T> withLayers(ResourceLocation modelLocation) {
            this.modelLocation = modelLocation;
            return this;
        }

        //public DinosaurRendererBuilder<T> withjebLayer(ResourceLocation saddleOverlay, ResourceLocation modelLocation) {
        //    layerFactories.add(new JebLayer<>(dinoRenderer, saddleOverlay, modelLocation));
        //    return this;
        //}

        // Add more layer methods here if needed

        public DinosaurRenderer<T> build() {
            for (GeoRenderLayer<T> factory : layerFactories) {
                dinoRenderer.addRenderLayer(factory);
            }
            return dinoRenderer;
        }

        private void checkLayers() {
            if (modelLocation == null) {
                throw new IllegalStateException("withLayers() must be called before adding other layers.");
            }
        }
    }
}
