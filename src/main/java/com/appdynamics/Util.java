package com.appdynamics;

import com.appdynamics.loadgen.CRMOnlineLoadGen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by abey.tom on 10/27/14.
 */
public class Util {
    public static final Logger logger = LoggerFactory.getLogger(Util.class);
    private static final Random random = new Random();
    private static long adjustment = 20;

    static {
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                int value = random.nextInt(20) + 10;
                if (random.nextBoolean()) {
                    adjustment += value;
                } else {
                    adjustment -= value;
                }
                if (adjustment < 0) {
                    adjustment = 0;
                }
                if(adjustment > 60){
                    adjustment = 20;
                }
                logger.info("The new Adjustment is {}", adjustment);
            }
        }, 0, 5, TimeUnit.SECONDS);
    }


    public static boolean applyUserExperience(List<Double> userExpList, List<Long> sleeps) {
        int randomPercent = random.nextInt(100);
        double currentVal = 0;
        for (int i = 0; i < userExpList.size(); i++) {
            if (i == userExpList.size() - 1) {
                try {
                    TimeUnit.MILLISECONDS.sleep(random.nextInt(500));
                } catch (InterruptedException e) {
                    logger.error("", e);
                }
                logger.info("About to throw an exception");
                return false;
            }
            currentVal += userExpList.get(i);
            if (randomPercent < currentVal) {
                try {
                    int max = (int) sleeps.get(i).longValue();
                    int min = 0;
                    if (i > 0) {
                        min = (int) sleeps.get(i - 1).longValue();
                    }
                    int sleep = random.nextInt(max - min) + min;
                    logger.info("Sleeping for {} millis", sleep);
                    TimeUnit.MILLISECONDS.sleep(sleep);
                    return true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public static boolean applyUserExperience1(List<Double> userExpList, List<Range> sleeps) {
        int randomPercent = random.nextInt(100);
        double currentVal = 0;
        for (int i = 0; i < userExpList.size(); i++) {
            Range sleepRange = sleeps.get(i);
            int randomSleepValue = getRandomSleepValue(sleepRange);
            if (i == userExpList.size() - 1) {
                try {
                    logger.info("Sleeping for {} millis", randomSleepValue);
                    TimeUnit.MILLISECONDS.sleep(randomSleepValue);
                } catch (InterruptedException e) {
                    logger.error("", e);
                }
                logger.info("About to throw an exception");
                return false;
            }
            currentVal += userExpList.get(i);
            if (randomPercent < currentVal) {
                try {
                    logger.info("Sleeping for {} millis", randomSleepValue);
                    TimeUnit.MILLISECONDS.sleep(randomSleepValue);
                    return true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private static int getRandomSleepValue(Range sleepRange) {
        int i = random.nextInt(sleepRange.getUpper() - sleepRange.getLower()) + sleepRange.getLower();
        if (i > 220) {
            return 0;
        }
        return i;
    }

    public static List<Double> arrayToDouble(String str) {
        List<Double> list = new ArrayList<Double>();
        String[] split = str.split(",");
        for (String s : split) {
            list.add(Double.parseDouble(s));
        }
        return list;
    }

    public static List<Long> arrayToLong(String str) {
        List<Long> list = new ArrayList<Long>();
        String[] split = str.split(",");
        for (String s : split) {
            list.add(Long.parseLong(s));
        }
        return list;
    }

    public static List<Range> convertToRange(String str) {
        List<Range> ranges = new ArrayList<Range>();
        String[] split = str.split(",");
        for (String s : split) {
            String[] rangeStr = s.split("\\-");
            int lower = Integer.parseInt(rangeStr[0]);
            int upper = Integer.parseInt(rangeStr[1]);
            ranges.add(new Range(lower, upper));
        }
        return ranges;
    }

    public static File getLocation() {
        ProtectionDomain pd = CRMOnlineLoadGen.class.getProtectionDomain();
        URL location = pd.getCodeSource().getLocation();
        File file;
        try {
            file = new File(location.toURI());
        } catch (URISyntaxException e) {
            file = new File(location.getPath());
        }
        return file.getParentFile();
    }

    public static void applyResponseTimeAdjustment() {
        if (adjustment > 0) {
            try {
                Thread.sleep(adjustment);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }
    }
}
