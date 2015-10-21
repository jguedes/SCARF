/**
 * 
 */
package br.com.jguedes.scarf.ferramentas;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author joao - Esta classe e' utilizada para otimizar a utilizacao da classe
 *         java.util.Scanner e trabalhar com outros servicos. Tipo pegar uma
 *         data.
 */
public class MeuScanner {

	private static MeuScanner meuScanner;

	private Scanner scanner;

	private MeuScanner() {

		scanner = new Scanner(System.in);

	}

	public static synchronized MeuScanner getInstance() {

		if (meuScanner == null) {

			meuScanner = new MeuScanner();

		}

		return meuScanner;

	}

	public Object clone() throws CloneNotSupportedException {

		throw new CloneNotSupportedException();

	}

	public String next() {

		String i = scanner.next();

		scanner.nextLine();

		return i;

	}

	public int nextInt() {
		int i;

		try {

			i = scanner.nextInt();

			scanner.nextLine();

		} catch (InputMismatchException e) {

			i = 0;

			scanner.nextLine();

		}

		return i;

	}

	public double nextDouble() {

		double i;

		try {

			i = scanner.nextDouble();

			scanner.nextLine();

		} catch (InputMismatchException e) {

			i = 0;

			scanner.nextLine();

		}

		return i;

	}

	public String nextLine() {

		String i = scanner.nextLine();

		return i;

	}

	/**
	 * Este metodo solicita ao usuario uma data. Se os dados nao forem validos
	 * retorna null.
	 * */
	public Date nextDateOnly() {

		String dateformat = "dd/MM/yyyy";

		SimpleDateFormat sdf = new SimpleDateFormat(dateformat);

		Date date = new Date();

		StringBuilder dataMontado;

		boolean digitarOutraData = false;

		do {

			dataMontado = new StringBuilder();

			System.out.println("Informe dia, mes e ano: ");
			System.out.print("\tdia: ");
			dataMontado.append(nextInt());
			dataMontado.append("/");
			System.out.print("\tmes: ");
			dataMontado.append(nextInt());
			dataMontado.append("/");
			System.out.print("\tano: ");
			dataMontado.append(nextInt());

			try {

				sdf.setLenient(false);

				date = sdf.parse(dataMontado.toString());

				digitarOutraData = false;

				return date;

			} catch (Exception e) {

				System.out.println("Data invalida: " + dataMontado.toString());

				date = null;

				digitarOutraData = digitarOutra("data");

			}

		} while (digitarOutraData);

		return date;

	}

	/**
	 * Este metodo solicita ao usuario uma data e hora. Se os dados nao forem
	 * validos retorna null.
	 * */
	public Date nextDateWithHour() {

		Date date = nextDateOnly();

		if (date == null)
			return null;

		String format = "dd/MM/yyyy";

		String dateString = new SimpleDateFormat(format).format(date);

		format = "dd/MM/yyyy | HH:mm";

		SimpleDateFormat sdf = new SimpleDateFormat(format);

		StringBuilder dateWithHourMontado;

		boolean digitarOtherHour = false;

		do {

			dateWithHourMontado = new StringBuilder();

			dateWithHourMontado.append(dateString);
			dateWithHourMontado.append(" | ");
			System.out.println("Informe hora e minuto: ");
			System.out.print("\thora: ");
			dateWithHourMontado.append(nextInt());
			dateWithHourMontado.append(":");
			System.out.print("\tminuto: ");
			dateWithHourMontado.append(nextInt());

			try {

				sdf.setLenient(false);

				date = sdf.parse(dateWithHourMontado.toString());

				digitarOtherHour = false;

				return date;

			} catch (Exception e) {

				System.out.println("Hora invalida: "
						+ dateWithHourMontado.toString());

				date = null;

				digitarOtherHour = digitarOutra("hora");

			}

		} while (digitarOtherHour);

		return date;

	}

	private boolean digitarOutra(String dataOuHora) {

		int op;

		do {

			System.out.print("\n\t1 - Digitar outra " + dataOuHora
					+ ".\n\t2 - Sair\nOpcao: ");

			op = nextInt();

		} while ((op != 1) && (op != 2));

		return (op == 1);

	}

	/**
	 * Este metodo funciona atribuindo a opcaoTrue o valor 1(true) e a
	 * opcaoFalse o valor 2(false). O usuario pode escolher apenas entre essas
	 * duas opcoes.
	 * */
	public boolean nextOpcaoTrueFalse(String opcaoTrue, String opcaoFalse) {

		int op;

		do {

			System.out.print("\n\t1 - " + opcaoTrue + "\n\t2 - " + opcaoFalse
					+ "\nOpcao: ");

			op = MeuScanner.getInstance().nextInt();

		} while ((op != 1) && (op != 2));

		return (op == 1);

	}

}
