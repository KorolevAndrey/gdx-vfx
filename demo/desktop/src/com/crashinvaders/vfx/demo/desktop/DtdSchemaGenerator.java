package com.crashinvaders.vfx.demo.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.crashinvaders.vfx.common.lml.CommonLmlSyntax;
import com.crashinvaders.vfx.common.lml.LmlUtils;
import com.github.czyzby.lml.parser.impl.DefaultLmlData;
import com.github.czyzby.lml.util.LmlParserBuilder;

/**
 * Generates DTD schema files for LML markup syntax.
 * Working directory should be set to project root.
 */
public class DtdSchemaGenerator {
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        LwjglApplicationConfiguration.disableAudio = true;
        config.width = 1;
        config.height = 1;
        config.title = "";
        config.initialBackgroundColor = Color.VIOLET;

        new LwjglApplication(new ApplicationAdapter() {
            private Skin skin;

            @Override
            public void create() {
                skin = createSkin();
                LmlUtils.saveDtdSchema(new LmlParserBuilder(new DefaultLmlData())
                                .syntax(new CommonLmlSyntax())
                                .skin(skin)
                                .build(),
                        Gdx.files.local("demo/dtd/common.dtd"));
                Gdx.app.exit();
            }

            @Override
            public void dispose() {
                skin.dispose();
                skin = null;
            }
        }, config);
    }

    /** Generates skin with minimum required set of default resources. */
    private static Skin createSkin() {
        BitmapFont font = new BitmapFont();
        final BaseDrawable drawable = new BaseDrawable();
        Color color = Color.WHITE;

        Skin skin = new Skin() {
            @Override
            public Drawable getDrawable(String name) {
                return drawable;
            }
        };
        skin.add("default", font);
        skin.add("default", new Label.LabelStyle(font, color));
        skin.add("default-horizontal", new SplitPane.SplitPaneStyle(drawable));
        skin.add("default-vertical", new SplitPane.SplitPaneStyle(drawable));
        skin.add("default", new CheckBox.CheckBoxStyle(drawable, drawable, font, color));
        skin.add("default-horizontal", new ProgressBar.ProgressBarStyle(drawable, drawable));
        skin.add("default-vertical", new ProgressBar.ProgressBarStyle(drawable, drawable));
        skin.add("default", new ScrollPane.ScrollPaneStyle(drawable, drawable, drawable, drawable, drawable));
        skin.add("default", new List.ListStyle(font, color, color, drawable));
        skin.add("default", new SelectBox.SelectBoxStyle(font, color, drawable, skin.get(ScrollPane.ScrollPaneStyle.class), skin.get(List.ListStyle.class)));
        skin.add("default", new Tree.TreeStyle(drawable, drawable, drawable));
        skin.add("default", new TextButton.TextButtonStyle(drawable, drawable, drawable, font));
        skin.add("default-horizontal", new Slider.SliderStyle(drawable, drawable));
        skin.add("default-vertical", new Slider.SliderStyle(drawable, drawable));
        skin.add("default", new TextTooltip.TextTooltipStyle(skin.get(Label.LabelStyle.class), drawable));
        skin.add("default", new ImageButton.ImageButtonStyle(drawable, drawable, drawable, drawable, drawable, drawable));
        skin.add("default", new TextField.TextFieldStyle(font, color, drawable, drawable, drawable));
        skin.add("default", new Window.WindowStyle(font, color, drawable));
        skin.add("default", new Button.ButtonStyle(drawable, drawable, drawable));
        skin.add("default", new Label.LabelStyle(font, color));
        skin.add("default", new Touchpad.TouchpadStyle(drawable, drawable));
        skin.add("default", new ImageTextButton.ImageTextButtonStyle(drawable, drawable, drawable, font));
        return skin;
    }
}
