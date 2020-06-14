package com.example.bangbingbo.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GameBoardManager {

    public enum BoardType {

        NORMAL(5),
        BIG(7);

        private final int boardLength;

        BoardType(int boardLength) {
            this.boardLength = boardLength;
        }

        public int getBoardLength () {
            return this.boardLength;
        }

        public int getBoardSize() {
            return  this.boardLength* this.boardLength;
        }
    }

    public static int calcNumberOfElementsForBoardType(BoardType boardType) {
        return BoardType.NORMAL == boardType ? 5 : 7;
    }

    public static List<Drawable> getTileDrawablesForBoardType(Context context, BoardType boardType) {
        List<Drawable> tiles = new ArrayList<>();
        String[] tileDrawables = getTileDrawables(boardType);

        for (String drawable : tileDrawables) {
            int id = context.getResources().getIdentifier(drawable, "drawable", context.getPackageName());
            Drawable tileDrawable = context.getResources().getDrawable(id);
            tiles.add(tileDrawable);
        }
        return tiles;
    }

    private static String[] getTileDrawables(BoardType boardType) {
        if (boardType == BoardType.BIG) {
            return new String[]{"circle_red", "circle_black", "circle_blue", "circle_yellow", "circle_green", "circle_purple", "circle_white"};
        }
        return new String[]{"circle_red", "circle_black", "circle_blue", "circle_yellow", "circle_green"};
    }

    public static List<ImageView> getTileViews(Context context, BoardType boardType) {
        List<ImageView> imageList = new ArrayList<>();
        String[] imageNames = getImageViewNames(boardType);

        ImageView imageView;
        for (String imageName : imageNames) {
            int id = context.getResources().getIdentifier(imageName, "id", context.getPackageName());
            imageView =  ((Activity) context).findViewById(id);
            imageView.setOnClickListener((View.OnClickListener) context);
            imageList.add(imageView);
        }
        return imageList;
    }

    private static String[] getImageViewNames(BoardType boardType) {
        if (boardType == BoardType.BIG) {
            return new String[]
                    {
                            "imageView_1_1", "imageView_1_2", "imageView_1_3", "imageView_1_4", "imageView_1_5", "imageView_1_6", "imageView_1_7",
                            "imageView_2_1", "imageView_2_2", "imageView_2_3", "imageView_2_4", "imageView_2_5", "imageView_2_6", "imageView_2_7",
                            "imageView_3_1", "imageView_3_2", "imageView_3_3", "imageView_3_5", "imageView_3_5", "imageView_3_6", "imageView_3_7",
                            "imageView_4_1", "imageView_4_2", "imageView_4_3", "imageView_4_4", "imageView_4_5", "imageView_4_6", "imageView_4_7",
                            "imageView_5_1", "imageView_5_2", "imageView_5_3", "imageView_5_4", "imageView_5_5", "imageView_5_6", "imageView_5_7",
                            "imageView_6_1", "imageView_6_2", "imageView_6_3", "imageView_6_4", "imageView_6_5", "imageView_6_6", "imageView_6_7",
                            "imageView_7_1", "imageView_7_2", "imageView_7_3", "imageView_7_4", "imageView_7_5", "imageView_7_6", "imageView_7_7"
                    };
        }

        return new String[]{
                "imageView_1_1", "imageView_1_2", "imageView_1_3", "imageView_1_4", "imageView_1_5",
                "imageView_2_1", "imageView_2_2", "imageView_2_3", "imageView_2_4", "imageView_2_5",
                "imageView_3_1", "imageView_3_2", "imageView_3_3", "imageView_3_4", "imageView_3_5",
                "imageView_4_1", "imageView_4_2", "imageView_4_3", "imageView_4_4", "imageView_4_5",
                "imageView_5_1", "imageView_5_2", "imageView_5_3", "imageView_5_4", "imageView_5_5"
        };
    }


}
