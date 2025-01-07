package linkStructure.rankLinkModule;

import rankStructure.rankModule.Rank;

public abstract class RankLink {

    private final Rank rank;

    public RankLink(Rank rank) {
        this.rank = rank;
    }

    public Rank getRank() {
        return rank;
    }
}