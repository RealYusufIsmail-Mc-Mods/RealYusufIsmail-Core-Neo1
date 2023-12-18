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
package io.github.realyusufismail.realyusufismailcore.data;

import io.github.realyusufismail.realyusufismailcore.data.lang.ModEnLangProvider;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.data.client.ModBlockStateProvider;
import io.github.realyusufismail.realyusufismailcore.data.loot.ModLootTables;
import io.github.realyusufismail.realyusufismailcore.data.recipe.ModRecipeProvider;
import io.github.realyusufismail.realyusufismailcore.data.tags.ModBlockTagsProvider;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;


public class DataGenerators {
    private DataGenerators() {}

    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        gen.addProvider(true, new ModBlockStateProvider(gen, existingFileHelper));
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper, lookup);
        gen.addProvider(true, blockTags);
        gen.addProvider(true, new ModEnLangProvider(gen));
        gen.addProvider(true, new ModRecipeProvider(gen));
        gen.addProvider(true, new ModLootTables(gen));
        gen.addProvider(true,
                new PackMetadataGenerator(gen.getPackOutput()).add(PackMetadataSection.TYPE,
                        new PackMetadataSection(Component.literal("Armour and Tools Mod Resources"),
                                DetectedVersion.BUILT_IN.getPackVersion(PackType.CLIENT_RESOURCES),
                                Arrays.stream(PackType.values())
                                    .collect(Collectors.toMap(Function.identity(),
                                            DetectedVersion.BUILT_IN::getPackVersion)))));
    }
}
