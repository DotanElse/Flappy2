package com.dotan.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animation {
    private Array<TextureRegion> frames; // stores frames
    private float maxFrameTime; // how long before switch
    private float currentFrameTime; // current frame time
    private int frameCount; //number of frames in animation
    private int frame; //current frame

    public Animation(TextureRegion region, int frameCount, float cycleTime)
    {
        frames = new Array<TextureRegion>();
        int frameWidth = region.getRegionWidth() / frameCount;
        for(int i =0; i < frameCount; i++){
            frames.add(new TextureRegion(region, i * frameWidth, 0, frameWidth, region.getRegionHeight()));
        }
        this.frameCount = frameCount;
        maxFrameTime = cycleTime / frameCount;
        frame = 0;
    }

    public void update(float dt){
        currentFrameTime += dt;
        if(currentFrameTime > maxFrameTime){
            frame = (frame+1)%frameCount;
            currentFrameTime = 0;
        }
    }

    public TextureRegion getFrame(){
        return frames.get(frame);
    }

}
