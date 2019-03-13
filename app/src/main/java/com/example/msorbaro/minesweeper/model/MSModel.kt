package com.example.msorbaro.minesweeper.model

data class Field(var type: Int, var minesAround: Int,
                 var isFlagged: Boolean, var wasClicked: Boolean)

object MineSweeperModel {


    private var mode = "CLICK"
    private var model =createModel()


    fun createModel() : Array<Array<Field>> {


        val fieldMatrix: Array<Array<Field>> = arrayOf(
                arrayOf(Field(0, 0, false, false),
                        Field(0, 0, false, false),
                        Field(0, 0, false, false),
                        Field(0, 0, false, false),
                        Field(0, 0, false, false)
                ),
                arrayOf(Field(0, 0, false, false),
                        Field(0, 0, false, false),
                        Field(0, 0, false, false),
                        Field(0, 0, false, false),
                        Field(0, 0, false, false)
                ),
                arrayOf(Field(0, 0, false, false),
                        Field(0, 0, false, false),
                        Field(0, 0, false, false),
                        Field(0, 0, false, false),
                        Field(0, 0, false, false)
                ),
                arrayOf(Field(0, 0, false, false),
                        Field(0, 0, false, false),
                        Field(0, 0, false, false),
                        Field(0, 0, false, false),
                        Field(0, 0, false, false)
                ),
                arrayOf(Field(0, 0, false, false),
                        Field(0, 0, false, false),
                        Field(0, 0, false, false),
                        Field(0, 0, false, false),
                        Field(0, 0, false, false)
                )
        )

        var x = 0
        while (x < 3)
        {
            x++
            val randomRow = (0..4).shuffled().first()
            val randomColumn = (0..4).shuffled().first()
            if(fieldMatrix[randomRow][randomColumn].type==1){
                x--
            }
            fieldMatrix[randomRow][randomColumn].type = 1

        }

        calculateNumbers(fieldMatrix)
        return fieldMatrix

    }

    fun calculateNumbers(fieldMatrix: Array<Array<Field>>) {
        for (x in 0..4){
            for (y in 0..4){
                if (fieldMatrix[x][y].type == 0){
                    fieldMatrix[x][y].minesAround = oneSquareMines(x, y, fieldMatrix)
                }
            }
        }
    }

    fun oneSquareMines(x: Int, y: Int, fieldMatrix: Array<Array<Field>>): Int{
        var count = 0;
        if (y-1 >=0 && fieldMatrix[x][y-1].type==1){
            count ++
        }
        if (y-1 >=0 && x-1 >= 0 && fieldMatrix[x-1][y-1].type==1){
            count ++
        }
        if (y-1 >=0 && x+1 < 5 && fieldMatrix[x+1][y-1].type==1){
            count ++
        }
        if (y+1 < 5 && fieldMatrix[x][y+1].type==1){
            count ++
        }
        if (y+1 < 5 && x-1 >= 0 && fieldMatrix[x-1][y+1].type==1){
            count ++
        }
        if (y+1 <5 && x+1 < 5 && fieldMatrix[x+1][y+1].type==1){
            count ++
        }
        if (x+1 < 5 && fieldMatrix[x+1][y].type==1){
            count ++
        }
        if (x-1 >=0 && fieldMatrix[x-1][y].type==1){
            count ++
        }
        return count

    }
    fun getFieldContent(x: Int, y: Int) = model[x][y].type
    fun getBombNumber(x: Int, y: Int) = model[x][y].minesAround
    fun isClicked(x: Int, y: Int) = model[x][y].wasClicked
    fun isFlaged(x: Int, y: Int) = model[x][y].isFlagged


    fun changeClicked(x: Int, y: Int) {
        if (model[x][y].wasClicked == false){
            model[x][y].wasClicked = !model[x][y].wasClicked

        }
    }

    fun changeClickedBack(x: Int, y: Int) {
        model[x][y].wasClicked = !model[x][y].wasClicked

    }


    fun changeFlaged(x: Int, y: Int) {
        if (model[x][y].isFlagged == true)
        {
            model[x][y].isFlagged = false
        }
        else {
            model[x][y].isFlagged= true
        }
    }


    fun getMode() = mode


    fun flipMode(){
        if (mode == "CLICK") {
            mode = "FLAG"
        }
        else {
            mode = "CLICK"
        }
    }

    fun resetModel() {
        model = createModel()
    }

}

