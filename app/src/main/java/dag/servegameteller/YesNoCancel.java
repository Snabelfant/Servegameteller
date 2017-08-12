package dag.servegameteller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class YesNoCancel {
    public final static int YES = 0x01;
    public final static int NO = 0x02;
    public final static int CANCEL = 0x04;
    public static final Action EMPTY = new Action() {
        @Override
        public void doAction(int selection) {
        }
    };

    public static void show(Context context, String title, String message, final Action yesAction, final Action noAction, final Action cancelAction) {
        new YesNoCancel().show1(context, title, message, yesAction, noAction, cancelAction);
    }

    public void show1(Context context, String title, String message, final Action yesAction, final Action noAction, final Action cancelAction) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder
                .setTitle(title)
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert);

        if (yesAction != null) {
            builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    yesAction.doAction(YES);
                    dialog.dismiss();
                }
            });

        }

        if (noAction != null) {
            builder.setNegativeButton("Nei", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    noAction.doAction(NO);
                    dialog.dismiss();
                }
            });

        }

        if (cancelAction != null) {
            builder.setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    cancelAction.doAction(CANCEL);
                }
            });

        }

        builder.show();
    }

    public interface Action {
        void doAction(int selection);

    }

}
