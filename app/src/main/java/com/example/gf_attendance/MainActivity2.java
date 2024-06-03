package com.example.gf_attendance;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_page);

        // Initialiser les valeurs
        int pas = 19;
        int hdep = 146;

        // Définir l'heure de départ
        String hdepart = "9:00";

        // Mettre à jour hdep en fonction de hdepart
        switch (hdepart) {
            case "9:00":
                // Pas de changement pour hdep
                break;
            case "9:30":
                hdep += pas;
                break;
            case "10:00":
                hdep += 2 * pas;
                break;
            case "10:30":
                hdep += 3 * pas;
                break;
            case "11:00":
                hdep += 4 * pas;
                break;
            case "11:30":
                hdep += 5 * pas;
                break;
            case "12:00":
                hdep += 6 * pas;
                break;
            case "12:30":
                hdep += 7 * pas;
                break;
            case "13:00":
                hdep += 8 * pas;
                break;
            case "13:30":
                hdep += 9 * pas;
                break;
            case "14:00":
                hdep += 10 * pas;
                break;
            case "14:30":
                hdep += 11 * pas;
                break;
            case "15:00":
                hdep += 12 * pas;
                break;
            case "15:30":
                hdep += 13 * pas;
                break;
            case "16:00":
                hdep += 14 * pas;
                break;
            case "16:30":
                hdep += 15 * pas;
                break;
            case "17:00":
                hdep += 16 * pas;
                break;
            default:
                // Gérer les heures de départ inconnues
                System.out.println("Heure de départ inconnue.");
                break;
        }
        // Afficher le résultat
        TextView textView = findViewById(R.id.textView);
        textView.setText("hdep mis à jour: " + hdep);

        Button button = findViewById(R.id.startSession);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) button.getLayoutParams();
        params.topMargin = hdep;
        // Utilisez la valeur de hdep pour la marge supérieure
        button.setLayoutParams(params);

    }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.schedule_page);

            // Accéder au layout personnalisé
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) View customButton = findViewById(R.id.custom_Button);
            TextView textViewTitle = customButton.findViewById(R.id.matiere);


            // Définir un listener pour le layout personnalisé
            customButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    }
            });
        }
    }

}


