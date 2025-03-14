package com.peeko32213.unusualprehistory.datagen.entitydata;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.entity.goal.JarateFindWaterGoalCodec;
import com.peeko32213.unusualprehistory.common.data.entity.goal.RabiesHuntGoalCodec;
import com.peeko32213.unusualprehistory.common.entity.util.goal.JarateFindWaterGoal;
import com.scouter.goalsmith.data.GoalData;
import com.scouter.goalsmith.data.targets.AllEntityTargetType;
import com.scouter.goalsmith.data.targets.TagTargetType;
import com.scouter.goalsmith.datagen.GoalDataBuilder;
import com.scouter.goalsmith.datagen.GoalDataProvider;
import com.scouter.goalsmith.util.GSTags;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;

import java.util.function.Consumer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class EntityGoalGenerator extends GoalDataProvider {
    public EntityGoalGenerator(PackOutput pOutput) {
        super(pOutput, UnusualPrehistory.MODID);
    }

    @Override
    protected void createGoalData(Consumer<GoalDataConsumer> consumer) {


        GoalData addGoalToAll = new GoalDataBuilder(new AllEntityTargetType())
                .addGoals(new JarateFindWaterGoalCodec(-1), new RabiesHuntGoalCodec(-1)).build();

        consumer.accept(new GoalDataConsumer(prefix("add_rabies_and_jarate_goals") ,addGoalToAll));
    }
}
