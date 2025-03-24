package com.peeko32213.unusualprehistory.core.registry.entities;

import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricAquaticEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.base.old.PrehistoricEntityOld;
import com.peeko32213.unusualprehistory.common.entity.custom.base.old.TamablePrehistoricEntityOld;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.AmmoniteEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.BeelzebufoTadpoleEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.ScaumenaciaEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.aquatic.StethacanthusEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.AnurognathusEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.monster.EncrustedEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class UPEntityPlacement {
    public  static void entityPlacement() {
        SpawnPlacements.register(UPEntities.MAJUNGA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntityOld::checkSurfaceDinoSpawnRules);
        //Does not have bright enough to spawn check for now!
        SpawnPlacements.register(UPEntities.ANURO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AnurognathusEntity::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.BEELZ.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntityOld::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.BEELZE_TADPOLE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BeelzebufoTadpoleEntity::checkSurfaceWaterDinoSpawnRules);

        SpawnPlacements.register(UPEntities.AMMON.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AmmoniteEntity::checkSurfaceWaterDinoSpawnRules);
//        SpawnPlacements.register(UPEntities.PALAEOPHIS.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PalaeophisEntity::checkSurfaceWaterDinoSpawnRules);
        SpawnPlacements.register(UPEntities.COTY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntity::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.SCAU.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ScaumenaciaEntity::checkSurfaceWaterDinoSpawnRules);
        SpawnPlacements.register(UPEntities.TRICERATOPS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntity::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.PACHY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntityOld::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.BRACHI.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntity::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.VELOCIRAPTOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntity::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.TYRANNOSAURUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntity::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.PARACERATHERIUM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntityOld::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.BARINASUCHUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntity::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.MEGATHERIUM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntity::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.SMILODON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntity::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.MAMMOTH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntityOld::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.MEGALANIA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntityOld::checkSurfaceDinoSpawnRules);

        //Does not have bright enough to spawn check for now!
        SpawnPlacements.register(UPEntities.ERYON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntityOld::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.AUSTRO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntityOld::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.ANTARCO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntityOld::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.ULUG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntity::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.KENTRO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntityOld::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.HWACHA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntity::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.GIGANTOPITHICUS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntityOld::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.TALPANAS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PrehistoricEntityOld::checkSurfaceDinoSpawnRules);
        SpawnPlacements.register(UPEntities.ENCRUSTED.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EncrustedEntity::checkSurfaceDinoSpawnRules);

    }
}
