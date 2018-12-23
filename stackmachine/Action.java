//プログラミング演習Ⅱ　2018/01/30　BP16090 村松大輝


import java.applet.Applet;
import java.awt.TextField;
import java.awt.Label;
import java.awt.Button;	
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;		
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.*;
import java.awt.*;
import java.applet.*;


/**
 *GUI化クラス
 *<PRE>
 *GUI化します。
 *</PRE>
 *<OL>
 * <LI>public void init()
 * <LI>public void actionPerformed(ActionEvent evt)
 * <LI>public void paint(Graphics g)
 *</OL>
 *@author BP16090 村松大輝
 */

public class Action extends Applet implements ActionListener {
    
    private TextField expressionField;
    private String infixNotation;
    private String postfixNotation;
    private ArrayList<String> order;
    private int result = 0;
    private int firstStackSize = 0;
    private int stackSize = 0;
    private float[] stack;
    private Button conversionButton;
    private Button formButton;
	private Button executeButton;
    private Button stepExecuteButton;
    private boolean pushConversion = false;
    private boolean pushForm = false;
    private boolean pushStepExecute = false;
    private boolean wrt = false;
    private StackMachine machineIns;
    private OrderReader formulaReader;
    
    //命令列によって変わるため、定数にはしない
    public int WIDTH;
    public int SMALLHEIGHT;
    public int BIGHEIGHT;
    public int BODYSIZE;
    public int HEADINGSIZE;
    
    public static final int APLLETSIZE = 1000;
    
    public static final int LEFTWIDTH = 200;
    public static final int UPPERWIDTH = 10;
    public static final int LABELWIDTH = 100;
    public static final int LABELHEIGHT = 30;
    public static final int TEXTWIDTH = 500;
    
    public static final int HEIGHTBUTTON = 50;
    public static final int WIDTHBUTTON = 50;
    public static final int STEPWIDTHBUTTON = 100;
    
    public static final int TEXTLIMIT = 10;
 
    public static final int UPPERSHOW = 70;
    
    FlowLayout layout = new FlowLayout();
    
    /**
     *ラベル、テキストフィールド、変換ボタン、生成ボタン、ステップ実行ボタン、実行ボタンを作ります。
     */
	public void init( ) {
        setLayout(null);
        //アプレットのサイズを1000に固定
        resize(APLLETSIZE,APLLETSIZE);
        
        Label label = new Label("中置記法の式");
        label.setBounds(LEFTWIDTH,UPPERWIDTH,LABELWIDTH,LABELHEIGHT);
        this.add(label);
       
        this.expressionField = new TextField(30);
        this.expressionField.setBounds(LEFTWIDTH+LABELWIDTH,UPPERWIDTH,TEXTWIDTH,LABELHEIGHT);
        this.add(this.expressionField);

        //setLayout(new FlowLayout(FlowLayout.RIGHT,-25,30));
        //setLayout(new FlowLayout(FlowLayout.RIGHT));
        
		this.conversionButton = new Button("変換");
        this.conversionButton.setBounds(APLLETSIZE-WIDTHBUTTON*3-STEPWIDTHBUTTON,HEIGHTBUTTON,WIDTHBUTTON,APLLETSIZE-HEIGHTBUTTON);
        this.conversionButton.addActionListener(this);
        this.add(this.conversionButton);
		
        
        this.formButton = new Button("生成");
        this.formButton.setBounds(APLLETSIZE-WIDTHBUTTON*2-STEPWIDTHBUTTON,HEIGHTBUTTON,WIDTHBUTTON,APLLETSIZE-HEIGHTBUTTON);
        this.formButton.addActionListener(this);
        this.add(this.formButton);
      
        
        this.stepExecuteButton = new Button("ステップ実行");
        this.stepExecuteButton.setBounds(APLLETSIZE-WIDTHBUTTON-STEPWIDTHBUTTON,HEIGHTBUTTON,STEPWIDTHBUTTON,APLLETSIZE-HEIGHTBUTTON);
        this.add(this.stepExecuteButton);
        this.stepExecuteButton.addActionListener(this);
        
        this.executeButton = new Button("実行");
        this.executeButton.setBounds(APLLETSIZE-WIDTHBUTTON,HEIGHTBUTTON,WIDTHBUTTON,APLLETSIZE-HEIGHTBUTTON);
        this.add(this.executeButton);
        this.executeButton.addActionListener(this);
	}

    /**
     *それぞれのボタンに応じた処理を行います。
     *テキストフィールドに入力された式の長さによって、表示する大きさを設定します。
     */
    public void actionPerformed(ActionEvent evt) {
	Button button = (Button)evt.getSource();
        
        if(this.expressionField.getText().length() < TEXTLIMIT){
            WIDTH = 10;
            SMALLHEIGHT = 30;
            BIGHEIGHT = 45;
            BODYSIZE = 24;
            HEADINGSIZE = 26;
        }
        else{
            WIDTH = 10;
            SMALLHEIGHT = 20;
            BIGHEIGHT = 28;
            BODYSIZE = 15;
            HEADINGSIZE = 17;
        }
        
        
        if (button == conversionButton){
            this.pushConversion = true;
            this.result = 0;
            this.infixNotation = this.expressionField.getText();
            BinarySearchTree treeIns = new BinarySearchTree(infixNotation);
            treeIns.postfixNotationReset();
            treeIns.insert();
            this.postfixNotation = treeIns.traversePostorder();
        }
        
        if(button == formButton){
            this.pushForm = true;
            FormOrder formulaIns = new FormOrder(this.postfixNotation);
            this.order = formulaIns.formOrderFromFormula();
            this.firstStackSize = formulaIns.getStackSize();
            this.machineIns = new StackMachine(this.firstStackSize);
            this.formulaReader = new OrderReader(order,this.machineIns);
            this.stack = this.machineIns.getStack();
        }
        
        if(button == stepExecuteButton){
            this.pushStepExecute = true;
            this.formulaReader.stepReadOrder();
            this.stackSize = this.machineIns.getStackSize();
            if(machineIns.getWrt()){
                this.wrt = true;
                this.result = Math.round(stack[0]);
            }
        }
        
        if(button == executeButton){
            this.formulaReader.readOrder();
            this.wrt = true;
            this.result = Math.round(stack[0]);
        }
       
	    this.repaint();
	}

    /**
     *それぞれのボタンに応じた表示をします。
     */
	public void paint(Graphics g) {
        
        int height =UPPERSHOW;
        Font headingFont = new Font("Arial BOLD ITALIC",Font.BOLD,HEADINGSIZE);
        Font bodyFont = new Font("TimesRoman",Font.BOLD,BODYSIZE);
        
        if (this.pushConversion){
		g.setColor(Color.red);
            g.setFont(headingFont);
        g.drawString("後置記法",WIDTH,height);
            height += SMALLHEIGHT;
            g.setFont(bodyFont);
        g.drawString(this.postfixNotation,WIDTH,height);
        }
        
        if(this.pushForm){
            g.setColor(Color.blue);
            g.setFont(headingFont);
            height += BIGHEIGHT;
            g.drawString("スタックマシンの命令列",WIDTH,height);
            g.setFont(bodyFont);
            
            Iterator<String> it = order.iterator();
            while(it.hasNext()){
                height += SMALLHEIGHT;
                g.drawString(it.next(),WIDTH,height);
            }
        }
        
        if(this.pushStepExecute){
            g.setColor(Color.black);
            for(int i=0;i<this.stackSize;i++){
                g.drawRect(350,300-50*i,100,50);
                //g.drawRect(350,(height-100)-50*i,100,50);
                if(stack[i] == 2147483647)System.out.println("error");
                else{g.drawString(""+this.stack[i],385,332-50*i);
                }
            }
        }
       
        if(this.wrt){
            g.setColor(Color.green);
            g.setFont(headingFont);
            height += SMALLHEIGHT;
            g.drawString("計算結果",WIDTH,height);
             height += SMALLHEIGHT;
             Font font = new Font("TimesRoman",Font.BOLD,BODYSIZE);
             g.setFont(bodyFont);
             if(this.result == 2147483647){
                 g.drawString("error",WIDTH,height);
             }
             else{
             g.drawString(String.valueOf(this.result),WIDTH,height);
             }
         }
    }
}

