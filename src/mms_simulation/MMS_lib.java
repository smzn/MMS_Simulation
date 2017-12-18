package mms_simulation;

public class MMS_lib {
	private double lambda, mu, rho;
	private double q,l,w,u;
	private int s;
	public MMS_lib(double lambda, double mu, int s) {
		super();
		this.lambda = lambda;
		this.mu = mu;
		this.s = s;
		this.rho = lambda / ( mu * s ) ;
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
