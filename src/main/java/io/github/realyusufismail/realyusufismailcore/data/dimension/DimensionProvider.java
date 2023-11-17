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

import io.github.realyusufismail.realyusufismailcore.data.dimension.builder.DimensionBuilder;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

public abstract class DimensionProvider implements DataProvider {
    private final DataGenerator generator;
    private final String modId;

    public List<DimensionBuilder> dimensionBuilders;

    public DimensionProvider(DataGenerator generator, String modId) {
        this.generator = generator;
        this.modId = modId;
    }

    public abstract void addDimensionBuilders();

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        PackOutput dataProvider = generator.getPackOutput();

        for (DimensionBuilder dimensionBuilder : dimensionBuilders) {
            Path path =
                    resolvePath(dataProvider, "data/" + modId + "/dimension/" + dimensionBuilder.getName() + ".json");
            DataProvider.saveStable(cachedOutput, dimensionBuilder.toJson(), path);
        }

        return CompletableFuture.completedFuture(null);
    }

    private Path resolvePath(PackOutput path, String pathOther) {
        return path.getOutputFolder().resolve(pathOther);
    }

    @Override
    public @NotNull String getName() {
        return "Dimension Type Provider for " + modId;
    }
}
