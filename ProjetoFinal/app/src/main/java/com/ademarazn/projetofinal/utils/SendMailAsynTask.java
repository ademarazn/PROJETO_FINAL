package com.ademarazn.projetofinal.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Ademar Zório Neto on 07/12/2017
 */

public class SendMailAsynTask extends AsyncTask<Void, Void, Void> {
    //Declaring Variables
    private Context context;
    private View view;
    //Information to send email
    private String email;
    private String message;
    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;

    private Boolean enviado;

    //Class Constructor
    public SendMailAsynTask(Context context, View view, String email, String message) {
        //Initializing variables
        this.context = context;
        this.view = view;
        this.email = email;
        this.message = message;
        this.enviado = false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Showing progress dialog while sending email
        progressDialog = ProgressDialog.show(context, "Enviando Email", "Por favor aguarde...", false, false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismissing the progress dialog
        progressDialog.dismiss();
        if (enviado) {
            //Showing a success message
            Snackbar.make(view, "Email enviado com sucesso!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        Properties props = new Properties();
        //Configuring properties for gmail
        //If you are not using gmail you may need to change the values
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //Creating a new session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    //Authenticating the password
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
                    }
                });
        try {
            //Creating MimeMessage object
            MimeMessage mm = new MimeMessage(session);
            //Setting sender address
            mm.setFrom(new InternetAddress(Config.EMAIL));
            //Adding receiver
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            //Adding subject
            mm.setSubject("Projeto Final");
            //Adding message
            mm.setText(message);
            //Sending email
            Transport.send(mm);
            enviado = true;
        } catch (Exception e) {
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

}//fim da classe
