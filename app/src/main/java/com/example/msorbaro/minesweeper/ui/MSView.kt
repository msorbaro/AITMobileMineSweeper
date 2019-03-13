package com.example.msorbaro.minesweeper.ui;


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.msorbaro.minesweeper.model.MineSweeperModel
import com.example.msorbaro.minesweeper.MainActivity
import com.example.msorbaro.minesweeper.R

class MSView(context: Context?, attrs: AttributeSet?) : View(context, attrs)
{
    private val paintBackGround = Paint()
    private val paintLine = Paint()
    private val paintText = Paint()

    private var bitMapBg =
            BitmapFactory.decodeResource(resources, R.drawable.flag)

    private var bitMapBg2 =
            BitmapFactory.decodeResource(resources, R.drawable.bomb)

    init {
        paintBackGround.color = Color.CYAN
        paintBackGround.strokeWidth = 5F
        paintBackGround.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5F

        paintText.color = Color.MAGENTA
        paintText.textSize = 100F
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        paintText.textSize = height.toFloat()/5

        bitMapBg = Bitmap.createScaledBitmap(bitMapBg,
                width/5, height/5,false)

        bitMapBg2 = Bitmap.createScaledBitmap(bitMapBg2,
                width/5, height/5,false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(0f, 0f,
                width.toFloat(), height.toFloat(), paintBackGround)


        drawGameArea(canvas)

        drawPlayers(canvas)


    }

    fun drawGameArea(canvas: Canvas) {
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        canvas.drawLine(0f, (height / 5).toFloat(), width.toFloat(), (height / 5).toFloat(),
                paintLine)
        canvas.drawLine(0f, (2 * height / 5).toFloat(), width.toFloat(),
                (2 * height / 5).toFloat(), paintLine)

        canvas.drawLine(0f, (3 * height / 5).toFloat(), width.toFloat(),
                (3 * height / 5).toFloat(), paintLine)
        canvas.drawLine(0f, (4 * height / 5).toFloat(), width.toFloat(),
                (4 * height / 5).toFloat(), paintLine)

        canvas.drawLine((width / 5).toFloat(), 0f, (width / 5).toFloat(), height.toFloat(),
                paintLine)
        canvas.drawLine((2 * width / 5).toFloat(), 0f, (2 * width / 5).toFloat(), height.toFloat(),
                paintLine)

        canvas.drawLine((3 * width / 5).toFloat(), 0f, (3 * width / 5).toFloat(), height.toFloat(),
                paintLine)
        canvas.drawLine((4 * width / 5).toFloat(), 0f, (4 * width / 5).toFloat(), height.toFloat(),
                paintLine)
    }

    private fun drawPlayers(canvas: Canvas) {
        for (i in 0..4) {
            for (j in 0..4) {
                if (MineSweeperModel.getFieldContent(i, j) == 1 && MineSweeperModel.isClicked(i,j)==true
                        && MineSweeperModel.isFlaged(i, j) == true) {
                    canvas.drawBitmap(bitMapBg, (i * width / 5).toFloat(), (j * height / 5).toFloat(), null)
                }
                else if (MineSweeperModel.getFieldContent(i, j) == 1 && MineSweeperModel.isClicked(i,j)==true) {
                    canvas.drawBitmap(bitMapBg2, (i * width / 5).toFloat(), (j * height / 5).toFloat(), null)
                }
                else if (MineSweeperModel.getFieldContent(i, j) == 0 && MineSweeperModel.isClicked(i,j)==true) {
                    canvas.drawText(MineSweeperModel.getBombNumber(i, j).toString(), 40 + (i * width / 5).toFloat(), -25+ (j * height / 5 + (height/5)).toFloat(), paintText)
                }

            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val tX = event.x.toInt() / (width / 5)
        val tY = event.y.toInt() / (height / 5)
        if (tX < 5 && tY < 5 && MineSweeperModel.getMode() == "FLAG" && MineSweeperModel.getFieldContent(tX, tY) == 1){
            flagging(tX, tY)
            checkWin()

        }
        else if ((tX < 5 && tY < 5 && MineSweeperModel.getFieldContent(tX, tY) == 1) || (tX < 5 && tY < 5 && MineSweeperModel.getMode() == "FLAG" &&
                        MineSweeperModel.getFieldContent(tX, tY) == 0)) {
            lose(tX, tY )
        }
        else if (tX < 5 && tY < 5 && MineSweeperModel.getFieldContent(tX, tY) == 0 && MineSweeperModel.getMode()=="CLICK"){
            MineSweeperModel.changeClicked(tX, tY)
            click(tX, tY)
        }

        return super.onTouchEvent(event)
    }

    fun checkUp(x: Int, y: Int){
        if (MineSweeperModel.getBombNumber(x, y - 1) == 0 && MineSweeperModel.isClicked(x, y - 1) == false) {
            MineSweeperModel.changeClicked(x, y - 1)
            clearArea(x, y - 1)
        }
        MineSweeperModel.changeClicked(x, y - 1)
    }

    fun checkLeftUp(x: Int, y: Int){
        if (MineSweeperModel.getBombNumber(x-1, y-1) == 0 && MineSweeperModel.isClicked(x-1, y-1) == false){
            MineSweeperModel.changeClicked(x-1, y-1)
            clearArea(x-1, y-1)
        }
        MineSweeperModel.changeClicked(x-1, y-1)
    }

    fun checkRightUp(x: Int, y: Int) {
        if (MineSweeperModel.getBombNumber(x+1, y-1) == 0 && MineSweeperModel.isClicked(x+1, y-1) == false) {
            MineSweeperModel.changeClicked(x+1, y-1)
            clearArea(x+1, y-1)
        }
        MineSweeperModel.changeClicked(x+1, y-1)
    }

    fun checkDown(x: Int, y: Int){
        if (MineSweeperModel.getBombNumber(x, y+1) == 0 && MineSweeperModel.isClicked(x, y+1) == false){
            MineSweeperModel.changeClicked(x, y+1)
            clearArea(x, y+1)
        }
        MineSweeperModel.changeClicked(x, y+1)
    }

    fun checkDownLeft(x: Int, y: Int){
        if (MineSweeperModel.getBombNumber(x-1, y+1) == 0 && MineSweeperModel.isClicked(x-1, y+1) == false){
            MineSweeperModel.changeClicked(x-1, y+1)
            clearArea(x-1, y+1)
        }
        MineSweeperModel.changeClicked(x-1, y+1)
    }

    fun checkDownRight(x: Int, y: Int){
        if (MineSweeperModel.getBombNumber(x+1, y+1) == 0 && MineSweeperModel.isClicked(x+1, y+1) == false){
            MineSweeperModel.changeClicked(x+1, y+1)
            clearArea(x+1, y+1)
        }
        MineSweeperModel.changeClicked(x+1, y+1)
    }

    fun checkLeft(x: Int, y: Int){
        if (MineSweeperModel.getBombNumber(x-1, y) == 0 && MineSweeperModel.isClicked(x-1, y) == false){
            MineSweeperModel.changeClicked(x-1, y)
            clearArea(x-1, y)
        }

        MineSweeperModel.changeClicked(x-1, y)
    }

    fun checkRight(x: Int, y:Int ){
        if (MineSweeperModel.getBombNumber(x+1, y) == 0 && MineSweeperModel.isClicked(x+1, y) == false){
            MineSweeperModel.changeClicked(x+1, y)
            clearArea(x+1, y)
        }
        MineSweeperModel.changeClicked(x+1, y)
    }

    fun clearArea(x: Int, y: Int){
        if (y-1 >=0 ){
            checkUp(x, y)
        }
        if (y-1 >=0 && x-1 >= 0 ){
            checkLeftUp(x, y)
        }
        if (y-1 >=0 && x+1 < 5){
            checkRightUp(x, y)
        }
        if (y+1 < 5 ){
            checkDown(x, y)
        }

        if (y+1 < 5 && x-1 >= 0){
            checkDownLeft(x, y)
        }

        if (y+1 <5 && x+1 < 5){
            checkDownRight(x, y)
        }
        if (x+1 < 5 ){
            checkRight(x, y)
        }
        if (x-1 >=0 ){
            checkLeft(x, y)
        }
    }

    fun flagging(tX: Int, tY: Int) {
        MineSweeperModel.changeFlaged(tX,tY)

        if(MineSweeperModel.isClicked(tX, tY)== true) {
            MineSweeperModel.changeClickedBack(tX, tY)
        }
        else{
            MineSweeperModel.changeClicked(tX, tY)
        }

        invalidate()
    }

    fun lose(tX: Int, tY: Int){
        (context as MainActivity).showLoseMessage("You lose")
        MineSweeperModel.changeClicked(tX, tY)
        invalidate()
    }

    fun click(tX: Int, tY:Int){
        if(MineSweeperModel.getBombNumber(tX, tY) ==0){
            clearArea(tX, tY)
        }
        checkWin()
        invalidate()
    }

    fun checkWin(){
        for (i in 0..4){
            for (j in 0.. 4){
                if (! MineSweeperModel.isClicked(i, j)){
                    return
                }
            }
        }
        (context as MainActivity).showLoseMessage("You Win")
        invalidate()


    }


}

