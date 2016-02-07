import javax.swing.*;

public class PersonPanel extends JPanel {
    public PersonPanel(String title) {
        // Choose to lay out components manually
        setLayout(null);

        // Make a border around the outside with the given title
        setBorder(BorderFactory.createTitledBorder(title));

        JLabel aLabel = new JLabel("First Name:");
        aLabel.setLocation(10, 20);
        aLabel.setSize(100,30);
        add(aLabel);

        aLabel = new JLabel("Last Name:");
        aLabel.setLocation(10, 55);
        aLabel.setSize(100,30);
        add(aLabel);

        aLabel = new JLabel("Street:");
        aLabel.setLocation(10, 90);
        aLabel.setSize(100,30);
        add(aLabel);

        aLabel = new JLabel("City:");
        aLabel.setLocation(10, 125);
        aLabel.setSize(100,30);
        add(aLabel);
        
        aLabel = new JLabel("Province:");
        aLabel.setLocation(10, 160);
        aLabel.setSize(100,30);
        add(aLabel);
        
        aLabel = new JLabel("Postal Code:");
        aLabel.setLocation(10, 195);
        aLabel.setSize(100,30);
        add(aLabel);

        aLabel = new JLabel("Home Phone:");
        aLabel.setLocation(280, 20);
        aLabel.setSize(100,30);
        add(aLabel);

        aLabel = new JLabel("Cell Phone:");
        aLabel.setLocation(280, 55);
        aLabel.setSize(100,30);
        add(aLabel);

        aLabel = new JLabel("Email Address:");
        aLabel.setLocation(280, 90);
        aLabel.setSize(100,30);
        add(aLabel);
        
        aLabel = new JLabel("Age:");
        aLabel.setLocation(280, 125);
        aLabel.setSize(100,30);
        add(aLabel);

        aLabel = new JLabel("Gender:");
        aLabel.setLocation(280, 160);
        aLabel.setSize(100,30);
        add(aLabel);
        
        JTextField nameField = new JTextField();
        nameField.setLocation(100, 20);
        nameField.setSize(150,30);
        add(nameField);

        JTextField streetField = new JTextField();
        streetField.setLocation(100, 55);
        streetField.setSize(150,30);
        add(streetField);

        JTextField cityField = new JTextField();
        cityField.setLocation(100, 90);
        cityField.setSize(150,30);
        add(cityField);

        JTextField provinceField = new JTextField();
        provinceField.setLocation(100, 125);
        provinceField.setSize(150,30);
        add(provinceField);
        
        JTextField homePhoneField = new JTextField();
        homePhoneField.setHorizontalAlignment(JTextField.RIGHT);
        homePhoneField.setLocation(380, 20);
        homePhoneField.setSize(150,30);
        add(homePhoneField);

        JTextField cellPhoneField = new JTextField();
        cellPhoneField.setHorizontalAlignment(JTextField.RIGHT);
        cellPhoneField.setLocation(380, 55);
        cellPhoneField.setSize(150,30);
        add(cellPhoneField);

        JTextField emailAdressField = new JTextField();
        emailAdressField.setHorizontalAlignment(JTextField.RIGHT);
        emailAdressField.setLocation(380, 90);
        emailAdressField.setSize(150,30);
        add(emailAdressField);
        
        String[] addresses = {"AB","BC","MB","NB","NL","NS","NT","NU","ON","PE","QC","SK","YT"};
        JComboBox<String> addressBox1 = new JComboBox<String>(addresses); 
        addressBox1.setLocation(100,160); 
        addressBox1.setSize(60,30); 
        add(addressBox1); 

        JTextField postalCodeField = new JTextField();
        postalCodeField.setHorizontalAlignment(JTextField.CENTER);
        postalCodeField.setLocation(100, 195);
        postalCodeField.setSize(75,30);
        add(postalCodeField);
        
        SpinnerModel limit = new SpinnerNumberModel(0, 0, 99, 1); 
        JSpinner ageSpinner = new JSpinner(limit);    
        ageSpinner.setLocation(380, 125);        
        ageSpinner.setSize(50,30);      
        add(ageSpinner);

        JRadioButton maleButton = new JRadioButton("Male");
        JRadioButton femaleButton = new JRadioButton("Female");
        maleButton.setBounds(0,0,60,30);
        femaleButton.setBounds(60,0,70,30);
        maleButton.setSelected(true);
        
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(null);
        radioPanel.setBounds(380,160,150,30);
        add(radioPanel);
        radioPanel.add(maleButton);
        radioPanel.add(femaleButton);
        

        setSize(540, 240);
    }
}

