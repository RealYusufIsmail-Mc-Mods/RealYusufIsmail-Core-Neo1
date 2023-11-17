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
package io.github.realyusufismail.realyusufismailcore.data.dimension.builder.generator.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Noise {
    private int minY;
    private int height;
    private int sizeHorizontal;
    private int sizeVertical;

    public Noise setMinY(int minY) {
        this.minY = minY;
        return this;
    }

    public Noise setHeight(int height) {
        this.height = height;
        return this;
    }

    public Noise setSizeHorizontal(int sizeHorizontal) {
        this.sizeHorizontal = sizeHorizontal;
        return this;
    }

    public Noise setSizeVertical(int sizeVertical) {
        this.sizeVertical = sizeVertical;
        return this;
    }

    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("min_y", minY);
        object.addProperty("height", height);
        object.addProperty("size_horizontal", sizeHorizontal);
        object.addProperty("size_vertical", sizeVertical);
        return object;
    }
}
