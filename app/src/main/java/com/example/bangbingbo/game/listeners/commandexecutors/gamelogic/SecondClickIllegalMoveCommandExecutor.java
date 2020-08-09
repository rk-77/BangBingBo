package com.example.bangbingbo.game.listeners.commandexecutors.gamelogic;

import com.example.bangbingbo.game.enums.PieceClickStatusEvaluated;
import com.example.bangbingbo.game.listeners.commandexecutors.ui.FirstClickLegalUICommandExecutor;
import com.example.bangbingbo.viewmodels.DefaultActivityViewModel;
import com.example.bangbingbo.views.DefaultActivity;

public class SecondClickIllegalMoveCommandExecutor implements CommandExecutor {

    private static SecondClickIllegalMoveCommandExecutor secondClickIllegalMoveCommandExecutor;

    private SecondClickIllegalMoveCommandExecutor() {
    }

    public static synchronized SecondClickIllegalMoveCommandExecutor getInstance() {
        if (secondClickIllegalMoveCommandExecutor == null) {
            return secondClickIllegalMoveCommandExecutor = new SecondClickIllegalMoveCommandExecutor();
        }
        return secondClickIllegalMoveCommandExecutor;
    }

    @Override
    public void executeCommands(DefaultActivity defaultActivity) {
        DefaultActivityViewModel viewModel = defaultActivity.getViewModel();
        viewModel.boardClickListener.onGamePieceClickedEvaluated(viewModel.getPieceClickStatusEvaluated());
    }
}
