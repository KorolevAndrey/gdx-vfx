package com.crashinvaders.vfx.effects;

import com.crashinvaders.vfx.common.framebuffer.FboWrapper;
import com.crashinvaders.vfx.PostProcessorEffect;
import com.crashinvaders.vfx.filters.OldTvFilter;

public class OldTvEffect extends PostProcessorEffect implements UpdateableEffect {

    private final OldTvFilter oldTvFilter;

    private float time;

    public OldTvEffect() {
        oldTvFilter = new OldTvFilter();
    }

    @Override
    public void resize(int width, int height) {
        oldTvFilter.resize(width, height);
    }

    @Override
    public void rebind() {
        oldTvFilter.rebind();
    }

    @Override
    public void render(FboWrapper src, FboWrapper dest) {
        oldTvFilter.setInput(src).setOutput(dest).render();
    }

    @Override
    public void dispose() {
        oldTvFilter.dispose();
    }

    @Override
    public void update(float delta) {
        this.time += delta;
        oldTvFilter.setTime(time);
    }
}
