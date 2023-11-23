package com.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class perpustakaan {
    private List<siswa> dataSiswa;
    private String NIS;
    private String pilihBuku;
    private String pilihSemester;

    public perpustakaan() {
        dataSiswa = new ArrayList<>();
        // Inisialisasi data siswa (bisa diganti dengan membaca dari file atau database)
        dataSiswa.add(new siswa("111", "Abel", "26 Desember 2003"));
        dataSiswa.add(new siswa("222", "Selina", "07 Februari 2003"));
    }

    public void pinjamBuku() throws IOException {
        System.out.println("===== Menu Pinjam Buku =====");
        System.out.print("Masukan NIS Anda : ");
        NIS = new Scanner(System.in).nextLine();
        dataBuku();
        System.out.print("\nPilih buku mata pelajaran yang ingin Anda pinjam : ");
        pilihBuku = new Scanner(System.in).nextLine();

        System.out.print("\nPilih semester yang ingin Anda pinjam : ");
        pilihSemester = new Scanner(System.in).nextLine();

        System.out.println("===== Status Peminjaman =====");
        printData();
    }

    public void listBuku() throws IOException {
        dataBuku();
    }

    private void dataBuku() throws IOException {
        // Tambahkan implementasi untuk menu 1 (Pinjam Buku) di sini jika diperlukan
        // membaca database ada atau tidak
        try {
            File file = new File("databaseFiks.txt");
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan");
            return;
        }

        // ambil keyword dari user
        System.out.print("\nKelas berapakah Anda : ");
        String kelas = new Scanner(System.in).nextLine();
        String[] keywords = kelas.split("\\s+");

        // cek keyword di database
        cekBukuDiDatabase(keywords);
    }

    private void cekBukuDiDatabase(String[] keywords) throws IOException {
        FileReader fileInput = new FileReader("databaseFiks.txt");
        BufferedReader bufferInput = new BufferedReader(fileInput);

        String data = bufferInput.readLine();

        int nomorData = 0;
        boolean isExist;
        System.out.println("\n| No |\tKelas |\tSemester                |\tMata Pelajaran               |\tStock");
        System.out.println("----------------------------------------------------------------------------------------------------------");

        while (data != null) {
            nomorData++;
            isExist = true;
            for (String keyword : keywords) {
                isExist = isExist && data.toLowerCase().contains(keyword.toLowerCase());
            }

            if (isExist) {
                StringTokenizer stringToken = new StringTokenizer(data, ",");
                System.out.printf("| %2d ", nomorData);
                System.out.printf("|\t%4s  ", stringToken.nextToken());
                System.out.printf("|\t%-20s   ", stringToken.nextToken());
                System.out.printf("|\t%-20s   ", stringToken.nextToken());
                System.out.printf("|\t%s   ", stringToken.nextToken());
                System.out.print("\n");
            }

            data = bufferInput.readLine();
        }
    }

    private void printData() throws IOException {
        for (siswa siswa : dataSiswa) {
            if (siswa.getNIS().equals(NIS)) {
                System.out.println("Nama               : " + siswa.getNama());
                System.out.println("Kelas              : " + siswa.getTanggalLahir());
                System.out.println("Semester           : " + siswa.getTanggalLahir());
                System.out.println("Buku yang dipinjam : " + pilihBuku + " " + pilihSemester);
                break;
            }
        }
    }

    public static boolean getYesOrNo(String message){

        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\n" + message);
        String pilihanUser = terminalInput.next();

        while(!pilihanUser.equalsIgnoreCase("y") && !pilihanUser.equalsIgnoreCase("n")){
            System.err.println("Pilihan Anda bukan y atau n");
            System.out.print("\n" + message);
            pilihanUser = terminalInput.next();
        }

        return pilihanUser.equalsIgnoreCase("y");
    }

    public static void clearScreen(){
        try{
            if(System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }else{
                System.out.print("\033\143");
            }
        }catch(Exception ex){
            System.out.println("Anda tidak bisa clear screen");
        }
    }
}
