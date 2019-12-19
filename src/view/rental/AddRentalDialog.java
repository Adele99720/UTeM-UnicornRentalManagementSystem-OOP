package view.rental;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.manager.UnicornManager;
import controller.validator.InvalidNumberException;
import controller.validator.MaximumLengthException;
import controller.validator.MaximumNumberException;
import controller.validator.MinimumNumberException;
import controller.validator.RequiredFieldException;
import controller.validator.Validator;
import model.Rental;

public class AddRentalDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextField txtStartDate = new JTextField(15);
	private JTextField txtEndDate = new JTextField(15);
	private JTextField txtDepositPaid = new JTextField();

	private JButton btnSubmit = new JButton("Submit");
	private JButton btnReset = new JButton("Reset");

	public AddRentalDialog(ManageRentalDialog dialog) {
		super(dialog, "Add Rental", true);

		JPanel pnlCenter = new JPanel(new GridLayout(6, 2, 10, 10));
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

		pnlCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
		pnlSouth.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

		pnlCenter.add(new JLabel("StartDate: ", JLabel.RIGHT));
		pnlCenter.add(txtStartDate);
		pnlCenter.add(new JLabel("EndDate: ", JLabel.RIGHT));
		pnlCenter.add(txtEndDate);
		pnlCenter.add(new JLabel("DepositPaid (RM): ", JLabel.RIGHT));
		pnlCenter.add(txtDepositPaid);

		pnlSouth.add(btnSubmit);
		pnlSouth.add(btnReset);

		btnSubmit.addActionListener(this);
		btnReset.addActionListener(this);

		this.add(pnlCenter);
		this.add(pnlSouth, BorderLayout.SOUTH);

		this.getRootPane().setDefaultButton(btnSubmit);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(dialog);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();

		if (source == btnSubmit) {
			Vector<Exception> exceptions = new Vector<>();
			String plateNo = null, model = null;
			double price = 0;
			int capacity = 0;

			try {
				rentalID = Validator.validate("Rental ID", txtRentalID.getText(), true, 15);
			} catch (RequiredFieldException | MaximumLengthException e) {
				exceptions.add(e);
			}

			try {
				 startDate = Validator.validate("StartDate", txtStartDate.getText(), true, 50);
			} catch (RequiredFieldException | MaximumLengthException e) {
				exceptions.add(e);
			}
			
			try {
				endDate = Validator.validate("EndDate", txtEndDate.getText(), true, 50);
			} catch (RequiredFieldException | MaximumLengthException e) {
				exceptions.add(e);
			}

			try {
				depositPaid = Validator.validate("DepositPaid", txtDepositPaid.getText(), true, true, true, 5, 20);
			} catch (RequiredFieldException | InvalidNumberException | MinimumNumberException
					| MaximumNumberException e) {
				exceptions.add(e);
			}


			int size = exceptions.size();

			if (size == 0) {

				Rental rental = new Rental();

				rental.setRentalID(rentalID);
				rental.setStartDate(startDate);
				rental.setEndDate(endDate);
				rental.setDepositPaid(DepositPaid);

				try {
					if (UnicornManager.addUnicorn(unicorn) != -1) {
						JOptionPane.showMessageDialog(this,
								"Rental with ID " + unicorn.getUnicornID() + " has been succesfully added.", "Success",
								JOptionPane.INFORMATION_MESSAGE);
						reset();

					} else {
						JOptionPane.showMessageDialog(this, "Unable to add new Rental.", "Unsuccesful",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (ClassNotFoundException | SQLException e) {
					JOptionPane.showMessageDialog(this, e.getMessage(), "Database Error", JOptionPane.WARNING_MESSAGE);
				}

			} else {
				String message = null;
				if (size == 1) {
					message = exceptions.firstElement().getMessage();
				} else {
					message = "Please fix the following errors: ";

					for (int i = 0; i < size; i++) {
						message += "\n" + (i + 1) + ". " + exceptions.get(i).getMessage();
					}

					JOptionPane.showMessageDialog(this, message, "Validation Errors", JOptionPane.WARNING_MESSAGE);
				}
			}

		} else if (source == btnReset) {
			reset();
		}

	}

	private void reset() {
		txtRentalID.setText("");
		txtStartDate.setText("");
		txtEndDate.setText("");
		txtDepositPaid.setText("");

		txtRentalID.grabFocus();
	}
}