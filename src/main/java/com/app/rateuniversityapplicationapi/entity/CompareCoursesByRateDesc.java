package com.app.rateuniversityapplicationapi.entity;

import java.util.Comparator;

public class CompareCoursesByRateDesc implements Comparator<Course> {

    @Override
    public int compare(Course o1, Course o2) {
        return (int) (o2.getCourseRating() - o1.getCourseRating());
    }
}
