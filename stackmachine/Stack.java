//プログラミング演習Ⅱ　2018/01/30　BP16090 村松大輝


import java.util.Arrays;


/**
 *スタッククラス
 *<PRE>
 *スタックを使うためのクラス
 *</PRE>
 *<OL>
 * <LI>public Stack()
 * <LI>public Stack(int size)
 * <LI>public float[] getStack(int size)
 * <LI>public void push(float num)
 * <LI>public float pop()
 *</OL>
 *@author BP16090 村松大輝
 */

public class Stack{
private float[] stack;
private int size;
private int top;
private static  int Max_size = 100;

    /**
     *スタック容量の初期設定コンストラクタ
     */
public Stack(){
this(Max_size);
}
    /**
     *スタック初期設定コンストラクタ
     *@param size スタックのサイズ
     */
public Stack(int size){
this.stack = new float[size];
this.top = 0;
this.size = size;
    
}

    /**
     *stackのgetterメソッド
     *@return スタック
     */
    public float[] getStack(){
        return this.stack;
    }
    
    
/**
*pushメソッド
*@param num pushする数値
*/
public void push(float num){
stack[this.top] = num;
top++;
}


/**
*popメソッド
*@return popされる数値
*/
public float pop(){
top--;
if(top < 0){
}
float num = stack[top];
return num;
}
}
