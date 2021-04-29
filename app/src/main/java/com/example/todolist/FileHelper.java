package com.example.todolist;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileHelper {
    //Listelere yazilanlari bu isimle cihaz hafizasinda saxlayacayiq
    public static final String FileName = "listinfo.dat";

    public static void writeData(ArrayList<String> item, Context context) {
        //Bu sinif verileri dosyaya yazmaq ucun use olur
        //bu metod cihaz yaddasinda yer acacaq ve onu alacaq
        try{
            FileOutputStream fos = context.openFileOutput(FileName,Context.MODE_PRIVATE);
            //MODe_PRIVATE dosyaya uygulamanin her yerinden ulasmaq olar
            ObjectOutputStream oas = new ObjectOutputStream(fos);
            //BU kod item-icindeki arraylistleri dosyaya yazacaq
            oas.writeObject(item);
            oas.close();//dosya kapanmasa problem olar

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> readData( Context context) {
        //Okunudugum verileri icine eklemek icin arraylist olusturuyoruz
        ArrayList<String> itemList1 = null;
        try {

            FileInputStream fis = context.openFileInput(FileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            itemList1 = (ArrayList<String>) ois.readObject();

        } catch (FileNotFoundException e) {
            itemList1= new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return itemList1;
    }
}
