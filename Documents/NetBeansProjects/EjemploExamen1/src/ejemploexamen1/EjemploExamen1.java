/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemploexamen1;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author vidal
 */
public class EjemploExamen1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner teclado = new Scanner(System.in);
        System.out.println("Dame un usuario:");
        String usuario = teclado.nextLine();
        System.out.println("Dame una contraseña");
        String password =teclado.nextLine();
        
        URL url;
        
        try {
            url = new URL("http://localhost/Practica2/login.php?usuario=" + usuario + "&password=" + password);
            
            //Conectar a esa URL:
            URLConnection urlConnection = url.openConnection();
            urlConnection.setDoOutput(true);
            
             //Asocimos Flujo de entrada a la URL:
            InputStream input = (urlConnection.getInputStream());
            
              //Creamos buffer para leer la linea:
            BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new ByteArrayInputStream(buffer.readLine().getBytes("utf-8"))));
            document.getDocumentElement().normalize();
            
            
            String elemento = document.getElementsByTagName("success").item(0).getTextContent();
            
            if(elemento.equals("true")){
                System.out.println("Login correcto.");
                
                
               URL url2 = new URL("http://localhost/Practica2/listado.php");
               
               //Conectar a esa URL:
            URLConnection urlConnection2 = url2.openConnection();
            //urlConnection2.setDoOutput(true); (solo para escribir);
                
                 //Asocimos Flujo de entrada a la URL:
            InputStream input2 = (urlConnection2.getInputStream());
            
              //Creamos buffer para leer la linea:
            BufferedReader buffer2 = new BufferedReader(new InputStreamReader(input2));
            
            String data;
                        String cadena = new String();
                        while((data = buffer2.readLine()) !=null){
                            cadena += data;
                          //  System.out.println(data);
                        }

                       
            DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder2 = factory2.newDocumentBuilder();
            Document document2 = builder2.parse(new InputSource(new ByteArrayInputStream(cadena.getBytes("utf-8"))));
            document2.getDocumentElement().normalize();
           // System.out.println("Elemento raíz "+document2.getDocumentElement().getNodeName());
            
                NodeList agente =document2.getElementsByTagName("agente");
                int cont = 0;
                for (int i = 0; i < agente.getLength(); i++) {
                    cont++;
                    Node agentes = agente.item(i);
                    if (agentes.getNodeType() == Node.ELEMENT_NODE) {
                         Element elemento2 =(Element)agentes;
                                System.out.print("* Agente "+cont+": " + elemento2.getElementsByTagName("nombre").item(0).getTextContent());
                                System.out.println(" " + elemento2.getElementsByTagName("apellido").item(0).getTextContent());
                                 
                    }
                }    
                 buffer.close();
                
            }else{
                System.out.println("Login incorrecto");
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(EjemploExamen1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EjemploExamen1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(EjemploExamen1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(EjemploExamen1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
