attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;
uniform mat4 u_projTrans;

varying vec4 vColor;
varying vec2 vTexCoord;
void main() {
	vColor = a_color;
    vTexCoord = vec2(a_texCoord0.s, 1.0 - a_texCoord0.t);
	gl_Position =  u_projTrans * a_position;
}