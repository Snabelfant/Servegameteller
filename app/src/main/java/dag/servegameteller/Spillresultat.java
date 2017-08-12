package dag.servegameteller;

import org.threeten.bp.LocalDateTime;

/**
 * Created by Dag on 06.08.2017.
 */
public class Spillresultat {
    private LocalDateTime dato;
    private String spillerA;
    private String spillerB;
    private boolean aBegynte;
    private int gameVunnetA;
    private int servegameSpiltA;
    private int servegameVunnetA;
    private int gameVunnetB;
    private int servegameSpiltB;
    private int servegameVunnetB;

    public Spillresultat(String spillerA, String spillerB, boolean aBegynte) {
        this.dato = LocalDateTime.now();
        this.spillerA = spillerA;
        this.spillerB = spillerB;
        this.aBegynte = aBegynte;
    }

    private Spillresultat() {
    }

    public static String[] getKolonnenavn() {
        return new String[]{
                "dato",
                "spillerA",
                "spillerB",
                "aBegynte",
                "gameVunnetA",
                "servegameSpiltA",
                "servegameVunnetA",
                "gameVunnetB",
                "servegameSpiltB",
                "servegameVunnetB"
        };
    }

    public static Spillresultat fromStringArray(String[] line) {
        Spillresultat resultat = new Spillresultat();
        resultat.dato = LocalDateTime.parse(line[0]);
        resultat.spillerA = line[1];
        resultat.spillerB = line[2];
        resultat.aBegynte = Boolean.valueOf(line[3]);
        resultat.gameVunnetA = Integer.valueOf(line[4]);
        resultat.servegameSpiltA = Integer.valueOf(line[5]);
        resultat.servegameVunnetA = Integer.valueOf(line[6]);
        resultat.gameVunnetB = Integer.valueOf(line[7]);
        resultat.servegameSpiltB = Integer.valueOf(line[8]);
        resultat.servegameVunnetB = Integer.valueOf(line[9]);
        return resultat;
    }

    public LocalDateTime getDato() {
        return dato;
    }

    public String getSpillerA() {
        return spillerA;
    }

    public String getSpillerB() {
        return spillerB;
    }

    public boolean isaBegynte() {
        return aBegynte;
    }

    public int getGameVunnetA() {
        return gameVunnetA;
    }

    public int getServegameSpiltA() {
        return servegameSpiltA;
    }

    public int getServegameVunnetA() {
        return servegameVunnetA;
    }

    public int getGameVunnetB() {
        return gameVunnetB;
    }

    public int getServegameSpiltB() {
        return servegameSpiltB;
    }

    public int getServegameVunnetB() {
        return servegameVunnetB;
    }


    public String[] toStringArray() {
        return new String[]{
                dato.toString(),
                spillerA,
                spillerB,
                Boolean.toString(aBegynte),
                Integer.toString(gameVunnetA),
                Integer.toString(servegameSpiltA),
                Integer.toString(servegameVunnetA),
                Integer.toString(gameVunnetB),
                Integer.toString(servegameSpiltB),
                Integer.toString(servegameVunnetB)
        };
    }

    public void spillFerdig(int gameVunnetA,  int egneServeGameVunnetA, int gameVunnetB) {
        int gameSpiltTotalt = gameVunnetA + gameVunnetB;

        this.gameVunnetA = gameVunnetA;
        this.servegameSpiltA = aBegynte ? (gameSpiltTotalt+1) / 2 : gameSpiltTotalt/2;
        this.servegameVunnetA = egneServeGameVunnetA;

        this.gameVunnetB = gameVunnetB;
        this.servegameSpiltB =  aBegynte ? gameSpiltTotalt / 2 : (gameSpiltTotalt+1)/2;
        this.servegameVunnetB = gameVunnetB - (servegameSpiltA - servegameVunnetA);

        assert servegameSpiltA + servegameSpiltB == gameVunnetA + gameVunnetB;
    }
}
