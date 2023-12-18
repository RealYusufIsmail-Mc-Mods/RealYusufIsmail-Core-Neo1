/*
 * Copyright 2023 RealYusufIsmail.
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
package io.github.realyusufismail.realyusufismailcore.data.loot;


import io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore.*;

public class ModBlockLootTables extends BlockLootSubProvider {


    protected ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(LEGACY_SMITHING_TABLE.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues()
            .stream()
            .filter(entry -> Optional.ofNullable(ForgeRegistries.BLOCKS.getKey(entry))
                .filter(key -> key.getNamespace().equals(RealYusufIsmailCore.MOD_ID))
                .isPresent())
            .collect(Collectors.toSet());
    }
}
