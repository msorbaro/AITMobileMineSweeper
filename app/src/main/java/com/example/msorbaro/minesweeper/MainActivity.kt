package com.example.msorbaro.minesweeper

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

import android.support.design.widget.Snackbar
import com.example.msorbaro.minesweeper.model.MineSweeperModel
import com.example.msorbaro.minesweeper.ui.MSView




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRestart.setOnClickListener {
            mineSweeperView.restart()
        }

        btnMode.setOnClickListener {
            mineSweeperView.flipMode()
        }


        shimmerLayout.startShimmer()
    }

    private fun MSView.restart() {
        MineSweeperModel.resetModel()
        invalidate()
    }
    private fun MSView.flipMode() {
        MineSweeperModel.flipMode()
        if (btnMode.text == context.getString(R.string.ChangeToClick)){
            btnMode.text = context.getString(R.string.ChangeToFlag)
            mode.text = "You are in Click Mode"
        }
        else {
            btnMode.text = context.getString(R.string.ChangeToClick)
            mode.text = "You are in Flag Mode"
        }
        invalidate()
    }


    public fun showLoseMessage(msg: String) {
        Snackbar.make(mineSweeperView, msg, Snackbar.LENGTH_LONG)
                .setAction("Restart") {
                    MineSweeperModel.resetModel()
                    mineSweeperView.invalidate()
                }
                .show()
    }


}
