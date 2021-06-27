package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
private const val PNDG_OPR="pendingOperation"
private const val  OPRND1="operand1"
private const val OPRND_CHCK="operand1 stored"
class MainActivity : AppCompatActivity() {
    private lateinit var result:EditText
    private lateinit var newNumber:EditText
    private val dOpr by lazy(LazyThreadSafetyMode.NONE){findViewById<TextView>(R.id.textView)}
    private var operand1:Double?=null
    private var operand2: Double=0.0
    private var pendingOp="="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        result=findViewById<EditText>(R.id.result)
        newNumber=findViewById<EditText>(R.id.number)

        val btn0:Button=findViewById<Button>(R.id.button0)
        val btn1:Button=findViewById<Button>(R.id.button1)
        val btn2:Button=findViewById<Button>(R.id.button2)
        val btn3:Button=findViewById<Button>(R.id.button3)
        val btn4:Button=findViewById<Button>(R.id.button4)
        val btn5:Button=findViewById<Button>(R.id.button5)
        val btn6:Button=findViewById<Button>(R.id.button6)
        val btn7:Button=findViewById<Button>(R.id.button7)
        val btn8:Button=findViewById<Button>(R.id.button8)
        val btn9:Button=findViewById<Button>(R.id.button9)
        val btnDot:Button=findViewById<Button>(R.id.buttonDot)
        val btnEql:Button=findViewById<Button>(R.id.buttoneql)
        val btnDiv:Button=findViewById<Button>(R.id.buttondiv)
        val btnMul:Button=findViewById<Button>(R.id.buttonmul)
        val btnSub:Button=findViewById<Button>(R.id.buttonsub)
        val btnAdd:Button=findViewById<Button>(R.id.buttonadd)
        val btnNeg:Button=findViewById<Button>(R.id.buttonNeg)

        btnNeg.setOnClickListener { view->
            var value=newNumber.text.toString()
            if(value.isNotEmpty()) try {
                var doubleValue=value.toDouble()
                doubleValue*=-1
                newNumber.setText(doubleValue.toString())
            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }
            else
            {
                newNumber.setText("-")
            }
        }

        val listener=View.OnClickListener{v->
            var b=v as Button
            newNumber.append(b.text)
        }
        btn0.setOnClickListener(listener)
        btn1.setOnClickListener(listener)
        btn2.setOnClickListener(listener)
        btn3.setOnClickListener(listener)
        btn4.setOnClickListener(listener)
        btn5.setOnClickListener(listener)
        btn6.setOnClickListener(listener)
        btn7.setOnClickListener(listener)
        btn8.setOnClickListener(listener)
        btn9.setOnClickListener(listener)
        btnDot.setOnClickListener(listener)
        val OpListener=View.OnClickListener{v->
            var op=(v as Button).text.toString()
            try {
                var value=newNumber.text.toString().toDouble()
                performOperation(value,op)
            }catch (e:NumberFormatException){
                newNumber.setText("")
            }

            pendingOp=op
            dOpr.text=pendingOp

        }
        btnAdd.setOnClickListener(OpListener)
        btnDiv.setOnClickListener(OpListener)
        btnMul.setOnClickListener(OpListener)
        btnSub.setOnClickListener(OpListener)
        btnEql.setOnClickListener(OpListener)

    }
    private fun performOperation(operand2:Double,opr:String)
    {
           if(operand1==null)
           {
               operand1=operand2
           }
        else
           {

               if(pendingOp=="=")
               {
                   pendingOp=opr
               }
               when(pendingOp){
                   "="->operand1=operand2
                   "/"-> operand1 = if(operand2==0.0){
                       Double.NaN
                   } else {
                       operand1!!/operand2
                   }
                   "*"->operand1=operand1!!*operand2
                   "+"->operand1=operand1!!+operand2
                   "-"->operand1=operand1!!-operand2
               }
           }
        result.setText(operand1.toString())
        newNumber.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(operand1!=null)
        {
            outState.putDouble(OPRND1,operand1!!)
            outState.putBoolean(OPRND_CHCK,true)
        }
        outState.putString(PNDG_OPR,pendingOp)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = if(savedInstanceState.getBoolean(OPRND_CHCK))
        {
            savedInstanceState.getDouble(OPRND1)
        }
        else
        {
            null
        }
        pendingOp=savedInstanceState.getString(PNDG_OPR)!!
        dOpr.text=pendingOp
    }
}