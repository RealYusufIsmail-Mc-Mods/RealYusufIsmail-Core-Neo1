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
package io.github.realyusufismail.realyusufismailcore.data;

import static io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore.logger;

import io.github.realyusufismail.realyusufismailcore.data.client.ModBlockStateProvider;
import io.github.realyusufismail.realyusufismailcore.data.lang.ModEnLangProvider;
import io.github.realyusufismail.realyusufismailcore.data.loot.ModLootTables;
import io.github.realyusufismail.realyusufismailcore.data.recipe.ModRecipeProvider;
import io.github.realyusufismail.realyusufismailcore.data.tags.ModBlockTagsProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {
    private DataGenerators() {}

    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        try {
            gen.addProvider(true, new ModBlockStateProvider(gen, existingFileHelper));
            ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper, lookup);
            gen.addProvider(true, blockTags);
            gen.addProvider(true, new ModEnLangProvider(gen));
            gen.addProvider(true, new ModRecipeProvider(gen, lookup));
            gen.addProvider(true, new ModLootTables(gen.getPackOutput(), lookup));
        } catch (RuntimeException e) {
            logger.error("Error while generating data", e);
        }
    }
}
