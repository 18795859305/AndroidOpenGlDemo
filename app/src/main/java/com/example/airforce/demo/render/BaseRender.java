package com.example.airforce.demo.render;

import android.content.Context;
import android.opengl.GLES20;

import com.example.airforce.demo.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Airforce on 2018/5/2.
 */

public abstract class BaseRender {
    /**
     * 顶点坐标数据
     */
    private float[] vertexData={
            -1,1,0,
            -1,-1,0,
            1,1,0,
            1,-1,0
    };

    //纹理坐标数据
    private short[] coordData ={
            0,0,
            0,1,
            1,0,
            1,1
    };
    /**
     * 定点缓存数据
     */
    private FloatBuffer vertexBuffer;
    //纹理坐标缓存数据
    private ShortBuffer coordBuffer;
    //顶点坐标参数句柄
    protected int mPositionHandler;
    //纹理坐标参数句柄
    protected int mCoordHadnlder;
    //shader句柄
    protected int mProgramId;
    //转换矩阵句柄
    protected int mMatrixHandler;
    //纹理句柄
    protected int mTxtureHandler;

    private int textureId;

    private int textureType;

    protected Context mContext;



    public BaseRender(Context context)
    {
        this.mContext = context;
        init();
    }


    public void setTextureId(int textureId)
    {
        this.textureId = textureId;
    }

    public int getTextureId()
    {
        return textureId;
    }

    public void setTextureType(int textureType)
    {
        this.textureType = textureType;
    }

    public int getTextureType()
    {
        return  textureType;
    }


    public void create()
    {
        onCreate();
    }

    protected  void onCreate()
    {
        loadShader();
    }
    protected abstract void onSizeChanged(int width,int height);

    public void init()
    {
        //分配空间，一个float类型占四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertexData.length*4);
        vertexBuffer = vbb.order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        vertexBuffer.position(0);

        ByteBuffer cbb = ByteBuffer.allocateDirect(coordData.length*2);
        coordBuffer = cbb.order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(coordData);
        coordBuffer.position(0);

    }

    public void draw()
    {
        onClear();
        onUseProgram();
        onSetShaderParams();
        onBindTexture();
        onDraw();
        onDisable();
    }

    protected  void onBindTexture()
    {
        GLES20.glActiveTexture(getTextureType());
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,getTextureId());
        GLES20.glUniform1i(mTxtureHandler,getTextureType());
    }


    protected void onClear(){
        GLES20.glClearColor(0.0f,0.0f,0.0f,1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT |GLES20.GL_DEPTH_BUFFER_BIT);
    }

    protected void onUseProgram()
    {
        GLES20.glUseProgram(mProgramId);
    }

    protected  void onSetShaderParams(){
        GLES20.glEnableVertexAttribArray(mPositionHandler);
        GLES20.glVertexAttribPointer(mPositionHandler,3,GLES20.GL_FLOAT,false,0,vertexBuffer);

        GLES20.glEnableVertexAttribArray(mCoordHadnlder);
        GLES20.glVertexAttribPointer(mCoordHadnlder,2,GLES20.GL_SHORT,false,0,coordBuffer);

    }

    protected void onDraw()
    {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP,0,4);
    }

    protected void onDisable()
    {
        GLES20.glDisableVertexAttribArray(mPositionHandler);
        GLES20.glDisableVertexAttribArray(mCoordHadnlder);
    }

    protected abstract void loadShader();


    protected void readRawShaderFile(int vertexId,int fragId)
    {
       String vertexCode = ShaderUtil.readRawTextFileFromResource(mContext,vertexId);
       String fragCode = ShaderUtil.readRawTextFileFromResource(mContext,fragId);
        createShaderProgram(vertexCode,fragCode);
    }

    protected void createShaderProgram(String vertex,String fragment)
    {
        mProgramId = ShaderUtil.createProgram(vertex,fragment);
    }

}
