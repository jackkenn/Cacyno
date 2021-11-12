package com._2_ug_1.cacyno.game;

import com._2_ug_1.cacyno.models.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class HandChecker {


    public static void main(String args[]) {
        System.out.println(getHandRank(new int[]{1, 14, 27, 10, 24, 22, 40}));
    }

    public static Hands getHandRank(int[] cardsParam) {
        int ranks[] = new int[7];
        int suits[] = new int[7];
        Card cards[] = new Card[7];
        for (int i = 0; i <= 6; i++) {
            cards[i] = new Card(ranks[i] = cardsParam[i] % 13, suits[i] = cardsParam[i] / 13);
        }
        Arrays.sort(cards);
        Arrays.sort(ranks);
        Arrays.sort(suits);
        for (int i = 0; i <= 3; i++) {
            if (contains(cardsParam, i * 13 + 0) && contains(cardsParam, i * 13 + 9) && contains(cardsParam, i * 13 + 10) && contains(cardsParam, i * 13 + 11) && contains(cardsParam, i * 13 + 12)) {
                return Hands.ROYALFLUSH;
            }
        }

        for (int i = 0; i <= 2; i++) {
            for (int j = i+1; j <= 3; j++) {
                for (int k = j+1; k <= 4; k++) {
                    for (int l = k+1; l <= 5; l++) {
                        for (int m = l+1; m <= 6; m++) {
                            Card[] temp = new Card[]{cards[i], cards[j], cards[k], cards[l], cards[m]};
                            if (isStraightFlush(temp)) {
                                return Hands.STRAIGHTFLUSH;
                            }
                        }
                    }
                }
            }

        }


        try {
            for (int i = 0; i < 4; i++) {
                for (int j = i+1; j < 6; j++) {
                    if (ranks[i] == ranks[i + 1] && ranks[j] == ranks[j + 2] || ranks[i] == ranks[i + 2] && ranks[j] == ranks[j + 1]) {
                        return Hands.FULLHOUSE;
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {

        }

        if (suits[0] == suits[4] || suits[1] == suits[5] || suits[2] == suits[6]) {
            return Hands.FLUSH;
        }

        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            if (!temp.contains(ranks[i])) {
                temp.add(ranks[i]);
            }
        }
        Collections.sort(temp);

        for (int i = 0; i <= temp.size() - 5; i++) {
            if (temp.get(i) + 4 == temp.get(i + 4) || (temp.contains(0) && temp.contains(12) && temp.contains(11) && temp.contains(10) && temp.contains(9))) {
                return Hands.STRAIGHT;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (ranks[i] == ranks[i + 2]) {
                return Hands.THREEOFAKIND;
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = i+1; j < 6; j++) {
                if (ranks[i] == ranks[i + 1] && ranks[j] == ranks[j + 1]) {
                    return Hands.TWOPAIR;
                }
            }
        }

        for (int i = 0; i <= 5; i++) {
            if (ranks[i] == ranks[i + 1]) {
                return Hands.PAIR;
            }
        }


        return Hands.HIGHCARD;
    }

    private static boolean contains(int[] cardsParam, int num) {
        for (int i = 0; i < cardsParam.length; i++) {
            if (cardsParam[i] == num) {
                return true;
            }
        }
        return false;
    }

    private static boolean isStraightFlush(Card cards[]) {
        Arrays.sort(cards);
        if (cards[0].getRank() + 4 == cards[4].getRank()) {
            for (int i = 0; i <= 4; i++) {
                if (cards[0].getSuit() != cards[i].getSuit()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
