# gdx-vfx

LibGDX flexible post processing visual effects. The library is based on [libgdx-contribs-postprocessing](https://github.com/manuelbua/libgdx-contribs/tree/master/postprocessing), 
with lots of improvements, aim on stability and to provide lightweight integration with comfortable effect extensions.

Work is in progress, some official backends are still not tested/not supported.
More effects to be implemented and included in the standard library package. 


# Demo

Clone and play with the demo:
```
git clone https://github.com/crashinvaders/gdx-vfx.git
cd gdx-vfx
./gradlew demo:desktop:run
```

![Alt Text](https://i.imgur.com/bHl3ADH.gif)


# How to use

### 1. Include the library into the project

#### A. Local JAR artifacts.
The library is not yet available on any public maven repository,
so the simplest way is to download JAR artifacts from [releases page](https://github.com/crashinvaders/gdx-vfx/releases) and attach them to the project.
Put the downloaded `gdx-vfx-core.jar` and `gdx-vfx-effects.jar` into the `/core/libs` dir and add them as dependencies.

_/core/build.gradle_:
```gradle
dependencies {
    // ...
    compile fileTree(dir: 'libs', include: ['*.jar'])
}
```

#### B. Local maven archetype.

1. Clone the repository into a local directory.
```
git clone https://github.com/crashinvaders/gdx-vfx.git
```
2. Install local maven archetype using gradle task (maven should be installed on the system and added to the PATH).
```
./gradlew gdx-vfx:core:install gdx-vfx:effects:install
```
3. Include the library from a local maven repository.

_/core/build.gradle_:
```gradle
repositories {
    // ...
    mavenLocal()
}

dependencies {
    // ...
    compile "com.crashinvaders.vfx:core:0.1"
    compile "com.crashinvaders.vfx:effects:0.1"
}
```

### 2. Sample code

```java
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.crashinvaders.vfx.PostProcessor;
import com.crashinvaders.vfx.effects.BloomEffect;

public class PostProcessorExample extends ApplicationAdapter {

	private ShapeRenderer shapeRenderer;
	private PostProcessor postProcessor;
	private BloomEffect postProcessorEffect;

	@Override
	public void create () {
		shapeRenderer = new ShapeRenderer();

		// PostProcessor is a manager for the effects.
		// It captures rendering into internal off-screen buffer and applies a chain of defined effects.
		// Off-screen buffers may have any pixel format, but for better effect mixing
		// it's recommended to use values with an alpha component (e.g. RGBA8888 or RGBA4444).
		postProcessor = new PostProcessor(Pixmap.Format.RGBA8888);

		// PostProcessor must be initialized with the required size for internal off-screen buffers.
		postProcessor.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Create and add a shader effect.
		// PostProcessorEffect derivative classes serves as controllers for shader effects.
		// They usually provide some public properties to configure and control the effects.
		postProcessorEffect = new BloomEffect();
		postProcessorEffect.setBlurPasses(32);
		postProcessorEffect.setBloomIntesity(1.2f);
		postProcessor.addEffect(postProcessorEffect);
	}

	@Override
	public void resize(int width, int height) {
		// PostProcessor manages internal off-screen buffers,
		// which should always match the required viewport (whole screen in our case).
		postProcessor.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	@Override
	public void render () {
		// Clean up the screen.
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Begin render to an off-screen buffer.
		postProcessor.beginCapture();

		// Render some simple geometry.
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.PINK);
		shapeRenderer.rect(250f, 100f, 250f, 175f);
		shapeRenderer.setColor(Color.ORANGE);
		shapeRenderer.circle(200f, 250f, 100f);
		shapeRenderer.end();

		// End render to an off-screen buffer.
		postProcessor.endCapture();

		// Perform shader processing and render result to the screen buffer.
		postProcessor.render();
	}
	
	@Override
	public void dispose () {
		// Since PostProcessor manages internal off-screen buffers,
		// it should be disposed manually.
		postProcessorEffect.dispose();

		// PostProcessorEffect instances may also hold internal resources
		// that should be freed, thus disposed manually.
		postProcessor.dispose();

		shapeRenderer.dispose();
	}
}
```

![Result](https://i.imgur.com/qSaIEWD.png)
