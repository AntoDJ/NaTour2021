package com.example.natour2021;

import org.junit.Before;
import org.junit.Test;

import Exception.*;

import static org.junit.Assert.*;

import android.widget.Toast;

import Controller.*;
import Search.DetailView;

public class NaTourTest {

// CHECK PATH TEST

    @Test
    public void checkPathTestSuccess(){
        try{
            Controller.getInstance().checkPath(null, "Sentiero1","Bello",5,5,true,"42.126194082552324 13.366356790065765", "37.82564356422479 9.068414904177189", Controller_Stub.getInstance());
            assertTrue(true);
        }
        catch(NamePathWrongSizeException e1) {fail();}
        catch(PathWrongSizeException e2) {fail();}
        catch(DescriptionWrongSizeException e3) {fail();}
        catch(DifficultyOutOfRangeException e4) {fail();}
        catch(DurationOutOfRangeException e5) {fail();}
    }

    @Test
    public void checkPathTestEmptyName(){
        try{
            Controller.getInstance().checkPath(null, "","Bello",5,5,true,"42.126194082552324 13.366356790065765", "37.82564356422479 9.068414904177189", Controller_Stub.getInstance());
            fail();
        }
        catch(NamePathWrongSizeException e1) {assertTrue(true);}
        catch(PathWrongSizeException e2) {fail();}
        catch(DescriptionWrongSizeException e3) {fail();}
        catch(DifficultyOutOfRangeException e4) {fail();}
        catch(DurationOutOfRangeException e5) {fail();}
    }
    @Test
    public void checkPathTestTooLongName(){
        try{
            Controller.getInstance().checkPath(null, "Sentiero1111111111111111111111111111111111111111111111111111111Sentiero1111111111111111111111111111111111111111111111111111111","Bello",5,5,true,"42.126194082552324 13.366356790065765", "37.82564356422479 9.068414904177189", Controller_Stub.getInstance());
            fail();
        }
        catch(NamePathWrongSizeException e1) {assertTrue(true);}
        catch(PathWrongSizeException e2) {fail();}
        catch(DescriptionWrongSizeException e3) {fail();}
        catch(DifficultyOutOfRangeException e4) {fail();}
        catch(DurationOutOfRangeException e5) {fail();}
    }

    @Test
    public void checkPathTestEmptyStartPoint(){
        try{
            Controller.getInstance().checkPath(null,"Sentiero1","Bello",5,5,true,"", "37.82564356422479 9.068414904177189", Controller_Stub.getInstance());
            fail();
        }
        catch(NamePathWrongSizeException e1) {fail();}
        catch(PathWrongSizeException e2) {assertTrue(true);}
        catch(DescriptionWrongSizeException e3) {fail();}
        catch(DifficultyOutOfRangeException e4) {fail();}
        catch(DurationOutOfRangeException e5) {fail();}
    }

    @Test
    public void checkPathTestTooLongPath(){
        try{
            Controller.getInstance().checkPath(null,"Sentiero1","Bello",5,5,true,"42.126194082552324 13.366356790065765", "37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189 37.82564356422479 9.068414904177189", Controller_Stub.getInstance());
            fail();
        }
        catch(NamePathWrongSizeException e1) {fail();}
        catch(PathWrongSizeException e2) {assertTrue(true);}
        catch(DescriptionWrongSizeException e3) {fail();}
        catch(DifficultyOutOfRangeException e4) {fail();}
        catch(DurationOutOfRangeException e5) {fail();}
    }

    @Test
    public void checkPathTestTooLongDescription(){
        try{
            Controller.getInstance().checkPath(null,"Sentiero1","Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello Bello ",5,5,true,"42.126194082552324 13.366356790065765", "37.82564356422479 9.068414904177189", Controller_Stub.getInstance());
            fail();
        }
        catch(NamePathWrongSizeException e1) {fail();}
        catch(PathWrongSizeException e2) {fail();}
        catch(DescriptionWrongSizeException e3) {assertTrue(true);}
        catch(DifficultyOutOfRangeException e4) {fail();}
        catch(DurationOutOfRangeException e5) {fail();}
    }

    @Test
    public void checkPathTestNegativeDifficulty(){
        try{
            Controller.getInstance().checkPath(null,"Sentiero1","Bello",5,-5,true,"42.126194082552324 13.366356790065765", "37.82564356422479 9.068414904177189", Controller_Stub.getInstance());
            fail();
        }
        catch(NamePathWrongSizeException e1) {fail();}
        catch(PathWrongSizeException e2) {fail();}
        catch(DescriptionWrongSizeException e3) {fail();}
        catch(DifficultyOutOfRangeException e4) {assertTrue(true);}
        catch(DurationOutOfRangeException e5) {fail();}
    }

    @Test
    public void checkPathTestTooDifficulty(){
        try{
            Controller.getInstance().checkPath(null,"Sentiero1","Bello",5,15,true,"42.126194082552324 13.366356790065765", "37.82564356422479 9.068414904177189", Controller_Stub.getInstance());
            fail();
        }
        catch(NamePathWrongSizeException e1) {fail();}
        catch(PathWrongSizeException e2) {fail();}
        catch(DescriptionWrongSizeException e3) {fail();}
        catch(DifficultyOutOfRangeException e4) {assertTrue(true);}
        catch(DurationOutOfRangeException e5) {fail();}
    }

    @Test
    public void checkPathTestNegativeDuration(){
        try{
            Controller.getInstance().checkPath(null,"Sentiero1","Bello",-5,5,true,"42.126194082552324 13.366356790065765", "37.82564356422479 9.068414904177189", Controller_Stub.getInstance());
            fail();
        }
        catch(NamePathWrongSizeException e1) {fail();}
        catch(PathWrongSizeException e2) {fail();}
        catch(DescriptionWrongSizeException e3) {fail();}
        catch(DifficultyOutOfRangeException e4) {fail();}
        catch(DurationOutOfRangeException e5) {assertTrue(true);}
    }

    @Test
    public void checkPathTestTooLongDuration(){
        try{
            Controller.getInstance().checkPath(null,"Sentiero1","Bello",15,5,true,"42.126194082552324 13.366356790065765", "37.82564356422479 9.068414904177189", Controller_Stub.getInstance());
            fail();
        }
        catch(NamePathWrongSizeException e1) {fail();}
        catch(PathWrongSizeException e2) {fail();}
        catch(DescriptionWrongSizeException e3) {fail();}
        catch(DifficultyOutOfRangeException e4) {fail();}
        catch(DurationOutOfRangeException e5) {assertTrue(true);}
    }


//CHECK FILTERS

    @Test
    public void checkFiltersTestSuccess(){
        try{
            Controller.getInstance().checkFilters(null, 0, 10, 0,10, "42.126194082552324 13.366356790065765", true, Controller_Stub.getInstance());
            assertTrue(true);
        }
        catch(PositionNullException e1){fail();}
        catch(DifficultyOutOfRangeException e2){fail();}
        catch(DurationOutOfRangeException e3){fail();}
        catch(DifficultyMinMoreThanMaxException e4){fail();}
        catch(DurationMinMoreThanMaxException e5){fail();}
    }

    @Test
    public void checkFiltersTestNullPosition(){
        try{
            Controller.getInstance().checkFilters(null, 0, 10, 0,10, "", true, Controller_Stub.getInstance());
            fail();
        }
        catch(PositionNullException e1){assertTrue(true);}
        catch(DifficultyOutOfRangeException e2){fail();}
        catch(DurationOutOfRangeException e3){fail();}
        catch(DifficultyMinMoreThanMaxException e4){fail();}
        catch(DurationMinMoreThanMaxException e5){fail();}
    }

    @Test
    public void checkFiltersTestNegativeMindif(){
        try{
            Controller.getInstance().checkFilters(null, 0, 10, -2,10, "42.126194082552324 13.366356790065765", true, Controller_Stub.getInstance());
            fail();
        }
        catch(PositionNullException e1){fail();}
        catch(DifficultyOutOfRangeException e2){assertTrue(true);}
        catch(DurationOutOfRangeException e3){fail();}
        catch(DifficultyMinMoreThanMaxException e4){fail();}
        catch(DurationMinMoreThanMaxException e5){fail();}
    }

    @Test
    public void checkFiltersTestNegativeMaxdif(){
        try{
            Controller.getInstance().checkFilters(null, 0, 10, 0,-10, "42.126194082552324 13.366356790065765", true, Controller_Stub.getInstance());
            fail();
        }
        catch(PositionNullException e1){fail();}
        catch(DifficultyOutOfRangeException e2){assertTrue(true);}
        catch(DurationOutOfRangeException e3){fail();}
        catch(DifficultyMinMoreThanMaxException e4){fail();}
        catch(DurationMinMoreThanMaxException e5){fail();}
    }

    @Test
    public void checkFiltersTestTooBigMindif(){
        try{
            Controller.getInstance().checkFilters(null, 0, 10, 100, 10, "42.126194082552324 13.366356790065765", true, Controller_Stub.getInstance());
            fail();
        }
        catch(PositionNullException e1){fail();}
        catch(DifficultyOutOfRangeException e2){assertTrue(true);}
        catch(DurationOutOfRangeException e3){fail();}
        catch(DifficultyMinMoreThanMaxException e4){fail();}
        catch(DurationMinMoreThanMaxException e5){fail();}
    }

    @Test
    public void checkFiltersTestTooBigMaxdif(){
        try{
            Controller.getInstance().checkFilters(null, 0, 10, 0,100, "42.126194082552324 13.366356790065765", true, Controller_Stub.getInstance());
            fail();
        }
        catch(PositionNullException e1){fail();}
        catch(DifficultyOutOfRangeException e2){assertTrue(true);}
        catch(DurationOutOfRangeException e3){fail();}
        catch(DifficultyMinMoreThanMaxException e4){fail();}
        catch(DurationMinMoreThanMaxException e5){fail();}
    }

    @Test
    public void checkFiltersTestNegativeMindur(){
        try{
            Controller.getInstance().checkFilters(null, -2, 10, 0,10, "42.126194082552324 13.366356790065765", true, Controller_Stub.getInstance());
            fail();
        }
        catch(PositionNullException e1){fail();}
        catch(DifficultyOutOfRangeException e2){fail();}
        catch(DurationOutOfRangeException e3){assertTrue(true);}
        catch(DifficultyMinMoreThanMaxException e4){fail();}
        catch(DurationMinMoreThanMaxException e5){fail();}
    }

    @Test
    public void checkFiltersTestNegativeMaxdur(){
        try{
            Controller.getInstance().checkFilters(null, 0, -10, 0,10, "42.126194082552324 13.366356790065765", true, Controller_Stub.getInstance());
            fail();
        }
        catch(PositionNullException e1){fail();}
        catch(DifficultyOutOfRangeException e2){fail();}
        catch(DurationOutOfRangeException e3){assertTrue(true);}
        catch(DifficultyMinMoreThanMaxException e4){fail();}
        catch(DurationMinMoreThanMaxException e5){fail();}
    }

    @Test
    public void checkFiltersTestTooBigMindur(){
        try{
            Controller.getInstance().checkFilters(null, 100, 10, 0,10, "42.126194082552324 13.366356790065765", true, Controller_Stub.getInstance());
            fail();
        }
        catch(PositionNullException e1){fail();}
        catch(DifficultyOutOfRangeException e2){fail();}
        catch(DurationOutOfRangeException e3){assertTrue(true);}
        catch(DifficultyMinMoreThanMaxException e4){fail();}
        catch(DurationMinMoreThanMaxException e5){fail();}
    }

    @Test
    public void checkFiltersTestTooBigMaxdur(){
        try{
            Controller.getInstance().checkFilters(null, 0, 100, 0,10, "42.126194082552324 13.366356790065765", true, Controller_Stub.getInstance());
            fail();
        }
        catch(PositionNullException e1){fail();}
        catch(DifficultyOutOfRangeException e2){fail();}
        catch(DurationOutOfRangeException e3){assertTrue(true);}
        catch(DifficultyMinMoreThanMaxException e4){fail();}
        catch(DurationMinMoreThanMaxException e5){fail();}
    }

    @Test
    public void checkFiltersTestMindifMoreThanMaxdif(){
        try{
            Controller.getInstance().checkFilters(null, 0, 10, 10,0, "42.126194082552324 13.366356790065765", true, Controller_Stub.getInstance());
            fail();
        }
        catch(PositionNullException e1){fail();}
        catch(DifficultyOutOfRangeException e2){fail();}
        catch(DurationOutOfRangeException e3){fail();}
        catch(DifficultyMinMoreThanMaxException e4){assertTrue(true);}
        catch(DurationMinMoreThanMaxException e5){fail();}
    }

    @Test
    public void checkFiltersTestMindurMoreThanMaxdur(){
        try{
            Controller.getInstance().checkFilters(null, 10, 0, 0,10, "42.126194082552324 13.366356790065765", true, Controller_Stub.getInstance());
            fail();
        }
        catch(PositionNullException e1){fail();}
        catch(DifficultyOutOfRangeException e2){fail();}
        catch(DurationOutOfRangeException e3){fail();}
        catch(DifficultyMinMoreThanMaxException e4){fail();}
        catch(DurationMinMoreThanMaxException e5){assertTrue(true);}
    }


// CHECK REPORT

    @Test
    public void checkReportSuccess(){
        try {
            Controller.getInstance().checkReport(null, "Sentiero 1","", "Brutto" ,"io", Controller_Stub.getInstance());
            assertTrue(true);
        }
        catch(AnswerNotEmptyException e1){fail();}
        catch(MotivationWrongSizeException e2){fail();}
        catch(CreatorWrongSizeException e3){fail();}
        catch(ReporterWrongSizeException e4){fail();}
        catch(CreatorEqualsReporterException e5){fail();}
        catch(NamePathWrongSizeException e6){fail();}

    }

}
