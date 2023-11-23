package com.project;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        perpustakaan perpustakaan = new perpustakaan();

        boolean ulang = true;

        while(ulang){
            perpustakaan.clearScreen();
        System.out.println("===== PERPUSTAKAAN SD SUKAMAJU 2 =====");
        System.out.println("\n Pilih Menu");
        System.out.println("1. Pinjam Buku");
        System.out.println("2. List Buku");
        System.out.println("====================\n");

        System.out.print("Pilih menu yang Anda inginkan : ");
        String menu = new Scanner(System.in).nextLine();

        switch (menu) {
            case "1":
                perpustakaan.pinjamBuku();
                break;
            case "2":
                perpustakaan.listBuku();
                break;
            default:
                System.err.println("\nInput anda tidak ditemukan\nSilahkan pilih [1-2]");
        }
            ulang = perpustakaan.getYesOrNo("Apakah Anda ingin melanjutkan [y/n] ?");
        }
    }
}
