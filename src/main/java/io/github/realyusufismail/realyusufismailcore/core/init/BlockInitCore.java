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
package io.github.realyusufismail.realyusufismailcore.core.init;


import io.github.realyusufismail.realyusufismailcore.blocks.LegacySmithingTable;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;

import java.util.function.Supplier;

import static io.github.realyusufismail.realyusufismailcore.core.init.ItemInitCore.ITEMS;

public class BlockInitCore {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, RealYusufIsmailCore.MOD_ID);

    public static final RegistryObject<LegacySmithingTable> LEGACY_SMITHING_TABLE = registerSpecial(
            "legacy_smithing_table",
            () -> new LegacySmithingTable(BlockBehaviour.Properties.copy(Blocks.SMITHING_TABLE)));

    private static <T extends Block> RegistryObject<T> registerSpecial(String name,
            Supplier<T> supplier) {
        RegistryObject<T> blockReg = BLOCKS.register(name, supplier);
        ITEMS.register(name, () -> new BlockItem(blockReg.get(), new Item.Properties()));
        return blockReg;
    }

    private static RegistryObject<GeneralBlock> register(String name,
            Supplier<GeneralBlock> supplier) {
        RegistryObject<GeneralBlock> blockReg = BLOCKS.register(name, supplier);
        ITEMS.register(name, () -> new BlockItem(blockReg.get(), new Item.Properties()));
        return blockReg;
    }

    private static RegistryObject<GeneralBlock> register(String name, Block existingBlock) {
        return register(name,
                () -> new GeneralBlock(BlockBehaviour.Properties.copy(existingBlock)));
    }
}
