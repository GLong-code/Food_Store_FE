package UI;

import DAO.FoodModify;
import Model.Food;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class form1 extends javax.swing.JFrame {
    private JPanel mainPanel;
    private JTextField quantityField;
    private JTextField nameField;
    private JLabel TypeLabel;
    private JLabel quantityLabel;
    private JLabel expiryDateLabel;
    private JLabel nameLabel;
    private JLabel totalLabel;
    private JLabel yearLabel;
    private JLabel monthLabel;
    private JLabel totalTitleLabel;
    private JButton changeButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton totalButton;
    private JButton searchButton;
    private JTable table1;
    private JComboBox classComboBox;
    private JComboBox expiryDayComboBox;
    private JComboBox expiryYearComboBox;
    private JComboBox expiryMonthComboBox;
    private JComboBox monthComboBox;
    private JComboBox yearComboBox;
    private JLabel attributeLabel;
    private JTextField attributeTextField;
    private JComboBox searchComboBox;
    private JButton resetButton;
    private JLabel searchLabel;
    private JButton deleteExpiredFoodButton;


    DefaultTableModel tableModel;
    ArrayList<Food> foodList = new ArrayList<>();

//    public form1(){
//        tableModel = (DefaultTableModel) table1.getModel();
//        ShowFood();
//    }


    private void ShowFood() throws IOException {
        FoodModify foodModify = new FoodModify();
        foodList = foodModify.getAllProducts();
        tableModel.setRowCount(0);
        foodList.forEach(food -> {
            tableModel.addRow(new Object[] {food.getId(), food.getName(), food.getQuantity(),
                                            food.getImportDay(), food.getExpiry(),food.getClassName(), food.getPrice()});
        });
    }


    private void createTable(){
        table1.setModel(new DefaultTableModel(
                new String[]{"ID", "Name", "Quantity", "Import Date", "Expiry Date", "Product Type", "Price"},10
        ));
    }


    public form1(String title) throws HeadlessException, IOException {
        super(title);
        this.setSize(1200,800);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.createTable();
        tableModel = (DefaultTableModel) table1.getModel();
        ShowFood();



        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                String day = expiryDayComboBox.getSelectedItem().toString();
                String month = expiryMonthComboBox.getSelectedItem().toString();
                String year = expiryYearComboBox.getSelectedItem().toString();
                String foodClass = classComboBox.getSelectedItem().toString();
                double attribute = Double.parseDouble(attributeTextField.getText());
                int foodClassType;
                String expiry = day + "/" + month + "/" + year;
                String importDay = null;

                if (foodClass.equals("Fresh")){
                    foodClassType = 2;
                } else {
                    foodClassType = 1;
                }

                try {
                    LocalDate now = LocalDate.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    importDay = now.format(formatter);

                    Food food = new Food(name, foodClassType, quantity, importDay, expiry, attribute);
                    FoodModify foodModify = new FoodModify();

                    foodModify.insert(food);

                    ShowFood();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                quantityField.setText("");
                attributeTextField.setText("");
                expiryDayComboBox.setSelectedIndex(0);
                expiryMonthComboBox.setSelectedIndex(0);
                expiryYearComboBox.setSelectedIndex(0);
                classComboBox.setSelectedIndex(0);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table1.getSelectedRow();
                if (selectedIndex >= 0){
                    Food food = foodList.get(selectedIndex);
                    int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to permanently delete ?");
//                    System.out.println("option" + option);
                    if (option == 0){
                        FoodModify foodModify = new FoodModify();
                        try {
                            foodModify.delete(food);
                            ShowFood();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    }
                }
            }
        });


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchByClassString = searchComboBox.getSelectedItem().toString();

                if (searchByClassString.equals("Name")) {
                    String input = JOptionPane.showInputDialog(null, "Input name you want to find");
                    if (input.length() > 0) {
                        FoodModify foodModify = new FoodModify();
                        try {
                            foodList = foodModify.findByName(input);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        tableModel.setRowCount(0);
                        foodList.forEach(food -> {
                            tableModel.addRow(new Object[]{food.getId(), food.getName(), food.getQuantity(),
                                    food.getImportDay(), food.getExpiry(), food.getClassName(), food.getPrice()});
                        });
                    }
                } else if (searchByClassString.equals("Expiry date")){
                    String input = JOptionPane.showInputDialog(null, "Input expiry date you want to find");
                    if (input.length() > 0) {
                        FoodModify foodModify = new FoodModify();
                        try {
                            foodList = foodModify.findByExpiry(input);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        tableModel.setRowCount(0);
                        foodList.forEach(food -> {
                            tableModel.addRow(new Object[]{food.getId(), food.getName(), food.getQuantity(),
                                    food.getImportDay(), food.getExpiry(), food.getClassName(), food.getPrice()});
                        });
                    }
                } else if (searchByClassString.equals("Product type")){
                    String input = JOptionPane.showInputDialog(null, "Input product type you want to find?");
                    int foodClass;
                    if (input.equals("Fresh") || input.equals("fresh")){
                        foodClass = 2;
                    } else {
                        foodClass = 1;
                    }
                    if (input.length() > 0) {
                        FoodModify foodModify = new FoodModify();
                        try {
                            foodList = foodModify.findByFoodClass(foodClass);

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        tableModel.setRowCount(0);
                        foodList.forEach(food -> {
                            tableModel.addRow(new Object[]{food.getId(), food.getName(), food.getQuantity(),
                                    food.getImportDay(), food.getExpiry(), food.getClassName(), food.getPrice()});
                        });
                    }
                } else if (searchByClassString.equals("All product")) {
                    try {
                        ShowFood();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }


            }
        });

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = table1.getSelectedRow();
                if (selectedIndex >= 0){
                    Food oldFood = foodList.get(selectedIndex);

                    String name = nameField.getText();
                    int quantity = Integer.parseInt(quantityField.getText());
                    String day = expiryDayComboBox.getSelectedItem().toString();
                    String month = expiryMonthComboBox.getSelectedItem().toString();
                    String year = expiryYearComboBox.getSelectedItem().toString();
                    String foodClass = classComboBox.getSelectedItem().toString();
                    double attribute = Double.parseDouble(attributeTextField.getText());
                    int foodClassType;
                    String expiry = day + "/" + month + "/" + year;
                    String importDay = null;

                    if (foodClass.equals("Fresh")){
                        foodClassType = 2;
                    } else {
                        foodClassType = 1;
                    }

                    try {
                        LocalDate now = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        importDay = now.format(formatter);

                        Food food = new Food(name, foodClassType, quantity, importDay, expiry, attribute);
                        FoodModify foodModify = new FoodModify();

                        foodModify.update(food, oldFood.getId());

                        ShowFood();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        totalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String month = monthComboBox.getSelectedItem().toString();
                String year = yearComboBox.getSelectedItem().toString();
                String date ="01/"+month+"/"+year;
                FoodModify foodModify = new FoodModify();
                try {
                    double total = foodModify.totalCost(date);
                    totalLabel.setText(Double.toString(total));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        deleteExpiredFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FoodModify foodModify = new FoodModify();
                try {
                    foodModify.deleteExpiredFood();
                    ShowFood();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        table1.addMouseListener(new MouseListener() {

            @Override
            public void mousePressed(MouseEvent e) {
                int selectedIndex = table1.getSelectedRow();
                Food food = foodList.get(selectedIndex);
                nameField.setText(food.getName());
                quantityField.setText(Integer.toString(food.getQuantity()));
                attributeTextField.setText(Double.toString(food.getAttribute()));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                LocalDate dExpiry = LocalDate.parse(food.getExpiry(),formatter);
                expiryDayComboBox.setSelectedIndex(dExpiry.getDayOfMonth());
                expiryMonthComboBox.setSelectedIndex(dExpiry.getMonthValue());
                expiryYearComboBox.setSelectedIndex(dExpiry.getYear()-2019);
                classComboBox.setSelectedIndex(food.getFoodClass()-1);
            }

            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

}
