package com.spring.zuul;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ZuulApplication {

//    public static void main(String[] args) {
//        SpringApplication.run(ZuulApplication.class, args);
//    }

    public static void main(String[] args){

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource  resource = resourceLoader.getResource("result-6-45.csv");
        List<List<Integer>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(resource.getInputStream())) {
            while (scanner.hasNextLine()) {
                records.add(ZuulApplication.getRecordFromLine(scanner.nextLine()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        ZuulApplication.print( records);
        //Count number
        int[] countArray = ZuulApplication.count(records);
//        List<List<Integer>> minusRecords =  ZuulApplication.minusRecord(records);
        printSum( records );
//        printSum( minusRecords );

//        ZuulApplication.print( minusRecords );
    }

    private static List<List<Integer>> minusRecord(List<List<Integer>> records){
        List<List<Integer>> minusRecords = new ArrayList<>();
        for(List<Integer> record : records){
            List<Integer> minusRecord = new ArrayList<>();
            int minusNumber = -1;
            for(Integer number : record){
                if( minusNumber == -1){
                    minusNumber = number;
                }else{
                    minusNumber =number-minusNumber;
                }
                minusRecord.add( minusNumber );
                minusNumber = number;
            }
            minusRecords.add( minusRecord );
        }

        return minusRecords;
    }

    private static int[] count(List<List<Integer>> records){

        int[] countArray = new int[46];
        for(List<Integer> record : records){
            for(Integer number : record){
                countArray[number]++;
            }
        }

        return countArray;
    }

    private static void printSum(List<List<Integer>> records){
        for(List<Integer> record : records){
            int count = 0;
            for(Integer number : record){
                count+=number;
            }
            System.out.println(count);
        }
    }

    private static void print(int[] array, int min){
        for( int i=1;i< array.length;i++){
            if( array[i] >= min){
                System.out.println("Number : "+i+" count : " +array[i]);
            }
        }
    }

    private static void print(List<List<Integer>> records ){

        for(List<Integer> record : records){
            System.out.println(Arrays.toString(record.toArray( new Integer[record.size()])));
        }

    }

    private static List<Integer> getRecordFromLine(String line) {
        List<Integer> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(Integer.valueOf(rowScanner.next()));
            }
        }
        return values;
    }
}
