package dag.servegameteller;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by Dag on 06.08.2017.
 */
public class HistorikkTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void test() throws Exception {
       File  csvFile = new File(folder.getRoot(), "test.csv");
        Historikk historikk = new Historikk(csvFile);
        List<Spillresultat> resultater = historikk.les();
        assertThat(resultater.size(), is(0));

        assertThat(historikk.getSpillere().size(), is(0));
        Spillresultat resultat = new Spillresultat("A","B", false);
        resultat.spillFerdig(3, 2, 3);
        historikk.leggTil(resultat);

        resultater = historikk.les();
        assertThat(resultater.size(), is(1));
        assertThat(resultater.get(0).getSpillerA(), is("A"));

        resultat = new Spillresultat("C", "D", false);
        resultat.spillFerdig(28, 22, 23);
        historikk.leggTil(resultat);
        resultater = historikk.les();
        assertThat(resultater.size(), is(2));
        assertThat(resultater.get(0).getSpillerA(), is("A"));
        assertThat(resultater.get(1).getSpillerA(), is("C"));

        resultat = new Spillresultat("C", "D", false);
        resultat.spillFerdig(211, 201, 231);
        historikk.leggTil(resultat);
        List<String> spillere = historikk.getSpillere();
        assertThat(spillere.size(), is(4));
        assertThat(spillere, is(Arrays.asList("A", "B", "C", "D")));

    }

}