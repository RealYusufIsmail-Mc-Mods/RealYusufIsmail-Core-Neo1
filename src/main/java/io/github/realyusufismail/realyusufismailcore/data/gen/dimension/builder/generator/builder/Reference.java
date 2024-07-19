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
package io.github.realyusufismail.realyusufismailcore.data.gen.dimension.builder.generator.builder;

import lombok.Getter;

@Getter
public enum Reference {
    AMPLIFIED("minecraft:amplified"),
    CAVES("minecraft:caves"),
    END("minecraft:end"),
    FLOATING_ISLANDS("minecraft:floating_islands"),
    LARGE_BIOMES("minecraft:large_biomes"),
    NETHER("minecraft:nether"),
    OVERWORLD("minecraft:overworld");

    private final String id;

    Reference(String id) {
        this.id = id;
    }
}
