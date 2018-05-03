attribute vec4 aPosition;
attribute vec2 aTexCoord;
varying vec2 vTexCoord;
void mian()
{
   gl_Position = aPosition;
   vTexCoord = aTexCoord;
}