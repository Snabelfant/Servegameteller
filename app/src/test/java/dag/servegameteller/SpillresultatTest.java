package dag.servegameteller;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Created by Dag on 06.08.2017.
 */
public class SpillresultatTest {

    @Test
    public void test() throws Exception {
        Spillresultat r1 = new Spillresultat("A", "B", true);
        r1.spillFerdig(1,1,3);

        String[] line = r1.toStringArray();
        System.out.println(Arrays.asList(line));

        Spillresultat r2 = Spillresultat.fromStringArray(line);

        assertThat(r1.getDato(), notNullValue());
        assertThat(r1.getSpillerA(), is("A"));
        assertThat(r1.getSpillerB(), is("B"));
        assertThat(r1.getVarighet(), is(0));
        assertThat(r1.isaBegynte(), is(true));
        assertThat(r1.getGameVunnetA(), is(1));
        assertThat(r1.getServegameSpiltA(), is(2));
        assertThat(r1.getServegameVunnetA(), is(1));
        assertThat(r1.getGameVunnetB(), is(3));
        assertThat(r1.getServegameSpiltB(), is(2));
        assertThat(r1.getServegameVunnetB(), is(2));


        assertThat(r1.getDato(), is(r2.getDato()));
        assertThat(r1.getSpillerA(), is(r2.getSpillerA()));
        assertThat(r1.getSpillerB(), is(r2.getSpillerB()));
        assertThat(r1.getVarighet(), is(r2.getVarighet()));
        assertThat(r1.isaBegynte(), is(r2.isaBegynte()));
        assertThat(r1.getGameVunnetA(), is(r2.getGameVunnetA()));
        assertThat(r1.getServegameSpiltA(), is(r2.getServegameSpiltA()));
        assertThat(r1.getServegameVunnetA(), is(r2.getServegameVunnetA()));
        assertThat(r1.getGameVunnetB(), is(r2.getGameVunnetB()));
        assertThat(r1.getServegameSpiltB(), is(r2.getServegameSpiltB()));
        assertThat(r1.getServegameVunnetB(), is(r2.getServegameVunnetB()));
    }

    @Test
    public void test2() {
        Spillresultat r1 = new Spillresultat("A", "B", true);
        r1.spillFerdig(5,3,4);

        assertThat(r1.isaBegynte(), is(true));
        assertThat(r1.getGameVunnetA(), is(5));
        assertThat(r1.getServegameSpiltA(), is(5));
        assertThat(r1.getServegameVunnetA(), is(3));
        assertThat(r1.getGameVunnetB(), is(4));
        assertThat(r1.getServegameSpiltB(), is(4));
        assertThat(r1.getServegameVunnetB(), is(2));

    }

    @Test
    public void test3() {
        Spillresultat r1 = new Spillresultat("A", "B", false);
        r1.spillFerdig(5,3,4);

        assertThat(r1.isaBegynte(), is(false));
        assertThat(r1.getGameVunnetA(), is(5));
        assertThat(r1.getServegameSpiltA(), is(4));
        assertThat(r1.getServegameVunnetA(), is(3));
        assertThat(r1.getGameVunnetB(), is(4));
        assertThat(r1.getServegameSpiltB(), is(5));
        assertThat(r1.getServegameVunnetB(), is(3));

    }

}