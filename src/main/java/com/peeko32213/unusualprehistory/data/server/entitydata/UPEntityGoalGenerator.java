package com.peeko32213.unusualprehistory.data.server.entitydata;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.entity.goal.RabiesHuntGoalCodec;
import com.scouter.goalsmith.data.GoalData;
import com.scouter.goalsmith.data.targets.AllEntityTargetType;
import com.scouter.goalsmith.datagen.GoalDataBuilder;
import com.scouter.goalsmith.datagen.GoalDataProvider;
import net.minecraft.data.PackOutput;

import java.util.function.Consumer;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.modPrefix;

public class UPEntityGoalGenerator extends GoalDataProvider {
    public UPEntityGoalGenerator(PackOutput pOutput) {
        super(pOutput, UnusualPrehistory.MODID);
    }

    @Override
    protected void createGoalData(Consumer<GoalDataConsumer> consumer) {


        GoalData addGoalToAll = new GoalDataBuilder(new AllEntityTargetType())
                .addGoals(new RabiesHuntGoalCodec(-1)).build();

        consumer.accept(new GoalDataConsumer(modPrefix("add_rabies_and_jarate_goals") ,addGoalToAll));
    }
}
