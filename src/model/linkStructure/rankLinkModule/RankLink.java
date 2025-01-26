package model.linkStructure.rankLinkModule;

import model.rankStructure.rankModule.Rank;

public abstract class RankLink {

    private final Rank rank;

    public RankLink(Rank rank) {
        this.rank = rank;
    }

    public Rank getRank() {
        return rank;
    }
}