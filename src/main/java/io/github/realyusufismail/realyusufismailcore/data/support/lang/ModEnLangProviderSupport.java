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
package io.github.realyusufismail.realyusufismailcore.data.support.lang;

import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

public abstract class ModEnLangProviderSupport extends LanguageProvider {
    public ModEnLangProviderSupport(DataGenerator gen, String modid, String locale) {
        super(gen.getPackOutput(), modid, locale);
    }

    @Override
    protected abstract void addTranslations();

    /**
     * add(entry.get(), name); for all
     */
    protected abstract <T extends Item> void item(DeferredItem<T> entry, String name);

    protected abstract <T extends Block> void block(DeferredBlock<T> entry, String name);

    protected abstract <T extends Entity> void entity(EntityType<?> key, String name);

    /**
     * super.add(translatableComponent.getString(), lang);
     */
    protected abstract void add(Component translatableComponent, String lang);
}
