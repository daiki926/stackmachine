//プログラミング演習Ⅱ　2018/01/30　BP16090 村松大輝

import java.io.*;


/**
 *二分木クラス
 *<PRE>
 *二分木を生成します
 *中置記法を後置記法に変換します
 *</PRE>
 *<OL>
 * <LI>BinarySearchTree(String expression)
 * <LI>public void insert()
 * <LI>int getOperatorPosition(String expression)
 * <LI>String removeBracket(String expression)
 * <LI>public String traversePostorder()
 * <LI>public void postfixNotationReset()
 *</OL>
 *@author BP16090 村松大輝
 */


class BinarySearchTree{
static String postfixNotation = "";
private String expression;
private BinarySearchTree left = null;
private BinarySearchTree right = null;
    
    /**
     *expressionをthis.expressionにセットするコンストラクタ
     *呼び出されるたびに更新されます。
     */
BinarySearchTree(String expression){
    this.expression = expression;
}
    
    /**
     *二分木を生成するメソッド
     */
    public void insert(){
        expression = removeBracket(expression);
        if(expression.length() != 0){
        if(expression.charAt(0) == '-')
            expression = "0" + expression;
        }
    int operatorPosition = getOperatorPosition(expression);
        
        if (operatorPosition < 0) {
            left = null;
            right = null;
            return;
        }
    
        String leftSubExpression = removeBracket(this.expression.substring(0,operatorPosition));
        System.out.println("left: " + leftSubExpression);
        left = new BinarySearchTree(leftSubExpression);
        left.insert();
        
        String rightSubExpression = removeBracket(this.expression.substring(operatorPosition + 1));
        System.out.println("right: " + rightSubExpression);
        right = new BinarySearchTree(rightSubExpression);
        right.insert();
        
        this.expression = this.expression.substring(operatorPosition, operatorPosition + 1);
        System.out.println("node: " + this.expression);
    }

    /**
     *優先度を想定したオペレータの場所を得るメソッド
     *@param expression 式
     *@return オペレータの場所
     */
    int getOperatorPosition(String expression){
        if (expression == null || expression.length() == 0)
            return -1;
        
            int position = -1;
            int currentPriority = 3;
            int count = 0;
        
        for (int i = 0; i < expression.length(); i++) {
          int priority = 3;
            char token = expression.charAt(i);
            if(token == '+')priority = 1;
            else if(token == '-')priority = 1;
            else if(token == '*')priority = 2;
            else if(token == '/')priority = 2;
            else if(token == '(')count++;
            else if(token == ')')count--;
        
            if (count == 0 && priority <= currentPriority) {
                currentPriority = priority;
                position = i;
            }
        }
        if(currentPriority == 3)return -1;
                return position;
    }

    /**
     *式から一番外のかっこを外すメソッド
     *@param expression 式
     *@return カッコを外された式
     */
    String removeBracket(String expression){
        int count = 1;
        
        for (int i = 1; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                count++;
            }
            else if (expression.charAt(i) == ')') {
                count--;
                
                if (i < expression.length() - 1 && count == 0)
                    return expression;
            }
        }
        if (!(expression.startsWith("(") && expression.endsWith(")"))){
            return expression;
        }
        else{
                return removeBracket(expression.substring(1,expression.length() -1));
            }
        }
    
    /**
     *降順で二分木を辿るメソッド
     *@return 後置記法の式
     */
    public String traversePostorder() {
        if (left != null)
            left.traversePostorder();
        if (right != null)
            right.traversePostorder();
        if(this.expression.length() != 0){
        postfixNotation += this.expression + " ";
        }
        //System.out.println(postfixNotation);
        return postfixNotation;
    }
    
    public void postfixNotationReset(){
        postfixNotation ="";
    }
}
