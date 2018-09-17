/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nyugdij;

import java.util.Calendar;
import java.util.Scanner;
import javax.swing.*;



/**
 *
 * @author Ócsai Steve
 */
public class Nyugdij {
    
    private static final String DATE_FORMAT = "yyyy.MM.dd.";
    private static final Scanner SC = new Scanner(System.in);
    static Munkanapok retire = new Munkanapok(2018, 1, 11);

    /**
     * @param args the command line arguments
     */
    /*public static void main(String[] args) {
        
        System.out.printf("Nyugdíjad első napja (%s formában): ", DATE_FORMAT);
        String retireStr = SC.nextLine();
        Calendar retireDate = Munkanapok.convertSDF(retireStr);
        System.out.printf("%s-ig kivehető szabadnapjaid száma? ", DATE_FORMAT);
        int szabik = SC.nextInt();        
        Munkanapok retire = new Munkanapok(retireDate.get(Calendar.YEAR), 
                retireDate.get(Calendar.MONTH), retireDate.get(Calendar.DAY_OF_MONTH));
        retire.setNySzabi(szabik);
        System.out.printf("Nyugdíjig hátralévő munkanapok száma: %d%n", retire.workdays());
        
        Grafikus mainWindow = new Grafikus("Nyugdíj v 0.1");
        mainWindow.setWindowProp(600, 400, true);
        //mainWindow.menuBar();
        mainWindow.AButton();
    }*/

}
