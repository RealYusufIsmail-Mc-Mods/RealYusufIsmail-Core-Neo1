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
package io.github.realyusufismail.realyusufismailcore;

import io.github.realyusufismail.realyusufismailcore.client.ClientSetup;
import io.github.realyusufismail.realyusufismailcore.core.init.*;
import io.github.realyusufismail.realyusufismailcore.core.itemgroup.RealYusufIsmailCoreItemGroup;
import io.github.realyusufismail.realyusufismailcore.data.DataGenerators;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Optional;

@Mod("realyusufismailcore")
public class RealYusufIsmailCore {
    public static final String MOD_ID = "realyusufismailcore";

    public RealYusufIsmailCore() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockInitCore.BLOCKS.register(bus);
        ItemInitCore.ITEMS.register(bus);
        MenuTypeInit.MENU_TYPES.register(bus);
        RecipeTypeInit.RECIPE_TYPES.register(bus);
        RecipeSerializerInit.SERIALIZERS.register(bus);
        RealYusufIsmailCoreItemGroup.CREATIVE_MODE_TABS.register(bus);

        bus.addListener(DataGenerators::gatherData);
        bus.addListener(ClientSetup::clientSetup);

        bus.register(this);
    }

    public static String getVersion() {
        Optional<? extends ModContainer> o = ModList.get().getModContainerById(MOD_ID);
        if (o.isPresent()) {
            return o.get().getModInfo().getVersion().toString();
        }
        return "0.0.0";
    }

    public static boolean isDevBuild() {
        return "NONE".equals(getVersion());
    }

    public static ResourceLocation getId(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
