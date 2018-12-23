//プログラミング演習Ⅱ　2018/01/30　BP16090 村松大輝



import java.io.*;
import java.util.*;

interface Operator{
    
    void calc(Stack stack);
}

/**
 *インタフェースを実装した足し算をするクラスです。
 *@param スタック
 */
class Add implements Operator{
    @Override
    public void calc(Stack stack){
        float b = stack.pop();
        float a = stack.pop();
        stack.push(a + b);
    }
}

/**
 *インタフェースを実装した引き算をするクラスです。
 *@param スタック
 */
class Sub implements Operator{
    @Override
    public void calc(Stack stack){
        float b = stack.pop();
        float a = stack.pop();
        stack.push(a - b);
    }
}

/**
 *インタフェースを実装した掛け算をするクラスです。
 *@param スタック
 */
class Mul implements Operator{
    @Override
    public void calc(Stack stack){
        float b = stack.pop();
        float a = stack.pop();
        stack.push(a * b);
    }
}

/**
 *インタフェースを実装した割り算をするクラスです。
 *@param スタック
 */
class Div implements Operator{
    @Override
    public void calc(Stack stack){
        float b = stack.pop();
        float a = stack.pop();
        if(b==0){
            System.out.println("error");
            //System.exit(1);
        }
        stack.push(a / b);
    }
}


/**
 *スタックマシンクラス
 *<PRE>
 *スタックを使って四則演算をします
 *</PRE>
 *<OL>
 * <LI>StackMachine(int stackSize)
 * <LI>StackMachine()
 * <LI>public float[] getStack()
 * <LI>public int getStackSize()
 * <LI>public boolean getWrt()
 * <LI>public void setOperand(float token)
 * <LI>public void order(String token)
 * <LI>int getResult()
 *</OL>
 *@author BP16090 村松大輝
 */


public class StackMachine{
    private Stack stack;
    private int stackSize;
    private boolean wrt = false;
    
    /**
     *初期のスタックのサイズ(pushした回数)の配列のスタックを作成するコンストラクタ。
     */
    StackMachine(int stackSize){
        this.stack = new Stack(stackSize);
        this.stackSize = 0;
    }
    
    
    /**
     *スタックのサイズが与えられない場合のスタックを作成するコンストラクタ。
     */
    StackMachine(){
    this.stack = new Stack();
    }
    
    /**
     *stackを表す配列を返すgetterメソッド
     *@return 配列(配列)
     */
    public float[] getStack(){
        return this.stack.getStack();
    }
    
    /**
     *stackSizeのgetterメソッド
     *@return 配列を作るにおいて必要な最小のスタックのサイズ
     */
    public int getStackSize(){
        return this.stackSize;
    }
    
    /**
     *命令列がwrtに一致したらtrueを返すgetterメソッド
     *@return wrtに一致したかどうか
     */
    public boolean getWrt(){
        return wrt;
    }
    
    /**
     *tokenをstackにpushするメソッド
     *@param token オペランド
     */
    public void setOperand(float token){
        stack.push(token);
        this.stackSize++;
    }
    
    /**
     *オペレータを場合分けし、それに合わせた処理を行わせるメソッドです。
     *@param token オペレータ
     */
    public void order(String token){
        if(token.equals("add")){
            Add add = new Add();
            add.calc(stack);
            this.stackSize--;
            //this.result
        }
        
        else if(token.equals("sub")){
            Sub sub = new Sub();
            sub.calc(stack);
            this.stackSize--;
        }
        
        else if(token.equals("mul")){
            Mul mul = new Mul();
            mul.calc(stack);
            this.stackSize--;
        }
        
        else if(token.equals("div")){
            Div div = new Div();
            div.calc(stack);
            this.stackSize--;
        }
        
        else if(token.equals("wrt")){
            //System.out.println(Math.round(stack.pop()));
            wrt = true;
        }
        
        else if(token.equals("halt")){
            System.out.println("停止します");
            //System.exit(1);
        }
        
        else {
            System.out.println("適切なオペランドではありません");
        }
    }
   }









