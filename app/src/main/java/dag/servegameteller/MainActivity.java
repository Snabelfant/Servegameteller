package dag.servegameteller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Historikk historikk;
    private Tilstand tilstand;

    private Button startSpillButton;
    private Button vantEgetServegameButton;
    private Button spillFerdigButton;
    private Spinner gameVunnetSpillerASpinner;
    private Spinner gameVunnetSpillerBSpinner;
    private int egneServeGameVunnetSåLangtA;
    private Spillresultat spillresultat;
    private long sisteServegameVunnetKlikkTid;
private    String spillerAnavn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        gameVunnetSpillerASpinner = ((Spinner) MainActivity.this.findViewById(R.id.gameVunnetSpillerA));
        gameVunnetSpillerBSpinner = ((Spinner) MainActivity.this.findViewById(R.id.gameVunnetSpillerB));

        startSpillButton = (Button) this.findViewById(R.id.startSpill);
        startSpillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoCompleteTextView spillerAnavnTextView = (AutoCompleteTextView) MainActivity.this.findViewById(R.id.spillerAnavn);
                AutoCompleteTextView spillerBnavnTextView = (AutoCompleteTextView) MainActivity.this.findViewById(R.id.spillerBnavn);
                spillerAnavn = spillerAnavnTextView.getText().toString();
                String spillerBnavn = spillerBnavnTextView.getText().toString();
                boolean isABegynte = ((RadioButton) MainActivity.this.findViewById(R.id.spillerAbegynner)).isChecked();

                if (StringUtils.isEmpty(spillerAnavn) || StringUtils.isEmpty(spillerBnavn)) {
                    Toast.makeText(MainActivity.this, "Mangler spillernavn", Toast.LENGTH_LONG).show();
                    return;
                }

                spillerAnavnTextView.setText(spillerAnavn.trim());
                spillerBnavnTextView.setText(spillerBnavn.trim());

                if (spillerAnavn.equalsIgnoreCase(spillerBnavn)) {
                    Toast.makeText(MainActivity.this, "Like spillernavn", Toast.LENGTH_LONG).show();
                    return;
                }

                tilstand = Tilstand.SPILLSTARTET;
                gameVunnetSpillerASpinner.setSelection(0);
                gameVunnetSpillerBSpinner.setSelection(0);
                egneServeGameVunnetSåLangtA = 0;
                sisteServegameVunnetKlikkTid = System.currentTimeMillis();
                spillresultat = new Spillresultat(spillerAnavn, spillerBnavn, isABegynte);
                justerGui();
            }
        });

        vantEgetServegameButton = (Button) this.findViewById(R.id.vantEgetServegame);
        vantEgetServegameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long tidSidenSisteServegameVunnetKlikk = System.currentTimeMillis() - sisteServegameVunnetKlikkTid;
                long mintid = 60000;
                if (tidSidenSisteServegameVunnetKlikk < mintid) {
                    Toast.makeText(MainActivity.this, "Minst 1 min mellom klikkene (" + ((mintid - tidSidenSisteServegameVunnetKlikk) / 1000) + "s igjen)", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (gameVunnetSpillerASpinner.getSelectedItemId() > 0  || gameVunnetSpillerBSpinner.getSelectedItemId() > 0) {
                    Toast.makeText(MainActivity.this, "Ikke når skrevet resultat", Toast.LENGTH_SHORT).show();
                    return;
                }

                sisteServegameVunnetKlikkTid = System.currentTimeMillis();
                egneServeGameVunnetSåLangtA++;
                tilstand = Tilstand.SPILLSTARTET;
                justerGui();
            }
        });

        Spinner gameVunnetSpillerASpinnner = (Spinner) findViewById(R.id.gameVunnetSpillerA);
        ArrayAdapter gameVunnetSpillerAadapter = ArrayAdapter.createFromResource(this, R.array.mulige_antall_game, android.R.layout.simple_spinner_item);
        gameVunnetSpillerAadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameVunnetSpillerASpinnner.setAdapter(gameVunnetSpillerAadapter);

        Spinner gameVunnetSpillerBSpinnner = (Spinner) findViewById(R.id.gameVunnetSpillerB);
        ArrayAdapter gameVunnetSpillerBadapter = ArrayAdapter.createFromResource(this, R.array.mulige_antall_game, android.R.layout.simple_spinner_item);
        gameVunnetSpillerBadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameVunnetSpillerBSpinnner.setAdapter(gameVunnetSpillerBadapter);

        spillFerdigButton = (Button) this.findViewById(R.id.spillFerdig);
        spillFerdigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameVunnetSpillerASpinner.getSelectedItemId() <= 0 || gameVunnetSpillerBSpinner.getSelectedItemId() <= 0) {
                    Toast.makeText(MainActivity.this, "Mangler ant. game", Toast.LENGTH_SHORT).show();
                    return;
                }

                int gameVunnetSpillerA = Integer.valueOf(gameVunnetSpillerASpinner.getSelectedItem().toString());
                int gameVunnetSpillerB = Integer.valueOf(gameVunnetSpillerBSpinner.getSelectedItem().toString());
                if (gameVunnetSpillerA > 20 || gameVunnetSpillerB > 20) {
                    Toast.makeText(MainActivity.this, "<= 20 game", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (gameVunnetSpillerA < egneServeGameVunnetSåLangtA) {
                    Toast.makeText(MainActivity.this, "Minst " + egneServeGameVunnetSåLangtA + " på " + spillerAnavn, Toast.LENGTH_SHORT).show();
                    return;
                }

                spillresultat.spillFerdig(gameVunnetSpillerA, egneServeGameVunnetSåLangtA, gameVunnetSpillerB);
                historikk.leggTil(spillresultat);
                tilstand = Tilstand.NYTTSPILL;
                justerGui();
            }
        });

        AndroidThreeTen.init(this);
        historikk = new Historikk(this);

        initSpillere(historikk, R.id.spillerAnavn);
        initSpillere(historikk, R.id.spillerBnavn);

        tilstand = Tilstand.NYTTSPILL;
        justerGui();
    }

    private void enableParametre(boolean enabled) {
        this.findViewById(R.id.spillerAnavn).setEnabled(enabled);
        this.findViewById(R.id.spillerBnavn).setEnabled(enabled);
        this.findViewById(R.id.spillerAbegynner).setEnabled(enabled);
        this.findViewById(R.id.spillerBbegynner).setEnabled(enabled);
    }

    private void justerGui() {
        View underSpillView = this.findViewById(R.id.underSpill);

        switch (tilstand) {
            case NYTTSPILL:
                enableParametre(true);
                startSpillButton.setVisibility(View.VISIBLE);
                underSpillView.setVisibility(View.GONE);
                break;
            case SPILLSTARTET:
                enableParametre(false);
                startSpillButton.setVisibility(View.GONE);
                underSpillView.setVisibility(View.VISIBLE);
                vantEgetServegameButton.setText(spillerAnavn + " vant eget servegame! \n" + egneServeGameVunnetSåLangtA + " vunnet så langt");
                break;
        }
    }

    private void initSpillere(Historikk historikk, int id) {
        final List<String> spillere = historikk.getSpillere();
        AutoCompleteTextView spillerNavnAutoCompletionTextView = (AutoCompleteTextView) this.findViewById(id);
        ArrayAdapter<String> spillernavnAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spillere);
        spillernavnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spillerNavnAutoCompletionTextView.setAdapter(spillernavnAdapter);
        spillerNavnAutoCompletionTextView.setThreshold(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainactivitymenu, menu);
        return true;
    }

    public void menuSelectSummary(MenuItem item) {
        Toast.makeText(this, "Resyme", Toast.LENGTH_LONG).show();
    }

    public void menuSelectNyttSpill(MenuItem item) {
        YesNoCancel.show(this,
                "Nytt spill", "Nytt spill?",
                new YesNoCancel.Action() {
                    @Override
                    public void doAction(int selection) {
                        tilstand = Tilstand.NYTTSPILL;
                        justerGui();
                    }
                },
                new YesNoCancel.Action() {
                    @Override
                    public void doAction(int selection) {
                        Toast.makeText(MainActivity.this, "Neivel", Toast.LENGTH_LONG).show();

                    }
                }
                , null);

    }

}
