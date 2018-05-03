package com.example.airforce.demo.render;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;

import com.example.airforce.demo.R;
import com.example.airforce.demo.utils.ShaderUtil;


/**
 * Created by Airforce on 2018/5/2.
 */

public class OESRender extends BaseRender {
    public OESRender(Context context) {
        super(context);
    }


    @Override
    protected void onSizeChanged(int width, int height) {

    }

    @Override
    protected void loadShader() {
      String vertexCode =  ShaderUtil.readRawTextFileFromResource(mContext, R.raw.default_vertex_oes);
      String fragCode =  ShaderUtil.readRawTextFileFromResource(mContext, R.raw.default_frag_oes);
        mProgramId = ShaderUtil.createProgram(vertexCode,fragCode);
        mPositionHandler = GLES20.glGetAttribLocation(mProgramId,"aPosition");
        mCoordHadnlder = GLES20.glGetAttribLocation(mProgramId,"aCoordinate");
    }

    @Override
    protected void onBindTexture() {
      //  GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + getTextureType());
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,getTextureId());
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,GLES20.GL_TEXTURE_WRAP_S,GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,GLES20.GL_TEXTURE_WRAP_T,GLES20.GL_CLAMP_TO_EDGE);
     //   GLES20.glUniform1i(mTxtureHandler,getTextureType());
    }
}
