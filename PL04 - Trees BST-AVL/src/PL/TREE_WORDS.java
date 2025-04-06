package PL;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TREE_WORDS extends BST<TextWord> {

    public void createTree() throws FileNotFoundException {

        Scanner readfile = new Scanner(new File("src/PL/xxx.xxx"));

        while (readfile.hasNextLine()) {

            String[] pal = readfile.nextLine().split("(\\,)|(\\s)|(\\.)");

            for (String word : pal)
                if (word.length() > 0)
                    insert(new TextWord(word, 1));
        }
        readfile.close();
    }

    /**
     * Inserts a new word in the tree, or increments the number of its occurrences.
     *
     * @param element
     */
    @Override
    public void insert(TextWord element) {

        if (element == null) throw new IllegalArgumentException("Element cannot be null.");

        root = insert(element, root);
    }

    private Node<TextWord> insert(TextWord element, Node<TextWord> node) {

        if (node == null)
            return new Node<>(element, null, null);

        if (element.compareTo(node.getElement()) < 0) {

            node.setLeft(insert(element, node.getLeft()));

        } else if (element.compareTo(node.getElement()) > 0) {

            node.setRight(insert(element, node.getRight()));

        } else {
            node.getElement().increment();
        }
        return node;
    }

    /**
     * Returns a map with a list of words for each occurrence found.
     *
     * @return a map with a list of words for each occurrence found.
     */
    public Map<Integer, List<String>> getWordsOccurrences() {

        Map<Integer, List<String>> occurrencesMap = new HashMap<>();

        if (root != null) {
            fillOccurrencesMap(root, occurrencesMap);
        }
        return occurrencesMap;
    }

    private void fillOccurrencesMap(Node<TextWord> node, Map<Integer, List<String>> map) {

        if (node == null) return;

        fillOccurrencesMap(node.getLeft(), map);

        int occurrences = node.getElement().getOcorrences();

        String word = node.getElement().getWord();

        map.computeIfAbsent(occurrences, k -> new ArrayList<>()).add(word);

        fillOccurrencesMap(node.getRight(), map);
    }
}
