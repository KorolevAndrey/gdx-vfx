/*******************************************************************************
 * Copyright 2012 bmanuel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.crashinvaders.vfx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

//TODO Replace with unified provider.
public final class ShaderLoader {
    private static final String TAG = ShaderLoader.class.getSimpleName();

    public static String basePath = "shaders/";
    public static boolean pedantic = true;

    private ShaderLoader() {
    }

    public static ShaderProgram fromFile(String vertexFileName, String fragmentFileName) {
        return ShaderLoader.fromFile(vertexFileName, fragmentFileName, "");
    }

    public static ShaderProgram fromFile(String vertexFileName, String fragmentFileName, String defines) {
        String log = "\"" + vertexFileName + "/" + fragmentFileName + "\"";
        if (defines.length() > 0) {
            log += " w/ (" + defines.replace("\n", ", ") + ")";
        }
        log += "...";
        Gdx.app.log(TAG, "Compiling " + log);

        String vpSrc = Gdx.files.internal(basePath + vertexFileName + ".vert").readString();
        String fpSrc = Gdx.files.internal(basePath + fragmentFileName + ".frag").readString();

        ShaderProgram program = ShaderLoader.fromString(vpSrc, fpSrc, vertexFileName, fragmentFileName, defines);
        return program;
    }

    public static ShaderProgram fromString(String vertex, String fragment, String vertexName, String fragmentName) {
        return ShaderLoader.fromString(vertex, fragment, vertexName, fragmentName, "");
    }

    public static ShaderProgram fromString(String vertex, String fragment, String vertexName, String fragmentName, String defines) {
        ShaderProgram.pedantic = ShaderLoader.pedantic;
        ShaderProgram shader = new ShaderProgram(defines + "\n" + vertex, defines + "\n" + fragment);

        if (!shader.isCompiled()) {
            Gdx.app.error(TAG, "Compile error: " + vertexName + "/" + fragmentName);
            Gdx.app.error(TAG, shader.getLog());
            System.exit(-1);
        }
        return shader;
    }
}
