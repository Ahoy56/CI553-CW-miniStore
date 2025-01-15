package clients;

import clients.backDoor.BackDoorController;
import clients.backDoor.BackDoorModel;
import clients.backDoor.BackDoorView;
import clients.cashier.CashierController;
import clients.cashier.CashierModel;
import clients.cashier.CashierView;
import clients.customer.CustomerController;
import clients.customer.CustomerModel;
import clients.customer.CustomerView;
import clients.packing.PackingController;
import clients.packing.PackingModel;
import clients.packing.PackingView;
import middle.LocalMiddleFactory;
import middle.MiddleFactory;
import javax.swing.*;
import java.awt.*;

/**
 * Starts all the clients (user interface)  as a single application.
 * Good for testing the system using a single application.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 * @author  Shine University of Brighton
 * @version year-2024
 */

class Main
{
  public static void main (String args[])
  {
    new Main().begin();
  }

  /**
   * Starts the system (Non distributed)
   */
  public void begin()
  {
    //DEBUG.set(true); /* Lots of debug info */
    MiddleFactory mlf = new LocalMiddleFactory();  // Direct access
    startCustomerGUI_MVC( mlf );
    startCashierGUI_MVC( mlf );
    startCashierGUI_MVC( mlf ); // you can create multiple clients
    startPackingGUI_MVC( mlf );
    startBackDoorGUI_MVC( mlf );
    startDarkModePopup(); // Start dark mode popup
  }
  
  

/**
  * start the Customer client, -search product
  * @param mlf A factory to create objects to access the stock list
  */
  public void startCustomerGUI_MVC(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();
    window.setTitle( "Customer Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    Dimension pos = PosOnScrn.getPos();
    
    CustomerModel model      = new CustomerModel(mlf);
    CustomerView view        = new CustomerView( window, mlf, pos.width, pos.height );
    CustomerController cont  = new CustomerController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model, ---view is observer, model is Observable
    window.setVisible(true);         // start Screen
  }

  /**
   * start the cashier client - customer check stock, buy product
   * @param mlf A factory to create objects to access the stock list
   */
  public void startCashierGUI_MVC(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();
    window.setTitle( "Cashier Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    Dimension pos = PosOnScrn.getPos();
    
    CashierModel model      = new CashierModel(mlf);
    CashierView view        = new CashierView( window, mlf, pos.width, pos.height );
    CashierController cont  = new CashierController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Make window visible
    model.askForUpdate();            // Initial display
  }

  /**
   * start the Packing client - for warehouse staff to pack the bought order for customer, one order at a time
   * @param mlf A factory to create objects to access the stock list
   */
   //Start the dark mode popup in a separate thread.
  
 public void startDarkModePopup() {
     new Thread(() -> {
         JDialog popup = new JDialog((JFrame) null, "Dark Mode Toggle", true);
         popup.setSize(300, 200);
         popup.setLayout(new BorderLayout());
         popup.setLocationRelativeTo(null); // Center the dialog

         JPanel panel = new JPanel();
         panel.setLayout(new BorderLayout());
         panel.setBackground(Color.WHITE); // Initial background color

         JLabel label = new JLabel("Click the button to toggle Dark Mode!", SwingConstants.CENTER);
         label.setForeground(Color.BLACK); // Initial text color
         panel.add(label, BorderLayout.CENTER);

         JButton toggleButton = new JButton("Toggle Dark Mode");
         panel.add(toggleButton, BorderLayout.SOUTH);

         toggleButton.addActionListener(new java.awt.event.ActionListener() {
             private boolean isDarkMode = false;

             @Override
             public void actionPerformed(java.awt.event.ActionEvent e) {
                 if (isDarkMode) {
                     panel.setBackground(Color.WHITE);
                     label.setForeground(Color.BLACK);
                     toggleButton.setText("Toggle Dark Mode");
                     isDarkMode = false;
                 } else {
                     panel.setBackground(Color.DARK_GRAY);
                     label.setForeground(Color.WHITE);
                     toggleButton.setText("Toggle Light Mode");
                     isDarkMode = true;
                 }
             }
         });

         popup.add(panel);
         popup.setVisible(true); // Display the popup
     }).start();
 }

  
  public void startPackingGUI_MVC(MiddleFactory mlf)
  {
    JFrame  window = new JFrame();

    window.setTitle( "Packing Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    Dimension pos = PosOnScrn.getPos();
    
    PackingModel model      = new PackingModel(mlf);
    PackingView view        = new PackingView( window, mlf, pos.width, pos.height );
    PackingController cont  = new PackingController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Make window visible
  }
  
  /**
   * start the BackDoor client - store staff to check and update stock
   * @param mlf A factory to create objects to access the stock list
   */
  public void startBackDoorGUI_MVC(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();

    window.setTitle( "BackDoor Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    Dimension pos = PosOnScrn.getPos();
    
    BackDoorModel model      = new BackDoorModel(mlf);
    BackDoorView view        = new BackDoorView( window, mlf, pos.width, pos.height );
    BackDoorController cont  = new BackDoorController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Make window visible
  }
  
  
}

