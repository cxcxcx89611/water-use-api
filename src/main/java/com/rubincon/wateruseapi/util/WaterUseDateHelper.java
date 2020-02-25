package com.rubincon.wateruseapi.util;

import java.util.Calendar;
import java.util.Date;

public class WaterUseDateHelper {

    public Date getEndDate(Date date, int duration) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, duration);
        Date endDate = calendar.getTime();
        return endDate;
    }

    public boolean checkOverlap(Date startDate1, int duration1, Date startDate2, int duration2){
        Date endDate1 = this.getEndDate(startDate1, duration1);
        Date endDate2 = this.getEndDate(startDate2, duration2);

        if(startDate1.after(startDate2)  && startDate1.before(endDate2)){
            return true;
        }
        else if ( endDate1.after(startDate2) && endDate1.before(endDate2)){
            return true;
        }
        else if (startDate2.after(startDate1)  && startDate2.before(endDate1)) {
            return true;
        }
        else if (endDate2.after(startDate1) && endDate2.before(endDate1)) {
            return true;
        }
        else {
            return false;
        }
    }
}
