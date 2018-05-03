package com.example.airforce.demo;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;

import com.example.airforce.demo.render.OESRender;
import com.example.airforce.demo.render.WaterMaskRender;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Airforce on 2018/5/2.
 */

public class VideoView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private static final String TAG ="VideoView";

    private ISurfaceCreated mSurfaceListener;
    private int[] textures =new int[1];
    private OESRender oesRender;
    private WaterMaskRender waterMaskRender;
    private SurfaceTexture mSurfaceTexture;

    public VideoView(Context context) {
        super(context);
        init();
    }

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init()
    {
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
        oesRender = new OESRender(getContext());

    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        Log.d(TAG,"onSurfaceCreated");
        oesRender.create();
        genTextures();
        oesRender.setTextureId(textures[0]);
        mSurfaceTexture = new SurfaceTexture(textures[0]);
        mSurfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
            @Override
            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                requestRender();
            }
        });
        if(mSurfaceListener !=null)
        {
            mSurfaceListener.onSurfaceCreated(new Surface(mSurfaceTexture));
        }

    }

    private void genTextures() {
        GLES20.glGenTextures(textures.length,textures,0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0,0,width,height);

    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        if(mSurfaceTexture !=null)
        {
            mSurfaceTexture.updateTexImage();
        }

        oesRender.draw();

    }


    public void setOnSurfaceCreatedListener(ISurfaceCreated listener)
    {
        mSurfaceListener = listener;
    }

    interface  ISurfaceCreated
    {
        void onSurfaceCreated(Surface surface);
    }
}
