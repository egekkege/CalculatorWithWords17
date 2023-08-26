package consumer;

import osgi2.converter.*;
import osgi2.converter.impl.ConverterImpl.DefaultProcessor;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import javax.swing.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.util.Locale;
import java.util.ResourceBundle;


public class Activator implements BundleActivator {
	
	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("Consumer Starting...");
		ServiceReference<?> serviceReference = context.getServiceReference(Converter.class);
		Converter converter = (Converter) context.getService(serviceReference);
		//Gui.Interface();
		Interface(converter);
		System.out.println("Consumer Started");

	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		System.out.println("Consumer Stopped");
	}
	

	public void Interface(Converter converter) {


		ResourceBundle messages; // Resource bundle for handling localized messages
		final Locale[] locale = { Locale.getDefault() }; // Array to hold the locale,initially set to default
		if (!locale[0].getLanguage().equals("tr")) {
			locale[0] = Locale.ENGLISH; // Change the locale to English if not Turkish 
			}
			messages = ResourceBundle.getBundle("Bundle", locale[0]); // Load messages based on the locale

			//System.out.println("1"); 
			// Create a JFrame Object
			JFrame frame = new JFrame();
			//System.out.println("2");
			JLabel label = new JLabel(messages.getString("label.label"), JLabel.LEFT);
			label.setBounds(40, 20, 60, 20);

			JLabel label2 = new JLabel(messages.getString("label.label2"), JLabel.LEFT);
			label2.setBounds(40, 81, 60, 20);
			JLabel label3 = new JLabel(messages.getString("label.label3"), JLabel.LEFT);
			label3.setBounds(40, 141, 60, 20); // Add label to frame
			frame.add(label);
			frame.add(label2);
			frame.add(label3); // Create a textfield object
			JTextField textBox = new JTextField();
			JTextField textBox2 = new JTextField();
			JTextField textBox3 = new JTextField(); // Set the position of the textfield
			textBox.setBounds(140, 20, 220, 55);
			textBox2.setBounds(140, 81, 220, 55);
			textBox3.setBounds(140, 141, 220, 55); // Add the textfield into the form
			frame.add(textBox);
			frame.add(textBox2);
			frame.add(textBox3); // Create Submit button
			JButton sum = new JButton(messages.getString("button.sum"));
			JButton subtract = new JButton(messages.getString("button.subtract"));
			JButton multiply = new JButton(messages.getString("button.multiply"));
			JButton divide = new JButton(messages.getString("button.divide")); // Set button position
			sum.setBounds(40, 200, 80, 20);
			subtract.setBounds(120, 200, 80, 20);
			multiply.setBounds(200, 200, 80, 20);
			divide.setBounds(280, 200, 80, 20);
			// Add button to frame
			frame.add(sum);
			frame.add(subtract);
			frame.add(multiply);
			frame.add(divide);

			// Create Exit button
			JButton exitButton = new JButton(messages.getString("button.exit")); // Set button position
			exitButton.setBounds(160, 220, 80, 20); // Add button to frame
			frame.add(exitButton);

			// Set the size of the form
			frame.setSize(400, 300); // Set the layout
			frame.setLayout(null); // Set the title of the form
			frame.setTitle(messages.getString("title"));
			// set form in the center of the screen
			frame.setLocationRelativeTo(null);

			// Display the form

			frame.setVisible(true); //
			//ServiceReference<Converter> serviceReference = context.getServiceReference(Converter.class);
			//
			//Converter converter2 = (Converter) context.getService(serviceReference);
			DefaultProcessor processor = new DefaultProcessor();
			sum.addActionListener((ActionEvent e) -> {
				String input1 = textBox.getText();
				String input2 = textBox2.getText();
				BigInteger inputNumber1;
				BigInteger inputNumber2;
				if (locale[0].getLanguage().equals("tr")) {
					inputNumber1 = converter.convertTurkishToNumber(input1);
					inputNumber2 = converter.convertTurkishToNumber(input2);
					BigInteger result = inputNumber1.add(inputNumber2);
					textBox3.setText(converter.converterFromNumToTr(result.toString()));

				} else {
					inputNumber1 = converter.convertEnglishToNumber(input1);
					inputNumber2 = converter.convertEnglishToNumber(input2);
					BigInteger result = inputNumber1.add(inputNumber2);
					textBox3.setText(processor.getName(result.toString()));
				}

			});

			subtract.addActionListener((ActionEvent e) -> {
				String input1 = textBox.getText();
				String input2 = textBox2.getText();
				BigInteger inputNumber1;
				BigInteger inputNumber2;
				if (locale[0].getLanguage().equals("tr")) {
					inputNumber1 = converter.convertTurkishToNumber(input1);
					inputNumber2 = converter.convertTurkishToNumber(input2);
					BigInteger result = inputNumber1.subtract(inputNumber2);
					textBox3.setText(converter.converterFromNumToTr(result.toString()));
				} else {
					inputNumber1 = converter.convertEnglishToNumber(input1);
					inputNumber2 = converter.convertEnglishToNumber(input2);
					BigInteger result = inputNumber1.subtract(inputNumber2);
					textBox3.setText(processor.getName(result.toString()));
				}

			});

			multiply.addActionListener((ActionEvent e) -> {
				String input1 = textBox.getText();
				String input2 = textBox2.getText();
				BigInteger inputNumber1;
				BigInteger inputNumber2;
				if (locale[0].getLanguage().equals("tr")) {
					inputNumber1 = converter.convertTurkishToNumber(input1);
					inputNumber2 = converter.convertTurkishToNumber(input2);
					BigInteger result = inputNumber1.multiply(inputNumber2);
					textBox3.setText(converter.converterFromNumToTr(result.toString()));
				} else {
					inputNumber1 = converter.convertEnglishToNumber(input1);
					inputNumber2 = converter.convertEnglishToNumber(input2);
					BigInteger result = inputNumber1.multiply(inputNumber2);
					textBox3.setText(processor.getName(result.toString()));
				}

			});

			divide.addActionListener((ActionEvent e) -> {
				String input1 = textBox.getText();
				String input2 = textBox2.getText();
				BigInteger inputNumber1;
				BigInteger inputNumber2;
				if (locale[0].getLanguage().equals("tr")) {

					inputNumber1 = converter.convertTurkishToNumber(input1);
					inputNumber2 = converter.convertTurkishToNumber(input2);
					if (inputNumber2.equals(BigInteger.ZERO)) {
						textBox3.setText("Bir sayıyı 0'a bölemezsiniz!");

					} else {
						BigInteger result = inputNumber1.divide(inputNumber2);
						textBox3.setText(converter.converterFromNumToTr(result.toString()));
					}

				} else {

					inputNumber1 = converter.convertEnglishToNumber(input1);
					inputNumber2 = converter.convertEnglishToNumber(input2);
					if (!inputNumber2.equals(BigInteger.ZERO)) {
						BigInteger result = inputNumber1.divide(inputNumber2);
						textBox3.setText(processor.getName(result.toString()));
					} else {
						textBox3.setText("Can not divide by zero!");
					}
				}

			});

			exitButton.addActionListener((ActionEvent e) -> {

				System.exit(0);
			});

		}

	}