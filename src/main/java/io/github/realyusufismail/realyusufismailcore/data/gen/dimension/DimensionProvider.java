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
package io.github.realyusufismail.realyusufismailcore.data.gen.dimension;

import com.google.gson.JsonElement;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.DimensionBuilder;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public abstract class DimensionProvider implements DataProvider {
    private final PackOutput output;
    private final String modId;
    private final List<DimensionBuilder> dimensionBuilders = new ArrayList<>();

    public DimensionProvider(PackOutput output, String modId) {
        this.output = output;
        this.modId = modId;
    }

    public abstract void run();

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        run();

        if (!dimensionBuilders.isEmpty()) {
            for (DimensionBuilder dimensionBuilder : dimensionBuilders) {
                return save(
                        cachedOutput,
                        this.output
                                .getOutputFolder(PackOutput.Target.DATA_PACK)
                                .resolve(this.modId)
                                .resolve("dimension")
                                .resolve(dimensionBuilder.getName() + ".json"),
                        dimensionBuilder.toJson());
            }
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

    @Contract("_, _, _ -> new")
    private @NotNull CompletableFuture<?> save(CachedOutput cache, Path target, @NotNull JsonElement json) {
        return DataProvider.saveStable(cache, json, target);
    }

    public void addDimensionBuilder(DimensionBuilder dimensionBuilder) {
        dimensionBuilders.add(dimensionBuilder);
    }

    public void addDimensionBuilders(DimensionBuilder... dimensionBuilders) {
        this.dimensionBuilders.addAll(List.of(dimensionBuilders));
    }

    public void addDimensionBuilders(List<DimensionBuilder> dimensionBuilders) {
        this.dimensionBuilders.addAll(dimensionBuilders);
    }
}
