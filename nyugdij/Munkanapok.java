/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nyugdij;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Ócsai
 */
public class Munkanapok {
    
    private static final String DATE_FORMAT = "yyyy.MM.dd.";
    private final Calendar NOW = Calendar.getInstance();
    private Calendar until;
    private int nyYear, nyMonth, nyDay, nySzabi;
    private final String[] STATE_HOLIDAYS = {".01.01.", ".03.15.", ".05.01.", 
            ".08.20.", ".10.23.", ".11.01.", ".12.25.", ".12.26."};

    public Munkanapok() {
    }

    public Munkanapok(int nyYear, int nyMonth, int nyDay) {
        this.nyYear = nyYear;
        this.nyMonth = nyMonth;
        this.nyDay = nyDay;
        this.until = convertSDF(this.nyYear + "." + this.nyMonth + "." + this.nyDay);
    }

    public void setNySzabi(int nySzabi) {
        this.nySzabi = nySzabi;
    }
    
    public int workdays() {
        return workdaysBetween(NOW, until) - nySzabi;
    }
    
    /**A korábban megadott dátumformátumnak megfelelő <code>String</code>et
     * alakítja át <code>Calendar</code> típusú dátummá.
     *  
     * @param dateStr átalakítandó karaktersorozat.
     * @return az átalakított <code>Calendar</code> típusú dátum.
     */
    public static Calendar convertSDF(String dateStr) {
        SimpleDateFormat sdf = 
                new SimpleDateFormat(DATE_FORMAT.substring(0, 10));
        Calendar result = new GregorianCalendar();
        try {
            Date d = sdf.parse(dateStr);
            result.setTime(d);
        } catch (ParseException ex) {
            System.out.println("Nem sikerült átkonvertálni.");
        }
        return result;
    }
    
    /**A megadott két dátum közötti hétköznapokat számolja össze.
     * 
     * @param beginDate kazdődátum Calendar típusban
     * @param endDate végdátum Calendar típusban
     * @return A két dátum közötti hétköznapok száma. Ha a kezdődátum későbbi,
     * mint a végdátum <code>-1</code> lesz az eredmény.
     */
    private int workdaysBetween(Calendar beginDate, Calendar endDate) {
        int result = 1;
        if (beginDate.after(endDate))
            result = -1;
        while (beginDate.before(endDate)) {
            if (isWeekday(beginDate))
                result ++;
            beginDate.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result - weekdayFeasts(endDate);
    }
    
    /**Megvizsgálja, hogy a karaktersorozatként megadott dátum hétköznapra esik.
     * 
     * @param d a dátum <code>String</code> formátumban.
     * @return <code>1</code> - a keresett dátum hétköznap.
     * <br> <code>0</code> - a keresett dátum hétvége.
     */
    private int onWeekday(String d) {
        Calendar askedDay = convertSDF(NOW.get(Calendar.YEAR) + "." + d);
        if (NOW.get(Calendar.YEAR) < nyYear &&
            askedDay.get(Calendar.MONTH) < nyMonth) {
            askedDay.add(Calendar.YEAR, 1);
        }
        if (isWeekday(askedDay)) {
            return 1;
        } else return 0;
    }
    
    private int onWeekday(Calendar askedDay) {
        //Calendar askedDay = convertSDF(NOW.get(Calendar.YEAR) + "." + d);
        if (NOW.get(Calendar.YEAR) < nyYear &&
            askedDay.get(Calendar.MONTH) < nyMonth) {
            askedDay.add(Calendar.YEAR, 1);
        }
        if (isWeekday(askedDay)) {
            return 1;
        } else return 0;
    }

    /**Megadja, hogy egy dátum hétköznapra esik-e.
     * 
     * @param askedDay a keresett dátum <code>Calendar</code> típusban.
     * @return <code>true</code> - a keresett dátum hétköznap volt.
     * <code>false</code> - a keresett dátum hétvége volt.
     */
    private static boolean isWeekday(Calendar askedDay) {
        return (askedDay.get(Calendar.DAY_OF_WEEK) != 1 && 
                askedDay.get(Calendar.DAY_OF_WEEK) != 7);
    }
    
    /**A megadott évben kiszámolja húsvétvasárnap dátumát Jean Meeus 1991-es
     * gregorián húsvétszámoló algoritmusa segítségével.
     * Forrás: https://hu.wikipedia.org/wiki/H%C3%BAsv%C3%A9tk%C3%A9plet
     * @param year az év, amelyikben pünkösdhétfőt keressük.
     * @return Pünkösdhétfő dátuma <code>Calendar</code> típusban.
     */
    private Calendar getEaster(int year) {
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 2) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int month = ((h + l - 7 * m + 114) / 31) - 1;
        int day = ((h + l - 7 * m + 114) % 31) + 1;
        String easterStr = year + "." + month + "." + day + ".";
        Calendar easter = convertSDF(easterStr); //húsvétvasárnap
        //pentecost.add(Calendar.DAY_OF_YEAR, 50); //húsvétvasárnap + 50 nap
        return easter;
    }
    
    private String convertToString(Calendar date) {
        return  "." + date.get(Calendar.MONTH) + 
                "." + date.get(Calendar.DAY_OF_MONTH) + ".";
    }
    
    /**A paraméterben megadott évvel bezárólag visszaad egy listát az addigi
     * ünnepnapokról.
     * @param yearUntil
     * @return 
     */
    private ArrayList<Calendar> stateHolidays (int yearUntil) {
        ArrayList<Calendar> result = new ArrayList<>();
        for (int i = NOW.get(Calendar.YEAR); i <= yearUntil; i++) {
            for (String holiday : STATE_HOLIDAYS) {
                result.add(convertSDF(i + holiday));
            }
            result.addAll(Arrays.asList(moveableFeasts(i)));
        }
        return result;
    }
    
    private Calendar[] moveableFeasts(int year) {
        Calendar[] result = new Calendar[4];
        Calendar pentecost = getEaster(year);
        pentecost.add(Calendar.DAY_OF_YEAR, 50);
        for (int i = 0; i <= 1; i++) {
            result[i] = getEaster(year);
            result[i + 2] = pentecost;
        }
        result[1].add(Calendar.DAY_OF_YEAR, 1);
        result[3].add(Calendar.DAY_OF_YEAR, 1);
        return result;
    }

    private int weekdayFeasts(Calendar endDate) {
        int result = 0;
        ArrayList<Calendar> feasts = stateHolidays(endDate.get(Calendar.YEAR));
        for (Calendar feastDay : feasts) {
            result += onWeekday(feastDay);
        }
        return result;
    }

    private int weekdayFeasts(Calendar beginDate, Calendar endDate) {
        return -1;
    }

    
}
