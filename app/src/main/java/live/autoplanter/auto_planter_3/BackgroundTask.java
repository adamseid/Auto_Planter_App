package live.autoplanter.auto_planter_3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundTask extends AsyncTask<String, Void,String> {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Context context;
    BackgroundTask(Context temp){
        this.context = temp;
    }

    @Override
    protected String doInBackground(String... params) {
        preferences = context.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("flag","0");
        editor.apply();
        String registrationURL ="http://207.6.234.69/android/registration.php";
        String loginURL = "http://207.6.234.69/android/log_in.php";
        String task = params[0];

        if(task.equals("sign_in")){
            String firstName = params[1];
            String lastName = params[2];
            String email = params[3];
            String password = params[4];
            try {
                URL url = new URL(registrationURL);
                System.out.println("//////////////////////////////////////////////");
                System.out.println(registrationURL);
                System.out.println(firstName);
                System.out.println(lastName);
                System.out.println(email);
                System.out.println(password);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                System.out.println(httpURLConnection.getResponseCode());
                System.out.println("Hello");
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("first_name", "UTF-8") + "=" + URLEncoder.encode(firstName, "UTF-8")+"&"+
                        URLEncoder.encode("last_name", "UTF-8") + "=" + URLEncoder.encode(lastName, "UTF-8")+"&"+
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")+"&"+
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(myData);
                System.out.println(myData);
                System.out.println("//////////////////////////////////////////////");
                bufferedWriter.flush();
                bufferedWriter.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                editor.putString("flag","register");
                editor.apply();
                return "Successfully Registered " + email;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(task.equals("log_in")){
            String loginEmail = params[1];
            String loginPassword = params[2];
            try {
                URL url = new URL(loginURL);
                System.out.println(loginURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11");
                System.out.println(loginEmail);
                System.out.println(loginPassword);

                //Send email and Password to the database
                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("identifier_loginEmail","UTF-8")+"="+URLEncoder.encode(loginEmail,"UTF-8")+"&"
                        +URLEncoder.encode("identifier_loginPassword","UTF-8")+"="+URLEncoder.encode(loginPassword,"UTF-8");
                bufferedWriter.write(myData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                //Recieve response from the database
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String dataResponse = "";
                String inputLine = "";
                while ((inputLine=bufferedReader.readLine()) != null){
                    dataResponse += inputLine;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println(dataResponse);
                editor.putString("flag","login");
                editor.apply();
                return dataResponse;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //This method will be called when doInBackground completes... and it will return the completion string which
    //will display this toast.
    @Override
    protected void onPostExecute(String s) {
        String flag = preferences.getString("flag","0");

        assert flag != null;
        if(flag.equals("register")) {
            Toast.makeText(context,s,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context,MainScreen.class);
            context.startActivity(intent);
        }
        if(flag.equals("login")){
            String test = "false";
            String firstName = "";
            String lastName = "";
            String email = "";
            String[] serverResponse = s.split("[,]");
            System.out.println("????????????????????????????????????????");
            test = serverResponse[0];
            email = serverResponse[1];
            firstName = serverResponse[2];
            lastName = serverResponse[3];
            System.out.println(test);
            System.out.println(firstName);
            System.out.println(lastName);
            System.out.println(email);

            if(test.equals("true")){
                editor.putString("firstName",firstName);
                editor.apply();
                editor.putString("lastName",lastName);
                editor.apply();
                editor.putString("email",email);
                editor.apply();
                Toast.makeText(context,"Login Success",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context,MainScreen.class);
                context.startActivity(intent);
            }else if(test.equals("false")){
                Toast.makeText(context,"Login Failed...that email or password does not match our records",Toast.LENGTH_LONG).show();
//                display("Login Failed...", "That email and password do not match our records :(.");
            }
        }else{
            display("Login Failed...","Something weird happened :(.");
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    public void display(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
