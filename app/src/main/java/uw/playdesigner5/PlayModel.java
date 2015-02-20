package uw.playdesigner5;

/**
 * Created by lybar_000 on 2/20/2015.
 */
// see http://techlovejump.com/android-sqlite-database-tutorial/
public class PlayModel {

    public int index;
    public int player_id;
    public int position_x;
    public int position_y;


    public PlayModel(int index, int player_id, int position_x, int  position_y) {
        // TODO Auto-generated constructor stub
        this.index = index;
        this.player_id = player_id;
        this.position_x = position_x;
        this.position_y = position_y;

    }
    public PlayModel(){

    }

}
