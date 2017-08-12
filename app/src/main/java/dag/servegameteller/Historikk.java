package dag.servegameteller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Dag on 06.08.2017.
 */

public class Historikk {
    private File csvFile;
    private Context context;

    public Historikk(Context context) {
        csvFile = new File(context.getExternalFilesDir(null), "resultater.csv");
        Log.i("Historikk", "Fil=" +csvFile.getAbsolutePath());
        this.context = context;
    }

    Historikk(File csvFile) {
        this.csvFile = csvFile;
    }

    public List<String> getSpillere() {
        List<Spillresultat> resultater = les();
        Set<String> spillere = new HashSet<>();

        for (Spillresultat spillresultat : resultater) {
            spillere.add(spillresultat.getSpillerA());
            spillere.add(spillresultat.getSpillerB());
        }

        return new ArrayList<>(spillere);
    }

    public void leggTil(Spillresultat resultat)  {
        List<Spillresultat> resultater = les();
        resultater.add(resultat);
        lagre(resultater);
    }

    public void lagre(List<Spillresultat> resultater) {
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(csvFile));
            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeNext(Spillresultat.getKolonnenavn());
            for (Spillresultat resultat : resultater) {
                csvWriter.writeNext(resultat.toStringArray());
            }

            csvWriter.close();
            Log.i("Historikk","Lagret="+ csvFile.getAbsolutePath());

        } catch (IOException e) {
            Toast.makeText(context, "Det gikk galt: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public List<Spillresultat> les() {
        List<Spillresultat> resultater = new ArrayList<>();
        if (csvFile.exists()) {
            try {
                InputStreamReader reader = new InputStreamReader(new FileInputStream(csvFile));
            CSVReader csvReader = new CSVReader(reader);
                 csvReader.readNext();
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    Spillresultat resultat = Spillresultat.fromStringArray(line);
                    resultater.add(resultat);
                }
                csvReader.close();
            } catch (IOException e) {
                Toast.makeText(context, "Det gikk galt: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        return resultater;
    }
}
