package ee.ut.math.tvt.salessystem.ui.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ee.ut.math.tvt.salessystem.domain.data.Sale;

/**
 * Purchase history model.
 */
public class PurchaseHistoryTableModel extends SalesSystemTableModel<Sale> {
	private static final long serialVersionUID = 1L;

	private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

	private List<Sale> rows; // dzh 2012-11-25

	public PurchaseHistoryTableModel() {
		super(new String[] { "Id", "Time", "Sum", "Client" });
		this.rows = new ArrayList<Sale>(); // dzh 2012-11-25
	}

	@Override
	protected Object getColumnValue(Sale sale, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return sale.getId();
		case 1:
			return DATE_FORMAT.format(sale.getSellingTime());
		case 2:
			return sale.getSum();
	    case 3:
	        return sale.getClient();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final Sale sale : rows) {
			buffer.append(sale.getId() + "\t");
			//buffer.append(sale.getClient() != null ? sale.getClient().getFirstName() : "" + "\t");
			buffer.append(sale.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	@Override
	public List<Sale> getTableRows() {
		return this.rows;
	}

	@Override
	public void clear() {
		rows = new ArrayList<Sale>();
		fireTableDataChanged();
	}

}
