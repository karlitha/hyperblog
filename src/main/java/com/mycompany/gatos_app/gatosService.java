/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gatos_app;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.net.URLConnection;



/**
 *
 * @author Karlitha Bustos
 */
public class gatosService {
    
    public static void verGatos() throws IOException{
        OkHttpClient client = new OkHttpClient();
Request request = new Request.Builder().url("https://api.thecatapi.com/v1/images/search").get().build();
Response response = client.newCall(request).execute();
//objecto para las respuetas

String eljason=response.body().string(); //en el cuerpo biene la informacion
//quitamos el primer y ultimo corchete para que lo entienda un objecto de la clase gson

eljason=eljason.substring(1, eljason.length()); //funcion para poder recortar strings
eljason=eljason.substring(0, eljason.length()-1);

//crear un objectode la clase gson

Gson gson=new Gson();
gatos gatito=gson.fromJson(eljason, gatos.class);


//redimencionar en caso de necesitar
Image image=null;
        try {
            URL url=new URL(gatito.getUrl());
            System.out.println(url.toString());
              URLConnection urlc = url.openConnection();
    urlc.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; "
            + "Windows NT 5.1; en-US; rv:1.8.0.11) ");
    
    //InputStream inputFile = urlc.getInputStream();
           // image=ImageIO.read(url.openStream());
           image=ImageIO.read(urlc.getInputStream());
            
            
            System.out.println(image.toString());
            
            ImageIcon fondoGato=new ImageIcon(image);
            
            if(fondoGato.getIconWidth() >800){
                //redimimencionamos 
                Image fondo=fondoGato.getImage();
                Image modificada=fondo.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                fondoGato=new ImageIcon(modificada); 
            }
            
            String menu="opciones:  \n"+"1. ver otra imagen: \n"+"2. Favorito \n"+"3. Volver \n";
            
            String[] botones={"ver otra imagen","favorito","volver"};
            String id_gato=(gatito.getId());//++++++++++++++++
            String opcion=(String)JOptionPane.showInputDialog(null,menu,id_gato,JOptionPane.INFORMATION_MESSAGE,fondoGato,botones,botones[0]);
            
            //validar la opcion que escoje el usuario
            int seleccion=-1;
            
             for (int i = 0; i <botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    seleccion=i;
                }
             }
             switch(seleccion){
                case 0:
                    verGatos();
                    break;
                case 1:
                    favoritoGato(gatito);
                    break;
                   default:
                       break;
             
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }


    
    } 
    
    public static void favoritoGato(gatos gatito){
        try {
                  OkHttpClient client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\n   \n  \"image_id\":\""+gatito.getId()+"\"\n}");
                Request request = new Request.Builder()
                  .url("https://api.thecatapi.com/v1/favourites")
                  .method("POST", body)
                  .addHeader("Content-Type", "application/json")
                  .addHeader("x-api-key", gatito.getApikey())
                  .build();
Response response = client.newCall(request).execute();
            System.out.println(request.toString());
            
        } catch (IOException e) {
            System.out.println(e);
        }
    
    
    }
    
    public static void verFavorito(String apikey) throws IOException{
        try {
           System.out.println("entro");
            OkHttpClient client = new OkHttpClient();
           Request request = new Request.Builder()
          .url("https://api.thecatapi.com/v1/favourites")
          .method("GET", null)
          .addHeader("x-api-key", apikey)
          .build();
          Response response = client.newCall(request).execute();
         
          //guardamos el string con la respuesta
          String elJson=response.body().string();
          
          //creamos el objecto gson 
          Gson gson=new Gson();
          
           gatosFav[] gatosArray=gson.fromJson(elJson, gatosFav[].class);
          
          //la siguiente condicion evaluara si realmente tiene elementos el array
          if (gatosArray.length > 0){
          int min=1;
          int max=gatosArray.length;
          int aleatorio=(int)(Math.random()*gatosArray.length);
          int indice=aleatorio-1;
          
          gatosFav gatosfav=gatosArray[indice];
           Image image=null;
                try{
                    URL url = new URL(gatosfav.image.getUrl());
                     URLConnection urlc = url.openConnection();
                     urlc.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; "
                             + "Windows NT 5.1; en-US; rv:1.8.0.11) ");

    //InputStream inputFile = urlc.getInputStream();
           // image=ImageIO.read(url.openStream());
           image=ImageIO.read(urlc.getInputStream());
                   // image = ImageIO.read(url);

                    ImageIcon fondoGato = new ImageIcon(image);

                    if(fondoGato.getIconWidth() > 800){
                        //redimensionamos
                        Image fondo = fondoGato.getImage();
                        Image modificada = fondo.getScaledInstance(800, 600, java.awt.Image.SCALE_SMOOTH);
                        fondoGato = new ImageIcon(modificada);
                    }
                    String menu="opciones:  \n"+"1. ver otra imagen: \n"+"2. Eliminar Favorito \n"+"3. Volver \n";
            
            String[] botones={"ver otra imagen","Eliminar favorito","volver"};
            String id_gato=(gatosfav.getId());//++++++++++++++++
            String opcion=(String)JOptionPane.showInputDialog(null,menu,id_gato,JOptionPane.INFORMATION_MESSAGE,fondoGato,botones,botones[0]);
            
            //validar la opcion que escoje el usuario
            int seleccion=-1;
            
             for (int i = 0; i <botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    seleccion=i;
                }
             }
             switch(seleccion){
                case 0:
                    verFavorito(apikey);
                    break;
                case 1:
                   // gatosFav  gatofav=new gatosFav();
                    
                    borrarFavorito(gatosfav);
                   
                    break;
                   default:
                       break;
             
            }
            

    

          
          
          }catch(Exception ex){
              System.out.println(ex.getMessage());
          }
          }
        
        }
         catch(Exception ex)
             { System.out.println(ex.getMessage());}
     
          
         
          
          
          
          
            }
    
    
    public static void borrarFavorito(gatosFav gatosfav){
        try {
         OkHttpClient client = new OkHttpClient();
           MediaType mediaType = MediaType.parse("text/plain");
           RequestBody body = RequestBody.create(mediaType, "");
           Request request = new Request.Builder().url("https://api.thecatapi.com/v1/favourites/"+gatosfav.getId()+"").delete().addHeader("x-api-key", gatosfav.getApikey()).build();
             //.url("https://api.thecatapi.com/v1/favourites/"+gatofav.getImage_id()+"")
             //.method("DELETE", body)
             //.addHeader("x-api-key", gatofav.getApikey())
             //.build();
            
           Response response = client.newCall(request).execute();
            //System.out.print(response.body().toString());
            System.out.print("id gato="+gatosfav.getId());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

   
}
