package sg.nus.edu.iss.vttp5a_ssf_day19_task.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class LocalDateConverter {
    public LocalDateConverter(){

    }

    public LocalDate convert(long epoch){
        return Instant.ofEpochMilli(epoch).atZone(ZoneId.of("UTC")).toLocalDate();
    }
}
