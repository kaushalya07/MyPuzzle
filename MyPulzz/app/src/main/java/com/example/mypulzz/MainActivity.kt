package com.example.mypulzz

import android.graphics.Color
import android.location.SettingInjectorService
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {

    private var mainView: ViewGroup?=null
    private  var board: Board?=null
    private  var boardView: BoardView?=null
    private lateinit var moves: TextView
    private var boardSize = 4

    private lateinit var gameViewModel: GameViewModel

    private var numOfMoves: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            numOfMoves = savedInstanceState.getInt("numOfMoves", 0)
        }else {
            numOfMoves = 0 // Reset the move count to 0 when the activity is created
        }


        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        setSupportActionBar(findViewById(R.id.toolbar))
        mainView = findViewById(R.id.mainView)
        moves = findViewById(R.id.moves)
        moves.setTextColor(Color.RED)
        moves.textSize = 22f
        newGame()

        val puzzleSize = PreferencesManager.getPuzzleSize(this)


        // Retrieve high score from shared preferences
        val highScore = PreferencesManager.getHighScore(this)
        val highScoreTextView = findViewById<TextView>(R.id.highScoreTextView)


        // Update TextView with high score
        highScoreTextView.text = "High Score: $highScore"
        highScoreTextView.visibility = View.VISIBLE // Show the TextView

    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the current move count
        outState.putInt("numOfMoves", numOfMoves)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restore the move count when the activity is recreated
        numOfMoves = savedInstanceState.getInt("numOfMoves", 0)
        // Update the moves TextView
        moves.text = "Number of movements: $numOfMoves"
    }

    private fun newGame() {
        board = Board(boardSize)
        board!!.addBoardChangeListener(boardChangeListener)
        board!!.rearrange()
        mainView!!.removeView(boardView)
        boardView = BoardView(this, board!!)
        mainView!!.addView(boardView)
        moves.text = "Number of movements : 0"
    }

    fun changeSize(newSize:Int){
        if(newSize!=boardSize){
            boardSize=newSize
            newGame()
            boardView!!.invalidate()
        }
    }
    private val boardChangeListener: BoardChangeListener = object : BoardChangeListener {
        override fun tileSlid(from: Place?, to: Place?, numOfMoves: Int) {

            this@MainActivity.numOfMoves = numOfMoves
            moves.text = "Number of movements ${numOfMoves}"
        }


    override fun solved(numOfMoves: Int) {
        this@MainActivity.numOfMoves = numOfMoves
        moves.text = "Solved in ${numOfMoves} moves"

        // Calculate current score based on the number of moves
        val maxScore = 1000 // maximum score achievable
        val minScore = 100 // minimum score achievable
        val maxMoves = 100 // maximum number of moves allowed
        val currentScore = maxScore - (numOfMoves * (maxScore - minScore) / maxMoves)

        // Save the high score
        PreferencesManager.saveHighScore(this@MainActivity, currentScore)


        AlertDialog.Builder(this@MainActivity)
            .setTitle("You won ..!!")
            .setIcon(R.drawable.ic_celebration)
            .setMessage("you are win $numOfMoves moves....!!\n if you want a new Game..!!")
            .setPositiveButton("Yes") { dialog, _ ->
                board!!.rearrange()
                moves.text = "Number of movements : 0"
                boardView!!.invalidate()

                dialog.dismiss()
            }

            .setNegativeButton("No") { dialog, _ ->

                dialog.dismiss()
            }

            .create()
            .show()

    }
}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_new_game->{
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("New Game")
                    .setIcon(R.drawable.ic_new_game)
                    .setMessage("Are you sure want to begin a New game ???")
                    .setPositiveButton( "Yse"){
                        dialog,_->
                        board!!.rearrange()
                        moves.text = "Number of movements : 0"
                        boardView!!.invalidate()
                        dialog.dismiss()
                    }
                    .setNegativeButton("No"){
                            dialog,_->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
                true
            }
            R.id.action_settings->{
                val setting=SettingsDialogFragment(boardSize)
                setting.show(supportFragmentManager,"fragment_settings")
                true
            }
            R.id.action_help->{
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Instructions")
                    .setMessage("the goal of the puzzle is to place the tiles in order by making sliding moves that" +
                            "  use the empty space.The only valid moves are to move a tile which is immediately adjacent to the blank into th location of the blank ")
                    .setPositiveButton("Understood.let's Play !!"){
                            dialog,_->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}



