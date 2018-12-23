//プログラミング演習Ⅱ　2018/01/30　BP16090 村松大輝


import java.util.*;
import java.io.*;

/**
 *命令列読み取りクラス
 *<PRE>
 *命令列を読み取ります。
 *</PRE>
 *<OL>
 * <LI>OrderReader(ArrayList<String> order)
 * <LI>void readOrder()
 * <LI>void stepReadOrder()
 * <LI>void reader()
 *</OL>
 *@author BP16090 村松大輝
 */


class OrderReader{
    private ArrayList<String> order;
    private StackMachine machineIns;
    private Iterator<String> it;
    /**
     *orderをthis.orderにセットするコンストラクタ
     *@param order 命令列
     */
    OrderReader(ArrayList<String> order , StackMachine machineIns){
        this.machineIns = machineIns;
        this.it = order.iterator();
    }

    /**
     *readerメソッドをイテレータの要素がある限りでループするメソッド
     */
    public void readOrder(){
        while(this.it.hasNext()){
            reader();
                  }
    }
    
    /**
     *readerメソッドをイテレータの要素があれば使用するメソッド
     */
    public void stepReadOrder(){
        if(this.it.hasNext()){
            reader();
                }
    }

    /**
     *命令列を読み取るメソッド
     *オペランドとオペレーターで異なるメソッドに渡します。
     */
    public void reader(){
        try{
        String lineOrder = this.it.next();
        Scanner scanner = new Scanner(lineOrder);
        String stringToken = scanner.next();
        if(stringToken.equals("push")){
            int numberToken = scanner.nextInt();
            float floatToken = new Float(numberToken).floatValue();
            this.machineIns.setOperand(floatToken);
        }
        else{
            this.machineIns.order(stringToken);
        }
        scanner.close();
        }
        catch(InputMismatchException e){
            System.out.println(e);
        }
}
}
