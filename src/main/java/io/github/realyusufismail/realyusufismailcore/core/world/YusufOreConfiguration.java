/*
 * Copyright 2024 RealYusufIsmail.
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package io.github.realyusufismail.realyusufismailcore.core.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Taken from
 *
 * @see OreConfiguration
 */
@SuppressWarnings("unused")
public class YusufOreConfiguration implements FeatureConfiguration {
    public static final Codec<YusufOreConfiguration> CODEC = RecordCodecBuilder.create(create -> create.group(
                    Codec.list(TargetBlockState.CODEC).fieldOf("targets").forGetter(getter -> getter.targetStates),
                    Codec.intRange(0, 64).fieldOf("size").forGetter(getter -> getter.size),
                    Codec.floatRange(0.0F, 1.0F)
                            .fieldOf("discard_chance_on_air_exposure")
                            .forGetter(getter -> getter.discardChanceOnAirExposure))
            .apply(create, YusufOreConfiguration::new));
    public final List<YusufOreConfiguration.TargetBlockState> targetStates;
    public final int size;
    public final float discardChanceOnAirExposure;

    public YusufOreConfiguration(
            List<YusufOreConfiguration.TargetBlockState> targetStates, int size, float discardChanceOnAirExposure) {
        this.size = size;
        this.targetStates = targetStates;
        this.discardChanceOnAirExposure = discardChanceOnAirExposure;
    }

    public YusufOreConfiguration(List<YusufOreConfiguration.TargetBlockState> targetStates, int size) {
        this(targetStates, size, 0.0F);
    }

    public YusufOreConfiguration(RuleTest ruleTest, BlockState blockState, int size, float discardChanceOnAirExposure) {
        this(
                List.of(new YusufOreConfiguration.TargetBlockState(ruleTest, blockState)),
                size,
                discardChanceOnAirExposure);
    }

    public YusufOreConfiguration(RuleTest ruleTest, BlockState blockState, int size) {
        this(List.of(new YusufOreConfiguration.TargetBlockState(ruleTest, blockState)), size, 0.0F);
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static YusufOreConfiguration.@NotNull TargetBlockState target(RuleTest ruleTest, BlockState blockState) {
        return new YusufOreConfiguration.TargetBlockState(ruleTest, blockState);
    }

    public static class TargetBlockState {
        public static final Codec<YusufOreConfiguration.TargetBlockState> CODEC =
                RecordCodecBuilder.create(create -> create.group(
                                RuleTest.CODEC.fieldOf("target").forGetter(getter -> getter.target),
                                BlockState.CODEC.fieldOf("state").forGetter(getter -> getter.state))
                        .apply(create, TargetBlockState::new));
        public final RuleTest target;
        public final BlockState state;

        TargetBlockState(RuleTest ruleTest, BlockState blockState) {
            this.target = ruleTest;
            this.state = blockState;
        }
    }
}
