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
package io.github.realyusufismail.realyusufismailcore.data.dimension;

import io.github.realyusufismail.realyusufismailcore.data.dimension.builder.DimensionTypeBuilder;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.Setter;
import lombok.val;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

@Setter
public abstract class DimensionTypeProvider implements DataProvider {
    private final DataGenerator generator;
    private final String modId;

    protected List<DimensionTypeBuilder> dimensionTypeBuilders;

    public DimensionTypeProvider(DataGenerator generator, String modId) {
        this.generator = generator;
        this.modId = modId;
    }

    protected abstract void addDimensionTypes();

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        val dataProvider = generator.getPackOutput();

        for (DimensionTypeBuilder dimensionTypeBuilder : dimensionTypeBuilders) {
            val jsonObject = dimensionTypeBuilder.toJson();
            val path = resolvePath(
                    dataProvider, "data/" + modId + "/dimension_type/" + dimensionTypeBuilder.getName() + ".json");
            DataProvider.saveStable(cachedOutput, jsonObject, path);
        }

        return CompletableFuture.completedFuture(null);
    }

    private @NotNull Path resolvePath(@NotNull PackOutput path, String pathOther) {
        return path.getOutputFolder().resolve(pathOther);
    }

    @Override
    public @NotNull String getName() {
        return "Dimension Type Provider for " + modId;
    }
}
