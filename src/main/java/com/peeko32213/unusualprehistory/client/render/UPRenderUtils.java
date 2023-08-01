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
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

import java.util.ArrayList;
import java.util.List;

public class UPRenderUtils {
    public static <T extends EntityTameableBaseDinosaurAnimal> TamableDinosaurRendererBuilder<T> createTamableDinosaurRenderer(EntityRendererProvider.Context context, AnimatedGeoModel<T> model) {
        return new TamableDinosaurRendererBuilder<>(context, model);
    }

    public static <T extends EntityBaseDinosaurAnimal> DinosaurRendererBuilder<T> createDinosaurRenderer(EntityRendererProvider.Context context, AnimatedGeoModel<T> model) {
        return new DinosaurRendererBuilder<>(context, model);
    }

   //public static class TamableDinosaurRendererBuilder<T extends EntityTameableBaseDinosaurAnimal> {
   //    private final EntityRendererProvider.Context context;
   //    private final AnimatedGeoModel<T> model;
   //    private final TameableDinosaurRenderer<T> dinoRenderer;
   //    private final List<GeoLayerRenderer<T>> layerFactories = new ArrayList<>();

   //    public TamableDinosaurRendererBuilder(EntityRendererProvider.Context context, AnimatedGeoModel<T> model) {
   //        this.context = context;
   //        this.model = model;
   //        this.dinoRenderer = new TameableDinosaurRenderer<>(context, model);
   //    }

   //    public TamableDinosaurRendererBuilder<T> withSaddleLayer(ResourceLocation saddleOverlay, ResourceLocation modelLocation) {
   //        layerFactories.add(new TamableDinosaurSaddleLayer<>(dinoRenderer, saddleOverlay, modelLocation));
   //        return this;
   //    }

   //    public TamableDinosaurRendererBuilder<T> withjebLayer(ResourceLocation jebOverlay, ResourceLocation modelLocation) {
   //        layerFactories.add(new JebLayer<>(dinoRenderer, jebOverlay, modelLocation));
   //        return this;
   //    }

   //    public TamableDinosaurRendererBuilder<T> withCustomLayer(GeoLayerRenderer<T> customLayer) {
   //        layerFactories.add(customLayer);
   //        return this;
   //    }
   //
   //    // Add more layer methods here if needed

   //    public TameableDinosaurRenderer<T> build() {
   //        for (GeoLayerRenderer<T> factory : layerFactories) {
   //            dinoRenderer.addLayer(factory);
   //        }
   //        return dinoRenderer;
   //    }
   //}

    public static class TamableDinosaurRendererBuilder<T extends EntityTameableBaseDinosaurAnimal> {
        private final EntityRendererProvider.Context context;
        private final AnimatedGeoModel<T> model;
        private final TameableDinosaurRenderer<T> dinoRenderer;
        private final List<GeoLayerRenderer<T>> layerFactories = new ArrayList<>();
        private ResourceLocation modelLocation;

        private TamableDinosaurRendererBuilder(EntityRendererProvider.Context context, AnimatedGeoModel<T> model) {
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
            for (GeoLayerRenderer<T> factory : layerFactories) {
                dinoRenderer.addLayer(factory);
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
        private final AnimatedGeoModel<T> model;
        private final DinosaurRenderer<T> dinoRenderer;
        private final List<GeoLayerRenderer<T>> layerFactories = new ArrayList<>();
        private ResourceLocation modelLocation;

        public DinosaurRendererBuilder(EntityRendererProvider.Context context, AnimatedGeoModel<T> model) {
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
            for (GeoLayerRenderer<T> factory : layerFactories) {
                dinoRenderer.addLayer(factory);
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
