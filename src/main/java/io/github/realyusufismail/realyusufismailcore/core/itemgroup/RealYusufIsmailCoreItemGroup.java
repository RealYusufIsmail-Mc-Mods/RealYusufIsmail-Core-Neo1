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
package io.github.realyusufismail.realyusufismailcore.core.itemgroup;

import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore;
import io.github.realyusufismail.realyusufismailcore.core.init.ItemInitCore;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RealYusufIsmailCoreItemGroup {
    public static DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RealYusufIsmailCore.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> REALYUSUFISMAILCORE =
            CREATIVE_MODE_TABS.register(
                    "realyusufismailcore_tab", () -> createCreativeTabBuilder(CreativeModeTab.builder()));

    private static CreativeModeTab createCreativeTabBuilder(CreativeModeTab.Builder builder) {
        builder.displayItems((itemDisplayParameters, output) -> {
            BlockInitCore.BLOCKS.getEntries().stream()
                    .map(block -> block.get().asItem())
                    .forEach(output::accept);

            ItemInitCore.ITEMS.getEntries().stream()
                    .map(item -> item.get().asItem())
                    .forEach(output::accept);
        });
        builder.icon(() -> new ItemStack(BlockInitCore.LEGACY_SMITHING_TABLE.get()));
        builder.title(Component.translatable("creativetab.realyusufismailcore"));

        return builder.build();
    }
}
