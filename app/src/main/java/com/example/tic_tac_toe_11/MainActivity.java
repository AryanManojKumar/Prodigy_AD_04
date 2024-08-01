package com.example.tic_tac_toe_11;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tic_tac_toe_11.R;

public class MainActivity extends AppCompatActivity {

    private boolean playerX = true;
    private int[][] board = new int[3][3]; // 0 for empty, 1 for X, 2 for O
    private TextView tvStatus;
    private Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);
        btnReset = findViewById(R.id.btnReset);
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        // Initialize buttons and set onClick listeners
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            final int row = i / 3;
            final int col = i % 3;
            Button btn = (Button) gridLayout.getChildAt(i);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onGridButtonClick(v, row, col);
                }
            });
            Button closeButton = findViewById(R.id.close);
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Close this activity
                }
            });

        }

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        resetGame();
    }

    private void onGridButtonClick(View v, int row, int col) {
        if (board[row][col] == 0) {
            board[row][col] = playerX ? 1 : 2;
            ((Button) v).setText(playerX ? "X" : "O");
            playerX = !playerX;
            tvStatus.setText(playerX ? "Player X's Turn" : "Player O's Turn");

            if (checkForWin()) {
                tvStatus.setText(playerX ? "Player O Wins!" : "Player X Wins!");
                disableButtons();
            } else if (checkForDraw()) {
                tvStatus.setText("It's a Draw!");
            }
        }
    }

    private boolean checkForWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true;
            }
            if (board[0][i] != 0 && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return true;
            }
        }
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return true;
        }
        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return true;
        }
        return false;
    }

    private boolean checkForDraw() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableButtons() {
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            gridLayout.getChildAt(i).setEnabled(false);
        }
    }

    private void resetGame() {
        playerX = true;
        tvStatus.setText("Player X's Turn");

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button btn = (Button) gridLayout.getChildAt(i);
            btn.setText("");
            btn.setEnabled(true);
        }

        for (int[] row : board) {
            for (int i = 0; i < row.length; i++) {
                row[i] = 0;
            }
        }
    }
}
