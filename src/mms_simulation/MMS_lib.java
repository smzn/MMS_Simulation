package mms_simulation;

import java.util.Arrays;
import java.util.Random;

public class MMS_lib {
	private double lambda, mu, rho;
	private double q,l,w,u;
	private int s;
	private int time;
	Random rnd = new Random();
	double result[] = new double[1]; 
	
	public MMS_lib(double lambda, double mu, int s, int time) {
		this.lambda = lambda;
		this.mu = mu;
		this.s = s;
		this.time = time;
		this.rho = lambda / ( mu * s ) ;
	}
	
	public double[] getSimulation() {
		double arrival = this.getExponential(lambda);
		double service[] = new double[s];
		int queue[] = new int[s]; //サービス中を含むキューの長さ
		double elapse = 0;
		double total_queue = 0; //延べ系内人数
		int sum_queue = 0;
		
		arrival = this.getExponential(lambda); //次の到着までの時間
		service[0] = arrival + this.getExponential(mu); //到着した客のサービス時間設定
		
		while(elapse < time) {
			double mini_service = 100000; //最小のサービス時間
			int mini_index = -1; //最小のサービス時間をもつノード
			for(int i = 0; i < s; i++) {
				if( queue[i] > 0) {
					if( mini_service > service[i]) {
						mini_service = service[i];
						mini_index = i;
					}
				}
			}
			if( arrival < mini_service) { //到着が発生
				total_queue += sum_queue * arrival;
				sum_queue++;
				elapse += arrival;
				for(int i = 0; i < s; i++) {
					if( queue[i] > 0) service[i] -= arrival;
				}
				for(int i = 0; i < s; i++) { //到着した客のサービス時間を設定(どこかのノードが空の時)
					if( queue[i] == 0) {
						service[i] = this.getExponential(mu);
						queue[i] ++;
						break;
					}
				}
				arrival = this.getExponential(lambda); //次の到着までの時間
			}
			else if( arrival >= mini_service ) { //退去が発生
				total_queue += sum_queue * mini_service;
				sum_queue --;
				elapse += mini_service;
				arrival -= mini_service;
				for(int i = 0; i < s; i++) {
					if( queue[i] > 0) service[i] -= mini_service;
				}
				queue[mini_index] --;
				if( sum_queue >= s) { //サービス時間を割り当てられていない客がいる場合
					service[mini_index] = this.getExponential(mu);
					queue[mini_index]++;
				}
				else if( sum_queue == 0) {
					service[0] = arrival + this.getExponential(mu); //到着した客のサービス時間設定
				}
			}
			//System.out.println("queue = "+Arrays.toString(queue));
			//System.out.println("Arrival = "+arrival);
			//System.out.println("service = "+Arrays.toString(service));
		}
		result[0] = total_queue / time;
		return result; //イベントドリブン型
	}
	
	//指数乱数発生
	public double getExponential(double param) {
		return - Math.log(1 - rnd.nextDouble()) / param;
	}
	
	//nの階乗関数
	//引数nの階乗を返す
	public  int factorial(int n){
		int fact = 1;
		if(n == 0)
			return fact;

		else{
			for(int i=n; i>0; i--)
				fact *= i;

			return fact;
		}

	}
	//p0を求める関数
	//引数 a, row,でp0を返す
	public  double functionP0(double a, double row){
		double p0,temp=0;

		for(int k=0; k<=s-1; k++){
			temp += (Math.pow(a, (double) k) / factorial(k));
			//System.out.println("temp="+temp);
		}
		temp +=( Math.pow(a,(double) s)/ (factorial(s)*(1.0-row)));
		p0 = Math.pow(temp,-1);

		return p0;
	}

	//計算用エンジン関数
	public  void functionMMs(){
		double p0,a;

		a = lambda/mu;
		rho = a/(double) s;

		p0 = functionP0(a, rho);
		//待ち人数
		q = (Math.pow((double) s,(double) s) * Math.pow(rho,(double) s+1.0)) / (factorial(s) * Math.pow(1.0-rho,2.0)) * p0;
		l = q + a;			//系内人数
		w = q / lambda;		//待ち時間
		u = w + (1/mu); 	//系内時間
	}
	//各解を求める関数
	//利用率：ρ
	public double getrho() {
		return rho;
	}
	//Q：待ち人数
	public double getQ() {
		return q;
	}
	//L：系内人数
	public double getL() {
		return l;
	}
	//W：待ち時間
	public double getW() {
		return w;
	}
	//U:系内時間
	public double getU() {
		return u;
	}

	

}
