package ee.ut.math.tvt.BSS;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

public class StockNewItem extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StockTableModel model;
	private static final Logger log = Logger.getLogger(StockNewItem.class);

	public JLabel idLabel;
	public JLabel nameLabel;
	public JLabel priceLabel;
	public JLabel quantityLabel;
	public JLabel descriptionLabel;

	public JNumericField idField;
	public JTextField nameField;
	public JNumericField priceField;
	public JNumericField quantityField;
	public JTextField descriptionField;
	public JButton submitButton;
	public JButton cancelButton;

	public StockNewItem(StockTableModel model) {
		super("Add new item");
		this.model = model;
		draw();
	}

	protected void draw() {
		int width = 250;
		int height = 250;

		GridBagLayout gbl = new GridBagLayout();
		this.setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();

		idLabel = new JLabel("Id:");
		c.gridx = 0;
		c.gridy = 0;
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(idLabel, c);

		nameLabel = new JLabel("Name:");
		c.gridx = 0;
		c.gridy = 1;
		this.add(nameLabel, c);

		priceLabel = new JLabel("Price:");
		c.gridx = 0;
		c.gridy = 2;
		this.add(priceLabel, c);

		quantityLabel = new JLabel("Quantity:");
		c.gridx = 0;
		c.gridy = 3;
		this.add(quantityLabel, c);

		descriptionLabel = new JLabel("Description:");
		c.gridx = 0;
		c.gridy = 4;
		this.add(descriptionLabel, c);

		
		Long newBarcode = new Long(0);
		java.util.List<StockItem> getRows = model.getTableRows();

		for (int i = 0; i < model.getRowCount(); i++) {
			if (newBarcode < getRows.get(i).getId())
				newBarcode = getRows.get(i).getId();
		}
		newBarcode++;		
		
		idField = new JNumericField(newBarcode.toString(), 10, JNumericField.NUMERIC);
		idField.setMaxLength(6);
		idField.setFormat(1);
		c.gridx = 1;
		c.gridy = 0;
		idField.setEditable(true);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(idField, c);

		nameField = new JTextField();
		c.gridx = 1;
		c.gridy = 1;
		nameField.setEditable(true);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(nameField, c);

		priceField = new JNumericField();
		priceField.setMaxLength(6);
		priceField.setPrecision(2);
		priceField.setAllowNegative(false);
		c.gridx = 1;
		c.gridy = 2;
		nameField.setEditable(true);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(priceField, c);

		quantityField = new JNumericField(6, JNumericField.NUMERIC);
		quantityField.setAllowNegative(false);
		//quantityField.setMaxLength(6);
		//quantityField.setFormat(1);		
		
		c.gridx = 1;
		c.gridy = 3;
		nameField.setEditable(true);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(quantityField, c);

		descriptionField = new JTextField();
		c.gridx = 1;
		c.gridy = 4;
		descriptionField.setEditable(true);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(descriptionField, c);

		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SubmitButtonClicked();
			}
		});

		c.gridx = 1;
		c.gridy = 5;
		c.anchor = GridBagConstraints.PAGE_END;
		this.add(submitButton, c);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CancelButtonClicked();
			}
		});

		c.gridx = 0;
		c.gridy = 5;
		c.anchor = GridBagConstraints.PAGE_END;
		this.add(cancelButton, c);

		this.setSize(width, height);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screen.width - width) / 2,
				(screen.height - height) / 2);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0d;
		c.weighty = 0d;
		c.fill = GridBagConstraints.BOTH;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void SubmitButtonClicked() {
		boolean isComplete = true;
		long itemID = 0;
		String itemName = null;
		String itemDesc = null;
		double itemPrice = 0;
		int itemQuantity = 0;
		try {
			if (this.idField.getText().isEmpty()
					|| this.nameField.getText().isEmpty()
					|| this.priceField.getText().isEmpty()
					|| this.quantityField.getText().isEmpty()) {
				isComplete = false;
			}

			itemID = Long.parseLong(idField.getText());
			itemName = this.nameField.getText();
			itemDesc = descriptionField.getText();
			itemPrice = Double.parseDouble(priceField.getText());
			itemQuantity = Integer.parseInt(quantityField.getText());

		} catch (NumberFormatException E) {
			isComplete = false;
			log.error("NumberFormatException");
		}

		catch (Exception E) {
			isComplete = false;
			log.error(E.getMessage());
		}

		if (isComplete) {
			StockItem newStock = new StockItem(itemID, itemName, itemDesc,
					itemPrice, itemQuantity);
			System.out.println(newStock.toString());
			model.addItem(newStock);
			this.dispose();
		}
	}

	private void CancelButtonClicked() {
		this.dispose();
	}

}
