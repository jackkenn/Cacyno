package com._2_ug_1.cacyno.game;

import java.util.Arrays;
import java.lang.Integer;
import java.util.HashSet;
import java.util.Set;

public class HandChecker {

    public Hands getHandRank(int[] cards) {
        for(int i=0; i<4; i++)
        {
            if(Arrays.asList(cards).contains(12+i*13)&&Arrays.asList(cards).contains(11+i*13)&&Arrays.asList(cards).contains(10+i*13)&&Arrays.asList(cards).contains(9+i*13)&&Arrays.asList(cards).contains(8+i*13)) {
                return Hands.ROYALFLUSH;
            }
        }
        Integer ranks[] = new Integer[7];
        Integer suits[] = new Integer[7];
        Integer ranksSet[];
        Integer suitsSet[];
        boolean suitSimilarity;
        for (int i = 0; i <= 7; i++) {
            ranks[i] = cards[i] % 13;
            suits[i] = cards[i] / 13;
        }
        Arrays.sort(ranks);
        Arrays.sort(suits);
        ranksSet = (Integer[]) Set.copyOf(Arrays.asList(ranks)).toArray();
        suitsSet = (Integer[]) Set.copyOf(Arrays.asList(suits)).toArray();

        if(ranks[0]==8&&ranks[1]==9&&ranks[2]==10&&ranks[3]==11&&ranks[4]==12){
            if(suitSimilarity) {
                return Hands.ROYALFLUSH;
            }
        }

        if(ranks[0]+1==ranks[1]&&ranks[0]+2==ranks[2]&&ranks[0]+3==ranks[3]&&ranks[0]+4==ranks[4] || ranks[0]==12&&ranks[1]==0&&ranks[2]==1&&ranks[3]==2&&ranks[4]==3) {
            if(suitSimilarity) {
                return Hands.STRAIGHTFLUSH;
            } else {
                return Hands.STRAIGHT;
            }
        }

        for(int i=0; i<4; i++) {
            for(int j=i; j<6; j++) {
                if (ranksSet[i] == ranksSet[i + 1] && ranksSet[j] == ranksSet[j + 1]) {
                    return Hands.TWOPAIR;
                }
            }
        }

        for(int i=0; i<7; i++) {
            if(ranksSet[i] == ranksSet[i + 1]){
                return Hands.PAIR;
            }
        }


        return Hands.HIGHCARD;
    }

    private boolean checkSuitSimilarity(Integer[] suits) {
        return suits[0] == suits[1] && suits[0] == suits[2] && suits[0] == suits[3] && suits[0] == suits[4] && suits[0] == suits[5] && suits[0] == suits[6];
    }
}
