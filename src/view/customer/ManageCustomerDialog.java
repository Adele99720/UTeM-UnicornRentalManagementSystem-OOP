package view.customer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import view.MainFrame;

public class ManageCustomerDialog extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private JButton btnAddCustomer = new JButton("Add Customer");
	private JButton btnUpdateCustomer = new JButton("Search Customer");
	private JButton btnDeleteCustomer = new JButton("Delete Customer");
	private JButton btnListCustomer = new JButton("List Customer");

	public ManageCustomerDialog(MainFrame frame){
		super(frame, "Manage Customer", true);
		
		GridLayout layout = new GridLayout(4, 1, 10, 10);
		
		this.add(btnAddCustomer);
		this.add(btnUpdateCustomer);
		this.add(btnDeleteCustomer);
		this.add(btnListCustomer);


		btnAddCustomer.addActionListener(this);
		btnUpdateCustomer.addActionListener(this);
		btnDeleteCustomer.addActionListener(this);
		btnListCustomer.addActionListener(this);

		this.setLayout(layout);
		this.pack();
		this.setLocationRelativeTo(frame);
		this.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();

		if(source == btnAddCustomer){
			new AddCustomerDialog(this);
		} else if (source == btnUpdateCustomer){
			// TODO: Add ViewCustomers dialog box
		} else if (source == btnDeleteCustomer){
			new DeleteCustomerDialog(this);
		} else if (source == btnListCustomer){
			new ListCustomerDialog(this);
		}

	}


}