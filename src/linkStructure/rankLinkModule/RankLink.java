package linkStructure.rankLinkModule;

import rankStructure.rankModule.Rank;

public abstract class RankLink {

    private Rank rank;

    public RankLink(Rank rank) {
        System.out.println("RankLink initializing");
        this.rank = rank;
        System.out.println("RankLink initialized successfully");
    }

}