package ee.ut.math.tvt.salessystem.ui.model;

import java.util.NoSuchElementException;
import java.util.Vector;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.BSS.comboBoxItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/**
 * Stock item table model.
 */
public class StockTableModel extends SalesSystemTableModel<StockItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StockTableModel.class);

	public StockTableModel() {
		super(new String[] {"Id", "Name", "Price", "Quantity"});
	}

	@Override
	protected Object getColumnValue(StockItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	/**
	 * Add new stock item to table. If there already is a stock item with
	 * same id, then existing item's quantity will be increased.
	 * @param stockItem
	 */
	public void addItem(final StockItem stockItem) {
		try {
			StockItem item = getItemById(stockItem.getId());
			item.setQuantity(item.getQuantity() + stockItem.getQuantity());
			log.debug("Found existing item " + stockItem.getName()
					+ " increased quantity by " + stockItem.getQuantity());
		}
		catch (NoSuchElementException e) {
			rows.add(stockItem);
			log.debug("Added " + stockItem.getName()
					+ " quantity of " + stockItem.getQuantity());
		}
		fireTableDataChanged();
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final StockItem stockItem : rows) {
			buffer.append(stockItem.getId() + "\t");
			buffer.append(stockItem.getName() + "\t");
			buffer.append(stockItem.getPrice() + "\t");
			buffer.append(stockItem.getQuantity() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}
	
	public Vector<comboBoxItem> getProductList() {//dzh need for init product combobox
		Vector<comboBoxItem> modelComboBox = new Vector<comboBoxItem>();		
		for (int i = 0; i < this.getRowCount(); i++) {
			modelComboBox.addElement(new comboBoxItem(this.getTableRows()
					.get(i).getId(), this.getTableRows().get(i).getName()));
		}
		return modelComboBox;
	}

	public void addQuantity(Long id,  int quantity) {
		try {
			StockItem item = getItemById(id);
			item.setQuantity(item.getQuantity() + quantity);
			log.debug("Found existing item " + item.getName()
					+ " increased quantity by " + quantity);
		}
		catch (NoSuchElementException e) {			
			log.debug("Not found item with barcode: " + id.toString());
		}
		fireTableDataChanged();
	}

}