package clients.cashier;

import catalogue.Basket;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * View of the model 
 */
public class CashierView implements Observer {
    private static final int H = 300;       // Height of window pixels
    private static final int W = 400;      // Width of window pixels
    
    private static final String CHECK  = "Check";
    private static final String BUY    = "Buy";
    private static final String BOUGHT = "Bought/Pay";

    private final JLabel pageTitle = new JLabel();
    private final JLabel theAction = new JLabel();
    private final JTextField theInput = new JTextField();
    private final JTextArea theOutput = new JTextArea();
    private final JScrollPane theSP = new JScrollPane();
    private final JButton theBtCheck = new JButton(CHECK);
    private final JButton theBtBuy = new JButton(BUY);
    private final JButton theBtBought = new JButton(BOUGHT);

    private StockReadWriter theStock = null;
    private OrderProcessing theOrder = null;
    private CashierController cont = null;

    public CashierView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
        try {
            theStock = mf.makeStockReadWriter();        // Database access
            theOrder = mf.makeOrderProcessing();        // Process order
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        Container cp = rpc.getContentPane();            // Content Pane
        Container rootWindow = (Container) rpc;         // Root Window
        cp.setLayout(null);                             // No layout manager
        rootWindow.setSize(W, H);                       // Size of Window
        rootWindow.setLocation(x, y);

        Font f = new Font("Monospaced", Font.PLAIN, 12); // Font f is

        pageTitle.setBounds(110, 0, 270, 20);
        pageTitle.setText("Thank You for Shopping at MiniStrore");
        cp.add(pageTitle);

        theBtCheck.setBounds(16, 25 + 60 * 0, 80, 40);  // Check Button
        theBtCheck.addActionListener(
            e -> cont.doCheck(theInput.getText()));
        cp.add(theBtCheck);

        theBtBuy.setBounds(16, 25 + 60 * 1, 80, 40);    // Buy button
        theBtBuy.addActionListener(
            e -> cont.doBuy());
        cp.add(theBtBuy);

        theBtBought.setBounds(16, 25 + 60 * 3, 80, 40); // Bought Button
        theBtBought.addActionListener(
            e -> cont.doBought());
        cp.add(theBtBought);

        theAction.setBounds(110, 25, 270, 20);          // Message area
        theAction.setText("");                          // Blank
        cp.add(theAction);

        theInput.setBounds(110, 50, 270, 40);           // Input Area
        theInput.setText("");                           // Blank
        theInput.setBackground(new Color(128, 0, 128)); // Purple background
        theInput.setForeground(Color.ORANGE);            // White text for readability
        cp.add(theInput);

        theSP.setBounds(110, 100, 270, 160);            // Scrolling pane
        theOutput.setText("");                          // Blank
        theOutput.setFont(f);                           // Uses font
        theOutput.setBackground(new Color(128, 0, 128)); // Purple background
        theOutput.setForeground(Color.ORANGE);           // White text for readability
        cp.add(theSP);
        theSP.getViewport().add(theOutput);

        rootWindow.setVisible(true);                    // Make visible
        theInput.requestFocus();                        // Focus is here
    }

    public void setController(CashierController c) {
        cont = c;
    }

    @Override
    public void update(Observable modelC, Object arg) {
        CashierModel model = (CashierModel) modelC;
        String message = (String) arg;
        theAction.setText(message);
        Basket basket = model.getBasket();
        if (basket == null) {
            theOutput.setText("Customers order");
        } else {
            theOutput.setText(basket.getDetails());
        }
        theInput.requestFocus();
    }
}
