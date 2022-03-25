package juego.mundo;

public class Premio {
	private int premio;

	public Premio(int premio) {
		this.premio = premio;
	}
	
	public void aumentarPremio(int valor){
		premio += valor;
	}
	public int getPremio(){
		return premio;
	}
}
