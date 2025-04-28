package advanced;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.util.GregorianCalendar;
import java.util.List;

public class DateAndTime {
    public static void main(String[] args) {
        // setting property on system
        System.setProperty("user.timezone", "UTC");

        LocalDate today = LocalDate.now();
        // iso format
        // temporal interface
        System.out.println(today);
        System.out.println("_".repeat(30));
        LocalDate five5 = LocalDate.of(2025, 5, 5);
        System.out.println(five5);

        LocalDate may5th = LocalDate.of(2025, Month.MAY, 5);
        System.out.println(may5th);

        System.out.println("__".repeat(30));
        LocalDate day125 = LocalDate.ofYearDay(2025, 125);
        System.out.println(day125);

        System.out.println("__".repeat(30));
        LocalDate may5 = LocalDate.parse("2022-05-05");
        System.out.println(may5);
        System.out.println(may5.getYear());
        System.out.println(may5.getMonth());
        System.out.println(may5.getMonthValue());
        //
        System.out.println("_".repeat(20));
        System.out.println(may5.getDayOfMonth());
        System.out.println(may5.get(ChronoField.DAY_OF_MONTH));
        System.out.println("_".repeat(20));
        //
        // temporal interface is something XD
        System.out.println(may5.withYear(2003));
        // IMMUTABLE U NEED REASSIGNMENT
        System.out.println(may5);

        System.out.println(may5.with(ChronoField.DAY_OF_MONTH, 12));

        System.out.println("_".repeat(20));
        System.out.println(may5);
        System.out.println(may5.plusDays(365));
        System.out.println(may5.plusWeeks(1));
        System.out.println(may5.plus(3, ChronoUnit.CENTURIES));
        System.out.println("_".repeat(20));
        System.out.println(may5.minus(1, ChronoUnit.CENTURIES));
        System.out.println(LocalDate.now().compareTo(may5));
        System.out.println(LocalDate.now().equals(LocalDate.now()));
        System.out.println("_".repeat(20));
        System.out.println(LocalDate.now().withYear(4).isLeapYear());

        // god there are a lot of DateTime manip

        System.out.println("_".repeat(30));
        // days until = stream os localDate
        may5.datesUntil(may5.plusDays(7))
                // .map(date->date.getDayOfYear())
                .forEach(System.out::println);

        // period overload
        System.out.println("_".repeat(30));
        may5.datesUntil(may5.plusYears(1), Period.ofDays(20))
                // .map(date->date.getDayOfYear())
                .forEach(System.out::println);

        // localTime
        // [0,23]
        System.out.println("_".repeat(20));
        LocalTime time = LocalTime.now();
        System.out.println(time);

        // _ overloaded
        LocalTime sevenAM = LocalTime.of(19, 0);
        System.out.println(sevenAM.get(ChronoField.AMPM_OF_DAY));
        System.out.println(sevenAM);

        // getHour
        System.out.println(sevenAM.getHour());
        System.out.println(sevenAM.get(ChronoField.HOUR_OF_DAY));
        System.out.println(sevenAM.plus(1, ChronoUnit.HOURS));
        System.out.println();

        LocalDateTime todayAndNow = LocalDateTime.now();
        System.out.println(todayAndNow.getDayOfMonth());

        System.out.println(todayAndNow.format(DateTimeFormatter.ISO_WEEK_DATE));

        DateTimeFormatter dtf = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
        System.out.println(todayAndNow.format(dtf));
        System.out.println(todayAndNow.plusDays(1).format(dtf));

        // INSTANT IN TIME - PERIOD - DURATION = TIME BASED INTERNAL(HOURS,MINUTES,
        // SECONDS)

        // 000000 utc 1st jan 1970

        System.out.println("_".repeat(120));
        Instant instant = Instant.now();
        System.out.println(instant);
        // these are modern immutable thread-safe classes
        System.out.println("_".repeat(20));
        // os Time
        var now = System.currentTimeMillis();
        for (int i = 1; i < 15; i++) {

            if (System.currentTimeMillis() == now) {
                System.out.println("EQUAL! , i = " + i);
            } else {
                System.out.println("NOT EQUAL!, i = " + i);
                break;
            }

        }
        System.out.println("_".repeat(20));

        // jvm Time from OriginTime
        var nowJvm = System.nanoTime();
        for (int i = 1; i < 15; i++) {

            if (System.nanoTime() == nowJvm) {
                System.out.println("EQUAL! , i = " + i);
            } else {
                System.out.println("NOT EQUAL!, i = " + i);
                break;
            }

        }
        System.out.println("_".repeat(20));

        // UTC offset + rules for daylight saving 1200 12hours 00 mins (inparen =
        // dayLightSavingRule)
        // more about Time

        System.out.println(ZoneId.getAvailableZoneIds().size());
        // streaming zones and sort print
        var c = ZoneId.getAvailableZoneIds().stream()
                .filter(s -> s.startsWith("Asia"))
                .sorted()
                // .map(ZoneId::of)
                .count();
        System.out.println(c);
        // .forEach(System.out::println);

        System.out.println("_".repeat(20));
        System.out.println(ZoneId.systemDefault());
        System.out.println(LocalDateTime.now().getHour());
        System.out.println("_".repeat(20));
        Instant instant2 = Instant.now();
        // trailing Z = UTC 0 offset
        System.out.println(instant2);

        System.out.println("_".repeat(20));

        // instant = seconds and nanoseconds from epoch
        for (ZoneId z : List.of(
                ZoneId.of("Australia/Sydney"),
                ZoneId.of("Europe/Paris"),
                ZoneId.of("America/New_York"))) {

            DateTimeFormatter zoneFormat = DateTimeFormatter.ofPattern("z:zzzz");

            System.out.println(z);
            System.out.println("\t" + Instant.now().atZone(z));
            System.out.println("\t" + z.getRules().getDaylightSavings(instant2));
            System.out.println("\t" + z.getRules().isDaylightSavings(instant2));
        }

        // System.out.println(Instant.parse(""));

        ZonedDateTime dobSydney = ZonedDateTime.ofInstant(instant2, ZoneId.of("Australia/Sydney"));
        System.out.println(dobSydney.getDayOfYear());

        LocalDateTime dob = LocalDateTime.of(1999, Month.DECEMBER, 24, 0, 0);
        System.out.println("_".repeat(20));
        System.out.println(dob.getDayOfYear());
        // holiday born gang
        System.out.println(dob.getDayOfWeek());

        Period timePast = Period.between(dob.toLocalDate(), LocalDate.now());
        // 25 yrs
        System.out.println(timePast.getYears());
        // 4 months
        System.out.println(timePast.getMonths());
        // 4 days
        // 4 -4 ? xD
        System.out.println(timePast.getDays());
        // between local dates
        // duration between times
        System.out.println("_".repeat(20));
        Duration timeBetween = Duration.between(Instant.EPOCH, dob.toInstant(ZoneOffset.UTC));

        System.out.println(timeBetween.toHours());

        for (ChronoUnit u : ChronoUnit.values()) {
            if (u.isSupportedBy(LocalDate.EPOCH)) {
                Long val = u.between(LocalDate.EPOCH, dob.toLocalDate());
                System.out.println(u + " Past = " + val);
            } else {
                System.out.println("___ NOT SUPPPORTED ___ =>\t " + u);
            }

        }
        System.out.println("********".repeat(20));
        LocalDateTime test = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        System.out.println(test.format(dtf));
        // testing of all chrono units are supported on DATE TIME CLASS
        for (ChronoUnit u : ChronoUnit.values()) {
            if (u.isSupportedBy(test)) {
                Long val = u.between(test, dob);
                System.out.println(u + " Past = " + val);
            } else {
                System.out.println("___ NOT SUPPPORTED ___ =>\t " + u);
            }

        }

        // from here
        // PRINCIPLES OF LOCALIZATION 14
    }

}
