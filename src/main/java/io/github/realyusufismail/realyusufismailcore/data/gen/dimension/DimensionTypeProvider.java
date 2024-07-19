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
package io.github.realyusufismail.realyusufismailcore.data.gen.dimension;

import com.google.gson.JsonElement;
import io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.DimensionTypeBuilder;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.Setter;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@Setter
public abstract class DimensionTypeProvider implements DataProvider {
    private final PackOutput output;
    private final String modId;
    private final List<DimensionTypeBuilder> dimensionTypeBuilders = new ArrayList<>();

    public DimensionTypeProvider(PackOutput output, String modId) {
        this.output = output;
        this.modId = modId;
    }

    protected abstract void run();

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        run();

        if (!dimensionTypeBuilders.isEmpty()) {
            for (DimensionTypeBuilder dimensionTypeBuilder : dimensionTypeBuilders) {
                return save(
                        cachedOutput,
                        this.output
                                .getOutputFolder(PackOutput.Target.DATA_PACK)
                                .resolve(this.modId)
                                .resolve("dimension_type")
                                .resolve(dimensionTypeBuilder.getName() + ".json"),
                        dimensionTypeBuilder.toJson());
            }
        }

        return CompletableFuture.allOf();
    }

    @Contract("_, _, _ -> new")
    private @NotNull CompletableFuture<?> save(CachedOutput cache, Path target, @NotNull JsonElement json) {
        return DataProvider.saveStable(cache, json, target);
    }

    private @NotNull Path resolvePath(@NotNull PackOutput path, String pathOther) {
        return path.getOutputFolder().resolve(pathOther);
    }

    @Override
    public @NotNull String getName() {
        return "Dimension Type Provider for " + modId;
    }

    public void addDimensionTypeBuilder(DimensionTypeBuilder dimensionTypeBuilder) {
        dimensionTypeBuilders.add(dimensionTypeBuilder);
    }

    public void addDimensionTypeBuilders(List<DimensionTypeBuilder> dimensionTypeBuilders) {
        this.dimensionTypeBuilders.addAll(dimensionTypeBuilders);
    }

    public void addDimensionTypeBuilders(DimensionTypeBuilder... dimensionTypeBuilders) {
        this.dimensionTypeBuilders.addAll(Arrays.asList(dimensionTypeBuilders));
    }
}
