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
package io.github.realyusufismail.realyusufismailcore.data.client;

import io.github.realyusufismail.realyusufismailcore.core.init.BlockInitCore;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen.getPackOutput(), RealYusufIsmailCore.MOD_ID, exFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "RealYusufIsmail Core - Block States/Models";
    }

    @Override
    protected void registerStatesAndModels() {
        customSmithingTable(BlockInitCore.LEGACY_SMITHING_TABLE.get());
    }

    protected void customSmithingTable(Block block) {
        ResourceLocation name = ForgeRegistries.BLOCKS.getKey(block);

        if (name == null) {
            throw new NullPointerException("Block " + block + " has null name");
        }

        BlockModelBuilder builder = models().withExistingParent(name.getPath(), "block/cube");

        builder.texture("down", modLoc("block/" + name.getPath() + "_bottom"));
        builder.texture("east", modLoc("block/" + name.getPath() + "_side"));
        builder.texture("north", modLoc("block/" + name.getPath() + "_front"));
        builder.texture("particle", modLoc("block/" + name.getPath() + "_front"));
        builder.texture("south", modLoc("block/" + name.getPath() + "_side"));
        builder.texture("up", modLoc("block/" + name.getPath() + "_top"));
        builder.texture("west", modLoc("block/" + name.getPath() + "_side"));
        simpleBlockItem(block, builder);
        simpleBlock(block, builder);
    }

}
