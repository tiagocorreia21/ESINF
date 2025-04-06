package graph;

import java.util.ArrayList;
import java.util.List;

public class ExamExercise {

    public List<ArrayList<String>> bipartido(Graph<String, Integer> g) {

        List<ArrayList<String>> res = new ArrayList<>();

        ArrayList<String> lst1 = new ArrayList<>();
        ArrayList<String> lst2 = new ArrayList<>();

        for (String vert : g.vertices()) {
            boolean lst1ok = true, lst2ok = true;

            for (String vadj : g.adjVertices(vert)) {

                if (lst1.contains(vadj)) {
                    lst1ok = false;
                }
                if (lst2.contains(vadj)) {
                    lst2ok = false;
                }
            }

            if (!lst1ok && !lst2ok) return null;

            if (lst1ok) {

                lst1.add(vert);

            } else {
                lst2.add(vert);
            }
        }
        res.add(lst1);
        res.add(lst2);

        return res;
    }
}
