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
package io.github.realyusufismail.realyusufismailcore.data.support.loot;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import org.jetbrains.annotations.NotNull;

public abstract class ModLootTablesSupport extends LootTableProvider {
    public ModLootTablesSupport(
            PackOutput packOutput,
            CompletableFuture<HolderLookup.Provider> pRegistries,
            List<SubProviderEntry> enabledSubProviders) {
        super(packOutput, Set.of(), enabledSubProviders, pRegistries);
    }

    @Override
    public abstract void validate(
            @NotNull WritableRegistry<LootTable> writableRegistry,
            @NotNull ValidationContext validationContext,
            ProblemReporter.@NotNull Collector problemReporterCollector);
}
