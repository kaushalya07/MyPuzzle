package com.example.mypulzz

interface BoardChangeListener {
    fun tileSlid(from:Place?,to:Place?,numOfMoves:Int)

    fun solved(numOfMoves: Int)

}