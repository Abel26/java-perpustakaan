package com.project;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class admin {
    public static void main(String[] args) throws IOException {
        perpustakaan perpustakaan = new perpustakaan();
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
            ulang = perpustakaan.getYesOrNo("Apakah Anda ingin melanjutkan ? ");
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
            System.err.println("Silahkan tambah data terlebih dahoeloe");
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

    private static void cariData() throws IOException{

        // membaca database ada atau tidak

        try {
            File file = new File("databaseBuku.txt");
        } catch (Exception e){
            System.err.println("Database Tidak ditemukan");
            System.err.println("Silahkan tambah data terlebih dahoeloe");
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

            boolean isTambah = perpustakaan.getYesOrNo("Apakah akan ingin menambah data tersebut? ");

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

    private static File databaseBuku = new File("databaseBuku.txt");
    private static void hapusData() throws IOException {
        // Ambil database original
        File database = new File("databaseBuku.txt");
        FileReader fileInput = new FileReader(database);
        BufferedReader bufferInput = new BufferedReader(fileInput);

        // Buat database sementara
        File tempDb = new File("tempDB.txt");
        FileWriter fileOutput = new FileWriter(tempDb);
        BufferedWriter bufferedOutput = new BufferedWriter(fileOutput);

        // Tampilkan data
        tampilkanData();

        // Ambil user input untuk menghapus data
        Scanner terminalInput = new Scanner(System.in);

        System.out.print("\n\nMasukkan nomor buku yang akan dihapus: ");
        int deleteNum = terminalInput.nextInt();

        // Looping untuk membaca tiap data baris dan skip data yang akan dihapus
        int entryCounts = 0;

        String data = bufferInput.readLine();

        while (data != null) {
            entryCounts++;
            boolean isDelete = false;

            StringTokenizer st = new StringTokenizer(data, ",");

            if (deleteNum == entryCounts) {
                System.out.println("\n\nData yang ingin Anda hapus adalah: ");
                System.out.println("----------------------------------------");
                System.out.println("Kelas          : " + st.nextToken());
                System.out.println("Semester       : " + st.nextToken());
                System.out.println("Mata Pelajaran : " + st.nextToken());
                System.out.println("Stock          : " + st.nextToken());

                isDelete = perpustakaan.getYesOrNo("Apakah Anda yakin akan menghapus data tersebut? ");
            }

            if (isDelete) {
                // Skip data yang akan dihapus
                System.out.println("Data berhasil dihapus");
            } else {
                // Pindahkan data dari original ke sementara
                bufferedOutput.write(data);
                bufferedOutput.newLine();
            }
            data = bufferInput.readLine();
        }

        // Tutup file input
        bufferInput.close();

        // Menulis data ke file
        bufferedOutput.flush();
        // Delete original file
        if (database.delete()) {
            System.out.println("File original berhasil dihapus");
        } else {
            System.out.println("Gagal menghapus file original");
        }

        // Rename file sementara ke database
        if (tempDb.renameTo(database)) {
            System.out.println("File sementara berhasil direname");
        } else {
            System.out.println("Gagal melakukan rename file sementara");
        }

        // Tutup file output
        bufferedOutput.close();
    }
}
