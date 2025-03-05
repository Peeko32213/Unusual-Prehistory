package com.peeko32213.unusualprehistory.client.render;

import com.peeko32213.unusualprehistory.client.render.prehistoric.PrehistoricRenderer;
import com.peeko32213.unusualprehistory.client.render.prehistoric.TameableDinosaurRenderer;
import com.peeko32213.unusualprehistory.client.render.layer.PrehistoricSaddleLayer;
import com.peeko32213.unusualprehistory.client.render.layer.ItemHoldingLayer;
import com.peeko32213.unusualprehistory.client.render.layer.JebLayer;
import com.peeko32213.unusualprehistory.client.render.layer.TamablePrehistoricSaddleLayer;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.base.TamablePrehistoricEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.ArrayList;
import java.util.List;

public class UPRenderUtils {
    public static <T extends TamablePrehistoricEntity> TamableDinosaurRendererBuilder<T> createTamableDinosaurRenderer(EntityRendererProvider.Context context, GeoModel<T> model) {
        return new TamableDinosaurRendererBuilder<>(context, model);

    }

    public static <T extends PrehistoricEntity> DinosaurRendererBuilder<T> createDinosaurRenderer(EntityRendererProvider.Context context, GeoModel<T> model) {
        return new DinosaurRendererBuilder<>(context, model);
    }

    public static class TamableDinosaurRendererBuilder<T extends TamablePrehistoricEntity> {
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
            layerFactories.add(new TamablePrehistoricSaddleLayer<>(dinoRenderer, saddleOverlay, modelLocation));
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

    public static class DinosaurRendererBuilder<T extends PrehistoricEntity> {
        private final EntityRendererProvider.Context context;
        private final GeoModel<T> model;
        private final PrehistoricRenderer<T> dinoRenderer;
        private final List<GeoRenderLayer<T>> layerFactories = new ArrayList<>();
        private ResourceLocation modelLocation;

        public DinosaurRendererBuilder(EntityRendererProvider.Context context, GeoModel<T> model) {
            this.context = context;
            this.model = model;
            this.dinoRenderer = new PrehistoricRenderer<>(context, model);
        }

        public DinosaurRendererBuilder<T> withSaddleLayer(ResourceLocation saddleOverlay) {
            checkLayers();
            layerFactories.add(new PrehistoricSaddleLayer<>(dinoRenderer, saddleOverlay, modelLocation));
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

        public PrehistoricRenderer<T> build() {
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
