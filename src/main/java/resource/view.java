package resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class view extends JFrame implements ActionListener {
    // taskPanel will act as the container for the taskcomponentpanel
    // taskcomponentpanel will store all of the taskcomponents
    private JPanel taskPanel, taskComponentPanel;

    public view(){
        super("To Do List Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(commonConstants.GUI_SIZE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        addGuiComponents();
        try {
            // Thiết lập giao diện window
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private void addGuiComponents(){
        // banner text
        JLabel bannerLabel = new JLabel("To Do List");
        //bannerLabel.setFont(createFont("resources/LEMONMILK-Light.otf", 36f));
        bannerLabel.setFont(new Font("Arial", 20, 20));
        bannerLabel.setBounds(
                (commonConstants.GUI_SIZE.width - bannerLabel.getPreferredSize().width)/2,
                15,
                commonConstants.BANNER_SIZE.width,
                commonConstants.BANNER_SIZE.height
        );

        // taskpanel
        taskPanel = new JPanel();

        // taskcomponentpanel
        taskComponentPanel = new JPanel();
        taskComponentPanel.setLayout(new BoxLayout(taskComponentPanel, BoxLayout.Y_AXIS));
        taskPanel.add(taskComponentPanel);

        // add scrolling to the task panel
        JScrollPane scrollPane = new JScrollPane(taskPanel);
        scrollPane.setBounds(8, 70, commonConstants.TASKPANEL_SIZE.width, commonConstants.TASKPANEL_SIZE.height);
        scrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
        scrollPane.setMaximumSize(commonConstants.TASKPANEL_SIZE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // changing the speed of the scroll bar
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(20);

        // add task button
        JButton addTaskButton = new JButton("Add Task");
        //addTaskButton.setFont(createFont("resources/LEMONMILK-Light.otf", 18f));
        addTaskButton.setFont(new Font("Arial", 20, 20));
        addTaskButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addTaskButton.setBounds(-5, commonConstants.GUI_SIZE.height - 88,
                commonConstants.ADDTASK_BUTTON_SIZE.width, commonConstants.ADDTASK_BUTTON_SIZE.height);
        addTaskButton.addActionListener(this);

        // add to frame
        this.getContentPane().add(bannerLabel);
        this.getContentPane().add(scrollPane);
        this.getContentPane().add(addTaskButton);
    }

//    private Font createFont(String resource, float size){
//        // get the font file path
//        String filePath = getClass().getClassLoader().getResource(resource).getPath();
//
//        // check to see if the path contains a folder with spaces in them
//        if(filePath.contains("%20")){
//            filePath = getClass().getClassLoader().getResource(resource).getPath()
//                    .replaceAll("%20", " ");
//        }
//
//        // create font
//        try{
//            File customFontFile = new File(filePath);
//            Font customFont = Font.createFont(Font.TRUETYPE_FONT, customFontFile).deriveFont(size);
//            return customFont;
//        }catch(Exception e){
//            System.out.println("Error: " + e);
//        }
//        return null;
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equalsIgnoreCase("Add Task")){
            // create a task component
            taskComponent taskComponent = new taskComponent(taskComponentPanel);
            taskComponentPanel.add(taskComponent);

            // make the previous task appear disabled
            if(taskComponentPanel.getComponentCount() > 1){
                taskComponent previousTask = (taskComponent) taskComponentPanel.getComponent(
                        taskComponentPanel.getComponentCount() - 2);
                previousTask.getTaskField().setBackground(null);
            }

            // make the task field request focus after creation
            taskComponent.getTaskField().requestFocus();
            repaint();
            revalidate();
        }
    }
}
