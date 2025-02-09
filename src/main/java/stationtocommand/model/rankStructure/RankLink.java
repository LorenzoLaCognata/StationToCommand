package stationtocommand.model.rankStructure;

public abstract class RankLink {

    private final Rank rank;

    public RankLink(Rank rank) {
        this.rank = rank;
    }

    public Rank getRank() {
        return rank;
    }
}