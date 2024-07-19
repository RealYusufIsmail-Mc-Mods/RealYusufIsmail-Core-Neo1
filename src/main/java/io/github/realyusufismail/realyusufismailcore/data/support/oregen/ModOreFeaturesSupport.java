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
package io.github.realyusufismail.realyusufismailcore.data.support.oregen;

import java.util.List;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public abstract class ModOreFeaturesSupport {
    protected RuleTest ruleTest1 = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    protected RuleTest ruleTest2 = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    protected RuleTest ruleTest3 = new BlockMatchTest(Blocks.NETHERRACK);
    protected RuleTest ruleTest4 = new TagMatchTest(BlockTags.BASE_STONE_NETHER);

    public abstract void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context);

    private static void registerOre(
            BootstrapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> ore,
            List<OreConfiguration.TargetBlockState> targetBlockStates,
            int size) {
        context.register(ore, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(targetBlockStates, size)));
    }

    private static void registerOre(
            BootstrapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> ore,
            RuleTest ruleTest,
            BlockState blockState,
            int size) {
        context.register(ore, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ruleTest, blockState, size)));
    }

    protected static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name, String modId) {
        return ResourceKey.create(
                Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(modId, name.toLowerCase()));
    }
}
