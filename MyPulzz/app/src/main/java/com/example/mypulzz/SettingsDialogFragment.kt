package com.example.mypulzz

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

import androidx.lifecycle.ViewModelProvider

class SettingsDialogFragment(var size :Int):DialogFragment() {

    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameViewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
            .setTitle("Define the Size of the Puzzle")
            .setSingleChoiceItems(R.array.size_options, gameViewModel.puzzleSize - 2) { dialog, which ->
                gameViewModel.puzzleSize = which + 2
            }
            .setPositiveButton("Change") { dialog, id ->
                (activity as MainActivity).changeSize(gameViewModel.puzzleSize)
                PreferencesManager.savePuzzleSize(requireContext(), gameViewModel.puzzleSize)

            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
        val settingDialog = builder.create()
        settingDialog.show()
        return settingDialog
    }

   /** override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder=AlertDialog.Builder(activity)
            .setTitle("Define the Size of the Puzzle")
            .setSingleChoiceItems(R.array.size_options,size-2){
                dialog,which->
                size=which+2
            }
            .setPositiveButton("Change"){
                dialog,id->
                (getActivity() as MainActivity).changeSize(size)
            }


            .setNegativeButton("Cancel"){
                dialog,_->dialog.cancel()
            }
        val settingDialog=builder.create()
        settingDialog.show()
        return settingDialog
    }*/
}