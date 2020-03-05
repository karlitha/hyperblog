/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gatos_app;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Karlitha Bustos
 */
public class inicio {
    public static void main(String[] args) throws IOException {
        
        try {
            int opcion_menu=-1;
        //array tipo string
        String[] botones={"1. ver gatos","2. ver Favoritos","3. salir "};
        //                        0               1              2
        do {  
            //es el tipo de mensaje
            //tipo de respiesta 
            //**JOptionPane este objecto convertirlo a tipo string casteo
            //menu principal
            String opcion=(String)JOptionPane.showInputDialog(null,"Gatitos java","Menu principal", JOptionPane.INFORMATION_MESSAGE,null, botones,botones[0]);
            
            //validar la opcion que usuario eligio
            
            for (int i = 0; i <botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    opcion_menu=i;
                }
                
            }
            //hacer un swich que hacer con esa opcion
            switch(opcion_menu){
                case 0:
                    gatosService.verGatos();
                    break;
                case 1:
                    
                    gatos gatito=new gatos();
                    gatosService.verFavorito(gatito.getApikey());
                    break;
                   default:
                       break;
             
            }
         
        } while (opcion_menu != 2);
        } catch (Exception e) {
            
        }
        
       
       
        
    }
    
}
