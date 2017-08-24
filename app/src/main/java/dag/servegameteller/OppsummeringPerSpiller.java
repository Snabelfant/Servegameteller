package dag.servegameteller;

/**
 * Created by Dag on 24.08.2017.
 */
public class OppsummeringPerSpiller {
    private String spillernavn;
    private int antallResultater;
    private int sumVarighet;
    private int sumGameSpilt;
    private int sumGameVunnet;
    private int sumServegameSpilt;
    private int sumServegameVunnet;

    public OppsummeringPerSpiller(String spillernavn, int antallResultater) {
        this.spillernavn = spillernavn;
        this.antallResultater = antallResultater;
    }

    public void addSumVarighet(int varighet) {
        this.sumVarighet += varighet;
    }

    public void addSumGameSpilt(int gameSpilt) {
        this.sumGameSpilt += gameSpilt;
    }

    public void addSumGameVunnet(int gameVunnet) {
        this.sumGameVunnet += gameVunnet;
    }

    public void addSumServegameSpilt(int servegameSpilt) {
        this.sumServegameSpilt += servegameSpilt;
    }

    public void addSumServegameVunnet(int servegameVunnet) {
        this.sumServegameVunnet += servegameVunnet;
    }


    public static String getOverskrift() {
        return "       #S SGS SGV  %V\n" +
                "---------------------\n";
    }
    public String toString() {
        return String.format("%-6s%3d%4d%4d%3d%%",
                spillernavn, antallResultater,
                sumServegameSpilt, sumServegameVunnet, (sumServegameVunnet * 100 / sumServegameSpilt));

    }
}

