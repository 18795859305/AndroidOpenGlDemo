precision mediump float;
varying vec2 vTexCoord;
uniform sampler2D uTexture;
void mian()
{
 gl_FragColor = texture2D(uTexture,vTexCoord);
}
