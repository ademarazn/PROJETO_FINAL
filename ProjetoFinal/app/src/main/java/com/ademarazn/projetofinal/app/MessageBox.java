package com.ademarazn.projetofinal.app;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Ademar Zório Neto on 06/12/2017
 */

public class MessageBox {

    /**
     * @param context         Referência da classe Context
     * @param title           Título da caixa de mensagem
     * @param msg             String contendo a mensagema ser exibida
     * @param onClickListener Evento ao clicar no botão OK
     */
    public static void showInfo(Context context, String title, String msg,
                                DialogInterface.OnClickListener onClickListener) {
        show(context, title, msg, android.R.drawable.ic_dialog_info, onClickListener);
    } // Fim do método showInfo(Context, String, String, DialogInterface.OnClickListener)

    /**
     * @param context         Referência da classe Context
     * @param title           Título da caixa de mensagem
     * @param msg             String contendo a mensagema ser exibida
     * @param onClickListener Evento ao clicar no botão OK
     */
    public static void showAlert(Context context, String title, String msg,
                                 DialogInterface.OnClickListener onClickListener) {
        show(context, title, msg, android.R.drawable.ic_dialog_alert, onClickListener);
    } // Fim do método showAlert(Context, String, String, DialogInterface.OnClickListener)

    /**
     * @param context         Referência da classe Context
     * @param title           Título da caixa de mensagem
     * @param msg             String contendo a mensagema ser exibida
     * @param onClickListener Evento ao clicar no botão OK
     */
    public static void show(Context context, String title, String msg,
                            DialogInterface.OnClickListener onClickListener) {
        show(context, title, msg, 0, onClickListener);
    } // Fim do método show(Context, String, String, DialogInterface.OnClickListener)

    /**
     * @param context         Referência da classe Context
     * @param title           Título da caixa de mensagem
     * @param msg             String contendo a mensagema ser exibida
     * @param iconId          ID do ícone a ser exibido na caixa de mensagem
     * @param onClickListener Evento ao clicar no botão OK
     */
    public static void show(Context context, String title, String msg, int iconId,
                            DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setIcon(iconId);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setNeutralButton("OK", onClickListener);
        dialog.show();
    } // Fim do método show(Context, String, String, int, DialogInterface.OnClickListener)

    public static void showConfirm(Context context, String title, String msg, int iconId,
                                   DialogInterface.OnClickListener onClickPositive,
                                   DialogInterface.OnClickListener onClickNegative) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setIcon(iconId);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setPositiveButton("Sim", onClickPositive);
        dialog.setNegativeButton("Não", onClickNegative);
        dialog.show();
    } // Fim do método showConfirm(Context, String, String, int,
    // DialogInterface.OnClickListener, DialogInterface.OnClickListener)

} // Fim da classe
