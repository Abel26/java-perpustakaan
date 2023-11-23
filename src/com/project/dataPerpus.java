package com.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class dataPerpus {

    private static String NIS; // Deklarasi variabel NIS di level class
    private static String pilihBuku;

    public static void main(String[] args) throws IOException {
        Scanner terminalInput = new Scanner(System.in);

        String[][] dataSiswa = {
                {"111", "Abel", "26 Desember 2003"},
                {"222", "Selina", "07 Februari 2003"}
        };

        System.out.println("===== PERPUSTAKAAN SD SUKAMAJU 2 =====");
        System.out.println("\n Pilih Menu");
        System.out.println("1. Pinjam Buku");
        System.out.println("2. List Buku");
        System.out.println("====================\n");

        System.out.print("Pilih menu yang Anda inginkan : ");
        String menu = terminalInput.nextLine();

        switch (menu) {
            case "1":
                System.out.println("===== Menu Pinjam Buku =====");
                System.out.print("Masukan NIS Anda : ");
                NIS = terminalInput.nextLine(); // Assign nilai ke variabel NIS

                // Cari dan cetak data berdasarkan inputan NIS
//                boolean cari = false;
//
//                for (String[] row : dataSiswa) {
//                    if (row[0].equals(NIS)) {
//                        cari = true;
//                        for (String value : row) {
//                            System.out.print(value + " ");
//                        }
//                        break;
//                    }
//                }

//                if (!cari) {
//                    System.err.println("Data tidak ditemukan untuk NIS : " + NIS);
//                }

                dataBuku();

                System.out.print("Pilih buku mata pelajaran yang ingin Anda pinjam : ");
                pilihBuku = terminalInput.nextLine();

                System.out.println("===== Status Peminjaman =====");
                printData(dataSiswa);
                break;

            case "2" :
                dataBuku();
                break;
            default:
                System.err.println("\nInput anda tidak ditemukan\nSilahkan pilih [1-2]");

        }
    }

    private static void dataBuku() throws IOException {
        // Tambahkan implementasi untuk menu 1 (Pinjam Buku) di sini jika diperlukan
        // membaca database ada atau tidak
        try {
            File file = new File("databaseBuku.txt");
        } catch (Exception e) {
            System.err.println("Database tidak ditemukan");
            return;
        }

        // ambil keyword dari user
        Scanner terminalInput = new Scanner(System.in);
        System.out.print("\nKelas berapakah Anda : ");
        String kelas = terminalInput.nextLine();
        String[] keywords = kelas.split("\\s+");

        // cek keyword di database
        cekBukuDiDatabase(keywords);
    }

    private static void cekBukuDiDatabase(String[] keywords) throws IOException {
        FileReader fileInput = new FileReader("databaseBuku.txt");
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

    private static void printData(String[][] data) throws IOException{
        for (String[] row : data) {
            if (row[0].equals(NIS)) {
//                for (String value : row) {
//                    System.out.print(value + " ");
//                }
//                break;
                System.out.println("Nama               : " + row[0]);
                System.out.println("Kelas              : " + row[1]);
                System.out.println("Semester           : " + row[2]);
                System.out.println("Buku yang dipinjam : " + pilihBuku);
                break;
            }
        }
    }
}
