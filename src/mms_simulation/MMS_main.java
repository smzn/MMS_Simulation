package mms_simulation;

import java.util.Arrays;
import java.util.Scanner;

public class MMS_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Input Lambda > ");
		double lambda = Double.parseDouble(scan.next());
		System.out.print("Input mu > ");
		double mu = Double.parseDouble(scan.next());
		System.out.print("Input Number of Server > ");
		int s = Integer.parseInt(scan.next());
		System.out.print("Input Time > ");
		int time = Integer.parseInt(scan.next());

		MMS_lib mms = new MMS_lib(lambda, mu, s, time);
		System.out.println("Simulation : (系内人数) = "+Arrays.toString(mms.getSimulation()));
		mms.functionMMs();

		System.out.println("");
		System.out.println("rho利用率 =" + mms.getrho());
		System.out.println("Q:待ち人数 =" + mms.getQ());
		System.out.println("L:系内人数 =" + mms.getL());
		System.out.println("W:待ち時間 =" + mms.getW());
		System.out.println("U:系内時間 =" + mms.getU());
	}

}
