/*
 * Session represesnts the dialog and date of a speaker
 */
package Provincial_Miner.application;

import java.time.LocalDate;
import java.util.Calendar;

/**
 *
 * @author Weston Dransfield
 */
public class Session {

    LocalDate date = LocalDate.now();
    String content = new String();
    
    public LocalDate getDate() {
        return date;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setDate(int year, int month, int day) {
        //verify the year is in bounds
        if (year < 1867 || year > Calendar.getInstance().get(Calendar.YEAR)){
            return;
        }
        
        //verify month
        if (month < 0 || month > 12) {
            return;
        }
        
        //verify day
        if (day < 0 || day > 31) {
            return;
        }
        
        //if valid paramaters, set the new date
        date = LocalDate.of(year, month, day);
    }
    
    public void setContent(String content) {
        this.content = content;
    }
}
