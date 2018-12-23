//プログラミング演習Ⅱ　2018/01/30　BP16090 村松大輝

import java.io.*;
import java.util.*;


/**
 *命令列作成クラス
 *<PRE>
 *命令列を作成します
 *</PRE>
 *<OL>
 * <LI>FormOrder(File file)
 * <LI>FormOrder(String formula)
 * <LI>public int getStackSize()
 * <LI>public ArrayList<String> formOrderFromFile()
 * <LI>public ArrayList<String> formOrderFromFormula()
 * <LI>public boolean isNumber(String num)
 *</OL>
 *@author BP16090 村松大輝
 */


class FormOrder{
    private ArrayList<String> order = new ArrayList<String>();
    private File file;
    private String formula;
    private String line;
    private int stackSize;
    
    
    /**
     *fileをthis.fileにセットするコンストラクタ
     *@param file 読み取るファイル
     */
    FormOrder(File file){
        this.file = file;
    }
    /**
     *formulaをthis.formulaにセットするコンストラクタ
     *@param formula 読み取る式
     */
    FormOrder(String formula){
        this.formula = formula;
    }
    
    
    /**
     *stackSizeのgetterメソッド
     *@return スタックのサイズ
     */
    public int getStackSize(){
        return this.stackSize;
    }
    
    
    
    /**
     *csvファイルを読み取り、命令列を作成するメソッド
     *@return 命令列
     */
    public ArrayList<String> formOrderFromFile(){
        try{
            FileReader fr = new FileReader(this.file);
            BufferedReader br = new BufferedReader(fr);
            
            while((line = br.readLine())!=null){
                order.add(line.replace("," , " ").trim());
            }
            br.close();
            fr.close();
            
        }catch(FileNotFoundException e){
            System.out.println(e);
        
        }catch(IOException e){
            System.out.println(e);
        }
        catch(IndexOutOfBoundsException e){
            System.out.println(e);
        }
        return order;
    }
    
    /**
     *式を読み取り、命令列を作成するメソッド
     *@return 命令列
     */
    public ArrayList<String> formOrderFromFormula(){
        try{
        Scanner scanner = new Scanner(this.formula);
        while(scanner.hasNext()){
            String token = scanner.next();
            
            if(isNumber(token)){
                this.stackSize++;
                order.add("push" + " " + token);
            }
            else if(token.equals("+")) order.add("add");
            else if(token.equals("-")) order.add("sub");
            else if(token.equals("*")) order.add("mul");
            else if(token.equals("/")) order.add("div");
            else order.add(token);
        }
        order.add("wrt");
        order.add("halt");
        }catch(IllegalStateException e){
            System.out.println(e);
        }
        return order;
    }
    
    /**
     *数値かどうか判定するメソッド
     *@param num 判定する文字列
     *@return 数値に変換できた場合はtrue
     */
    public boolean isNumber(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
