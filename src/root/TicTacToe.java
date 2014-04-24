/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gregory ANNE <gregonmac@gmail.com>
 */
public class TicTacToe {

    public HashMap<Integer, HashMap<Integer, String>> values;

    public TicTacToe() {
        this.values = new HashMap<Integer, HashMap<Integer, String>>();
        HashMap<Integer, String> tmp = new HashMap<Integer, String>();
        tmp.put(1, ".");
        tmp.put(2, ".");
        tmp.put(3, ".");
        tmp.put(4, ".");
        this.values.put(1, tmp);
        this.values.put(2, (HashMap<Integer, String>) tmp.clone());
        this.values.put(3, (HashMap<Integer, String>) tmp.clone());
        this.values.put(4, (HashMap<Integer, String>) tmp.clone());
    }

    public void addValue(char value, int x, int y) {

        this.values.get(y).put(x, String.valueOf(value));
    }

    public String getResult() {
        String winner = "";
        Boolean complete = true;
        for (int x = 1; x <= 4; x++) {
            HashMap<Integer, String> datas = new HashMap<Integer, String>();
            for (int i = 1; i <= 4; i++) {
                datas.put(i, values.get(x).get(i));
            }
            if (datas.get(1).equals(datas.get(2))
                    && datas.get(2).equals(datas.get(3))
                    && datas.get(3).equals(datas.get(4))) {
                if (datas.get(1).equals('O') || datas.get(1).equals('X')) {
                    return datas.get(1) + " won";
                }
            }
        }
        for (int y = 1; y <= 4; y++) {
            HashMap<Integer, String> datas = values.get(y);
            if (datas.get(1).equals(datas.get(2)) && datas.get(2).equals(datas.get(3)) && datas.get(3).equals(datas.get(4))) {
                if (datas.get(1).equals('O') || datas.get(1).equals('X')) {
                    return datas.get(1) + " won";
                }
            }
        }

        return winner;
    }
}
