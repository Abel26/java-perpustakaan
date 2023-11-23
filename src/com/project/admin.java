package com.project;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class admin {
    public static void main(String[] args) throws IOException {
        Scanner terminalInput = new Scanner(System.in);

        boolean ulang = true;

        while(ulang){
            System.out.println("Database Perpustakaan\n");
            System.out.println("1.\tLihat Seluruh Buku");
            System.out.println("2.\tCari Data Buku");
            System.out.println("3.\tTambah data buku");
            System.out.println("4.\tUbah data buku");
            System.out.println("5.\tHapus data buku");

            System.out.print("Pilih menu [1-5] : ");
            String pilihanAdmin = terminalInput.nextLine();

            switch(pilihanAdmin){
                case "1":
                    System.out.println("\n=================");
                    System.out.println("LIST SELURUH BUKU");
                    System.out.println("=================");
                    tampilkanData();
                    break;
                case "2":
                    System.out.println("\n=========");
                    System.out.println("CARI BUKU");
                    System.out.println("=========");
                    cariData();
                    break;
                case "3":
                    System.out.println("\n================");
                    System.out.println("TAMBAH DATA BUKU");
                    System.out.println("================");
                    tambahData();
                    break;
                case "4":
                    System.out.println("\n==============");
                    System.out.println("UBAH DATA BUKU");
                    System.out.println("==============");
                    // ubah data
                    break;
                case "5":
                    System.out.println("\n===============");
                    System.out.println("HAPUS DATA BUKU");
                    System.out.println("===============");
                    hapusData();
                    break;
                default:
                    System.err.print("\nInput anda tidak ditemukan\nSilahkan pilih [1-5] : ");
            }
            ulang = getYesOrNo("Apakah Anda ingin melanjutkan ? ");
        }
        terminalInput.close();
    }

    private static void tampilkanData() throws IOException {

        FileReader fileInput;
        BufferedReader bufferInput;

        try {
            fileInput = new FileReader("databaseBuku.txt");
            bufferInput = new BufferedReader(fileInput);
        } catch (Exception e){
            System.err.println("Database Tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            tambahData();
            return;
        }


        System.out.println("\n| No |\tKelas |\tSemester                |\tMata Pelajaran               |\tStock");
        System.out.println("----------------------------------------------------------------------------------------------------------");

        String data = bufferInput.readLine();
        int nomorData = 0;
        while(data != null) {
            nomorData++;

            StringTokenizer stringToken = new StringTokenizer(data, ",");
            System.out.printf("| %2d ", nomorData);
            System.out.printf("|\t%4s  ", stringToken.nextToken());
            System.out.printf("|\t%-20s   ", stringToken.nextToken());
            System.out.printf("|\t%-20s   ", stringToken.nextToken());
            System.out.printf("|\t%s   ", stringToken.nextToken());
            System.out.print("\n");

            data = bufferInput.readLine();
        }

        System.out.println("----------------------------------------------------------------------------------------------------------");
    }

    private static void hapusData() throws IOException{
        // ambil database original
        File database = new File("databaseBuku.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferedInput = new BufferedReader(fileInput);

        // buat database sementara
        File tempDB = new File("databaseFiks.txt");
        FileWriter fileOutput = new FileWriter(tempDB);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        // tampilkan data
        System.out.println("List Buku");
        tampilkanData();

        // ambil user input untuk mendelete data
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nMasukan nomor buku yang akan dihapus : ");
        int deleteNum = terminalInput.nextInt();

        // looping untuk membaca tiap data baris dan skip data yang akan didelete
        int entryCounts = 0;

        String data = bufferedInput.readLine();

        while(data != null){
            entryCounts++;
            boolean isDelete = false;

            StringTokenizer st = new StringTokenizer(data, ",");
            // tampilkan data yang ingin dihapus
            if(deleteNum == entryCounts){
                System.out.println("\nData yang ingin Anda hapus adalah");
                System.out.println("-----------------------------------");
                System.out.println("Kelas          : " + st.nextToken());
                System.out.println("Semester       : " + st.nextToken());
                System.out.println("Mata Pelajaran : " + st.nextToken());
                System.out.println("Stock          : " + st.nextToken());
                isDelete = getYesOrNo("Apakah anda yakin akan menghapus?");
            }

            if(isDelete == true){
                // skip pindahkan data dari original ke sementara
                System.out.println("Data berhasil dihapus");
            }else{
                // pindahkan data dari original ke sementara
                bufferedOutput.write(data);
                bufferedOutput.newLine();
            }
            data = bufferedInput.readLine();
        }
        // menulis data ke file
        bufferedOutput.flush();
        // delete original file
        database.delete();
        // rename file sementara ke database
        tempDB.renameTo(database);
    }

    private static void cariData() throws IOException{

        // membaca database ada atau tidak

        try {
            File file = new File("databaseBuku.txt");
        } catch (Exception e){
            System.err.println("Database Tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahulu");
            tambahData();
            return;
        }

        // kita ambil keyword dari user

        Scanner terminalInput = new Scanner(System.in);
        System.out.print("Masukan kata kunci untuk mencari buku: ");
        String cariString = terminalInput.nextLine();
        String[] keywords = cariString.split("\\s+");

        // kita cek keyword di database
        cekBukuDiDatabase(keywords,true);
    }

    private static boolean cekBukuDiDatabase(String[] keywords, boolean isDisplay) throws IOException{

        FileReader fileInput = new FileReader("databaseBuku.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();
        boolean isExist = false;
        int nomorData = 0;

        if (isDisplay) {
            System.out.println("\n| No |\tKelas |\tSemester                |\tMata Pelajaran               |\tStock");
            System.out.println("----------------------------------------------------------------------------------------------------------");
        }

        while(data != null){

            // cek keywords didalam baris
            isExist = true;

            for(String keyword:keywords){
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }

            // jika keywordsnya cocok maka tampilkan

            if(isExist){
                if(isDisplay) {
                    nomorData++;
                    StringTokenizer stringToken = new StringTokenizer(data, ",");

                    System.out.printf("| %2d ", nomorData);
                    System.out.printf("|\t%4s  ", stringToken.nextToken());
                    System.out.printf("|\t%-20s   ", stringToken.nextToken());
                    System.out.printf("|\t%-20s   ", stringToken.nextToken());
                    System.out.printf("|\t%s   ", stringToken.nextToken());
                    System.out.print("\n");
                } else {
                    break;
                }
            }

            data = bufferInput.readLine();
        }

        if (isDisplay){
            System.out.println("----------------------------------------------------------------------------------------------------------");
        }

        return isExist;
    }

    private static void tambahData() throws IOException{


        FileWriter fileOutput = new FileWriter("databaseBuku.txt",true);
        BufferedWriter bufferOutput = new BufferedWriter(fileOutput);


        // mengambil input dari user
        Scanner terminalInput = new Scanner(System.in);
        String kelas,semester,mapel,stock;

        // Mengambil input dari user
        System.out.print("Masukan kelas          : ");
        kelas = terminalInput.nextLine();
        System.out.print("Masukan Semester       : ");
        semester = terminalInput.nextLine();
        System.out.print("Masukan Mata Pelajaran : ");
        mapel = terminalInput.nextLine();
        System.out.print("Masukan Stock          : ");
        stock = terminalInput.nextLine();

        // cek buku di database

        String[] keywords = {kelas+","+semester+","+mapel+","+stock};

        boolean isExist = cekBukuDiDatabase(keywords,false);

        // menulis buku di databse
        if (!isExist){
            System.out.println("\nData yang akan Anda masukan adalah");
            System.out.println("----------------------------------------");
            System.out.println("Kelas          : " + kelas);
            System.out.println("Semester       : " + semester);
            System.out.println("Mata Pelajaran : " + mapel);
            System.out.println("Stock          : " + stock);

            boolean isTambah = getYesOrNo("Apakah akan ingin menambah data tersebut? ");

            if(isTambah){
                bufferOutput.write(kelas + "," + semester + ","+ mapel +"," + stock);
                bufferOutput.newLine();
                bufferOutput.flush();
                System.out.println("Data berhasil ditambahkan");
            }

        } else {
            System.out.println("buku yang anda akan masukan sudah tersedia di database dengan data berikut:");
            cekBukuDiDatabase(keywords,true);
        }
        bufferOutput.close();
    }

    // private static File databaseBuku = new File("databaseBuku.txt");
    private static boolean getYesOrNo(String message){
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\n"+message+" (y/n)? ");
        String pilihanUser = terminalInput.next();

        while(!pilihanUser.equalsIgnoreCase("y") && !pilihanUser.equalsIgnoreCase("n")) {
            System.err.println("Pilihan anda bukan y atau n");
            System.out.print("\n"+message+" (y/n)? ");
            pilihanUser = terminalInput.next();
        }

        return pilihanUser.equalsIgnoreCase("y");

    }
}
