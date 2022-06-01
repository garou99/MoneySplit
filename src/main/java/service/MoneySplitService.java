package service;

import dao.Details;

import java.util.List;
import java.util.Map;

public class MoneySplitService {
    private Map<String, Integer> personToValue;

    private Map<Integer, String> valueToPerson;

    private int getMaximum(int amount[], int size) {
        int maxIndex = 0;
        for (int i = 1; i < size; i++) {
            if (amount[i] > amount[maxIndex])
                maxIndex = i;
        }
        return maxIndex;
    }

    private int getMinimum(int amount[], int size) {
        int minIndex = 0;
        for (int i = 1; i < size; i++) {
            if (amount[minIndex] > amount[i])
                minIndex = i;
        }
        return minIndex;
    }

    private String minCashFlow(int amount[], int size) {
        int maximumCredited = getMaximum(amount, size);
        int maximumDebited = getMinimum(amount, size);

        if (amount[maximumDebited] == 0 && amount[maximumCredited] == 0)
            return "";

        int minimumFromMaximumCreditedOrMaximumCredited = Math.min(-amount[maximumDebited], amount[maximumCredited]);
        amount[maximumCredited] -= minimumFromMaximumCreditedOrMaximumCredited;
        amount[maximumDebited] += minimumFromMaximumCreditedOrMaximumCredited;

        String pays = valueToPerson.get(maximumDebited) + " pays " + Integer.toString(minimumFromMaximumCreditedOrMaximumCredited)
                + " to " + valueToPerson.get(maximumCredited);

        return pays + minCashFlow(amount, size);
    }

    private String minCashFlow(int graph[][], int size) {
        int amount[] = new int[size];
        for (int person1 = 0; person1 < size; person1++) {
            for (int person2 = 0; person2 < size; person2++) {
                amount[person1] += (graph[person1][person2] - graph[person2][person1]);
            }
        }
        return minCashFlow(amount, size);
    }

    public String getSplitWiseMoney(List<Details> details) {
        for (int i = 0; i < details.size(); i++) {
            String name = details.get(i).getName();
            personToValue.put(name, i);
            valueToPerson.put(i, name);
        }
        int graph[][] = new int[details.size()][details.size()];
        for (int i = 0; i < details.size(); i++) {
            int debitedPerson = personToValue.get(details.get(i).getName());
            Map<String, Integer> persons = details.get(i).getPersons();
            for (Map.Entry<String, Integer> entry : persons.entrySet()) {
                int creditedPerson = personToValue.get(entry.getKey());
                int amount = entry.getValue();
                graph[debitedPerson][creditedPerson] += amount;
            }
        }
        return minCashFlow(graph, details.size());
    }
}
