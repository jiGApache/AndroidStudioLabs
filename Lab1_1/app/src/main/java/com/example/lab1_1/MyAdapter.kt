package com.example.lab1_1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.lang.StringBuilder

public class MyAdapter(private val values: ArrayList<Int>, val context: Context) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.rv_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(values[position]%2 == 0){
            holder.linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.grey))
        }
        else{
            holder.linearLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
        holder.textView.text = parseToWords(values[position])
    }

    override fun getItemCount() = values.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var linearLayout: LinearLayout = itemView.findViewById(R.id.LinearLayout)
        var textView: TextView = itemView.findViewById(R.id.textView)
    }

    fun parseToWords(firstElement: Int) : String{
        var element = firstElement
        val decs : Int = 10
        val hundreds : Int = 100
        val thousands : Int = 1000
        var number = StringBuilder()

        if(element/thousands != 0){
            var T  = element/thousands
            if(T/hundreds != 0){
                when(T/hundreds){
                    1->number.append(context.resources.getString(R.string.Hundred)+" ")
                    2->number.append(context.resources.getString(R.string.TwoHundred)+" ")
                    3->number.append(context.resources.getString(R.string.ThreeHundred)+" ")
                    4->number.append(context.resources.getString(R.string.FourHundred)+" ")
                    5->number.append(context.resources.getString(R.string.FiveHundred)+" ")
                    6->number.append(context.resources.getString(R.string.SixHundred)+" ")
                    7->number.append(context.resources.getString(R.string.SevenHundred)+" ")
                    8->number.append(context.resources.getString(R.string.EightHundred)+" ")
                    9->number.append(context.resources.getString(R.string.NineHundred)+" ")
                }
                T%=hundreds
            }
            when(T){
                0->number.append(context.resources.getString(R.string.Thousand)+" ")
                1->number.append(context.resources.getString(R.string.One_a)+" "+context.resources.getString(R.string.Thousand_end_A)+" ")
                2->number.append(context.resources.getString(R.string.Two_e)+" "+context.resources.getString(R.string.Thousand_end_И)+" ")
                3->number.append(context.resources.getString(R.string.Three)+" "+context.resources.getString(R.string.Thousand_end_И)+" ")
                4->number.append(context.resources.getString(R.string.Four)+" "+context.resources.getString(R.string.Thousand_end_И)+" ")
                5->number.append(context.resources.getString(R.string.Five)+" "+context.resources.getString(R.string.Thousand)+" ")
                6->number.append(context.resources.getString(R.string.Six)+" "+context.resources.getString(R.string.Thousand)+" ")
                7->number.append(context.resources.getString(R.string.Seven)+" "+context.resources.getString(R.string.Thousand)+" ")
                8->number.append(context.resources.getString(R.string.Eight)+" "+context.resources.getString(R.string.Thousand)+" ")
                9->number.append(context.resources.getString(R.string.Nine)+" "+context.resources.getString(R.string.Thousand)+" ")
                10->number.append(context.resources.getString(R.string.Ten)+" "+context.resources.getString(R.string.Thousand)+" ")
                11->number.append(context.resources.getString(R.string.Eleven)+" "+context.resources.getString(R.string.Thousand)+" ")
                12->number.append(context.resources.getString(R.string.Twelve)+" "+context.resources.getString(R.string.Thousand)+" ")
                13->number.append(context.resources.getString(R.string.Thirteen)+" "+context.resources.getString(R.string.Thousand)+" ")
                14->number.append(context.resources.getString(R.string.Fourteen)+" "+context.resources.getString(R.string.Thousand)+" ")
                15->number.append(context.resources.getString(R.string.Fifteen)+" "+context.resources.getString(R.string.Thousand)+" ")
                16->number.append(context.resources.getString(R.string.Sixteen)+" "+context.resources.getString(R.string.Thousand)+" ")
                17->number.append(context.resources.getString(R.string.Seventeen)+" "+context.resources.getString(R.string.Thousand)+" ")
                18->number.append(context.resources.getString(R.string.Eighteen)+" "+context.resources.getString(R.string.Thousand)+" ")
                19->number.append(context.resources.getString(R.string.Nineteen)+" "+context.resources.getString(R.string.Thousand)+" ")
                else->{
                    when (T / decs) {
                        2 -> number.append(context.resources.getString(R.string.Twenty) + " ")
                        3 -> number.append(context.resources.getString(R.string.Thirty) + " ")
                        4 -> number.append(context.resources.getString(R.string.Forty) + " ")
                        5 -> number.append(context.resources.getString(R.string.Fifty) + " ")
                        6 -> number.append(context.resources.getString(R.string.Sixty) + " ")
                        7 -> number.append(context.resources.getString(R.string.Seventy) + " ")
                        8 -> number.append(context.resources.getString(R.string.Eighty) + " ")
                        9 -> number.append(context.resources.getString(R.string.Ninety) + " ")
                    }
                    T %= decs
                    when (T) {
                        0 -> number.append(context.resources.getString(R.string.Thousand) + " ")
                        1 -> number.append(context.resources.getString(R.string.One_a) + " " + context.resources.getString(R.string.Thousand_end_A) + " ")
                        2 -> number.append(context.resources.getString(R.string.Two_e) + " " + context.resources.getString(R.string.Thousand_end_И) + " ")
                        3 -> number.append(context.resources.getString(R.string.Three) + " " + context.resources.getString(R.string.Thousand_end_И) + " ")
                        4 -> number.append(context.resources.getString(R.string.Four) + " " + context.resources.getString(R.string.Thousand_end_И) + " ")
                        5 -> number.append(context.resources.getString(R.string.Five) + " " + context.resources.getString(R.string.Thousand) + " ")
                        6 -> number.append(context.resources.getString(R.string.Six) + " " + context.resources.getString(R.string.Thousand) + " ")
                        7 -> number.append(context.resources.getString(R.string.Seven) + " " + context.resources.getString(R.string.Thousand) + " ")
                        8 -> number.append(context.resources.getString(R.string.Eight) + " " + context.resources.getString(R.string.Thousand) + " ")
                        9 -> number.append(context.resources.getString(R.string.Nine) + " " + context.resources.getString(R.string.Thousand) + " ")
                    }
                }
            }
            element%=thousands
        }
        if(element/hundreds != 0){
            when(element/hundreds){
                1->number.append(context.resources.getString(R.string.Hundred)+" ")
                2->number.append(context.resources.getString(R.string.TwoHundred)+" ")
                3->number.append(context.resources.getString(R.string.ThreeHundred)+" ")
                4->number.append(context.resources.getString(R.string.FourHundred)+" ")
                5->number.append(context.resources.getString(R.string.FiveHundred)+" ")
                6->number.append(context.resources.getString(R.string.SixHundred)+" ")
                7->number.append(context.resources.getString(R.string.SevenHundred)+" ")
                8->number.append(context.resources.getString(R.string.EightHundred)+" ")
                9->number.append(context.resources.getString(R.string.NineHundred)+" ")
            }
            element%=hundreds
        }
        when(element){
            1->number.append(context.resources.getString(R.string.One)+" ")
            2->number.append(context.resources.getString(R.string.Two)+" ")
            3->number.append(context.resources.getString(R.string.Three)+" ")
            4->number.append(context.resources.getString(R.string.Four)+" ")
            5->number.append(context.resources.getString(R.string.Five)+" ")
            6->number.append(context.resources.getString(R.string.Six)+" ")
            7->number.append(context.resources.getString(R.string.Seven)+" ")
            8->number.append(context.resources.getString(R.string.Eight)+" ")
            9->number.append(context.resources.getString(R.string.Nine)+" ")
            10->number.append(context.resources.getString(R.string.Ten)+" ")
            11->number.append(context.resources.getString(R.string.Eleven)+" ")
            12->number.append(context.resources.getString(R.string.Twelve)+" ")
            13->number.append(context.resources.getString(R.string.Thirteen)+" ")
            14->number.append(context.resources.getString(R.string.Fourteen)+" ")
            15->number.append(context.resources.getString(R.string.Fifteen)+" ")
            16->number.append(context.resources.getString(R.string.Sixteen)+" ")
            17->number.append(context.resources.getString(R.string.Seventeen)+" ")
            18->number.append(context.resources.getString(R.string.Eighteen)+" ")
            19->number.append(context.resources.getString(R.string.Nineteen)+" ")
            else->{
                when(element/decs){
                    2->number.append(context.resources.getString(R.string.Twenty)+" ")
                    3->number.append(context.resources.getString(R.string.Thirty)+" ")
                    4->number.append(context.resources.getString(R.string.Forty)+" ")
                    5->number.append(context.resources.getString(R.string.Fifty)+" ")
                    6->number.append(context.resources.getString(R.string.Sixty)+" ")
                    7->number.append(context.resources.getString(R.string.Seventy)+" ")
                    8->number.append(context.resources.getString(R.string.Eighty)+" ")
                    9->number.append(context.resources.getString(R.string.Ninety)+" ")
                }
                element%=decs
                when(element){
                    1->number.append(context.resources.getString(R.string.One))
                    2->number.append(context.resources.getString(R.string.Two))
                    3->number.append(context.resources.getString(R.string.Three))
                    4->number.append(context.resources.getString(R.string.Four))
                    5->number.append(context.resources.getString(R.string.Five))
                    6->number.append(context.resources.getString(R.string.Six))
                    7->number.append(context.resources.getString(R.string.Seven))
                    8->number.append(context.resources.getString(R.string.Eight))
                    9->number.append(context.resources.getString(R.string.Nine))
                }
            }
        }
        return number.toString()
    }
}