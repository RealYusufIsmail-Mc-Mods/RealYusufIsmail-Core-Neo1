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
package io.github.realyusufismail.realyusufismailcore.data.dimension;

import static io.github.realyusufismail.realyusufismailcore.RealYusufIsmailCore.MOD_ID;

import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.DimensionTypeProvider;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.DimensionTypeBuilder;
import lombok.val;
import net.minecraft.data.DataGenerator;

public class RealYusufIsmailDimensionProvider extends DimensionTypeProvider {
    public RealYusufIsmailDimensionProvider(DataGenerator generator) {
        super(generator.getPackOutput(), MOD_ID);
    }

    @Override
    protected void run() {
        val dimensionTypeBuilder = new DimensionTypeBuilder("test_dimension_type")
                .setNatural(true)
                .setHasCeiling(true)
                .setHasSkylight(true)
                .setHasRaids(true)
                .setBedWorks(true)
                .setRespawnAnchorWorks(true)
                .setPiglinSafe(true)
                .setUltrawarm(true)
                .setAmbientLight(0)
                .setCoordinateScale(1)
                .setLogicalHeight(256)
                .setFixedTime(0);

        addDimensionTypeBuilder(dimensionTypeBuilder);
    }
}
